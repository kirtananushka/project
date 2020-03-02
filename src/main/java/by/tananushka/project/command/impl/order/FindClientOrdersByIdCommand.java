package by.tananushka.project.command.impl.order;

import by.tananushka.project.bean.TicketOrder;
import by.tananushka.project.bean.UserRole;
import by.tananushka.project.command.Command;
import by.tananushka.project.command.CommandException;
import by.tananushka.project.controller.PageName;
import by.tananushka.project.controller.ParamName;
import by.tananushka.project.controller.Router;
import by.tananushka.project.controller.SessionContent;
import by.tananushka.project.service.OrderService;
import by.tananushka.project.service.ServiceException;
import by.tananushka.project.service.ServiceProvider;
import by.tananushka.project.util.PagesCalculator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The type Find client orders by id command.
 */
public class FindClientOrdersByIdCommand implements Command {

	private static final String FULL_URL =
					"controller?command=single_client_orders&id=%d&page=";
	private static Logger log = LogManager.getLogger();
	private OrderService orderService = ServiceProvider.getInstance().getOrderService();
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
			router.setPageToGo(PageName.ALL_ORDERS_PAGE);
			String strClientId = content.getRequestParameter(ParamName.PARAM_CLIENT_ID);
			try {
				List<TicketOrder> ordersList = orderService.findOrdersByClientId(content, strClientId);
				String strPageNumber = content.getRequestParameter(ParamName.PARAM_PAGE);
				int pageNumber = 1;
				try {
					pageNumber = Integer.parseInt(strPageNumber);
				} catch (NumberFormatException e) {
					log.info("Invalid page number: {}.", strPageNumber);
				}
				int ordersFrom = pagesCalculator.calculateItemsFrom(pageNumber);
				int ordersNumber = ordersList.size();
				int totalPages = pagesCalculator.calculateTotalPages(ordersNumber);
				ordersList = ordersList
								.stream()
								.skip(ordersFrom)
								.limit(pagesCalculator.getItemsPerPage())
								.collect(Collectors.toList());
				int clientId = (int) content.getSessionAttribute(ParamName.PARAM_CLIENT_ID_FOR_ORDER);
				String fullUrl = String.format(FULL_URL, clientId);
				content.assignRequestAttribute(ParamName.PARAM_ORDERS_LIST, ordersList);
				content.assignRequestAttribute(ParamName.PARAM_TOTAL_PAGES, totalPages);
				content.assignRequestAttribute(ParamName.PARAM_PAGE_NUMBER, pageNumber);
				content.assignRequestAttribute(ParamName.PARAM_PAGE_URL, fullUrl);
				content.assignSessionAttribute(ParamName.PARAM_PAGE_TO_RETURN, fullUrl + pageNumber);
			} catch (ServiceException e) {
				throw new CommandException("Exception while finding client orders.", e);
			}
		}
		return router;
	}
}
