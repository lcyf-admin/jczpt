package cn.lhkj.commons.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.sf.json.JSONArray;
import org.apache.log4j.Logger;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import cn.lhkj.commons.base.BaseDataCode;
import cn.lhkj.commons.defined.Column;
import cn.lhkj.commons.defined.Entity;


public class StringUtil {
	static public final Logger log = Logger.getLogger(StringUtil.class);
	
	/**得到一个表单流水号*/
	public synchronized static String getFormSerial(){
		return CalendarUtil.format(new Date(),"yyMMddHHmmss")+String.valueOf(Math.random()).substring(2, 5);
	}
	
	public static String listToString(List<String> l){
		String relust = "";
		for(String s : l){
			relust += s;
		}
		return relust;
	}
	
	/**
	 * 判断字符串组是否包含字符串
	 * @param array
	 * @param value
	 * @return
	 */
	public static boolean contains(String[] array ,String value){
		for(String s : array){
			if(s.equals(value)) return true;
		}
		return false;
	}
	
	/**
	 * 判断字符还组是否模糊包含字符串
	 * @param array
	 * @param value
	 * @return
	 */
	public static boolean containsFyzzy(String[] array ,String value){
		if(isNull(value)) return false;
		for(String s : array){
			if(value.indexOf(s) != -1) return true;
		}
		return false;
	}
	
	/**将boolean值转成0和1*/
	public static String formatBoolean(Boolean b){
		if(b) return "1";
		return "0";
	}
	
	/**
	 * 在常规trim方法加入对null值的String类型的处理.
	 * Method: utilTrim
	 * @param str
	 * @return
	 * desc:
	 */
	public static String trim(String str){
		if(str == null) return "";
		str = str.trim();
		if(str.length()< 5 && str.toLowerCase().equals("null")) return "";
		return str;
	}
	
	public static String trim(Object str){
		if(str == null) return "";
		String s = String.valueOf(str).trim();
		if(s.length()< 5 && s.toLowerCase().equals("null")) return "";
		return s;
	}
	/**
	 * 把字符串数组转换为'xx','yy'格式.
	 * @param array
	 * @return
	 */
	public static String getSQLCaseParam(String[] array){
		String hql = "";
		if(array == null || array.length==0) return "''";
		for(String str : array){
			if(hql.length() > 0) hql += ",";
			hql += "'" + str + "'";
		}
		return hql;
	}
	
	
	/**数组转换为'xx','yy'格式. */
	public static String getSQLCaseParam(Object[] array){
		String hql = "";
		if(array == null || array.length==0) return "''";
		for(Object str : array){
			if(hql.toString().length() > 0) hql += ",";
			hql += "'" + str.toString() + "'";
		}
		return hql;
	}
	
	/**
	 * 把整形数组转换为3,4格式.
	 * @param array
	 * @return
	 */
	public static String getSQLCaseParam(Integer[] array){
		String hql = "";
		for(int num : array){
			if(hql.length() > 0) hql += ",";
			hql += num;
		}
		return hql;
	}
	/**
	 * 把字符串xx,yy组转换为'xx','yy'格式.
	 * @param array
	 * @return
	 * deptids.replaceAll("(\\w+)\\b|\\b(\\w+)", "'$1'");
	 */
	public static String getSQLCaseParam(String splitParam){
		if(StringUtil.trim(splitParam).length()==0) return "''"; 
		String[] array = splitParam.split(",");
		return getSQLCaseParam(array);
	}
	
	/**
	 * 转化为 a,b,c格式
	 * @param set
	 * @return
	 */
	public static String formatSet(Set<String> set){
		if(set == null) return "";
		String result = "";
		for(String s : set){
			if(result.length() == 0){
				result += s;
			}else{
				result += "," + s;
			}
		}
		return result;
	}
	
	/**
	 * 转化为 a,b,c格式
	 * @param set
	 * @return
	 */
	public static String formatList(List<String> list){
		if(list == null) return "";
		String result = "";
		for(String s : list){
			if(result.length() == 0){
				result += s;
			}else{
				result += "," + s;
			}
		}
		return result;
	}
	
