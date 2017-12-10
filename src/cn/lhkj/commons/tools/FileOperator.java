package cn.lhkj.commons.tools;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import cn.lhkj.commons.util.StringUtil;

/**文件操作工具*/
public class FileOperator {
	static public final Logger log = Logger.getLogger(FileOperator.class);
	
	/**
	 * 新建目录
	 * @param folderPath String 如 c:/fqf
	 * @return boolean
	 */
	public static void newFolder(String folderPath) {
		try {
			String filePath = folderPath;
			filePath = filePath.toString();
			File myFilePath = new File(filePath);
			if (!myFilePath.exists()) {
				myFilePath.mkdirs();
			}
		} catch (Exception e) {
			log.error("新建目录操作出错!path="+folderPath);
			log.error(e.getMessage(),e);
		}
	}
	
	/**创建文件 */
	public static void creatFolder(String filePath) {
		int index = filePath.lastIndexOf("\\");
		File fonder = new File(filePath.substring(0, index));
		if(!fonder.exists()){
			fonder.mkdirs();
		}
	}
	
	/**
	 * 删除文件夹下所有空文件夹
	 * @param folderPath
	 */
	public static void deltetEmptyFolder(String folderPath,Boolean isLastFolder){
		File folder = new File(folderPath);
		if(!folder.exists()) return;
		if(folder.isFile()) return;
		File[] files =  folder.listFiles();
		if(files.length ==0){
			folder.delete();
			if(isLastFolder){
				deltetEmptyFolder(folder.getParentFile(),false);
			}
		}else{
			for(int i=0;i<files.length;i++){
				if((i+1) == files.length){
					deltetEmptyFolder(files[i],true);
				}else{
					deltetEmptyFolder(files[i],false);
				}	
			}
		}
	}
	
	/**
	 * 删除文件夹下所有空文件夹
	 * @param folderPath
	 */
	public static void deltetEmptyFolder(File folder,Boolean isLastFolder){
		if(!folder.exists()) return;
		if(folder.isFile()) return;
		File[] files =  folder.listFiles();
		if(files.length ==0){
			folder.delete();
			if(isLastFolder){
				deltetEmptyFolder(folder.getParentFile(),false);
			}
		}else{
			for(int i=0;i<files.length;i++){
				if((i+1) == files.length){
					deltetEmptyFolder(files[i],true);
				}else{
					deltetEmptyFolder(files[i],false);
				}
			}
		}
	}

	/**
	 * 新建文件
	 * @param filePathAndName 文件路径及名称 如c:/fqf.txt
	 * @param fileContent 文件内容
	 */
	public static void newFile(String filePathAndName, String fileContent) {

		try {
			String filePath = filePathAndName;
			filePath = filePath.toString();
			File myFilePath = new File(filePath);
			if (!myFilePath.exists()) {
				myFilePath.createNewFile();
			}
			FileWriter resultFile = new FileWriter(myFilePath);
			PrintWriter myFile = new PrintWriter(resultFile);
			String strContent = fileContent;
			myFile.println(strContent);
			resultFile.close();
		} catch (Exception e) {
			log.error("新建目录操作出错!path="+filePathAndName);
			log.error(e.getMessage(),e);
		}
	}

	/**
	 * 删除文件
	 * @param filePathAndName 文件路径及名称 如c:/fqf.txt
	 */
	public static void delFile(String filePathAndName) {
		try {
			String filePath = filePathAndName;
			filePath = filePath.toString();
			File myDelFile = new File(filePath);
			myDelFile.delete();
		}catch(Exception e) {
			log.error("删除文件操作出错!path="+filePathAndName);
			log.error(e.getMessage(),e);
		}

	}

	/**
	 * 删除文件夹
	 * @param folderPath 文件夹路径及名称 如c:/fqf
	 * @return boolean
	 */
	public static void delFolder(String folderPath) {
		try {
			delAllFile(folderPath); // 删除完里面所有内容
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			myFilePath.delete(); // 删除空文件夹
		} catch (Exception e) {
			log.error("删除文件夹操作出错!path="+folderPath);
			log.error(e.getMessage(),e);
		}

	}

