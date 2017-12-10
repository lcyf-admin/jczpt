package cn.lhkj.commons.base;

import java.io.Writer;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import oracle.sql.CLOB;
import org.apache.log4j.Logger;

import cn.lhkj.commons.defined.Column;
import cn.lhkj.commons.defined.Entity;
import cn.lhkj.commons.entity.PageInfo;
import cn.lhkj.commons.entity.Proc;
import cn.lhkj.commons.util.DatabaseUtil;
import cn.lhkj.commons.util.CalendarUtil;
import cn.lhkj.commons.util.PackageUtil;
import cn.lhkj.commons.util.StringUtil;


/**
 * 封装一个从连接池得到连接的类 控制事物请看下面方法
 * 有异常都往外面抛出
 * @Repository("baseDao")
 */

public class BaseDao {
	private static Logger log = Logger.getLogger(BaseDao.class);
	private Connection conn = null;
	
	public BaseDao() throws Exception {
		this.conn = DatabasePool.getConnection();
		if(this.conn == null){
			log.error(CalendarUtil.getCurrentTime() + "---数据库连接失败！ ");
		}
	}
	
	/**返回连接 */
	public Connection getConn(){
		return this.conn;
	}
	
	/**关闭连接 */
	public void close(){
		if(this.conn == null) return;
		try {
			this.conn.close();
		} catch (SQLException e) {
			log.error(e.getMessage(),e);
		}finally{
			this.conn = null;
		}
	}

	/**开启事物 */
	public void beginTranscation() throws Exception {
		this.conn.setAutoCommit(false);
	}
	
	/**提交事物 */
	public void commit() throws Exception{
		if(!this.conn.getAutoCommit()){
			this.conn.commit();
		}
	}
	
	/**回滚事物  */
	public void rollback() throws Exception {
		if(!this.conn.getAutoCommit()){
			this.conn.rollback();
		}
	}
	
	/** 执行update或者insert语句 */
	public boolean execute(String sql) throws Exception {
		log.debug("execute---------SQL: "+sql);
		Statement stmt = null;
		try{
			boolean result = false;
			stmt = this.conn.createStatement();
			int executeCount = stmt.executeUpdate(sql);
			if( executeCount > 0) result = true;
			return result;
		}catch (Exception e) {
			log.error("SQL="+sql);
			log.error(e.getMessage(), e);
			throw e;
		}finally{
			if(stmt != null) stmt.close();
		}
	}
	
	/** 执行update或者insert语句 */
	public void execute(List<String> sqlList) throws Exception {
		if(this.conn == null) throw new NullPointerException("数据库连接失败！");
		if(StringUtil.isNull(sqlList)) return;
		Statement stmt = null;
		try{
			stmt = this.conn.createStatement();
			for(String s : sqlList){
				stmt.addBatch(s);
			}
			stmt.executeBatch();
		}catch (Exception e) {
			log.error(e.getMessage(), e);
			throw e;
		}finally{
			if(stmt != null) stmt.close();
		}
	}
	
	/**
	 * 执行update或者insert语句
	 * @param sql
	 * @return executeCount 修改记录数
	 * @throws Exception
	 */
	public Integer executeReturnCount(String sql) throws Exception {
		log.debug("execute---------SQL: "+sql);
		Statement stmt = null;
		try{
			stmt = conn.createStatement();
			int executeCount = stmt.executeUpdate(sql);
			return executeCount;
		}catch (Exception e) {
			log.error("SQL="+sql);
			log.error(e.getMessage(), e);
			throw e;
		}finally{
			if(stmt != null) stmt.close();
		}
	}
	
