package bdhb.usershiro.vo;

import java.io.Serializable;

public class WeXinResoult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Integer code = 0;

	WeXinDataVo data;

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public WeXinDataVo getData() {
		return data;
	}

	public void setData(WeXinDataVo data) {
		this.data = data;
	}

}
