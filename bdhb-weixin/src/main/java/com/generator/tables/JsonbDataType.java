package com.generator.tables;

import org.jooq.DataType;
import org.jooq.impl.SQLDataType;

import com.bdhanbang.weixin.entity.OkapiConfig;
import com.fasterxml.jackson.databind.JsonNode;

final class JsonbDataType {

	@SuppressWarnings("unchecked")
	static final DataType<JsonNode> JSONB = (DataType<JsonNode>) (DataType<?>) SQLDataType.CLOB;

	@SuppressWarnings("unchecked")
	static final DataType<OkapiConfig> OKAPI_CONFIG = (DataType<OkapiConfig>) (DataType<?>) SQLDataType.CLOB;

	private JsonbDataType() {
	}
}
