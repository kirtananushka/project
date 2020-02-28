package by.tananushka.project.bean;

import java.time.LocalDateTime;

public class User extends Entity {

	private static final long serialVersionUID = 1L;
	private int id;
	private String login;
	private String password;
	private String email;
	private UserRole role;
	private boolean isActive;
	private boolean isVerified;
	private LocalDateTime registrationDate;

	public User() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean active) {
		isActive = active;
	}

	public boolean isVerified() {
		return isVerified;
	}

	public void setVerified(boolean verified) {
		isVerified = verified;
	}

	public LocalDateTime getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(LocalDateTime registrationDate) {
		this.registrationDate = registrationDate;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		User user = (User) o;
		if (id != user.id) {
			return false;
		}
		if (isActive != user.isActive) {
			return false;
		}
		if (isVerified != user.isVerified) {
			return false;
		}
		if (login != null ? !login.equals(user.login) : user.login != null) {
			return false;
		}
		if (password != null ? !password.equals(user.password) : user.password != null) {
			return false;
		}
		if (email != null ? !email.equals(user.email) : user.email != null) {
			return false;
		}
		if (role != user.role) {
			return false;
		}
		return registrationDate != null ? registrationDate.equals(user.registrationDate)
		                                : user.registrationDate == null;
	}

	@Override
	public int hashCode() {
		int result = id;
		result = 31 * result + (login != null ? login.hashCode() : 0);
		result = 31 * result + (password != null ? password.hashCode() : 0);
		result = 31 * result + (email != null ? email.hashCode() : 0);
		result = 31 * result + (role != null ? role.hashCode() : 0);
		result = 31 * result + (isActive ? 1 : 0);
		result = 31 * result + (isVerified ? 1 : 0);
		result = 31 * result + (registrationDate != null ? registrationDate.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("User{");
		sb.append("id=").append(id);
		sb.append(", login='").append(login).append('\'');
		sb.append(", password='").append(password).append('\'');
		sb.append(", email='").append(email).append('\'');
		sb.append(", role=").append(role);
		sb.append(", isActive=").append(isActive);
		sb.append(", isVerified=").append(isVerified);
		sb.append(", registrationDate=").append(registrationDate);
		sb.append('}');
		return sb.toString();
	}
}
