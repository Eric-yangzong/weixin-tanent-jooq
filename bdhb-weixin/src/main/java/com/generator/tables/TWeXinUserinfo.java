package com.generator.tables;

import java.util.UUID;

import org.jooq.Record;
import org.jooq.TableField;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;

import com.fasterxml.jackson.databind.JsonNode;

public class TWeXinUserinfo extends TableImpl<Record> {

	private static final long serialVersionUID = 1L;

	public final TableField<Record, UUID> ID = createField("id", SQLDataType.UUID, this, "");
	public final TableField<Record, String> OPEN_ID = createField("open_id", SQLDataType.VARCHAR, this, "");
	public final TableField<Record, String> NICK_NAME = createField("nick_name", SQLDataType.VARCHAR, this, "");
	public final TableField<Record, Integer> GENDER = createField("gender", SQLDataType.INTEGER, this, "");
	public final TableField<Record, String> LANGUAGE = createField("language", SQLDataType.VARCHAR, this, "");
	public final TableField<Record, String> CITY = createField("city", SQLDataType.VARCHAR, this, "");
	public final TableField<Record, String> PROVINCE = createField("province", SQLDataType.VARCHAR, this, "");
	public final TableField<Record, String> COUNTRY = createField("country", SQLDataType.VARCHAR, this, "");
	public final TableField<Record, String> AVATAR_URL = createField("avatar_url", SQLDataType.VARCHAR, this, "");
	public final TableField<Record, JsonNode> WATERMARK = createField("watermark", JsonbDataType.JSONB, this, "");
	public final TableField<Record, JsonNode> JSONB = createField("jsonb", JsonbDataType.JSONB, this, "");

	public TWeXinUserinfo() {
		super("t_we_xin_userinfo");
	}
}
