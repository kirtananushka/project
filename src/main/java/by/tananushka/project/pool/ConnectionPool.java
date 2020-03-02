package by.tananushka.project.pool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

/**
 * The type Connection pool.
 */
public class ConnectionPool {

	private static ConnectionPool instance;
	private static AtomicBoolean isInstanceCreated = new AtomicBoolean(false);
	private static Logger log = LogManager.getLogger();
	private static ReentrantLock lock = new ReentrantLock();
	private BlockingQueue<ProxyConnection> freeConnectionQueue;
	private Queue<ProxyConnection> givenConnectionQueue;
	private String driverName;
	private String url;
	private String user;
	private String password;
	private int poolSize;

	private ConnectionPool() {
		DbResourceManager dBResourceManager = DbResourceManager.getInstance();
		driverName = dBResourceManager.getValue(DbParameter.DB_DRIVER);
		url = dBResourceManager.getValue(DbParameter.DB_URL);
		user = dBResourceManager.getValue(DbParameter.DB_USER);
		password = dBResourceManager.getValue(DbParameter.DB_PASS);
		poolSize = Integer.parseInt(dBResourceManager.getValue(DbParameter.DB_POOL_SIZE));
		try {
			initializeConnectionPool();
		} catch (ConnectionPoolException e) {
			log.fatal("Connection creation error.");
			throw new RuntimeException("Connection creation error.", e);
		}
	}

	/**
	 * Gets instance.
	 *
	 * @return the instance
	 */
	public static ConnectionPool getInstance() {
		if (!isInstanceCreated.get()) {
			lock.lock();
			try {
				if (instance == null) {
					instance = new ConnectionPool();
					isInstanceCreated.set(true);
				}
			} finally {
				lock.unlock();
			}
		}
		return instance;
	}

	private void initializeConnectionPool() throws ConnectionPoolException {
		try {
			Class.forName(driverName);
			freeConnectionQueue = new LinkedBlockingQueue<>(poolSize);
			givenConnectionQueue = new LinkedBlockingQueue<>(poolSize);
			createConnections(poolSize);
			checkConnectionsNumber();
		} catch (SQLException e) {
			log.fatal("SQL exception while connection pool initialization.");
			throw new ConnectionPoolException("SQL exception while connection pool initialization.", e);
		} catch (ClassNotFoundException e) {
			log.fatal("Database driver class is not found.");
			throw new ConnectionPoolException("Database driver class is not found.", e);
		}
	}

	private void createConnections(int connectionsNumber) throws SQLException {
		for (int i = 0; i < connectionsNumber; i++) {
			Connection connection = DriverManager.getConnection(url, user, password);
			ProxyConnection pooledConnection = new ProxyConnection(connection);
			freeConnectionQueue.add(pooledConnection);
			log.debug("Connection was added to pool.");
		}
	}

	private void checkConnectionsNumber() throws SQLException {
		int freeConnectionSize = getFreeConnectionSize();
		int givenConnectionSize = getGivenConnectionSize();
		log.debug("Connections: free/given/poolsize: {}/{}/{}.", freeConnectionSize,
						givenConnectionSize, poolSize);
		if ((givenConnectionSize + freeConnectionSize) == 0) {
			log.fatal("There are no connections in the pool.");
			throw new RuntimeException("There are no connections in the pool.");
		}
		int difference = poolSize - (givenConnectionSize + freeConnectionSize);
		if (difference > 0) {
			log.debug("Connection is missed. Connections difference: {}", difference);
			createConnections(difference);
		}
	}

	private int getFreeConnectionSize() {
		return freeConnectionQueue.size();
	}

	private int getGivenConnectionSize() {
		return givenConnectionQueue.size();
	}

	/**
	 * Take connection proxy connection.
	 *
	 * @return the proxy connection
	 */
	public ProxyConnection takeConnection() {
		ProxyConnection connection = null;
		try {
			connection = freeConnectionQueue.take();
			givenConnectionQueue.add(connection);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			log.error("Exception while connecting to data source.", e);
		}
		return connection;
	}

