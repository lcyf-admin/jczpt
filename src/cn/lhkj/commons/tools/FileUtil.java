package cn.lhkj.commons.tools;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Date;

import cn.lhkj.commons.util.CalendarUtil;

public class FileUtil {
	
	private FileOutputStream fos;
	private OutputStreamWriter osw;
	private BufferedWriter bw;
	private FileInputStream fis;
	private InputStreamReader isr;
	private BufferedReader br;
	
	public FileUtil(){
		fos = null;
		osw = null;
		bw = null;
		fis = null;
		isr = null;
		br = null;
	}
	
	/**
	 * @param paths 文件夹路径
	 * @param filename 文件名称
	 * @return
	 */
	public void createFile(String paths,String filename) throws IOException{
		if(paths == null || "".equals(paths) || "null".equals(paths)) return;
		paths = paths+File.separator+filename+CalendarUtil.format(new Date(), "yyyyMMddHHmmss")+".DI";
		File file = new File(paths);
		//文件夹不存在
		if(!file.getParentFile().exists()){
			if (!file.getParentFile().mkdirs()){
				return;
			}
		}
		//写入中文字符时解决中文乱码问题
        fos = new FileOutputStream(file);
        osw=new OutputStreamWriter(fos, "UTF-8");
        bw=new BufferedWriter(osw);     
	}
	
	/**
	 * @param contents 写入文件数据
	 * @return
	 */
	public void writeFile(String contents) throws IOException{
		if(bw == null) return;
		bw.write(contents);
		bw.newLine();//换行
	}
	
	public void closeStream() throws IOException{	
		//注意关闭的先后顺序，先打开的后关闭，后打开的先关闭
		if(bw != null) bw.close();
		if(osw != null) osw.close();
		if(fos != null) fos.close();
		if(br != null) br.close();
		if(isr != null) isr.close();
		if(fis != null) fis.close();
	}
	
	/**
	 * @param directory 文件夹路径
	 * @return
	 */
	public String[] readFilelist(String directory){
		File file = new File(directory);
		if(file.isDirectory()){
			return file.list();
		}
		return null;
	}
	
	/**
	 * @param paths 文件路径
	 * @return
	 */
	public BufferedReader readFile(String paths) throws IOException {
		if(paths == null || "".equals(paths) || "null".equals(paths)) return null;
		File file = new File(paths);
		if(!file.exists()) return null;
        fis=new FileInputStream(file);
        isr=new InputStreamReader(fis, "UTF-8");
        br = new BufferedReader(isr);
        return br;
    }
	
	/**
	 * @param paths 删除磁盘文件
	 * @return
	 */
	public void delFile(String paths){
		File file = new File(paths);
		if(file.exists()){
			file.delete();
		}
	}
}
