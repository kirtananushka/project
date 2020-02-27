package by.tananushka.project.command.impl.user;

import by.tananushka.project.bean.User;
import by.tananushka.project.bean.UserRole;
import by.tananushka.project.command.Command;
import by.tananushka.project.command.CommandException;
import by.tananushka.project.controller.PageName;
import by.tananushka.project.controller.ParamName;
import by.tananushka.project.controller.Router;
import by.tananushka.project.controller.SessionContent;
import by.tananushka.project.service.ServiceException;
import by.tananushka.project.service.ServiceProvider;
import by.tananushka.project.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class FindAllUsersCommand implements Command {

	private static final String FULL_URL =
					"controller?command=find_all_users&page=";
	private static Logger log = LogManager.getLogger();
	private UserService userService = ServiceProvider.getInstance().getUserService();

	@Override
	public Router execute(SessionContent content) throws CommandException {
		Router router = new Router();
		router.setRoute(Router.RouteType.FORWARD);
		router.setPageToGo(PageName.ACCESS_DENIED_PAGE);
		String role = (String) content.getSessionAttribute(ParamName.PARAM_ROLE);
		if (role != null && role.equals(UserRole.ADMIN.toString())) {
			router.setPageToGo(PageName.ALL_USERS_PAGE);
			try {
				List<List<? extends User>> usersInitialList = userService.findAllUsers();
				List<? extends User> usersList =
								usersInitialList.stream()
								                .flatMap(Collection::stream)
								                .collect(Collectors.toList());
				int pageNumber = Integer.parseInt(content.getRequestParameter(ParamName.PARAM_PAGE));
				int usersFrom = 5 * (pageNumber - 1);
				int usersNumber = usersList.size();
				int totalPages = (usersNumber + 4) / 5;
				usersList = usersList
								.stream()
								.skip(usersFrom)
								.limit(5)
								.collect(Collectors.toList());
				content.assignRequestAttribute(ParamName.PARAM_USERS_LIST, usersList);
				content.assignRequestAttribute(ParamName.PARAM_TOTAL_PAGES, totalPages);
				content.assignRequestAttribute(ParamName.PARAM_PAGE_NUMBER, pageNumber);
				content.assignRequestAttribute(ParamName.PARAM_PAGE_URL, FULL_URL);
				content.assignSessionAttribute(ParamName.PARAM_PAGE_TO_RETURN, FULL_URL + pageNumber);
			} catch (ServiceException e) {
				throw new CommandException("Exception while finding all users.", e);
			}
		}
		return router;
	}
}
