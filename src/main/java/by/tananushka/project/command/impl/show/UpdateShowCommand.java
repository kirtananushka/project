package by.tananushka.project.command.impl.show;

import by.tananushka.project.bean.Show;
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

public class UpdateShowCommand implements Command {

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
			router.setRoute(Router.RouteType.REDIRECT);
			String pageToGo = PageName.EDIT_SHOW_PAGE;
			content.assignSessionAttribute(ParamName.PARAM_CURRENT_PAGE, pageToGo);
			try {
				Show show = showService.updateShow(content)
				                       .orElse((Show) content
								                       .getSessionAttribute(ParamName.PARAM_SHOW_OBJ));
				content.assignSessionAttribute(ParamName.PARAM_SHOW_OBJ, show);
				router.setRoute(Router.RouteType.REDIRECT);
				pageToGo = PageName.VIEW_SHOW_PAGE;
				content.assignSessionAttribute(ParamName.PARAM_CURRENT_PAGE, pageToGo);
			} catch (ServiceException e) {
				log.error("Exception while updating show.", e);
				Map<String, String> errorsMap =
								(Map<String, String>) content
												.getSessionAttribute(ParamName.PARAM_ERR_UPDATE_SHOW_MESSAGE);
				if (errorsMap == null) {
					errorsMap = new LinkedHashMap<>();
				}
				errorsMap.put(ErrorMessageKey.UPDATE_FAILED, "");
				content.assignSessionAttribute(ParamName.PARAM_ERR_UPDATE_SHOW_MESSAGE, errorsMap);
			} catch (Exception e) {
				throw new CommandException("Exception while updating show.", e);
			}
			router.setPageToGo(pageToGo);
		}
		return router;
	}
}
