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

	public static String date2String(Date date) throws ParseException{
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:MM:SS");
		df.setLenient(false);
		String dateStr=df.format(date);
		return dateStr;
	}
}
