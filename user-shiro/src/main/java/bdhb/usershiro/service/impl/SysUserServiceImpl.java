package bdhb.usershiro.service.impl;

import org.springframework.stereotype.Service;

import com.bdhanbang.base.service.impl.BaseServiceImpl;
import com.generator.tables.pojos.SysUser;

import bdhb.usershiro.dao.QSysUser;
import bdhb.usershiro.service.SysUserService;

@Service
public class SysUserServiceImpl extends BaseServiceImpl<QSysUser, SysUser> implements SysUserService {

}
