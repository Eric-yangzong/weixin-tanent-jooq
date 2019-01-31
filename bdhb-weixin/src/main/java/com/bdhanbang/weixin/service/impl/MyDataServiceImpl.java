package com.bdhanbang.weixin.service.impl;

import org.springframework.stereotype.Service;

import com.bdhanbang.base.service.impl.BaseServiceImpl;
import com.bdhanbang.weixin.entity.MyData;
import com.bdhanbang.weixin.jooq.tables.QMyData;
import com.bdhanbang.weixin.service.MyDataService;

@Service
public class MyDataServiceImpl extends BaseServiceImpl<QMyData, MyData> implements MyDataService {

}
