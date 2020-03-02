package by.tananushka.project.command.impl.user;

import by.tananushka.project.command.Command;
import by.tananushka.project.command.CommandException;
import by.tananushka.project.controller.PageName;
import by.tananushka.project.controller.ParamName;
import by.tananushka.project.controller.Router;
import by.tananushka.project.controller.SessionContent;

/**
 * The type Prepare change password command.
 */
public class PrepareChangePasswordCommand implements Command {

	@Override
	public Router execute(SessionContent content) throws CommandException {
		Router router = new Router();
		router.setRoute(Router.RouteType.FORWARD);
		String pageToGo = PageName.ACCESS_DENIED_PAGE;
		String role = (String) content.getSessionAttribute(ParamName.PARAM_ROLE);
		if (role != null) {
			pageToGo = PageName.CHANGE_PASS_PAGE;
			content.assignSessionAttribute(ParamName.PARAM_CURRENT_PAGE, pageToGo);
			content.assignSessionAttribute(ParamName.PARAM_ERR_REG_MESSAGE, null);
		}
		router.setPageToGo(pageToGo);
		return router;
	}
}
