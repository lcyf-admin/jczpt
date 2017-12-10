package cn.lhkj.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import javax.annotation.Resource;
import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.lhkj.commons.base.BaseController;
import cn.lhkj.commons.base.BaseDao;
import cn.lhkj.commons.base.BaseDataCode;
import cn.lhkj.commons.tools.HttpPost;
import cn.lhkj.commons.tools.HttpPostSANHUI;
import cn.lhkj.commons.tools.IdCardGenerator;
import cn.lhkj.commons.tools.UUIDFactory;
import cn.lhkj.commons.util.CalendarUtil;
import cn.lhkj.commons.util.StringUtil;
import cn.lhkj.project.contrast.entity.ContrastPerson;
import cn.lhkj.project.equipment.entity.Equipment;
import cn.lhkj.project.person.entity.Person;
import cn.lhkj.project.system.entity.Orgzon;
import cn.lhkj.project.system.service.OrgzonService;

@Controller
@Scope("prototype")
public class TestController extends BaseController {
	static public final Logger logger = Logger.getLogger(TestController.class);
	private String path = "/pages/test/test_";
	
	private @Resource OrgzonService orgzonService;

	@ResponseBody
	@RequestMapping(value="/test_ajaxDataDoorPerson")
	public String ajaxDataDoorPerson(){
		try{
			String api_key = super.getParameter("api_key");
			String api_secret = super.getParameter("api_secret");
			String begin_time = super.getParameter("begin_time");
			String end_time = super.getParameter("end_time");
			String page = super.getParameter("page");
			String page_number = super.getParameter("page_number");
			String url = super.getParameter("url");
			
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("query_type", "3");
			map.put("api_key", api_key);
			map.put("api_secret", api_secret);
			map.put("begin_time", begin_time);
			map.put("end_time", end_time);
			map.put("page", page);
			map.put("page_number", page_number);
			String par = StringUtil.obj2json(map);
			String text = HttpPost.sendPostTest(url, par);
			showPerson(text);
			super.printText(text);
		}catch (Exception e) {
			super.printText(e.getMessage());
		}
		return null;
	}
	
	@ResponseBody
	@RequestMapping(value="/test_ajaxDataDoorWarning")
	public String ajaxDataDoorWarning(){
		try{
			String api_key = super.getParameter("api_key");
			String api_secret = super.getParameter("api_secret");
			String begin_time = super.getParameter("begin_time");
			String end_time = super.getParameter("end_time");
			String page = super.getParameter("page");
			String page_number = super.getParameter("page_number");
			String url = super.getParameter("url");
			
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("query_type", "3");
			map.put("api_key", api_key);
			map.put("api_secret", api_secret);
			map.put("begin_time", begin_time);
			map.put("end_time", end_time);
			map.put("page", page);
			map.put("page_number", page_number);
			String par = StringUtil.obj2json(map);
			String text = HttpPost.sendPostTest(url, par);
			showWarning(text);
			super.printText(text);
		}catch (Exception e) {
			super.printText(e.getMessage());
		}
		return null;
	}
	
