package by.tananushka.project.command.impl.user;

import by.tananushka.project.bean.User;
import by.tananushka.project.command.Command;
import by.tananushka.project.command.CommandException;
import by.tananushka.project.command.ErrorMessageKey;
import by.tananushka.project.controller.PageName;
import by.tananushka.project.controller.ParamName;
import by.tananushka.project.controller.Router;
import by.tananushka.project.controller.SessionContent;
import by.tananushka.project.service.ServiceException;
import by.tananushka.project.service.ServiceProvider;
import by.tananushka.project.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The type Update profile command.
 */
public class UpdateProfileCommand implements Command {

	private static Logger log = LogManager.getLogger();
	private UserService userService = ServiceProvider.getInstance().getUserService();

	@Override
	public Router execute(SessionContent content) throws CommandException {
		Router router = new Router();
		router.setRoute(Router.RouteType.FORWARD);
		String pageToGo = PageName.ACCESS_DENIED_PAGE;
		String role = (String) content.getSessionAttribute(ParamName.PARAM_ROLE);
		if (role != null) {
			router.setRoute(Router.RouteType.REDIRECT);
			pageToGo = PageName.VIEW_PROFILE_UPDATED_PAGE;
			content.assignSessionAttribute(ParamName.PARAM_CURRENT_PAGE, pageToGo);
			content.assignSessionAttribute(ParamName.PARAM_ERR_UPDATE_USER_MESSAGE, null);
			try {
				Optional<User> userOptional = userService.updateUser(content);
				if (userOptional.isPresent()) {
					content.assignSessionAttribute(ParamName.PARAM_USER_UPD, userOptional.get());
				} else {
					pageToGo = PageName.EDIT_PROFILE_PAGE;
					List<String> errorsList =
									(List<String>) content
													.getSessionAttribute(ParamName.PARAM_ERR_UPDATE_USER_MESSAGE);
					if (errorsList == null) {
						errorsList = new ArrayList<>();
					}
					errorsList.add(ErrorMessageKey.UPDATE_FAILED);
					content.assignSessionAttribute(ParamName.PARAM_ERR_UPDATE_USER_MESSAGE, errorsList);
				}
				if (content.getSessionAttribute(ParamName.PARAM_PAGE_TO_RETURN) == null) {
					content.assignSessionAttribute(ParamName.PARAM_PAGE_TO_RETURN,
									PageName.MAIN_PAGE);
				}
			} catch (ServiceException e) {
				log.error("Exception while user updating.", e);
			} catch (Exception e) {
				throw new CommandException("Exception while user updating.", e);
			}
		}
		router.setPageToGo(pageToGo);
		return router;
	}
}
