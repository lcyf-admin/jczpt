package cn.lhkj.commons.base;


import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.apache.commons.configuration.*;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Properties;


/**
 * 用dbcp做数据库连接池，以下程序经过测试，好用！！！
 * 支持oracle和mysql
 */
public class DatabasePool {
    private static BasicDataSource dataSource = null;
    private static String validationQure;
    static private Logger log = Logger.getLogger(DatabasePool.class);

    public DatabasePool() {}

    public static void init() {
        if (dataSource != null) {
            try {
                dataSource.close();
            } catch (Exception e) {}
            dataSource = null;
        }
        try {
        	Configuration config = new PropertiesConfiguration("dbinfo.properties");
        	Properties p = new Properties();
            p.setProperty("driverClassName", config.getString("driverClassName"));
            p.setProperty("url", config.getString("url"));
            p.setProperty("username", config.getString("username"));
            p.setProperty("password", config.getString("password"));
            p.setProperty("maxActive", config.getString("maxActive"));
            p.setProperty("initialSize", config.getString("initialSize"));
            p.setProperty("maxIdle", config.getString("maxIdle"));
            p.setProperty("maxWait", config.getString("maxWait"));
            p.setProperty("removeAbandoned", config.getString("removeAbandoned"));
            p.setProperty("removeAbandonedTimeout", config.getString("removeAbandonedTimeout"));
            p.setProperty("logAbandoned", config.getString("logAbandoned"));
            p.setProperty("testOnBorrow", config.getString("testOnBorrow"));
            p.setProperty("validationQure", config.getString("validationQure"));
            validationQure = config.getString("validationQure");
            dataSource = (BasicDataSource) BasicDataSourceFactory.createDataSource(p);
        } catch (Exception e) {
        	log.error("创建数据库连接池失败！");
        }
    }

    public static synchronized Connection getConnection() throws SQLException {
        if (dataSource == null) init();
        Connection conn = null;
        if (dataSource != null) {
        	try{
	            conn = dataSource.getConnection();
	            Statement stmt = conn.createStatement();
	            stmt.execute(validationQure);
	            stmt.close();
            }catch (Exception e) {
            	init();
            	conn = dataSource.getConnection();
			}
        }
        return conn;
    }
}