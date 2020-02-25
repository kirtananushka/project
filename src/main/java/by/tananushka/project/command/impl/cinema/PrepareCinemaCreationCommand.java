package by.tananushka.project.command.impl.cinema;

import by.tananushka.project.bean.UserRole;
import by.tananushka.project.command.Command;
import by.tananushka.project.command.CommandException;
import by.tananushka.project.controller.PageName;
import by.tananushka.project.controller.ParamName;
import by.tananushka.project.controller.Router;
import by.tananushka.project.controller.SessionContent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PrepareCinemaCreationCommand implements Command {

	private static Logger log = LogManager.getLogger();

	@Override
	public Router execute(SessionContent content) throws CommandException {
		Router router = new Router();
		router.setRoute(Router.RouteType.FORWARD);
		router.setPageToGo(PageName.ACCESS_DENIED_PAGE);
		String role = (String) content.getSessionAttribute(ParamName.PARAM_ROLE);
		if (role != null &&
						(role.equals(UserRole.MANAGER.toString()) ||
										role.equals(UserRole.ADMIN.toString()))) {
			String pageToGo = PageName.CREATE_CINEMA_PAGE;
			router.setPageToGo(pageToGo);
			content.assignSessionAttribute(ParamName.PARAM_CURRENT_PAGE, pageToGo);
			content.assignSessionAttribute(ParamName.PARAM_ERR_CREATE_CINEMA_MESSAGE, null);
			if (content.getSessionAttribute(ParamName.PARAM_PAGE_TO_RETURN) == null) {
				content.assignSessionAttribute(ParamName.PARAM_PAGE_TO_RETURN,
								PageName.MAIN_PAGE);
			}
		}
		return router;
	}
}
