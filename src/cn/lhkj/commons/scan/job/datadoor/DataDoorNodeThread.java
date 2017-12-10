package cn.lhkj.commons.scan.job.datadoor;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import cn.lhkj.commons.base.BaseDao;
import cn.lhkj.commons.base.BaseDataCode;
import cn.lhkj.commons.scan.job.node.DataDoorCode;
import cn.lhkj.commons.scan.thread.ComparPersonThread;
import cn.lhkj.commons.tools.FileOperator;
import cn.lhkj.commons.util.CalendarUtil;
import cn.lhkj.commons.util.StringUtil;
import cn.lhkj.project.equipment.entity.Equipment;
import cn.lhkj.project.person.entity.Person;
import cn.lhkj.project.person.entity.PersonWarning;

/**
 * 数据门轮询查询经过数据门的数据
 */
public class DataDoorNodeThread implements Runnable {
	
	private static final Logger logger = Logger.getLogger(DataDoorNodeThread.class);
	
	private String url;
	private Equipment em;
	
	public DataDoorNodeThread(Equipment em){
		this.url = em.getUrl();
		this.em = em;
	}
	
	@Override
	public void run(){
		long currentTime = System.currentTimeMillis();
		try {
			DataDoorCode.addEquipmentSet(em.getId());
			for(int i=0;i<30;i++){
				Map<String,Object> map = new HashMap<String, Object>();
				map.put("api_key", "");
				map.put("api_secret", "");
				map.put("page", 1);
				map.put("page_number", 500);
				map.put("query_type", "3");//1为侦码告警  2为wifi告警 3为人员信息告警
				map.put("begin_time", "");
				map.put("end_time", "");
				
				Long serverTime = DataDoorPost.getServiceTime(url+"person", StringUtil.obj2json(map));
				if(serverTime == null){
					try {
						Thread.sleep(1500);
						if(currentTime < DataDoorCode.resetTime) return;
						continue;
					}catch (Exception e) {}
				}
				String end_time = CalendarUtil.formatToHMS(new Date(serverTime+10000));
				String begin_time = CalendarUtil.formatToHMS(new Date(serverTime-15000));
				map.put("begin_time", begin_time);
				map.put("end_time", end_time);
				String params = StringUtil.obj2json(map);
				//获取通行人员数据
				String personData = DataDoorPost.sendPost(url+"person", params);
				String errorCode = savePerson(url+"person", params , personData , em);
				if("-3".equals(errorCode)){//超过最大线程
					DataDoorCode.resetTime = System.currentTimeMillis();
					DataDoorCode.removeEquipmentSet(em.getId());
					return;
				}
				///////////////////////////////////////////////////////
				//获取告警人员数据
				String warningData = DataDoorPost.sendPost(url+"warning", params);
				errorCode = saveWarning(url+"warning", params, warningData, em);
				if("-3".equals(errorCode)){//超过最大线程
					DataDoorCode.resetTime = System.currentTimeMillis();
					DataDoorCode.removeEquipmentSet(em.getId());
					return;
				}
				///////////////////////////////////////////////////////
				
				try {
					Thread.sleep(1000);
					if(currentTime < DataDoorCode.resetTime) return;
				}catch (Exception e) {}
			}
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
		}finally{
			if(currentTime > DataDoorCode.resetTime){
				DataDoorCode.removeEquipmentSet(em.getId());
			}
		}
	}
	
