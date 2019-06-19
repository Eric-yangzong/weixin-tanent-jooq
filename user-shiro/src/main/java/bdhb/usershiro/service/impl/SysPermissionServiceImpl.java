package bdhb.usershiro.service.impl;

import org.springframework.stereotype.Service;

import com.bdhanbang.base.service.impl.BaseServiceImpl;
import com.generator.tables.SysPermission;
import com.generator.tables.pojos.SysPermissionEntity;

import bdhb.usershiro.service.SysPermissionService;

@Service
public class SysPermissionServiceImpl extends BaseServiceImpl<SysPermission, SysPermissionEntity>
		implements SysPermissionService {
}
