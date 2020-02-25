//package by.tananushka.project.filter;
//
//import by.tananushka.project.controller.PageName;
//import by.tananushka.project.controller.ParamName;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//
//import javax.servlet.Filter;
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.annotation.WebListener;
//import javax.servlet.http.HttpServletRequest;
//import java.io.IOException;
//
//@WebFilter(urlPatterns = {"/user/admin/*", "/user/client/*", "/user/manager/*"})
//
//public class AccessFilter implements Filter {
//
//	private static Logger log = LogManager.getLogger();
//
//
//	@Override
//	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//					throws IOException, ServletException {
//		HttpServletRequest httpRequest = (HttpServletRequest) request;
//		String userRole = ((String) httpRequest.getSession().getAttribute(ParamName.PARAM_ROLE));
//		if (userRole != null) {
//			userRole = userRole.toLowerCase();
//			String currentPage = httpRequest.getRequestURL().toString();
//			log.debug("Access filter: {}", currentPage);
//			if (currentPage.contains("/" + userRole + "/")) {
//				chain.doFilter(request, response);
//			} else {
//				httpRequest.getRequestDispatcher(PageName.ACCESS_DENIED_PAGE).forward(request, response);
//			}
//		} else {
//			httpRequest.getRequestDispatcher(PageName.ACCESS_DENIED_PAGE).forward(request, response);
//		}
//	}
//}