package bdhb.weixin_pay.util;

import java.util.Objects;

public class StringUtils {

	public static boolean isNotBlank(String return_code) {

		if (Objects.isNull(return_code) || "".equals(return_code)) {
			return false;
		} else {
			return true;
		}
	}

}
