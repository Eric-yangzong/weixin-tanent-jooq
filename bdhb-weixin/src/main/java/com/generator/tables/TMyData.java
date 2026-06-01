package com.generator.tables;

import java.util.UUID;

import org.jooq.Record;
import org.jooq.TableField;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;

import com.fasterxml.jackson.databind.JsonNode;

public class TMyData extends TableImpl<Record> {

	private static final long serialVersionUID = 1L;

	public final TableField<Record, UUID> ID = createField("id", SQLDataType.UUID, this, "");
	public final TableField<Record, String> DATA_TYPE = createField("data_type", SQLDataType.VARCHAR, this, "");
	public final TableField<Record, JsonNode> JSONB = createField("jsonb", JsonbDataType.JSONB, this, "");

	public TMyData() {
		super("t_my_data");
	}
}
