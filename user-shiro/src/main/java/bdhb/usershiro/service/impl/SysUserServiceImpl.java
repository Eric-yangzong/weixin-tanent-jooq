package bdhb.usershiro.service.impl;

import java.util.UUID;

import org.jooq.Condition;
import org.jooq.Record1;
import org.jooq.SelectConditionStep;
import org.springframework.stereotype.Service;

import com.bdhanbang.base.common.Query;
import com.bdhanbang.base.jooq.GenSchema;
import com.bdhanbang.base.service.impl.BaseServiceImpl;
import com.bdhanbang.base.util.JOOQHelper;
import com.generator.tables.SysUser;
import com.generator.tables.pojos.SysUserEntity;

import bdhb.usershiro.service.SysUserService;

/**
 * 用户信息接口
 */
@Service
public class SysUserServiceImpl extends BaseServiceImpl<SysUser, SysUserEntity> implements SysUserService {

	@Override
	public SelectConditionStep<Record1<UUID>> getUserCondition(String schema, Query query) {
		SysUser sysUser = new SysUser();
		GenSchema s = new GenSchema(schema);
		sysUser.setSchema(s);

		Condition condition = JOOQHelper.analyzeQuery(sysUser, query.getQuerys());

		return dsl.select(sysUser.USER_ID).from(sysUser).where(condition);
	}
}
