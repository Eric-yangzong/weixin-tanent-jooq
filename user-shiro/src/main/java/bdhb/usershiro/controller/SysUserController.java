package bdhb.usershiro.controller;

import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.bdhanbang.base.common.ApiResult;
import com.bdhanbang.base.common.QueryPage;
import com.bdhanbang.base.common.QueryResults;
import com.bdhanbang.base.message.CommonMessage;
import com.generator.tables.SysUser;
import com.generator.tables.pojos.SysUserEntity;

import bdhb.usershiro.common.AppCommon;
import bdhb.usershiro.common.CurrentUser;
import bdhb.usershiro.service.SysUserService;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/sys_user")
public class SysUserController {

	@Autowired
	private SysUserService SysUserService;

	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public ApiResult<SysUserEntity> insert(@Valid @RequestBody SysUserEntity sysUserEntity,
			@ApiIgnore @CurrentUser SysUserEntity currentUser) {

		String realSchema = currentUser.getTanentId() + AppCommon.scheam;

		ApiResult<SysUserEntity> apiResult = new ApiResult<>();

		sysUserEntity.setUserId(UUID.randomUUID());// 设置系统的UUID

		SysUserService.insertEntity(realSchema, SysUser.class, sysUserEntity);

		apiResult.setData(sysUserEntity);

		apiResult.setStatus(CommonMessage.CREATE.getStatus());
		apiResult.setMessage(CommonMessage.CREATE.getMessage());

		return apiResult;

	}

	@RequestMapping(method = RequestMethod.PUT, produces = { "application/json;charset=UTF-8" })
	@ResponseStatus(HttpStatus.OK)
	public ApiResult<SysUserEntity> update(@RequestBody SysUserEntity sysUserEntity,
			@ApiIgnore @CurrentUser SysUserEntity currentUser) {

		String realSchema = currentUser.getTanentId() + AppCommon.scheam;

		ApiResult<SysUserEntity> apiResult = new ApiResult<>();

		SysUserService.updateEntity(realSchema, SysUser.class, sysUserEntity);

		apiResult.setData(sysUserEntity);

		apiResult.setStatus(CommonMessage.UPDATE.getStatus());
		apiResult.setMessage(CommonMessage.UPDATE.getMessage());

		return apiResult;

	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable("id") String id, @ApiIgnore @CurrentUser SysUserEntity currentUser) {
		String realSchema = currentUser.getTanentId() + AppCommon.scheam;
		SysUserService.deleteEntity(realSchema, SysUser.class, id);

	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = { "application/json;charset=UTF-8" })
	@ResponseStatus(HttpStatus.OK)
	public ApiResult<SysUserEntity> getEntity(@PathVariable("id") String id,
			@ApiIgnore @CurrentUser SysUserEntity currentUser) {

		String realSchema = currentUser.getTanentId() + AppCommon.scheam;
		ApiResult<SysUserEntity> apiResult = new ApiResult<>();

		SysUserEntity sysUserEntity = SysUserService.getEntity(realSchema, SysUser.class, SysUserEntity.class, id);

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
		String realSchema = currentUser.getTanentId() + AppCommon.scheam;

		ApiResult<QueryResults<SysUserEntity>> apiResult = new ApiResult<>();

		QueryResults<SysUserEntity> queryResults = SysUserService.queryPage(realSchema, SysUser.class,
				SysUserEntity.class, queryPage);

		apiResult.setData(queryResults);

		apiResult.setStatus(CommonMessage.SUCCESS.getStatus());
		apiResult.setMessage(CommonMessage.SUCCESS.getMessage());

		return apiResult;

	}

}
