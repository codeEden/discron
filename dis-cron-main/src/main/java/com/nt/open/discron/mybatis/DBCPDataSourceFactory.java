package com.nt.open.discron.mybatis;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.apache.ibatis.datasource.DataSourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author bjfulianqiu
 *
 */
public class DBCPDataSourceFactory implements DataSourceFactory {
	
	private static Logger logger = LoggerFactory.getLogger("discron");
	private static final String configFile = "conf/db.properties";  
	
	private static DataSource dataSource;
	
	static{
		Properties dbProperties = new Properties();
		 try {
			dbProperties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(configFile));
			dataSource = BasicDataSourceFactory.createDataSource(dbProperties); 
			logger.info("dbcp datasource init success...");
		} catch (Exception e) {
			logger.error("连接池初始化失败", e);
			e.printStackTrace();
		}
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setProperties(Properties arg0) {
		
	}
	
}
