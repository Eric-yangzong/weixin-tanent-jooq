package com.bdhanbang.weixin.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Size;

public class IEntity implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = 1L;

	@Size(max = 300)
	private String aaa = "";
	
	@Size(max = 500)
	private String ccc ="";
	
	private String ddd ="";
	 
	private ICEntity bbb;
	
	public ICEntity getBbb() {
		return bbb;
	}

	public void setBbb(ICEntity bbb) {
		this.bbb = bbb;
	}

	public String getDdd() {
		return ddd;
	}

	public void setDdd(String ddd) {
		this.ddd = ddd;
	}

	public String getCcc() {
		return ccc;
	}

	public void setCcc(String ccc) {
		this.ccc = ccc;
	}

	private List<String> strList = new ArrayList<>();

	public List<String> getStrList() {
		return strList;
	}

	public void setStrList(List<String> strList) {
		this.strList = strList;
	}

	public String getAaa() {
		return aaa;
	}

	public void setAaa(String aaa) {
		this.aaa = aaa;
	}

}
