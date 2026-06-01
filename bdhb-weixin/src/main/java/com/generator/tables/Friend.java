package com.generator.tables;

import java.time.LocalDateTime;
import java.util.UUID;

import org.jooq.Record;
import org.jooq.TableField;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;

import com.fasterxml.jackson.databind.JsonNode;

public class Friend extends TableImpl<Record> {

	private static final long serialVersionUID = 1L;

	public final TableField<Record, UUID> ID = createField("id", SQLDataType.UUID, this, "");
	public final TableField<Record, String> USER_ID = createField("user_id", SQLDataType.VARCHAR, this, "");
	public final TableField<Record, String> FRIEND_ID = createField("friend_id", SQLDataType.VARCHAR, this, "");
	public final TableField<Record, String> FRIEND_NAME = createField("friend_name", SQLDataType.VARCHAR, this, "");
	public final TableField<Record, LocalDateTime> BUILD_TIME = createField("build_time", SQLDataType.LOCALDATETIME,
			this, "");
	public final TableField<Record, String> AVATAR = createField("avatar", SQLDataType.VARCHAR, this, "");
	public final TableField<Record, JsonNode> JSONB = createField("jsonb", JsonbDataType.JSONB, this, "");

	public Friend() {
		super("friend");
	}
}
