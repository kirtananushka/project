package by.tananushka.project.command;

import by.tananushka.project.command.impl.ViewProfileCommand;
import by.tananushka.project.command.impl.common.AuthenticationCommand;
import by.tananushka.project.command.impl.common.ChangeLocaleCommand;
import by.tananushka.project.command.impl.common.EmailConfirmationCommand;
import by.tananushka.project.command.impl.common.LogoutCommand;
import by.tananushka.project.command.impl.common.NoSuchCommand;
import by.tananushka.project.command.impl.common.RegistrationCommand;
import by.tananushka.project.command.impl.common.RegistrationInitializeCommand;
import by.tananushka.project.command.impl.common.SendMessageCommand;
import by.tananushka.project.command.impl.films.FindAvailableFilmsCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.EnumMap;
import java.util.Map;

public class CommandProvider {

	private static final CommandProvider instance = new CommandProvider();
	private static Logger log = LogManager.getLogger();
	private Map<CommandName, Command> commands = new EnumMap<>(CommandName.class);

	private CommandProvider() {
		commands.put(CommandName.AUTHENTICATION, new AuthenticationCommand());
		commands.put(CommandName.CHANGE_LOCALE, new ChangeLocaleCommand());
		commands.put(CommandName.VIEW_PROFILE, new ViewProfileCommand());
//		commands.put(CommandName.VIEW_ADMIN_PROFILE, new ViewAdminProfileCommand());
		commands.put(CommandName.NO_SUCH_COMMAND, new NoSuchCommand());
		commands.put(CommandName.REGISTRATION, new RegistrationCommand());
		commands.put(CommandName.REGISTRATION_INITIALIZE, new RegistrationInitializeCommand());
		commands.put(CommandName.LOGOUT, new LogoutCommand());
		commands.put(CommandName.EMAIL_CONFIRMATION, new EmailConfirmationCommand());
		commands.put(CommandName.SEND_MESSAGE, new SendMessageCommand());
		commands.put(CommandName.FILMS_AVAILABLE, new FindAvailableFilmsCommand());
	}

	public static CommandProvider getInstance() {
		return instance;
	}

	public Command getCommand(String commandName) {
		Command command = commands.get(CommandName.NO_SUCH_COMMAND);
		try {
			CommandName name = CommandName
							.valueOf(commandName
											.replace("-", "_")
											.toUpperCase());
			command = commands.get(name);
			log.debug("Command: ", name); // FIXME: 20.01.2020
		} catch (IllegalArgumentException e) {
			log.error("No such command: {}", commandName, e);
		}
		return command;
	}
}
