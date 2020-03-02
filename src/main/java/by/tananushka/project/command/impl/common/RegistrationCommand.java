package by.tananushka.project.command.impl.common;

import by.tananushka.project.bean.Client;
import by.tananushka.project.command.Command;
import by.tananushka.project.command.CommandException;
import by.tananushka.project.controller.PageName;
import by.tananushka.project.controller.ParamName;
import by.tananushka.project.controller.Router;
import by.tananushka.project.controller.SessionContent;
import by.tananushka.project.service.ClientService;
import by.tananushka.project.service.ServiceException;
import by.tananushka.project.service.ServiceProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

/**
 * The type Registration command.
 */
public class RegistrationCommand implements Command {

	private static Logger log = LogManager.getLogger();
	private ClientService clientService = ServiceProvider.getInstance().getClientService();

	@Override
	public Router execute(SessionContent content) throws CommandException {
		Router router = new Router();
		router.setRoute(Router.RouteType.REDIRECT);
		String pageToGo = PageName.REGISTRATION_PAGE;
		content.assignSessionAttribute(ParamName.PARAM_CURRENT_PAGE, pageToGo);
		try {
			Optional<Client> clientOptional = clientService.createClient(content);
			if (clientOptional.isPresent()) {
				content.assignSessionAttribute(ParamName.PARAM_NEW_USER, clientOptional.get());
				router.setRoute(Router.RouteType.REDIRECT);
				pageToGo = PageName.REGISTRATION_SUCCESSFUL_PAGE;
				content.assignSessionAttribute(ParamName.PARAM_ERR_REG_MESSAGE, null);
				content.assignSessionAttribute(ParamName.PARAM_LOGIN_DEFAULT, null);
				content.assignSessionAttribute(ParamName.PARAM_NAME_DEFAULT, null);
				content.assignSessionAttribute(ParamName.PARAM_SURNAME_DEFAULT, null);
				content.assignSessionAttribute(ParamName.PARAM_EMAIL_DEFAULT, null);
				content.assignSessionAttribute(ParamName.PARAM_PHONE_DEFAULT, null);
				content.assignSessionAttribute(ParamName.PARAM_PASS_DEFAULT, null);
				content.assignSessionAttribute(ParamName.PARAM_PASS_REPEATED_DEFAULT, null);
				if (content.getSessionAttribute(ParamName.PARAM_PAGE_TO_RETURN) == null) {
					content.assignSessionAttribute(ParamName.PARAM_PAGE_TO_RETURN,
									PageName.MAIN_PAGE);
				}
			}
		} catch (ServiceException e) {
			log.error("Exception while registration.", e);
		} catch (Exception e) {
			log.error("Exception while registration.", e);
			throw new CommandException("Exception while registration.", e);
		}
		router.setPageToGo(pageToGo);
		return router;
	}
}
