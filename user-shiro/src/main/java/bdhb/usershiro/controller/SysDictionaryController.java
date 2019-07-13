package bdhb.usershiro.controller;

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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.bdhanbang.base.common.ApiResult;
import com.bdhanbang.base.common.Query;
import com.bdhanbang.base.common.QueryPage;
import com.bdhanbang.base.common.QueryResults;
import com.bdhanbang.base.message.CommonMessage;
import com.bdhanbang.base.util.BeanUtils;
import com.generator.tables.SysDictionary;
import com.generator.tables.pojos.SysDictionaryEntity;
import com.generator.tables.pojos.SysUserEntity;

import bdhb.usershiro.common.AppCommon;
import bdhb.usershiro.common.CurrentUser;
import bdhb.usershiro.service.TableService;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/sys_dictionary")
public class SysDictionaryController {

	@Autowired
	private TableService sysDictionary;

	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public ApiResult<SysDictionaryEntity> insert(@Valid @RequestBody SysDictionaryEntity sysDictionaryEntity,
			@ApiIgnore @CurrentUser SysUserEntity currentUser) {

		String realSchema = currentUser.getTenantId() + AppCommon.scheam;

		ApiResult<SysDictionaryEntity> apiResult = new ApiResult<>();

		sysDictionaryEntity.setId(UUID.randomUUID());

		sysDictionary.insertEntity(realSchema, SysDictionary.class, sysDictionaryEntity);

		apiResult.setData(sysDictionaryEntity);

		apiResult.setStatus(CommonMessage.CREATE.getStatus());
		apiResult.setMessage(CommonMessage.CREATE.getMessage());

		return apiResult;

	}

	@RequestMapping(method = RequestMethod.PUT, produces = { "application/json;charset=UTF-8" })
	@ResponseStatus(HttpStatus.OK)
	public ApiResult<SysDictionaryEntity> update(@RequestBody SysDictionaryEntity sysDictionaryEntity,
			@ApiIgnore @CurrentUser SysUserEntity currentUser) {

		String realSchema = currentUser.getTenantId() + AppCommon.scheam;

		ApiResult<SysDictionaryEntity> apiResult = new ApiResult<>();

		SysDictionaryEntity isysDictionaryEntity = sysDictionary.getEntity(realSchema, SysDictionary.class,
				SysDictionaryEntity.class, sysDictionaryEntity.getId());

		BeanUtils.copyProperties(sysDictionaryEntity, isysDictionaryEntity);

		sysDictionary.updateEntity(realSchema, SysDictionary.class, isysDictionaryEntity);

		apiResult.setData(isysDictionaryEntity);

		apiResult.setStatus(CommonMessage.UPDATE.getStatus());
		apiResult.setMessage(CommonMessage.UPDATE.getMessage());

		return apiResult;

	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable("id") String id, @ApiIgnore @CurrentUser SysUserEntity currentUser) {
		String realSchema = currentUser.getTenantId() + AppCommon.scheam;
		sysDictionary.deleteEntity(realSchema, SysDictionary.class, id);

	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = { "application/json;charset=UTF-8" })
	@ResponseStatus(HttpStatus.OK)
	public ApiResult<SysDictionaryEntity> getEntity(@PathVariable("id") String id,
			@ApiIgnore @CurrentUser SysUserEntity currentUser) {

		String realSchema = currentUser.getTenantId() + AppCommon.scheam;
		ApiResult<SysDictionaryEntity> apiResult = new ApiResult<>();

		SysDictionaryEntity sysDictionaryEntity = sysDictionary.getEntity(realSchema, SysDictionary.class,
				SysDictionaryEntity.class, id);

		apiResult.setData(sysDictionaryEntity);

		apiResult.setStatus(CommonMessage.SUCCESS.getStatus());
		apiResult.setMessage(CommonMessage.SUCCESS.getMessage());

		return apiResult;

	}

	@RequestMapping(value = "/all", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(HttpStatus.OK)
	public ApiResult<List<SysDictionaryEntity>> queryAll(@ApiIgnore @CurrentUser SysUserEntity currentUser) {
		String realSchema = currentUser.getTenantId() + AppCommon.scheam;

		ApiResult<List<SysDictionaryEntity>> apiResult = new ApiResult<>();

		List<SysDictionaryEntity> queryList = sysDictionary.queryList(realSchema, SysDictionary.class,
				SysDictionaryEntity.class, new Query());

		apiResult.setData(queryList);

		apiResult.setStatus(CommonMessage.SUCCESS.getStatus());
		apiResult.setMessage(CommonMessage.SUCCESS.getMessage());

		return apiResult;

	}

	@RequestMapping(method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(HttpStatus.OK)
	public ApiResult<QueryResults<SysDictionaryEntity>> query(@RequestParam("queryPage") QueryPage queryPage,
			@ApiIgnore @CurrentUser SysUserEntity currentUser) {
		String realSchema = currentUser.getTenantId() + AppCommon.scheam;

		ApiResult<QueryResults<SysDictionaryEntity>> apiResult = new ApiResult<>();

		QueryResults<SysDictionaryEntity> queryResults = sysDictionary.queryPage(realSchema, SysDictionary.class,
				SysDictionaryEntity.class, queryPage);

		apiResult.setData(queryResults);

		apiResult.setStatus(CommonMessage.SUCCESS.getStatus());
		apiResult.setMessage(CommonMessage.SUCCESS.getMessage());

		return apiResult;

	}

}
