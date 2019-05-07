package com.bdhanbang.weixin.service.impl;

import org.springframework.stereotype.Service;

import com.bdhanbang.base.service.impl.BaseServiceImpl;
import com.bdhanbang.weixin.jooq.tables.QSysUser;
import com.bdhanbang.weixin.service.SysUserService;
import com.generator.tables.pojos.SysUser;

@Service
public class SysUserServiceImpl extends BaseServiceImpl<QSysUser, SysUser> implements SysUserService {

}
