package by.tananushka.project.dao;

import by.tananushka.project.bean.User;
import by.tananushka.project.bean.UserRole;

import java.util.List;
import java.util.Optional;

public interface UserDao extends AbstractDao {

	Optional<User> authentication(String login, String password) throws DaoException;

	List<List<? extends User>> findAllUsers() throws DaoException;

	Optional<UserRole> findRoleById(int userId) throws DaoException;

	boolean emailConfirmation(int userId) throws DaoException;

	boolean checkLoginAndEmail(String login, String email) throws DaoException;

	boolean setNewPassword(String login, String password) throws DaoException;

	boolean deleteUser(int userId) throws DaoException;

}
