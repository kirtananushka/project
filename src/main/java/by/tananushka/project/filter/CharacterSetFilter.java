package by.tananushka.project.filter;

import by.tananushka.project.controller.ParamName;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter(urlPatterns = "/*", dispatcherTypes = {
				DispatcherType.FORWARD,
				DispatcherType.REQUEST
})
public class CharacterSetFilter implements Filter {

	private String encoding;

	@Override
	public void init(FilterConfig config) throws ServletException {
		encoding = config.getInitParameter(ParamName.PARAM_REQUEST_ENCODING);
		if (encoding == null) {
			encoding = ParamName.PARAM_UTF_8;
		}
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain next)
					throws IOException, ServletException {
		if (null == request.getCharacterEncoding()) {
			request.setCharacterEncoding(encoding);
		}
		response.setContentType(ParamName.PARAM_CONTENT_TYPE);
		response.setCharacterEncoding(ParamName.PARAM_UTF_8);
		next.doFilter(request, response);
	}
}
