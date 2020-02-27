package by.tananushka.project.command.impl.client;

import by.tananushka.project.bean.Client;
import by.tananushka.project.bean.UserRole;
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

public class EditClientCommand implements Command {

	private static Logger log = LogManager.getLogger();
	private ClientService clientService = ServiceProvider.getInstance().getClientService();

	@Override
	public Router execute(SessionContent content) throws CommandException {
		Router router = new Router();
		router.setRoute(Router.RouteType.FORWARD);
		router.setPageToGo(PageName.ACCESS_DENIED_PAGE);
		String role = (String) content.getSessionAttribute(ParamName.PARAM_ROLE);
		if (role != null &&
						(role.equals(UserRole.MANAGER.toString()) ||
										role.equals(UserRole.ADMIN.toString()))) {
			router.setPageToGo(PageName.EDIT_CLIENT_PAGE);
			content.assignSessionAttribute(ParamName.PARAM_CURRENT_PAGE, PageName.EDIT_CLIENT_PAGE);
			String clientId = content.getRequestParameter(ParamName.PARAM_CLIENT_ID);
			try {
				Optional<Client> clientOptional = clientService.findClientById(clientId);
				if (clientOptional.isPresent()) {
					Client client = clientOptional.get();
					content.assignSessionAttribute(ParamName.PARAM_CLIENT, client);
				}
				if (content.getSessionAttribute(ParamName.PARAM_PAGE_TO_RETURN) == null) {
					content.assignSessionAttribute(ParamName.PARAM_PAGE_TO_RETURN,
									PageName.MAIN_PAGE);
				}
			} catch (ServiceException e) {
				throw new CommandException("Exception while finding client by id.", e);
			}
		}
		return router;
	}
}
