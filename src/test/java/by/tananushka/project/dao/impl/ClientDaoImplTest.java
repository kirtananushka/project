package by.tananushka.project.dao.impl;

import by.tananushka.project.dao.ClientDao;
import by.tananushka.project.dao.DaoException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ClientDaoImplTest {

	@Test
	public void testCheckLogin() {
		ClientDao clientDao = ClientDaoImpl.getInstance();
		String login = "admin";
		boolean result = true;
		try {
			result = !clientDao.checkLogin(login);
		} catch (DaoException e) {
			e.printStackTrace();
		}
		Assert.assertTrue(result);
	}
}