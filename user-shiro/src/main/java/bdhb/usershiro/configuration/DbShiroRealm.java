package bdhb.usershiro.configuration;

import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import com.generator.tables.pojos.SysUserEntity;

import bdhb.usershiro.service.SysPermissionService;
import bdhb.usershiro.service.SysUserService;

public class DbShiroRealm extends AuthorizingRealm {


	private SysUserService userService;

	private SysPermissionService sysPermissionService;

	public DbShiroRealm(SysUserService userService) {
		this.userService = userService;
		HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
		// md5加密1次
		hashedCredentialsMatcher.setHashAlgorithmName("md5");
		hashedCredentialsMatcher.setHashIterations(1);
		this.setCredentialsMatcher(hashedCredentialsMatcher);
	}

	@Override
	public boolean supports(AuthenticationToken token) {
		return token instanceof UsernamePasswordToken;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken userpasswordToken = (UsernamePasswordToken) token;
		String username = userpasswordToken.getUsername();
		SysUserEntity user = userService.getUserInfo(username);
		if (user == null)
			throw new AuthenticationException("用户名或者密码错误");

		return new SimpleAuthenticationInfo(user, user.getPassword(), ByteSource.Util.bytes(user.getSalt()), "dbRealm");
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
		SysUserEntity user = (SysUserEntity) principals.getPrimaryPrincipal();
		List<String> roles = sysPermissionService.queryUserPermission(user.getTanentId(), user.getUserId());

		if (roles != null)
			simpleAuthorizationInfo.addRoles(roles);

		return simpleAuthorizationInfo;
	}

}