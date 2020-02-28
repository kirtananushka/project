package by.tananushka.project.util;

import by.tananushka.project.bean.Client;
import by.tananushka.project.controller.ParamName;
import by.tananushka.project.controller.SessionContent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailSender {

	private static Logger log = LogManager.getLogger();
	private static EmailPropertiesReader propertiesReader = EmailPropertiesReader.getInstance();
	private static Properties properties = propertiesReader.readProperties();

	private EmailSender() {
	}

	public static void sendConfirmation(Client client) {
		String username = properties.getProperty(ParamName.PARAM_MAIL_USERNAME);
		String password = properties.getProperty(ParamName.PARAM_MAIL_PASS);
		String subject = properties.getProperty(ParamName.PARAM_MAIL_CONFIRMATION_SUBJECT);
		String text = formConfirmationText(client);
		Session session = Session.getDefaultInstance(properties, new Authenticator() {
			@Override
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(client.getEmail()));
			message.setSubject(subject);
			message.setContent(text, ParamName.PARAM_CONTENT_TYPE);
			Transport.send(message);
		} catch (MessagingException e) {
			log.error("Exception while sending email", e);
		}
	}

	public static void sendMessage(SessionContent content) {
		String username = properties.getProperty(ParamName.PARAM_MAIL_USERNAME);
		String usernameForMsg = properties.getProperty(ParamName.PARAM_MAIL_USERNAME_MSG);
		String password = properties.getProperty(ParamName.PARAM_MAIL_PASS);
		String subject = properties.getProperty(ParamName.PARAM_MAIL_MESSAGE_SUBJECT);
		String text = formMessageText(content);
		Session session = Session.getDefaultInstance(properties, new Authenticator() {
			@Override
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(usernameForMsg));
			message.setSubject(subject);
			message.setContent(text, ParamName.PARAM_CONTENT_TYPE);
			Transport.send(message);
		} catch (MessagingException e) {
			log.error("Exception while sending message", e);
		}
		log.info("New message was sent");
	}

	public static void sendNewPassword(String email, String newPassword) {
		String username = properties.getProperty(ParamName.PARAM_MAIL_USERNAME);
		String password = properties.getProperty(ParamName.PARAM_MAIL_PASS);
		String subject = properties.getProperty(ParamName.PARAM_SEND_NEW_PASSWORD_SUBJECT);
		String text = formNewPasswordText(newPassword);
		Session session = Session.getDefaultInstance(properties, new Authenticator() {
			@Override
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
			message.setSubject(subject);
			message.setContent(text, ParamName.PARAM_CONTENT_TYPE);
			Transport.send(message);
		} catch (MessagingException e) {
			log.error("Exception while sending new password", e);
		}
	}

	private static String formMessageText(SessionContent content) {
		String message1 = properties.getProperty(ParamName.PARAM_MSG_TEXT_1);
		String message2 = properties.getProperty(ParamName.PARAM_MSG_TEXT_2);
		String message3 = properties.getProperty(ParamName.PARAM_MSG_TEXT_3);
		String name = content.getRequestParameter(ParamName.PARAM_MSG_NAME);
		String surname = content.getRequestParameter(ParamName.PARAM_MSG_SURNAME);
		String email = content.getRequestParameter(ParamName.PARAM_MSG_EMAIL);
		String phone = content.getRequestParameter(ParamName.PARAM_MSG_PHONE);
		String text = content.getRequestParameter(ParamName.PARAM_MSG_MESSAGE);
		StringBuilder messageText = new StringBuilder();
		messageText.append(String.format(message1, name, surname));
		messageText.append(String.format(message2, email, phone));
		messageText.append(String.format(message3, text));
		return messageText.toString();
	}

	private static String formConfirmationText(Client client) {
		String message1 = properties.getProperty(ParamName.PARAM_CONFIRMATION_TEXT_1);
		String message2 = properties.getProperty(ParamName.PARAM_CONFIRMATION_TEXT_2);
		String message3 = properties.getProperty(ParamName.PARAM_CONFIRMATION_TEXT_3);
		String message4 = properties.getProperty(ParamName.PARAM_CONFIRMATION_TEXT_4);
		StringBuilder messageText = new StringBuilder();
		messageText.append(String.format(message1, client.getName(), client.getSurname()));
		messageText.append(message2);
		messageText.append(String.format(message3, client.getLogin()));
		messageText.append(String.format(message4, client.getId()));
		return messageText.toString();
	}

	private static String formNewPasswordText(String password) {
		String message = properties.getProperty(ParamName.PARAM_SEND_NEW_PASSWORD_TEXT);
		String messageText = String.format(message, password);
		return messageText;
	}
}
