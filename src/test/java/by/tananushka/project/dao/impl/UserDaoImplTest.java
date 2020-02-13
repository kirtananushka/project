package by.tananushka.project.dao.impl;

import by.tananushka.project.bean.User;
import by.tananushka.project.bean.UserRole;
import by.tananushka.project.dao.DaoException;
import by.tananushka.project.dao.UserDao;
import org.testng.Assert;
import org.testng.annotations.Test;

public class UserDaoImplTest {

	private static final String LOGIN = "admin";
	private static final String PASSWORD = "password";
	private static final int ID = 1;
	private static UserDao userDao = UserDaoImpl.getInstance();

	@Test(description = "Check dao authentication method")
	public void testAuthentication() {
		User user = null;
		try {
			user = userDao.authentication(LOGIN, PASSWORD).orElse(null);
		} catch (DaoException e) {
			e.printStackTrace();
		}
		assert user != null;
		Assert.assertEquals(UserRole.ADMIN, user.getRole());
	}

	@Test(description = "Check dao confirmation method")
	public void testEmailConfirmation() {
		boolean confirmation = false;
		try {
			confirmation = userDao.emailConfirmation(ID);
		} catch (DaoException e) {
			e.printStackTrace();
		}
		Assert.assertTrue(confirmation);
	}
}