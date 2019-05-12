package bdhb.usershiro.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.bdhanbang.base.service.impl.BaseServiceImpl;
import com.generator.tables.SysPermission;
import com.generator.tables.pojos.SysPermissionEntity;

import bdhb.usershiro.service.SysPermissionService;

@Service
public class SysPermissionServiceImpl extends BaseServiceImpl<SysPermission, SysPermissionEntity>
		implements SysPermissionService {

	@Override
	public List<String> queryUserPermission(String tenantId, UUID userId) {
		List<String> list = new ArrayList<>();
		

		return list;
	}
}
