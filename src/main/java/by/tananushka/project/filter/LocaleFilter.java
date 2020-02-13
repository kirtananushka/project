package by.tananushka.project.filter;

import by.tananushka.project.controller.ParamName;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = {"/jsp/*"}, dispatcherTypes = {DispatcherType.FORWARD,
                                                        DispatcherType.INCLUDE,
                                                        DispatcherType.REQUEST})
//
//@WebFilter(urlPatterns = {"/jsp/*", "/command*"}, dispatcherTypes = {DispatcherType.FORWARD,
//                                                        DispatcherType.INCLUDE,
//                                                        DispatcherType.REQUEST})
public class LocaleFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
	                     FilterChain next) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpSession session = httpRequest.getSession(false);
		if (session != null && session.getAttribute(ParamName.PARAM_LOCALE) == null) {
			session.setAttribute(ParamName.PARAM_LOCALE, ParamName.PARAM_RU_RU);
		}
		next.doFilter(httpRequest, response);
	}
}