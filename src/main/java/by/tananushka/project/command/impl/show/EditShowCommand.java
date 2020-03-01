package by.tananushka.project.command.impl.show;

import by.tananushka.project.bean.Show;
import by.tananushka.project.bean.UserRole;
import by.tananushka.project.command.Command;
import by.tananushka.project.command.CommandException;
import by.tananushka.project.controller.PageName;
import by.tananushka.project.controller.ParamName;
import by.tananushka.project.controller.Router;
import by.tananushka.project.controller.SessionContent;
import by.tananushka.project.parsing.DateParser;
import by.tananushka.project.parsing.MoneyParser;
import by.tananushka.project.service.FilmService;
import by.tananushka.project.service.ServiceException;
import by.tananushka.project.service.ServiceProvider;
import by.tananushka.project.service.ShowService;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * The type Edit show command.
 */
public class EditShowCommand implements Command {

	private ShowService showService = ServiceProvider.getInstance().getShowService();
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
			router.setPageToGo(PageName.EDIT_SHOW_PAGE);
			content.assignSessionAttribute(ParamName.PARAM_CURRENT_PAGE, PageName.EDIT_SHOW_PAGE);
			content.assignSessionAttribute(ParamName.PARAM_ERR_UPDATE_SHOW_MESSAGE, null);
			String showId = content.getRequestParameter(ParamName.PARAM_SHOW_ID);
			try {
				Optional<Show> showOptional = showService.findShowById(showId);
				if (showOptional.isPresent()) {
					Show show = showOptional.get();
					Map<Integer, String> cinemasMap = showService.findCinemas();
					String cinemaName = show.getCinemaName();
					int cinemaNameId = cinemasMap.entrySet()
					                             .stream()
					                             .filter(entry -> cinemaName.equals(entry.getValue()))
					                             .map(Map.Entry::getKey)
					                             .findFirst().orElse(0);
					Map<Integer, String> titlesMap = filmService.findTitlesMap();
					String title = show.getFilm().getTitle();
					int titleId = titlesMap.entrySet()
					                       .stream()
					                       .filter(entry -> title.equals(entry.getValue()))
					                       .map(Map.Entry::getKey)
					                       .findFirst().orElse(0);
					String hour = String.format("%02d", show.getDateTime().getHour());
					String minute = String.format("%02d", show.getDateTime().getMinute());
					String strDate = DateParser.getInstance().formatDate(show.getDateTime().toLocalDate());
					List<String> hoursList = IntStream.range(0, 24)
					                                  .mapToObj(h -> String.format("%02d", h))
					                                  .collect(Collectors.toList());
					List<String> minutesList = IntStream.range(0, 60).filter(m -> m % 5 == 0)
					                                    .mapToObj(m -> String.format("%02d", m))
					                                    .collect(Collectors.toList());
					List<String> datesList = DateParser.getInstance().getListOfDates(2);
					List<String> hundredList = IntStream.range(0, 100)
					                                    .mapToObj(p -> String.format("%02d", p))
					                                    .collect(Collectors.toList());
					List<String> dozenList = IntStream.range(0, 100).filter(m -> m % 10 == 0)
					                                  .mapToObj(m -> String.format("%02d", m))
					                                  .collect(Collectors.toList());
					String ruble =
									MoneyParser.getInstance()
									           .parseBigDecimalToString(show.getCost(), 0)
									           .orElse("");
					String copeck =
									MoneyParser.getInstance()
									           .parseBigDecimalToString(show.getCost(), 1)
									           .orElse("");
					content.assignSessionAttribute(ParamName.PARAM_HOUR, hour);
					content.assignSessionAttribute(ParamName.PARAM_HOURS, hoursList);
					content.assignSessionAttribute(ParamName.PARAM_MINUTE, minute);
					content.assignSessionAttribute(ParamName.PARAM_MINUTES, minutesList);
					content.assignSessionAttribute(ParamName.PARAM_DATE, strDate);
					content.assignSessionAttribute(ParamName.PARAM_DATES_LIST, datesList);
					content.assignSessionAttribute(ParamName.PARAM_HUNDRED_LIST, hundredList);
					content.assignSessionAttribute(ParamName.PARAM_DOZEN_LIST, dozenList);
					content.assignSessionAttribute(ParamName.PARAM_CINEMAS_MAP, cinemasMap);
					content.assignSessionAttribute(ParamName.PARAM_CINEMA_ID, cinemaNameId);
					content.assignSessionAttribute(ParamName.PARAM_TITLE_ID, titleId);
					content.assignSessionAttribute(ParamName.PARAM_TITLES_MAP, titlesMap);
					content.assignSessionAttribute(ParamName.PARAM_SHOW_OBJ, show);
					content.assignSessionAttribute(ParamName.PARAM_RUBLE, ruble);
					content.assignSessionAttribute(ParamName.PARAM_COPECK, copeck);
					if (content.getSessionAttribute(ParamName.PARAM_PAGE_TO_RETURN) == null) {
						content.assignSessionAttribute(ParamName.PARAM_PAGE_TO_RETURN,
										PageName.MAIN_PAGE);
					}
				}
			} catch (ServiceException e) {
				throw new CommandException("Exception while finding show by id.", e);
			}
		}
		return router;
	}
}
