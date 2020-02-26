package by.tananushka.project.command.impl;

import by.tananushka.project.bean.User;
import by.tananushka.project.bean.UserRole;
import by.tananushka.project.command.Command;
import by.tananushka.project.controller.PageName;
import by.tananushka.project.controller.ParamName;
import by.tananushka.project.controller.Router;
import by.tananushka.project.controller.SessionContent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ViewProfileCommand implements Command {

	private static Logger log = LogManager.getLogger();

	@Override
	public Router execute(SessionContent content) {
		User user = (User) content.getSessionAttribute(ParamName.PARAM_USER);
		String pageToGo = PageName.AUTHENTICATION_PAGE;
		if (user != null) {
			if (user.getRole().equals(UserRole.ADMIN)) {
				pageToGo = PageName.MAIN_PAGE;
			}
		}
		Router router = new Router();
		router.setRoute(Router.RouteType.FORWARD);
		router.setPageToGo(pageToGo);
		return router;
	}
}
