/**
 * 
 */
package com.nt.open.proc.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author bjfulianqiu
 *
 */
public class DateUtil {

	public static String date2String(Date date) throws ParseException{
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		df.setLenient(false);
		String dateStr=df.format(date);
		return dateStr;
	}
}
