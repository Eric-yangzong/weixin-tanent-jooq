package com.bdhanbang.base.util.query;

/**
 * @ClassName: Operate
 * @Description: >,>=等关系运算符号
 * @author yangxz
 * @date 2019年6月29日 下午1:37:54
 * 
 */
public enum Operate {
	/**
	 * @Fields equals : =
	 */
	equals(1),
	/**
	 * @Fields notEquals : !=
	 */
	notEquals(2),
	/**
	 * @Fields like : ilike(ignore case)
	 */
	like(3),

	/**
	 * @Fields great : >
	 */
	great(4),

	/**
	 * @Fields greatEquals : >=
	 */
	greatEquals(5),

	/**
	 * @Fields less : <
	 */
	less(6),

	/**
	 * @Fields lessEquals : <=
	 */
	lessEquals(7),

	/**
	 * @Fields notLike : not like
	 */
	notLike(8),
	/**
	 * @Fields isNull : isNull
	 */
	isNull(9),
	/**
	 * @Fields isNotNull : is not null
	 */
	isNotNull(10),
	/**
	 * @Fields in : in
	 */
	in(11),

	/**
	 * @Fields notIn : notIn
	 */
	notIn(12);

	private final Integer operate;

	private Operate(Integer operate) {
		this.operate = operate;
	}

	public Integer get() {
		return operate;
	}
}
