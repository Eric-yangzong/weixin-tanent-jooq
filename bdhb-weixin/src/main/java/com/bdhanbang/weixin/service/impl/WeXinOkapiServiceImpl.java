package com.bdhanbang.weixin.service.impl;

import org.springframework.stereotype.Service;

import com.bdhanbang.base.service.impl.BaseServiceImpl;
import com.bdhanbang.weixin.entity.TWeXinOkapi;
import com.bdhanbang.weixin.jooq.tables.QWeXinOkapi;
import com.bdhanbang.weixin.service.WeXinOkapiService;

/**
 * @ClassName: WeXinOkapiServiceImpl
 * @Description: 实现微信和okapi关联的curd 接口
 * @author yangxz
 * @date 2018年8月4日 上午10:19:01
 * 
 */
@Service
public class WeXinOkapiServiceImpl extends BaseServiceImpl<QWeXinOkapi, TWeXinOkapi> implements WeXinOkapiService {

}
