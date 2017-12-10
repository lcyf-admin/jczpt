package cn.lhkj.project.system.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.core.io.ClassPathResource;

import cn.lhkj.project.system.entity.ConfigInfo;

public class ConfigService {

	private static final Logger logger = Logger.getLogger(ConfigService.class);
	
	/**获取系统运行配置信息*/
	public static ConfigInfo getConfigInfo() {
		try {
			ConfigInfo configInfo = new ConfigInfo();
			Properties createPros;
			createPros = createPros("config.properties");
			Field[] fields = ConfigInfo.class.getDeclaredFields();
			for (Field f : fields) {
				Method method = gainSetMethod(ConfigInfo.class, f);
				String value = createPros.getProperty(f.getName());
				if (f.getType() == String.class) {
					method.invoke(configInfo, value);
				} else if (f.getType() == Integer.class) {
					method.invoke(configInfo, Integer.parseInt(value));
				}
			}
			return configInfo;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}
	
	/**更新系统配置信息*/
	public static void updateConfigInfo(ConfigInfo configInfo) throws Exception{
		if(configInfo == null) return;
		String path = "config.properties";
		ClassPathResource source = new ClassPathResource(path);
		File file = source.getFile();
		Properties property = new Properties();
		property.load(new FileInputStream(file));
		Writer writer = new FileWriter(file);
		Field[] fields = ConfigInfo.class.getDeclaredFields();
		for(Field f : fields){
			Method method = gainGetMethod(ConfigInfo.class, f);
			Object obj = method.invoke(configInfo);
			property.setProperty(f.getName(),String.valueOf(obj));
		}
		property.store(writer, "update");
	}
	
	/**
	 * 方法说明：读取配置文件 配置文件改变不需要重启
	 * @param path	配置文件路径
	 * @return
	 * @throws IOException 
	 */
	public static Properties createPros(String path) throws IOException{
		ClassPathResource source = new ClassPathResource(path);
		Properties property = new Properties();
		property.load(new FileInputStream(source.getFile()));
		return property;
	}
	
	/** 反射得到对象的set方法*/
	private static Method gainSetMethod(Class<?> clazz,Field field) throws Exception{
		String fieldName = field.getName();
		String fistChar = fieldName.substring(0,1).toUpperCase();
		String methodName = "set"+fistChar+fieldName.substring(1);
		return clazz.getMethod(methodName, field.getType());
	}
	
	/** 反射得到对象的get方法*/
	private static Method gainGetMethod(Class<?> clazz,Field field) throws Exception{
		String fieldName = field.getName();
		String fistChar = fieldName.substring(0,1).toUpperCase();
		String methodName = "get"+fistChar+fieldName.substring(1);
		return clazz.getMethod(methodName);
	}
	
}
