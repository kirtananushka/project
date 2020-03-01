package by.tananushka.project.controller;

/**
 * The type Router.
 */
public class Router {

	private String pageToGo;
	private RouteType route = RouteType.FORWARD;

	/**
	 * Gets page to go.
	 *
	 * @return the page to go
	 */
	public String getPageToGo() {
		return pageToGo;
	}

	/**
	 * Sets page to go.
	 *
	 * @param pageToGo the page to go
	 */
	public void setPageToGo(String pageToGo) {
		this.pageToGo = pageToGo;
	}

	/**
	 * Gets route.
	 *
	 * @return the route
	 */
	public RouteType getRoute() {
		return route;
	}

	/**
	 * Sets route.
	 *
	 * @param route the route
	 */
	public void setRoute(RouteType route) {
		if (route == null) {
			this.route = RouteType.FORWARD;
		}
		this.route = route;
	}

	/**
	 * The enum Route type.
	 */
	public enum RouteType {
		/**
		 * Forward route type.
		 */
		FORWARD,
		/**
		 * Redirect route type.
		 */
		REDIRECT
	}
}