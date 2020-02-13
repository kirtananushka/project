package by.tananushka.project.filter;

import by.tananushka.project.controller.JspPageName;
import by.tananushka.project.controller.ParamName;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = {"/*"})
public class CurrentPageFilter implements Filter {

	private static final String REFERER = "referer";
	private static final String LOCALHOST = "http://localhost:8080";
	private static final String CONTROLLER = "/controller";
	private static final String CONTROLLER_COMMAND = "/controller?command";
	private static final String JSP_CONTROLLER = "/jsp/controller";
	private static final String JSP = "/jsp/";
	private static Logger log = LogManager.getLogger();

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
					throws ServletException, IOException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpSession session = request.getSession(true);
		log.debug("Get the previous page: {}", session.getAttribute(ParamName.PARAM_PREVIOUS_PAGE));
		log.debug("Get the current page: {}", session.getAttribute(ParamName.PARAM_CURRENT_PAGE));
		String url = request.getHeader(REFERER);
		log.debug("url: {}", url);
		String path = substringPathWithRegex(url);
		log.debug("path: {}", path);
		if (session.getAttribute(ParamName.PARAM_PREVIOUS_PAGE) == null) {
			session.setAttribute(ParamName.PARAM_PREVIOUS_PAGE, session
							.getAttribute(ParamName.PARAM_CURRENT_PAGE));
		}
		if (url != null && url.contains(CONTROLLER) && !url.contains(CONTROLLER_COMMAND)) {
			log.debug("Get previous page if controller: {}", session
							.getAttribute(ParamName.PARAM_PREVIOUS_PAGE));
		} else {
			session.setAttribute(ParamName.PARAM_PREVIOUS_PAGE, session
							.getAttribute(ParamName.PARAM_CURRENT_PAGE));
			session.setAttribute(ParamName.PARAM_CURRENT_PAGE, path);
		}
		log.debug("Set the previous page: {}", session.getAttribute(ParamName.PARAM_PREVIOUS_PAGE));
		chain.doFilter(req, resp);
	}

	private String substringPathWithRegex(String url) {
		String path = null;
		if (url != null) {
			path = url.replace(LOCALHOST, "");
			if (url.contains(CONTROLLER) || url.contains(JSP_CONTROLLER)) {
				path = JspPageName.MAIN_PAGE;
			}
			if (url.contains(CONTROLLER_COMMAND)) {
				path = url;
			}
			if (url.contains(JSP)) {
				path = url.replace(JSP, "/");
			}
			path = path.replace(LOCALHOST, "");
		} else {
			path = JspPageName.MAIN_PAGE;
		}
		return path;
	}
}

