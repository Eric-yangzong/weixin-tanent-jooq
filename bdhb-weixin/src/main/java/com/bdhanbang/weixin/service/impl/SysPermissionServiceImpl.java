package com.bdhanbang.weixin.service.impl;

import org.springframework.stereotype.Service;

import com.bdhanbang.base.service.impl.BaseServiceImpl;
import com.bdhanbang.weixin.jooq.tables.QSysPermission;
import com.bdhanbang.weixin.service.SysPermissionService;
import com.generator.tables.pojos.SysPermission;

@Service
public class SysPermissionServiceImpl extends BaseServiceImpl<QSysPermission, SysPermission> implements SysPermissionService {

}
