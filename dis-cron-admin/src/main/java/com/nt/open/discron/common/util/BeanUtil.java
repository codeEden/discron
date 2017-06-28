/**
 * 
 */
package com.nt.open.discron.common.util;

import java.text.ParseException;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.cglib.core.Converter;

import com.nt.open.discron.util.DateUtil;

/**
 * @author bjfulianqiu
 *
 */
public class BeanUtil {

	/**
	 * 
	 * (基于spring BeanCopier) 
	 * @author bjfulianqiu
	 * @param src
	 * @param dest
	 */
	public static void objectCopy(Object src, Object dest) {
		BeanCopier beanCopier = BeanCopier.create(src.getClass(),  
				dest.getClass(), true);  
        beanCopier.copy(src, dest, new CustomConverter());
	}
	
	static class CustomConverter implements Converter {
		/**
		 * 日志对象
		 */
		protected Logger logger = LoggerFactory.getLogger(getClass());

		@SuppressWarnings("rawtypes")
		public Object convert(Object value, Class target, Object context) {
			try {
				if(value != null){
					if (value.getClass().isAssignableFrom(Integer.class)
							&& target.getCanonicalName().equals(Byte.class.getCanonicalName())) {
						//Integer转Byte
						return Byte.parseByte(String.valueOf((Integer)value));
					}if (value.getClass().isAssignableFrom(Byte.class)
							&& target.getCanonicalName().equals(String.class.getCanonicalName())) {
						//Byte转String
						return String.valueOf((Byte)value);
					}if (value.getClass().isAssignableFrom(String.class)
							&& target.getCanonicalName().equals(Byte.class.getCanonicalName())) {
						//String转Byte
						return Byte.parseByte(String.valueOf(value));
					} if (value.getClass().isAssignableFrom(Integer.class)
							&& target.getCanonicalName().equals(String.class.getCanonicalName())) {
						//Integer转String
						return String.valueOf((Integer)value);
					} if (value.getClass().isAssignableFrom(Float.class)
							&& target.getCanonicalName().equals(String.class.getCanonicalName())) {
						//Float转String
						return String.valueOf((Float)value);
					}if (value.getClass().isAssignableFrom(String.class)
							&& target.getCanonicalName().equals(Integer.class.getCanonicalName())) {
						//String转Integer
						return Integer.parseInt(String.valueOf(value));
					} else if (value.getClass().isAssignableFrom(Date.class)
							&& target.getCanonicalName().equals(String.class.getCanonicalName())) {
						//String转Date
						return DateUtil.date2Str((Date) value);
//						return null;
					} else if (value != null && value.getClass().isAssignableFrom(String.class)
							&& target.getCanonicalName().equals(Date.class.getCanonicalName())) {
						try {
							//Date转String
							return DateUtil.dateStr2Date(String.valueOf(value));
						} catch (ParseException e) {
							e.printStackTrace();
						}
						return value;
					}
				}else {
					return value;
				}
			} catch (Exception e) {
				logger.error("类型转换失败", e);
			}
			return value;
		}
	}
}
