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
import by.tananushka.project.service.impl.FilmServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

public class PrepareGenreEditionCommand implements Command {

	private static Logger log = LogManager.getLogger();
	private FilmService filmService = FilmServiceImpl.getInstance();

	@Override
	public Router execute(SessionContent content) throws CommandException {
		Router router = new Router();
		router.setRoute(Router.RouteType.FORWARD);
		router.setPageToGo(PageName.ACCESS_DENIED_PAGE);
		String role = (String) content.getSessionAttribute(ParamName.PARAM_ROLE);
		if (role != null &&
						(role.equals(UserRole.MANAGER.toString()) ||
										role.equals(UserRole.ADMIN.toString()))) {
			String pageToGo = PageName.EDIT_GENRE_PAGE;
			router.setPageToGo(pageToGo);
			content.assignSessionAttribute(ParamName.PARAM_CURRENT_PAGE, pageToGo);
			content.assignSessionAttribute(ParamName.PARAM_ERR_EDIT_GENRE_MESSAGE, null);
			if (content.getSessionAttribute(ParamName.PARAM_PAGE_TO_RETURN) == null) {
				content.assignSessionAttribute(ParamName.PARAM_PAGE_TO_RETURN,
								PageName.MAIN_PAGE);
			}
			Map<Integer, String> genresMap;
			try {
				genresMap = filmService.findGenresMap();
				content.assignSessionAttribute(ParamName.PARAM_GENRES_MAP, genresMap);
			} catch (ServiceException e) {
				throw new CommandException("Exception while preparing genre edition.", e);
			}
		}
		return router;
	}
}
