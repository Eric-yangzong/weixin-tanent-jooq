package com.generator.tables;

import java.time.LocalDateTime;
import java.util.UUID;

import org.jooq.Record;
import org.jooq.TableField;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;

import com.fasterxml.jackson.databind.JsonNode;

public class TMyLogin extends TableImpl<Record> {

	private static final long serialVersionUID = 1L;

	public final TableField<Record, UUID> ID = createField("id", SQLDataType.UUID, this, "");
	public final TableField<Record, String> USER_CODE = createField("user_code", SQLDataType.VARCHAR, this, "");
	public final TableField<Record, String> USER_NAME = createField("user_name", SQLDataType.VARCHAR, this, "");
	public final TableField<Record, String> LOGIN_TYPE = createField("login_type", SQLDataType.VARCHAR, this, "");
	public final TableField<Record, LocalDateTime> LOGIN_TIME = createField("login_time", SQLDataType.LOCALDATETIME, this,
			"");
	public final TableField<Record, String> LOGIN_IP = createField("login_ip", SQLDataType.VARCHAR, this, "");
	@SuppressWarnings("unchecked")
	public final TableField<Record, JsonNode> JSONB = createField("jsonb", JsonbDataType.JSONB, this, "");

	public TMyLogin() {
		super("t_my_login");
	}
}
