package com.bdhanbang.base.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * @ClassName: Query
 * @Description: 查询信息
 * @author yangxz
 * @date 2018年7月14日 下午2:38:58
 * 
 */
public class Query implements Serializable {

	private static final long serialVersionUID = 1L;

	private String field;// 字段名称

	@Min(1)
	@Max(10)
	private Integer op;// 操作符1:=,2:!=,3:like,4:>,5:>=,6:<,7:<=,8:not like, 9:isNull,10:isNotNull

	private Object value;// 字段值

	@Min(1)
	@Max(2)
	private Integer relation;// 逻辑符 1:and ,2:or

	List<Query> query = new ArrayList<Query>();

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public Integer getOp() {
		return op;
	}

	public void setOp(Integer op) {
		this.op = op;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public Integer getRelation() {
		return relation;
	}

	public void setRelation(Integer relation) {
		this.relation = relation;
	}

	public List<Query> getQuery() {
		return query;
	}

	public void setQuery(List<Query> query) {
		this.query = query;
	}

}
