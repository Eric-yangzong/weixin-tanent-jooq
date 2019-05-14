package bdhb.usershiro.configuration;

import java.util.List;
import java.util.Objects;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.bdhanbang.base.common.Query;
import com.generator.tables.SysUser;
import com.generator.tables.pojos.SysUserEntity;

import bdhb.usershiro.service.SysPermissionService;
import bdhb.usershiro.service.SysUserService;

public class UserRealm extends AuthorizingRealm {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserRealm.class);

	@Autowired
	private SysUserService sysUserService;

	@Autowired
	private SysPermissionService sysPermissionService;

	/**
	 * 授权
	 *
	 * @param principals
	 * @return
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		SysUserEntity sysUser = (SysUserEntity) principals.getPrimaryPrincipal();

		List<String> sysPermissions = sysPermissionService.queryUserPermission(sysUser.getTanentId(),
				sysUser.getUserId());

		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.addStringPermissions(sysPermissions);
		LOGGER.info("doGetAuthorizationInfo");
		return info;
	}

	/**
	 * 认证
	 *
	 * @param authenticationToken
	 * @return
	 * @throws AuthenticationException
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
			throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;

		Query query = new Query();
		query.getQuerys().add(new Query("userName", token.getUsername()));
		List<SysUserEntity> sysUsers = sysUserService.queryList("tat0004_mod_login", SysUser.class, SysUserEntity.class,
				query.getQuerys());

		if (Objects.isNull(sysUsers) || sysUsers.size() == 0) {
			return null;
		}
		SysUserEntity sysUserEntity = sysUsers.get(0);

		LOGGER.info("doGetAuthenticationInfo");
		return new SimpleAuthenticationInfo(sysUserEntity, sysUserEntity.getPassword().toCharArray(),
				ByteSource.Util.bytes(sysUserEntity.getSalt()), getName());
	}
}