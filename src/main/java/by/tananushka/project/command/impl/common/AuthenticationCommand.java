package by.tananushka.project.command.impl.common;

import by.tananushka.project.bean.User;
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

import java.util.Optional;

public class AuthenticationCommand implements Command {

	private static Logger log = LogManager.getLogger();
	private UserService userService = ServiceProvider.getInstance().getUserService();

	@Override
	public Router execute(SessionContent content) {
		Router router = new Router();
		content.assignSessionAttribute(ParamName.PARAM_USER_AUTHORIZATED, null);
		String login;
		String password;
		login = content.getRequestParameter(ParamName.PARAM_LOGIN);
		password = content.getRequestParameter(ParamName.PARAM_PASS);
		String pageToGo = null;
		try {
			Optional<User> userOptional = userService.authentication(login, password);
			if (userOptional.isPresent()) {
				router.setRoute(Router.RouteType.REDIRECT);
				User user = userOptional.get();
				content.assignSessionAttribute(ParamName.PARAM_USER_AUTHORIZATED, user);
				content.assignSessionAttribute(ParamName.PARAM_ROLE, user.getRole().toString());
				pageToGo = PageName.MAIN_PAGE;
				content.assignSessionAttribute(ParamName.PARAM_ERR_AUTH_MESSAGE, null);
			} else {
				router.setRoute(Router.RouteType.REDIRECT);
				content.assignSessionAttribute(ParamName.PARAM_ERR_AUTH_MESSAGE,
								ErrorMessageKey.WRONG_LOGIN_OR_PASS);
				pageToGo = PageName.AUTHENTICATION_PAGE;
			}
		} catch (ServiceException e) {
			log.error("Service exception", e);
		}
		router.setPageToGo(pageToGo);
		return router;
	}
}
