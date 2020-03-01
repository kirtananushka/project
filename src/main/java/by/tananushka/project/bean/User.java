package by.tananushka.project.bean;

import java.time.LocalDateTime;

/**
 * The type User.
 */
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

	/**
	 * Instantiates a new User.
	 */
	public User() {
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
	 * Gets login.
	 *
	 * @return the login
	 */
	public String getLogin() {
		return login;
	}

	/**
	 * Sets login.
	 *
	 * @param login the login
	 */
	public void setLogin(String login) {
		this.login = login;
	}

	/**
	 * Gets password.
	 *
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets password.
	 *
	 * @param password the password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Gets email.
	 *
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets email.
	 *
	 * @param email the email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Gets role.
	 *
	 * @return the role
	 */
	public UserRole getRole() {
		return role;
	}

	/**
	 * Sets role.
	 *
	 * @param role the role
	 */
	public void setRole(UserRole role) {
		this.role = role;
	}

	/**
	 * Is active boolean.
	 *
	 * @return the boolean
	 */
	public boolean isActive() {
		return isActive;
	}

	/**
	 * Sets active.
	 *
	 * @param active the active
	 */
	public void setActive(boolean active) {
		isActive = active;
	}

	/**
	 * Is verified boolean.
	 *
	 * @return the boolean
	 */
	public boolean isVerified() {
		return isVerified;
	}

	/**
	 * Sets verified.
	 *
	 * @param verified the verified
	 */
	public void setVerified(boolean verified) {
		isVerified = verified;
	}

	/**
	 * Gets registration date.
	 *
	 * @return the registration date
	 */
	public LocalDateTime getRegistrationDate() {
		return registrationDate;
	}

	/**
	 * Sets registration date.
	 *
	 * @param registrationDate the registration date
	 */
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
