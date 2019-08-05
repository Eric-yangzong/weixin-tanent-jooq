package bdhb.usershiro.controller;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import javax.validation.Valid;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.bdhanbang.base.common.ApiResult;
import com.bdhanbang.base.common.Query;
import com.bdhanbang.base.common.QueryPage;
import com.bdhanbang.base.common.QueryResults;
import com.bdhanbang.base.exception.BusinessException;
import com.bdhanbang.base.message.CommonMessage;
import com.generator.tables.SysUser;
import com.generator.tables.pojos.SysUserEntity;

import bdhb.usershiro.common.AppCommon;
import bdhb.usershiro.common.CurrentUser;
import bdhb.usershiro.service.SysUserService;
import bdhb.usershiro.vo.NewPassword;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/sys_user")
public class SysUserController {

	@Autowired
	private SysUserService sysUserService;

	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public ApiResult<SysUserEntity> insert(@Valid @RequestBody SysUserEntity sysUserEntity,
			@ApiIgnore @CurrentUser SysUserEntity currentUser) {

		String realSchema = currentUser.getTenantId() + AppCommon.scheam;

		ApiResult<SysUserEntity> apiResult = new ApiResult<>();

		Query query = new Query();

		query.add("userName", sysUserEntity.getUserName());

		List<SysUserEntity> queryList = sysUserService.queryList(realSchema, SysUser.class, SysUserEntity.class,
				query.getQuerys());

		if (!Objects.isNull(queryList) && queryList.size() > 0) {
			throw new BusinessException("20000", String.format("【%s】用户名已存在。", sysUserEntity.getUserName()));
		}

		sysUserEntity.setUserId(UUID.randomUUID());// 设置系统的UUID
		sysUserEntity.setUpdateFullName(currentUser.getFullName());
		sysUserEntity.setUpdateTime(OffsetDateTime.now());
		sysUserEntity.setTenantId(currentUser.getTenantId());
		sysUserEntity.setPassword(AppCommon.DEFAULT_PASSWORD);
		sysUserEntity.setSalt(String.valueOf(((Double) (Math.random() * 100)).intValue()));
		String inPassword = DigestUtils.md5Hex(sysUserEntity.getPassword() + sysUserEntity.getSalt());
		sysUserEntity.setPassword(inPassword);

		sysUserService.insertEntity(realSchema, SysUser.class, sysUserEntity);

		sysUserEntity.setPassword("");
		sysUserEntity.setSalt("");

		apiResult.setData(sysUserEntity);

		apiResult.setStatus(CommonMessage.CREATE.getStatus());
		apiResult.setMessage(CommonMessage.CREATE.getMessage());

		return apiResult;

	}

	@RequestMapping(method = RequestMethod.PUT, produces = { "application/json;charset=UTF-8" })
	@ResponseStatus(HttpStatus.OK)
	public ApiResult<SysUserEntity> update(@RequestBody SysUserEntity sysUserEntity,
			@ApiIgnore @CurrentUser SysUserEntity currentUser) {

		String realSchema = currentUser.getTenantId() + AppCommon.scheam;

		ApiResult<SysUserEntity> apiResult = new ApiResult<>();

		Query query = new Query();

		query.add("userName", sysUserEntity.getUserName());

		List<SysUserEntity> queryList = sysUserService.queryList(realSchema, SysUser.class, SysUserEntity.class,
				query.getQuerys());

		if (!Objects.isNull(queryList) && queryList.size() > 0) {
			queryList.forEach(x -> {
				if (!sysUserEntity.getUserId().equals(x.getUserId())) {
					throw new BusinessException("20000", String.format("【%s】用户名已存在。", sysUserEntity.getUserName()));
				}
			});
		}

		SysUserEntity entity = sysUserService.getEntity(realSchema, SysUser.class, SysUserEntity.class,
				sysUserEntity.getUserId());

		if (Objects.isNull(sysUserEntity)) {
			sysUserEntity.setTenantId(currentUser.getTenantId());
		}

		sysUserEntity.setTenantId(currentUser.getTenantId());
		sysUserEntity.setUpdateFullName(currentUser.getFullName());
		sysUserEntity.setUpdateTime(OffsetDateTime.now());
		sysUserEntity.setPassword(entity.getPassword());
		sysUserEntity.setSalt(entity.getSalt());
		sysUserEntity.setOpenId(entity.getOpenId());

		sysUserService.updateEntity(realSchema, SysUser.class, sysUserEntity);

		apiResult.setData(sysUserEntity);

		apiResult.setStatus(CommonMessage.UPDATE.getStatus());
		apiResult.setMessage(CommonMessage.UPDATE.getMessage());

		return apiResult;

	}

