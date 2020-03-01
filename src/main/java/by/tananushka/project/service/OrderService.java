package by.tananushka.project.service;

import by.tananushka.project.bean.TicketOrder;
import by.tananushka.project.controller.SessionContent;

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
}
