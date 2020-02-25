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

public class PrepareCinemaUpdateCommand implements Command {

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
			Map<String, String> errorsMap = new LinkedHashMap<>();
			String pageToGo;
			try {
				Map<Integer, String> cinemasMap = showService.findActiveCinemas();
				int cinemaId = Integer.parseInt(content.getRequestParameter(ParamName.PARAM_CINEMA));
				content.assignRequestAttribute(ParamName.PARAM_CINEMA_ID, cinemaId);
				String cinemaName = cinemasMap.get(cinemaId);
				content.assignRequestAttribute(ParamName.PARAM_CINEMA, cinemaName);
				pageToGo = PageName.UPDATE_CINEMA_PAGE;
				router.setPageToGo(pageToGo);
			} catch (ServiceException e) {
				pageToGo = PageName.EDIT_CINEMA_PAGE;
				router.setPageToGo(pageToGo);
				Map<String, String> errorsMapFromContent =
								(Map<String, String>) content
												.getSessionAttribute(ParamName.PARAM_ERR_UPDATE_CINEMA_MESSAGE);
				if (errorsMapFromContent != null) {
					errorsMap = errorsMapFromContent;
				}
				errorsMap.put(ErrorMessageKey.CINEMA_UPDATING_FAILED, "");
				content.assignSessionAttribute(ParamName.PARAM_ERR_UPDATE_CINEMA_MESSAGE, errorsMap);
			}
		}
		return router;
	}
}
