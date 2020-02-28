package by.tananushka.project.command.impl.show;

import by.tananushka.project.bean.Show;
import by.tananushka.project.command.Command;
import by.tananushka.project.command.CommandException;
import by.tananushka.project.controller.PageName;
import by.tananushka.project.controller.ParamName;
import by.tananushka.project.controller.Router;
import by.tananushka.project.controller.SessionContent;
import by.tananushka.project.parsing.DateParser;
import by.tananushka.project.service.ServiceException;
import by.tananushka.project.service.ServiceProvider;
import by.tananushka.project.service.ShowService;
import by.tananushka.project.util.PagesCalculator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FindShowsCommand implements Command {

	private static final String ALL = "all";
	private static final String FULL_URL =
					"controller?command=shows_available&date=%s&cinema=%s&page=";
	private static Logger log = LogManager.getLogger();
	private ShowService showService = ServiceProvider.getInstance().getShowService();
	private DateParser dateParser = DateParser.getInstance();
	private PagesCalculator pagesCalculator = PagesCalculator.getInstance();

	@Override
	public Router execute(SessionContent content) throws CommandException {
		Router router = new Router();
		router.setRoute(Router.RouteType.FORWARD);
		router.setPageToGo(PageName.AVAILABLE_SHOWS_PAGE);
		String paramDate = content.getRequestParameter(ParamName.PARAM_DATE);
		String paramCinema = content.getRequestParameter(ParamName.PARAM_CINEMA);
		log.debug("cinema: {}", paramCinema);
		paramDate = paramDate == null ? dateParser.formatDate(LocalDate.now()) : paramDate.strip();
		paramCinema = (paramCinema == null || paramCinema.isBlank()) ? ALL : paramCinema.strip();
		try {
			Map<String, List<Show>> showsMap;
			if (paramCinema.equals(ALL)) {
				showsMap = showService.findShow(paramDate);
			} else {
				showsMap = showService.findShow(paramDate, paramCinema);
			}
			List<LocalDate> datesList = showService.findDates();
			List<String> strDatesList = datesList.stream()
			                                     .map(d -> dateParser.formatDate(d))
			                                     .collect(Collectors.toList());
			Map<Integer, String> cinemasMap = showService.findCinemas();
			int pageNumber = Integer.parseInt(content.getRequestParameter(ParamName.PARAM_PAGE));
			int showsFrom = pageNumber - pagesCalculator.getSingleItemsPerPage();
			int totalPages = showsMap.size();
			List<String> cinemaNames = new ArrayList<>(showsMap.keySet())
							.stream().skip(showsFrom)
							.limit(pagesCalculator.getSingleItemsPerPage())
							.collect(Collectors.toList());
			Map<String, List<Show>> showsForPageMap = new LinkedHashMap<>();
			for (String cinemaName : cinemaNames) {
				List<Show> shows = showsMap.get(cinemaName);
				showsForPageMap.put(cinemaName, shows);
			}
			String pageURL = String.format(FULL_URL, paramDate, paramCinema);
			content.assignRequestAttribute(ParamName.PARAM_SHOWS_MAP, showsForPageMap);
			content.assignRequestAttribute(ParamName.PARAM_DEFAULT_DATE, paramDate);
			content.assignRequestAttribute(ParamName.PARAM_TOTAL_PAGES, totalPages);
			content.assignRequestAttribute(ParamName.PARAM_PAGE_NUMBER, pageNumber);
			content.assignRequestAttribute(ParamName.PARAM_PAGE_URL, pageURL);
			content.assignSessionAttribute(ParamName.PARAM_PAGE_TO_RETURN, pageURL + pageNumber);
			content.assignRequestAttribute(ParamName.PARAM_DATES_LIST, strDatesList);
			content.assignRequestAttribute(ParamName.PARAM_CINEMAS_MAP, cinemasMap);
		} catch (ServiceException e) {
			throw new CommandException("Exception while finding available shows.", e);
		}
		return router;
	}
}
