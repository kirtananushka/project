package by.tananushka.project.bean;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Show extends Entity {

	private static final long serialVersionUID = 1L;
	private int id;
	private Film film;
	private String cinemaName;
	private LocalDateTime dateTime;
	private BigDecimal cost;
	private int freePlace;

	public Show() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Film getFilm() {
		return film;
	}

	public void setFilm(Film film) {
		this.film = film;
	}

	public String getCinemaName() {
		return cinemaName;
	}

	public void setCinemaName(String cinemaName) {
		this.cinemaName = cinemaName;
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}

	public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	public int getFreePlace() {
		return freePlace;
	}

	public void setFreePlace(int freePlace) {
		this.freePlace = freePlace;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Show show = (Show) o;
		if (id != show.id) {
			return false;
		}
		if (freePlace != show.freePlace) {
			return false;
		}
		if (film != null ? !film.equals(show.film) : show.film != null) {
			return false;
		}
		if (cinemaName != null ? !cinemaName.equals(show.cinemaName) : show.cinemaName != null) {
			return false;
		}
		if (dateTime != null ? !dateTime.equals(show.dateTime) : show.dateTime != null) {
			return false;
		}
		return cost != null ? cost.equals(show.cost) : show.cost == null;
	}

	@Override
	public int hashCode() {
		int result = id;
		result = 31 * result + (film != null ? film.hashCode() : 0);
		result = 31 * result + (cinemaName != null ? cinemaName.hashCode() : 0);
		result = 31 * result + (dateTime != null ? dateTime.hashCode() : 0);
		result = 31 * result + (cost != null ? cost.hashCode() : 0);
		result = 31 * result + freePlace;
		return result;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("Show{");
		sb.append("id=").append(id);
		sb.append(", film=").append(film);
		sb.append(", cinema='").append(cinemaName).append('\'');
		sb.append(", dateTime=").append(dateTime);
		sb.append(", cost=").append(cost);
		sb.append(", freePlace=").append(freePlace);
		sb.append('}');
		return sb.toString();
	}
}
