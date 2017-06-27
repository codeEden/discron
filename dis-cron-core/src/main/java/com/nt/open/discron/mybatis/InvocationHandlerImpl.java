/**
 * 
 */
package com.nt.open.discron.mybatis;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.apache.ibatis.session.SqlSession;

/**
 * @author bjfulianqiu
 * @param <T>
 *
 */
public class InvocationHandlerImpl<T> implements InvocationHandler {
	
	private Class<T> daoInterface;
	
	public InvocationHandlerImpl(Class<T> daoInterface){
		this.daoInterface=daoInterface;
	}

	/* (non-Javadoc)
	 * @see java.lang.reflect.InvocationHandler#invoke(java.lang.Object, java.lang.reflect.Method, java.lang.Object[])
	 */
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		SqlSession session=SessionFactory.getSession();
		Object result=null;
		try{
			Object jobDao=session.getMapper(daoInterface);
			// 执行目标对象的方法  
	        result = method.invoke(jobDao, args);  
		}finally{
			SessionFactory.closeSession(session);
		}
		return result;
	}
	
	public Object getProxy(){
		return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{daoInterface},this);
	}

}
