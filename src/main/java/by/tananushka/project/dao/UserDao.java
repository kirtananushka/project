package by.tananushka.project.dao;

import by.tananushka.project.bean.User;

import java.util.Optional;

public interface UserDao extends AbstractDao {

	Optional<User> authentication(String login, String password) throws DaoException;

	boolean emailConfirmation(int userId) throws DaoException;
}
