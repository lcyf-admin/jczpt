package cn.lhkj.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.lhkj.commons.base.BaseDataCode;
import cn.lhkj.commons.base.BaseDataDict;
import cn.lhkj.commons.tools.HttpPost;
import cn.lhkj.commons.tools.UUIDFactory;
import cn.lhkj.commons.util.CalendarUtil;
import cn.lhkj.commons.util.StringUtil;

public class TestImei {
	
	public static void main(String[] args) {
		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("passTime", CalendarUtil.getCurrentTime());
		map.put("ip", "127.0.0.1");
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		map.put("data", list);
		for (int i = 0; i < 1; i++) {
			Map<String,Object> o = new HashMap<String, Object>();
			o.put("id", UUIDFactory.getUUIDStr());
			o.put("mac", "");
			o.put("imei", "imei-"+i);
			o.put("imsi", "imsi-"+i);
			o.put("phoneNum", "phoneNum-"+i);
			list.add(o);			
		}

	
		
		String params = StringUtil.obj2json(map);
		BaseDataDict.init();//数据字典初始化
		BaseDataCode.init();//将设备信息放入内存
		String relust = HttpPost.sendPostTest("http://127.0.0.1:8888/jczpt/phone_upload", params);
		System.out.println("relust="+relust);
		
	}
}
