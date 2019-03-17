package bdhb.weixin_pay.common;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtils {

	public static String getFormatTime(Date date, String timeFormat) {

		SimpleDateFormat df = new SimpleDateFormat(timeFormat);
		return df.format(date);
	}

	public static Date addDay(Date date, String timeExpire) {

		Calendar calstart = Calendar.getInstance();
		calstart.setTime(date);

		calstart.add(java.util.Calendar.HOUR_OF_DAY, Integer.parseInt(timeExpire));

		return calstart.getTime();
	}

}
