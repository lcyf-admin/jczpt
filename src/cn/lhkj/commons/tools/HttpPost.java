package cn.lhkj.commons.tools;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.log4j.Logger;

import cn.lhkj.commons.base.BaseDataCode;
import cn.lhkj.commons.util.CalendarUtil;
import cn.lhkj.commons.util.StringUtil;

public class HttpPost {
	
	private static final Logger logger = Logger.getLogger(HttpPost.class);
	
	private static Integer timeOut = 5000;//请求超时时间10秒--测试时使用
	
	/**
	 * 获取请求服务器的时间
	 * @param strURL
	 * @param params
	 * @return
	 */
	public static Long getServiceTime(String strURL, String params){
		InputStream is = null;
		try {
			URL url = new URL(strURL);// 创建连接
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setUseCaches(false);
			connection.setConnectTimeout(2000);//设置建立连接的超时时间
			connection.setReadTimeout(2000);//设置传递数据的超时时间
			connection.setInstanceFollowRedirects(true);
			connection.setRequestMethod("POST"); // 设置请求方式
			
			connection.setRequestProperty("Charset","utf-8");
            connection.setRequestProperty("Accept", "*/*");//接收任意资源
            connection.setRequestProperty("Content-Type", "application/json;charset=utf-8");//设置发送数据格式
            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8"); // utf-8编码
			out.append(params);
			out.flush();
			out.close();
			// 读取响应
			is = connection.getInputStream();
			long relust = connection.getDate();
			return relust;
		}catch (Exception e) {
			logger.error("数据门接口访问失败："+strURL+"  getServiceTime()");
			return null;
		}finally{
			try {
				if(is != null) is.close();
			} catch (IOException e) {}
		}
	}
	
	//////////////////////////////////////////////////////////////
	
