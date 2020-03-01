package by.tananushka.project.command.impl.film;

import by.tananushka.project.bean.UserRole;
import by.tananushka.project.command.Command;
import by.tananushka.project.command.CommandException;
import by.tananushka.project.command.ErrorMessageKey;
import by.tananushka.project.controller.PageName;
import by.tananushka.project.controller.ParamName;
import by.tananushka.project.controller.Router;
import by.tananushka.project.controller.SessionContent;
import by.tananushka.project.service.FilmService;
import by.tananushka.project.service.ServiceException;
import by.tananushka.project.service.ServiceProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Delete film command.
 */
public class DeleteFilmCommand implements Command {

	private FilmService filmService = ServiceProvider.getInstance().getFilmService();

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
			content.assignSessionAttribute(ParamName.PARAM_ERR_DELETE_FILM_MESSAGE, null);
			String filmId = content.getRequestParameter(ParamName.PARAM_FILM_ID);
			try {
				if (filmService.deleteFilm(filmId)) {
					content.assignSessionAttribute(ParamName.PARAM_FILM_OBJ, null);
					if (content.getSessionAttribute(ParamName.PARAM_PAGE_TO_RETURN) == null) {
						content.assignSessionAttribute(ParamName.PARAM_PAGE_TO_RETURN,
										PageName.MAIN_PAGE);
					}
					router.setRoute(Router.RouteType.REDIRECT);
					router.setPageToGo(PageName.DELETION_SUCCESSFUL_PAGE);
				} else {
					router.setRoute(Router.RouteType.FORWARD);
					router.setPageToGo(PageName.DELETE_FILM_PAGE);
					List<String> errorsList = new ArrayList<>();
					errorsList.add(ErrorMessageKey.DELETION_FAILED);
					content.assignSessionAttribute(ParamName.PARAM_ERR_DELETE_FILM_MESSAGE, errorsList);
				}
			} catch (ServiceException e) {
				throw new CommandException("Exception while film deletion.", e);
			}
		}
		return router;
	}
}
