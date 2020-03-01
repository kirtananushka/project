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
import by.tananushka.project.util.PagesCalculator;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The type Find active clients command.
 */
public class FindActiveClientsCommand implements Command {

	private static final String FULL_URL =
					"controller?command=find_active_clients&page=";
	private ClientService clientService = ServiceProvider.getInstance().getClientService();
	private PagesCalculator pagesCalculator = PagesCalculator.getInstance();

	@Override
	public Router execute(SessionContent content) throws CommandException {
		Router router = new Router();
		router.setRoute(Router.RouteType.FORWARD);
		router.setPageToGo(PageName.ACCESS_DENIED_PAGE);
		String role = (String) content.getSessionAttribute(ParamName.PARAM_ROLE);
		if (role != null &&
						(role.equals(UserRole.MANAGER.toString()) ||
										role.equals(UserRole.ADMIN.toString()))) {
			router.setPageToGo(PageName.CLIENTS_PAGE);
			try {
				List<Client> clientsList = clientService.findActiveClients();
				int pageNumber = Integer.parseInt(content.getRequestParameter(ParamName.PARAM_PAGE));
				int clientsFrom = pagesCalculator.calculateItemsFrom(pageNumber);
				int clientsNumber = clientsList.size();
				int totalPages = pagesCalculator.calculateTotalPages(clientsNumber);
				clientsList = clientsList
								.stream()
								.skip(clientsFrom)
								.limit(pagesCalculator.getItemsPerPage())
								.collect(Collectors.toList());
				content.assignRequestAttribute(ParamName.PARAM_CLIENTS_LIST, clientsList);
				content.assignRequestAttribute(ParamName.PARAM_TOTAL_PAGES, totalPages);
				content.assignRequestAttribute(ParamName.PARAM_PAGE_NUMBER, pageNumber);
				content.assignRequestAttribute(ParamName.PARAM_PAGE_URL, FULL_URL);
				content.assignSessionAttribute(ParamName.PARAM_PAGE_TO_RETURN, FULL_URL + pageNumber);
			} catch (ServiceException e) {
				throw new CommandException("Exception while finding active clients.", e);
			}
		}
		return router;
	}
}
