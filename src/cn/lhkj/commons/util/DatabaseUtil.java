package cn.lhkj.commons.util;


import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;

import cn.lhkj.commons.entity.PageInfo;


/**
 * 处理oracle和mysql差异的类
 */
public class DatabaseUtil {
	static private Logger log = Logger.getLogger(DatabaseUtil.class);
	
	/**数据库类型 oracle或者mysql*/
	static private String databaseType;
	
	public static String getPagingSQL(String sql,PageInfo pageInfo){
		if(pageInfo.getRows() == 0) return sql;
		String relust = "";
		if("mysql".equals(getDatabaseType())){
			Integer statr = (pageInfo.getPage()-1)*pageInfo.getRows();
			Integer end = pageInfo.getRows();
			relust = sql + " limit "+statr+","+ end;
		}else if("oracle".equals(getDatabaseType())){
			Integer statr = (pageInfo.getPage()-1)*pageInfo.getRows() + 1;
			Integer end = pageInfo.getPage()*pageInfo.getRows();
			relust = "select * from(select zzz.*,rownum rn from("+sql+") zzz) where rn between "+statr +" and "+end;
		}
		return relust;
		
	}
	
	public static String getPagingSQL(String sql,Integer start,Integer end){
		String relust = "";
		if("mysql".equals(getDatabaseType())){
			relust = sql + " limit "+start+","+ end;
		}else if("oracle".equals(getDatabaseType())){
			relust = "select * from(select zzz.*,rownum rn from("+sql+") zzz) where rn between "+start +" and "+end;
		}
		return relust;
		
	}
	
	/**系统时间函数*/
	public static String getSysdate(){
		String relust = "";
		if("mysql".equals(getDatabaseType())){
			relust = "sysdate()";
		}else if("oracle".equals(getDatabaseType())){
			relust = "sysdate";
		}
		return relust;
	}
	
	/**连接函数 */
	public static String concatFun(String key){
		String relust = "";
		if("mysql".equals(getDatabaseType())){
			relust = "GROUP_CONCAT("+key+")";
		}else if("oracle".equals(getDatabaseType())){
			relust = "WM_CONCAT("+key+")";
		}
		return relust;
	}
	///////////////////////////////
	public static String getDatabaseType() {
		if(databaseType == null) setDatabaseType();
		return databaseType;
	}

	private static void setDatabaseType() {
		try {
			 Configuration config = new PropertiesConfiguration("dbinfo.properties");
			 String driverClassName = config.getString("driverClassName").toString();
			 if(driverClassName.toLowerCase().indexOf("mysql") != -1){
				 databaseType = "mysql";
	     	}else if(driverClassName.toLowerCase().indexOf("oracle") != -1){
	     		databaseType = "oracle";
	     	}
		 }catch (Exception e) {
			 log.error(e.getMessage(),e);
		}
	}
}