	/**和三汇大数据平台比对测试*/
	@ResponseBody
	@RequestMapping(value="/test_ajaxCompar")
	public String ajaxCompar(){
		String result = "";
		try{
			String type = super.getParameter("type");
			String url = super.getParameter("url");
			String idcard = super.getParameter("idcard");
			String plate = super.getParameter("plate");
			String plate_color = super.getParameter("plate_color");
			String mac = super.getParameter("mac");
			String imei = super.getParameter("imei");
			String imsi = super.getParameter("imsi");
			
			Map<String,String> map = new HashMap<String,String>();
			if("person".equals(type)){
				if(StringUtil.isNotNull(idcard))
					map.put("id", idcard);
			}else if("plate".equals(type)){
				if(StringUtil.isNotNull(plate)){
					map.put("plate", plate);
					map.put("plate_color", plate_color);
				}
			}else if("mac".equals(type)){
				if(StringUtil.isNotNull(mac))
					map.put("mac", mac.toLowerCase());
			}else if("imei,imsi".equals(type)){
				if(StringUtil.isNotNull(imei) || StringUtil.isNotNull(imsi)){
					map.put("imei", imei);
					map.put("imsi", imsi);
				}
			}
			result = HttpPostSANHUI.sendPostTest(url, type, map);
			System.out.println("比对服务测试relust="+result);
			super.printText(result);
		}catch (Exception e) {
			super.printText(e.getMessage());
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	private void showPerson(String relust){
		if(StringUtil.isNull(relust)) return;
		try {
			JSONObject personObject = JSONObject.fromObject(relust);
			JSONArray datas  = personObject.getJSONArray("datas");//通过人员数据
			for(int i=0;i<datas.size();i++){
				JSONObject personObj = datas.getJSONObject(i);
				Set<String> set = personObj.entrySet();
				System.out.println("----------------personObject-data["+i+"]");
				System.out.println(set.toString());
				System.out.println("-------------------------------------------");
			}
		}catch (JSONException e) {
			logger.error("接口参数JSON解析失败："+e.getMessage());
			logger.error("relust="+relust);
		}
	}
	
	@SuppressWarnings("unchecked")
	private void showWarning(String relust){
		if(StringUtil.isNull(relust)) return;
		try {
			JSONObject warningObject = JSONObject.fromObject(relust);
			JSONArray datas  = warningObject.getJSONArray("datas");//比中人员数据
			for(int i=0;i<datas.size();i++){
				JSONObject warningObj = datas.getJSONObject(i);
				Set<String> set = warningObj.entrySet();
				System.out.println("----------------warningObj-data["+i+"]");
				System.out.println(set.toString());
				System.out.println("-------------------------------------------");
			}
		}catch (JSONException e) {
			logger.error("接口参数JSON解析失败："+e.getMessage());
			logger.error("relust="+relust);
		}
	}	
	/////////////////////////////////////////insertPerson///////////////////////////////////////
	@ResponseBody
	@RequestMapping(value="/test_ajaxInsertPerson")
	public String ajaxInsertPerson() {
		try {
			Set<String> stations = orgzonService.getStations();
			for(String id : stations){
				Random random = new Random();
				BaseDao dao = new BaseDao();
				List<Equipment> dataDoorList = BaseDataCode.dataDoorList;
				Person person = new Person(UUIDFactory.getUUIDStr());
				person.setAddress("新疆维吾尔自治区喀什地区喀什市南湖小区258号");
				person.setCaptureTime(new Date());
				person.setStartDate("2010-12-3");
				person.setEndDate("2030-12-3");
				person.setEquipmentId(randomDoorId(id));
				person.setLocation(randomDoorName(person.getEquipmentId()));
				person.setIdcard(IdCardGenerator.randomIdcard());
				person.setGender(IdCardGenerator.gender(person.getIdcard()));
				person.setLocalImgUrl(randomPersonImage(super.obtainBasePath()));
				person.setImgUrl(person.getLocalImgUrl());
				
/*				String url = person.getLocalImgUrl();
				Date date = new Date();
				String fileName = CalendarUtil.format(date, "yyyyMMddHHmmss")+person.getIdcard()+".jpg";
				String root =  "\\\\192.168.57.128\\share\\cetc\\datadoorPic\\";
				//String root =  "\\\\"+BaseDataCode.config.getFileServerIp()+"\\cetc\\rzhj\\";
				String path_url = root + CalendarUtil.format(date, "yyyyMM")+"\\"+CalendarUtil.format(date, "yyyyMMdd")+"\\";
				saveToFile(url,path_url,path_url+fileName); */
				
				
				person.setIsAPerson("1");
				person.setNames(IdCardGenerator.randomName());
				dao.save(person);
				dao.close();
				Equipment e = dataDoorList.get(random.nextInt(dataDoorList.size()));
				BaseDataCode.putPersonMap(e.getId().substring(0, 13), person);
				String codes = BaseDataCode.config.getFrontId().substring(0, 13); //获取当前组织机构
				Orgzon org = BaseDataCode.orgMap.get(codes);
				try {
					String strURL = BaseDataCode.config.getComparBigDataURL();
					Map<String,String> map = new HashMap<String,String>();
					map.put("id", person.getIdcard());
					String relust = HttpPostSANHUI.sendPost(strURL, "person", map, org);
					if(!StringUtil.isNull(relust)){
						try {
							JSONObject jsonObject = JSONObject.fromObject(relust);
							String flag = jsonObject.getString("flag");//【白名单、黑名单、普通】的标志
							if("black".equals(flag)){
								addContrastPerson(jsonObject,e,person);
							}
						}catch (JSONException e2) {}
					}	
				} catch (Exception e3) {
					logger.error(e3.getMessage());
				}
			}
			
		} catch (Exception e) {}
		super.printText("插入一条人员数据" + CalendarUtil.getCurrentTime());
		return null;
	}
	
	/**
	 * 根据网络地址保存图片 
	 * @param destUrl 网络地址
	 * @param filePath 图片存储路径
	 */
/*	public void saveToFile(String destUrl,String imgFilePath,String filePath) {  
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
	} */
	
	/**
	 * 三汇比中车辆 保存数据
	 * @param dao
	 * @param datas
	 * @param t
	 * @param em
	 * @throws Exception
	 */
	private void addContrastPerson(JSONObject jsonObject,Equipment em,Person person) throws Exception{
		BaseDao dao = null;
		try {
			JSONArray datas = jsonObject.getJSONArray("data");
			if(datas != null && datas.size() >0){
				JSONObject data = datas.getJSONObject(0);
				String idcard = data.getString("sfzh");
				dao = new BaseDao();
				ContrastPerson type = new ContrastPerson(data.getString("uuid"));//人员预警信息
				type.setIdcard(idcard);
				type.setNames(person.getNames());
				type.setAddress(person.getAddress());
				type.setTag(data.getString("datasource"));
				type.setAction(data.getString("action"));
				type.setBkArea(data.getString("bkfwmc"));
				type.setYjArea(data.getString("yjfwmc"));
				type.setCaptureTime(person.getCaptureTime());
				type.setGatherPhotoURL(person.getImgUrl());
				type.setInsertTime(new Date());
				type.setSource("本站产生");
				type.setLocation(em.getNames());
				type.setBirth(IdCardGenerator.birth(type.getIdcard()));
				type.setGender(IdCardGenerator.gender(type.getIdcard()));
				type.setEquipmentId(em.getId());
				type.setAreaCode(em.getId().substring(0, 6));
				type.setCheckPointId(em.getId().substring(0, 13));
				dao.saveOrUpdate(type);
				BaseDataCode.putContrastPersonMap(em.getId().substring(0, 13), type);
			}
		}catch (Exception e) {
			logger.error(e.getMessage(),e);
		}finally{
			if(dao != null){dao.close();}
		}
	}

	/**
	 * 随机获取车道编号
	 * 
	 * @return
	 */
	private String randomDoorName(String id) {
		HashMap<String, Equipment> equipmentMap = BaseDataCode.equipmentMap;
		Equipment e = equipmentMap.get(id);
		return e.getNames();
	}

	// ///////////////////////////////////////insertVehicle///////////////////////////////////////
	@ResponseBody
	@RequestMapping(value="/test_ajaxInsertTest")
	public String ajaxInsertTest() {
		String params = "{\"ver\":\"1.0\",\"tranNO\":\"B605A79DC00A4E14AA99629AE21E33E1\",\"tranType\":\"9001\",\"sendTime\":\"2017-10-17 13:54:08\",\"key\":\"F6C9E44489BD0A7F4921BFB7E0EB932C\",\"data\":{\"passageaway\":\"6502030100001-CZHY02\",\"dataNum\":\"1937D23A921343B7AA92EA609AD22BD6\",\"passdate\":\"2017-10-17 13:54:08\",\"cardata\":[{\"carNum\":\"新JC9338\",\"carImg\":\"\\\\21.224.250.13\\cetc\\diffluence\\vehicle\\20171017\\13\\20171017135408_out_v_1_新JC9339.jpg\",\"plateColor\":\"蓝\"}],\"driverdata\":[],\"passengerdata\":[]}}";   
		try {
			String strURL = super.obtainBasePath()+"vehicle_czhy.action";
			String relust = HttpPost.sendPost(strURL, params);
			System.out.println(relust);
		} catch (Exception e) {}
		return null;
	}
	
	@ResponseBody
	@RequestMapping(value="/test_ajaxInsertVehicle")
	public String ajaxInsertVehicle() {
		try {
			Set<String> stations = orgzonService.getStations();
			for (String id : stations) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("ver", "1.0");
				map.put("tranNO", UUIDFactory.getUUIDStr());
				map.put("tranType", "9001");
				map.put("sendTime", CalendarUtil.getCurrentTime());

				HashMap<String, Object> data = new HashMap<String, Object>();
				data.put("passageaway", randomPassageaway(id));// randomPassageaway());
				data.put("dataNum", UUIDFactory.getUUIDStr());
				data.put("passdate", CalendarUtil.getCurrentTime());

				HashMap<String, Object> cardata = new HashMap<String, Object>();
				cardata.put("carNum", randomPlate());
				cardata.put("plateColor", "2");
				cardata.put("carImg", randomVehicleImage(super.obtainBasePath()));
				cardata.put("vehicleType", "3");

				HashMap<String, Object> driverdata = new HashMap<String, Object>();
				driverdata.put("userName", IdCardGenerator.randomName());
				driverdata.put("minzu", "维族");
				driverdata.put("cardNum", IdCardGenerator.randomIdcard());
				driverdata.put("sex", IdCardGenerator
						.gender((String) driverdata.get("cardNum")));
				driverdata.put("birthDate", IdCardGenerator
						.birth((String) driverdata.get("cardNum")));
				driverdata.put("address", "新疆维吾尔自治区喀什地区喀什市南湖小区258号");
				driverdata.put("qianfa", "喀什市公安局");
				driverdata.put("youxiaoqi", "20年");
				driverdata.put("cardImg", randomDriverImage(super.obtainBasePath()));
				driverdata.put("result", "1");
				data.put("cardata", StringUtil.obj2Array(cardata));
				data.put("driverdata", StringUtil.obj2Array(driverdata));
				List<Map<String, Object>> passengerdata = new ArrayList<Map<String, Object>>();
				data.put("passengerdata", passengerdata);
				for (int i = 0; i < 3; i++) {
					Map<String, Object> passenger = new HashMap<String, Object>();
					passenger.put("userName", IdCardGenerator.randomName());
					passenger.put("cardNum", IdCardGenerator.randomIdcard());
					passenger.put("sex", IdCardGenerator
							.gender((String) passenger.get("cardNum")));
					passenger.put("minzu", "维族");
					passenger.put("birthDate", IdCardGenerator
							.birth((String) passenger.get("cardNum")));
					passenger.put("address","新疆维吾尔自治区喀什地区喀什市南湖小区258号");
					passenger.put("qianfa", "喀什市公安局");
					passenger.put("youxiaoqi", "20年");
					passenger.put("cardImg", "http://");
					passengerdata.add(passenger);
				}
				map.put("data", data);

				String strURL = super.obtainBasePath()+"vehicle_czhy.action";
				String params = StringUtil.obj2json(map);
				String relust = HttpPost.sendPost(strURL, params);
				logger.debug("HttpPost--relust:" + relust);
				map.put("tranType", "5001");
				String relust2 = HttpPost.sendPost(strURL, StringUtil.obj2json(map));
				logger.debug("HttpPost--relust:" + relust2);
			}
			super.printText("插入一条车辆数据" + CalendarUtil.getCurrentTime());
		} catch (Exception e) {}
		return null;
	}

	@ResponseBody
	@RequestMapping(value="/test_ajaxInsertShunt")
	public String ajaxInsertShunt() {
		try {
			Set<String> stations = orgzonService.getStations();
			for (String id : stations) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("ver", "1.0");
				map.put("tranNO", UUIDFactory.getUUIDStr());
				map.put("tranType", "1001");
				map.put("sendTime", CalendarUtil.getCurrentTime());

				HashMap<String, Object> data = new HashMap<String, Object>();
				data.put("deviceIp", "127.0.0.1");
				data.put("plate", randomPlate());
				data.put("vehicleType","小轿车");
				data.put("color","2");
				data.put("vehicleImage",randomVehicleImage(super.obtainBasePath()));
				data.put("plateImage","");
				data.put("passageaway", id+"-CZHY00");

				map.put("data", data);

				String strURL = super.obtainBasePath()+"vehicle_czhy";
				String params = StringUtil.obj2json(map);
				String relust = HttpPost.sendPost(strURL, params);
				logger.debug("HttpPost--relust:" + relust);
			}
			super.printText("插入一条前值卡口车辆数据" + CalendarUtil.getCurrentTime());
		} catch (Exception e) {}
		return null;
	}
	// /////////////////////////////////////////////////////////////////////////////////////
	@ResponseBody
	@RequestMapping(value="/test_ajaxDeleteData")
	public String ajaxDeleteData() {
		try {
			BaseDao dao = new BaseDao();
			dao.execute("delete from CHECK_PERSON");
			dao.execute("delete from CHECK_PERSON_PEERS");
			dao.execute("delete from CHECK_VEHICLE");
			dao.execute("delete from CHECK_VEHICLE_PASSENGER");
			dao.execute("delete from CSL_CONTRAST_PERSON");
			dao.execute("delete from CSL_CONTRAST_VEHICLE");
			dao.execute("delete from CSL_PASSENGER");
			dao.execute("delete from CSL_PERSON");
			dao.execute("delete from CSL_PERSON_WARNING");
			dao.execute("delete from CSL_VEHICLE");
			dao.execute("delete from CSL_VEHICLE_BRAKE");
			dao.execute("delete from CSL_VEHICLE_SHUNT");
			BaseDataCode.getPersonMap().clear();
			BaseDataCode.getContrastVehicleMap().clear();
			BaseDataCode.todayVehicleShuntContrastMap.clear();
			BaseDataCode.getContrastPersonMap().clear();
			BaseDataCode.vehicleMap.clear();
			dao.close();
			super.printText("清除测试数据" + CalendarUtil.getCurrentTime());
		} catch (Exception e) {

		}
		return null;

	}

