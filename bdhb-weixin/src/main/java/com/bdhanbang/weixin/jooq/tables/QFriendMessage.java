package com.bdhanbang.weixin.jooq.tables;

import org.jooq.Schema;

import com.bdhanbang.base.jooq.ISchemaSwitch;
import com.generator.tables.FriendMessage;

public class QFriendMessage extends FriendMessage implements ISchemaSwitch {

	private static final long serialVersionUID = 1L;

	Schema schema;

	@Override
	public Schema getSchema() {
		return schema;
	}

	@Override
	public void setSchema(Schema schema) {
		this.schema = schema;
	}
}
