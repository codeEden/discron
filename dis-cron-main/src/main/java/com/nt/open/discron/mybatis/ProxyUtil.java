/**
 * 
 */
package com.nt.open.discron.mybatis;

/**
 * @author bjfulianqiu
 *
 */
public class ProxyUtil {

	public static Object getProxy(Class interfaceClass){
		InvocationHandlerImpl invocationHandlerFactory=new InvocationHandlerImpl(interfaceClass);
		return invocationHandlerFactory.getProxy();
	}
}
