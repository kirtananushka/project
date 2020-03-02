package by.tananushka.project.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Session content.
 */
public class SessionContent {

	private HashMap<String, Object> requestAttributes;
	private HashMap<String, Object> sessionAttributes;
	private HashMap<String, String[]> requestParameters;
	private HttpServletRequest request;
	private boolean invalidateSession;

	/**
	 * Instantiates a new Session content.
	 *
	 * @param request the request
	 */
	public SessionContent(HttpServletRequest request) {
		this.request = request;
		extractRequestValues(request);
	}

	private void extractRequestValues(HttpServletRequest request) {
		Enumeration<String> attributeNames = request.getAttributeNames();
		requestAttributes = new HashMap<>();
		for (String name : Collections.list(attributeNames)) {
			requestAttributes.put(name, request.getAttribute(name));
		}
		requestParameters = new HashMap<>(request.getParameterMap());
		HttpSession session = request.getSession(false);
		sessionAttributes = new HashMap<>();
		if (session != null) {
			Enumeration<String> sessionAttributesNames = session.getAttributeNames();
			for (String name : Collections.list(sessionAttributesNames)) {
				sessionAttributes.put(name, session.getAttribute(name));
			}
		}
	}

	/**
	 * Insert attributes.
	 *
	 * @param request the request
	 */
	void insertAttributes(HttpServletRequest request) {
		for (Map.Entry<String, Object> entry : requestAttributes.entrySet()) {
			String name = entry.getKey();
			Object o = entry.getValue();
			request.setAttribute(name, o);
		}
		HttpSession session = request.getSession(false);
		if (session != null) {
			for (Map.Entry<String, Object> entry : sessionAttributes.entrySet()) {
				String key = entry.getKey();
				Object value = entry.getValue();
				session.setAttribute(key, value);
			}
		}
		if (invalidateSession) {
			request.getSession().invalidate();
		}
	}

	/**
	 * Assign session attribute object.
	 *
	 * @param attributeName the attribute name
	 * @param object        the object
	 * @return the object
	 */
	public Object assignSessionAttribute(String attributeName, Object object) {
		Object updatedAttributes = sessionAttributes.put(attributeName, object);
		return updatedAttributes;
	}

	/**
	 * Assign request attribute object.
	 *
	 * @param attributeName the attribute name
	 * @param object        the object
	 * @return the object
	 */
	public Object assignRequestAttribute(String attributeName, Object object) {
		Object updatedAttributes = requestAttributes.put(attributeName, object);
		return updatedAttributes;
	}

	/**
	 * Gets session attribute.
	 *
	 * @param attributeName the attribute name
	 * @return the session attribute
	 */
	public Object getSessionAttribute(String attributeName) {
		Object attribute = sessionAttributes.get(attributeName);
		return attribute;
	}

	/**
	 * Gets request attribute.
	 *
	 * @param attributeName the attribute name
	 * @return the request attribute
	 */
	public Object getRequestAttribute(String attributeName) {
		Object attribute = requestAttributes.get(attributeName);
		return attribute;
	}

	/**
	 * Gets request parameter.
	 *
	 * @param parameterName the parameter name
	 * @return the request parameter
	 */
	public String getRequestParameter(String parameterName) {
		String[] parameters = requestParameters.get(parameterName);
		String parameter;
		if (parameters != null && parameters.length != 0) {
			parameter = parameters[0];
		} else {
			parameter = null;
		}
		return parameter;
	}

	/**
	 * Gets request.
	 *
	 * @return the request
	 */
	public HttpServletRequest getRequest() {
		return request;
	}

	/**
	 * Invalidate session.
	 */
	public void invalidateSession() {
		invalidateSession = true;
	}

	/**
	 * Get parameter values by name string [ ].
	 *
	 * @param paramName the param name
	 * @return the string [ ]
	 */
	public String[] getParameterValuesByName(String paramName) {
		return request.getParameterValues(paramName);
	}
}
