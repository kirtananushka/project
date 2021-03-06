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
import by.tananushka.project.util.PagesCalculator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The type Find active films command.
 */
public class FindActiveFilmsCommand implements Command {

	private static final String CONTROLLER = "controller?command=";
	private static final String COMMAND = "films_active";
	private static final String SELECT = "&select=%s";
	private static final String ITEM = "&item=%s&page=";
	private static final String ALL = "all";
	private static final String DEFAULT_URL = "controller?command=films_active&page=";
	private static final String SELECT_VALUE_DEFAULT = "film.%s";
	private static Logger log = LogManager.getLogger();
	private FilmService filmService = ServiceProvider.getInstance().getFilmService();
	private PagesCalculator pagesCalculator = PagesCalculator.getInstance();

	@Override
	public Router execute(SessionContent content) throws CommandException {
		Router router = new Router();
		router.setRoute(Router.RouteType.FORWARD);
		router.setPageToGo(PageName.ACCESS_DENIED_PAGE);
		String role = (String) content.getSessionAttribute(ParamName.PARAM_ROLE);
		if (role != null &&
						(role.equals(UserRole.MANAGER.toString()) ||
										role.equals(UserRole.ADMIN.toString()))) {
			router.setRoute(Router.RouteType.FORWARD);
			router.setPageToGo(PageName.ACTIVE_FILMS_PAGE);
			String paramSelect = content.getRequestParameter(ParamName.PARAM_SELECT);
			String paramItem = content.getRequestParameter(ParamName.PARAM_ITEM);
			paramSelect = paramSelect == null ? ALL : paramSelect;
			paramItem = paramItem == null ? ALL : paramItem;
			try {
				List<Film> filmsList;
				Map itemMap;
				switch (paramSelect) {
					case ParamName.PARAM_GENRE:
						filmsList = filmService.findActiveFilmsByGenre(paramItem);
						itemMap = filmService.findGenresMap();
						break;
					case ParamName.PARAM_COUNTRY:
						filmsList = filmService.findActiveFilmsByCountry(paramItem);
						itemMap = filmService.findCountriesMap();
						break;
					case ParamName.PARAM_AGE:
						filmsList = filmService.findActiveFilmsByAge(paramItem);
						itemMap = filmService.findAgesMap();
						break;
					case ParamName.PARAM_YEAR:
						filmsList = filmService.findActiveFilmsByYear(paramItem);
						itemMap = filmService.findYearsMap();
						break;
					default:
						filmsList = filmService.findActiveFilms();
						itemMap = filmService.findGenresMap();
						paramSelect = ParamName.PARAM_GENRE;
				}
				String strPageNumber = content.getRequestParameter(ParamName.PARAM_PAGE);
				int pageNumber = 1;
				try {
					pageNumber = Integer.parseInt(strPageNumber);
				} catch (NumberFormatException e) {
					log.info("Invalid page number: {}.", strPageNumber);
				}
				int filmsFrom = pagesCalculator.calculateItemsFrom(pageNumber);
				int filmsNumber = filmsList.size();
				int totalPages = pagesCalculator.calculateTotalPages(filmsNumber);
				List<Film> filmsForPageList =
								filmsList.stream()
								         .skip(filmsFrom)
								         .limit(pagesCalculator.getItemsPerPage())
								         .collect(Collectors.toList());
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
				throw new CommandException("Exception while finding active films.", e);
			}
		}
		return router;
	}
}
