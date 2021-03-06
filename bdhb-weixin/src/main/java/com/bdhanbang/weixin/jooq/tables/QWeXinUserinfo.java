package com.bdhanbang.weixin.jooq.tables;

import org.jooq.Schema;

import com.bdhanbang.base.jooq.IEntity;
import com.bdhanbang.base.jooq.ISchemaSwitch;
import com.generator.tables.TWeXinUserinfo;

/**
 * @ClassName: QWeXinUserinfo
 * @Description: 存放微信用户信息
 * @author yangxz
 * @date 2018年8月4日 下午12:42:25
 * 
 */
public class QWeXinUserinfo extends TWeXinUserinfo implements ISchemaSwitch, IEntity {

	private static final long serialVersionUID = 1L;

	public static String idName = "id";

	Schema schema;

	@Override
	public Schema getSchema() {
		return schema;
	}

	@Override
	public void setSchema(Schema schema) {
		this.schema = schema;
	}

	@Override
	public String getEntityIdName() {
		return idName;
	}

}