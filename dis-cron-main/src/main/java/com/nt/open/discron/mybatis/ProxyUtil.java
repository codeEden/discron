/**
 * 
 */
package com.nt.open.discron.mybatis;

/**
 * @author bjfulianqiu
 *
 */
public class ProxyUtil {

	public static <T> Object getProxy(Class<T> interfaceClass){
		InvocationHandlerImpl<T> invocationHandlerFactory=new InvocationHandlerImpl<T>(interfaceClass);
		return invocationHandlerFactory.getProxy();
	}
}
