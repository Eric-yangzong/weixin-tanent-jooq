package bdhb.usershiro.service;

import java.util.UUID;

import org.jooq.Record1;
import org.jooq.SelectConditionStep;

import com.bdhanbang.base.common.Query;
import com.bdhanbang.base.service.BaseService;
import com.generator.tables.SysUser;
import com.generator.tables.pojos.SysUserEntity;

public interface SysUserService extends BaseService<SysUser, SysUserEntity> {

	/**
	 * @param schema
	 * @param query
	 * @return
	 */
	SelectConditionStep<Record1<UUID>> getUserCondition(String schema, Query query);

}