	/**
	 * 转化为 'a','b','c'格式
	 * @param set
	 * @return
	 */
	public static String formatSetForSQL(Set<String> set){
		if(set == null) return "";
		String result = "";
		for(String s : set){
			if(result.length() == 0){
				result += "'" + s + "'";
			}else{
				result += ",'" + s + "'";
			}
		}
		return result;
	}
	
	/**获取工程部署的根目录 */
    public static String obtainRootPath(){
		String s = System.getProperty("webapp.root");
    	if(s==null){try{
    		s = ClassLoader.getSystemResource("").getPath();
    		}catch (Exception e) {}
    	}
    	if(s==null){try{
    			s = Class.class.getClass().getResource("/").getPath();
				s = URLDecoder.decode(s, "utf-8").substring(1);
    		}catch (Exception e) {}
    	}
    	if(s==null){try{
    		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();    
    			s = webApplicationContext.getServletContext().getRealPath("/");
    		}catch (Exception e) {}
    	}
    	if(s==null){try{
    			s = Thread.currentThread().getContextClassLoader().getResource("").getPath();
    		}catch (Exception e) {}
    	}
    	if(s==null){
    		s = System.getProperty("user.dir");
    		int index = s.indexOf("bin");
    		if(index != -1){
    			s = s.substring(0,index) + "webapps\\lnt\\";
    		}
    	}
		return s;
	}
    
    /**
     * 判断列表是否为空
     * @param list
     * @return
     */
	@SuppressWarnings("rawtypes")
	public static boolean isNull(List list){
    	return list == null || list.size() == 0 || list.get(0) == null ? true:false;
    }
	
	/**
     * 判断字符串不为空
     * @param arg
     * @return
     */
    public static boolean isNotNull(String arg){
    	if(arg != null && !"".equals(arg) && !"null".equals(arg))return true;
    	return false;
    }
    
    /**
     * 判断字符串是否无效
     * @param arg
     * @return
     */
    public static boolean isNull(String arg){
    	if(arg == null || "".equals(arg) || "null".equals(arg) || "undefined".equals(arg) || "NaN".equals(arg) || "Infinity".equals(arg) || "-Infinity".equals(arg))return true;
    	return false;
    }
    
    
    /**
     * 判断字符串UTF-8解码
     * @param arg
     * @return
     */
    public static String decodeStr(String str){
    	if(str != null && str.contains("%")){
			try {
				return URLDecoder.decode(str,"UTF-8");
			} catch (Exception e) {
				e.printStackTrace();
			}
    	}
    	return str;
    }
 
    public static String decodeStr2(String str){
    	str = decodeStr(str);
    	if(isNull(str))return str;
    	return str;
    }
    public static String encodeStr(String str,boolean isEncode){
    	if(isEncode && !isNull(str)){
    		try {
				str = URLEncoder.encode(str, "UTF-8");
			} catch (Exception e) {
				e.printStackTrace();
			}
    		return str;
    	}
    	return str;
    }
    
    public static String getString(String str){
    	if(isNull(str))return "";
    	return trim(str);
    }
    
