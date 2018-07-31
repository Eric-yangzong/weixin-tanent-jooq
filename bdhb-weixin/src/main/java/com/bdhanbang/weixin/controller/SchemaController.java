package com.bdhanbang.weixin.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.bdhanbang.weixin.entity.Module;
import com.bdhanbang.weixin.service.impl.SchemaService;


/**
 * @ClassName: SchemaController
 * @Description: schema数据操作
 * @author yangxz
 * @date 2018年7月9日 上午11:17:37
 *
 */
@RestController
@RequestMapping("/_/tenant")
public class SchemaController {

	public static final String OKAPI_HEADER_TENANT = "x-okapi-tenant";

	@Autowired
	SchemaService schemaService;

	/**
	 * @Title: createLogSchema
	 * @Description: 建立租客schema接口
	 * @param @param
	 *            schema 设定文件
	 * @return void 返回类型
	 * @throws:
	 */
	@RequestMapping(value = "", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public void createLogSchema(@RequestHeader(OKAPI_HEADER_TENANT) String tenantId,
			@Valid @RequestBody Module module) {

		schemaService.initSchema(tenantId);

	}

	/**
	 * @Title: updateLogSchema
	 * @Description: 删除租客schema接口
	 * @param @param
	 *            tenantId
	 * @param @return
	 *            设定文件
	 * @return ResponseEntity<?> 返回类型
	 * @throws:
	 */
	@RequestMapping(value = "", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteById(@RequestHeader(OKAPI_HEADER_TENANT) String tenantId) {
		schemaService.dropSchema(tenantId);
	}

}
