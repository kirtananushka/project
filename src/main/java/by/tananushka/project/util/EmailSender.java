package by.tananushka.project.util;

import by.tananushka.project.bean.Client;
import by.tananushka.project.bean.TicketOrder;
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
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.Properties;

/**
 * The type Email sender.
 */
public class EmailSender {

	private static final String DECIMAL_PATTERN = "###.00";
	private static Logger log = LogManager.getLogger();
	private static EmailPropertiesReader propertiesReader = EmailPropertiesReader.getInstance();
	private static Properties properties = propertiesReader.readProperties();

	private EmailSender() {
	}

	/**
	 * Send confirmation.
	 *
	 * @param client the client
	 */
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

	/**
	 * Send message.
	 *
	 * @param content the content
	 */
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

	/**
	 * Send new password.
	 *
	 * @param email       the email
	 * @param newPassword the new password
	 */
	public static void sendNewPassword(String email, String newPassword) {
		String username = properties.getProperty(ParamName.PARAM_MAIL_USERNAME);
		String password = properties.getProperty(ParamName.PARAM_MAIL_PASS);
		String subject = properties.getProperty(ParamName.PARAM_SEND_NEW_PASS_SUBJECT);
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

	/**
	 * Send ticket.
	 *
	 * @param content the content
	 */
	public static void sendTicket(SessionContent content) {
		String username = properties.getProperty(ParamName.PARAM_MAIL_USERNAME);
		String usernameForMsg = properties.getProperty(ParamName.PARAM_MAIL_USERNAME_MSG);
		String password = properties.getProperty(ParamName.PARAM_MAIL_PASS);
		String subject = properties.getProperty(ParamName.PARAM_TICKET_MESSAGE_SUBJECT);
		String text = formTicketText(content);
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
			log.error("Exception while sending ticket", e);
		}
		log.info("Ticket was sent");
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
		String message = properties.getProperty(ParamName.PARAM_SEND_NEW_PASS_TEXT);
		String messageText = String.format(message, password);
		return messageText;
	}

	private static String formTicketText(SessionContent content) {
		String message = properties.getProperty(ParamName.PARAM_TICKET_MESSAGE_TEXT);
		TicketOrder order = (TicketOrder) content.getSessionAttribute(ParamName.PARAM_ORDER_OBJ);
		int orderNumber = order.getId();
		LocalDateTime orderDate = order.getOrderingDate();
		LocalDateTime showDate = order.getShow().getDateTime();
		String cinemaName = order.getShow().getCinemaName();
		String filmTitle = order.getShow().getFilm().getTitle();
		int ticketsNumber = order.getTicketsNumber();
		BigDecimal totalCost = order.getTicketCost().multiply(new BigDecimal(ticketsNumber));
		DecimalFormat df = new DecimalFormat(DECIMAL_PATTERN);
		String strTotalCost = df.format(totalCost);
		String messageText = String.format(message, orderNumber, orderDate, orderDate, orderDate,
						orderDate, orderDate, showDate, showDate, showDate, showDate, showDate, showDate,
						cinemaName, filmTitle, ticketsNumber, strTotalCost);
		return messageText;
	}
}
