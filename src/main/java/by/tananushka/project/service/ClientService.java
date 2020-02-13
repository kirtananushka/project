package by.tananushka.project.service;

import by.tananushka.project.bean.Client;
import by.tananushka.project.controller.SessionContent;

public interface ClientService {

	Client createClient(SessionContent content) throws ServiceException;

	boolean checkLogin(String login) throws ServiceException;
}
