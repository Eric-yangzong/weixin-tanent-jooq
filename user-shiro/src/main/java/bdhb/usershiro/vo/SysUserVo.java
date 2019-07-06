package bdhb.usershiro.vo;

import javax.validation.constraints.NotNull;

public class SysUserVo {

	@NotNull
	private String tenantId;

	@NotNull
	private String userName;

	@NotNull
	private String password;

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
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
