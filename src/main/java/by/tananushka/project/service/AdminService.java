package by.tananushka.project.service;

import by.tananushka.project.bean.Admin;
import by.tananushka.project.controller.SessionContent;

import java.util.Optional;

/**
 * The interface Admin service.
 */
public interface AdminService {

	/**
	 * Update admin optional.
	 *
	 * @param content the content
	 * @return the optional
	 * @throws ServiceException the service exception
	 */
	Optional<Admin> updateAdmin(SessionContent content) throws ServiceException;
}
