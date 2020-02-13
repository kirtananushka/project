package by.tananushka.project.controller;

import by.tananushka.project.command.Command;
import by.tananushka.project.command.CommandException;
import by.tananushka.project.command.CommandProvider;
import by.tananushka.project.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/controller")
public class Controller extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final CommandProvider commandProvider = CommandProvider.getInstance();
	private static Logger log = LogManager.getLogger();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
					throws ServletException, IOException {
		doProcess(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
					throws ServletException, IOException {
		doProcess(request, response);
	}

	private void doProcess(HttpServletRequest request, HttpServletResponse response)
					throws ServletException, IOException {
		SessionContent content = new SessionContent(request);
		String commandName = request.getParameter(ParamName.PARAM_COMMAND);
		log.info(commandName); // FIXME: 20.01.2020
		Command command = commandProvider.getCommand(commandName);
		String pageToGo;
		Router router;
		try {
			router = command.execute(content);
			pageToGo = router.getPageToGo();
			content.insertAttributes(request);
			switch (router.getRoute()) {
				case FORWARD:
					request.getRequestDispatcher(pageToGo).forward(request, response);
					break;
				case REDIRECT:
					response.sendRedirect(pageToGo);
					break;
				default:
					log.error("Unknown route");
					throw new CommandException();
			}
		} catch (CommandException e) {
			log.error("The command can't be executed", e);
			request.setAttribute(ParamName.PARAM_ERROR, e); // FIXME: 25.01.2020
			request.getRequestDispatcher(JspPageName.ERROR_PAGE).forward(request, response);
		}
	}

	@Override
	public void destroy() {
		ConnectionPool.getInstance().clearConnectionPool();
	}
}
