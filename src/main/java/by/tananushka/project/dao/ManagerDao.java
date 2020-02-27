package by.tananushka.project.dao;

import by.tananushka.project.bean.Manager;

import java.util.List;
import java.util.Optional;

public interface ManagerDao extends AbstractDao {

	Optional<Manager> updateManager(Manager manager) throws DaoException;

	List<Manager> findActiveManagers() throws DaoException;

	List<Manager> findAllManagers() throws DaoException;

	Optional<Manager> findManagerById(int managerId) throws DaoException;

	Optional<Manager> appointManager(int clientId) throws DaoException;
}