	/**
	 * 保存请求人员结果
	 * @param postURL 请求路径
	 * @param params 请求参数
	 * @param relust 请求结果
	 * @param em
	 * @throws Exception
	 */
	private String savePerson(String postURL ,String params , String relust,Equipment em) throws Exception{
		BaseDao dao = null;
		if(StringUtil.isNull(relust)) return ""; 
		try {
			JSONObject personObject = JSONObject.fromObject(relust);
			String error_code = personObject.getString("error_code");//0成功
			if("-1".equals(error_code)){
				logger.error("【-1】【数据门通行人员查询错误】【"+postURL+"】");
			}else if("-2".equals(error_code)){
				logger.error("【-2】【数据门通行人员查询错误,时间输入不合法】【"+postURL+"】【"+params+"】");
			}else if("-3".equals(error_code)){
				logger.info("【-3】【数据门通行人员查询错误,超过最大并发数】【"+postURL+"】");
			}else if("-4".equals(error_code)) {
				logger.error("【-4】【数据门通行人员查询错误,指定的查询条件数据结果为空】【"+postURL+"】【"+params+"】");
			}else if("-5".equals(error_code)){
				logger.error("【-5】【数据门通行人员查询错误,参数传递错误】【"+postURL+"】【"+params+"】");
			}else if("-6".equals(error_code)){
				logger.error("【-6】【数据门通行人员查询错误,分页输入不合法，每页条目数应大于1并且小于3000】【"+postURL+"】【"+params+"】");
			}
			if(!"0".equals(error_code)) return error_code;
			
			JSONArray datas  = personObject.getJSONArray("datas");//通过人员数据
			if(datas == null || datas.size() == 0) return "";
			dao = new BaseDao();
			try {
				long curentPersonCaptureTime = 0;
				if(DataDoorCode.getPersonMap().get(em.getId()) != null){
					curentPersonCaptureTime = DataDoorCode.getPersonMap().get(em.getId()).getCaptureTime().getTime();
				}
				for(int i=0;i<datas.size();i++){
					JSONObject personObj = datas.getJSONObject(i);
					String name = personObj.getString("name");
					String sex = personObj.getString("sex");
					String identity = personObj.getString("identity");
					String address = personObj.getString("address");
					String capture_time = personObj.getString("capture_time");//采集时间
					String start_date = personObj.getString("start_date");
					String end_date = personObj.getString("end_date");
					String is_a_person = personObj.getString("is_a_person");
					String serial_number = personObj.getString("serial_number");
					String capture_image = personObj.getString("capture_image");
					
					Person person = new Person(serial_number);
					person.setLocation(em.getNames());
					person.setAddress(address);
					person.setCaptureTime(CalendarUtil.toDate(capture_time,"yyyy-MM-dd HH:mm:ss"));
					person.setEndDate(end_date);
					person.setEquipmentId(em.getId());
					person.setGender(sex);
					person.setIdcard(identity);
					if(!StringUtil.isNull(capture_image)){
						String url = em.getUrl();
						int index = url.indexOf(":9000");
						if(index != -1){
							url = url.substring(0,index+5);
							String httpImgUrl = url + capture_image;
							//将图片存入文件服务器
							Date date = new Date();
							String fileName = CalendarUtil.format(date, "yyyyMMddHHmmss")+person.getIdcard()+".jpg";
							String root =  "\\\\"+BaseDataCode.config.getFileServerIp()+"\\cetc\\datadoorPic\\";
							String path_url = root + CalendarUtil.format(date, "yyyyMM")+"\\"+CalendarUtil.format(date, "yyyyMMdd")+"\\";   
							saveToFile(httpImgUrl,path_url,path_url+fileName); 
					        String img_url = "http://"+BaseDataCode.config.getFileServerIp()+":8889/datadoorPic/"+
					        		CalendarUtil.format(date, "yyyyMM")+"/"+
					        		CalendarUtil.format(date, "yyyyMMdd")+"/"+fileName;
							person.setImgUrl(img_url);
							person.setLocalImgUrl(img_url);
						}
					}
					person.setIsAPerson("true".equals(is_a_person) ? "1" : "0");
					person.setNames(name);
					person.setStartDate(start_date);
					person.setInsertTime(new Date());
						
					if(person.getCaptureTime().getTime() > curentPersonCaptureTime){
						DataDoorCode.putPersonMap(em.getId(), person);
						BaseDataCode.putPersonMap(em.getId().substring(0, 13), person);
						dao.saveOrUpdate(person);
					}
				}
			}catch (JSONException e) {
				logger.error("【数据门通行人员查询结果解析错误】【"+e.getMessage()+"】【"+relust+"】");
			}
			return "OK";
		}catch (JSONException e) {
			logger.error("【数据门通行人员查询结果解析错误】【"+e.getMessage()+"】【"+relust+"】");
			return "ERROR";
		}finally{
			if(dao != null){ dao.close();}
		}
	}
	