    public static String formatCondition(String condition,String key,String value){
    	if(key.equals(value)) return condition;
    	condition = rep(condition," "+key+" ", " "+value+" ");
		condition = rep(condition,"."+key+" ", "."+value+" ");
		condition = rep(condition,"("+key+" ", "("+value+" ");
		condition = rep(condition,"("+key+",", "("+value+",");
		condition = rep(condition,"."+key+" ", "."+value+" ");
		condition = rep(condition," "+key+",", " "+value+",");
		condition = rep(condition,"."+key+",", "."+value+",");
		condition = rep(condition," "+key+"=", " "+value+"=");
		condition = rep(condition,"("+key+"=", "("+value+"=");
		condition = rep(condition,"."+key+"=", "."+value+"=");
		condition = rep(condition," "+key+"!", " "+value+"!");
		condition = rep(condition,"."+key+"!", "."+value+"!");
		condition = rep(condition," "+key+">", " "+value+">");
		condition = rep(condition,"."+key+">", "."+value+">");
		condition = rep(condition," "+key+"<", " "+value+"<");
		condition = rep(condition,"."+key+"<", "."+value+"<");
		if(condition.endsWith(" "+key)){
			condition = condition.substring(0, condition.lastIndexOf(" "+key))+" "+value+" ";
		}
		if(condition.endsWith("."+key)){
			condition = condition.substring(0, condition.lastIndexOf("."+key))+"."+value+" ";
		}
		return condition;
    }
	
    
    /**替换字符串 */
	public static String rep(String source, String oldV, String newV){
		if(isNull(source) || isNull(oldV) || isNull(newV)) return source;
		if(oldV.equals(newV)) return source;
		int index = source.indexOf(oldV);
		if(index == -1) return source;
		String frontPart = source.substring(0,index);	
		String backPart = source.substring(index+oldV.length(),source.length());
		return rep(frontPart+newV+backPart,oldV,newV);
	}
    
