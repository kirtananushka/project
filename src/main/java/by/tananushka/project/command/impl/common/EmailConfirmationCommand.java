package by.tananushka.project.command.impl.common;

import by.tananushka.project.command.Command;
import by.tananushka.project.command.ErrorMessageKey;
import by.tananushka.project.controller.PageName;
import by.tananushka.project.controller.ParamName;
import by.tananushka.project.controller.Router;
import by.tananushka.project.controller.SessionContent;
import by.tananushka.project.service.ServiceException;
import by.tananushka.project.service.ServiceProvider;
import by.tananushka.project.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EmailConfirmationCommand implements Command {

	private static Logger log = LogManager.getLogger();
	private UserService userService = ServiceProvider.getInstance().getUserService();

	@Override
	public Router execute(SessionContent content) {
		boolean isConfirmationSuccessful = false;
		int userId = 0;
		String pageToGo = null;
		try {
			userId = Integer.parseInt(content.getRequestParameter(ParamName.PARAM_USER_ID));
			isConfirmationSuccessful = userService.emailConfirmation(userId);
			if (isConfirmationSuccessful) {
				pageToGo = PageName.CONFIRMATION_SUCCESSFUL_PAGE;
				content.assignSessionAttribute(ParamName.PARAM_ERR_AUTH_MESSAGE,
								null);
				content.assignSessionAttribute(ParamName.PARAM_USER, null);
			} else {
				content.assignSessionAttribute(ParamName.PARAM_ERR_AUTH_MESSAGE,
								ErrorMessageKey.EMAIL_VERIFICATION_FAILED);
				content.assignSessionAttribute(ParamName.PARAM_USER, null);
				pageToGo = PageName.LOGIN_PAGE;
			}
		} catch (ServiceException e) {
			log.error("Service exception", e);
		}
		log.debug("Congirmation of user {}: {}", userId, isConfirmationSuccessful);
		Router router = new Router();
		router.setPageToGo(pageToGo);
		router.setRoute(Router.RouteType.FORWARD);
		return router;
	}
}
