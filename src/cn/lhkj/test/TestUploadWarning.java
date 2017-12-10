package cn.lhkj.test;

import java.util.HashMap;
import java.util.Map;
import cn.lhkj.commons.base.BaseDataCode;
import cn.lhkj.commons.base.BaseDataDict;
import cn.lhkj.commons.tools.HttpPost;
import cn.lhkj.commons.tools.IdCardGenerator;
import cn.lhkj.commons.tools.UUIDFactory;
import cn.lhkj.commons.util.CalendarUtil;
import cn.lhkj.commons.util.StringUtil;

public class TestUploadWarning {
	
	private static String url = "http://192.168.16.108:8888/jczhj/upload/warning";
	
	public static void main(String[] args) {
		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("sendTime", CalendarUtil.getCurrentTime());
		map.put("areaCode", "654002");
		map.put("x", "81.302933");
		map.put("y", "43.987594");
		map.put("stationId", "6540020100001");
		map.put("stationName", "达达木图检查站");
		map.put("type", "vehicle");
		Map<String,Object> data = new HashMap<String, Object>();
		map.put("data", data);
		data.put("passTime", CalendarUtil.getCurrentTime());
		data.put("plateNum", IdCardGenerator.randomPlate());
		data.put("plateColor", "蓝色");
		data.put("vehicleType", "小轿车");
		data.put("vehicleImgUrl", "http://127.0.0.1:8889/00.jpg");
		data.put("plateImgUrl", "http://127.0.0.1:8889/00.jpg");
		data.put("equipmentId", "采集设备编号");
		data.put("equipmentName", "采集设备名称");
		data.put("equipmentType", "采集设备类型");
		data.put("location", "喀什地区喀什市南关检查站小车特检通道③");
		data.put("uuid", UUIDFactory.getUUIDStr());
		data.put("car_type", "车辆品牌");
		data.put("sfzh", IdCardGenerator.randomIdcard());
		data.put("name", IdCardGenerator.randomName());
		data.put("identlocation", "XX省Xx市XX县XX地址");
		data.put("tag", "二手车牌");
		data.put("datasource", "标签");
		data.put("action", "处置措施");
		data.put("person_photo", "base64车主照片");
		data.put("bkfwdm", "布控范围代码");
		data.put("bkfwmc", "布控范围名称");
		data.put("yjfwdm", "预警范围代码");
		data.put("yjfwmc", "预警范围名称");
		
		
		String params = StringUtil.obj2json(map);
		System.out.println("params="+params);
		BaseDataDict.init();//数据字典初始化
		BaseDataCode.init();//将设备信息放入内存
		String relust = HttpPost.sendPost(url, params);
		System.out.println("relust="+relust);
		
		Map<String,Object> map2 = new HashMap<String, Object>();
		map2.put("sendTime", CalendarUtil.getCurrentTime());
		map2.put("areaCode", "654002");
		map2.put("x", "81.302933");
		map2.put("y", "43.987594");
		map2.put("stationId", "6540020100001");
		map2.put("stationName", "达达木图检查站");
		map2.put("type", "person");
		Map<String,Object> data2 = new HashMap<String, Object>();
		map2.put("data", data2);
		data2.put("passTime", CalendarUtil.getCurrentTime());
		data2.put("name", IdCardGenerator.randomName());
		data2.put("idcard", IdCardGenerator.randomIdcard());
		data2.put("gender", IdCardGenerator.gender((String)data2.get("idcard")));
		data2.put("nation", "民族");
		data2.put("birthday", IdCardGenerator.birth((String)data2.get("idcard")));
		data2.put("address", "地址");
		data2.put("authority", "签发机关");
		data2.put("validDate", "有效期");
		data2.put("personImgUrl", "http://127.0.0.1:8889/00.png");
		data2.put("equipmentId", "采集设备编号");
		data2.put("equipmentName", "采集设备名称");
		data2.put("equipmentType", "采集设备类型");
		data2.put("location", "喀什地区喀什市南关检查站7号数据门");
		data2.put("uuid", UUIDFactory.getUUIDStr());
		data2.put("datasource", "标签");
		data2.put("action", "处置措施");
		data2.put("bkfwdm", "布控范围代码");
		data2.put("bkfwmc", "布控范围名称");
		data2.put("yjfwdm", "预警范围代码");
		data2.put("yjfwmc", "预警范围名称");
		String params2 = StringUtil.obj2json(map2);
		System.out.println("params="+params);
		
		String relust2 = HttpPost.sendPost(url, params2);
		System.out.println("relust2="+relust2);
		
		
	}
}
