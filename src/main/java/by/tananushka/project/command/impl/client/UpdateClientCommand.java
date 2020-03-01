package by.tananushka.project.command.impl.client;

import by.tananushka.project.bean.Client;
import by.tananushka.project.bean.UserRole;
import by.tananushka.project.command.Command;
import by.tananushka.project.command.CommandException;
import by.tananushka.project.command.ErrorMessageKey;
import by.tananushka.project.controller.PageName;
import by.tananushka.project.controller.ParamName;
import by.tananushka.project.controller.Router;
import by.tananushka.project.controller.SessionContent;
import by.tananushka.project.service.ClientService;
import by.tananushka.project.service.ServiceException;
import by.tananushka.project.service.ServiceProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The type Update client command.
 */
public class UpdateClientCommand implements Command {

	private static Logger log = LogManager.getLogger();
	private ClientService clientService = ServiceProvider.getInstance().getClientService();

	@Override
	public Router execute(SessionContent content) throws CommandException {
		Router router = new Router();
		router.setRoute(Router.RouteType.FORWARD);
		String pageToGo = PageName.ACCESS_DENIED_PAGE;
		String role = (String) content.getSessionAttribute(ParamName.PARAM_ROLE);
		if (role != null &&
						(role.equals(UserRole.MANAGER.toString()) ||
										role.equals(UserRole.ADMIN.toString()))) {
			router.setRoute(Router.RouteType.REDIRECT);
			pageToGo = PageName.VIEW_CLIENT_UPDATED_PAGE;
			content.assignSessionAttribute(ParamName.PARAM_CURRENT_PAGE, pageToGo);
			content.assignSessionAttribute(ParamName.PARAM_ERR_UPDATE_CLIENT_MESSAGE, null);
			try {
				Optional<Client> clientOptional = clientService.updateClient(content);
				if (clientOptional.isPresent()) {
					content.assignSessionAttribute(ParamName.PARAM_CLIENT_UPD, clientOptional.get());
				} else {
					pageToGo = PageName.EDIT_CLIENT_PAGE;
					List<String> errorsList =
									(List<String>) content
													.getSessionAttribute(ParamName.PARAM_ERR_UPDATE_CLIENT_MESSAGE);
					if (errorsList == null) {
						errorsList = new ArrayList<>();
					}
					errorsList.add(ErrorMessageKey.UPDATE_FAILED);
					content.assignSessionAttribute(ParamName.PARAM_ERR_UPDATE_CLIENT_MESSAGE, errorsList);
				}
				if (content.getSessionAttribute(ParamName.PARAM_PAGE_TO_RETURN) == null) {
					content.assignSessionAttribute(ParamName.PARAM_PAGE_TO_RETURN,
									PageName.MAIN_PAGE);
				}
			} catch (ServiceException e) {
				log.error("Exception while client updating.", e);
			} catch (Exception e) {
				throw new CommandException("Exception while client updating.", e);
			}
		}
		router.setPageToGo(pageToGo);
		return router;
	}
}
