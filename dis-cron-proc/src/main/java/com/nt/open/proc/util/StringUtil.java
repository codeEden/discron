/**
 * 
 */
package com.nt.open.proc.util;

import com.google.common.base.Joiner;

/**
 * @author bjfulianqiu
 *
 */
public class StringUtil {

	public static String joinStr(String... args){
		return Joiner.on("").useForNull("null").join(args);
	}
	
}
