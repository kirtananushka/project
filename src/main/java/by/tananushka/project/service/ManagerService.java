package by.tananushka.project.service;

import by.tananushka.project.bean.Manager;
import by.tananushka.project.controller.SessionContent;

import java.util.List;
import java.util.Optional;

public interface ManagerService {

	Optional<Manager> createManager(SessionContent content) throws ServiceException;

	Optional<Manager> updateManager(SessionContent content) throws ServiceException;

	List<Manager> findActiveManagers() throws ServiceException;

	List<Manager> findAllManagers() throws ServiceException;

	Optional<Manager> findManagerById(String strManagerId) throws ServiceException;

	Optional<Manager> findManagerById(int managerId) throws ServiceException;
}
