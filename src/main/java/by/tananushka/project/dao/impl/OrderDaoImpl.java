package by.tananushka.project.dao.impl;

import by.tananushka.project.bean.Client;
import by.tananushka.project.bean.Show;
import by.tananushka.project.bean.TicketOrder;
import by.tananushka.project.dao.DaoException;
import by.tananushka.project.dao.OrderDao;
import by.tananushka.project.dao.SqlColumnsName;
import by.tananushka.project.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

/**
 * The type Order dao.
 */
public class OrderDaoImpl implements OrderDao {

	private static final String CREATE_ORDER =
					"INSERT INTO orders (client_id_fk, films_shows_id_fk, ticket_cost, tickets_num)\n"
									+ "VALUES (?, ?, ?, ?);";
	private static final String FIND_ORDER_BY_ID =
					"SELECT order_id, client_id_fk, films_shows_id_fk, ticket_cost, tickets_num, ordering_date\n"
									+ "FROM orders WHERE order_id = ?;";
	private static final String FIND_ORDERS_BY_CLIENT_ID =
					"SELECT order_id, client_id_fk, films_shows_id_fk, orders.ticket_cost, tickets_num,\n"
									+ "ordering_date FROM orders INNER JOIN films_shows\n"
									+ "ON films_shows_id_fk = films_shows_id\n"
									+ "WHERE client_id_fk = ? ORDER BY show_date_time;";
	private static final String FIND_ALL_ORDERS =
					"SELECT order_id, client_id_fk, films_shows_id_fk, orders.ticket_cost, tickets_num,\n"
									+ "ordering_date FROM orders;";
	private static final String UPDATE_PLACES =
					"UPDATE films_shows SET show_free_places = show_free_places - ?\n"
									+ "WHERE films_shows_id = ?;";
	private static Logger log = LogManager.getLogger();
	private static OrderDao orderDao = new OrderDaoImpl();
	private final Calendar timezone = Calendar.getInstance(TimeZone.getTimeZone("GMT+3:00"));

	private OrderDaoImpl() {
	}

	/**
	 * Gets instance.
	 *
	 * @return the instance
	 */
	public static OrderDao getInstance() {
		return orderDao;
	}

	@Override
	public Optional<TicketOrder> createOrder(TicketOrder order) throws DaoException {
		Optional<TicketOrder> orderOptional = Optional.empty();
		try (Connection connection = ConnectionPool.getInstance().takeConnection();
		     PreparedStatement createOrderStatement = connection.prepareStatement(CREATE_ORDER,
						     Statement.RETURN_GENERATED_KEYS);
		     PreparedStatement changeFreePlaces = connection.prepareStatement(UPDATE_PLACES)) {
			ResultSet resultSet = null;
			try {
				connection.setAutoCommit(false);
				createOrderStatement.setInt(1, order.getClient().getId());
				createOrderStatement.setInt(2, order.getShow().getId());
				createOrderStatement.setBigDecimal(3, order.getShow().getCost());
				createOrderStatement.setInt(4, order.getTicketsNumber());
				createOrderStatement.execute();
				resultSet = createOrderStatement.getGeneratedKeys();
				if (resultSet.next()) {
					order.setId(resultSet.getInt(1));
				}
				changeFreePlaces.setInt(1, order.getTicketsNumber());
				changeFreePlaces.setInt(2, order.getShow().getId());
				changeFreePlaces.execute();
				connection.commit();
				orderOptional = findOrderById(order.getId());
			} catch (SQLException e) {
				connection.rollback();
				throw new DaoException("Order creation failed.", e);
			} finally {
				connection.setAutoCommit(true);
				closeResultSet(resultSet);
			}
		} catch (SQLException e) {
			throw new DaoException("SQL exception while order creation.", e);
		}
		return orderOptional;
	}

	@Override
	public Optional<TicketOrder> findOrderById(int orderId) throws DaoException {
		Optional<TicketOrder> orderOptional = Optional.empty();
		ResultSet findOrderResultSet = null;
		try (Connection connection = ConnectionPool.getInstance().takeConnection();
		     PreparedStatement findOrderStatement = connection.prepareStatement(
						     FIND_ORDER_BY_ID)) {
			findOrderStatement.setInt(1, orderId);
			findOrderResultSet = findOrderStatement.executeQuery();
			if (findOrderResultSet.first()) {
				orderOptional = Optional.of(findOrderQuery(findOrderResultSet));
			}
		} catch (SQLException e) {
			throw new DaoException("SQLException while getting order by ID.", e);
		} finally {
			closeResultSet(findOrderResultSet);
		}
		return orderOptional;
	}

	@Override
	public List<TicketOrder> findOrdersByClientId(int clientId) throws DaoException {
		List<TicketOrder> ordersList = new ArrayList<>();
		ResultSet resultSet = null;
		try (Connection connection = ConnectionPool.getInstance().takeConnection();
		     PreparedStatement findOrdersStatement = connection.prepareStatement(
						     FIND_ORDERS_BY_CLIENT_ID)) {
			findOrdersStatement.setInt(1, clientId);
			resultSet = findOrdersStatement.executeQuery();
			while (resultSet.next()) {
				TicketOrder order = findOrderQuery(resultSet);
				ordersList.add(order);
			}
		} catch (SQLException e) {
			throw new DaoException("SQLException while getting orders by client ID.", e);
		} finally {
			closeResultSet(resultSet);
		}
		return ordersList;
	}

	@Override
	public List<TicketOrder> findAllOrders() throws DaoException {
		List<TicketOrder> ordersList = new ArrayList<>();
		ResultSet resultSet = null;
		try (Connection connection = ConnectionPool.getInstance().takeConnection();
		     PreparedStatement findOrdersStatement = connection.prepareStatement(
						     FIND_ALL_ORDERS)) {
			resultSet = findOrdersStatement.executeQuery();
			while (resultSet.next()) {
				TicketOrder order = findOrderQuery(resultSet);
				ordersList.add(order);
			}
		} catch (SQLException e) {
			throw new DaoException("SQLException while getting all orders.", e);
		} finally {
			closeResultSet(resultSet);
		}
		return ordersList;
	}

	private TicketOrder findOrderQuery(ResultSet findOrderResultSet)
					throws SQLException, DaoException {
		TicketOrder order = new TicketOrder();
		order.setId(findOrderResultSet.getInt(SqlColumnsName.ORDER_ID));
		int clientId = findOrderResultSet.getInt(SqlColumnsName.CLIENT_ID_FK);
		Client client = new Client();
		Optional<Client> clientOptional = ClientDaoImpl.getInstance().findClientById(clientId);
		if (clientOptional.isPresent()) {
			client = clientOptional.get();
		}
		order.setClient(client);
		int showId = findOrderResultSet.getInt(SqlColumnsName.FILMS_SHOWS_ID_FK);
		Show show = new Show();
		Optional<Show> showOptional = ShowDaoImpl.getInstance().findShowById(showId);
		if (showOptional.isPresent()) {
			show = showOptional.get();
		}
		order.setShow(show);
		order.setTicketCost(findOrderResultSet.getBigDecimal(SqlColumnsName.TICKET_COST));
		order.setTicketsNumber(findOrderResultSet.getInt(SqlColumnsName.TICKETS_NUM));
		order.setOrderingDate(findOrderResultSet.getTimestamp(SqlColumnsName.ORDERING_DATE,
						timezone).toLocalDateTime());
		return order;
	}
}

