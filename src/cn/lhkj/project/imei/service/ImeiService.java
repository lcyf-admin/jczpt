package cn.lhkj.project.imei.service;

import java.util.Map;

import net.sf.json.JSONObject;
import cn.lhkj.project.imei.entity.Imei;

public interface ImeiService {
	
	/**
	 * 获取手机侦码实例
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Imei getImei(String id)  throws Exception;
	
	/**
	 * 上传侦码采集数据
	 * @param json
	 * @param ip
	 * @return
	 * @throws Exception
	 */
	public Map<String,String> gather(JSONObject json,String ip) throws Exception;
	
	/**
	 * 北京路北延检查站上传侦码采集数据
	 * @param json
	 * @param ip
	 * @return
	 * @throws Exception
	 */
	public Map<String,String> uploadPhone(JSONObject json,String ip) throws Exception;
	
}
