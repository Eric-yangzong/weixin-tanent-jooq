package bdhb.usershiro.controller;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
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
import com.bdhanbang.base.common.Order;
import com.bdhanbang.base.common.QueryPage;
import com.bdhanbang.base.common.QueryResults;
import com.bdhanbang.base.message.CommonMessage;
import com.generator.tables.SysPermission;
import com.generator.tables.pojos.SysPermissionEntity;
import com.generator.tables.pojos.SysUserEntity;

import bdhb.usershiro.common.AppCommon;
import bdhb.usershiro.common.CurrentUser;
import bdhb.usershiro.service.SysPermissionService;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/sys_permission")
public class SysPermissionController {

	@Autowired
	private SysPermissionService SysUserService;

	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public ApiResult<SysPermissionEntity> insert(@Valid @RequestBody SysPermissionEntity sysPermissionEntity,
			@ApiIgnore @CurrentUser SysUserEntity currentUser) {

		String realSchema = currentUser.getTenantId() + AppCommon.scheam;

		ApiResult<SysPermissionEntity> apiResult = new ApiResult<>();

		sysPermissionEntity.setId(UUID.randomUUID());// 设置系统的UUID

		SysUserService.insertEntity(realSchema, SysPermission.class, sysPermissionEntity);

		sysPermissionEntity.setUpdateFullName(currentUser.getFullName());
		sysPermissionEntity.setUpdateTime(OffsetDateTime.now());

		apiResult.setData(sysPermissionEntity);

		apiResult.setStatus(CommonMessage.CREATE.getStatus());
		apiResult.setMessage(CommonMessage.CREATE.getMessage());

		return apiResult;

	}

	@RequestMapping(method = RequestMethod.PUT, produces = { "application/json;charset=UTF-8" })
	@ResponseStatus(HttpStatus.OK)
	public ApiResult<SysPermissionEntity> update(@RequestBody SysPermissionEntity sysPermissionEntity,
			@ApiIgnore @CurrentUser SysUserEntity currentUser) {

		String realSchema = currentUser.getTenantId() + AppCommon.scheam;

		ApiResult<SysPermissionEntity> apiResult = new ApiResult<>();

		sysPermissionEntity.setUpdateFullName(currentUser.getFullName());
		sysPermissionEntity.setUpdateTime(OffsetDateTime.now());

		SysUserService.updateEntity(realSchema, SysPermission.class, sysPermissionEntity);

		apiResult.setData(sysPermissionEntity);

		apiResult.setStatus(CommonMessage.UPDATE.getStatus());
		apiResult.setMessage(CommonMessage.UPDATE.getMessage());

		return apiResult;

	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable("id") String id, @ApiIgnore @CurrentUser SysUserEntity currentUser) {
		String realSchema = currentUser.getTenantId() + AppCommon.scheam;
		SysUserService.deleteEntity(realSchema, SysPermission.class, id);

	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = { "application/json;charset=UTF-8" })
	@ResponseStatus(HttpStatus.OK)
	public ApiResult<SysPermissionEntity> getEntity(@PathVariable("id") String id,
			@ApiIgnore @CurrentUser SysUserEntity currentUser) {

		String realSchema = currentUser.getTenantId() + AppCommon.scheam;
		ApiResult<SysPermissionEntity> apiResult = new ApiResult<>();

		SysPermissionEntity sysPermissionEntity = SysUserService.getEntity(realSchema, SysPermission.class,
				SysPermissionEntity.class, id);

		apiResult.setData(sysPermissionEntity);

		apiResult.setStatus(CommonMessage.SUCCESS.getStatus());
		apiResult.setMessage(CommonMessage.SUCCESS.getMessage());

		return apiResult;

	}

	@RequestMapping(method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public ApiResult<QueryResults<SysPermissionEntity>> query(@RequestParam("queryPage") QueryPage queryPage,
			@ApiIgnore @CurrentUser SysUserEntity currentUser) {
		String realSchema = currentUser.getTenantId() + AppCommon.scheam;

		ApiResult<QueryResults<SysPermissionEntity>> apiResult = new ApiResult<>();

		List<Order> orders = new ArrayList<>();

		orders.add(new Order("qsort"));

		queryPage.setOrders(orders);

		QueryResults<SysPermissionEntity> queryResults = SysUserService.queryPage(realSchema, SysPermission.class,
				SysPermissionEntity.class, queryPage);

		apiResult.setData(queryResults);

		apiResult.setStatus(CommonMessage.SUCCESS.getStatus());
		apiResult.setMessage(CommonMessage.SUCCESS.getMessage());

		return apiResult;

	}

}
