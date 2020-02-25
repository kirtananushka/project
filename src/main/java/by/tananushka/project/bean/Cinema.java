package by.tananushka.project.bean;

import java.util.ArrayList;
import java.util.List;

@Deprecated
public class Cinema extends Entity {

	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private String address;
	private List<Show> showsList;

	public Cinema() {
		showsList = new ArrayList<>();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<Show> getShowsList() {
		return showsList;
	}

	public void setShowsList(List<Show> showsList) {
		this.showsList = showsList;
	}

	public void addShow(Show show) {
		showsList.add(show);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Cinema cinema = (Cinema) o;
		if (id != cinema.id) {
			return false;
		}
		if (name != null ? !name.equals(cinema.name) : cinema.name != null) {
			return false;
		}
		if (address != null ? !address.equals(cinema.address) : cinema.address != null) {
			return false;
		}
		return showsList != null ? showsList.equals(cinema.showsList) : cinema.showsList == null;
	}

	@Override
	public int hashCode() {
		int result = id;
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (address != null ? address.hashCode() : 0);
		result = 31 * result + (showsList != null ? showsList.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("Cinema{");
		sb.append("id=").append(id);
		sb.append(", name='").append(name).append('\'');
		sb.append(", address='").append(address).append('\'');
		sb.append(", showsList=").append(showsList);
		sb.append('}');
		return sb.toString();
	}
}
