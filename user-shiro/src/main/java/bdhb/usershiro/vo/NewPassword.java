package bdhb.usershiro.vo;

import java.util.UUID;

import javax.validation.constraints.NotNull;

public class NewPassword {

	@NotNull
	private UUID userId;

	@NotNull
	private String userName;

	@NotNull
	private String newPassword;

	@NotNull
	private String oldPassword;

	public UUID getUserId() {
		return userId;
	}

	public void setUserId(UUID userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
}
