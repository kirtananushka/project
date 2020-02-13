package by.tananushka.project.command.impl.common;

import by.tananushka.project.command.Command;
import by.tananushka.project.controller.JspPageName;
import by.tananushka.project.controller.ParamName;
import by.tananushka.project.controller.Router;
import by.tananushka.project.controller.SessionContent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ChangeLocaleCommand implements Command {

	private static Logger log = LogManager.getLogger();

	@Override
	public Router execute(SessionContent content) {
		String currentParam = ParamName.PARAM_LOCALE;
		String currentLocale = (String) content.getSessionAttribute(currentParam);
		String changedLocale = currentLocale.equals(ParamName.PARAM_RU_RU) ? ParamName.PARAM_EN_EN
		                                                                   : ParamName.PARAM_RU_RU;
		content.assignSessionAttribute(currentParam, changedLocale);
		String pageToGo = (String) content.getSessionAttribute(ParamName.PARAM_CURRENT_PAGE);
		log.debug("Page to go: {}", pageToGo);
		if (pageToGo == null) {
			pageToGo = JspPageName.MAIN_PAGE;
		}
		Router router = new Router();
		router.setPageToGo(pageToGo);
		router.setRoute(Router.RouteType.FORWARD);
		return router;
	}
}
