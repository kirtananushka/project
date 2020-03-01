package by.tananushka.project.command.impl.user;

import by.tananushka.project.bean.User;
import by.tananushka.project.bean.UserRole;
import by.tananushka.project.command.Command;
import by.tananushka.project.command.CommandException;
import by.tananushka.project.controller.PageName;
import by.tananushka.project.controller.ParamName;
import by.tananushka.project.controller.Router;
import by.tananushka.project.controller.SessionContent;
import by.tananushka.project.dao.AdminDao;
import by.tananushka.project.dao.ClientDao;
import by.tananushka.project.dao.DaoException;
import by.tananushka.project.dao.ManagerDao;
import by.tananushka.project.dao.impl.AdminDaoImpl;
import by.tananushka.project.dao.impl.ClientDaoImpl;
import by.tananushka.project.dao.impl.ManagerDaoImpl;

import java.util.Optional;

/**
 * The type View profile command.
 */
public class ViewProfileCommand implements Command {

	@Override
	public Router execute(SessionContent content) throws CommandException {
		Optional<? extends User> userOptional = Optional.empty();
		Router router = new Router();
		router.setRoute(Router.RouteType.FORWARD);
		router.setPageToGo(PageName.ACCESS_DENIED_PAGE);
		User user = (User) content.getSessionAttribute(ParamName.PARAM_USER_AUTHORIZATED);
		if (user != null) {
			UserRole userRole = user.getRole();
			switch (userRole) {
				case ADMIN:
					try {
						AdminDao adminDao = AdminDaoImpl.getInstance();
						userOptional = adminDao.findAdmin();
					} catch (DaoException e) {
						throw new CommandException("Exception while preparing admin's profile.");
					}
					break;
				case MANAGER:
					try {
						ManagerDao managerDao = ManagerDaoImpl.getInstance();
						userOptional = managerDao.findManagerById(user.getId());
					} catch (DaoException e) {
						throw new CommandException("Exception while preparing manager's profile.");
					}
					break;
				case CLIENT:
					try {
						ClientDao clientDao = ClientDaoImpl.getInstance();
						userOptional = clientDao.findClientById(user.getId());
					} catch (DaoException e) {
						throw new CommandException("Exception while preparing client's profile.");
					}
					break;
			}
			if (userOptional.isPresent()) {
				content.assignSessionAttribute(ParamName.PARAM_USER_OBJ, userOptional.get());
				router.setPageToGo(PageName.USER_PROFILE_PAGE);
				content.assignSessionAttribute(ParamName.PARAM_CURRENT_PAGE, PageName.USER_PROFILE_PAGE);
			} else {
				router.setPageToGo(PageName.MAIN_PAGE);
			}
		}
		return router;
	}
}
