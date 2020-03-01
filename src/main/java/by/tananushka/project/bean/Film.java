package by.tananushka.project.bean;

import java.util.Set;
import java.util.TreeSet;

/**
 * The type Film.
 */
public class Film extends Entity {

	private static final long serialVersionUID = 1L;
	private int id;
	private String title;
	private int age;
	private int year;
	private String img;
	private boolean active;
	private Set<String> countries;
	private Set<String> genres;

	/**
	 * Instantiates a new Film.
	 */
	public Film() {
		countries = new TreeSet<>();
		genres = new TreeSet<>();
	}

	/**
	 * Is active boolean.
	 *
	 * @return the boolean
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * Sets active.
	 *
	 * @param active the active
	 */
	public void setActive(boolean active) {
		this.active = active;
	}

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
	 * Gets title.
	 *
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets title.
	 *
	 * @param title the title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Gets age.
	 *
	 * @return the age
	 */
	public int getAge() {
		return age;
	}

	/**
	 * Sets age.
	 *
	 * @param age the age
	 */
	public void setAge(int age) {
		this.age = age;
	}

	/**
	 * Gets year.
	 *
	 * @return the year
	 */
	public int getYear() {
		return year;
	}

	/**
	 * Sets year.
	 *
	 * @param year the year
	 */
	public void setYear(int year) {
		this.year = year;
	}

	/**
	 * Gets img.
	 *
	 * @return the img
	 */
	public String getImg() {
		return img;
	}

	/**
	 * Sets img.
	 *
	 * @param img the img
	 */
	public void setImg(String img) {
		this.img = img;
	}

	/**
	 * Gets countries.
	 *
	 * @return the countries
	 */
	public Set<String> getCountries() {
		return countries;
	}

	/**
	 * Sets countries.
	 *
	 * @param countries the countries
	 */
	public void setCountries(Set<String> countries) {
		this.countries = countries;
	}

	/**
	 * Gets genres.
	 *
	 * @return the genres
	 */
	public Set<String> getGenres() {
		return genres;
	}

	/**
	 * Sets genres.
	 *
	 * @param genres the genres
	 */
	public void setGenres(Set<String> genres) {
		this.genres = genres;
	}

	/**
	 * Add country.
	 *
	 * @param country the country
	 */
	public void addCountry(String country) {
		countries.add(country);
	}

	/**
	 * Add genre.
	 *
	 * @param genre the genre
	 */
	public void addGenre(String genre) {
		genres.add(genre);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Film film = (Film) o;
		if (id != film.id) {
			return false;
		}
		if (age != film.age) {
			return false;
		}
		if (year != film.year) {
			return false;
		}
		if (active != film.active) {
			return false;
		}
		if (title != null ? !title.equals(film.title) : film.title != null) {
			return false;
		}
		if (img != null ? !img.equals(film.img) : film.img != null) {
			return false;
		}
		if (countries != null ? !countries.equals(film.countries) : film.countries != null) {
			return false;
		}
		return genres != null ? genres.equals(film.genres) : film.genres == null;
	}

	@Override
	public int hashCode() {
		int result = id;
		result = 31 * result + (title != null ? title.hashCode() : 0);
		result = 31 * result + age;
		result = 31 * result + year;
		result = 31 * result + (img != null ? img.hashCode() : 0);
		result = 31 * result + (active ? 1 : 0);
		result = 31 * result + (countries != null ? countries.hashCode() : 0);
		result = 31 * result + (genres != null ? genres.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("Film{");
		sb.append("id=").append(id);
		sb.append(", title='").append(title).append('\'');
		sb.append(", age=").append(age);
		sb.append(", year=").append(year);
		sb.append(", img='").append(img).append('\'');
		sb.append(", active=").append(active);
		sb.append(", countries=").append(countries);
		sb.append(", genres=").append(genres);
		sb.append('}');
		return sb.toString();
	}
}
