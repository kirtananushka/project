package by.tananushka.project.command.impl.common;

import by.tananushka.project.command.Command;
import by.tananushka.project.command.CommandException;
import by.tananushka.project.controller.ParamName;
import by.tananushka.project.controller.Router;
import by.tananushka.project.controller.SessionContent;

public class NoSuchCommand implements Command {

	@Override
	public Router execute(SessionContent content) throws CommandException {
		String unknownCommand = content.getRequestParameter(ParamName.PARAM_COMMAND);
		throw new CommandException("Unknown command: " + unknownCommand);
//		return null; fixme
	}
}
