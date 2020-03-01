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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;

/**
 * The type Edit film command.
 */
public class EditFilmCommand implements Command {

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
			router.setPageToGo(PageName.EDIT_FILM_PAGE);
			content.assignSessionAttribute(ParamName.PARAM_CURRENT_PAGE, PageName.EDIT_FILM_PAGE);
			content.assignSessionAttribute(ParamName.PARAM_ERR_UPDATE_FILM_MESSAGE, null);
			String filmId = content.getRequestParameter(ParamName.PARAM_FILM_ID);
			try {
				Optional<Film> filmOptional = filmService.findFilmById(filmId);
				if (filmOptional.isPresent()) {
					Film film = filmOptional.get();
					Map<Integer, String> genresMap = filmService.findGenresMap();
					Map<Integer, String> countriesMap = filmService.findCountriesMap();
					List<Integer> ages = filmService.findAges();
					List<Integer> years = new ArrayList<>();
					IntStream.range(1895, LocalDate.now()
					                               .getYear() + 1).forEach(years::add);
					Map<Integer, String> unselectedGenresMap = new LinkedHashMap<>();
					Map<Integer, String> selectedGenresMap = new LinkedHashMap<>();
					for (Map.Entry<Integer, String> entry : genresMap.entrySet()) {
						String value = entry.getValue();
						if (film.getGenres().contains(value)) {
							selectedGenresMap.put(entry.getKey(), entry.getValue());
						} else {
							unselectedGenresMap.put(entry.getKey(), entry.getValue());
						}
					}
					Map<Integer, String> unselectedCountriesMap = new LinkedHashMap<>();
					Map<Integer, String> selectedCountriesMap = new LinkedHashMap<>();
					for (Map.Entry<Integer, String> entry : countriesMap.entrySet()) {
						String value = entry.getValue();
						if (film.getCountries().contains(value)) {
							selectedCountriesMap.put(entry.getKey(), entry.getValue());
						} else {
							unselectedCountriesMap.put(entry.getKey(), entry.getValue());
						}
					}
					content.assignSessionAttribute(ParamName.PARAM_GENRES_MAP, genresMap);
					content.assignSessionAttribute(ParamName.PARAM_COUNTRIES_MAP, countriesMap);
					content.assignSessionAttribute(ParamName.PARAM_UNSEL_GENRES_MAP,
									unselectedGenresMap);
					content.assignSessionAttribute(ParamName.PARAM_SEL_GENRES_MAP, selectedGenresMap);
					content.assignSessionAttribute(ParamName.PARAM_UNSEL_COUNTRIES_MAP,
									unselectedCountriesMap);
					content.assignSessionAttribute(ParamName.PARAM_SEL_COUNTRIES_MAP, selectedCountriesMap);
					content.assignSessionAttribute(ParamName.PARAM_AGES_LIST, ages);
					content.assignSessionAttribute(ParamName.PARAM_YEARS_LIST, years);
					content.assignSessionAttribute(ParamName.PARAM_FILM_OBJ, film);
					content.assignSessionAttribute(ParamName.PARAM_IMG, film.getImg());
					if (content.getSessionAttribute(ParamName.PARAM_PAGE_TO_RETURN) == null) {
						content.assignSessionAttribute(ParamName.PARAM_PAGE_TO_RETURN,
										PageName.MAIN_PAGE);
					}
				}
			} catch (ServiceException e) {
				throw new CommandException("Exception while finding film by id.", e);
			}
		}
		return router;
	}
}
