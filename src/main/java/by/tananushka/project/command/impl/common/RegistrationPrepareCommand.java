package by.tananushka.project.command.impl.common;

import by.tananushka.project.command.Command;
import by.tananushka.project.command.CommandException;
import by.tananushka.project.controller.PageName;
import by.tananushka.project.controller.ParamName;
import by.tananushka.project.controller.Router;
import by.tananushka.project.controller.SessionContent;

/**
 * The type Registration prepare command.
 */
public class RegistrationPrepareCommand implements Command {

	@Override
	public Router execute(SessionContent content) throws CommandException {
		content.assignSessionAttribute(ParamName.PARAM_ERR_REG_MESSAGE, null);
		String pageToGo = PageName.REGISTRATION_PAGE;
		content.assignSessionAttribute(ParamName.PARAM_CURRENT_PAGE, pageToGo);
		content.assignSessionAttribute("user", null);
		Router router = new Router();
		router.setPageToGo(pageToGo);
		router.setRoute(Router.RouteType.FORWARD);
		return router;
	}
}
