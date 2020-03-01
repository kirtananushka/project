package by.tananushka.project.dao;

import by.tananushka.project.bean.Manager;

import java.util.List;
import java.util.Optional;

/**
 * The interface Manager dao.
 */
public interface ManagerDao extends AbstractDao {

	/**
	 * Update manager optional.
	 *
	 * @param manager the manager
	 * @return the optional
	 * @throws DaoException the dao exception
	 */
	Optional<Manager> updateManager(Manager manager) throws DaoException;

	/**
	 * Find active managers list.
	 *
	 * @return the list
	 * @throws DaoException the dao exception
	 */
	List<Manager> findActiveManagers() throws DaoException;

	/**
	 * Find all managers list.
	 *
	 * @return the list
	 * @throws DaoException the dao exception
	 */
	List<Manager> findAllManagers() throws DaoException;

	/**
	 * Find manager by id optional.
	 *
	 * @param managerId the manager id
	 * @return the optional
	 * @throws DaoException the dao exception
	 */
	Optional<Manager> findManagerById(int managerId) throws DaoException;

	/**
	 * Appoint manager optional.
	 *
	 * @param clientId the client id
	 * @return the optional
	 * @throws DaoException the dao exception
	 */
	Optional<Manager> appointManager(int clientId) throws DaoException;
}
