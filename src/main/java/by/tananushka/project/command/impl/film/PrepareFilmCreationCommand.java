package by.tananushka.project.command.impl.film;

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
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * The type Prepare film creation command.
 */
public class PrepareFilmCreationCommand implements Command {

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
			router.setPageToGo(PageName.CREATE_FILM_PAGE);
			content.assignSessionAttribute(ParamName.PARAM_PAGE_TO_RETURN,
							content.getSessionAttribute(ParamName.PARAM_CURRENT_PAGE));
			content.assignSessionAttribute(ParamName.PARAM_CURRENT_PAGE, PageName.CREATE_FILM_PAGE);
			content.assignSessionAttribute(ParamName.PARAM_ERR_CREATE_FILM_MESSAGE, null);
			content.assignSessionAttribute(ParamName.PARAM_TITLE_DEFAULT, null);
			content.assignSessionAttribute(ParamName.PARAM_IMG, PageName.IMAGE_DEFAULT);
			try {
				Map<Integer, String> genresMap = filmService.findGenresMap();
				Map<Integer, String> countriesMap = filmService.findCountriesMap();
				List<Integer> ages = filmService.findAges();
				List<Integer> years = new ArrayList<>();
				IntStream.range(1895, LocalDate.now()
				                               .getYear() + 1).forEach(years::add);
				content.assignSessionAttribute(ParamName.PARAM_GENRES_MAP, genresMap);
				content.assignSessionAttribute(ParamName.PARAM_COUNTRIES_MAP, countriesMap);
				content.assignSessionAttribute(ParamName.PARAM_AGES_LIST, ages);
				content.assignSessionAttribute(ParamName.PARAM_YEARS_LIST, years);
				if (content.getSessionAttribute(ParamName.PARAM_PAGE_TO_RETURN) == null) {
					content.assignSessionAttribute(ParamName.PARAM_PAGE_TO_RETURN,
									PageName.MAIN_PAGE);
				}
			} catch (ServiceException e) {
				throw new CommandException("Exception while preparing to create film.", e);
			}
		}
		return router;
	}
}