	/**
	 * 获取执行的sql返回的list
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,String>> findBySQL(String sql) throws Exception{
		log.debug("executeList-----SQL: "+sql);
		ResultSet rs = null;
		Statement stmt = null;
		try{
			stmt = this.conn.createStatement();
			rs = stmt.executeQuery(sql);
			List<Map<String,String>> list = ResultSetUtil.rs2List(rs);
			return list;
		}catch (Exception e) {
			log.error("SQL="+sql);
			log.error(e.getMessage(), e);
			throw e;
		}finally{
			if(rs != null) rs.close();
			if(stmt != null) stmt.close();
		}
	}
	
	/**
	 * hql语句例子：from User where ...
	 * @param hql
	 * @return
	 * @throws Exception
	 */
	public List<?> findByHQL(String hql) throws Exception{
		log.debug("executeList-----HQL: "+hql);
		if(StringUtil.isNull(hql)) return null;
		Map<String,String> entityClassMap = PackageUtil.getEntityMap();
		String[] elements = hql.split(" ");
		String className = "";
		String alias = "";
		for(String s : elements){
			if(s.length() == 0) continue;
			if(s.equalsIgnoreCase("from")) continue;
			if(s.equalsIgnoreCase("where"))break;
			if(s.equalsIgnoreCase("order"))break;
			if(!StringUtil.isNull(alias)) break;
			if(StringUtil.isNull(className)){
				className = entityClassMap.get(s);
			}else{
				alias = s;
			}
		}
		if(StringUtil.isNull(className)){
			throw new NullPointerException("Include Undefined Entity 【"+hql+"】");
		}
		Class<?> clazz = Class.forName(className);
		Entity entity = clazz.getAnnotation(Entity.class);
		String tableName = entity.table();
		String sql = "select * from "+tableName;
		
		if(!StringUtil.isNull(alias)) sql += " " + alias; 
		int index = hql.toLowerCase().indexOf(" where ");
		if(index != -1 ){
			String condition = hql.substring(index);
			Field[] fields = clazz.getDeclaredFields();
			for(Field f : fields){
				Column column = f.getAnnotation(Column.class);
				if(column == null) continue;
				String name = f.getName();
				String columnName = column.value();
				if(name.equalsIgnoreCase(columnName)) continue;
				condition = StringUtil.formatCondition(condition,name,columnName);
			}
			sql += " " + condition;
		}else{
			int orderIndex = hql.toLowerCase().indexOf(" order ");
			if(orderIndex != -1){
				String condition = hql.substring(orderIndex);
				Field[] fields = clazz.getDeclaredFields();
				for(Field f : fields){
					Column column = f.getAnnotation(Column.class);
					if(column == null) continue;
					String name = f.getName();
					String columnName = column.value();
					if(name.equalsIgnoreCase(columnName)) continue;
					condition = StringUtil.formatCondition(condition,name,columnName);
				}
				sql += " " + condition;
			}
		}
		log.debug("executeList-----SQL: "+sql);
		Statement stmt = null;
		ResultSet rs = null;
		try{
			stmt = this.conn.createStatement();
			rs = stmt.executeQuery(sql);
			List<Object> list = ResultSetUtil.rs2List(rs, clazz);
			return list;
		}catch (Exception e) {
			log.error("SQL="+sql);
			log.error(e.getMessage(), e);
			throw e;
		}finally{
			if(rs != null) rs.close();
			if(stmt != null) stmt.close();
		}
	}
	
