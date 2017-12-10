package cn.lhkj.commons.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import sun.misc.BASE64Decoder;

public class Base64Util {
	private static Log logger = LogFactory.getLog(Base64Util.class);//日志记录工具
	/**********************************************
	 * 将图片根据Base64编码为String 
	 * @param 图片的路径名 如C:/1111.bmp
	 * @return
	 ***********************************************/
	public static String GeneratorCode(String s) throws Exception{
		String baseCode;

		File file = new File(s);

		FileInputStream fileForInput = new FileInputStream(file);
		try {
			
			byte[] bytes = new byte[fileForInput.available()];
			fileForInput.read(bytes);
			baseCode = new sun.misc.BASE64Encoder().encode(bytes); // 具体的编码方法
			

			return baseCode;
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage(),e);
		} catch (IOException e) {
			logger.error(e.getMessage(),e);
		}finally{
			fileForInput.close();
		}
		return null;
	}
	
	/**
	 * 采用BASE64加密信息.
	 * Method: BASE64encode
	 * @param msg
	 * @return
	 * desc:
	 */
	public static String BASE64encode(String msg){
		return new sun.misc.BASE64Encoder().encode(msg.getBytes());
	}
	
	/**
	 * 采用BASE64加密信息.
	 * Method: BASE64encode
	 * @param msg
	 * @return
	 * desc:
	 */
	public static String BASE64encode(byte[] b){
		try {
			return new sun.misc.BASE64Encoder().encode(b);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return "";
		}
	}
	
	/**
	 * 采用BASE64解密信息.
	 * Method: BASE64encode
	 * @param code
	 * @return
	 * desc:
	 */
	public static String BASE64decode(String code){
		try {
			return new String(new sun.misc.BASE64Decoder().decodeBuffer(code));
		} catch (IOException e) {
			logger.error(e.getMessage(),e);
			return null;
		}
	}
	
	/**********************************************
	 * 将图片解码，输出照片
	 * @param s 图片编码之后的String,
	 * @param path 照片的存储路径
	 * @return
	 ***********************************************/
	public static boolean GenerateImage(String s, String path) throws Exception {
		if (null == s) return false;
		BASE64Decoder decoder = new BASE64Decoder();
		File file = new File(path);
		if ("".equals(s)) {
			while (file.exists() && file.delete()) {
				file = file.getParentFile();
			}
		} else {
			byte[] b = decoder.decodeBuffer(s);
			OutputStream out = null;
			try {
				out = new FileOutputStream(file);
				out.write(b);
			} catch (Exception ex) {
				logger.error(ex.getMessage());
			} finally {
				if (out != null) out.close();
			}
		}
		return true;
	}
}