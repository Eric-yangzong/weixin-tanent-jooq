package com.generator.tables;

import java.util.UUID;

import org.jooq.Record;
import org.jooq.TableField;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;

import com.bdhanbang.weixin.entity.OkapiConfig;

public class TWeXinOkapi extends TableImpl<Record> {

	private static final long serialVersionUID = 1L;

	public final TableField<Record, UUID> ID = createField("id", SQLDataType.UUID, this, "");
	public final TableField<Record, String> TANENT_ID = createField("tanent_id", SQLDataType.VARCHAR, this, "");
	public final TableField<Record, String> APP_ID = createField("app_id", SQLDataType.VARCHAR, this, "");
	public final TableField<Record, String> APP_SECRET = createField("app_secret", SQLDataType.VARCHAR, this, "");
	public final TableField<Record, String> USER_NAME = createField("user_name", SQLDataType.VARCHAR, this, "");
	public final TableField<Record, String> PASSWORD = createField("password", SQLDataType.VARCHAR, this, "");
	public final TableField<Record, String> NOTE = createField("note", SQLDataType.VARCHAR, this, "");
	public final TableField<Record, OkapiConfig> JSONB = createField("jsonb", JsonbDataType.OKAPI_CONFIG, this, "");

	public TWeXinOkapi() {
		super("t_we_xin_okapi");
	}
}
