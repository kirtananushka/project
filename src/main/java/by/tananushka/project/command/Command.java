package by.tananushka.project.command;

import by.tananushka.project.controller.Router;
import by.tananushka.project.controller.SessionContent;

/**
 * The interface Command.
 */
public interface Command {

	/**
	 * Execute router.
	 *
	 * @param content the content
	 * @return the router
	 * @throws CommandException the command exception
	 */
	Router execute(SessionContent content) throws CommandException;
}


