package by.tananushka.project.service.impl;

import by.tananushka.project.bean.Client;
import by.tananushka.project.bean.Show;
import by.tananushka.project.bean.TicketOrder;
import by.tananushka.project.bean.User;
import by.tananushka.project.command.ErrorMessageKey;
import by.tananushka.project.controller.ParamName;
import by.tananushka.project.controller.SessionContent;
import by.tananushka.project.dao.DaoException;
import by.tananushka.project.dao.DaoProvider;
import by.tananushka.project.dao.OrderDao;
import by.tananushka.project.service.ClientService;
import by.tananushka.project.service.OrderService;
import by.tananushka.project.service.ServiceException;
import by.tananushka.project.service.ServiceProvider;
import by.tananushka.project.service.ShowService;
import by.tananushka.project.service.validation.FilmDataValidator;
import by.tananushka.project.service.validation.OrderDataValidator;
import by.tananushka.project.util.EmailSender;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

/**
 * The type Order service.
 */
public class OrderServiceImpl implements OrderService {

	private static final OrderService instance = new OrderServiceImpl();
	private static final FilmDataValidator filmValidator = FilmDataValidator.getInstance();
	private static final OrderDataValidator validator = OrderDataValidator.getInstance();
	private static Logger log = LogManager.getLogger();
	private OrderDao orderDao = DaoProvider.getInstance().getOrderDao();

	private OrderServiceImpl() {
	}

	/**
	 * Gets instance.
	 *
	 * @return the instance
	 */
	public static OrderService getInstance() {
		return instance;
	}

	@Override
	public Optional<TicketOrder> createOrder(SessionContent content) throws ServiceException {
		Optional<TicketOrder> orderOptional;
		ShowService showService = ServiceProvider.getInstance().getShowService();
		ClientService clientService = ServiceProvider.getInstance().getClientService();
		boolean isParameterValid = true;
		Map<String, String> errorsMap = new LinkedHashMap<>();
		content.assignSessionAttribute(ParamName.PARAM_ERR_CREATE_ORDER_MESSAGE, null);
		String strShowId = content.getRequestParameter(ParamName.PARAM_SHOW_ID).strip();
		if (!filmValidator.checkIdString(strShowId) || !filmValidator.checkId(strShowId)) {
			errorsMap.put(ErrorMessageKey.INVALID_ID, strShowId);
			isParameterValid = false;
		}
		User user = (User) content.getSessionAttribute(ParamName.PARAM_USER_AUTHORIZATED);
		int userId = user.getId();
		String strTicketsNumber = content.getRequestParameter(ParamName.PARAM_TICKETS_NUMBER);
		int ticketNumber = 0;
		if (filmValidator.checkNumber(strTicketsNumber)) {
			ticketNumber = Integer.parseInt(strTicketsNumber);
		} else {
			errorsMap.put(ErrorMessageKey.INVALID_TICKETS_NUMBER, strTicketsNumber);
			isParameterValid = false;
		}
		String cardNumber = content.getRequestParameter(ParamName.PARAM_CREDIT_CARD_NUMBER);
		if (!validator.checkCreditCard(cardNumber)) {
			errorsMap.put(ErrorMessageKey.INVALID_CARD_NUMBER, cardNumber);
			isParameterValid = false;
		}
		TicketOrder order = new TicketOrder();
		if (isParameterValid) {
			Optional<Client> clientOptional = clientService.findClientById(userId);
			clientOptional.ifPresent(order::setClient);
			Optional<Show> showOptional = showService.findShowById(strShowId);
			showOptional.ifPresent(order::setShow);
			order.setTicketsNumber(ticketNumber);
		} else {
			content.assignSessionAttribute(ParamName.PARAM_ERR_CREATE_ORDER_MESSAGE, errorsMap);
			throw new ServiceException("Invalid parameter(s)");
		}
		try {
			orderOptional = orderDao.createOrder(order);
			if (orderOptional.isPresent()) {
				order = orderOptional.get();
				content.assignSessionAttribute(ParamName.PARAM_ORDER_OBJ, order);
				EmailSender.sendTicket(content);
			}
		} catch (DaoException e) {
			log.error("Exception while creating order.", e);
			throw new ServiceException("Exception while creating order.", e);
		}
		return orderOptional;
	}

	@Override
	public Optional<TicketOrder> findOrderById(int id) throws ServiceException {
		try {
			orderDao.findOrderById(id);
		} catch (DaoException e) {
			log.error("Exception while getting order by ID.", e);
			throw new ServiceException("Exception while getting order by ID.", e);
		}
		return Optional.empty();
	}
}