	/**
	 * hql语句例子：from User where ...
	 * @param hql
	 * @return
	 * @throws Exception
	 */
	public List<?> findByHQL(String hql,Integer start,Integer end) throws Exception{
		log.debug("executeList-----HQL: "+hql);
		if(StringUtil.isNull(hql)) return null;
		Map<String,String> entityClassMap = PackageUtil.getEntityMap();
		String[] elements = hql.split(" ");
		String className = "";
		String alias = "";
		for(String s : elements){
			if(s.length() == 0) continue;
			if(s.equalsIgnoreCase("from")) continue;
			if(s.equalsIgnoreCase("where"))break;
			if(s.equalsIgnoreCase("order"))break;
			if(!StringUtil.isNull(alias)) break;
			if(StringUtil.isNull(className)){
				className = entityClassMap.get(s);
			}else{
				alias = s;
			}
		}
		if(StringUtil.isNull(className)){
			throw new NullPointerException("Include Undefined Entity 【"+hql+"】");
		}
		Class<?> clazz = Class.forName(className);
		Entity entity = clazz.getAnnotation(Entity.class);
		String tableName = entity.table();
		String sql = "select * from "+tableName;
		
		if(!StringUtil.isNull(alias)) sql += " " + alias; 
		int index = hql.toLowerCase().indexOf(" where ");
		if(index != -1 ){
			String condition = hql.substring(index);
			Field[] fields = clazz.getDeclaredFields();
			for(Field f : fields){
				Column column = f.getAnnotation(Column.class);
				if(column == null) continue;
				String name = f.getName();
				String columnName = column.value();
				if(name.equalsIgnoreCase(columnName)) continue;
				condition = StringUtil.formatCondition(condition,name,columnName);
			}
			sql += " " + condition;
		}else{
			int orderIndex = hql.toLowerCase().indexOf(" order ");
			if(orderIndex != -1){
				String condition = hql.substring(orderIndex);
				Field[] fields = clazz.getDeclaredFields();
				for(Field f : fields){
					Column column = f.getAnnotation(Column.class);
					if(column == null) continue;
					String name = f.getName();
					String columnName = column.value();
					if(name.equalsIgnoreCase(columnName)) continue;
					condition = StringUtil.formatCondition(condition,name,columnName);
				}
				sql += " " + condition;
			}
		}
		log.debug("executeList-----SQL: "+sql);
		String pagingSQL = DatabaseUtil.getPagingSQL(sql, start, end);//分页sql
		Statement stmt = null;
		ResultSet rs = null;
		try{
			stmt = this.conn.createStatement();
			rs = stmt.executeQuery(pagingSQL);
			List<Object> list = ResultSetUtil.rs2List(rs, clazz);
			return list;
		}catch (Exception e) {
			log.error("SQL="+sql);
			log.error(e.getMessage(), e);
			throw e;
		}finally{
			if(rs != null) rs.close();
			if(stmt != null) stmt.close();
		}
	}
	
	/**
	 * 获取执行的sql返回分页的list
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public List executePageList(String sql,PageInfo pageInfo) throws Exception{
		String pagingSQL = DatabaseUtil.getPagingSQL(sql, pageInfo);
		log.debug("executeList-----pagingSQL: "+pagingSQL);
		ResultSet rs = null;
		Statement stmt = null;
		try{
			stmt = this.conn.createStatement();
			rs = stmt.executeQuery(pagingSQL);
			List list = ResultSetUtil.rs2List(rs);
			return list;
		}catch (Exception e) {
			log.error("SQL="+sql);
			log.error(e.getMessage(), e);
			throw e;
		}finally{
			if(rs != null) rs.close();
			if(stmt != null) stmt.close();
		}
	}
	
	/**
	 * 获取数量
	 * @param sql= select count(1) as count from ...
	 * @return
	 * @throws Exception
	 */
	public Integer getCount(String sql) throws Exception{
		return getCount(sql,null);
	}
	
	/**
	 * 获取数量
	 * @param sql= select count(1) as count from ...
	 * @param params sql参数
	 * @return
	 * @throws Exception
	 */
	public Integer getCount(String sql,List<Proc> params) throws Exception{
		log.debug("execute---------SQL: "+sql);
		ResultSet rs = null;
		CallableStatement proc = null;
		try{
			Integer count = 0;
			proc = this.conn.prepareCall(sql);
			this.procParams(proc, params);
			rs = proc.executeQuery();
			if(rs.next()){
				count = rs.getInt("count");
			}
			return count;
		}catch (Exception e) {
			log.error("SQL="+sql);
			log.error(e.getMessage(), e);
			throw e;
		}finally{
			if(rs != null) rs.close();
			if(proc != null) proc.close();
		}
	}
	
