package com.bdhanbang.weixin.service.impl;

import org.springframework.stereotype.Service;

import com.bdhanbang.base.service.impl.BaseServiceImpl;
import com.bdhanbang.weixin.entity.WeXinUserinfo;
import com.bdhanbang.weixin.jooq.tables.QWeXinUserinfo;
import com.bdhanbang.weixin.service.WeXinUserinfoService;

@Service
public class WeXinUserinfoServiceImpl extends BaseServiceImpl<QWeXinUserinfo, WeXinUserinfo> implements WeXinUserinfoService {

}
