package by.tananushka.project.command.impl.user;

import by.tananushka.project.bean.User;
import by.tananushka.project.command.Command;
import by.tananushka.project.command.CommandException;
import by.tananushka.project.controller.PageName;
import by.tananushka.project.controller.ParamName;
import by.tananushka.project.controller.Router;
import by.tananushka.project.controller.SessionContent;
import by.tananushka.project.service.ServiceException;
import by.tananushka.project.service.ServiceProvider;
import by.tananushka.project.service.UserService;

import java.util.Optional;

/**
 * The type Edit profile command.
 */
public class EditProfileCommand implements Command {

	private UserService userService = ServiceProvider.getInstance().getUserService();

	@Override
	public Router execute(SessionContent content) throws CommandException {
		Router router = new Router();
		router.setRoute(Router.RouteType.FORWARD);
		router.setPageToGo(PageName.ACCESS_DENIED_PAGE);
		String role = (String) content.getSessionAttribute(ParamName.PARAM_ROLE);
		if (role != null) {
			router.setPageToGo(PageName.EDIT_PROFILE_PAGE);
			content.assignSessionAttribute(ParamName.PARAM_CURRENT_PAGE, PageName.EDIT_PROFILE_PAGE);
			String userId = content.getRequestParameter(ParamName.PARAM_USER_ID);
			try {
				Optional<User> userOptional = userService.findUserById(userId);
				if (userOptional.isPresent()) {
					User user = userOptional.get();
					content.assignSessionAttribute(ParamName.PARAM_USER, user);
				}
				if (content.getSessionAttribute(ParamName.PARAM_PAGE_TO_RETURN) == null) {
					content.assignSessionAttribute(ParamName.PARAM_PAGE_TO_RETURN,
									PageName.MAIN_PAGE);
				}
			} catch (ServiceException e) {
				throw new CommandException("Exception while finding user by id.", e);
			}
		}
		return router;
	}
}
