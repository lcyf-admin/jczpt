package cn.lhkj.commons.tools;

import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;
import cn.lhkj.commons.base.BaseDataCode;
import cn.lhkj.commons.util.CalendarUtil;
import cn.lhkj.commons.util.StringUtil;
import cn.lhkj.project.system.entity.Orgzon;

/**
 * 三汇比对服务的请求专用
 * @author Administrator
 *
 */
public class HttpPostSANHUI {
	
	private final static Logger logger = Logger.getLogger(HttpPostSANHUI.class);
	private final static String key = "NDVkMmM5NDdkNTIyZGM5YTI5NzhjYzI4YWRjZTRmYzM2OTgwNjVkNQ==";
	private static Integer timeOut = 5000;//请求超时时间10秒--测试时使用
	
	/**
	 * 将颜色处理成白黄蓝黑
	 * @param value
	 * @return
	 */
	public static String dealWithPlateColor(String value){
		if(StringUtil.isNull(value)) return null;
		value = value.substring(0, 1);
		if("白黄蓝黑".indexOf(value) == -1) return null;
		return value;
	}
	
	//////////////////////////////////////////////////////////
	
	/**
	 * 发送HttpPost请求
	 * @param strURL 请求地址
	 * @param type person或plate或mac或imei,imsi
	 * @param map 身份证号或者车牌号或者mac等
	 * @return 成功:返回json字符串
	 */
	public static String sendPost(String strURL,String type, Map<String,String> map,Orgzon orgzon) {
		if(StringUtil.isNull(strURL)) return null;
		if(StringUtil.isNull(type)) return null;
		if(map == null) return null;
		if(orgzon == null) return null;
		if (StringUtil.isNull(orgzon.getX())) return null;
		if (StringUtil.isNull(orgzon.getY())) return null;
		if (StringUtil.isNull(orgzon.getCodes())) return null;
		if (StringUtil.isNull(orgzon.getNames())) return null;
		map.put("longitude", orgzon.getX());
		map.put("latitude", orgzon.getY());
		map.put("stationid", orgzon.getCodes().substring(0, 6));
		map.put("areacode", orgzon.getCodes().substring(0, 6));
		map.put("location", orgzon.getNames());
		map.put("passtime", CalendarUtil.format(new Date(), "yyyyMMddHHmmSS"));
		String timestamp = CalendarUtil.format(new Date(),"yyyyMMddHHmmss");
		String sign = new MD5().getMD5ofStr(key+timestamp).toLowerCase();
		HashMap<String, Object> messageMap = new HashMap<String, Object>();
		messageMap.put("type", type);
		messageMap.put("data", map);
			
		StringBuffer buffer = new StringBuffer();
		buffer.append("message=").append(StringUtil.obj2json(messageMap));
		buffer.append("&timestamp=").append(timestamp);
		buffer.append("&sign=").append(sign);
		buffer.append("&key=").append(key);
        try {
        	Integer timeOut = BaseDataCode.config.getTimeOut();
            URL url = new URL(strURL);
            // 打开和URL之间的连接
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setUseCaches(false);
            connection.setConnectTimeout(timeOut);//设置建立连接的超时时间
			connection.setReadTimeout(timeOut);//设置传递数据的超时时间
			connection.setInstanceFollowRedirects(true);//自动处理重定向
			connection.setRequestMethod("POST"); // 设置请求方式
			
			connection.setRequestProperty("Charset","utf-8");
            connection.setRequestProperty("Accept", "*/*");//接收任意资源
            //connection.setRequestProperty("Content-Type", "application/json;charset=utf-8");//设置发送数据格式
            // 获取URLConnection对象对应的输出流
            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(),"UTF-8");
            out.write(buffer.toString());//发送请求参数
            out.flush(); // flush输出流的缓冲
            out.close();
            // 定义 BufferedReader输入流来读取URL的响应
            InputStream is = connection.getInputStream();
            return StringUtil.convertStreamToString(is);
        }catch (Exception e) {
        	logger.error("大数据平台比对【"+strURL+"】【 "+e.getMessage()+"】【 "+map.toString()+"】");
			return null;
        }
	}
	
	/**
	 * 发送HttpPost请求
	 * @param strURL 请求地址
	 * @param type person或plate或mac或imei,imsi
	 * @param map 身份证号或者车牌号或者mac等
	 * @return 成功:返回json字符串
	 */
	public static String sendPostTest(String strURL,String type, Map<String,String> map) {
		if(StringUtil.isNull(strURL)) return null;
		if(StringUtil.isNull(type)) return null;
		if(map == null) return null;
		String timestamp = CalendarUtil.format(new Date(),"yyyyMMddHHmmss");
		String sign = new MD5().getMD5ofStr(key+timestamp).toLowerCase();
		HashMap<String, Object> messageMap = new HashMap<String, Object>();
		messageMap.put("type", type);
		messageMap.put("data", map);
			
		StringBuffer buffer = new StringBuffer();
		buffer.append("message=").append(StringUtil.obj2json(messageMap));
		buffer.append("&timestamp=").append(timestamp);
		buffer.append("&sign=").append(sign);
		buffer.append("&key=").append(key);
        try {
            URL url = new URL(strURL);
            // 打开和URL之间的连接
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setUseCaches(false);
            connection.setConnectTimeout(timeOut);//设置建立连接的超时时间
			connection.setReadTimeout(timeOut);//设置传递数据的超时时间
			connection.setInstanceFollowRedirects(true);//自动处理重定向
			connection.setRequestMethod("POST"); // 设置请求方式
			
			connection.setRequestProperty("Charset","utf-8");
            connection.setRequestProperty("Accept", "*/*");//接收任意资源
            //connection.setRequestProperty("Content-Type", "application/json;charset=utf-8");//设置发送数据格式
            // 获取URLConnection对象对应的输出流
            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(),"UTF-8");
            out.write(buffer.toString());//发送请求参数
            out.flush(); // flush输出流的缓冲
            out.close();
            // 定义 BufferedReader输入流来读取URL的响应
            InputStream is = connection.getInputStream();
            return StringUtil.convertStreamToString(is);
        }catch (Exception e) {
        	logger.error("HttpPostSANHUI请求post地址:"+strURL+"----"+buffer.toString());
			logger.error("HttpPostSANHUI请求post异常:"+e.getMessage());
			return e.getClass()+"     "+e.getMessage();
        }
	}
	
	public static void main(String[] args) {
		String strURL = "http://21.0.23.78/dataXiLing";//熙菱手机侦码地址
		String key = "ZGJlNWKbJyVrMfDj22U2ZjkzYTgzYWMwNGQxNzBlNTViNjdkMGI0Yg==";
		String timestamp = CalendarUtil.format(new Date(),"yyyyMMddHHmmss");
		String sign = new MD5().getMD5ofStr(key+timestamp).toLowerCase();
		StringBuffer buffer = new StringBuffer();
		buffer.append("message=");
		buffer.append("&timestamp=").append(timestamp);
		buffer.append("&sign=").append(sign);
		buffer.append("&key=").append(key);
        try {
            URL url = new URL(strURL);
            // 打开和URL之间的连接
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setUseCaches(false);
            connection.setConnectTimeout(timeOut);//设置建立连接的超时时间
			connection.setReadTimeout(timeOut);//设置传递数据的超时时间
			connection.setInstanceFollowRedirects(true);//自动处理重定向
			connection.setRequestMethod("POST"); // 设置请求方式
			
			connection.setRequestProperty("Charset","utf-8");
            connection.setRequestProperty("Accept", "*/*");//接收任意资源
            //connection.setRequestProperty("Content-Type", "application/json;charset=utf-8");//设置发送数据格式
            // 获取URLConnection对象对应的输出流
            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(),"UTF-8");
            out.write(buffer.toString());//发送请求参数
            out.flush(); // flush输出流的缓冲
            out.close();
            // 定义 BufferedReader输入流来读取URL的响应
            InputStream is = connection.getInputStream();
            String s = StringUtil.convertStreamToString(is);
            System.out.println("s="+s);
        }catch (Exception e) {
        	logger.error("HttpPostSANHUI请求post地址:"+strURL+"----"+buffer.toString());
			logger.error("HttpPostSANHUI请求post异常:"+e.getMessage());
        }
	}
}