	/**
	 * 通过request设置查询参数（s./seq./sge./sle.）的SQL.
	 * @param requestParams
	 * @param clazz 实例中属性与column的对应
	 * @return
	 * @throws Exception 
	 */
	public static String getSQLByRequest(Map<String, Object> requestParams,Class<?> clazz) throws Exception{
		if(requestParams == null || clazz == null) return "";
		String sql = "";
		Annotation annotation = clazz.getAnnotation(Entity.class);
		if(annotation == null) return sql;
		Field[] fields = clazz.getDeclaredFields();
		Set<String>keySet = requestParams.keySet();
		for(String key : keySet){
			if("SESSION_BEAN".equals(key)) continue;
			String paraValue = requestParams.get(key).toString();//key对应的值
			if(StringUtil.isNull(paraValue)) continue;
			if(key.indexOf("s:") == 0){ //模糊查询条件，如姓名
				sql = sql + " and instr("+getColumnName(key.substring("s:".length()), fields)+", '"+paraValue+"')>0 ";
			}
			if(key.indexOf("seq:") == 0){ // 完全等于的查询条件，如部门id，数据字典
				if(paraValue.equals("true") || paraValue.equals("false")){
					sql = sql + " and " + getColumnName(key.substring("seq:".length()), fields) + " = "+Boolean.valueOf(paraValue);
				}else{
					sql = sql + " and " + getColumnName(key.substring("seq:".length()), fields) + " = '"+paraValue+"' ";
				}
			}
			if(key.indexOf("nseq:") == 0){ // 完全不等于的查询条件，如部门id，数据字典
				if(paraValue.equals("true")||paraValue.equals("false")){
					sql = sql + " and " + getColumnName(key.substring("nseq:".length()), fields) + " != " + Boolean.valueOf(paraValue);
				}else{
					sql = sql + " and " + getColumnName(key.substring("nseq:".length()), fields) + " != '" + paraValue + "' ";
				}
			}
			if(key.indexOf("sle:") == 0){ //小于等于查询条件，如时间，金额
				paraValue = CalendarUtil.getNextDay(paraValue);
				if("oracle".equals(DatabaseUtil.getDatabaseType())){
					sql = sql + " and " + getColumnName(key.substring("sle:".length()), fields) + " <= to_date('"+paraValue+"','yyyy-MM-dd')";
				}else{
					sql = sql + " and " + getColumnName(key.substring("sle:".length()), fields) + " <= '"+paraValue+"' ";
				}
			}
			if(key.indexOf("sge:") == 0){//大于等于查询条件，如时间，金额
				if("oracle".equals(DatabaseUtil.getDatabaseType())){
					sql = sql + " and " + getColumnName(key.substring("sge:".length()), fields) + " >= to_date('"+paraValue+"','yyyy-MM-dd')";
				}else{
					sql = sql + " and " + getColumnName(key.substring("sge:".length()), fields) + " >= '"+paraValue+"' ";
				}
			}
			if(key.indexOf("sleo:") == 0){ //小于查询条件，如时间，金额
				if("oracle".equals(DatabaseUtil.getDatabaseType())){
					sql = sql + " and " + getColumnName(key.substring("sleo:".length()), fields) + " < to_date('"+paraValue+"','yyyy-MM-dd')";
				}else{
					sql = sql + " and " + getColumnName(key.substring("sleo:".length()), fields) + " < '"+paraValue+"' ";
				}
			}
			if(key.indexOf("sgeo:") == 0){//大于查询条件，如时间，金额
				if("oracle".equals(DatabaseUtil.getDatabaseType())){
					sql = sql + " and " + getColumnName(key.substring("sgeo:".length()), fields) + " > to_date('"+paraValue+"','yyyy-MM-dd')";
				}else{
					sql = sql + " and " + getColumnName(key.substring("sgeo:".length()), fields) + " > '"+paraValue+"' ";
				}
			}
			
			if(key.indexOf("sin:") == 0){//多个值
				sql += " and " + getColumnName(key.substring("sin:".length()), fields) + " in (" + getSQLCaseParam(paraValue) + ")";
			}
			if(key.indexOf("nsin:") == 0){//不等于 多个值
				sql += " and " +  getColumnName(key.substring("nsin:".length()), fields) + " not in (" + getSQLCaseParam(paraValue) + ")";
			}
			
			/**或者*/
			if(key.indexOf("ors:") == 0){ //模糊查询条件，如姓名
				sql = sql + " or instr("+getColumnName(key.substring("ors:".length()), fields)+", '"+paraValue+"')>0 ";
			}
			if(key.indexOf("orseq:") == 0){ // 完全等于的查询条件，如部门id，数据字典
				if(paraValue.equals("true") || paraValue.equals("false")){
					sql = sql + " or " + getColumnName(key.substring("orseq:".length()), fields) + " = "+Boolean.valueOf(paraValue);
				}else{
					sql = sql + " or " + getColumnName(key.substring("orseq:".length()), fields) + " = '"+paraValue+"' ";
				}
			}
			if(key.indexOf("ornseq:") == 0){ // 完全不等于的查询条件，如部门id，数据字典
				if(paraValue.equals("true")||paraValue.equals("false")){
					sql = sql + " or " + getColumnName(key.substring("ornseq:".length()), fields) + " != " + Boolean.valueOf(paraValue);
				}else{
					sql = sql + " or " + getColumnName(key.substring("ornseq:".length()), fields) + " != '" + paraValue + "' ";
				}
			}
			if(key.indexOf("orsle:") == 0){ //小于查询条件，如时间，金额
				paraValue = CalendarUtil.getNextDay(paraValue);
				if("oracle".equals(DatabaseUtil.getDatabaseType())){
					sql = sql + " or " + getColumnName(key.substring("orsle:".length()), fields) + " <= to_date('"+paraValue+"','yyyy-MM-dd')";
				}else{
					sql = sql + " or " + getColumnName(key.substring("orsle:".length()), fields) + " <= '"+paraValue+"' ";
				}
				
			}
			if(key.indexOf("orsge:") == 0){//大于查询条件，如时间，金额
				if("oracle".equals(DatabaseUtil.getDatabaseType())){
					sql = sql + " or " + getColumnName(key.substring("orsge:".length()), fields) + " >= to_date('"+paraValue+"','yyyy-MM-dd')";
				}
				sql = sql + " or " + getColumnName(key.substring("orsge:".length()), fields) + " >= '"+paraValue+"' ";
			}
			if(key.indexOf("orsin:") == 0){//多个值
				sql += " or " + getColumnName(key.substring("orsin:".length()), fields) + " in (" + getSQLCaseParam(paraValue) + ")";
			}
			if(key.indexOf("ornsin:") == 0){//不等于 多个值
				sql += " or " +  getColumnName(key.substring("ornsin:".length()), fields) + " not in (" + getSQLCaseParam(paraValue) + ")";
			}
		}
		return sql;
	}
	