	@RequestMapping(value = "/password", method = RequestMethod.PUT, produces = { "application/json;charset=UTF-8" })
	@ResponseStatus(HttpStatus.OK)
	public ApiResult<NewPassword> updatePassword(@RequestBody NewPassword newPassword,
			@ApiIgnore @CurrentUser SysUserEntity currentUser) {

		String realSchema = currentUser.getTenantId() + AppCommon.scheam;

		ApiResult<NewPassword> apiResult = new ApiResult<>();

		SysUserEntity sysUserEntity = sysUserService.getEntity(realSchema, SysUser.class, SysUserEntity.class,
				newPassword.getUserId());

		if (sysUserEntity.getPassword()
				.equals(DigestUtils.md5Hex(newPassword.getOldPassword() + sysUserEntity.getSalt()))) {

			sysUserEntity.setUpdateFullName(currentUser.getFullName());
			sysUserEntity.setUpdateTime(OffsetDateTime.now());
			sysUserEntity.setPassword(newPassword.getNewPassword());
			sysUserEntity.setSalt(String.valueOf(((Double) (Math.random() * 100)).intValue()));
			String inPassword = DigestUtils.md5Hex(sysUserEntity.getPassword() + sysUserEntity.getSalt());
			sysUserEntity.setPassword(inPassword);

			sysUserService.updateEntity(realSchema, SysUser.class, sysUserEntity);
		} else {
			throw new BusinessException("20000", "原密码不正确。");
		}

		apiResult.setData(newPassword);

		apiResult.setStatus(CommonMessage.UPDATE.getStatus());
		apiResult.setMessage(CommonMessage.UPDATE.getMessage());

		return apiResult;

	}

	@RequestMapping(value = "/reset/{id}", method = RequestMethod.PUT, produces = { "application/json;charset=UTF-8" })
	@ResponseStatus(HttpStatus.OK)
	public ApiResult<String> resetPassword(@PathVariable("id") String id,
			@ApiIgnore @CurrentUser SysUserEntity currentUser) {

		String realSchema = currentUser.getTenantId() + AppCommon.scheam;

		ApiResult<String> apiResult = new ApiResult<>();

		SysUserEntity sysUserEntity = sysUserService.getEntity(realSchema, SysUser.class, SysUserEntity.class, id);

		sysUserEntity.setSalt(String.valueOf(((Double) (Math.random() * 100)).intValue()));
		String inPassword = DigestUtils.md5Hex(AppCommon.DEFAULT_PASSWORD + sysUserEntity.getSalt());
		sysUserEntity.setPassword(inPassword);

		sysUserService.updateEntity(realSchema, SysUser.class, sysUserEntity);

		apiResult.setData("密码重置成功！");

		apiResult.setStatus(CommonMessage.UPDATE.getStatus());
		apiResult.setMessage(CommonMessage.UPDATE.getMessage());

		return apiResult;

	}

