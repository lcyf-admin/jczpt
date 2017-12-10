package cn.lhkj.project.imei.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import cn.lhkj.commons.base.BaseDao;
import cn.lhkj.commons.base.BaseDataCode;
import cn.lhkj.commons.base.BaseService;
import cn.lhkj.commons.scan.thread.ImeiThread;
import cn.lhkj.commons.tools.HttpPostSANHUI;
import cn.lhkj.commons.tools.IdCardGenerator;
import cn.lhkj.commons.util.CalendarUtil;
import cn.lhkj.commons.util.StringUtil;
import cn.lhkj.project.contrast.entity.ContrastPerson;
import cn.lhkj.project.imei.entity.Imei;
import cn.lhkj.project.system.entity.Orgzon;

@Service("imeiService")
@Scope("prototype")
public class ImeiServiceImpl extends BaseService implements ImeiService {
	
	private static final Logger logger = Logger.getLogger(ImeiServiceImpl.class);
	@Override
	public Imei getImei(String id) throws Exception {
		return (Imei)baseDao.getEntity(Imei.class, id);
	}
	
	@Override
	public Map<String, String> gather(JSONObject json, String ip)
			throws Exception {
		Map<String,String> map = new HashMap<String, String>();
		String passTime = StringUtil.trim(json.getString("passTime"));
		JSONArray data = json.getJSONArray("data");
		if(data != null && data.size() > 0){
			for(int i=0;i<data.size();i++){
				JSONObject o = data.getJSONObject(i);
				Imei t = new Imei(StringUtil.trim(o.getString("id")));
				t.setImei(StringUtil.trim(o.getString("imei")).toLowerCase());
				t.setImsi(StringUtil.trim(o.getString("imsi")).toLowerCase());
				t.setInsertTime(new Date());
				t.setIp(ip);
				t.setMac(StringUtil.trim(o.getString("mac")).toLowerCase());
				t.setPassTime(CalendarUtil.toDate(passTime, "yyyy-MM-dd HH:mm:ss"));
				t.setPhoneNum(StringUtil.trim(o.getString("phoneNum")));
				baseDao.saveOrUpdate(t);
				new Thread(new ImeiThread(t)).start();
			}
			map.put("sendTime", CalendarUtil.getCurrentTime());
			map.put("code", "200");
			map.put("msg", "比对成功");
		}else{
			map.put("sendTime", CalendarUtil.getCurrentTime());
			map.put("code", "400");
			map.put("msg", "无数据");
		}
		return map;
	}
	
	@Override
	public Map<String, String> uploadPhone(JSONObject json, String ip)
			throws Exception {
		Map<String,String> map = new HashMap<String, String>();
		String passTime = StringUtil.trim(json.getString("passTime"));
		JSONArray data = json.getJSONArray("data");
		String warnPhone = "";
		if(data != null && data.size() > 0){
			for(int i=0;i<data.size();i++){
				JSONObject o = data.getJSONObject(i);
				Imei t = new Imei(StringUtil.trim(o.getString("id")));
				t.setImei(StringUtil.trim(o.getString("imei")).toLowerCase());
				t.setImsi(StringUtil.trim(o.getString("imsi")).toLowerCase());
				t.setInsertTime(new Date());
				t.setIp(ip);
				t.setMac(StringUtil.trim(o.getString("mac")).toLowerCase());
				t.setPassTime(CalendarUtil.toDate(passTime, "yyyy-MM-dd HH:mm:ss"));
				t.setPhoneNum(StringUtil.trim(o.getString("phoneNum")));
				baseDao.saveOrUpdate(t);
				warnPhone = compare(t);
			}
			map.put("sendTime", CalendarUtil.getCurrentTime());
			map.put("code", "200");
			map.put("msg", "比对成功");
			map.put("warnPhone", warnPhone);
		}else{
			map.put("sendTime", CalendarUtil.getCurrentTime());
			map.put("code", "400");
			map.put("msg", "无数据");
			map.put("warnPhone", "");
		}
		return map;
	}

