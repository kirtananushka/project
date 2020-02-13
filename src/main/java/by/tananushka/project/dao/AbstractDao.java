package by.tananushka.project.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface AbstractDao {

	default void closeResultSet(ResultSet resultSet) throws DaoException {
		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException e) {
				throw new DaoException("Result set was not close", e);
			}
		}
	}
}
