package by.tananushka.project.controller;

import by.tananushka.project.service.ClientService;
import by.tananushka.project.service.ServiceException;
import by.tananushka.project.service.ServiceProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "LoginChecker", urlPatterns = {"/LoginChecker"})
public class AjaxController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static Logger log = LogManager.getLogger();

	public void processRequest(HttpServletRequest request, HttpServletResponse response) {
		log.debug("AjaxController");
		String login = request.getParameter(ParamName.PARAM_LOGIN);
		ServiceProvider serviceProvider = ServiceProvider.getInstance();
		ClientService clientService = serviceProvider.getClientService();
		response.setContentType(ParamName.PARAM_APP_JSON);
		response.setCharacterEncoding(ParamName.PARAM_UTF_8);
		try (PrintWriter out = response.getWriter()) {
			JSONObject jsonEnt = new JSONObject();
			jsonEnt.put(ParamName.PARAM_LOGIN_INFO, clientService.checkLogin(login));
			out.print(jsonEnt.toString());
		} catch (ServiceException | IOException e) {
			log.error("Service exception", e);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		try {
			processRequest(request, response);
		} catch (JSONException e) {
			log.error("JSON Exception", e);
		}
	}
}
