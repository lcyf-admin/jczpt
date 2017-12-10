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

public class TestUploadPerson {
	
	private static String url = "http://127.0.0.1:8888/jczhj/upload/person";
	
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
		map.put("total", "5");
		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
		map.put("data", data);
		for(int i=0;i<5;i++){
			Map<String,Object> m = new HashMap<String, Object>();
			data.add(m);
			m.put("id", UUIDFactory.getUUIDStr());
			m.put("passTime", CalendarUtil.getCurrentTime());
			String idcard = IdCardGenerator.randomIdcard();
			m.put("idcard", idcard);
			m.put("name", IdCardGenerator.randomName());
			m.put("gender", IdCardGenerator.gender(idcard));
			m.put("nation", "汉族");
			m.put("birthday", IdCardGenerator.birth(idcard));
			m.put("address", "XX省XX市XX县XX小区XX地址");
			m.put("authority", "签发机关");
			m.put("validDate", "有效期");
			m.put("personImgUrl", "http://127.0.0.1:8889/14.png");
			m.put("idcardImgUrl", "http://127.0.0.1:8889/14.png");
			
			m.put("equipmentId", "采集设备编号");
			m.put("equipmentName", "采集设备名称");
			m.put("equipmentType", "采集设备类型");
			m.put("location", "二号数据门");
		}
		String params = StringUtil.obj2json(map);
		System.out.println("params="+params);
		
		String relust = HttpPost.sendPost(url, params);
		System.out.println("relust="+relust);
		
		
	}
}
