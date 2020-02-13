package by.tananushka.project.bean;

public class User extends Entity {

	private static final long serialVersionUID = 1L;
	// FIXME: 08.01.2020 eq hc tostr
	private int id;
	private String login;
	private String password;
	private UserRole role;
	private boolean isActive;

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

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("User{");
		sb.append("id=").append(id);
		sb.append(", login='").append(login).append('\'');
		sb.append(", password='").append(password).append('\'');
		sb.append(", role=").append(role);
		sb.append(", isActive=").append(isActive);
		sb.append('}');
		return sb.toString();
	}
}
