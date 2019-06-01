package bdhb.usershiro.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.bdhanbang.base.jooq.GenSchema;
import com.bdhanbang.base.service.impl.BaseServiceImpl;
import com.generator.tables.SysPermission;
import com.generator.tables.SysRolePermission;
import com.generator.tables.SysUserRole;
import com.generator.tables.pojos.SysPermissionEntity;

import bdhb.usershiro.common.AppCommon;
import bdhb.usershiro.service.SysPermissionService;

@Service
public class SysPermissionServiceImpl extends BaseServiceImpl<SysPermission, SysPermissionEntity>
		implements SysPermissionService {

	@Override
	public List<String> queryUserPermission(String tenantId, UUID userId) {
		List<String> list = new ArrayList<>();

		// 生成schema
		GenSchema s = new GenSchema(tenantId + AppCommon.scheam);

		// 权限表
		SysPermission sysPermission = new SysPermission();
		sysPermission.setSchema(s);

		// 用户角色表
		SysUserRole sysUserRole = new SysUserRole();
		sysUserRole.setSchema(s);

		// 角色权限表
		SysRolePermission sysRolePermission = new SysRolePermission();
		sysRolePermission.setSchema(s);

		// 查询数据
		list = dsl.select(sysPermission.PERMISSION).from(sysUserRole, sysRolePermission, sysPermission)
				.where(sysUserRole.ROLE_ID.eq(sysRolePermission.ROLE_ID)
						.and(sysRolePermission.PERMISSION_ID.eq(sysPermission.ID).and(sysUserRole.USER_ID.eq(userId))))
				.fetch().into(String.class);

		return list;
	}
}
