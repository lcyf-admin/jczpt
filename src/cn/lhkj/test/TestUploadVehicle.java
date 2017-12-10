package cn.lhkj.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.lhkj.commons.base.BaseDataCode;
import cn.lhkj.commons.base.BaseDataDict;
import cn.lhkj.commons.tools.HttpPost;
import cn.lhkj.commons.tools.IdCardGenerator;
import cn.lhkj.commons.tools.UUIDFactory;
import cn.lhkj.commons.util.CalendarUtil;
import cn.lhkj.commons.util.StringUtil;

public class TestUploadVehicle {
	
	private static String url = "http://127.0.0.1:8888/jczhj/upload/vehicle";
	
	public static void main(String[] args) {
		BaseDataDict.init();//数据字典初始化
		BaseDataCode.init();//将设备信息放入内存
		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("sendTime", CalendarUtil.getCurrentTime());
		map.put("areaCode", "654002");
		map.put("x", "81.302933");
		map.put("y", "43.987594");
		map.put("stationId", "6540020100001");
		map.put("stationName", "达达木图检查站");
		map.put("total", "10");
		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
		map.put("data", data);
		for(int i=0;i<10;i++){
			Map<String,Object> m = new HashMap<String, Object>();
			data.add(m);
			m.put("id", UUIDFactory.getUUIDStr());
			m.put("passTime", CalendarUtil.getCurrentTime());
			m.put("plateNum", IdCardGenerator.randomPlate());
			m.put("plateColor", "蓝色");
			m.put("vehicleType", "小轿车");
			m.put("vehicleImgUrl", "http://127.0.0.1:8889/00.jpg");
			m.put("plateImgUrl", "http://127.0.0.1:8889/00.jpg");
			m.put("equipmentId", "采集设备编号");
			m.put("equipmentName", "采集设备名称");
			m.put("equipmentType", "采集设备类型");
			m.put("location", "三号安检车道");
			m.put("result", "一般车辆");
			List<Map<String,Object>> driverData = new ArrayList<Map<String,Object>>();
			m.put("driverData", driverData);
			Map<String,Object> driverMap = new HashMap<String, Object>();
			driverData.add(driverMap);
			String idcard = IdCardGenerator.randomIdcard();
			driverMap.put("idcard", idcard);
			driverMap.put("name", IdCardGenerator.randomName());
			driverMap.put("gender", IdCardGenerator.gender(idcard));
			driverMap.put("nation", "汉族");
			driverMap.put("birthday", IdCardGenerator.birth(idcard));
			driverMap.put("address", "XX省XX市XX县XX小区XX地址");
			driverMap.put("authority", "签发机关");
			driverMap.put("validDate", "有效期");
			driverMap.put("personImgUrl", "http://127.0.0.1:8889/14.png");
			driverMap.put("idcardImgUrl", "http://127.0.0.1:8889/14.png");
			m.put("passengerData", "[]");
		}
		String params = StringUtil.obj2json(map);
		System.out.println("params="+params);
		
		String relust = HttpPost.sendPost(url, params);
		System.out.println("relust="+relust);
	}
}
