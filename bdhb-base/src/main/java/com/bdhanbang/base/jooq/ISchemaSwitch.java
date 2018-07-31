package com.bdhanbang.base.jooq;

import org.jooq.Schema;

/**
 * @ClassName: ISchemaSwitch
 * @Description: 切换schema
 * @author yangxz
 * @date 2018年7月14日 上午9:11:07
 * 
 */
public interface ISchemaSwitch {

	void setSchema(Schema schema);
}