	/**
	 * 根据网络地址保存图片
	 * @param destUrl 网络地址
	 * @param filePath 图片存储路径
	 */
	public void saveToFile(String destUrl,String imgFilePath,String filePath) {  
		FileOperator.newFolder(imgFilePath);
		FileOutputStream fos = null;  
		BufferedInputStream bis = null;  
		HttpURLConnection httpUrl = null;  
		URL url = null;  
		int BUFFER_SIZE = 1024;  
		byte[] buf = new byte[BUFFER_SIZE];  
		int size = 0;  
		try {  
			url = new URL(destUrl);  
			httpUrl = (HttpURLConnection) url.openConnection();  
			httpUrl.connect();  
			bis = new BufferedInputStream(httpUrl.getInputStream());  
			fos = new FileOutputStream(filePath);  
			while ((size = bis.read(buf)) != -1) {   
				fos.write(buf, 0, size);  
			}  
			fos.flush();  
		} catch (IOException e) {  
		} catch (ClassCastException e) {  
		} finally {  
			try {  
				fos.close();  
				bis.close();  
				httpUrl.disconnect();  
			} catch (IOException e) {  
			} catch (NullPointerException e) {  
			}  
		}  
	}
	
	
	
	
	/**
	 * 保存数据门比中人员数据
	 * @param postURL 比对的url
	 * @param params 比对参数
	 * @param relust 比对结果
	 * @throws Exception
	 */
	private String saveWarning(String postURL,String params,String relust,Equipment em) throws Exception{
		BaseDao dao = null;
		if(StringUtil.isNull(relust)) return ""; 
		try {
			JSONObject personObject = JSONObject.fromObject(relust);
			String error_code = personObject.getString("error_code");//0成功
			if("-1".equals(error_code)){
				logger.error("【-1】【数据门预警人员查询错误】【"+postURL+"】");
			}else if("-2".equals(error_code)){
				logger.error("【-2】【数据门预警人员查询错误,时间输入不合法】【"+postURL+"】【"+params+"】");
			}else if("-3".equals(error_code)){
				logger.info("【-3】【数据门预警人员查询错误,超过最大并发数】【"+postURL+"】");
			}else if("-4".equals(error_code)) {
				logger.error("【-4】【数据门预警人员查询错误,指定的查询条件数据结果为空】【"+postURL+"】【"+params+"】");
			}else if("-5".equals(error_code)){
				logger.error("【-5】【数据门预警人员查询错误,参数传递错误】【"+postURL+"】【"+params+"】");
			}else if("-6".equals(error_code)){
				logger.error("【-6】【数据门预警人员查询错误,分页输入不合法，每页条目数应大于1并且小于3000】【"+postURL+"】【"+params+"】");
			}
			if(!"0".equals(error_code)) return error_code;
			
			JSONArray datas  = personObject.getJSONArray("datas");//比中人员数据
			if(datas == null || datas.size() == 0) return "";
			dao = new BaseDao();
			try {
				long curentCaptureTime = 0;
				if(DataDoorCode.getPersonWarningMap().get(em.getId()) != null){
					try{
						curentCaptureTime = DataDoorCode.getPersonWarningMap().get(em.getId()).getCaptureTime().getTime();
					}catch(Exception e){}
				}
				
				for(int i=0;i<datas.size();i++){
					JSONObject personObj = datas.getJSONObject(i);
					String tag = personObj.getString("tag");//[一体化录入人员]的同家族人员|一体化录入人员|不准出境人员|危安管控对象
					String message = personObj.getString("message");//在逃日期:2009-09-15 00:00:00,
					String reserve1 = "";
					String reserve2 = "";
					String reserve3 = "";
					String reserve4 = "";
					if(personObj.containsKey("reserve1")){
						reserve1 = personObj.getString("reserve1");//一体化布控
					}else if(personObj.containsKey("reserved1")){
						reserve1 = personObj.getString("reserved1");//一体化布控
					}
					if(personObj.containsKey("reserve2")){
						reserve2 = personObj.getString("reserve2");//发现即抓捕，同时通知原籍公安机关
					}else if(personObj.containsKey("reserved2")){
						reserve2 = personObj.getString("reserved2");//发现即抓捕，同时通知原籍公安机关
					}
					if(personObj.containsKey("reserve3")){
						reserve3 = personObj.getString("reserve3");//22028219931203411X
					}else if(personObj.containsKey("reserved3")){
						reserve3 = personObj.getString("reserved3");//22028219931203411X
					}
					if(personObj.containsKey("reserve4")){
						reserve4 = personObj.getString("reserve4");
					}else if(personObj.containsKey("reserved4")){
						reserve4 = personObj.getString("reserved4");
					}
					
					String name = personObj.getString("name");//李思远
					String sex = personObj.getString("sex");//男
					String identity = personObj.getString("identity");//430223198601117288
					String serial_number = personObj.getString("serial_number");//430223198601117288_20170323
					String capture_time = personObj.getString("capture_time");//采集时间
					
					PersonWarning pw = new PersonWarning(serial_number);
					pw.setGender(sex);
					pw.setIdcard(identity);
					pw.setMessage(message);
					pw.setNames(name);
					pw.setReserved1(reserve1);
					pw.setReserved2(reserve2);
					pw.setReserved3(reserve3);
					pw.setReserved4(reserve4);
					pw.setTag(tag);
					pw.setInsertTime(new Date());
					pw.setCaptureTime(CalendarUtil.toDate(capture_time,"yyyy-MM-dd HH:mm:ss"));
					
					if(pw.getCaptureTime().getTime() > curentCaptureTime){
						DataDoorCode.putPersonWarningMap(em.getId(), pw);
						dao.saveOrUpdate(pw);
						new Thread(new ComparPersonThread(pw,em)).start();
					}
				}
			}catch (JSONException e) {
				logger.error("【数据门预警人员查询结果解析错误】【"+e.getMessage()+"】【"+relust+"】");
				return "ERROR";
			}
			return "OK";
		}catch (Exception e){
			logger.error("【数据门预警人员查询结果解析错误】【"+e.getMessage()+"】【"+relust+"】");
			return "ERROR";
		}finally{
			if(dao != null){dao.close();}
		}
	}
}
