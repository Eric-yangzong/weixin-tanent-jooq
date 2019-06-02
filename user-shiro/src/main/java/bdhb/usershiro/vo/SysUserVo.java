package bdhb.usershiro.vo;

import javax.validation.constraints.NotNull;

public class SysUserVo {

	@NotNull
	private String tanentId;

	@NotNull
	private String userName;

	@NotNull
	private String password;

	public String getTanentId() {
		return tanentId;
	}

	public void setTanentId(String tanentId) {
		this.tanentId = tanentId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
