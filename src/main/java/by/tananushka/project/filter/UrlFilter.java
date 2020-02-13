//package by.tananushka.project.filter;
//
//import javax.servlet.Filter;
//import javax.servlet.FilterChain;
//import javax.servlet.FilterConfig;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.http.HttpServletRequest;
//import java.io.IOException;
//import java.io.UnsupportedEncodingException;
//import java.net.URLEncoder;
//
//@WebFilter(urlPatterns = {"/*"})
//public class UrlFilter implements Filter {
////
////	public static String encodeUrl(String string) throws UnsupportedEncodingException {
////		if (string == null) {
////			string = "";
////		}
////		return URLEncoder.encode(string, "UTF-8");
////	}
////
////	public static String getRequestUrl(HttpServletRequest request) {
////		String parameters = request.getQueryString();
////		String uri = (String) request.getAttribute("javax.servlet.forward.request_uri");
////		if (uri == null) {
////			uri = request.getRequestURI();
////		}
////		return uri + (parameters == null ? "" : ("?" + parameters));
////	}
////
////	@Override
////	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
////		servletRequest.setAttribute("fullUrl",
////						encodeUrl(getRequestUrl((HttpServletRequest) servletRequest))
////		);
////		filterChain.doFilter(servletRequest, servletResponse);
////	}
//}