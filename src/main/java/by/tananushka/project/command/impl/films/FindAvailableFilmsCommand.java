package by.tananushka.project.command.impl.films;

import by.tananushka.project.bean.Film;
import by.tananushka.project.command.Command;
import by.tananushka.project.command.CommandException;
import by.tananushka.project.controller.JspPageName;
import by.tananushka.project.controller.ParamName;
import by.tananushka.project.controller.Router;
import by.tananushka.project.controller.SessionContent;
import by.tananushka.project.service.FilmService;
import by.tananushka.project.service.ServiceException;
import by.tananushka.project.service.ServiceProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.stream.Collectors;

public class FindAvailableFilmsCommand implements Command {

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
		router.setPageToGo(JspPageName.AVAILABLE_FILMS_PAGE);
		String paramSelect = content.getRequestParameter(ParamName.PARAM_SELECT);
		String paramItem = content.getRequestParameter(ParamName.PARAM_ITEM);
		paramSelect = paramSelect == null ? ALL : paramSelect;
		paramItem = paramItem == null ? ALL : paramItem;
		List<Film> filmsList;
		List itemList;
		log.debug(paramSelect);
		try {
			switch (paramSelect) {
				case ParamName.PARAM_GENRE:
					filmsList = filmService.findFilmsByGenre(paramItem);
					itemList = filmService.findGenres();
					break;
				case ParamName.PARAM_COUNTRY:
					filmsList = filmService.findFilmsByCountry(paramItem);
					itemList = filmService.findCountries();
					break;
				case "age":
					filmsList = filmService.findFilmsByAge(paramItem);
					itemList = filmService.findAges();
					break;
				case "year":
					filmsList = filmService.findFilmsByYear(paramItem);
					itemList = filmService.findYears();
					break;
				default:
					filmsList = filmService.findAvailableFilms();
					itemList = filmService.findGenres();
					paramSelect = ParamName.PARAM_GENRE;
			}
			int pageNumber = Integer.parseInt(content.getRequestParameter(ParamName.PARAM_PAGE));
			int filmFrom = 5 * (pageNumber - 1);
			int filmsNumber = filmsList.size();
			log.debug(filmsNumber);
			int totalPages = (filmsNumber + 4) / 5;
			log.debug(totalPages);
			List<Film> filmsForPageList =
							filmsList.stream().skip(filmFrom).limit(5).collect(Collectors.toList());
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
			content.assignRequestAttribute(ParamName.PARAM_DEFAULT_VALUE, defaultValue);
			content.assignRequestAttribute(ParamName.PARAM_SELECT_PARAM, paramSelect);
			content.assignRequestAttribute(ParamName.PARAM_SELECT_LIST, itemList);
		} catch (ServiceException e) {
			throw new CommandException(e);
		}
		return router;
	}
}
