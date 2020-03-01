package by.tananushka.project.bean;

/**
 * The type Admin.
 */
public class Admin extends User {

	private static final long serialVersionUID = 1L;
	private String name;
	private String surname;

	/**
	 * Instantiates a new Admin.
	 */
	public Admin() {
		setRole(UserRole.ADMIN);
	}

	/**
	 * Gets name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets name.
	 *
	 * @param name the name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets surname.
	 *
	 * @return the surname
	 */
	public String getSurname() {
		return surname;
	}

	/**
	 * Sets surname.
	 *
	 * @param surname the surname
	 */
	public void setSurname(String surname) {
		this.surname = surname;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		if (!super.equals(o)) {
			return false;
		}
		Admin admin = (Admin) o;
		if (name != null ? !name.equals(admin.name) : admin.name != null) {
			return false;
		}
		return surname != null ? surname.equals(admin.surname) : admin.surname == null;
	}

	@Override
	public int hashCode() {
		int result = super.hashCode();
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (surname != null ? surname.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("Admin{");
		sb.append("name='").append(name).append('\'');
		sb.append(", surname='").append(surname).append('\'');
		sb.append('}');
		return sb.toString();
	}
}
