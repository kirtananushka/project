package by.tananushka.project.service;

import by.tananushka.project.bean.TicketOrder;
import by.tananushka.project.controller.SessionContent;

import java.util.List;
import java.util.Optional;

/**
 * The interface Order service.
 */
public interface OrderService {

	/**
	 * Create order optional.
	 *
	 * @param content the content
	 * @return the optional
	 * @throws ServiceException the service exception
	 */
	Optional<TicketOrder> createOrder(SessionContent content) throws ServiceException;

	/**
	 * Find order by id optional.
	 *
	 * @param id the id
	 * @return the optional
	 * @throws ServiceException the service exception
	 */
	Optional<TicketOrder> findOrderById(int id) throws ServiceException;

	/**
	 * Find orders by client id list.
	 *
	 * @param content the content
	 * @return the list
	 * @throws ServiceException the service exception
	 */
	List<TicketOrder> findOrdersByClientId(SessionContent content) throws ServiceException;

	/**
	 * Find orders by client id list.
	 *
	 * @param content     the content
	 * @param strClientId the str client id
	 * @return the list
	 * @throws ServiceException the service exception
	 */
	List<TicketOrder> findOrdersByClientId(SessionContent content, String strClientId)
					throws ServiceException;

	/**
	 * Find all orders list.
	 *
	 * @return the list
	 * @throws ServiceException the service exception
	 */
	List<TicketOrder> findAllOrders() throws ServiceException;
}
