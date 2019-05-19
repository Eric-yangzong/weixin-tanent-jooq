package bdhb.usershiro.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.bdhanbang.base.common.Query;
import com.bdhanbang.base.service.impl.BaseServiceImpl;
import com.generator.tables.SysUser;
import com.generator.tables.pojos.SysUserEntity;

import bdhb.usershiro.configuration.JwtUtils;
import bdhb.usershiro.service.SysUserService;

/**
 * 用户信息接口
 */
@Service
public class SysUserServiceImpl extends BaseServiceImpl<SysUser, SysUserEntity> implements SysUserService {

	@Autowired
	private StringRedisTemplate redisTemplate;

	/**
	 * 保存user登录信息，返回token
	 * 
	 * @param userDto
	 */
	public String generateJwtToken(String username) {
		String salt = JwtUtils.generateSalt();

		// 将salt保存到数据库或者缓存中
		redisTemplate.opsForValue().set("token:" + username, salt, 3600, TimeUnit.SECONDS);

		return JwtUtils.sign(username, salt, 3600); // 生成jwt token，设置过期时间为1小时
	}

	/**
	 * 获取上次token生成时的salt值和登录用户信息
	 * 
	 * @param username
	 * @return
	 */
	public SysUserEntity getJwtTokenInfo(String username) {
		/**
		 * @todo 从数据库或者缓存中取出jwt token生成时用的salt
		 */
		String salt = redisTemplate.opsForValue().get("token:" + username);

		SysUserEntity user = getUserInfo(username);
		user.setSalt(salt);
		return user;
	}

	/**
	 * 清除token信息
	 * 
	 * @param userName
	 *            登录用户名
	 * @param terminal
	 *            登录终端
	 */
	public void deleteLoginInfo(String username) {
		/**
		 * @todo 删除数据库或者缓存中保存的salt
		 */

		redisTemplate.delete("token:" + username);

	}

	/**
	 * 获取数据库中保存的用户信息，主要是加密后的密码
	 * 
	 * @param userName
	 * @return
	 */
	public SysUserEntity getUserInfo(String userName) {

		Query query = new Query();

		query.add(new Query("userName", userName));

		List<SysUserEntity> userList = this.queryList("tat0004_mod_login", SysUser.class, SysUserEntity.class,
				query.getQuerys());

		if (Objects.isNull(userList) || userList.size() == 0) {
			return null;
		} else {
			return userList.get(0);
		}

	}

}
