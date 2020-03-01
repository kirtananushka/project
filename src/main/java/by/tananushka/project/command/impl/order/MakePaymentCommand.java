package by.tananushka.project.command.impl.order;

import by.tananushka.project.bean.TicketOrder;
import by.tananushka.project.bean.UserRole;
import by.tananushka.project.command.Command;
import by.tananushka.project.command.CommandException;
import by.tananushka.project.command.ErrorMessageKey;
import by.tananushka.project.controller.PageName;
import by.tananushka.project.controller.ParamName;
import by.tananushka.project.controller.Router;
import by.tananushka.project.controller.SessionContent;
import by.tananushka.project.service.OrderService;
import by.tananushka.project.service.ServiceException;
import by.tananushka.project.service.ServiceProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

/**
 * The type Make payment command.
 */
public class MakePaymentCommand implements Command {

	private static Logger log = LogManager.getLogger();
	private OrderService orderService = ServiceProvider.getInstance().getOrderService();

	@Override
	public Router execute(SessionContent content) throws CommandException {
		Router router = new Router();
		router.setRoute(Router.RouteType.FORWARD);
		String pageToGo = null;
		router.setPageToGo(PageName.ACCESS_DENIED_PAGE);
		String role = (String) content.getSessionAttribute(ParamName.PARAM_ROLE);
		if (role != null && role.equals(UserRole.CLIENT.toString())) {
			router.setRoute(Router.RouteType.REDIRECT);
			pageToGo = PageName.PAY_FOR_TICKETS_PAGE;
			Map<String, String> errorsMap = new LinkedHashMap<>();
			try {
				Optional<TicketOrder> orderOptional = orderService.createOrder(content);
				if (orderOptional.isPresent()) {
					router.setRoute(Router.RouteType.REDIRECT);
					pageToGo = PageName.PAYMENT_SUCCESSFUL_PAGE;
					content.assignSessionAttribute(ParamName.PARAM_CURRENT_PAGE, pageToGo);
				} else {
					pageToGo = PageName.PAY_FOR_TICKETS_PAGE;
					errorsMap.put(ErrorMessageKey.ORDER_CREATION_FAILED, "");
				}
			} catch (ServiceException e) {
				log.error("Exception while order creation.", e);
				Map<String, String> errorsMapFromContent =
								(Map<String, String>) content
												.getSessionAttribute(ParamName.PARAM_ERR_CREATE_ORDER_MESSAGE);
				if (errorsMapFromContent != null) {
					errorsMap = errorsMapFromContent;
				}
				errorsMap.put(ErrorMessageKey.ORDER_CREATION_FAILED, "");
			} catch (Exception e) {
				throw new CommandException("Exception while order creation.", e);
			}
			content.assignSessionAttribute(ParamName.PARAM_ERR_CREATE_ORDER_MESSAGE, errorsMap);
			router.setPageToGo(pageToGo);
		}
		return router;
	}
}
