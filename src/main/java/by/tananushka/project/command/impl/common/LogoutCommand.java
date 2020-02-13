package by.tananushka.project.command.impl.common;

import by.tananushka.project.command.Command;
import by.tananushka.project.command.CommandException;
import by.tananushka.project.controller.JspPageName;
import by.tananushka.project.controller.ParamName;
import by.tananushka.project.controller.Router;
import by.tananushka.project.controller.SessionContent;

public class LogoutCommand implements Command {

	@Override
	public Router execute(SessionContent content) throws CommandException {
		content.assignSessionAttribute(ParamName.PARAM_USER, null);
		Router router = new Router();
		router.setPageToGo(JspPageName.MAIN_PAGE);
		router.setRoute(Router.RouteType.FORWARD);
		return router;
	}
}
