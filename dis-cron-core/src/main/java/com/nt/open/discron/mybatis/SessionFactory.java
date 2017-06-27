/**
 * 
 */
package com.nt.open.discron.mybatis;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.nt.open.discron.log.LogUtil;

/**
 * @author bjfulianqiu
 *
 */
public class SessionFactory {
	
	private static SqlSessionFactory sqlSessionFactory=null;
	private final static String resource = "mybatis-config.xml";
	
	static{
		InputStream inputStream;
		try {
			inputStream = Resources.getResourceAsStream(resource);
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			LogUtil.info("mybatis session factory init success ...");
		} catch (IOException e) {
			LogUtil.error("sqlSessionFactory init error ", e);
			e.printStackTrace();
		}
	}
	
	
	public static SqlSession getSession(){
		return sqlSessionFactory.openSession(true);
	}
	
	public static void closeSession(SqlSession session){
		if(session!=null){
			session.close();
		}
	}

}
