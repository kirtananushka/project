package by.tananushka.project.command.impl.manager;

import by.tananushka.project.bean.Manager;
import by.tananushka.project.bean.UserRole;
import by.tananushka.project.command.Command;
import by.tananushka.project.command.CommandException;
import by.tananushka.project.controller.PageName;
import by.tananushka.project.controller.ParamName;
import by.tananushka.project.controller.Router;
import by.tananushka.project.controller.SessionContent;
import by.tananushka.project.service.ManagerService;
import by.tananushka.project.service.ServiceException;
import by.tananushka.project.service.ServiceProvider;

import java.util.Optional;

/**
 * The type Edit manager command.
 */
public class EditManagerCommand implements Command {

	private ManagerService managerService = ServiceProvider.getInstance().getManagerService();

	@Override
	public Router execute(SessionContent content) throws CommandException {
		Router router = new Router();
		router.setRoute(Router.RouteType.FORWARD);
		router.setPageToGo(PageName.ACCESS_DENIED_PAGE);
		String role = (String) content.getSessionAttribute(ParamName.PARAM_ROLE);
		if (role != null &&
						(role.equals(UserRole.MANAGER.toString()) ||
										role.equals(UserRole.ADMIN.toString()))) {
			router.setPageToGo(PageName.EDIT_MANAGER_PAGE);
			content.assignSessionAttribute(ParamName.PARAM_CURRENT_PAGE, PageName.EDIT_MANAGER_PAGE);
			String managerId = content.getRequestParameter(ParamName.PARAM_MANAGER_ID);
			try {
				Optional<Manager> managerOptional = managerService.findManagerById(managerId);
				if (managerOptional.isPresent()) {
					Manager manager = managerOptional.get();
					content.assignSessionAttribute(ParamName.PARAM_MANAGER, manager);
				}
				if (content.getSessionAttribute(ParamName.PARAM_PAGE_TO_RETURN) == null) {
					content.assignSessionAttribute(ParamName.PARAM_PAGE_TO_RETURN,
									PageName.MAIN_PAGE);
				}
			} catch (ServiceException e) {
				throw new CommandException("Exception while finding manager by id.", e);
			}
		}
		return router;
	}
}
