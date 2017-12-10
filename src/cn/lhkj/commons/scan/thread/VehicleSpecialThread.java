package cn.lhkj.commons.scan.thread;

import java.util.HashMap;

import org.apache.log4j.Logger;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import cn.lhkj.commons.base.BaseDao;
import cn.lhkj.commons.base.BaseDataCode;
import cn.lhkj.commons.tools.HttpPost;
import cn.lhkj.commons.tools.UUIDFactory;
import cn.lhkj.commons.util.CalendarUtil;
import cn.lhkj.commons.util.StringUtil;
import cn.lhkj.project.equipment.entity.Equipment;
import cn.lhkj.project.vehicle.entity.VehicleBrake;

/**
 * 特检通道比对
 */
public class VehicleSpecialThread implements Runnable {
	
	private static final Logger logger = Logger.getLogger(VehicleSpecialThread.class);
	
	private VehicleBrake t;
	private String deviceCode;
	private String backUrl;
	private String tranNO;
	
	public VehicleSpecialThread(VehicleBrake t,String tranNO){
		this.t = t;
		this.deviceCode = BaseDataCode.config.getDeviceCode();
		this.backUrl = t.getBackUrl();
		this.tranNO = tranNO;
	}
	
	@Override
	public void run(){
		if(t == null) return;
		try {
			String strURL = BaseDataCode.config.getComparSpecialURL();
			if(StringUtil.isNull(strURL)) return;
			if("1".equals(BaseDataCode.config.getTestModel())) return;
			
			Equipment em = BaseDataCode.equipmentMap.get(t.getEquipmentId());
			if(em == null) return;
			if(StringUtil.isNull(backUrl)){
				backUrl = em.getUrl();
				t.setBackUrl(backUrl);
			}
			if("9001".equals(t.getTranType())){//9001推送
				tranType9001(em, t, strURL);
			}else if("5001".equals(t.getTranType())){
				tranType5001(em, t, strURL);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	
	/**
	 * 来自9001的数据
	 * @param em
	 * @param t
	 * @param strURL
	 */
	private void tranType9001(Equipment em, VehicleBrake t, String strURL)throws Exception{
		BaseDao dao = null;
		HashMap<String, String> m = new HashMap<String, String>();
		m.put("deviceCode", deviceCode);
		m.put("plateNo", t.getCarNum());
		m.put("extendInfo", "");
		String relust= HttpPost.sendPost(strURL+"redList/carRedList", StringUtil.obj2json(m));
		if(StringUtil.isNull(relust)){ //比对结果失败
			comparError(em, t); 
			return; 
		}
		try {
			JSONObject jsonObject = JSONObject.fromObject(relust);
			String isRedList = jsonObject.getString("isRedList");//【白名单、黑名单、普通】的标志
			String jobId = jsonObject.getString("jobId");//作业编号
			t.setRemark(jobId);
			
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("tranNO", tranNO);
			map.put("ver", "1.0");
			map.put("sendTime", CalendarUtil.getCurrentTime());
			map.put("key", UUIDFactory.getUUIDStr());
			map.put("tranType", "1101");
			
			HashMap<String, Object> PassData = new HashMap<String, Object>();
			PassData.put("passageaway", t.getEquipmentId());
			
			t.setStates("0");
			if("1".equals(isRedList)){//红名单车辆
				t.setRelust("红名单车辆");
				PassData.put("message", "0|请开闸放行");
			}else{
				PassData.put("message", "2|请刷身份证");
			}
			map.put("data", PassData);
			String par = StringUtil.obj2json(map);
			HttpPost.sendPost(backUrl, par);
			
			dao = new BaseDao();
			dao.execute("update CSL_VEHICLE set relust='"+t.getRelust()+"',BACK_URL='"+backUrl+"' " +
					"where MONTH_ID='"+t.getMonthId()+"' and ID='"+t.getId()+"'");
			dao.execute("update CSL_VEHICLE_BRAKE set relust='"+t.getRelust()+"',BACK_URL='"+backUrl+"' " +
					"where MONTH_ID='"+t.getMonthId()+"' and ID='"+t.getId()+"'");
			
			HashMap<String, String> map2 = new HashMap<String, String>();
			map2.put("deviceCode", deviceCode);
			map2.put("jobId", jobId);
			map2.put("jobStatus", "1");
			map2.put("dataSource", "6");
			map2.put("dataType", "1");
			map2.put("plateNo", t.getCarNum());//新QZ3223
			map2.put("plateColor", color(t.getPlateColor()));
			map2.put("platePhoto", t.getCarImg());
			map2.put("carPhoto", t.getCarImg());
			map2.put("passTime", CalendarUtil.getCurrentTime());
			
			String par2 = StringUtil.obj2json(map2);
			HttpPost.sendPost(strURL+"passCar/passCarLog", par2);
		}catch (JSONException e) {
			logger.error("调用三所车辆比对接口错误，解析json错误："+relust);
			logger.error("调用三所车辆比对接口错误，解析json错误："+e.getMessage());
			comparError(em, t);
		}finally{
			if(dao != null){ dao.close(); }
		}
	}
	
	/**
	 * 5001推送的数据
	 * @param em
	 * @param t
	 * @param strURL
	 * @param dao
	 * @throws Exception
	 */
	private void tranType5001(Equipment em, VehicleBrake t, String strURL)throws Exception{
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("tranNO", tranNO);
		map.put("ver", "1.0");
		map.put("sendTime", CalendarUtil.getCurrentTime());
		map.put("key", UUIDFactory.getUUIDStr());
		map.put("tranType", "4001");
		HashMap<String, Object> PassData = new HashMap<String, Object>();
		PassData.put("passageaway", t.getEquipmentId());
		
		if(StringUtil.isNull(t.getCardNum())){
			PassData.put("message", "OK");
			map.put("data", PassData);
			String par = StringUtil.obj2json(map);
			HttpPost.sendPost(backUrl, par);
		}else{
			String personRelust = comparPerson(t.getCardNum(),strURL,t,em);
			if(StringUtil.isNull(personRelust)){
				PassData.put("message", "OK");
			}else{
				PassData.put("message", personRelust);
			}
			map.put("data", PassData);
			String par = StringUtil.obj2json(map);
			HttpPost.sendPost(backUrl, par);
		}
	}
	/**
	 * 三所平台比对失败
	 */
	private void comparError(Equipment em, VehicleBrake t){
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("tranNO", tranNO);
		map.put("ver", "1.0");
		map.put("sendTime", CalendarUtil.getCurrentTime());
		map.put("key", UUIDFactory.getUUIDStr());
		
		HashMap<String, Object> PassData = new HashMap<String, Object>();
		PassData.put("passageaway", t.getEquipmentId());
		
		if("9001".equals(t.getTranType())){
			map.put("tranType", "1101");
			PassData.put("message", "2|请刷身份证");
			map.put("data", PassData);
			String par = StringUtil.obj2json(map);
			HttpPost.sendPost(backUrl, par);
		}else if("5001".equals(t.getTranType())){
			map.put("tranType", "4001");
			PassData.put("message", "OK");
			map.put("data", PassData);
			String par = StringUtil.obj2json(map);
			HttpPost.sendPost(backUrl, par);
		}
	}
	
	/**
	 * 比对司机
	 * @param driver--5001接口
	 * @param strURL
	 * @param t
	 * @param em
	 * @return
	 * @throws Exception
	 */
	private String comparPerson(String cardNum,String strURL,VehicleBrake t , Equipment em) throws Exception{
		String r = "";
		HashMap<String, String> m = new HashMap<String, String>();
		m.put("deviceCode", deviceCode);
		m.put("cardNo", cardNum);
		m.put("extendInfo", "");
		String relust= HttpPost.sendPost(strURL+"redList/personRedList", StringUtil.obj2json(m));
		if(StringUtil.isNull(relust)) return r;
		try {
			JSONObject jsonObject = JSONObject.fromObject(relust);
			String isRedList = jsonObject.getString("isRedList");//【白名单、黑名单、普通】的标志
			if("1".equals(isRedList)){//红名单人人员
				return "";
			}else{
				return cardNum+",非红名单人员";
			}
		}catch (JSONException e) {
			logger.error("调用三所人员比对接口错误，解析json错误："+relust);
			logger.error("调用三所人员比对接口错误，解析json错误："+e.getMessage());
		}
		return r;
	}
	
	
	private String color(String s){
		if("蓝色".indexOf(s) != -1) return "0";
		if("黄色".indexOf(s) != -1) return "1";
		if("白色".indexOf(s) != -1) return "2";
		if("黑色".indexOf(s) != -1) return "3";
		if("绿色".indexOf(s) != -1) return "4";
		if("民航黑色".indexOf(s) != -1) return "5";
		return "255";
				
	}
}