	/**
	 * 更新或保存对象
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public boolean saveOrUpdate(Object type) throws Exception{
		if(this.conn == null) throw new NullPointerException("数据库连接失败！");
		boolean result = false;
		PreparedStatement proc = null;
		try{
			Class<?> clazz =  type.getClass();
			Annotation annotation = clazz.getAnnotation(Entity.class);
			if(annotation == null) return false;
			Entity entity = (Entity)annotation;
			String tableName = entity.table();
			StringBuffer sql = new StringBuffer("insert into " + tableName + " (");
			Field[] fields = clazz.getDeclaredFields();
			String columnPra = "";
			String valuePra = "";
			for(Field f : fields){
				f.setAccessible(true);
				Column column = (Column)f.getAnnotation(Column.class);
				if(column == null) continue;
				String columnName = column.value();
				if(f.get(type) == null) continue;
				if(DatabaseUtil.getDatabaseType().equals("oracle") 
						&& column.clob()) continue;//oralce数据库且clob字段
				columnPra += ","+columnName;
				valuePra += ",?";
			}
			if(columnPra.length()==0) return false;
			sql.append(columnPra.substring(1)+") values("+valuePra.substring(1)+")");
			log.debug("execute---------SQL: "+sql);
			proc = this.conn.prepareStatement(sql.toString());
			int i = 1;
			for(Field f : fields){
				f.setAccessible(true);
				Column column = (Column)f.getAnnotation(Column.class);
				if(column == null) continue;
				Object value = f.get(type);
				if(value == null) continue;
				if(DatabaseUtil.getDatabaseType().equals("oracle") 
						&& column.clob()) continue;//oralce数据库且clob字段
			   
				if(f.getType() == String.class){
					proc.setString(i, StringUtil.trim(String.valueOf(value)));
				}else if(f.getType() == Date.class){
					proc.setTimestamp(i, new Timestamp(((Date)value).getTime()));
				}else if(f.getType() == Integer.class || f.getType() == int.class){
					proc.setInt(i, (Integer)value);
				}else if(f.getType() == Long.class || f.getType() == long.class){
					proc.setLong(i, (Long)value);
				}else if(f.getType() == Float.class || f.getType() == float.class){
					proc.setDouble(i, (Float)value);
				}else if(f.getType() == Double.class || f.getType() == double.class){
					proc.setDouble(i, (Double)value);
				}else if(f.getType() == Boolean.class || f.getType() == boolean.class){
					proc.setString(i, StringUtil.formatBoolean((Boolean)value));
				}
				i++;
			}
			result = proc.execute();
			updateClob(type);//保存结束后 在存CLOB大字段
			return result;
		}catch (Exception e) {
			if(StringUtil.trim(e.getMessage()).contains("ORA-00001")){
				return this.update(type);
			}else{
				log.error(e.getMessage(), e);
				throw e;
			}
		}finally{
			if(proc != null) proc.close();
		}
	}
	
	/**
	 * 保存对象
	 * @autor zzy 2016-10-27
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public boolean save(Object type) throws Exception{
	   boolean result = false;
	   PreparedStatement proc = null;
	   try{
		   Class<?> clazz =  type.getClass();
		   Annotation annotation = clazz.getAnnotation(Entity.class);
		   if(annotation == null) return false;
		   Entity entity = (Entity)annotation;
		   String tableName = entity.table();
		   String sql = "insert into " + tableName + " (";
		   Field[] fields = clazz.getDeclaredFields();
		   String columnPra = "";
		   String valuePra = "";
		   for(Field f : fields){
			   f.setAccessible(true);
			   Column column = (Column)f.getAnnotation(Column.class);
			   if(column == null) continue;
			   String columnName = column.value();
			   if(f.get(type) == null) continue;
			   if(DatabaseUtil.getDatabaseType().equals("oracle") 
						&& column.clob()) continue;//oralce数据库且clob字段
			   columnPra += ","+columnName;
			   valuePra += ",?";
		   }
		   if(columnPra.length()==0) return false;
		   sql += columnPra.substring(1)+") values("+valuePra.substring(1)+")";
		   log.debug("execute---------SQL: "+sql);
		   proc = this.conn.prepareStatement(sql);
		   int i = 1;
		   for(Field f : fields){
			   f.setAccessible(true);
			   Column column = (Column)f.getAnnotation(Column.class);
			   if(column == null) continue;
			   Object value = f.get(type);
			   if(value == null) continue;
			   if(DatabaseUtil.getDatabaseType().equals("oracle") && column.clob()) continue;//oralce数据库且clob字段
			   
			   if(f.getType() == String.class){
				   proc.setString(i, StringUtil.trim(String.valueOf(value)));
			   }else if(f.getType() == Date.class){
				   proc.setTimestamp(i, new Timestamp(((Date)value).getTime()));
			   }else if(f.getType() == Integer.class || f.getType() == int.class){
				   proc.setInt(i, (Integer)value);
			   }else if(f.getType() == Long.class || f.getType() == long.class){
				   proc.setLong(i, (Long)value);
			   }else if(f.getType() == Float.class || f.getType() == float.class){
				   proc.setDouble(i, (Float)value);
			   }else if(f.getType() == Double.class || f.getType() == double.class){
				   proc.setDouble(i, (Double)value);
			   }else if(f.getType() == Boolean.class || f.getType() == boolean.class){
				   proc.setString(i, StringUtil.formatBoolean((Boolean)value));
			   }
			   i++;
		   }
		   result = proc.execute();
		   updateClob(type);//保存结束后 在存CLOB大字段
		   return result;
	   }catch (Exception e) {
		   log.error(e.getMessage(), e);
		   throw e;
	   }finally{
			if(proc != null) proc.close();
	   }
	}
	
	/**
	 * 更新对象（CLOB字段不作处理） 注意：对象主键必须id
	 * @autor zzy 2015-07-26
	 * @param type 
	 * @return
	 * @throws Exception
	 */
	public boolean update(Object type) throws Exception{
		boolean result = false;
		PreparedStatement proc = null;
		try{
			updateClob(type);//先更新CLOB大字段
			Class<?> clazz =  type.getClass();
			Annotation annotation = clazz.getAnnotation(Entity.class);
			if(annotation == null) return false;
			Entity entity = (Entity)annotation;
			String tableName = entity.table();
			String partition = entity.partition();
			String key = entity.key().toLowerCase();
			if(StringUtil.isNull(key)) return false;
			
			StringBuffer sql = new StringBuffer("update " + tableName + " set ");
			Field[] fields = clazz.getDeclaredFields();
			StringBuffer columnPra = new StringBuffer();
			for(Field f : fields){
				if(f.getName().equals(key)) continue;//主键
				if(f.getName().equals(partition)) continue;//分区字段
				f.setAccessible(true);
				Column column = (Column)f.getAnnotation(Column.class);
				if(column == null) continue;
				String columnName = column.value();
				Object value = f.get(type);
				if(value == null) continue;
				if(DatabaseUtil.getDatabaseType().equals("oracle") && column.clob()) continue;//oralce数据库且clob字段
				if(columnPra.length() == 0){
					columnPra.append(columnName).append(" = ? ");
				}else{
					columnPra.append(", ").append(columnName).append(" = ? ");
				}
			}
			if(columnPra.length() == 0) return false;
			sql.append(columnPra);
			
			Field keyField = clazz.getDeclaredField(key.toLowerCase());
			keyField.setAccessible(true);
			Column keyColumn = (Column)keyField.getAnnotation(Column.class);
			String keyColumnName = keyColumn.value();
			Object keyValue = keyField.get(type);
			
			if(!StringUtil.isNull(partition)){
				Field partitionField = clazz.getDeclaredField(partition);
				partitionField.setAccessible(true);
				Column partitionColumn = (Column)partitionField.getAnnotation(Column.class);
				String partitionColumnName = partitionColumn.value();
				Object partitionValue = partitionField.get(type);
				sql.append(" where ").append(partitionColumnName).append(" = ").append(String.valueOf(partitionValue))
				   .append(" and ").append(keyColumnName).append(" = ? ");
			}else{
				sql.append(" where ").append(keyColumnName).append(" = ? ");
			}
			log.debug("execute---------SQL: "+sql.toString());
			proc = this.conn.prepareStatement(sql.toString());
			int i = 1;
			for(Field f : fields){
				if(f.getName().equals(key)) continue;//主键
				if(f.getName().equals(partition)) continue;//分区字段
				f.setAccessible(true);
				Column column = (Column)f.getAnnotation(Column.class);
				if(column == null) continue;
				Object value = f.get(type);
				if(value == null) continue;
				if(DatabaseUtil.getDatabaseType().equals("oracle") && column.clob()) continue; //oracle数据库且clob字段
						   
				if(f.getType() == String.class){
					proc.setString(i, StringUtil.trim(String.valueOf(value)));
				}else if(f.getType() == Date.class){
					proc.setTimestamp(i, new Timestamp(((Date)value).getTime()));
				}else if(f.getType() == Integer.class || f.getType() == int.class){
					proc.setInt(i, (Integer)value);
				}else if(f.getType() == Long.class || f.getType() == long.class){
					proc.setLong(i, (Long)value);
				}else if(f.getType() == Float.class || f.getType() == float.class){
					proc.setDouble(i, (Float)value);
				}else if(f.getType() == Double.class || f.getType() == double.class){
					proc.setDouble(i, (Double)value);
				}else if(f.getType() == Boolean.class || f.getType() == boolean.class){
					proc.setString(i, StringUtil.formatBoolean((Boolean)value));
				}
				i++;
			}
			proc.setString(i, StringUtil.trim(String.valueOf(keyValue)));
			result = proc.execute();
			return result;
		}catch (Exception e) {
			log.error(e.getMessage(), e);
			throw e;
		}finally{
			if(proc != null) proc.close();
		}
	}
	   
	
	/**
	 * 删除对象
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public boolean delete(Object type)throws Exception{
		boolean result = false;
		String sql = "";
		try{
			Class<?> clazz =  type.getClass();
			Annotation annotation = clazz.getAnnotation(Entity.class);
			if(annotation == null) return false;
			Entity entity = (Entity)annotation;
			String tableName = entity.table();
			
			String key = entity.key();
			Field keyField = clazz.getDeclaredField(key.toLowerCase());
			keyField.setAccessible(true);
			Column keyColumn = (Column)keyField.getAnnotation(Column.class);
			String keyColumnName = keyColumn.value();
			Object keyValue = keyField.get(type);
			
			sql = "delete from "+tableName+" where "+keyColumnName+"='"+keyValue+"'";
			result = this.execute(sql);
		}catch (Exception e) {
			log.error("SQL="+sql);
			log.error(e.getMessage(), e);
			throw e;
		}
		return result;
	}
	
	/**
	 * 通过ID获取对象
	 * @param clazz_
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Object getEntity(Class<?> clazz,String id) throws Exception{
		Statement stmt = null;
		ResultSet rs = null;		
		try{
			if(StringUtil.isNull(id)) return null;
			Annotation annotation = clazz.getAnnotation(Entity.class);
			if(annotation == null) return null;
			Entity entity = (Entity)annotation;
			String tableName = entity.table();
			
			String key = entity.key();
			Field keyField = clazz.getDeclaredField(key.toLowerCase());
			keyField.setAccessible(true);
			Column keyColumn = (Column)keyField.getAnnotation(Column.class);
			String keyColumnName = keyColumn.value();
			String sql = "select * from "+tableName+" where "+keyColumnName+"='"+id+"'";
			log.debug("execute---------SQL: "+sql);
			
			stmt = this.conn.createStatement();
			rs = stmt.executeQuery(sql);
			ResultSetMetaData rsmd = rs.getMetaData();
			int colCount = rsmd.getColumnCount();
			Field[] fields = clazz.getDeclaredFields();
			Object type = null;clazz.newInstance();
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
				type = clazz.newInstance();
				for(Field f : fields){
					f.setAccessible(true);
					Column column = (Column)f.getAnnotation(Column.class);
					if(column == null) continue;
					String columnName = column.value().toUpperCase();
					if(bean.get(columnName) == null) continue;
					if(f.getType() == String.class){
						f.set(type, StringUtil.trim(String.valueOf(bean.get(columnName))));
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
			}
			return type;
		}catch (Exception e) {
			log.error(e.getMessage(), e);
			throw e;
		}finally{
			if(rs != null) rs.close();
			if(stmt != null) stmt.close();
		}
	}
	
	/**
	 * 如果有CLOB字段 先更新CLOB字段 oracle数据库使用
	 * @param fields
	 * @param clazz
	 * @param type
	 * @return
	 * @throws Exception
	 */
	private Boolean updateClob(Object type) throws Exception{
		if(!DatabaseUtil.getDatabaseType().equals("oracle")){//非oralce数据库
			return true;
		}
		Class<?> clazz =  type.getClass();
		Field[] fields = clazz.getDeclaredFields();
		
		Annotation annotation = clazz.getAnnotation(Entity.class);
		if(annotation == null) return false;
		Entity entity = (Entity)annotation;
		String tableName = entity.table();
		
		String key = entity.key();
		Field keyField = clazz.getDeclaredField(key.toLowerCase());
		
		keyField.setAccessible(true);
		Column keyColumn = (Column)keyField.getAnnotation(Column.class);
		String keyColumnName = keyColumn.value();
		String keyValue = StringUtil.trim(String.valueOf(keyField.get(type)));
		
		for(Field f : fields){
			f.setAccessible(true);
			Column column = (Column)f.getAnnotation(Column.class);
			if(column == null) continue;
			String columnName = column.value();
			if(!column.clob()) continue;
			if(f.getType() != String.class) continue;
			String value = String.valueOf(f.get(type));
			if(value == null) continue;
			String emptyCLOB = "update "+tableName+ " set "+columnName+"=empty_clob() where "+keyColumnName+"='"+keyValue+"'";
			String updateClob = "select "+columnName+ " from "+tableName+" where "+keyColumnName+"='"+keyValue+"' for update ";
			myUpdateClob(emptyCLOB,updateClob, value);
		}
		return true;
	}
	
