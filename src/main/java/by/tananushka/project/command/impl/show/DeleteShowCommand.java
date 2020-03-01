package by.tananushka.project.command.impl.show;

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

import java.util.ArrayList;
import java.util.List;

/**
 * The type Delete show command.
 */
public class DeleteShowCommand implements Command {

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
			content.assignSessionAttribute(ParamName.PARAM_CURRENT_PAGE,
							PageName.DELETION_SUCCESSFUL_PAGE);
			content.assignSessionAttribute(ParamName.PARAM_ERR_DELETE_SHOW_MESSAGE, null);
			String showId = content.getRequestParameter(ParamName.PARAM_SHOW_ID);
			try {
				if (showService.deleteShow(showId)) {
					content.assignSessionAttribute(ParamName.PARAM_SHOW_OBJ, null);
					if (content.getSessionAttribute(ParamName.PARAM_PAGE_TO_RETURN) == null) {
						content.assignSessionAttribute(ParamName.PARAM_PAGE_TO_RETURN,
										PageName.MAIN_PAGE);
					}
					router.setRoute(Router.RouteType.REDIRECT);
					router.setPageToGo(PageName.DELETION_SUCCESSFUL_PAGE);
				} else {
					router.setPageToGo(PageName.DELETE_SHOW_PAGE);
					List<String> errorsList = new ArrayList<>();
					errorsList.add(ErrorMessageKey.DELETION_FAILED);
					content.assignSessionAttribute(ParamName.PARAM_ERR_DELETE_SHOW_MESSAGE, errorsList);
				}
			} catch (ServiceException e) {
				throw new CommandException("Exception while show deletion.", e);
			}
		}
		return router;
	}
}
