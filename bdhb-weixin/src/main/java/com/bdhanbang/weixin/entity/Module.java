package com.bdhanbang.weixin.entity;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

/**
 * @ClassName: Schema
 * @Description: Schema实体
 * @author yangxz
 * @date 2018年7月9日 上午11:07:06
 *
 */
@JsonNaming(SnakeCaseStrategy.class)
public class Module {

	@Size(min = 1, max = 500, message = "最小1个字符，最大500个字符")
	private String moduleTo;

	public String getModuleTo() {
		return moduleTo;
	}

	public void setModuleTo(String moduleTo) {
		this.moduleTo = moduleTo;
	}

	public String getModuleFrom() {
		return moduleFrom;
	}

	public void setModuleFrom(String moduleFrom) {
		this.moduleFrom = moduleFrom;
	}

	@Size(min = 1, max = 500, message = "最小1个字符，最大500个字符")
	private String moduleFrom;

}