	/**
	 * 与三汇大数据平台比对
	 * @param dao
	 * @param datas
	 * @param t
	 * @param em
	 * @throws Exception
	 */
	private String compare(Imei t) throws Exception{
		String strURL = BaseDataCode.config.getComparBigDataURL();
		if(StringUtil.isNull(strURL)) return "0";
		String codes = BaseDataCode.config.getFrontId().substring(0, 13); //获取当前组织机构
		Orgzon org = BaseDataCode.orgMap.get(codes);
		
		String mac = StringUtil.trim(t.getMac());
		String imei = StringUtil.trim(t.getImei());
		String imsi = StringUtil.trim(t.getImsi());
		if (StringUtil.isNotNull(mac)) {
			Map<String,String> map = new HashMap<String,String>();
			map.put("mac", mac);
			String relust = HttpPostSANHUI.sendPost(strURL, "mac", map, org);
			if(StringUtil.isNull(relust)) return "0";  //暂无数据
			try {
				JSONObject jsonObject = JSONObject.fromObject(relust);
				String flag = jsonObject.getString("flag");//【白名单、黑名单、普通】的标志
				JSONArray datas = jsonObject.getJSONArray("data");
				if(datas == null || datas.size()==0){
					if(!"white".equals(flag)){
						flag = "normal";
					}
				}
				if("black".equals(flag)){
					addContrast(jsonObject,"mac"); //添加手机mac预警
					return "1";
				}
			}catch (JSONException e) {
				//return e.getMessage()
				return "0";
			}
		}else if (StringUtil.isNotNull(imei) || StringUtil.isNotNull(imsi)) {
			Map<String,String> map = new HashMap<String,String>();
			map.put("imei", imei);
			map.put("imsi", imsi);
			String relust = HttpPostSANHUI.sendPost(strURL, "imei,imsi", map, org);
			if(StringUtil.isNull(relust)) return "0";  //暂无数据
			try {
				JSONObject jsonObject = JSONObject.fromObject(relust);
				String flag = jsonObject.getString("flag");//【白名单、黑名单、普通】的标志
				JSONArray datas = jsonObject.getJSONArray("data");
				if(datas == null || datas.size()==0){
					if(!"white".equals(flag)){
						flag = "normal";
					}
				}
				if("black".equals(flag)){
					addContrast(jsonObject,"imei,imsi"); //添加手机mac预警
					return "1";
				}
			}catch (JSONException e) {
				//return e.getMessage()
				return "0";
			}				
		}
		return "0";	
	}
	
	/**
	 * 三汇比中MAC 保存数据
	 * @param dao
	 * @param datas
	 * @param t
	 * @param em
	 * @throws Exception
	 */
	private void addContrast(JSONObject jsonObject,String res) throws Exception{
		BaseDao dao = null;
		try {
			JSONArray datas = jsonObject.getJSONArray("data");
			if(datas == null || datas.size() ==0) return;
			dao = new BaseDao();
			String station_id = BaseDataCode.config.getFrontId().substring(0, 13);
			JSONObject data = datas.getJSONObject(0);
			String idcard = data.getString("sfzh");
			ContrastPerson type = new ContrastPerson(data.getString("uuid"));//人员预警信息
			type.setIdcard(idcard);
			type.setGender(IdCardGenerator.gender(idcard));
			type.setBirth(IdCardGenerator.birth(idcard));
			type.setCaptureTime(new Date());
			type.setNames(data.getString("name"));
			type.setAddress(data.getString("identlocation"));
			type.setTag(data.getString("datasource"));
			type.setAction(data.getString("action"));
			type.setBkArea(data.getString("bkfwmc"));
			type.setYjArea(data.getString("yjfwmc"));
			if ("mac".equals(res)) {
				type.setMac(data.getString("mac"));
			}else if ("imei,imsi".equals(res)) {
				type.setImei(data.getString("imei"));
				type.setImsi(data.getString("imsi"));
			}
			type.setLocation("侦码设备");
			type.setEquipmentId(station_id);
			type.setAreaCode(station_id.substring(0, 6));
			type.setCheckPointId(station_id.substring(0, 13));
			dao.saveOrUpdate(type);
			BaseDataCode.putContrastPersonMap(type.getEquipmentId().substring(0, 13), type);
		}catch (Exception e) {
			logger.error(e.getMessage(),e);
		}finally{
			if(dao != null){dao.close();}
		}
	}	
}
