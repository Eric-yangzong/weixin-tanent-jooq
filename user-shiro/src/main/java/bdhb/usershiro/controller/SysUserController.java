package bdhb.usershiro.controller;

import java.util.UUID;

import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
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

import bdhb.usershiro.configuration.AppCommon;
import bdhb.usershiro.service.TableService;

@RestController
@RequestMapping("/sys_user")
public class SysUserController {

	@Autowired
	private TableService tableService;

	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public ApiResult<SysUserEntity> insert(@RequestHeader(AppCommon.TENANT_ID) String tanentId,
			@Valid @RequestBody SysUserEntity SysUserEntity) {
		String realSchema = tanentId + AppCommon.scheam;

		ApiResult<SysUserEntity> apiResult = new ApiResult<>();

		SysUserEntity.setUserId(UUID.randomUUID());// 设置系统的UUID

		tableService.insertEntity(realSchema, SysUser.class, SysUserEntity);

		apiResult.setData(SysUserEntity);

		apiResult.setStatus(CommonMessage.CREATE.getStatus());
		apiResult.setMessage(CommonMessage.CREATE.getMessage());

		return apiResult;

	}

	@RequestMapping(method = RequestMethod.PUT, produces = { "application/json;charset=UTF-8" })
	@ResponseStatus(HttpStatus.OK)
	public ApiResult<SysUserEntity> update(@RequestHeader(AppCommon.TENANT_ID) String tanentId,
			@RequestBody SysUserEntity SysUserEntity) {

		String realSchema = tanentId + AppCommon.scheam;
		ApiResult<SysUserEntity> apiResult = new ApiResult<>();

		tableService.updateEntity(realSchema, SysUser.class, SysUserEntity);

		apiResult.setData(SysUserEntity);

		apiResult.setStatus(CommonMessage.UPDATE.getStatus());
		apiResult.setMessage(CommonMessage.UPDATE.getMessage());

		return apiResult;

	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@RequestHeader(AppCommon.TENANT_ID) String tanentId, @PathVariable("id") String id) {
		String realSchema = tanentId + AppCommon.scheam;
		tableService.deleteEntity(realSchema, SysUser.class, id);

	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = { "application/json;charset=UTF-8" })
	@ResponseStatus(HttpStatus.OK)
	public ApiResult<SysUserEntity> getEntity(@RequestHeader(AppCommon.TENANT_ID) String tanentId,
			@PathVariable("id") String id) {

		String realSchema = tanentId + AppCommon.scheam;
		ApiResult<SysUserEntity> apiResult = new ApiResult<>();

		SysUserEntity SysUserEntity = tableService.getEntity(realSchema, SysUser.class, SysUserEntity.class, id);

		apiResult.setData(SysUserEntity);

		apiResult.setStatus(CommonMessage.UPDATE.getStatus());
		apiResult.setMessage(CommonMessage.UPDATE.getMessage());

		return apiResult;

	}

	@RequestMapping(method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(HttpStatus.OK)
	public ApiResult<QueryResults<SysUserEntity>> query(@RequestHeader(AppCommon.TENANT_ID) String tanentId,
			@RequestParam("queryPage") QueryPage queryPage) {
		String realSchema = tanentId + AppCommon.scheam;

		ApiResult<QueryResults<SysUserEntity>> apiResult = new ApiResult<>();

		QueryResults<SysUserEntity> queryResults = tableService.queryPage(realSchema, SysUser.class,
				SysUserEntity.class, queryPage);

		apiResult.setData(queryResults);

		apiResult.setStatus(CommonMessage.SUCCESS.getStatus());
		apiResult.setMessage(CommonMessage.SUCCESS.getMessage());

		return apiResult;

	}

}
