package cn.lhkj.commons.scan.thread;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import cn.lhkj.commons.base.BaseDao;
import cn.lhkj.commons.base.BaseDataCode;
import cn.lhkj.commons.tools.HttpPost;
import cn.lhkj.commons.tools.HttpPostSANHUI;
import cn.lhkj.commons.tools.IdCardGenerator;
import cn.lhkj.commons.tools.UUIDFactory;
import cn.lhkj.commons.util.CalendarUtil;
import cn.lhkj.commons.util.StringUtil;
import cn.lhkj.project.contrast.entity.ContrastPerson;
import cn.lhkj.project.contrast.entity.ContrastVehicle;
import cn.lhkj.project.equipment.entity.Equipment;
import cn.lhkj.project.lane.entity.Lane;
import cn.lhkj.project.system.entity.Orgzon;
import cn.lhkj.project.vehicle.entity.VehicleBrake;

/**
 * 线程监听车证合一采集的车辆是否允许通过
 */
public class VehicleBrakeThread implements Runnable {
	
	private static final Logger logger = Logger.getLogger(VehicleBrakeThread.class);
	
	private VehicleBrake t;
	private String backUrl;
	private String tranNO;
	
	public VehicleBrakeThread(VehicleBrake t,String tranNO){
		this.t = t;
		this.backUrl = t.getBackUrl();
		this.tranNO = tranNO;
	}
	
