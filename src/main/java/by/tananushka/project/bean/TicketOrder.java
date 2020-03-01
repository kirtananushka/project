package by.tananushka.project.bean;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * The type Ticket order.
 */
public class TicketOrder extends Entity {

	private static final long serialVersionUID = 1L;
	private int id;
	private Client client;
	private Show show;
	private BigDecimal ticketCost;
	private int ticketsNumber;
	private LocalDateTime orderingDate;

	/**
	 * Gets id.
	 *
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Sets id.
	 *
	 * @param id the id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Gets client.
	 *
	 * @return the client
	 */
	public Client getClient() {
		return client;
	}

	/**
	 * Sets client.
	 *
	 * @param client the client
	 */
	public void setClient(Client client) {
		this.client = client;
	}

	/**
	 * Gets show.
	 *
	 * @return the show
	 */
	public Show getShow() {
		return show;
	}

	/**
	 * Sets show.
	 *
	 * @param show the show
	 */
	public void setShow(Show show) {
		this.show = show;
	}

	/**
	 * Gets ticket cost.
	 *
	 * @return the ticket cost
	 */
	public BigDecimal getTicketCost() {
		return ticketCost;
	}

	/**
	 * Sets ticket cost.
	 *
	 * @param ticketCost the ticket cost
	 */
	public void setTicketCost(BigDecimal ticketCost) {
		this.ticketCost = ticketCost;
	}

	/**
	 * Gets tickets number.
	 *
	 * @return the tickets number
	 */
	public int getTicketsNumber() {
		return ticketsNumber;
	}

	/**
	 * Sets tickets number.
	 *
	 * @param ticketsNumber the tickets number
	 */
	public void setTicketsNumber(int ticketsNumber) {
		this.ticketsNumber = ticketsNumber;
	}

	/**
	 * Gets ordering date.
	 *
	 * @return the ordering date
	 */
	public LocalDateTime getOrderingDate() {
		return orderingDate;
	}

	/**
	 * Sets ordering date.
	 *
	 * @param orderingDate the ordering date
	 */
	public void setOrderingDate(LocalDateTime orderingDate) {
		this.orderingDate = orderingDate;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		TicketOrder order = (TicketOrder) o;
		if (id != order.id) {
			return false;
		}
		if (ticketsNumber != order.ticketsNumber) {
			return false;
		}
		if (client != null ? !client.equals(order.client) : order.client != null) {
			return false;
		}
		if (show != null ? !show.equals(order.show) : order.show != null) {
			return false;
		}
		if (ticketCost != null ? !ticketCost.equals(order.ticketCost) : order.ticketCost != null) {
			return false;
		}
		return orderingDate != null ? orderingDate.equals(order.orderingDate)
		                            : order.orderingDate == null;
	}

	@Override
	public int hashCode() {
		int result = id;
		result = 31 * result + (client != null ? client.hashCode() : 0);
		result = 31 * result + (show != null ? show.hashCode() : 0);
		result = 31 * result + (ticketCost != null ? ticketCost.hashCode() : 0);
		result = 31 * result + ticketsNumber;
		result = 31 * result + (orderingDate != null ? orderingDate.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("TicketOrder{");
		sb.append("id=").append(id);
		sb.append(", client=").append(client);
		sb.append(", show=").append(show);
		sb.append(", ticketCost=").append(ticketCost);
		sb.append(", ticketsNumber=").append(ticketsNumber);
		sb.append(", orderingDate=").append(orderingDate);
		sb.append('}');
		return sb.toString();
	}
}