	/**
	 * 删除文件夹里面的所有文件
	 * @param path 文件夹路径 如 c:/fqf
	 */
	public static void delAllFile(String path) {
		File file = new File(path);
		if (!file.exists()) return;
		if (!file.isDirectory()) return;
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
				delFolder(path + "/" + tempList[i]);// 再删除空文件夹
			}
		}
	}

	/**
	 * 复制单个文件
	 * @param oldPath 原文件路径 如：c:/fqf.txt
	 * @param newPath 复制后路径 如：f:/fqf.txt
	 */
	public static void copyFile(String oldPath, String newPath) {
		try{
			int byteread = 0;
			File oldfile = new File(oldPath);
			if (oldfile.exists()) { // 文件存在时
				FileInputStream inStream = new FileInputStream(oldPath); // 读入原文件
				File newFolder = new File(newPath.substring(0,newPath.lastIndexOf(File.separator)));
				if (!newFolder.exists()) {
					newFolder.mkdirs();
				}
				File newFile = new File(newPath);
				if(!newFile.exists()){
					newFile.createNewFile();
				}
				FileOutputStream fs = new FileOutputStream(newPath);
				byte[] buffer = new byte[1444];
				while ((byteread = inStream.read(buffer)) != -1) {
					fs.write(buffer, 0, byteread);
				}
				fs.close();
				inStream.close();
			}
		}catch(Exception e) {
			log.error("复制单个文件操作出错!oldPath="+oldPath);
			log.error("复制单个文件操作出错!newPath="+newPath);
			log.error(e.getMessage(),e);
		}
	}

	/**
	 * 复制整个文件夹内容
	 * @param oldPath 原文件路径 如：c:/fqf
	 * @param newPath 如：f:/fqf/ff
	 */
	public static void copyFolder(String oldPath, String newPath) {
		try {
			(new File(newPath)).mkdirs(); // 如果文件夹不存在 则建立新文件夹
			File a = new File(oldPath);
			String[] file = a.list();
			File temp = null;
			for (int i = 0; i < file.length; i++) {
				if (oldPath.endsWith(File.separator)) {
					temp = new File(oldPath + file[i]);
				} else {
					temp = new File(oldPath + File.separator + file[i]);
				}

				if (temp.isFile()) {
					FileInputStream input = new FileInputStream(temp);
					FileOutputStream output = new FileOutputStream(newPath
							+ "/" + (temp.getName()).toString());
					byte[] b = new byte[1024 * 5];
					int len;
					while ((len = input.read(b)) != -1) {
						output.write(b, 0, len);
					}
					output.flush();
					output.close();
					input.close();
				}
				if (temp.isDirectory()) {// 如果是子文件夹
					copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
				}
			}
		} catch (Exception e) {
			log.error("复制整个文件夹内容操作出错!oldPath="+oldPath);
			log.error("复制整个文件夹内容操作出错!newPath="+newPath);
			log.error(e.getMessage(),e);
		}

	}

	/**
	 * 移动文件到指定目录
	 * @param oldPath 如：c:/fqf.txt
	 * @param newPath 如：d:/fqf.txt
	 */
	public static void moveFile(String oldPath, String newPath) {
		if(StringUtil.trim(oldPath).length() != 0 && StringUtil.trim(newPath).length() != 0){
			File oldFile = new File(oldPath);
			if(oldFile.exists() && oldFile.isFile()){
				creatFolder(newPath);
				File newFile = new File(newPath);
				oldFile.renameTo(newFile);
			}
		}
	}
	
	/**
	 * 移动文件到指定目录
	 * 
	 * @param oldFile 如：c:/fqf.txt
	 * @param newFile 如：d:/fqf.txt
	 */
	public static void moveFile(File oldFile, File newFile) {
		if(oldFile.exists() && oldFile.isFile()){
			creatFolder(newFile.getPath());
			oldFile.renameTo(newFile);
		}
	}

	/**
	 * 移动目录下文件到新目录
	 * 
	 * @param oldPath String 如：c:/a/
	 * @param newPath String 如：d:/b/
	 */
	public static void moveFolder(String oldPath, String newPath) {
		if(StringUtil.trim(oldPath).length() != 0 && StringUtil.trim(newPath).length() != 0){
			if(!oldPath.endsWith("\\"))	oldPath = oldPath + File.separator;
			if(!newPath.endsWith("\\"))	newPath = newPath + File.separator; 
			File oldFile = new File(oldPath);
			if(oldFile.exists() && oldFile.isDirectory()){
				File[] fileList = oldFile.listFiles();
				if(fileList != null){
					creatFolder(newPath);
					for (int i = 0; i < fileList.length; i++) {
						if(fileList[i].isFile()){
							File tpfile = new File(newPath+fileList[i].getName());
							fileList[i].renameTo(tpfile);
						}else{
							String oldPath_ = fileList[i].getPath();
							String newPath_ = newPath + oldPath_.substring(oldPath_.lastIndexOf("\\")+1);
							moveFolder(oldPath_,newPath_);
						}
		
					}
					oldFile.delete();
				}	
			}
		}
	}
	
	/**
	 * 下载文件
	 * @param downPath 文件路径
	 * @param fileName 文件名称
	 * @throws Exception 
	 */
	public static void outputFile(String downPath,String fileName, HttpServletResponse response) throws Exception{
		try {
			File fileDes = new File(downPath);
		    response.reset();
		    response.setCharacterEncoding("utf-8");
		    response.addHeader("content-Disposition", "attachment;filename="+new String(fileName.getBytes(),"ISO-8859-1"));
		    response.addHeader("content-Length", ""+fileDes.length());
		    response.setContentType("application/octet-stream");
	    	InputStream fis = new BufferedInputStream(new FileInputStream(downPath));
		    OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
		    byte[] buffer = new byte[1024];
		    int i = 0;
		    while ((i=fis.read(buffer)) >= 0) {
		    	toClient.write(buffer,0,i);
		    }
		    fis.close();
		    toClient.flush();
		    toClient.close();
		}catch (Exception e) {
			//用户取消文件的下载不需要抛异常
			if(e.getClass().getName().indexOf("ClientAbortException") == -1){
				if(e.getClass().getName().indexOf("FileNotFoundException") == -1)
					throw e;
			}
		}
	}
	
	/**
	 * 将文件内容读取成字符串
	 * @param file
	 * @return
	 */
	public static String readFile(File file){
		if(file == null || !file.exists()) return null;
		try {
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file),"gbk"));
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return null;
	}
	
	public static void main(String arge[]){
		deltetEmptyFolder("D:\\abalah-tcp-rootpath\\2014-09\\",false);
	}
}
