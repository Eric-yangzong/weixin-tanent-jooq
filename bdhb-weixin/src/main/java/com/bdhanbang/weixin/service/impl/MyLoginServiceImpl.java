package com.bdhanbang.weixin.service.impl;

import org.springframework.stereotype.Service;

import com.bdhanbang.base.service.impl.BaseServiceImpl;
import com.bdhanbang.weixin.entity.TMyLogin;
import com.bdhanbang.weixin.jooq.tables.QMyLogin;
import com.bdhanbang.weixin.service.MyLoginService;

@Service
public class MyLoginServiceImpl extends BaseServiceImpl<QMyLogin, TMyLogin> implements MyLoginService {

}
