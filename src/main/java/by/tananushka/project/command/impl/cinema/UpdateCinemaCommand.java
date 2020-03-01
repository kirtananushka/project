package by.tananushka.project.command.impl.cinema;

import by.tananushka.project.bean.UserRole;
import by.tananushka.project.command.Command;
import by.tananushka.project.command.CommandException;
import by.tananushka.project.command.ErrorMessageKey;
import by.tananushka.project.controller.PageName;
import by.tananushka.project.controller.ParamName;
import by.tananushka.project.controller.Router;
import by.tananushka.project.controller.SessionContent;
import by.tananushka.project.service.ServiceException;
import by.tananushka.project.service.ServiceProvider;
import by.tananushka.project.service.ShowService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * The type Update cinema command.
 */
public class UpdateCinemaCommand implements Command {

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
			try {
				Map<String, String> errorsMap = new LinkedHashMap<>();
				String pageToGo;
				if (showService.updateCinema(content)) {
					router.setRoute(Router.RouteType.REDIRECT);
					pageToGo = PageName.UPDATING_SUCCESSFUL_PAGE;
				} else {
					pageToGo = PageName.UPDATE_CINEMA_PAGE;
					Map<String, String> errorsMapFromContent =
									(Map<String, String>) content
													.getSessionAttribute(ParamName.PARAM_ERR_UPDATE_CINEMA_MESSAGE);
					if (errorsMapFromContent != null) {
						errorsMap = errorsMapFromContent;
					}
					errorsMap.put(ErrorMessageKey.CINEMA_UPDATING_FAILED, "");
					content.assignSessionAttribute(ParamName.PARAM_ERR_UPDATE_CINEMA_MESSAGE, errorsMap);
				}
				router.setPageToGo(pageToGo);
				content.assignSessionAttribute(ParamName.PARAM_CURRENT_PAGE, pageToGo);
			} catch (ServiceException e) {
				log.error("Exception while cinema updating.");
				throw new CommandException("Exception while cinema updating.", e);
			}
		}
		return router;
	}
}
