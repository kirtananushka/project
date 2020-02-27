package by.tananushka.project.command.impl.manager;

import by.tananushka.project.bean.Manager;
import by.tananushka.project.bean.UserRole;
import by.tananushka.project.command.Command;
import by.tananushka.project.command.CommandException;
import by.tananushka.project.command.ErrorMessageKey;
import by.tananushka.project.controller.PageName;
import by.tananushka.project.controller.ParamName;
import by.tananushka.project.controller.Router;
import by.tananushka.project.controller.SessionContent;
import by.tananushka.project.service.ManagerService;
import by.tananushka.project.service.ServiceException;
import by.tananushka.project.service.ServiceProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AppointManagerCommand implements Command {

	private static Logger log = LogManager.getLogger();
	private ManagerService managerService = ServiceProvider.getInstance().getManagerService();

	@Override
	public Router execute(SessionContent content) throws CommandException {
		Router router = new Router();
		router.setRoute(Router.RouteType.FORWARD);
		String pageToGo = PageName.ACCESS_DENIED_PAGE;
		String role = (String) content.getSessionAttribute(ParamName.PARAM_ROLE);
		if (role != null && role.equals(UserRole.ADMIN.toString())) {
			router.setRoute(Router.RouteType.REDIRECT);
			pageToGo = PageName.VIEW_USER_UPDATED_PAGE;
			content.assignSessionAttribute(ParamName.PARAM_CURRENT_PAGE, pageToGo);
			content.assignSessionAttribute(ParamName.PARAM_ERR_UPDATE_USER_MESSAGE, null);
			try {
				Optional<Manager> managerOptional = managerService.appointManager(content);
				if (managerOptional.isPresent()) {
					content.assignSessionAttribute(ParamName.PARAM_USER_UPD, managerOptional.get());
				} else {
					pageToGo = PageName.EDIT_USER_PAGE;
					List<String> errorsList = new ArrayList<>();
					errorsList.add(ErrorMessageKey.MANAGER_APPOINTMENT_FAILED);
					content.assignSessionAttribute(ParamName.PARAM_ERR_UPDATE_USER_MESSAGE,
									errorsList);
				}
				if (content.getSessionAttribute(ParamName.PARAM_PAGE_TO_RETURN) == null) {
					content.assignSessionAttribute(ParamName.PARAM_PAGE_TO_RETURN,
									PageName.MAIN_PAGE);
				}
			} catch (ServiceException e) {
				log.error("Exception while appointment of manager.", e);
			} catch (Exception e) {
				throw new CommandException("Exception while appointment of manager.", e);
			}
		}
		router.setPageToGo(pageToGo);
		return router;
	}
}
