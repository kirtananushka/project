package by.tananushka.project.command.impl.film;

import by.tananushka.project.bean.Film;
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

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FindFilmsCommand implements Command {

	private static final String CONTROLLER = "controller?command=";
	private static final String COMMAND = "films_available";
	private static final String SELECT = "&select=%s";
	private static final String ITEM = "&item=%s&page=";
	private static final String ALL = "all";
	private static final String DEFAULT_URL = "controller?command=films_available&page=";
	private static final String SELECT_VALUE_DEFAULT = "film.%s";
	private static Logger log = LogManager.getLogger();
	private FilmService filmService = ServiceProvider.getInstance().getFilmService();

	@Override
	public Router execute(SessionContent content) throws CommandException {
		Router router = new Router();
		router.setRoute(Router.RouteType.FORWARD);
		router.setPageToGo(PageName.AVAILABLE_FILMS_PAGE);
		String paramSelect = content.getRequestParameter(ParamName.PARAM_SELECT);
		String paramItem = content.getRequestParameter(ParamName.PARAM_ITEM);
		paramSelect = paramSelect == null ? ALL : paramSelect;
		paramItem = paramItem == null ? ALL : paramItem;
		log.debug(paramSelect);
		try {
			List<Film> filmsList;
			Map itemMap;
			switch (paramSelect) {
				case ParamName.PARAM_GENRE:
					filmsList = filmService.findFilmsByGenre(paramItem);
					itemMap = filmService.findGenresMap();
					break;
				case ParamName.PARAM_COUNTRY:
					filmsList = filmService.findFilmsByCountry(paramItem);
					itemMap = filmService.findCountriesMap();
					break;
				case ParamName.PARAM_AGE:
					filmsList = filmService.findFilmsByAge(paramItem);
					itemMap = filmService.findAgesMap();
					break;
				case ParamName.PARAM_YEAR:
					filmsList = filmService.findFilmsByYear(paramItem);
					itemMap = filmService.findYearsMap();
					break;
				default:
					filmsList = filmService.findAvailableFilms();
					itemMap = filmService.findGenresMap();
					paramSelect = ParamName.PARAM_GENRE;
			}
			int pageNumber = Integer.parseInt(content.getRequestParameter(ParamName.PARAM_PAGE));
			int filmsFrom = 5 * (pageNumber - 1);
			int filmsNumber = filmsList.size();
			log.debug(filmsNumber);
			int totalPages = (filmsNumber + 4) / 5;
			log.debug(totalPages);
			List<Film> filmsForPageList =
							filmsList.stream().skip(filmsFrom).limit(5).collect(Collectors.toList());
			String select = String.format(SELECT, paramSelect).strip();
			String item = String.format(ITEM, paramItem).strip();
			String pageURL;
			if (!paramItem.equals(ALL)) {
				pageURL = (CONTROLLER + COMMAND + select + item).strip();
			} else {
				pageURL = DEFAULT_URL;
			}
			log.debug(pageURL);
			String defaultValue = String.format(SELECT_VALUE_DEFAULT, paramSelect);
			content.assignRequestAttribute(ParamName.PARAM_FILMS_LIST, filmsForPageList);
			content.assignRequestAttribute(ParamName.PARAM_TOTAL_PAGES, totalPages);
			content.assignRequestAttribute(ParamName.PARAM_PAGE_NUMBER, pageNumber);
			content.assignRequestAttribute(ParamName.PARAM_PAGE_URL, pageURL);
			content.assignSessionAttribute(ParamName.PARAM_PAGE_TO_RETURN, pageURL + pageNumber);
			content.assignRequestAttribute(ParamName.PARAM_DEFAULT_VALUE, defaultValue);
			content.assignRequestAttribute(ParamName.PARAM_SELECT_PARAM, paramSelect);
			content.assignRequestAttribute(ParamName.PARAM_SELECT_MAP, itemMap);
		} catch (ServiceException e) {
			throw new CommandException("Exception while finding available films.", e);
		}
		return router;
	}
}
