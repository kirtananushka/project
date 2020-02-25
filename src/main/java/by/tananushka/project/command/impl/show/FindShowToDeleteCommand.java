package by.tananushka.project.command.impl.show;

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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class FindShowToDeleteCommand implements Command {

	private static Logger log = LogManager.getLogger();
	private ShowService showService = ServiceProvider.getInstance().getShowService();

	@Override
	public Router execute(SessionContent content) throws CommandException {
		Router router = new Router();
		router.setRoute(Router.RouteType.FORWARD);
		router.setPageToGo(PageName.ACCESS_DENIED_PAGE);
		String role = (String) content.getSessionAttribute(ParamName.PARAM_ROLE);
		if (role != null &&
						(role.equals(UserRole.MANAGER.toString()) ||
										role.equals(UserRole.ADMIN.toString()))) {
			router.setPageToGo(PageName.DELETE_SHOW_PAGE);
			content.assignSessionAttribute(ParamName.PARAM_CURRENT_PAGE, PageName.DELETE_SHOW_PAGE);
			content.assignSessionAttribute(ParamName.PARAM_ERR_DELETE_SHOW_MESSAGE, null);
			String showId = content.getRequestParameter(ParamName.PARAM_SHOW_ID);
			try {
				Optional<Show> showOptional = showService.findShowById(showId);
				if (showOptional.isPresent()) {
					Show show = showOptional.get();
					content.assignSessionAttribute(ParamName.PARAM_SHOW_OBJ, show);
					if (content.getSessionAttribute(ParamName.PARAM_PAGE_TO_RETURN) == null) {
						content.assignSessionAttribute(ParamName.PARAM_PAGE_TO_RETURN,
										PageName.MAIN_PAGE);
					}
				}
			} catch (ServiceException e) {
				throw new CommandException("Exception while finding show by id.", e);
			}
		}
		return router;
	}
}
