package by.tananushka.project.command.impl.film;

import by.tananushka.project.bean.Film;
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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class CreateFilmCommand implements Command {

	private static Logger log = LogManager.getLogger();
	private FilmService filmService = ServiceProvider.getInstance().getFilmService();

	@Override
	public Router execute(SessionContent content) throws CommandException {
		Router router = new Router();
		router.setRoute(Router.RouteType.REDIRECT);
		String pageToGo = PageName.CREATE_FILM_PAGE;
		content.assignSessionAttribute(ParamName.PARAM_CURRENT_PAGE, pageToGo);
		Map<String, String> errorsMap = new LinkedHashMap<>();
		try {
			Optional<Film> filmOptional = filmService.createFilm(content);
			if (filmOptional.isPresent()) {
				Film film = filmOptional.get();
				content.assignSessionAttribute(ParamName.PARAM_FILM_OBJ, film);
				router.setRoute(Router.RouteType.REDIRECT);
				pageToGo = PageName.VIEW_FILM_PAGE;
				content.assignSessionAttribute(ParamName.PARAM_CURRENT_PAGE, pageToGo);
			} else {
				errorsMap.put(ErrorMessageKey.FILM_CREATION_FAILED, "");
			}
		} catch (ServiceException e) {
			log.error("Exception while creating film.", e);
			Map<String, String> errorsMapFromContent =
							(Map<String, String>) content
											.getSessionAttribute(ParamName.PARAM_ERR_CREATE_FILM_MESSAGE);
			if (errorsMapFromContent != null) {
				errorsMap = errorsMapFromContent;
			}
			errorsMap.put(ErrorMessageKey.FILM_CREATION_FAILED, "");
		} catch (Exception e) {
			throw new CommandException("Exception while film creation.", e);
		}
		content.assignSessionAttribute(ParamName.PARAM_ERR_CREATE_FILM_MESSAGE, errorsMap);
		router.setPageToGo(pageToGo);
		return router;
	}
}
