package com.bdhanbang.base.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import com.bdhanbang.base.util.query.Operate;
import com.bdhanbang.base.util.query.Relation;

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
	private Integer op = 1;// 操作符1:=,2:!=,3:like,4:>,5:>=,6:<,7:<=,8:not like, 9:isNull,10:isNotNull

	private Object value;// 字段值

	@Min(1)
	@Max(2)
	private Integer relation = 1;// 逻辑符 1:and ,2:or

	List<Query> querys = new ArrayList<Query>();

	public Query() {
	}

	public Query(String field, Object value) {
		this.field = field;
		this.value = value;
	}

	public Query(String field, Object value, Operate operate) {
		this.field = field;
		this.value = value;
		this.op = operate.get();
	}

	public Query(String field, Object value, Relation relation) {
		this.field = field;
		this.value = value;
		this.relation = relation.get();
	}

	public Query(String field, Object value, Operate operate, Relation relation) {
		this.field = field;
		this.value = value;
		this.op = operate.get();
		this.relation = relation.get();
	}

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

	public List<Query> getQuerys() {
		return querys;
	}

	public void setQuerys(List<Query> querys) {
		this.querys = querys;
	}

	public Query add(Query query) {
		this.querys.add(query);
		return this;
	}

	public Query add(String field, Object value) {
		this.querys.add(new Query(field, value));
		return this;
	}

	public Query add(String field, Object value, Operate operate) {
		this.querys.add(new Query(field, value, operate));
		return this;
	}

	public Query add(String field, Object value, Relation relation) {
		this.querys.add(new Query(field, value, relation));
		return this;
	}

	public Query add(String field, Object value, Operate operate, Relation relation) {
		this.querys.add(new Query(field, value, operate, relation));
		return this;
	}

}
