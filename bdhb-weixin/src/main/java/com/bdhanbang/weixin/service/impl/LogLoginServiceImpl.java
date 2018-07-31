package com.bdhanbang.weixin.service.impl;

import org.springframework.stereotype.Service;

import com.bdhanbang.base.service.impl.BaseServiceImpl;
import com.bdhanbang.weixin.entity.TLogLogin;
import com.bdhanbang.weixin.jooq.tables.QLogLogin;
import com.bdhanbang.weixin.service.LogLoginService;

@Service
public class LogLoginServiceImpl extends BaseServiceImpl<QLogLogin, TLogLogin> implements LogLoginService {

}
