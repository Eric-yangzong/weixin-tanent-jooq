package com.bdhanbang.base.common;

import java.io.Serializable;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * @ClassName: Order
 * @Description: 排序用类
 * @author yangxz
 * @date 2018年7月17日 下午5:50:25
 * 
 */
public class Order implements Serializable {

	/**
	 * @Fields serialVersionUID : 排序数据
	 */
	private static final long serialVersionUID = 1L;

	@Min(1)
	@Max(2)
	Integer order = 1;// 排序方式

	String fieldName = "";// 排序字段

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

}
