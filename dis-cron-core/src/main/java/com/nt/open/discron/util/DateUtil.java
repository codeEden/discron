/**
 * 
 */
package com.nt.open.discron.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author bjfulianqiu
 *
 */
public class DateUtil {

	public static final String DATE_FORMAT_DEFAULT = "yyyy-MM-dd HH:mm:ss";
	
	public static String date2String(Date date) throws ParseException{
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:MM:SS");
		df.setLenient(false);
		String dateStr=df.format(date);
		return dateStr;
	}
	
	public static String date2Str(Date date) {
		SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT_DEFAULT);
		df.setLenient(false);
		String dateStr=df.format(date);
		return dateStr;
	}
	
	public static Date dateStr2Date(String dateStr) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT_DEFAULT);
		df.setLenient(false);
		Date date = df.parse(dateStr);
		return date;
	}
}
