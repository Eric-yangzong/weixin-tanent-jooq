package com.bdhanbang.base.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Min;

/**
 * @ClassName: QueryPage
 * @Description: 分页查询用
 * @author yangxz
 * @date 2018年7月14日 下午2:34:43
 * 
 */

public class QueryPage implements Serializable {

	private static final long serialVersionUID = 1L;

	@Min(1)
	Integer page = 1;

	@Min(1)
	Integer size = 10;

	List<Order> orders = new ArrayList<>();

	List<Query> querys = new ArrayList<>();

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrder(List<Order> orders) {
		this.orders = orders;
	}

	public List<Query> getQuerys() {
		return querys;
	}

	public void setQuery(List<Query> querys) {
		this.querys = querys;
	}

}
