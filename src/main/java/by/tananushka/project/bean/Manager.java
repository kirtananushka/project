package by.tananushka.project.bean;

/**
 * The type Manager.
 */
public class Manager extends User {

	private static final long serialVersionUID = 1L;
	private String name;
	private String surname;
	private String phone;

	/**
	 * Instantiates a new Manager.
	 */
	public Manager() {
		setRole(UserRole.MANAGER);
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

	/**
	 * Gets phone.
	 *
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * Sets phone.
	 *
	 * @param phone the phone
	 */
	public void setPhone(String phone) {
		this.phone = phone;
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
		Manager manager = (Manager) o;
		if (name != null ? !name.equals(manager.name) : manager.name != null) {
			return false;
		}
		if (surname != null ? !surname.equals(manager.surname) : manager.surname != null) {
			return false;
		}
		return phone != null ? phone.equals(manager.phone) : manager.phone == null;
	}

	@Override
	public int hashCode() {
		int result = super.hashCode();
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (surname != null ? surname.hashCode() : 0);
		result = 31 * result + (phone != null ? phone.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("Manager{");
		sb.append("name='").append(name).append('\'');
		sb.append(", surname='").append(surname).append('\'');
		sb.append(", phone='").append(phone).append('\'');
		sb.append('}');
		return sb.toString();
	}
}
