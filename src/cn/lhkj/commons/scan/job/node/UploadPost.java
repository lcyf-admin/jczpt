package cn.lhkj.commons.scan.job.node;

import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import org.apache.log4j.Logger;
import cn.lhkj.commons.util.StringUtil;


/***
 * 上传数据post
 */
public class UploadPost {
	
	private static final Logger logger = Logger.getLogger(UploadPost.class);
	
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
            connection.setConnectTimeout(10000);//设置建立连接的超时时间
			connection.setReadTimeout(30000);//设置传递数据的超时时间
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
        	if(!StringUtil.trim(e.getMessage()).contains("Read timed out")){
        		logger.error("【UploadPost】【"+e.getMessage()+"】【"+strURL+"】");
        	}
			return null;
        }
	}

}