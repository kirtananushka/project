package by.tananushka.project.command.impl.order;

import by.tananushka.project.bean.Show;
import by.tananushka.project.bean.UserRole;
import by.tananushka.project.command.Command;
import by.tananushka.project.command.CommandException;
import by.tananushka.project.controller.PageName;
import by.tananushka.project.controller.ParamName;
import by.tananushka.project.controller.Router;
import by.tananushka.project.controller.SessionContent;
import by.tananushka.project.service.ServiceException;
import by.tananushka.project.service.ServiceProvider;
import by.tananushka.project.service.ShowService;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * The type Prepare payment command.
 */
public class PreparePaymentCommand implements Command {

	private ShowService showService = ServiceProvider.getInstance().getShowService();

	@Override
	public Router execute(SessionContent content) throws CommandException {
		Router router = new Router();
		router.setRoute(Router.RouteType.FORWARD);
		router.setPageToGo(PageName.ACCESS_DENIED_PAGE);
		String role = (String) content.getSessionAttribute(ParamName.PARAM_ROLE);
		if (role != null && role.equals(UserRole.CLIENT.toString())) {
			router.setPageToGo(PageName.PAY_FOR_TICKETS_PAGE);
			content.assignSessionAttribute(ParamName.PARAM_CURRENT_PAGE, PageName.PAY_FOR_TICKETS_PAGE);
			content.assignSessionAttribute(ParamName.PARAM_ERR_BUYING_TICKETS_MESSAGE, null);
			String showId = content.getRequestParameter(ParamName.PARAM_SHOW_ID);
			String strTicketsNumber = content.getRequestParameter(ParamName.PARAM_TICKETS_NUMBER);
			int ticketsNumber = 1;
			if (strTicketsNumber != null) {
				ticketsNumber = Integer.parseInt(strTicketsNumber);
			}
			try {
				Optional<Show> showOptional = showService.findShowById(showId);
				if (showOptional.isPresent()) {
					Show show = showOptional.get();
					content.assignSessionAttribute(ParamName.PARAM_SHOW_OBJ, show);
					content.assignSessionAttribute(ParamName.PARAM_TICKETS_NUMBER, ticketsNumber);
					content.assignSessionAttribute(ParamName.PARAM_COST,
									show.getCost().multiply(BigDecimal.valueOf(ticketsNumber)));
					if (content.getSessionAttribute(ParamName.PARAM_PAGE_TO_RETURN) == null) {
						content.assignSessionAttribute(ParamName.PARAM_PAGE_TO_RETURN,
										PageName.MAIN_PAGE);
					}
				}
			} catch (ServiceException e) {
				throw new CommandException("Exception while preparing tickets buying.", e);
			}
		}
		return router;
	}
}