	/**
	 * Clear connection pool.
	 */
	public void clearConnectionPool() {
		try {
			closeConnectionQueue(givenConnectionQueue);
			closeConnectionQueue(freeConnectionQueue);
			log.debug("Connections were destroyed.");
		} catch (SQLException e) {
			log.error("Exception while clearing the connection pool.", e);
		}
		Enumeration<Driver> drivers = DriverManager.getDrivers();
		while (drivers.hasMoreElements()) {
			Driver driver = drivers.nextElement();
			try {
				DriverManager.deregisterDriver(driver);
				log.debug("Driver {} was deregistered.", driver);
			} catch (SQLException e) {
				log.error("Exception while deregistering driver {}.", driver, e);
			}
		}
		log.debug("All drivers were deregistered.");
	}

	private void closeConnectionQueue(Queue<ProxyConnection> queue) throws SQLException {
		ProxyConnection connection;
		while ((connection = queue.poll()) != null) {
			if (!connection.getAutoCommit()) {
				connection.commit();
			}
			connection.reallyClose();
		}
	}

	private class ProxyConnection implements Connection {

		private Connection connection;

		/**
		 * Instantiates a new Proxy connection.
		 *
		 * @param connection the connection
		 * @throws SQLException the sql exception
		 */
		ProxyConnection(Connection connection) throws SQLException {
			this.connection = connection;
			this.connection.setAutoCommit(true);
		}

		/**
		 * Really close.
		 *
		 * @throws SQLException the sql exception
		 */
		void reallyClose() throws SQLException {
			connection.close();
		}

		@Override
		public void close() throws SQLException {
			checkConnectionsNumber();
			if (connection == null) {
				log.error("Attempt to close the null connection.");
				throw new SQLException("Attempt to close the null connection.");
			}
			if (connection.isClosed()) {
				log.error("Attempt to close the closed connection.");
				throw new SQLException("Attempt to close the closed connection.");
			}
			if (!(this instanceof ProxyConnection)) {
				log.error("Attempt to put connection, which was created outside the pool.");
				throw new SQLException("Attempt to put connection, which was created outside the pool.");
			}
			if (connection.isReadOnly()) {
				connection.setReadOnly(false);
			}
			this.setAutoCommit(true);
			if (!givenConnectionQueue.remove(this)) {
				log.error("Exception while deletion connection from the pool of given connections.");
				throw new SQLException("Exception while deletion connection "
								+ "from the pool of given connections.");
			}
			if (!freeConnectionQueue.offer(this)) {
				log.error("Exception while placing connection in the pool.");
				throw new SQLException("Exception while placing connection in the pool.");
			}
			checkConnectionsNumber();
		}

		@Override
		public Statement createStatement() throws SQLException {
			return connection.createStatement();
		}

		@Override
		public PreparedStatement prepareStatement(String sql) throws SQLException {
			return connection.prepareStatement(sql);
		}

		@Override
		public CallableStatement prepareCall(String sql) throws SQLException {
			return connection.prepareCall(sql);
		}

		@Override
		public String nativeSQL(String sql) throws SQLException {
			return connection.nativeSQL(sql);
		}

		@Override
		public boolean getAutoCommit() throws SQLException {
			return connection.getAutoCommit();
		}

		@Override
		public void setAutoCommit(boolean autoCommit) throws SQLException {
			connection.setAutoCommit(autoCommit);
		}

		@Override
		public void commit() throws SQLException {
			connection.commit();
		}

		@Override
		public void rollback() throws SQLException {
			connection.rollback();
		}

		@Override
		public boolean isClosed() throws SQLException {
			return connection.isClosed();
		}

		@Override
		public DatabaseMetaData getMetaData() throws SQLException {
			return connection.getMetaData();
		}

		@Override
		public boolean isReadOnly() throws SQLException {
			return connection.isReadOnly();
		}

		@Override
		public void setReadOnly(boolean readOnly) throws SQLException {
			connection.setReadOnly(readOnly);
		}

		@Override
		public String getCatalog() throws SQLException {
			return connection.getCatalog();
		}

		@Override
		public void setCatalog(String catalog) throws SQLException {
			connection.setCatalog(catalog);
		}

		@Override
		public int getTransactionIsolation() throws SQLException {
			return connection.getTransactionIsolation();
		}

		@Override
		public void setTransactionIsolation(int level) throws SQLException {
			connection.setTransactionIsolation(level);
		}

		@Override
		public SQLWarning getWarnings() throws SQLException {
			return connection.getWarnings();
		}

