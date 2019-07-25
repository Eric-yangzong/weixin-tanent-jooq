package bdhb.usershiro.controller;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;
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
import com.bdhanbang.base.exception.BusinessException;
import com.bdhanbang.base.jooq.GenSchema;
import com.bdhanbang.base.message.CommonMessage;
import com.bdhanbang.base.util.BeanUtils;
import com.bdhanbang.base.util.query.Operate;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.generator.tables.BusWindow;
import com.generator.tables.SysUser;
import com.generator.tables.pojos.BusWindowEntity;
import com.generator.tables.pojos.SysUserEntity;

import bdhb.usershiro.common.AppCommon;
import bdhb.usershiro.common.CurrentUser;
import bdhb.usershiro.service.SysUserService;
import bdhb.usershiro.service.TableService;
import bdhb.usershiro.service.impl.FWindowInfoCode;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/bus_window")
public class BusWindowController {

	@Autowired
	private TableService busWindowService;

	@Autowired
	private SysUserService sysUserService;

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

	/**
	 * @ClassName: PairAvatar
	 * @Description: 接受三个值
	 * @author yangxz
	 * @date 2019年6月6日 下午3:58:19
	 * 
	 * @param <A>
	 * @param <B>
	 * @param <C>
	 */
	public static class PairAvatar<A, B, C> {

		private A value0;
		private B value1;
		private C value2;

		public C getValue2() {
			return value2;
		}

		public void setValue2(C value2) {
			this.value2 = value2;
		}

		public B getValue1() {
			return value1;
		}

		public A getValue0() {
			return value0;
		}

		public void setValue0(A value0) {
			this.value0 = value0;
		}

		public void setValue1(B value1) {
			this.value1 = value1;
		}

	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/batch", method = RequestMethod.PUT, produces = { "application/json;charset=UTF-8" })
	@ResponseStatus(HttpStatus.OK)
	public ApiResult<PairAvatar<UUID, UUID, List<UUID>>> updateWoker(
			@RequestBody PairAvatar<UUID, UUID, List<UUID>> pair, @ApiIgnore @CurrentUser SysUserEntity currentUser) {
		ApiResult<PairAvatar<UUID, UUID, List<UUID>>> apiResult = new ApiResult<>();

		String realSchema = currentUser.getTenantId() + AppCommon.scheam;

		UUID workerId = pair.getValue0();
		UUID masterId = pair.getValue1();

		List<UUID> windowIds = pair.getValue2();

		Query query = new Query();
		query.add("id", windowIds, Operate.in);

		Pair<String, Object> workerPairs = new Pair<String, Object>("worker", workerId);
		Pair<String, Object> masterPairs = new Pair<String, Object>("master", masterId);

		busWindowService.updateColumn(realSchema, BusWindow.class, query, workerPairs, masterPairs);

		apiResult.setData(pair);

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

		this.addUserMessage(realSchema, busWindowEntity);

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

		if (Objects.isNull(roles)) {
			throw new BusinessException("20000", "权限为空不可查询业务数据");
		}

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

		// 支持子查询
		querys.forEach(x -> {
			if (!Objects.isNull(x.getField())
					&& (x.getField().indexOf("worker.") >= 0 || x.getField().indexOf("master.") >= 0)) {

				String field = x.getField().substring(0, x.getField().indexOf("."));

				Query query = new Query();
				query.add(x.getField().substring(x.getField().indexOf(".") + 1), x.getValue(), Operate.get(x.getOp()));

				x.setField(field);
				x.setValue(sysUserService.getUserCondition(realSchema, query));
				x.setOp(Operate.in.get());
			}
		});

		QueryResults<BusWindowEntity> queryResults = busWindowService.queryPage(realSchema, BusWindow.class,
				BusWindowEntity.class, queryPage);

		// 添加用户相关信息
		queryResults.getResults().forEach(x -> {
			this.addUserMessage(realSchema, x);
		});

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

		if (Objects.isNull(roles)) {
			throw new BusinessException("20000", "权限为空不可查询业务数据");
		}

		for (int i = 0; i < roles.length; i++) {
			if (AppCommon.master.equals(roles[i])) {
				querys.add(new Query("master", currentUser.getUserId()));
				break;
			} else if (AppCommon.worker.equals(roles[i])) {
				querys.add(new Query("worker", currentUser.getUserId()));
				break;
			}
		}

		// 支持子查询
		querys.forEach(x -> {
			if (!Objects.isNull(x.getField())
					&& (x.getField().indexOf("worker.") >= 0 || x.getField().indexOf("master.") >= 0)) {

				String field = x.getField().substring(0, x.getField().indexOf("."));

				Query query = new Query();
				query.add(x.getField().substring(x.getField().indexOf(".") + 1), x.getValue(), Operate.get(x.getOp()));

				x.setField(field);
				x.setValue(sysUserService.getUserCondition(realSchema, query));
				x.setOp(Operate.in.get());
			}
		});

		QueryResults<BusWindowEntity> queryResults = busWindowService.queryPage(realSchema, BusWindow.class,
				BusWindowEntity.class, queryPage);

		// 添加用户相关信息
		queryResults.getResults().forEach(x -> {
			this.addUserMessage(realSchema, x);
		});

		apiResult.setData(queryResults);

		apiResult.setStatus(CommonMessage.SUCCESS.getStatus());
		apiResult.setMessage(CommonMessage.SUCCESS.getMessage());

		return apiResult;

	}

	/**
	 * @Title: addUserMessage
	 * @Description: 增加用户信息
	 * @param @param realSchema
	 * @param @param x 设定文件
	 * @return void 返回类型
	 * @throws:
	 */
	private void addUserMessage(String realSchema, BusWindowEntity busWindowEntity) {

		ObjectMapper mapper = new ObjectMapper();

		try {

			if (Objects.isNull(busWindowEntity.getJsonb()) || busWindowEntity.getJsonb().isNull()) {
				busWindowEntity.setJsonb(mapper.readTree("{}"));
			}

			if (!Objects.isNull(busWindowEntity.getWorker())) {
				SysUserEntity sysUserEntity = sysUserService.getEntity(realSchema, SysUser.class, SysUserEntity.class,
						busWindowEntity.getWorker());

				if (!Objects.isNull(sysUserEntity)) {
					JsonNode jsonNode = busWindowEntity.getJsonb();

					((ObjectNode) jsonNode).put("workerFullName", sysUserEntity.getFullName());
					((ObjectNode) jsonNode).put("workerPhone", sysUserEntity.getPhone());
				}
			}

			if (!Objects.isNull(busWindowEntity.getMaster())) {
				SysUserEntity sysUserEntity = sysUserService.getEntity(realSchema, SysUser.class, SysUserEntity.class,
						busWindowEntity.getMaster());

				if (!Objects.isNull(sysUserEntity)) {
					JsonNode jsonNode = busWindowEntity.getJsonb();

					((ObjectNode) jsonNode).put("masterFullName", sysUserEntity.getFullName());
					((ObjectNode) jsonNode).put("masterPhone", sysUserEntity.getPhone());
				}
			}

		} catch (IOException e) {
			throw new BusinessException(e, "20000", "数据转换错误");
		}
	}

}
