package by.tananushka.project.dao;

import by.tananushka.project.bean.Admin;

import java.util.Optional;

/**
 * The interface Admin dao.
 */
public interface AdminDao extends AbstractDao {

	/**
	 * Update admin optional.
	 *
	 * @param admin the admin
	 * @return the optional
	 * @throws DaoException the dao exception
	 */
	Optional<Admin> updateAdmin(Admin admin) throws DaoException;

	/**
	 * Find admin optional.
	 *
	 * @return the optional
	 * @throws DaoException the dao exception
	 */
	Optional<Admin> findAdmin() throws DaoException;
}
