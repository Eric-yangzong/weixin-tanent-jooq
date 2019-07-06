package bdhb.usershiro.service;

import java.util.List;

import com.bdhanbang.base.service.BaseService;
import com.generator.tables.SysPermission;
import com.generator.tables.pojos.SysPermissionEntity;

import bdhb.usershiro.vo.Menu;

public interface SysPermissionService extends BaseService<SysPermission, SysPermissionEntity> {

	List<Menu> getMenus(String tenantId, String[] roles);
}
