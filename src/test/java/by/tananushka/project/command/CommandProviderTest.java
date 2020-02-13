package by.tananushka.project.command;

import by.tananushka.project.command.impl.common.AuthenticationCommand;
import by.tananushka.project.command.impl.common.NoSuchCommand;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CommandProviderTest {

	private CommandProvider commandProvider = CommandProvider.getInstance();

	@Test(description = "Check NPE", expectedExceptions = NullPointerException.class)
	public void testGetCommand0() {
		commandProvider.getCommand(null);
	}

	@Test(description = "Check wrong command")
	public void testGetCommand1() {
		Command noSuchCommand = commandProvider.getCommand("no such command");
		Assert.assertTrue(noSuchCommand instanceof NoSuchCommand);
	}

	@Test(description = "Check right command")
	public void testGetCommand2() {
		Command authenticationCommand = commandProvider.getCommand("authentication");
		Assert.assertTrue(authenticationCommand instanceof AuthenticationCommand);
	}
}