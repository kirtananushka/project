package by.tananushka.project.command.impl.common;

import by.tananushka.project.command.Command;
import by.tananushka.project.command.CommandException;
import by.tananushka.project.controller.JspPageName;
import by.tananushka.project.controller.ParamName;
import by.tananushka.project.controller.Router;
import by.tananushka.project.controller.SessionContent;

public class RegistrationInitializeCommand implements Command {

	@Override
	public Router execute(SessionContent content) throws CommandException {
		content.assignSessionAttribute(ParamName.PARAM_ERR_REG_MESSAGE, null);
		String pageToGo = JspPageName.REGISTRATION_PAGE;
		content.assignSessionAttribute(ParamName.PARAM_CURRENT_PAGE, pageToGo);
		content.assignSessionAttribute("user", null);
		Router router = new Router();
		router.setPageToGo(pageToGo);
		router.setRoute(Router.RouteType.FORWARD);
		return router;
	}
}
