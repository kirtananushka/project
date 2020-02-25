package by.tananushka.project.filter;

import by.tananushka.project.controller.PageName;
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
	private static final String EMAIL_CONFIRMATION = "command=email_confirmation";
	private static Logger log = LogManager.getLogger();

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
					throws ServletException, IOException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpSession session = request.getSession(true);
//		log.debug("Get the previous page: {}", session.getAttribute(ParamName.PARAM_PREVIOUS_PAGE));
//		log.debug("Get the current page: {}", session.getAttribute(ParamName.PARAM_CURRENT_PAGE));
		String url = request.getHeader(REFERER);
//		log.debug("url: {}", url);
		String path = substringPathWithRegex(url);
//		log.debug("path: {}", path);
		if (session.getAttribute(ParamName.PARAM_PREVIOUS_PAGE) == null) {
			session.setAttribute(ParamName.PARAM_PREVIOUS_PAGE, session
							.getAttribute(ParamName.PARAM_CURRENT_PAGE));
		}
		if (!(url != null && url.contains(PageName.CONTROLLER) && !url
						.contains(PageName.CONTROLLER_COMMAND))) {
			session.setAttribute(ParamName.PARAM_PREVIOUS_PAGE, session
							.getAttribute(ParamName.PARAM_CURRENT_PAGE));
			session.setAttribute(ParamName.PARAM_CURRENT_PAGE, path);
		}
		if (url != null && url.contains(PageName.REDIRECT)) {
			session.setAttribute(ParamName.PARAM_PREVIOUS_PAGE, session
							.getAttribute(ParamName.PARAM_CURRENT_PAGE));
			session.setAttribute(ParamName.PARAM_CURRENT_PAGE, path);
		}
		chain.doFilter(req, resp);
	}

	private String substringPathWithRegex(String url) {
		String path = null;
		if (url != null) {
			path = url.replace(PageName.LOCALHOST, "");
			if (url.contains(PageName.CONTROLLER) || url.contains(PageName.JSP_CONTROLLER)) {
				path = PageName.MAIN_PAGE;
			}
			if (url.contains(PageName.CONTROLLER_COMMAND)) {
				path = url;
			}
			if (url.contains(PageName.JSP)) {
				path = url.replace(PageName.JSP, "/");
			}
			if (url.contains(EMAIL_CONFIRMATION)) {
				path = PageName.CONFIRMATION_SUCCESSFUL_PAGE;
			}
			path = path.replace(PageName.LOCALHOST, "");
		} else {
			path = PageName.MAIN_PAGE;
		}
		return path;
	}
}

