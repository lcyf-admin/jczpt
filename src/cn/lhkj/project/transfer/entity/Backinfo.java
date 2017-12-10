package cn.lhkj.project.transfer.entity;

import java.util.HashMap;

import cn.lhkj.commons.util.StringUtil;

/** 富士车证合一和分流接口返回数据记录  */
public class Backinfo {
	
	public static String backMsg(Transfer transfer){
		if(transfer == null) return "";
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("tranNO", transfer.getTranNO());
		map.put("ver", transfer.getVer());
		Integer i_TranType = null;
		try{
			i_TranType = Integer.parseInt(transfer.getTranType()) + 1;
		}catch (Exception e){}
		map.put("tranType", StringUtil.trim(i_TranType));
		map.put("tranResult", transfer.getTranResult());
		map.put("tranMsg", transfer.getTranMsg());
		map.put("sendTime", transfer.getSendTime());
		map.put("key", transfer.getKey());
		map.put("data", null);
		return StringUtil.obj2json(map);
	}
	
}
