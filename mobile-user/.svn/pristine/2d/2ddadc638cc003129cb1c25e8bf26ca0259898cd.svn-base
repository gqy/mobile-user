package com.fx.mobile.user.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DateUtil {

	public static final String FORMAT_DATE_19 = "yyyy-MM-dd HH:mm:ss";

	public static final String FORMAT_DATE_10_SLASH = "yyyy/MM/dd";

	/**
	 *  "yyyy-MM-dd HH:mm:ss:SSS";的时间格式字符创
	 */
//	private static final String DATE_FORMAT_SSS = "yyyy-MM-dd HH:mm:ss:SSS";

	private static final String DF_TO_DAY = "yyyy-MM-dd";
	
	public static final String DF_TO_DAY_2 = "yyyyMMdd";
	
	public static final String DF_TO_DAY_6 = "yyMMdd";


	/**
	 * 日期格式化
	 * @param pattern
	 * @param date
	 * @return
	 */
	public static String formatDate(String pattern, Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}
	
	public static Date formatDate(String pattern, String dateStr) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		try {
			return sdf.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 默认格式化当前日期
	 * @param pattern
	 * @return
	 */
	public static String formatDate(String pattern) {
		return formatDate(pattern, new Date());
	}
	
	
	public static String formatDate(Date date,String pattern) {
		return formatDate(pattern, date);
	}

	public static String formateDate19(Date date) {
		return formatDate(FORMAT_DATE_19, date);
	}
	
	public static Date formateDate19(String dateStr) {
		return formatDate(FORMAT_DATE_19, dateStr);
	}







	/**
	 * date1 日期是否大于date2日期，比较精度到日期
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean after(Date date1, Date date2) {
		if (date1 == null)
			return false;
		if (date2 == null)
			return true;
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);
		cal1.set(Calendar.HOUR_OF_DAY, 0);
		cal1.set(Calendar.MINUTE, 0);
		cal1.set(Calendar.SECOND, 0);

		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);
		cal2.set(Calendar.HOUR_OF_DAY, 0);
		cal2.set(Calendar.MINUTE, 0);
		cal2.set(Calendar.SECOND, 0);
		return cal1.after(cal2);
	}

	public static void main(String[] a) {
		System.out.println(formatDate("yyyy/M/d"));
	}


	/**
	* @param time
	* @return
	*/
	public static String getToDayDate(long time) {
		DateFormat fmt = createDateFmt(DF_TO_DAY);
		return fmt.format(new Date(time));
	}
	
	public static String getToDayDate2(long time) {
		DateFormat fmt = createDateFmt(DF_TO_DAY_2);
		return fmt.format(new Date(time));
	}
	public static String getToDayDate6(long time) {
		DateFormat fmt = createDateFmt(DF_TO_DAY_6);
		return fmt.format(new Date(time));
	}

	private static DateFormat createDateFmt(String datefmt) {
		DateFormat fmt = new SimpleDateFormat(datefmt);
		return fmt;
	}
	
}
