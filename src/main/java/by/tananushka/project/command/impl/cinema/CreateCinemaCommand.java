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
 * The type Create cinema command.
 */
public class CreateCinemaCommand implements Command {

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
				if (showService.createCinema(content)) {
					router.setRoute(Router.RouteType.REDIRECT);
					pageToGo = PageName.CREATION_SUCCESSFUL_PAGE;
				} else {
					pageToGo = PageName.CREATE_CINEMA_PAGE;
					Map<String, String> errorsMapFromContent =
									(Map<String, String>) content
													.getSessionAttribute(ParamName.PARAM_ERR_CREATE_CINEMA_MESSAGE);
					if (errorsMapFromContent != null) {
						errorsMap = errorsMapFromContent;
					}
					errorsMap.put(ErrorMessageKey.CINEMA_CREATION_FAILED, "");
					content.assignSessionAttribute(ParamName.PARAM_ERR_CREATE_CINEMA_MESSAGE, errorsMap);
				}
				router.setPageToGo(pageToGo);
				content.assignSessionAttribute(ParamName.PARAM_CURRENT_PAGE, pageToGo);
			} catch (ServiceException e) {
				log.error("Exception while cinema creation.");
				throw new CommandException("Exception while cinema creation.", e);
			}
		}
		return router;
	}
}