	@RequestMapping(value = "wx", method = RequestMethod.PUT, produces = { "application/json;charset=UTF-8" })
	@ResponseStatus(HttpStatus.OK)
	@Transactional
	public ApiResult<SysUserEntity> updateWx(@RequestBody SysUserEntity sysUserEntity,
			@ApiIgnore @CurrentUser SysUserEntity currentUser) {

		String realSchema = currentUser.getTenantId() + AppCommon.scheam;

		ApiResult<SysUserEntity> apiResult = new ApiResult<>();

		// 说明是微信更新
		if (currentUser.getUserName().equals(currentUser.getOpenId())) {
			Query query = new Query();

			query.add("userName", sysUserEntity.getUserName());

			List<SysUserEntity> queryList = sysUserService.queryList(realSchema, SysUser.class, SysUserEntity.class,
					query.getQuerys());

			if (!Objects.isNull(queryList) && queryList.size() == 1) {
				SysUserEntity sysUserEntityAdmin = queryList.get(0);

				currentUser.setRoles(sysUserEntityAdmin.getRoles());
				currentUser.setUserName(sysUserEntity.getUserName());
				currentUser.setPhone(sysUserEntity.getPhone());

				// 更新当前用户信息
				sysUserService.updateEntity(realSchema, SysUser.class, currentUser);

				if (!sysUserEntityAdmin.getUserId().equals(currentUser.getUserId())) {
					// 并删除管理员录入的信息
					sysUserService.deleteEntity(realSchema, SysUser.class, sysUserEntityAdmin.getUserId());
					apiResult.setMessage("请重新登录小程序以获管理员分配的角色权限!");
				} else {
					apiResult.setMessage("请联系管理员并提供账号信息，以便分配角色，待角色分配后，重新登录小程序!");
				}

			} else {

				currentUser.setUserName(sysUserEntity.getUserName());
				currentUser.setPhone(sysUserEntity.getPhone());
				sysUserService.updateEntity(realSchema, SysUser.class, currentUser);

				apiResult.setMessage("请联系管理员并提供账号信息，以便分配角色，待角色分配后，重新登录小程序!");
			}
		} else {
			apiResult.setMessage("请联系管理员并提供账号信息，以便分配角色，待角色分配后，重新登录小程序!");
		}

		// 数据脱敏
		currentUser.setPassword("");
		currentUser.setSalt("");

		apiResult.setData(currentUser);

		apiResult.setStatus(CommonMessage.UPDATE.getStatus());

		return apiResult;

	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable("id") String id, @ApiIgnore @CurrentUser SysUserEntity currentUser) {
		String realSchema = currentUser.getTenantId() + AppCommon.scheam;
		sysUserService.deleteEntity(realSchema, SysUser.class, id);

	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = { "application/json;charset=UTF-8" })
	@ResponseStatus(HttpStatus.OK)
	public ApiResult<SysUserEntity> getEntity(@PathVariable("id") String id,
			@ApiIgnore @CurrentUser SysUserEntity currentUser) {

		String realSchema = currentUser.getTenantId() + AppCommon.scheam;
		ApiResult<SysUserEntity> apiResult = new ApiResult<>();

		SysUserEntity sysUserEntity = sysUserService.getEntity(realSchema, SysUser.class, SysUserEntity.class, id);

		sysUserEntity.setSalt("");
		sysUserEntity.setPassword("");

		apiResult.setData(sysUserEntity);

		apiResult.setStatus(CommonMessage.SUCCESS.getStatus());
		apiResult.setMessage(CommonMessage.SUCCESS.getMessage());

		return apiResult;

	}

	@RequestMapping(value = "wx/{openId}", method = RequestMethod.GET, produces = { "application/json;charset=UTF-8" })
	@ResponseStatus(HttpStatus.OK)
	public ApiResult<SysUserEntity> getWxEntity(@PathVariable("openId") String openId,
			@ApiIgnore @CurrentUser SysUserEntity currentUser) {

		String realSchema = currentUser.getTenantId() + AppCommon.scheam;
		ApiResult<SysUserEntity> apiResult = new ApiResult<>();

		Query query = new Query();
		query.add("openId", openId);

		List<SysUserEntity> queryList = sysUserService.queryList(realSchema, SysUser.class, SysUserEntity.class,
				query.getQuerys());

		SysUserEntity sysUserEntity = new SysUserEntity();

		if (!Objects.isNull(queryList) && queryList.size() > 0) {
			sysUserEntity = queryList.get(0);
		}

		sysUserEntity.setSalt("");
		sysUserEntity.setPassword("");

		apiResult.setData(sysUserEntity);

		apiResult.setStatus(CommonMessage.SUCCESS.getStatus());
		apiResult.setMessage(CommonMessage.SUCCESS.getMessage());

		return apiResult;

	}

	@RequestMapping(method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public ApiResult<QueryResults<SysUserEntity>> query(@RequestParam("queryPage") QueryPage queryPage,
			@ApiIgnore @CurrentUser SysUserEntity currentUser) {
		String realSchema = currentUser.getTenantId() + AppCommon.scheam;

		ApiResult<QueryResults<SysUserEntity>> apiResult = new ApiResult<>();

		QueryResults<SysUserEntity> queryResults = sysUserService.queryPage(realSchema, SysUser.class,
				SysUserEntity.class, queryPage);

		queryResults.getResults().forEach(x -> {
			x.setPassword("");
			x.setSalt("");
		});

		apiResult.setData(queryResults);

		apiResult.setStatus(CommonMessage.SUCCESS.getStatus());
		apiResult.setMessage(CommonMessage.SUCCESS.getMessage());

		return apiResult;

	}

}
