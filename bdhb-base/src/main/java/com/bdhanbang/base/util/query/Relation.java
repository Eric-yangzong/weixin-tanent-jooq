package com.bdhanbang.base.util.query;

/**
 * @ClassName: Relation
 * @Description: and,or,关系运送符号
 * @author yangxz
 * @date 2019年6月29日 下午1:34:24
 * 
 */
public enum Relation {
	and(1), or(2);

	private final Integer relation;

	private Relation(Integer relation) {
		this.relation = relation;
	}

	public Integer get() {
		return relation;
	}
}
