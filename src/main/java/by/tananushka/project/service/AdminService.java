package by.tananushka.project.service;

import by.tananushka.project.bean.Admin;
import by.tananushka.project.controller.SessionContent;

import java.util.Optional;

public interface AdminService {

	Optional<Admin> updateAdmin(SessionContent content) throws ServiceException;
}