		@Override
		public void clearWarnings() throws SQLException {
			connection.clearWarnings();
		}

		@Override
		public Statement createStatement(int resultSetType, int resultSetConcurrency)
						throws SQLException {
			return connection.createStatement(resultSetType, resultSetConcurrency);
		}

		@Override
		public PreparedStatement prepareStatement(String sql, int resultSetType,
		                                          int resultSetConcurrency) throws SQLException {
			return connection.prepareStatement(sql, resultSetType, resultSetConcurrency);
		}

		@Override
		public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency)
						throws SQLException {
			return connection.prepareCall(sql, resultSetType, resultSetConcurrency);
		}

		@Override
		public Map<String, Class<?>> getTypeMap() throws SQLException {
			return connection.getTypeMap();
		}

		@Override
		public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
			connection.setTypeMap(map);
		}

		@Override
		public int getHoldability() throws SQLException {
			return connection.getHoldability();
		}

		@Override
		public void setHoldability(int holdability) throws SQLException {
			connection.setHoldability(holdability);
		}

		@Override
		public Savepoint setSavepoint() throws SQLException {
			return connection.setSavepoint();
		}

		@Override
		public Savepoint setSavepoint(String name) throws SQLException {
			return connection.setSavepoint(name);
		}

		@Override
		public void rollback(Savepoint savepoint) throws SQLException {
			connection.rollback(savepoint);
		}

		@Override
		public void releaseSavepoint(Savepoint savepoint) throws SQLException {
			connection.releaseSavepoint(savepoint);
		}

		@Override
		public Statement createStatement(int resultSetType, int resultSetConcurrency,
		                                 int resultSetHoldability) throws SQLException {
			return connection.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability);
		}

		@Override
		public PreparedStatement prepareStatement(String sql, int resultSetType,
		                                          int resultSetConcurrency, int resultSetHoldability)
						throws SQLException {
			return connection
							.prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
		}

		@Override
		public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency,
		                                     int resultSetHoldability) throws SQLException {
			return connection.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
		}

		@Override
		public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys)
						throws SQLException {
			return connection.prepareStatement(sql, autoGeneratedKeys);
		}

		@Override
		public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
			return connection.prepareStatement(sql, columnIndexes);
		}

		@Override
		public PreparedStatement prepareStatement(String sql, String[] columnNames)
						throws SQLException {
			return connection.prepareStatement(sql, columnNames);
		}

		@Override
		public Clob createClob() throws SQLException {
			return connection.createClob();
		}

		@Override
		public Blob createBlob() throws SQLException {
			return connection.createBlob();
		}

		@Override
		public NClob createNClob() throws SQLException {
			return connection.createNClob();
		}

		@Override
		public SQLXML createSQLXML() throws SQLException {
			return connection.createSQLXML();
		}

		@Override
		public boolean isValid(int timeout) throws SQLException {
			return connection.isValid(timeout);
		}

		@Override
		public void setClientInfo(String name, String value) throws SQLClientInfoException {
			connection.setClientInfo(name, value);
		}

		@Override
		public String getClientInfo(String name) throws SQLException {
			return connection.getClientInfo(name);
		}

		@Override
		public Properties getClientInfo() throws SQLException {
			return connection.getClientInfo();
		}

		@Override
		public void setClientInfo(Properties properties) throws SQLClientInfoException {
			connection.setClientInfo(properties);
		}

		@Override
		public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
			return connection.createArrayOf(typeName, elements);
		}

		@Override
		public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
			return connection.createStruct(typeName, attributes);
		}

		@Override
		public String getSchema() throws SQLException {
			return connection.getSchema();
		}

		@Override
		public void setSchema(String schema) throws SQLException {
			connection.setSchema(schema);
		}

		@Override
		public void abort(Executor executor) throws SQLException {
			connection.abort(executor);
		}

		@Override
		public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {
			connection.setNetworkTimeout(executor, milliseconds);
		}

		@Override
		public int getNetworkTimeout() throws SQLException {
			return connection.getNetworkTimeout();
		}

		@Override
		public <T> T unwrap(Class<T> iface) throws SQLException {
			return connection.unwrap(iface);
		}

		@Override
		public boolean isWrapperFor(Class<?> iface) throws SQLException {
			return connection.isWrapperFor(iface);
		}
	}
}
