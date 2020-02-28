package by.tananushka.project.command.impl.common;

import by.tananushka.project.command.Command;
import by.tananushka.project.controller.PageName;
import by.tananushka.project.controller.ParamName;
import by.tananushka.project.controller.Router;
import by.tananushka.project.controller.SessionContent;
import by.tananushka.project.service.ServiceException;
import by.tananushka.project.service.ServiceProvider;
import by.tananushka.project.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SendPasswordCommand implements Command {

	private static Logger log = LogManager.getLogger();
	private UserService userService = ServiceProvider.getInstance().getUserService();

	@Override
	public Router execute(SessionContent content) {
		Router router = new Router();
		String pageToGo = null;
		try {
			boolean isPasswordSent = userService.sendPassword(content);
			if (isPasswordSent) {
				router.setRoute(Router.RouteType.REDIRECT);
				pageToGo = PageName.PASSWORD_CHANGED_PAGE;
				content.assignSessionAttribute(ParamName.PARAM_ERR_SEND_NEW_PASSWORD_MESSAGE, null);
			} else {
				router.setRoute(Router.RouteType.FORWARD);
				pageToGo = PageName.PASSWORD_FORGOTTEN_PAGE;
			}
		} catch (ServiceException e) {
			log.error("Service exception", e);
		}
		router.setPageToGo(pageToGo);
		return router;
	}
}
