package by.tananushka.project.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SessionContent {

	private HashMap<String, Object> requestAttributes;
	private HashMap<String, Object> sessionAttributes;
	private HashMap<String, String[]> requestParameters;
	private HttpServletRequest request;
	private boolean invalidateSession;

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
		if (Objects.nonNull(session)) {
			Enumeration<String> sessionAttributesNames = session.getAttributeNames();
			for (String name : Collections.list(sessionAttributesNames)) {
				sessionAttributes.put(name, session.getAttribute(name));
			}
		}
	}

	void insertAttributes(HttpServletRequest request) {
		for (Map.Entry<String, Object> entry : requestAttributes.entrySet()) {
			String name = entry.getKey();
			Object o = entry.getValue();
			request.setAttribute(name, o);
		}
		HttpSession session = request.getSession(false);
		if (Objects.nonNull(session)) {
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

	public Object assignSessionAttribute(String attributeName, Object object) {
		Object updatedAttributes = sessionAttributes.put(attributeName, object);
		return updatedAttributes;
	}

	public Object assignRequestAttribute(String attributeName, Object object) {
		Object updatedAttributes = requestAttributes.put(attributeName, object);
		return updatedAttributes;
	}

	public Object getSessionAttribute(String attributeName) {
		Object attribute = sessionAttributes.get(attributeName);
		return attribute;
	}

	public Object getRequestAttribute(String attributeName) {
		Object attribute = requestAttributes.get(attributeName);
		return attribute;
	}

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

	public HttpServletRequest getRequest() {
		return request;
	}

	public void invalidateSession() {
		invalidateSession = true;
	}

	public String[] getParameterValuesByName(String paramName) {
		return request.getParameterValues(paramName);
	}
}