	private static String randomPlate() {
		String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		Random random = new Random();
		String carNo = str.charAt(random.nextInt(26)) + "";
		for (int i = 0; i < 6; i++) {
			int num = random.nextInt(10);
			if (num < 5) {// 字母与数字的概率相同
				carNo += random.nextInt(10);
			} else {
				carNo += str.charAt(random.nextInt(26));
			}
		}
		return "新" + carNo;
	}

	/** 随机车辆照片 */
	private static String randomVehicleImage(String rootPath) {
		Random random = new Random();
		String relust = rootPath + "img/car0";
		relust += (random.nextInt(5) + 1);
		relust += ".jpg";
		return relust;
	}

	/** 随机人员照片 */
	private static String randomPersonImage(String rootPath) {
		Random random = new Random();
		String relust = rootPath + "img/person0";
		relust += (random.nextInt(5) + 1);
		relust += ".jpg";
		return relust;
	}

	/** 随机人员照片 */
	private static String randomDriverImage(String rootPath) {
		Random random = new Random();
		String relust = rootPath + "img/driver0";
		relust += (random.nextInt(5) + 1);
		relust += ".jpg";
		return relust;
	}

	/**
	 * 随机获取车道编号
	 * 
	 * @return
	 */
	private static String randomPassageaway(String id) {
		HashMap<String, Equipment> equipmentMap = BaseDataCode.equipmentMap;
		int size = equipmentMap.size();
		Random r = new Random();
		Equipment e = equipmentMap.get(equipmentMap.keySet().toArray()[r
				.nextInt(size)]);
		if (e.getId().indexOf("CZHY") != -1) {
			if (e.getId().contains(id))
				return e.getId();
			else
				return randomPassageaway(id);
		}
		return randomPassageaway(id);
	}

	/**
	 * 随机获取车道编号
	 * 
	 * @return
	 */
	private String randomDoorId(String id) {
		HashMap<String, Equipment> equipmentMap = BaseDataCode.equipmentMap;
		int size = equipmentMap.size();
		Random r = new Random();
		Equipment e = equipmentMap.get(equipmentMap.keySet().toArray()[r
				.nextInt(size)]);
		if (e.getId().indexOf("SJM") != -1) {
			if (e.getId().contains(id))
				return e.getId();
			else
				return randomDoorId(id);
		}
		return randomDoorId(id);
	}
	
	/**
	 * 非ajax请求调转到/WEB-INF/pages/{param}.jsp页面
	 * 参考springmvc-servlet.xml的配置
	 */
	@RequestMapping("/test_{param}")
    public String direction(@PathVariable("param")String param){
        return path+param;
    }
}