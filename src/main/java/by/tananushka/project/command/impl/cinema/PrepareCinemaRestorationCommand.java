package by.tananushka.project.command.impl.cinema;

import by.tananushka.project.bean.UserRole;
import by.tananushka.project.command.Command;
import by.tananushka.project.command.CommandException;
import by.tananushka.project.controller.PageName;
import by.tananushka.project.controller.ParamName;
import by.tananushka.project.controller.Router;
import by.tananushka.project.controller.SessionContent;
import by.tananushka.project.service.ServiceException;
import by.tananushka.project.service.ShowService;
import by.tananushka.project.service.impl.ShowServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

/**
 * The type Prepare cinema restoration command.
 */
public class PrepareCinemaRestorationCommand implements Command {

	private static Logger log = LogManager.getLogger();
	private ShowService showService = ShowServiceImpl.getInstance();

	@Override
	public Router execute(SessionContent content) throws CommandException {
		Router router = new Router();
		router.setRoute(Router.RouteType.FORWARD);
		router.setPageToGo(PageName.ACCESS_DENIED_PAGE);
		String role = (String) content.getSessionAttribute(ParamName.PARAM_ROLE);
		if (role != null &&
						(role.equals(UserRole.MANAGER.toString()) ||
										role.equals(UserRole.ADMIN.toString()))) {
			String pageToGo = PageName.RESTORE_CINEMA_PAGE;
			router.setPageToGo(pageToGo);
			content.assignSessionAttribute(ParamName.PARAM_CURRENT_PAGE, pageToGo);
			content.assignSessionAttribute(ParamName.PARAM_ERR_RESTORE_CINEMA_MESSAGE, null);
			if (content.getSessionAttribute(ParamName.PARAM_PAGE_TO_RETURN) == null) {
				content.assignSessionAttribute(ParamName.PARAM_PAGE_TO_RETURN,
								PageName.MAIN_PAGE);
			}
			Map<Integer, String> cinemasMap;
			try {
				cinemasMap = showService.findInactiveCinemas();
				content.assignSessionAttribute(ParamName.PARAM_CINEMAS_MAP, cinemasMap);
			} catch (ServiceException e) {
				log.error("Exception while preparing cinema restoration.");
				throw new CommandException("Exception while preparing cinema restoration.", e);
			}
		}
		return router;
	}
}
