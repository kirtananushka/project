package by.tananushka.project.dao;

import by.tananushka.project.bean.Admin;

import java.util.Optional;

public interface AdminDao extends AbstractDao {

	Optional<Admin> updateAdmin(Admin admin) throws DaoException;

	Optional<Admin> findAdmin() throws DaoException;
}