	/**
     * 向指定URL发送GET方法的请求
     * @param strURL  发送请求的URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String strURL, String param) {
    	if(StringUtil.isNull(strURL)) return null;
        try {
        	Integer timeOut = BaseDataCode.config.getTimeOut();
        	long time = System.currentTimeMillis();
            String urlNameString = strURL+"?time="+time+"&"+param;
            URL url = new URL(urlNameString);
            // 打开和URL之间的连接
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			connection.setUseCaches(false);
            connection.setConnectTimeout(timeOut);//设置建立连接的超时时间
			connection.setReadTimeout(timeOut);//设置传递数据的超时时间
			connection.setInstanceFollowRedirects(true);//自动处理重定向
			connection.setRequestMethod("GET"); // 设置请求方式
			connection.setRequestProperty("Charset","utf-8");
            connection.setRequestProperty("Accept", "*/*");//接收任意资源
            connection.setRequestProperty("Content-Type", "application/json;charset=utf-8");//设置发送数据格式
            // 建立实际的连接
            connection.connect();
            // 定义 BufferedReader输入流来读取URL的响应
            InputStream is = connection.getInputStream();
            return StringUtil.convertStreamToString(is);
        } catch (Exception e) {
        	logger.error("HttpRequest-ERROR:"+e.getMessage()+"----"+strURL+"?"+param);
			return null;
        }
    }
    
    /**
	 * 发送HttpPost请求
	 * @param strURL 服务地址
	 * @param params json字符串,
	 * @return 成功:返回json字符串<br/>
	 */
    public static String sendPost(String strURL, String params) {
		if(StringUtil.isNull(strURL)) return null;
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
            connection.setRequestProperty("Content-Type", "application/json;charset=utf-8");//设置发送数据格式
            // 获取URLConnection对象对应的输出流
            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(),"UTF-8");
            out.write(params);//发送请求参数
            out.flush(); // flush输出流的缓冲
            out.close();
            // 定义 BufferedReader输入流来读取URL的响应
            InputStream is = connection.getInputStream();
            return StringUtil.convertStreamToString(is);
        }catch (Exception e) {
        	logger.error("HttpRequest-ERROR:"+e.getMessage()+"----"+strURL);
			return null;
        }
	}
	
	/**
	 * 发送HttpPost请求
	 * @param strURL 服务地址
	 * @param params json字符串,
	 * @return 成功:返回json字符串<br/>
	 */
    public static String sendPostTest(String strURL, String params) {
		if(StringUtil.isNull(strURL)) return null;
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
            connection.setRequestProperty("Content-Type", "application/json;charset=utf-8");//设置发送数据格式
            // 获取URLConnection对象对应的输出流
            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(),"UTF-8");
            out.write(params);//发送请求参数
            out.flush(); // flush输出流的缓冲
            out.close();
            // 定义 BufferedReader输入流来读取URL的响应
            InputStream is = connection.getInputStream();
            return StringUtil.convertStreamToString(is);
        }catch (Exception e) {
        	logger.error("HttpRequest请求post地址:"+strURL+"----"+params);
			logger.error("HttpRequest请求post异常:"+e.getMessage());
			return e.getClass()+"     "+e.getMessage();
        }
	}
    
    
    public static void main(String[] args) {
		String s1 = "{ver: \"1.0\", "+//接口版本号
				"id: \""+UUIDFactory.getUUIDStr()+"\", "+
				"passTime: \""+CalendarUtil.getCurrentTime()+"\", "+
				"plateNum: \"车牌号\", "+
				"plateColor: \"1\", "+
				"vehicleType: \"2\", "+
				"vehicleImgUrl: \"车辆图片访问地址\", "+
				"plateImgUrl: \"车牌图片访问地址\", "+
				"areaCode: \"650100\", "+
				"x: \"经度\", "+//保留到小数点后6位
				"y: \"纬度\", "+//保留到小数点后6位
				"equipmentId: \"设备编号\", "+
				"equipmentName: \"设备名称\", "+
				"equipmentType: \"设备类型\", "+
				"stationId: \"警务站编号\", "+//中电科提供所有警务站的代码表，如果知道本小区归属与某个警务站管辖，传警务站编号，否则传空（中电科再根据经纬度计算有效半径内的警务站进行推送）
				"stationName: \"警务站名称\", "+//同stationId
				"backUrl: \"比中回传的接口URL\", "+
				"location: \"XXX小区\", "+
				"driverData: [{"+//司机信息
						"name: \"姓名\", "+
						"gender: \"性别\", "+
						"nation: \"民族\", "+
						"idcard: \"身份证号\", "+
						"birthday: \"出生年月如：1997年4月3日\", "+
						"address: \"地址\", "+
						"authority: \"签发机关\", "+
						"validDate: \"有效期\", "+
						"personImgUrl: \"采集司机照片访问路径\", "+
						"idcardImgUrl: \"身份证照片访问路径\""+
				"}], "+
				"passengerData: [{"+//乘客信息，若不采集传[]即可
						"name: \"姓名\", "+
						"gender: \"性别\", "+
						"nation: \"民族\", "+
						"idcard: \"身份证号\", "+
						"birthday: \"出生年月如：1997年4月3日\", "+
						"address: \"地址\", "+
						"authority: \"签发机关\", "+
						"validDate: \"有效期\", "+
						"personImgUrl: \"采集乘客照片访问路径\", "+
						"idcardImgUrl: \"身份证照片访问路径\""+
				"}]"+
    		"}";
		
		String s2 = "{	ver:\"1.0\", "+//接口版本号
				"id: \""+UUIDFactory.getUUIDStr()+"\", "+
				"passTime: \""+CalendarUtil.getCurrentTime()+"\", "+
				"name:\"姓名\", "+
				"gender:\"性别\", "+
				"nation:\"民族\", "+
				"idcard:\"身份证号\", "+
				"birthday:\"出生年月如：1997年4月3日\", "+
				"address:\"地址\", "+
				"authority:\"签发机关\", "+
				"validDate:\"有效期\", "+
				"personImgUrl:\"采集照片访问路径\", "+
				"idcardImgUrl:\"身份证照片访问路径\", "+
				"areaCode:\"650100\", "+
				"x:\"经度\", "+//保留到小数点后6位
				"y:\"纬度\", "+//保留到小数点后6位
				"equipmentId:\"设备编号\", "+
				"equipmentName:\"设备名称\", "+
				"equipmentType:\"设备类型\", "+
				"stationId:\"警务站编号\", "+//中电科提供所有警务站的代码表，如果知道本小区归属与某个警务站管辖，传警务站编号，否则传空（中电科再根据经纬度计算有效半径内的警务站进行推送）
				"stationName:\"警务站名称\", "+//同stationId
				"location: \"XXX小区\", "+
				"backUrl:\"比中回传的接口URL\" "+
    	"}";
		BaseDataCode.init();
		String result1 = sendPost("http://127.0.0.1:8888/sqpt/upload/vehicle", s1);
		String result2 = sendPost("http://127.0.0.1:8888/sqpt/upload/person", s2);
		System.out.println(result1);
		System.out.println(result2);

	}

}