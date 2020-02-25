package by.tananushka.project.command.impl.film;

import by.tananushka.project.bean.Film;
import by.tananushka.project.bean.UserRole;
import by.tananushka.project.command.Command;
import by.tananushka.project.command.CommandException;
import by.tananushka.project.controller.PageName;
import by.tananushka.project.controller.ParamName;
import by.tananushka.project.controller.Router;
import by.tananushka.project.controller.SessionContent;
import by.tananushka.project.service.FilmService;
import by.tananushka.project.service.ServiceException;
import by.tananushka.project.service.ServiceProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class SetImageCommand implements Command {

	private static Logger log = LogManager.getLogger();
	private FilmService filmService = ServiceProvider.getInstance().getFilmService();

	@Override
	public Router execute(SessionContent content) throws CommandException {
		Router router = new Router();
		router.setPageToGo(PageName.ACCESS_DENIED_PAGE);
		String role = (String) content.getSessionAttribute(ParamName.PARAM_ROLE);
		if (role != null &&
						(role.equals(UserRole.MANAGER.toString()) ||
										role.equals(UserRole.ADMIN.toString()))) {
			content.assignSessionAttribute(ParamName.PARAM_CURRENT_PAGE, PageName.VIEW_FILM_PAGE);
			Film film = (Film) content.getSessionAttribute(ParamName.PARAM_FILM_OBJ);
			int filmId = film.getId();
			String img = (String) content.getSessionAttribute(ParamName.PARAM_IMG);
			try {
				Optional<Film> filmOptional = filmService.updateImage(filmId, img);
				film = filmOptional.orElse(film);
				router.setRoute(Router.RouteType.REDIRECT);
				router.setPageToGo(PageName.VIEW_FILM_PAGE);
				content.assignSessionAttribute(ParamName.PARAM_FILM_OBJ, film);
			} catch (ServiceException e) {
				throw new CommandException("Exception while image updating.", e);
			}
		}
		return router;
	}
}
