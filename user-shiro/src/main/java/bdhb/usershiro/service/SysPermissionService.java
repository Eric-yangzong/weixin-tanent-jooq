package bdhb.usershiro.service;

import java.util.List;
import java.util.UUID;

import com.bdhanbang.base.service.BaseService;
import com.generator.tables.SysPermission;
import com.generator.tables.pojos.SysPermissionEntity;


public interface SysPermissionService extends BaseService<SysPermission, SysPermissionEntity> {

	/**
	 * @param tenantId
	 * @param userId
	 * @return
	 */
	List<String> queryUserPermission(String tenantId, UUID userId);

}
