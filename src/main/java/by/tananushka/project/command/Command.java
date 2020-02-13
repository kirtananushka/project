package by.tananushka.project.command;

import by.tananushka.project.controller.Router;
import by.tananushka.project.controller.SessionContent;

public interface Command {

	Router execute(SessionContent content) throws CommandException;
}


