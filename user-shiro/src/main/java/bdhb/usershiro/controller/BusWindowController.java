package bdhb.usershiro.controller;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.javatuples.Pair;
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
import com.bdhanbang.base.jooq.GenSchema;
import com.bdhanbang.base.message.CommonMessage;
import com.bdhanbang.base.util.BeanUtils;
import com.generator.tables.BusWindow;
import com.generator.tables.pojos.BusWindowEntity;
import com.generator.tables.pojos.SysUserEntity;

import bdhb.usershiro.common.AppCommon;
import bdhb.usershiro.common.CurrentUser;
import bdhb.usershiro.service.TableService;
import bdhb.usershiro.service.impl.FWindowInfoCode;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/bus_window")
public class BusWindowController {

	@Autowired
	private TableService busWindowService;

	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public ApiResult<BusWindowEntity> insert(@Valid @RequestBody BusWindowEntity busWindowEntity,
			@ApiIgnore @CurrentUser SysUserEntity currentUser) {

		String realSchema = currentUser.getTenantId() + AppCommon.scheam;

		GenSchema s = new GenSchema(realSchema);
		FWindowInfoCode fCode = new FWindowInfoCode(s);
		fCode.setTenant(currentUser.getTenantId().toUpperCase());

		String code = busWindowService.getFunctionValue(fCode, String.class);

		ApiResult<BusWindowEntity> apiResult = new ApiResult<>();

		busWindowEntity.setId(UUID.randomUUID());
		busWindowEntity.setWindowCode(code);
		busWindowEntity.setUpdateFullName(currentUser.getFullName());
		busWindowEntity.setUpdateTime(OffsetDateTime.now());

		busWindowService.insertEntity(realSchema, BusWindow.class, busWindowEntity);

		apiResult.setData(busWindowEntity);

		apiResult.setStatus(CommonMessage.CREATE.getStatus());
		apiResult.setMessage(CommonMessage.CREATE.getMessage());

		return apiResult;

	}

	@RequestMapping(method = RequestMethod.PUT, produces = { "application/json;charset=UTF-8" })
	@ResponseStatus(HttpStatus.OK)
	public ApiResult<BusWindowEntity> update(@RequestBody BusWindowEntity busWindowEntity,
			@ApiIgnore @CurrentUser SysUserEntity currentUser) {

		String realSchema = currentUser.getTenantId() + AppCommon.scheam;

		ApiResult<BusWindowEntity> apiResult = new ApiResult<>();

		BusWindowEntity ibusWindowEntity = busWindowService.getEntity(realSchema, BusWindow.class,
				BusWindowEntity.class, busWindowEntity.getId());

		BeanUtils.copyProperties(busWindowEntity, ibusWindowEntity);

		ibusWindowEntity.setUpdateFullName(currentUser.getFullName());
		ibusWindowEntity.setUpdateTime(OffsetDateTime.now());

		busWindowService.updateEntity(realSchema, BusWindow.class, ibusWindowEntity);

		apiResult.setData(ibusWindowEntity);

		apiResult.setStatus(CommonMessage.UPDATE.getStatus());
		apiResult.setMessage(CommonMessage.UPDATE.getMessage());

		return apiResult;

	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable("id") String id, @ApiIgnore @CurrentUser SysUserEntity currentUser) {
		String realSchema = currentUser.getTenantId() + AppCommon.scheam;
		busWindowService.deleteEntity(realSchema, BusWindow.class, id);

	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = { "application/json;charset=UTF-8" })
	@ResponseStatus(HttpStatus.OK)
	public ApiResult<BusWindowEntity> getEntity(@PathVariable("id") String id,
			@ApiIgnore @CurrentUser SysUserEntity currentUser) {

		String realSchema = currentUser.getTenantId() + AppCommon.scheam;
		ApiResult<BusWindowEntity> apiResult = new ApiResult<>();

		BusWindowEntity busWindowEntity = busWindowService.getEntity(realSchema, BusWindow.class, BusWindowEntity.class,
				id);

		apiResult.setData(busWindowEntity);

		apiResult.setStatus(CommonMessage.SUCCESS.getStatus());
		apiResult.setMessage(CommonMessage.SUCCESS.getMessage());

		return apiResult;

	}

	@RequestMapping(value = "/wx", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(HttpStatus.OK)
	public ApiResult<Pair<String, QueryResults<BusWindowEntity>>> wxQuery(
			@RequestParam("queryPage") QueryPage queryPage, @ApiIgnore @CurrentUser SysUserEntity currentUser) {
		String realSchema = currentUser.getTenantId() + AppCommon.scheam;

		ApiResult<Pair<String, QueryResults<BusWindowEntity>>> apiResult = new ApiResult<>();

		String[] roles = currentUser.getRoles();
		String role = "";
		List<Query> querys = queryPage.getQuerys();

		for (int i = 0; i < roles.length; i++) {
			if (AppCommon.master.equals(roles[i])) {
				querys.add(new Query("master", currentUser.getUserId()));
				role = roles[i];
				break;
			} else if (AppCommon.worker.equals(roles[i])) {
				querys.add(new Query("worker", currentUser.getUserId()));
				role = roles[i];
				break;
			}
		}

		QueryResults<BusWindowEntity> queryResults = busWindowService.queryPage(realSchema, BusWindow.class,
				BusWindowEntity.class, queryPage);

		Pair<String, QueryResults<BusWindowEntity>> pair = new Pair<String, QueryResults<BusWindowEntity>>(role,
				queryResults);

		apiResult.setData(pair);

		apiResult.setStatus(CommonMessage.SUCCESS.getStatus());
		apiResult.setMessage(CommonMessage.SUCCESS.getMessage());

		return apiResult;

	}

	@RequestMapping(method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(HttpStatus.OK)
	public ApiResult<QueryResults<BusWindowEntity>> query(@RequestParam("queryPage") QueryPage queryPage,
			@ApiIgnore @CurrentUser SysUserEntity currentUser) {
		String realSchema = currentUser.getTenantId() + AppCommon.scheam;

		ApiResult<QueryResults<BusWindowEntity>> apiResult = new ApiResult<>();

		// 权限过滤
		String[] roles = currentUser.getRoles();
		List<Query> querys = queryPage.getQuerys();

		for (int i = 0; i < roles.length; i++) {
			if (AppCommon.master.equals(roles[i])) {
				querys.add(new Query("master", currentUser.getUserId()));
				break;
			} else if (AppCommon.worker.equals(roles[i])) {
				querys.add(new Query("worker", currentUser.getUserId()));
				break;
			}
		}

		QueryResults<BusWindowEntity> queryResults = busWindowService.queryPage(realSchema, BusWindow.class,
				BusWindowEntity.class, queryPage);

		apiResult.setData(queryResults);

		apiResult.setStatus(CommonMessage.SUCCESS.getStatus());
		apiResult.setMessage(CommonMessage.SUCCESS.getMessage());

		return apiResult;

	}

}
