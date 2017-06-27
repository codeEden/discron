/**
 * 
 */
package com.nt.open.discron.mybatis;

import java.util.concurrent.ConcurrentMap;

import com.google.common.collect.Maps;

/**
 * @author bjfulianqiu
 *
 */
public enum ProxyUtil {
	
	PROXYUTIL;
	
	private volatile static ConcurrentMap<Object, Object> proxyMap=Maps.newConcurrentMap();

	public static <T> Object getProxy(Class<T> interfaceClass){
		String interfaceName=interfaceClass.getSimpleName();
		Object result=proxyMap.get(interfaceName);
		
		if(result==null){
			synchronized(interfaceClass){
				if(result==null){
					InvocationHandlerImpl<T> invocationHandlerFactory=new InvocationHandlerImpl<T>(interfaceClass);
					result=invocationHandlerFactory.getProxy();
					proxyMap.putIfAbsent(interfaceName, result);
				}
			}
		}
		return result;
	}
	
}
