package bdhb.usershiro.service.impl;

import org.springframework.stereotype.Service;

import com.bdhanbang.base.service.impl.BaseServiceImpl;
import com.generator.tables.pojos.SysPermission;

import bdhb.usershiro.dao.QSysPermission;
import bdhb.usershiro.service.SysPermissionService;

@Service
public class SysPermissionServiceImpl extends BaseServiceImpl<QSysPermission, SysPermission> implements SysPermissionService {

}
