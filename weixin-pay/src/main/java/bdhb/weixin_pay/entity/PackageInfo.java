package bdhb.weixin_pay.entity;

import java.io.Serializable;

/**
 * @ClassName: PackageInfo
 * @Description: 返回给前台的实体
 * @author yangxz
 * @date 2019年3月17日 上午11:35:40
 * 
 */
public class PackageInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String prepayId = "";

	private String nonceStr = "";

	public String getNonceStr() {
		return nonceStr;
	}

	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}

	public String getPrepayId() {
		return prepayId;
	}

	public void setPrepayId(String prepayId) {
		this.prepayId = prepayId;
	}

}