	/**
	 * 获取类字段对应的数据库列字段
	 * @param key
	 * @param fields
	 * @return
	 */
	private static String getColumnName(String key,Field[] fields){
		String columnName = key;
		for(Field f : fields){
			Column column = f.getAnnotation(Column.class);
			if(column == null) continue;
			if(key.equals(f.getName())){
				columnName = column.value();
				break;
			}
		}
		return columnName;
	}
	
	/**
	 * 替换SQL查询条件中的单引号
	 * @param variable
	 * @return
	 */
	public static String replaceSQLVariable(String variable){
		return variable.replaceAll("'", "''");
	}
	
	/**获取卸载码的算法*/
	public static String converUninstallCode(String uninstallStr)throws Exception{
		if(isNull(uninstallStr)) return "";
		String subStr = "";
		String resultStr = "";
		for (int i = 4; i < uninstallStr.length(); i = i + 3) {
			subStr += String.valueOf((Integer.parseInt(
					uninstallStr.substring(i, i + 3), 16) + 101));
		}
		for (int i = 0; i < subStr.length(); i = i + 2) {
			resultStr += uninstallStr.substring((subStr.length() - i) / 2,
					((subStr.length() - i) / 2) + 1);
			String resultHexStr = Integer.toHexString(Integer
					.valueOf(subStr.substring(i, i + 2)));
			resultHexStr = resultHexStr.length() == 1 ? "0" + resultHexStr
					: resultHexStr;
			resultStr += resultHexStr;
		}
		resultStr += uninstallStr.substring(0, 1);
		return resultStr.toUpperCase();
	}
	
	/**判断字符串是否在数组中*/
	public static boolean inArray(String key,String[] array){
		if(key == null || array == null) return false;
		for(String s : array){
			if(key.equals(s)) return true;
		}
		return false;
	}
    
	/**将对象转成json：{xxx:1,yy:2,z:3}*/
	public static String obj2json(Object obj){
		String result = JSONArray.fromObject(obj).toString();
		result = result.substring(1, result.length()-1);
		log.debug(result);
		return result;
	}
	
	/**将对象转成json：[{xxx:1,yy:2,z:3}]*/
	public static String obj2Array(Object obj){
		String result = JSONArray.fromObject(obj).toString();
		log.debug(result);
		return result;
	}
	
	/**
	 * 将流转成字符串
	 * @param is
	 * @return
	 */
	public static String convertStreamToString(InputStream is) {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf-8"));
			StringBuffer sb = new StringBuffer();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			return sb.toString();
		}catch (Exception e) {
			return "";
		}finally {
			try {
				if(is != null) is.close();
			} catch (IOException e) {}
		}
	}
	
	/**
	 * 通过身份证号计算年龄
	 * @param s
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static String getAge(String s){
		try {
			if (isNull(s)) return "-";
			if (s.length() != 18) return "-";
			String yy1 = s.substring(6, 10); // 出生的年份
			String mm1 = s.substring(10, 12); // 出生的月份
			String dd1 = s.substring(12, 14); // 出生的日期
			String birthday = yy1.concat("-").concat(mm1).concat("-").concat(dd1);
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd");
			String s1 = sdf.format(date);
			Date today = sdf.parse(s1);
			Date birth = sdf.parse(birthday);
			return String.valueOf(today.getYear() - birth.getYear());
		} catch (ParseException e) { }
		return "-";
	}
	
	/***
	 * 判断请求的IP是否有效
	 * @param ip
	 * @return
	 */
	public static boolean isLegal(String ip){
		if("127.0.0.1".equals(ip)) return true;
		String legalIds = BaseDataCode.config.getLegalIds();
		if(isNull(legalIds)) return false;
		String[] ips = legalIds.split(",");
		for(String s : ips){
			if(ip.indexOf(s) != -1){
				return true;
			}
		}
		return false;
	}
}
