package com.bdhanbang.weixin.configuration;

import com.bdhanbang.base.common.Query;
import com.bdhanbang.weixin.jooq.tables.QSysPermission;
import com.bdhanbang.weixin.jooq.tables.QSysUser;
import com.bdhanbang.weixin.service.SysPermissionService;
import com.bdhanbang.weixin.service.SysUserService;
import com.generator.tables.pojos.SysPermission;
import com.generator.tables.pojos.SysUser;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
		SysUser sysUser = (SysUser) principals.getPrimaryPrincipal();

		Query query = new Query();

		// query.add(new Query("",sysUser.getUserId()));

		List<String> sysPermissions = sysPermissionService
				.queryList("tat0004_mod_login", QSysPermission.class, SysPermission.class, query.getQuerys()).stream()
				.map(x -> x.getId().toString()).collect(Collectors.toList());

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
		List<SysUser> sysUsers = sysUserService.queryList("tat0004_mod_login", QSysUser.class, SysUser.class,
				query.getQuerys());
		
		if (Objects.isNull(sysUsers) || sysUsers.size() == 0) {
			return null;
		}
		SysUser sysUser = sysUsers.get(0);
		
		LOGGER.info("doGetAuthenticationInfo");
		return new SimpleAuthenticationInfo(sysUser, sysUser.getPassword().toCharArray(),
				ByteSource.Util.bytes(sysUser.getSalt()), getName());
	}
}