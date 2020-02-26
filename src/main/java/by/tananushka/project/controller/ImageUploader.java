package by.tananushka.project.controller;

import by.tananushka.project.service.validation.EscapeCharactersChanger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;

@WebServlet(urlPatterns = {"/upload"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024,
				maxFileSize = 1024 * 1024 * 5,
				maxRequestSize = 1024 * 1024 * 5 * 5)
public class ImageUploader extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final EscapeCharactersChanger changer = EscapeCharactersChanger.getInstance();
	private static final String IMAGE = "image";
	private static final String UPLOAD_DIR = "E:/IdeaProjects/projectfl/src/main/webapp/image/film/";
	private static final String PATH = "/image/film/";
	private static Logger log = LogManager.getLogger();

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
					throws ServletException, IOException {
		File saveDir = new File(UPLOAD_DIR);
		if (!saveDir.exists()) {
			saveDir.mkdirs();
		}
		String imageName = null;
		for (Part part : request.getParts()) {
			if (part.getSubmittedFileName() != null && part.getContentType().contains(IMAGE)) {
				part.write(UPLOAD_DIR + changer.deleteCharacters(part.getSubmittedFileName()));
				imageName = changer.deleteCharacters(part.getSubmittedFileName());
			}
		}
		request.getSession().setAttribute(ParamName.PARAM_IMG, PATH + imageName);
		request.getRequestDispatcher(PageName.SET_IMAGE_PAGE).forward(request, response);
	}
}
