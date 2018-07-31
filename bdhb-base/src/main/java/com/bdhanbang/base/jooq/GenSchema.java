package com.bdhanbang.base.jooq;

import org.jooq.impl.SchemaImpl;

/**
 * @ClassName: GenSchema
 * @Description: 生成schema，为了切换schema
 * @author yangxz
 * @date 2018年7月14日 上午9:11:39
 * 
 */
public class GenSchema extends SchemaImpl {

	public final static String schemaName = "userlib";

	public GenSchema() {
		super(schemaName, null);
	}

	public GenSchema(String name) {
		super(name, null);
	}

	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = 1L;

}
