package cn.lhkj.commons.base;

import java.lang.reflect.Field;
import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import cn.lhkj.commons.defined.Column;
import cn.lhkj.commons.util.CalendarUtil;



public class ResultSetUtil {
	
	/**
	 * 将查询的集合转成JSONObject
	 * @param rs
	 * @return
	 * @throws Exception
	 */
	static public JSONObject rs2JsonObject(ResultSet rs) throws Exception{
		JSONObject jsonObject = new JSONObject();
		ResultSetMetaData rsmd = rs.getMetaData();
		int colCount = rsmd.getColumnCount();
		if((rs.next())){
		    for (int i = 1; i <= colCount; i++) {
		        String columnName = rsmd.getColumnName(i);
		        String value = "";
		        if(rs.getObject(i)!=null){
		        	if(rs.getObject(i) instanceof java.util.Date){
		        		value = CalendarUtil.format((java.util.Date)rs.getObject(i),"yyyy-MM-dd HH:mm:ss");
		        	}else if(rsmd.getColumnTypeName(i).equalsIgnoreCase("CLOB")){
	        			Clob c = rs.getClob(i);
	        		    if(c != null){
	        		    	value = c.getSubString((long)1,(int)c.length());
	        		    }
		        	}else{
		        		value = rs.getObject(i).toString();
		        	}
		        }
		        jsonObject.put(columnName, value);
		    }
		}
		return jsonObject;
	}
	
	/**
	 * 将查询的集合转成JSONArray
	 * @param rs
	 * @return
	 * @throws Exception
	 */
	static public JSONArray rs2JsonArray(ResultSet rs) throws Exception{
		JSONArray jsonArray = new JSONArray();
		ResultSetMetaData rsmd = rs.getMetaData();
		int colCount = rsmd.getColumnCount();
		int i = 0;
		while (rs.next()) {
			Map<String,String> bean = new HashMap<String,String>();
		    for (int j = 1; j <= colCount; j++) {
		        String columnName = rsmd.getColumnLabel(j);//rsmd.getColumnName(j) mysql中得到的是原始字段名而不是别名
		        String value = "";
		        if(rs.getObject(j)!=null){
		        	if(rs.getObject(j) instanceof java.util.Date){
		        		value = CalendarUtil.format((java.util.Date)rs.getObject(j),"yyyy-MM-dd HH:mm:ss");
		        	}else if(rsmd.getColumnTypeName(j).equalsIgnoreCase("CLOB")){
	        			Clob c = rs.getClob(j);
	        		    if(c != null){
	        		    	value = c.getSubString((long)1,(int)c.length());
	        		    }
		        	}else{
		        		value = rs.getObject(j).toString();
		        	}
		        }
		        bean.put(columnName.toUpperCase(), value);	//列名全部统一转成大写,mysql有这样的问题
		    }
		    jsonArray.add(i,bean);
		    i++;
		}
		return jsonArray;
	}
	
	/**
	 * 将查询的集合转成List<Map<String,String>>
	 * @param rs
	 * @return
	 * @throws Exception
	 */
	static public List<Map<String,String>> rs2List(ResultSet rs) throws Exception{
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		ResultSetMetaData rsmd = rs.getMetaData();
		int colCount = rsmd.getColumnCount();
		if(rs != null){
			while (rs.next()) {
				Map<String,String> bean = new HashMap<String,String>();
			    for (int j = 1; j <= colCount; j++) {
			        String columnName = rsmd.getColumnLabel(j);//rsmd.getColumnName(j) mysql中得到的是原始字段名而不是别名
			        String value = "";
			        if(rs.getObject(j)!=null){
			        	if(rs.getObject(j) instanceof java.util.Date){
			        		value = CalendarUtil.format((java.util.Date)rs.getObject(j),"yyyy-MM-dd HH:mm:ss");
			        	}else if(rsmd.getColumnTypeName(j).equalsIgnoreCase("CLOB")){
		        			Clob c = rs.getClob(j);
		        		    if(c != null){
		        		    	value = c.getSubString((long)1,(int)c.length());
		        		    }
			        	}else{
			        		value = rs.getObject(j).toString();
			        	}
			        }
			        bean.put(columnName.toUpperCase(), value);	//列名全部统一转成大写,mysql有这样的问题
			    }
			    list.add(bean);
			}
		}
		return list;
	}
	
	/**
	 * 将查询的集合转成List<Object>
	 * @param rs
	 * @return
	 * @throws Exception
	 */
	static public List<Object> rs2List(ResultSet rs,Class<?> clazz) throws Exception{
		List<Object> list = new ArrayList<Object>();
		ResultSetMetaData rsmd = rs.getMetaData();
		int colCount = rsmd.getColumnCount();
		Field[] fields = clazz.getDeclaredFields();
		while (rs.next()) {
			Map<String,Object> bean = new HashMap<String,Object>();
			for (int j = 1; j <= colCount; j++) {//列名全部统一转成大写,mysql有这样的问题
				String columnName = rsmd.getColumnLabel(j);//rsmd.getColumnName(j) mysql中得到的是原始字段名而不是别名
				if(rs.getObject(j)!=null){
			    	if(rs.getObject(j) instanceof java.util.Date){
			    		bean.put(columnName.toUpperCase(), (java.util.Date)rs.getObject(j));
			    	}else if(rsmd.getColumnTypeName(j).equalsIgnoreCase("CLOB")){
			    		Clob c = rs.getClob(j);
			    		if(c != null){
			    			String value = c.getSubString((long)1,(int)c.length());
		        			bean.put(columnName.toUpperCase(), value);
		        		}
			    	}else{
			    		String value = rs.getObject(j).toString();
			    		bean.put(columnName.toUpperCase(), value);
			    	}
				}
			}
		    Object type = clazz.newInstance();
		    for(Field f : fields){
		    	f.setAccessible(true);
		    	Column column = (Column)f.getAnnotation(Column.class);
		    	if(column == null) continue;
		    	String columnName = column.value().toUpperCase();
		    	if(bean.get(columnName) == null) continue;
				if(f.getType() == String.class){
					f.set(type, String.valueOf(bean.get(columnName)));
				}else if(f.getType() == Date.class){
					f.set(type, (Date)bean.get(columnName));
				}else if(f.getType() == Integer.class || f.getType() == int.class){
					f.set(type, Integer.parseInt(String.valueOf(bean.get(columnName))));
				}else if(f.getType() == Long.class || f.getType() == long.class){
					f.set(type, Long.parseLong(String.valueOf(bean.get(columnName))));
				}else if(f.getType() == Float.class || f.getType() == float.class){
					f.set(type, Float.parseFloat(String.valueOf(bean.get(columnName))));
				}else if(f.getType() == Double.class || f.getType() == double.class){
					f.set(type, Double.parseDouble(String.valueOf(bean.get(columnName))));
				}else if(f.getType() == Boolean.class || f.getType() == boolean.class){
					if(String.valueOf(bean.get(columnName)).equals("0"))
						f.set(type, false);
					else
						f.set(type, true);
				}
		    }
		    list.add(type);
		}
		return list;
	}
}
