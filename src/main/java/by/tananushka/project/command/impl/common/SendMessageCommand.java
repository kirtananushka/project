package by.tananushka.project.command.impl.common;

import by.tananushka.project.command.Command;
import by.tananushka.project.controller.PageName;
import by.tananushka.project.controller.ParamName;
import by.tananushka.project.controller.Router;
import by.tananushka.project.controller.SessionContent;
import by.tananushka.project.service.ServiceProvider;
import by.tananushka.project.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SendMessageCommand implements Command {

	private static Logger log = LogManager.getLogger();
	private UserService userService = ServiceProvider.getInstance().getUserService();

	@Override
	public Router execute(SessionContent content) {
		String pageToGo = PageName.SEND_MESSAGE_PAGE;
		if (userService.sendMessage(content)) {
			pageToGo = PageName.MESSAGE_SENT_PAGE;
			content.assignSessionAttribute(ParamName.PARAM_ERR_SEND_MESSAGE, null);
			content.assignSessionAttribute(ParamName.PARAM_NAME_DEFAULT, null);
			content.assignSessionAttribute(ParamName.PARAM_SURNAME_DEFAULT, null);
			content.assignSessionAttribute(ParamName.PARAM_EMAIL_DEFAULT, null);
			content.assignSessionAttribute(ParamName.PARAM_PHONE_DEFAULT, null);
			content.assignSessionAttribute(ParamName.PARAM_MESSAGE_DEFAULT, null);
		}
		Router router = new Router();
		router.setPageToGo(pageToGo);
		router.setRoute(Router.RouteType.FORWARD);
		content.assignSessionAttribute(ParamName.PARAM_CURRENT_PAGE, pageToGo);
		return router;
	}
}
