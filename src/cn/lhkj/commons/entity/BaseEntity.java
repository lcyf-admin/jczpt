package cn.lhkj.commons.entity;

import java.util.Map;

/**
 * Entity需要实现此类 EchoData中才能调用save对象的方法
 * @author zzy
 *
 */
public interface BaseEntity {
	
	/**
	 * 此类对应数据库表名
	 */
	public String getTableName();
	
	/**
	 * key对应类中的字段，value对应数据库表中的字段名
	 */
	public Map<String, String> getColumnValue();
	
	/**
	 * 判断字段是否是CLOB，方便存入大字段,类中无CLOB返回false即可
	 */
	public Boolean isCLOB(String cloumnID);
}