	@Override
	public void run(){
		if(t == null) return;
		try {
			String strURL = BaseDataCode.config.getComparBigDataURL();
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
		if(StringUtil.isNull(t.getCarNum()) || "无车牌".equals(t.getCarNum())){
			comparError(em, t); 
			return;
		}
		
		Map<String,String> postMap = new HashMap<String,String>();
		postMap.put("plate", t.getCarNum());
		postMap.put("plate_color", HttpPostSANHUI.dealWithPlateColor(t.getPlateColor()));
		if(StringUtil.trim(postMap.get("plate_color")).contains("黄")){//黄牌车不需要比对
			comparError(em, t); 
			return; 
		}
		
		String relust = BaseDataCode.todayVehicleShuntContrastMap.get(t.getCarNum());//在前置卡口预警车辆中找
		String codes = t.getEquipmentId().substring(0, 13); //获取当前组织机构
		Orgzon org = BaseDataCode.orgMap.get(codes);
		if(StringUtil.isNull(relust)){
			relust = HttpPostSANHUI.sendPost(strURL, "plate", postMap, org);//和大数据平台比对抓拍到的车辆
			if(StringUtil.isNull(relust)){ //比对结果失败
				comparError(em, t);
				return;
			}
		}
		
		try {
			JSONObject jsonObject = JSONObject.fromObject(relust);
			String flag = jsonObject.getString("flag");//【白名单、黑名单、普通】的标志
			JSONArray datas = jsonObject.getJSONArray("data");
			if(datas == null || datas.size()==0){
				if(!"white".equals(flag)){
					flag = "normal";
				}
			}
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("tranNO", tranNO);
			map.put("ver", "1.0");
			map.put("sendTime", CalendarUtil.getCurrentTime());
			map.put("key", UUIDFactory.getUUIDStr());
			
			HashMap<String, Object> PassData = new HashMap<String, Object>();
			PassData.put("passageaway", t.getEquipmentId());
			
			if("有人证机".equals(StringUtil.trim(em.getRemark()))){//有人证设备的
				map.put("tranType", "1101");
				t.setStates("0");
				t.setRelust("普通车辆");
				PassData.put("message", "2|请刷身份证");
				if("white".equals(flag)){
					t.setRelust("红名单车辆");
					PassData.put("message", "0|请开闸放行");
				}else if("black".equals(flag)){
					t = addContrastVehicle(jsonObject, t, em);
					if("1".equals(t.getStates())){
						PassData.put("message", "1|"+t.getCarNum()+"，请接受检查");
					}
				}
				map.put("data", PassData);
				map.put("sendTime", CalendarUtil.getCurrentTime());//最新的时间
				String par = StringUtil.obj2json(map);
				postKAIZHA(backUrl, par);
			}else if("无人证机".equals(StringUtil.trim(em.getRemark()))){//无人证设备的  不是黑名单就message=OK,是黑名单就message=皖AU207U，此车在黑名单中请重点排查
				map.put("tranType", "4001");
				t.setStates("0");
				t.setRelust("普通车辆");
				PassData.put("message", "OK");
				if("white".equals(flag)){
					t.setRelust("红名单车辆");
				}else if("black".equals(flag)){
					t = addContrastVehicle(jsonObject, t, em);
					if("1".equals(t.getStates())){
						PassData.put("message", t.getCarNum()+"，请接受检查");
					}
				}
				map.put("data", PassData);
				map.put("sendTime", CalendarUtil.getCurrentTime());
				String par = StringUtil.obj2json(map);
				postKAIZHA(backUrl, par);
			}
			dao = new BaseDao();
			dao.execute("update CSL_VEHICLE set relust='"+t.getRelust()+"',BACK_URL='"+backUrl+"' " +
					"where MONTH_ID='"+t.getMonthId()+"' and ID='"+t.getId()+"'");
			dao.execute("update CSL_VEHICLE_BRAKE set relust='"+t.getRelust()+"',BACK_URL='"+backUrl+"' " +
					"where MONTH_ID='"+t.getMonthId()+"' and ID='"+t.getId()+"'");
		}catch (JSONException e) {
			logger.error("调用三汇车辆比对接口错误，解析json错误："+relust);
			logger.error("调用三汇车辆比对接口错误，解析json错误："+e.getMessage());
			comparError(em, t);
		}finally{
			if(dao != null){dao.close();}
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
			postKAIZHA(backUrl, par);
		}else{
			String personRelust = comparPerson(t.getCardNum(),strURL,t,em);
			if(StringUtil.isNull(personRelust)){
				PassData.put("message", "OK");
			}else{
				PassData.put("message", personRelust);
			}
			map.put("data", PassData);
			map.put("sendTime", CalendarUtil.getCurrentTime());//最新的时间
			String par = StringUtil.obj2json(map);
			postKAIZHA(backUrl, par);
		}
	}
	/**
	 * 大数据平台比对失败
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
			if("有人证机".equals(em.getRemark())){//有人证设备的
				map.put("tranType", "1101");
				PassData.put("message", "2|请刷身份证");
				map.put("data", PassData);
			}else{//无人证设备的
				map.put("tranType", "4001");
				PassData.put("message", "OK");
				map.put("data", PassData);
			}
			String par = StringUtil.obj2json(map);
			postKAIZHA(backUrl, par);
		}else if("5001".equals(t.getTranType())){
			map.put("tranType", "4001");
			PassData.put("message", "OK");
			map.put("data", PassData);
			String par = StringUtil.obj2json(map);
			postKAIZHA(backUrl, par);
		}
	}
	
	/**
	 * 三汇比中车辆 保存数据---仅9001时候比车辆
	 * @param dao
	 * @param datas
	 * @param t
	 * @param em
	 * @throws Exception
	 */
	private VehicleBrake addContrastVehicle(JSONObject jsonObject,VehicleBrake t ,Equipment em) throws Exception{
		BaseDao dao = null;
		t.setStates("0");
		t.setRelust("普通车辆");
		try {
			JSONArray datas = jsonObject.getJSONArray("data");
			if(datas != null && datas.size() >0){
				JSONObject data = datas.getJSONObject(0);
				String car_type = StringUtil.trim(data.getString("car_type"));//比中返回的车辆品牌
				if(StringUtil.containsFyzzy(BaseDataCode.motoTypes, car_type)){//比中得到是摩托车数据
					t.setStates("0");
					t.setRelust("普通车辆");
					return t;
				}
				/*
				if(BaseDataCode.getContrasBrake().containsKey(t.getCarNum())){//今日已通行过的黑名单车
					long dely = new Date().getTime() - BaseDataCode.getContrasBrake().get(t.getCarNum());
					if(dely > 5*60*1000){//5分钟以前通行过的黑名单车--防止重复报警此次放行
						t.setStates("0");
						t.setRelust("黑名单车辆");
						return t;
					}else{//黑名单车辆未开闸，重复采集
						t.setStates("1");//发送给道闸设备报警标志
						t.setRelust("黑名单车辆");
						return t;
					}
				}*/
				
				dao = new BaseDao();
				//ContrastVehicle type = new ContrastVehicle(data.getString("uuid"));//车辆预警信息
				ContrastVehicle type = new ContrastVehicle(UUIDFactory.getUUIDStr());//车辆预警信息
				type.setVehicleId(t.getId());
				type.setCarNum(data.getString("plate"));
				type.setVehicleType(data.getString("car_type"));
				type.setPlateColor(t.getPlateColor());
				type.setIdcard(data.getString("sfzh"));
				type.setNames(data.getString("name"));
				type.setAddress(data.getString("identlocation"));
				type.setLabel(data.getString("tag"));
				type.setDescribe(data.getString("tag"));
				type.setTag(data.getString("datasource"));
				type.setPersonPhoto(data.getString("person_photo"));
				type.setAction(data.getString("action"));
				type.setBkArea(data.getString("bkfwmc"));
				type.setYjArea(data.getString("yjfwmc"));
				type.setPassdate(t.getPassdate());
				type.setInsertTime(new Date());
				type.setSource("本站产生");
				if(t.getLane() != null){
					type.setLocation(t.getLane().getLaneName());
				}else{
					Lane lane = (Lane)dao.getEntity(Lane.class, em.getLaneId());
					if(lane != null)
						type.setLocation(lane.getLaneName());
				}
				type.setEquipmentId(em.getId());
				type.setAreaCode(em.getId().substring(0, 6));
				type.setCheckPointId(em.getId().substring(0, 13));
				type.setGatherPhotoURL(t.getLocalCarImg());
				dao.saveOrUpdate(type);
				BaseDataCode.putContrastVehicleMap(em.getId().substring(0, 13), type);
				t.setStates("1");//发送给道闸设备报警标志
				t.setRelust("黑名单车辆");
			}
			return t;
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return t;
		}finally{
			if(dao != null){ dao.close(); }
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
		BaseDao dao = null;
		try {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Calendar calendar = Calendar.getInstance();
    		calendar.setTimeInMillis(System.currentTimeMillis());
    		calendar.add(Calendar.SECOND, -300);
    		String date = df.format(calendar.getTime());
			
			dao = new BaseDao();
			String r = "";
			String relust = "";
			Map<String,String> postMap = new HashMap<String,String>();
			postMap.put("id", cardNum);
			String codes = t.getEquipmentId().substring(0, 13); //获取当前组织机构
			Orgzon org = BaseDataCode.orgMap.get(codes);
			relust = HttpPostSANHUI.sendPost(strURL, "person", postMap ,org);//和大数据平台比对司机
			if(StringUtil.isNull(relust)){
				Integer count = dao.getCount("select count(1) as count from csl_contrast_vehicle where " +
						" month_id="+CalendarUtil.getCurrentMonth()+" " +
					 "and car_num='"+t.getCarNum()+"' " +
					 "and insert_time between to_date('"+date+"','yyyy-mm-dd hh24:mi:ss') and sysdate ");
				if(count != 0){
					r = t.getCarNum()+" 请接受检查 ";
				}
				return r;
			}
			try {
				JSONObject jsonObject = JSONObject.fromObject(relust);
				String flag = jsonObject.getString("flag");//【白名单、黑名单、普通】的标志
				if("black".equals(flag)){
					r = addContrastPerson(jsonObject, t, em);
				}
			}catch (JSONException e) {
				logger.error("调用三汇车辆比对接口错误，解析json错误："+relust);
				logger.error("调用三汇车辆比对接口错误，解析json错误："+e.getMessage());
			}
			if(StringUtil.isNull(r)){
				Integer count = dao.getCount("select count(1) as count from csl_contrast_vehicle where " +
						" month_id="+CalendarUtil.getCurrentMonth()+" " +
					 "and car_num='"+t.getCarNum()+"' " +
					 "and insert_time between to_date('"+date+"','yyyy-mm-dd hh24:mi:ss') and sysdate ");
				if(count != 0){
					r = t.getCarNum()+" 请接受检查 ";
				}
			}
			return r;
		}catch (Exception e) {
			logger.error(e.getMessage(),e);
			return "";
		}finally{
			if(dao != null){ dao.close(); }
		}
		
	}
	
	/**
	 * 三汇比中车辆 保存数据-5001
	 * @param dao
	 * @param datas
	 * @param t
	 * @param em
	 * @throws Exception
	 */
	private String addContrastPerson(JSONObject jsonObject,VehicleBrake t ,Equipment em) throws Exception{
		BaseDao dao = null;
		try {
			JSONArray datas = jsonObject.getJSONArray("data");
			if(datas != null && datas.size() >0){
				JSONObject data = datas.getJSONObject(0);
				String idcard = data.getString("sfzh");
				String name = data.getString("name");
				
				dao = new BaseDao();
				ContrastPerson type = new ContrastPerson(data.getString("uuid"));//人员预警信息
				type.setIdcard(idcard);
				type.setNames(data.getString("name"));
				type.setAddress(data.getString("identlocation"));
				type.setTag(data.getString("datasource"));
				type.setAction(data.getString("action"));
				type.setBkArea(data.getString("bkfwmc"));
				type.setYjArea(data.getString("yjfwmc"));
				type.setCaptureTime(t.getPassdate());
				type.setInsertTime(new Date());
				type.setSource("本站产生");
				if(t.getLane() != null){
					type.setLocation(t.getLane().getLaneName());
				}else{
					Lane lane = (Lane)dao.getEntity(Lane.class, em.getLaneId());
					if(lane != null)
						type.setLocation(lane.getLaneName());
				}
				type.setBirth(IdCardGenerator.birth(type.getIdcard()));
				type.setGender(IdCardGenerator.gender(type.getIdcard()));
				type.setEquipmentId(em.getId());
				type.setAreaCode(em.getId().substring(0, 6));
				type.setCheckPointId(em.getId().substring(0, 13));
				type.setGatherPhotoURL(t.getLocalCardImg());
				dao.saveOrUpdate(type);
				BaseDataCode.putContrastPersonMap(em.getId().substring(0, 13), type);
				return name+",请接受检查";
			}
			return "";
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return "";
		}finally{
			if(dao != null){ dao.close(); }
		}
	}
	
	/**返回开闸信息*/
	private static String postKAIZHA(String backUrl,String par){
		logger.info("【"+backUrl+"】【"+par+"】");
		return HttpPost.sendPost(backUrl, par);
	}
	
	
	public static void main(String[] args) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		calendar.add(Calendar.SECOND, -300);
		String date = df.format(calendar.getTime());
		System.out.println(CalendarUtil.getCurrentTime());
		System.out.println(date);
		System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date().getTime()-60*1000));
	}
}
