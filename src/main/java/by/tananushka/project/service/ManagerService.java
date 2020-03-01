package by.tananushka.project.service;

import by.tananushka.project.bean.Manager;
import by.tananushka.project.controller.SessionContent;

import java.util.List;
import java.util.Optional;

/**
 * The interface Manager service.
 */
public interface ManagerService {

	/**
	 * Find active managers list.
	 *
	 * @return the list
	 * @throws ServiceException the service exception
	 */
	List<Manager> findActiveManagers() throws ServiceException;

	/**
	 * Find all managers list.
	 *
	 * @return the list
	 * @throws ServiceException the service exception
	 */
	List<Manager> findAllManagers() throws ServiceException;

	/**
	 * Find manager by id optional.
	 *
	 * @param strManagerId the str manager id
	 * @return the optional
	 * @throws ServiceException the service exception
	 */
	Optional<Manager> findManagerById(String strManagerId) throws ServiceException;

	/**
	 * Find manager by id optional.
	 *
	 * @param managerId the manager id
	 * @return the optional
	 * @throws ServiceException the service exception
	 */
	Optional<Manager> findManagerById(int managerId) throws ServiceException;

	/**
	 * Appoint manager optional.
	 *
	 * @param content the content
	 * @return the optional
	 * @throws ServiceException the service exception
	 */
	Optional<Manager> appointManager(SessionContent content) throws ServiceException;

	/**
	 * Update manager optional.
	 *
	 * @param content the content
	 * @return the optional
	 * @throws ServiceException the service exception
	 */
	Optional<Manager> updateManager(SessionContent content) throws ServiceException;
}
