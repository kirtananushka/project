package by.tananushka.project.bean;

public class Manager extends User {

	private static final long serialVersionUID = 1L;
	private String name;
	private String surname;
	private String phone;
	private String email;

	public Manager() {
		setRole(UserRole.MANAGER);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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
		if (phone != null ? !phone.equals(manager.phone) : manager.phone != null) {
			return false;
		}
		return email != null ? email.equals(manager.email) : manager.email == null;
	}

	@Override
	public int hashCode() {
		int result = super.hashCode();
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (surname != null ? surname.hashCode() : 0);
		result = 31 * result + (phone != null ? phone.hashCode() : 0);
		result = 31 * result + (email != null ? email.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("Manager{");
		sb.append("name='").append(name).append('\'');
		sb.append(", surname='").append(surname).append('\'');
		sb.append(", phone='").append(phone).append('\'');
		sb.append(", email='").append(email).append('\'');
		sb.append('}');
		return sb.toString();
	}
}
