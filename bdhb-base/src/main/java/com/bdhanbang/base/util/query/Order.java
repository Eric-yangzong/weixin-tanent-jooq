package com.bdhanbang.base.util.query;

/**
 * @ClassName: Order
 * @Description: 排序
 * @author yangxz
 * @date 2019年6月29日 下午1:45:06
 * 
 */
public enum Order {

	asc(1), desc(2);

	private final Integer order;

	private Order(Integer order) {
		this.order = order;
	}

	public Integer get() {
		return order;
	}
}
