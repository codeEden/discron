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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author bjfulianqiu
 *
 */
public class SessionFactory {
	
	private static Logger logger = LoggerFactory.getLogger("discron");
	
	private static SqlSessionFactory sqlSessionFactory=null;
	private final static String resource = "mybatis-config.xml";
	
	static{
		InputStream inputStream;
		try {
			inputStream = Resources.getResourceAsStream(resource);
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			logger.info("mybatis session factory init success ...");
		} catch (IOException e) {
			logger.error("sqlSessionFactory init error ", e);
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
