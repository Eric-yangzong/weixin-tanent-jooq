package bdhb.usershiro.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
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
import com.bdhanbang.base.common.Query;
import com.bdhanbang.base.common.QueryPage;
import com.bdhanbang.base.common.QueryResults;
import com.bdhanbang.base.exception.BusinessException;
import com.bdhanbang.base.message.CommonMessage;
import com.bdhanbang.base.util.query.Operate;
import com.generator.tables.SysRole;
import com.generator.tables.pojos.SysRoleEntity;
import com.generator.tables.pojos.SysUserEntity;

import bdhb.usershiro.common.AppCommon;
import bdhb.usershiro.common.CurrentUser;
import bdhb.usershiro.service.TableService;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/sys_role")
public class SysRoleController {

	@Autowired
	private TableService sysRoleService;

	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public ApiResult<SysRoleEntity> insert(@Valid @RequestBody SysRoleEntity sysRoleEntity,
			@ApiIgnore @CurrentUser SysUserEntity currentUser) {

		String realSchema = currentUser.getTenantId() + AppCommon.scheam;

		ApiResult<SysRoleEntity> apiResult = new ApiResult<>();

		Query query = new Query();

		query.add("roleCode", sysRoleEntity.getRoleCode());

		List<SysRoleEntity> queryList = sysRoleService.queryList(realSchema, SysRole.class, SysRoleEntity.class, query);

		if (!Objects.isNull(queryList) && queryList.size() > 0) {
			throw new BusinessException("20000", String.format("【%s】角色编码已存在。", sysRoleEntity.getRoleCode()));
		}

		sysRoleEntity.setRoleId(UUID.randomUUID());// 设置系统的UUID
		sysRoleEntity.setUpdateFullName(currentUser.getFullName());
		sysRoleEntity.setUpdateTime(LocalDateTime.now());

		sysRoleService.insertEntity(realSchema, SysRole.class, sysRoleEntity);

		apiResult.setData(sysRoleEntity);

		apiResult.setStatus(CommonMessage.CREATE.getStatus());
		apiResult.setMessage(CommonMessage.CREATE.getMessage());

		return apiResult;

	}

	@RequestMapping(method = RequestMethod.PUT, produces = { "application/json;charset=UTF-8" })
	@ResponseStatus(HttpStatus.OK)
	public ApiResult<SysRoleEntity> update(@RequestBody SysRoleEntity sysRoleEntity,
			@ApiIgnore @CurrentUser SysUserEntity currentUser) {

		String realSchema = currentUser.getTenantId() + AppCommon.scheam;

		ApiResult<SysRoleEntity> apiResult = new ApiResult<>();

		Query query = new Query();

		query.add("roleCode", sysRoleEntity.getRoleCode());
		query.add("roleId", sysRoleEntity.getRoleId(), Operate.notEquals);

		List<SysRoleEntity> queryList = sysRoleService.queryList(realSchema, SysRole.class, SysRoleEntity.class, query);

		if (!Objects.isNull(queryList) && queryList.size() > 0) {
			throw new BusinessException("20000", String.format("【%s】角色编码已存在。", sysRoleEntity.getRoleCode()));
		}

		sysRoleEntity.setUpdateFullName(currentUser.getFullName());
		sysRoleEntity.setUpdateTime(LocalDateTime.now());

		sysRoleService.updateEntity(realSchema, SysRole.class, sysRoleEntity);

		apiResult.setData(sysRoleEntity);

		apiResult.setStatus(CommonMessage.UPDATE.getStatus());
		apiResult.setMessage(CommonMessage.UPDATE.getMessage());

		return apiResult;

	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable("id") String id, @ApiIgnore @CurrentUser SysUserEntity currentUser) {
		String realSchema = currentUser.getTenantId() + AppCommon.scheam;
		sysRoleService.deleteEntity(realSchema, SysRole.class, id);

	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = { "application/json;charset=UTF-8" })
	@ResponseStatus(HttpStatus.OK)
	public ApiResult<SysRoleEntity> getEntity(@PathVariable("id") String id,
			@ApiIgnore @CurrentUser SysUserEntity currentUser) {

		String realSchema = currentUser.getTenantId() + AppCommon.scheam;
		ApiResult<SysRoleEntity> apiResult = new ApiResult<>();

		SysRoleEntity sysRoleEntity = sysRoleService.getEntity(realSchema, SysRole.class, SysRoleEntity.class, id);

		apiResult.setData(sysRoleEntity);

		apiResult.setStatus(CommonMessage.SUCCESS.getStatus());
		apiResult.setMessage(CommonMessage.SUCCESS.getMessage());

		return apiResult;

	}

	@RequestMapping(method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public ApiResult<QueryResults<SysRoleEntity>> query(@RequestParam("queryPage") QueryPage queryPage,
			@ApiIgnore @CurrentUser SysUserEntity currentUser) {
		String realSchema = currentUser.getTenantId() + AppCommon.scheam;

		ApiResult<QueryResults<SysRoleEntity>> apiResult = new ApiResult<>();

		QueryResults<SysRoleEntity> queryResults = sysRoleService.queryPage(realSchema, SysRole.class,
				SysRoleEntity.class, queryPage);

		apiResult.setData(queryResults);

		apiResult.setStatus(CommonMessage.SUCCESS.getStatus());
		apiResult.setMessage(CommonMessage.SUCCESS.getMessage());

		return apiResult;

	}

}
