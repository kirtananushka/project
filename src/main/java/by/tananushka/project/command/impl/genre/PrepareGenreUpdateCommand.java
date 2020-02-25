package by.tananushka.project.command.impl.genre;

import by.tananushka.project.bean.UserRole;
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

public class PrepareGenreUpdateCommand implements Command {

	private static Logger log = LogManager.getLogger();
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
			Map<String, String> errorsMap = new LinkedHashMap<>();
			String pageToGo;
			try {
				Map<Integer, String> genresMap = filmService.findGenresMap();
				int genreId = Integer.parseInt(content.getRequestParameter(ParamName.PARAM_GENRE));
				content.assignSessionAttribute(ParamName.PARAM_GENRE_ID, genreId);
				String genreName = genresMap.get(genreId);
				content.assignSessionAttribute(ParamName.PARAM_GENRE, genreName);
				pageToGo = PageName.UPDATE_GENRE_PAGE;
				router.setPageToGo(pageToGo);
			} catch (ServiceException e) {
				pageToGo = PageName.EDIT_GENRE_PAGE;
				router.setPageToGo(pageToGo);
				Map<String, String> errorsMapFromContent =
								(Map<String, String>) content
												.getSessionAttribute(ParamName.PARAM_ERR_UPDATE_GENRE_MESSAGE);
				if (errorsMapFromContent != null) {
					errorsMap = errorsMapFromContent;
				}
				errorsMap.put(ErrorMessageKey.GENRE_UPDATING_FAILED, "");
				content.assignSessionAttribute(ParamName.PARAM_ERR_UPDATE_GENRE_MESSAGE, errorsMap);
			}
		}
		return router;
	}
}
