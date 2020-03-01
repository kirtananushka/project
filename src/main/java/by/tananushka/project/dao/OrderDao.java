package by.tananushka.project.dao;

import by.tananushka.project.bean.TicketOrder;

import java.util.Optional;

/**
 * The interface Order dao.
 */
public interface OrderDao extends AbstractDao {

	/**
	 * Create order optional.
	 *
	 * @param order the order
	 * @return the optional
	 * @throws DaoException the dao exception
	 */
	Optional<TicketOrder> createOrder(TicketOrder order) throws DaoException;

	/**
	 * Find order by id optional.
	 *
	 * @param orderId the order id
	 * @return the optional
	 * @throws DaoException the dao exception
	 */
	Optional<TicketOrder> findOrderById(int orderId) throws DaoException;
}