	/**
	 * 执行update clob 
	 * @param sql  例 select FORM_HTML from TB_WF_TEMP_FORM where FORM_ID='xxxx'for update
	 * @param clobStr  需要跟新的FORM_HTML大字段的值    注意在插入是要用empty_clob()替代FORM_HTML的值
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public boolean myUpdateClob(String emptyCLOB,String updateClob, String clobStr) throws SQLException,
			Exception {
		Statement stmt = null;
		ResultSet rs = null;
		try {
			log.debug("execute---------SQL: "+emptyCLOB);
			log.debug("execute---------SQL: "+updateClob);
			log.debug("CLOB------------PAR: "+clobStr);
			stmt = conn.createStatement();
			conn.setAutoCommit(false);
			stmt.executeQuery(emptyCLOB);
			rs = stmt.executeQuery(updateClob);
			CLOB clob = null;
			if (rs.next()) {
				clob = (CLOB) rs.getClob(1);
			}
			Writer writer = clob.getCharacterOutputStream();
			writer.write(clobStr);
			writer.flush();
			writer.close();
			conn.commit();
			conn.setAutoCommit(true);
			return true;
		} catch (Exception e) { 
			log.error(e.getMessage(),e);
			return false;
		}finally{
			if(rs != null) rs.close();
			if(stmt != null) stmt.close();
		}
	}
	
	/** SQL得到分页查询结果 */
	public PageInfo findPageBySQL(PageInfo pageInfo,String sql) throws Exception{
		if(pageInfo == null) return null;
		String pagingSQL = DatabaseUtil.getPagingSQL(sql, pageInfo);
		List<Map<String,String>> list = findBySQL(pagingSQL);
		pageInfo.setGridModel(list);
		int index = sql.indexOf("from");
		if(index == -1) sql.indexOf("FROM");
		String countSQL = "select count(*) as count " + sql.substring(index);
		Integer record = this.getCount(countSQL,null);
		pageInfo.setRecord(record);
		return pageInfo;
	}
	
