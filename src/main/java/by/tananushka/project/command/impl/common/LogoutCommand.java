package by.tananushka.project.command.impl.common;

import by.tananushka.project.command.Command;
import by.tananushka.project.command.CommandException;
import by.tananushka.project.controller.PageName;
import by.tananushka.project.controller.ParamName;
import by.tananushka.project.controller.Router;
import by.tananushka.project.controller.SessionContent;

/**
 * The type Logout command.
 */
public class LogoutCommand implements Command {

	@Override
	public Router execute(SessionContent content) throws CommandException {
		content.assignSessionAttribute(ParamName.PARAM_USER_AUTHORIZATED, null);
		content.assignSessionAttribute(ParamName.PARAM_ROLE, null);
		Router router = new Router();
		router.setPageToGo(PageName.MAIN_PAGE);
		router.setRoute(Router.RouteType.FORWARD);
		return router;
	}
}
