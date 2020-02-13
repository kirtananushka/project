package by.tananushka.project.service.impl;

import by.tananushka.project.bean.User;
import by.tananushka.project.bean.UserRole;
import by.tananushka.project.service.ServiceException;
import by.tananushka.project.service.ServiceProvider;
import by.tananushka.project.service.UserService;
import org.testng.Assert;
import org.testng.annotations.Test;

public class UserServiceImplTest {

	private static final String LOGIN = "admin";
	private static final String PASSWORD = "password";
	private static final String WRONG_PASSWORD = "wrongpassword";
	private static final int ID = 1;
	private static final int ID2 = -1;
	private UserService userService = ServiceProvider.getInstance().getUserService();

	@Test(description = "Check service authentication method")
	public void testAuthentication() {
		User user = null;
		try {
			user = userService.authentication(LOGIN, PASSWORD).orElse(null);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		assert user != null;
		Assert.assertEquals(UserRole.ADMIN, user.getRole());
	}

	@Test(description = "Check service authentication method with wrong password")
	public void testAuthentication2() {
		User user = null;
		try {
			user = userService.authentication(LOGIN, WRONG_PASSWORD).orElse(null);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		Assert.assertNull(user);
	}

	@Test(description = "Check service confirmation method")
	public void testEmailConfirmation1() {
		boolean confirmation = false;
		try {
			confirmation = userService.emailConfirmation(ID);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		Assert.assertTrue(confirmation);
	}

	@Test(description = "Check service confirmation method with negative ID", expectedExceptions =
					ServiceException.class)
	public void testEmailConfirmation2() throws ServiceException {
		boolean confirmation = false;
		confirmation = userService.emailConfirmation(ID2);
	}
}

