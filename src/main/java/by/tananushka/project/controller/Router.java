package by.tananushka.project.controller;

public class Router {

	private String pageToGo;
	private RouteType route = RouteType.FORWARD;

	public String getPageToGo() {
		return pageToGo;
	}

	public void setPageToGo(String pageToGo) {
		this.pageToGo = pageToGo;
	}

	public RouteType getRoute() {
		return route;
	}

	public void setRoute(RouteType route) {
		if (route == null) {
			this.route = RouteType.FORWARD;
		}
		this.route = route;
	}

	public enum RouteType {
		FORWARD, REDIRECT
	}
}