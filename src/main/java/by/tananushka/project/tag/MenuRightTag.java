package by.tananushka.project.tag;

import by.tananushka.project.bean.User;
import by.tananushka.project.bean.UserRole;
import by.tananushka.project.controller.ParamName;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * The type Menu right tag.
 */
public class MenuRightTag extends TagSupport {

	private static final String LANGUAGE_EN = "en";
	private static final String LANGUAGE_RU = "ru";
	private static final String COUNTRY_EN = "EN";
	private static final String COUNTRY_RU = "RU";
	private static final String BASE_NAME = "bundle.pagecontent";
	private static final String VALUE_LOGIN = "header.login";
	private static final String VALUE_REGISTRATION = "header.registration";
	private static final String VALUE_LOGOUT = "logout";
	private static final String VALUE_PROFILE = "profile";
	private static final String VALUE_CLIENT = "client";
	private static final String VALUE_GUEST = "guest";
	private static final long serialVersionUID = 1L;

	@Override
	public int doStartTag() throws JspException {
		HttpSession session = pageContext.getSession();
		String currentLocale = (String) session.getAttribute(ParamName.PARAM_LOCALE);
		Locale pageLocale;
		if (ParamName.PARAM_EN_EN.equals(currentLocale)) {
			pageLocale = new Locale(LANGUAGE_EN, COUNTRY_EN);
		} else {
			pageLocale = new Locale(LANGUAGE_RU, COUNTRY_RU);
		}
		ResourceBundle resourceBundle = ResourceBundle.getBundle(BASE_NAME, pageLocale);
		String valLogin = resourceBundle.getString(VALUE_LOGIN);
		String valRegistration = resourceBundle.getString(VALUE_REGISTRATION);
		String valLogout = resourceBundle.getString(VALUE_LOGOUT);
		String valProfile = resourceBundle.getString(VALUE_PROFILE);
		String valClient = resourceBundle.getString(VALUE_CLIENT);
		String valGuest = resourceBundle.getString(VALUE_GUEST);
		User user = (User) session.getAttribute(ParamName.PARAM_USER_AUTHORIZATED);
		StringBuilder sb = new StringBuilder();
		sb.append("<div class=\"header-login\">\n");
		sb.append("<div class=\"header-user\">\n");
		if (user == null) {
			sb.append("<form class=\"header-user-form\" action=\"/authentication\" method=\"post\">\n");
			sb.append("<input type=\"submit\" value=\"");
			sb.append(valLogin);
			sb.append("\">\n");
			sb.append("</form>\n");
			sb.append("<form class=\"header-user-form\" action=\"/registration\" method=\"post\">\n");
			sb.append("<input type=\"submit\" value=\"");
			sb.append(valRegistration);
			sb.append("\">\n");
		} else {
			sb.append("<form class=\"header-user-form\">\n");
			sb.append("<input type=\"submit\" value=\"");
			sb.append(user.getLogin());
			sb.append("\" disabled>\n");
			sb.append("</form>\n");
			sb.append("<form class=\"header-user-form\" action=\"/controller\" method=\"post\">\n");
			sb.append("<input type=\"hidden\" name=\"command\" value=\"view_profile\">\n");
			sb.append("<input type=\"submit\" value=\"");
			sb.append(valProfile);
			sb.append("\">\n");
			sb.append("</form>\n");
			sb.append("<form class=\"header-user-form\" action=\"controller\" method=\"post\">\n");
			sb.append("<input type=\"hidden\" name=\"command\" value=\"logout\">\n");
			sb.append("<input type=\"submit\" value=\"");
			sb.append(valLogout);
			sb.append("\">\n");
		}
		sb.append("</form>\n");
		sb.append("</div>\n");
		sb.append("</div>\n");
		if (user != null) {
			UserRole role = user.getRole();
			if (role == UserRole.ADMIN) {
				sb.append("<div class=\"header-role\">\n<p><span class=\"red\">ADMIN</span></p>\n</div>\n");
			} else if (role == UserRole.MANAGER) {
				sb.append(
								"<div class=\"header-role\">\n<p><span class=\"yellow\">MANAGER</span></p>\n</div>\n");
			} else if (role == UserRole.CLIENT) {
				sb.append("<div class=\"header-role\">\n<p><span class=\"blue\">");
				sb.append(valClient.toUpperCase());
				sb.append("</span></p>\n</div>\n");
			}
		} else {
			sb.append("<div class=\"header-role\">\n<p><span class=\"white\">");
			sb.append(valGuest.toUpperCase());
			sb.append("</span></p>\n</div>\n");
		}
		try {
			JspWriter out = this.pageContext.getOut();
			out.write(sb.toString());
		} catch (IOException e) {
			throw new JspException(e.getMessage());
		}
		return SKIP_BODY;
	}

	@Override
	public int doEndTag() throws JspException {
		return EVAL_PAGE;
	}
}
