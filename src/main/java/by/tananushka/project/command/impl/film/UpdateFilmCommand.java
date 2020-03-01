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

/**
 * The type Update film command.
 */
public class UpdateFilmCommand implements Command {

	private static Logger log = LogManager.getLogger();
	private FilmService filmService = ServiceProvider.getInstance().getFilmService();

	@Override
	public Router execute(SessionContent content) throws CommandException {
		Router router = new Router();
		router.setRoute(Router.RouteType.REDIRECT);
		String pageToGo = PageName.EDIT_FILM_PAGE;
		content.assignSessionAttribute(ParamName.PARAM_CURRENT_PAGE, pageToGo);
		try {
			Film film = filmService.updateFilm(content)
			                       .orElse((Film) content
							                       .getSessionAttribute(ParamName.PARAM_FILM_OBJ));
			content.assignSessionAttribute(ParamName.PARAM_FILM_OBJ, film);
			router.setRoute(Router.RouteType.REDIRECT);
			pageToGo = PageName.VIEW_FILM_UPDATED_PAGE;
			content.assignSessionAttribute(ParamName.PARAM_CURRENT_PAGE, pageToGo);
		} catch (ServiceException e) {
			log.error("Exception while updating film.", e);
			Map<String, String> errorsMap =
							(Map<String, String>) content
											.getSessionAttribute(ParamName.PARAM_ERR_UPDATE_FILM_MESSAGE);
			if (errorsMap == null) {
				errorsMap = new LinkedHashMap<>();
			}
			errorsMap.put(ErrorMessageKey.UPDATE_FAILED, "");
			content.assignSessionAttribute(ParamName.PARAM_ERR_UPDATE_FILM_MESSAGE, errorsMap);
		} catch (Exception e) {
			throw new CommandException("Exception while updating film.", e);
		}
		router.setPageToGo(pageToGo);
		return router;
	}
}
