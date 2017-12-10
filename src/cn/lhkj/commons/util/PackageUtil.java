package cn.lhkj.commons.util;

import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.lhkj.commons.defined.Entity;

public class PackageUtil {
	
	private static Map<String,String> entityClassMap;
	
	/**
	 * 返回所有的Entity类<简称,详细>
	 * @param packageName
	 * @return
	 */
	public static Map<String,String> getEntityMap(){
		String packageName = "cn.lhkj.project";
		if(entityClassMap == null){
			entityClassMap = getEntityClassName(packageName,new HashMap<String, String>());
		}
		return entityClassMap;
	}
	
	private static Map<String,String> getEntityClassName(String packageName,Map<String,String> relustMap){
		//"cn.fh.lightning" -> "cn/fh/lightning" *
		String splashPath = packageName.replaceAll("\\.", "/");
		URL url = PackageUtil.class.getClassLoader().getResource(splashPath);   
		String filePath = getRootPath(url);
		List<String> names = readFromDirectory(filePath);
		for (String name : names) {            
			if (isClassFile(name)) {
				String classNameShort = name.substring(0,name.indexOf(".class"));
				String className = packageName+"."+classNameShort;
				try {
					if(Class.forName(className).getAnnotation(Entity.class) != null){
						relustMap.put(classNameShort ,className);
					}
				} catch (ClassNotFoundException e) {
					continue;
				}
			}else{                
				getEntityClassName(packageName+"."+name, relustMap);            
			}        
		} 
		return relustMap;
	}
	
	private static boolean isClassFile(String name) {        
		return name.endsWith(".class");    
	} 
	
	
	private static List<String> readFromDirectory(String path) {        
		File file = new File(path);        
		String[] names = file.list();         
		if (null == names) return null;            
		return Arrays.asList(names);    
	}
	
	/**
	 * * "file:/home/whf/cn/fh" -> "/home/whf/cn/fh" *
	 * "jar:file:/home/whf/foo.jar!cn/fh" -> "/home/whf/foo.jar"
	 */
	private static String getRootPath(URL url) {
		String fileUrl = url.getFile();
		int pos = fileUrl.indexOf('!');
		if (-1 == pos) {
			return fileUrl;
		}
		return fileUrl.substring(5, pos);
	}
	
	public static void main(String[] args) {
		Map<String,String> map = PackageUtil.getEntityMap();
		Set<String> keySet = map.keySet();
		for(String key : keySet){
			System.out.println(key+"="+map.get(key));
		}
	}
	
}