	/** HQL得到分页查询结果  from User t where ... */
	public PageInfo findPageByHQL(PageInfo pageInfo,String hql) throws Exception{
		if(pageInfo == null) return null;
		log.debug("executeList-----HQL: "+hql);
		if(StringUtil.isNull(hql)) return null;
		Statement stmt = null;
		ResultSet rs = null;
		try{
			Map<String,String> entityClassMap = PackageUtil.getEntityMap();
			String[] elements = hql.split(" ");
			String className = "";
			String alias = "";
			for(String s : elements){
				if(s.length() == 0) continue;
				if(s.equalsIgnoreCase("from")) continue;
				if(s.equalsIgnoreCase("where"))break;
				if(!StringUtil.isNull(alias)) break;
				if(StringUtil.isNull(className)){
					className = entityClassMap.get(s);
				}else{
					alias = s;
				}
			}
			if(StringUtil.isNull(className)){
				throw new NullPointerException("Include Undefined Entity 【"+hql+"】");
			}
			Class<?> clazz = Class.forName(className);
			Entity entity = clazz.getAnnotation(Entity.class);
			String tableName = entity.table();
			String sql = "select * from "+tableName;
			if(!StringUtil.isNull(alias)) sql += " " + alias; 
			int index = hql.toLowerCase().indexOf(" where ");
			if(index != -1 ){
				String condition = hql.substring(index);
				Field[] fields = clazz.getDeclaredFields();
				for(Field f : fields){
					Column column = f.getAnnotation(Column.class);
					if(column == null) continue;
					String name = f.getName();
					String columnName = column.value();
					if(name.equalsIgnoreCase(columnName)) continue;
					condition = StringUtil.formatCondition(condition,name,columnName);
				}
				sql += " " + condition;
				
			}
			log.debug("executeList-----SQL: "+sql);
			String pagingSQL = DatabaseUtil.getPagingSQL(sql, pageInfo);//分页sql
			log.debug("executeList-----SQL: "+pagingSQL);
			stmt = this.conn.createStatement();
			rs = stmt.executeQuery(pagingSQL);
			List<Object> listObject = ResultSetUtil.rs2List(rs, clazz);
			pageInfo.setGridModel(listObject);
			
			String countSQL = "select count(1) as count " + sql.substring(sql.indexOf("from"));
			Integer record = this.getCount(countSQL,null);
			pageInfo.setRecord(record);
			return pageInfo;
		}catch (Exception e) {
			throw e;
		}finally{
			if(rs != null) rs.close();
			if(stmt != null) stmt.close();
		}
	}
	
	/**
	 * sql注入参数
	 * @param proc
	 * @param params
	 * @throws SQLException
	 */
	private void procParams(CallableStatement proc,List<Proc> params) throws SQLException{
		if(proc == null || params == null) return;
		for (int i = 0; i < params.size(); i++) {
			String dataType = params.get(i).getType();
			String value = StringUtil.trim(String.valueOf(params.get(i).getValue()));
			if (dataType.equalsIgnoreCase("string")) {
				proc.setString(i+1, value);
			} else if (dataType.equalsIgnoreCase("int")) {
				proc.setInt(i+1, Integer.parseInt(value));
			} else if (dataType.equalsIgnoreCase("double")) {
				proc.setDouble(i+1, Double.parseDouble(value));
			}
		}
	}
	
	public static void main(String[] args) {
		String hql = "select * from vi_attend t dd where m.states!=0 and '2017-03-13 11:43:42' Between m.startValidity and m.endValidity ";
		int index = hql.toLowerCase().indexOf(" where ");
		if(index != -1 ){
			String sql = hql.substring(hql.toLowerCase().indexOf(" left "), index);
			System.out.println(sql+"321");
		}
	}
	
}
