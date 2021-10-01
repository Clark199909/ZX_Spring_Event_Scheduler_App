package app.user;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ResetUser {
	
	@NotNull(message = "is required")
	@Size(min = 1, message = "is required")
	private String userNameOrEmail;
	
	public ResetUser() {}

	public String getUserNameOrEmail() {
		return userNameOrEmail;
	}

	public void setUserNameOrEmail(String userNameOrEmail) {
		this.userNameOrEmail = userNameOrEmail;
	}
	
	
}
