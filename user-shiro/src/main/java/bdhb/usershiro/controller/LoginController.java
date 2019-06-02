package bdhb.usershiro.controller;

import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

import com.bdhanbang.base.common.ApiResult;
import com.bdhanbang.base.common.Query;
import com.bdhanbang.base.exception.AuthenticationException;
import com.bdhanbang.base.message.CommonMessage;
import com.generator.tables.SysUser;
import com.generator.tables.pojos.SysUserEntity;

import bdhb.usershiro.common.AppCommon;
import bdhb.usershiro.common.CurrentUser;
import bdhb.usershiro.service.SysUserService;
import bdhb.usershiro.util.JwtUtils;
import bdhb.usershiro.vo.SysUserVo;

@RestController
@RequestMapping("/login")
public class LoginController {

	@Autowired
	SysUserService sysUserService;

	final Base64.Encoder encoder = Base64.getEncoder();

	@RequestMapping(method = RequestMethod.POST)
	public ApiResult<String> login(@Valid @RequestBody SysUserVo sysUserEntity, HttpServletResponse response) {

		String schema = String.format("%s%s", sysUserEntity.getTanentId(), AppCommon.scheam);

		ApiResult<String> apiResult = new ApiResult<>();

		Query query = new Query();

		query.add(new Query("userName", sysUserEntity.getUserName()));

		List<SysUserEntity> sysUserEntitys = sysUserService.queryList(schema, SysUser.class, SysUserEntity.class,
				query.getQuerys());

		if (Objects.isNull(sysUserEntitys) || sysUserEntitys.size() == 0) {
			throw new AuthenticationException("用户名或密码错误。");
		}

		SysUserEntity sysUser = sysUserEntitys.get(0);

		String inPassword = DigestUtils.md5Hex(sysUserEntity.getPassword() + sysUser.getSalt());

		if (inPassword.equals(sysUser.getPassword())) {
			apiResult.setData("登录成功");

			Map<String, String> claims = new HashMap<>();

			claims.put(AppCommon.PAYLOAD_NAME, sysUserEntity.getUserName());

			String token = JwtUtils.createToken(claims);
			response.setHeader(AppCommon.TOKEN, token);
			// 生成token
		} else {
			throw new AuthenticationException("用户名或密码错误。");
		}

		apiResult.setStatus(CommonMessage.SUCCESS.getStatus());
		apiResult.setMessage(CommonMessage.SUCCESS.getMessage());

		return apiResult;

	}

	// @Autowired
	// private MyLoginService myLoginService;
	//
	// @RequestMapping(value = "/{schema}", method = RequestMethod.POST)
	// @ResponseStatus(HttpStatus.CREATED)
	// public ApiResult<TMyLogin> insert(@PathVariable String schema, @Valid
	// @RequestBody TMyLogin logLogin) {
	//
	// ApiResult<TMyLogin> apiResult = new ApiResult<>();
	//
	// logLogin.setId(UUID.randomUUID());// 设置系统的UUID
	// logLogin.setLoginTime(LocalDateTime.now()); // 设置系统时间
	//
	// myLoginService.insertEntity(schema, QMyLogin.class, logLogin);
	//
	// apiResult.setData(logLogin);
	//
	// apiResult.setStatus(CommonMessage.CREATE.getStatus());
	// apiResult.setMessage(CommonMessage.CREATE.getMessage());
	//
	// return apiResult;
	//
	// }
	//
	// @RequestMapping(value = "/{schema}", method = RequestMethod.PUT, produces = {
	// "application/json;charset=UTF-8" })
	// @ResponseStatus(HttpStatus.OK)
	// public ApiResult<TMyLogin> update(@PathVariable String schema, @RequestBody
	// TMyLogin logLogin) {
	//
	// ApiResult<TMyLogin> apiResult = new ApiResult<>();
	//
	// logLogin.setLoginTime(LocalDateTime.now());
	// myLoginService.updateEntity(schema, QMyLogin.class, logLogin);
	//
	// apiResult.setData(logLogin);
	//
	// apiResult.setStatus(CommonMessage.UPDATE.getStatus());
	// apiResult.setMessage(CommonMessage.UPDATE.getMessage());
	//
	// return apiResult;
	//
	// }
	//
	// @RequestMapping(value = "/{schema}/{id}", method = RequestMethod.DELETE)
	// @ResponseStatus(HttpStatus.NO_CONTENT)
	// public void delete(@PathVariable String schema, @PathVariable("id") String
	// id) {
	//
	// myLoginService.deleteEntity(schema, QMyLogin.class, id);
	//
	// }
	//
	@RequestMapping(value = "/test", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(HttpStatus.OK)
	public ApiResult<String> query(@CurrentUser SysUserEntity userEntity) {

		ApiResult<String> apiResult = new ApiResult<>();

		System.out.println(userEntity.getUserName());

		apiResult.setData("OK");

		apiResult.setStatus(CommonMessage.SUCCESS.getStatus());
		apiResult.setMessage(CommonMessage.SUCCESS.getMessage());

		return apiResult;

	}

}
