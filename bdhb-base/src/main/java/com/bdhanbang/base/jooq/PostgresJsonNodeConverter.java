package com.bdhanbang.base.jooq;

import java.io.IOException;

import org.jooq.Converter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.NullNode;

/**
 * @ClassName: PostgresJsonNodeConverter
 * @Description: 字符和实体转换
 * @author yangxz
 * @date 2018年7月17日 上午11:27:54
 * 
 */
public class PostgresJsonNodeConverter implements Converter<Object, JsonNode> {

	/**
	 * @Fields serialVersionUID : 字符和实体转换
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public JsonNode from(Object t) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			if (t instanceof String) {
				return t == null ? NullNode.instance : mapper.readTree("" + t);
			} else {
				return t == null ? NullNode.instance : mapper.readTree(mapper.writeValueAsString(t));
			}

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Object to(JsonNode u) {
		try {
			return u == null || u.equals(NullNode.instance) ? null : new ObjectMapper().writeValueAsString(u);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Class<Object> fromType() {
		return Object.class;
	}

	@Override
	public Class<JsonNode> toType() {
		return JsonNode.class;
	}
}