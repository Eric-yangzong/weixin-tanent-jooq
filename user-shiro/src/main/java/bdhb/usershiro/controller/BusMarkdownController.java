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
import com.generator.tables.BusMarkdown;
import com.generator.tables.pojos.BusMarkdownEntity;
import com.generator.tables.pojos.SysUserEntity;

import bdhb.usershiro.common.AppCommon;
import bdhb.usershiro.common.CurrentUser;
import bdhb.usershiro.service.TableService;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/bus_markdown")
public class BusMarkdownController {

	@Autowired
	private TableService busMarkdownService;

	private final String tenant = "tat0004";

	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public ApiResult<BusMarkdownEntity> insert(@Valid @RequestBody BusMarkdownEntity busMarkdownEntity) {

		String realSchema = tenant + AppCommon.scheam;

		ApiResult<BusMarkdownEntity> apiResult = new ApiResult<>();

		busMarkdownEntity.setId(UUID.randomUUID());// 设置系统的UUID

		busMarkdownService.insertEntity(realSchema, BusMarkdown.class, busMarkdownEntity);

		apiResult.setData(busMarkdownEntity);

		apiResult.setStatus(CommonMessage.CREATE.getStatus());
		apiResult.setMessage(CommonMessage.CREATE.getMessage());

		return apiResult;

	}

	@RequestMapping(method = RequestMethod.PUT, produces = { "application/json;charset=UTF-8" })
	@ResponseStatus(HttpStatus.OK)
	public ApiResult<BusMarkdownEntity> update(@RequestBody BusMarkdownEntity sysPermissionEntity) {

		String realSchema = tenant + AppCommon.scheam;

		ApiResult<BusMarkdownEntity> apiResult = new ApiResult<>();

		busMarkdownService.updateEntity(realSchema, BusMarkdown.class, sysPermissionEntity);

		apiResult.setData(sysPermissionEntity);

		apiResult.setStatus(CommonMessage.UPDATE.getStatus());
		apiResult.setMessage(CommonMessage.UPDATE.getMessage());

		return apiResult;

	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable("id") String id) {
		String realSchema = tenant + AppCommon.scheam;
		busMarkdownService.deleteEntity(realSchema, BusMarkdown.class, id);

	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = { "application/json;charset=UTF-8" })
	@ResponseStatus(HttpStatus.OK)
	public ApiResult<BusMarkdownEntity> getEntity(@PathVariable("id") String id) {

		String realSchema = tenant + AppCommon.scheam;
		ApiResult<BusMarkdownEntity> apiResult = new ApiResult<>();

		BusMarkdownEntity sysPermissionEntity = busMarkdownService.getEntity(realSchema, BusMarkdown.class,
				BusMarkdownEntity.class, id);

		apiResult.setData(sysPermissionEntity);

		apiResult.setStatus(CommonMessage.SUCCESS.getStatus());
		apiResult.setMessage(CommonMessage.SUCCESS.getMessage());

		return apiResult;

	}

	@RequestMapping(method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public ApiResult<QueryResults<BusMarkdownEntity>> query(@RequestParam("queryPage") QueryPage queryPage,
			@ApiIgnore @CurrentUser SysUserEntity currentUser) {
		String realSchema = currentUser.getTenantId() + AppCommon.scheam;

		ApiResult<QueryResults<BusMarkdownEntity>> apiResult = new ApiResult<>();

		QueryResults<BusMarkdownEntity> queryResults = busMarkdownService.queryPage(realSchema, BusMarkdown.class,
				BusMarkdownEntity.class, queryPage);

		apiResult.setData(queryResults);

		apiResult.setStatus(CommonMessage.SUCCESS.getStatus());
		apiResult.setMessage(CommonMessage.SUCCESS.getMessage());

		return apiResult;

	}

}
