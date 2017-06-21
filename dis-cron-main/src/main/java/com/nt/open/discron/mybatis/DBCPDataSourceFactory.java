package com.nt.open.discron.mybatis;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.apache.ibatis.datasource.DataSourceFactory;

import com.nt.open.discron.log.LogUtil;

/**
 * 
 * @author bjfulianqiu
 *
 */
public class DBCPDataSourceFactory implements DataSourceFactory {
	
	private static final String configFile = "conf/db.properties";  
	
	private static DataSource dataSource;
	
	static{
		Properties dbProperties = new Properties();
		 try {
			dbProperties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(configFile));
			dataSource = BasicDataSourceFactory.createDataSource(dbProperties); 
			LogUtil.info("dbcp datasource init success...");
		} catch (Exception e) {
			LogUtil.error("连接池初始化失败", e);
			e.printStackTrace();
		}
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setProperties(Properties arg0) {
		
	}
	
}
