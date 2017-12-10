package cn.lhkj.commons.scan.job.datadoor;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import org.apache.log4j.Logger;
import cn.lhkj.commons.util.StringUtil;

public class DataDoorPost {
	
	private static final Logger logger = Logger.getLogger(DataDoorPost.class);
	
	private static Integer timeOut = 8000;//请求超时时间10秒--测试时使用
	
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
			connection.setConnectTimeout(3000);//设置建立连接的超时时间
			connection.setReadTimeout(3000);//设置传递数据的超时时间
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
	
    /**
	 * 发送HttpPost请求
	 * @param strURL 服务地址
	 * @param params json字符串,
	 * @return 成功:返回json字符串<br/>
	 */
    public static String sendPost(String strURL, String params) {
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
        	logger.error("HttpRequest-ERROR:"+e.getMessage()+"----"+strURL);
			return null;
        }
	}
	
    

}