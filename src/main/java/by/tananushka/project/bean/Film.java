package by.tananushka.project.bean;

import java.util.Set;
import java.util.TreeSet;

public class Film extends Entity {

	private static final long serialVersionUID = 1L;
	private int id;
	private String title;
	private int age;
	private int year;
	private String img;
	private Set<String> countries;
	private Set<String> genres;

	public Film() {
		countries = new TreeSet<>();
		genres = new TreeSet<>();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public Set<String> getCountries() {
		return countries;
	}

	public void setCountries(Set<String> countries) {
		this.countries = countries;
	}

	public Set<String> getGenres() {
		return genres;
	}

	public void setGenres(Set<String> genres) {
		this.genres = genres;
	}

	public void addCountry(String country) {
		countries.add(country);
	}

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
		sb.append(", countries=").append(countries);
		sb.append(", genres=").append(genres);
		sb.append('}');
		return sb.toString();
	}
}
