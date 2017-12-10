package cn.lhkj.project.vehicle.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.lhkj.commons.base.BaseDao;
import cn.lhkj.commons.base.BaseDataCode;
import cn.lhkj.commons.base.BaseDataDict;
import cn.lhkj.commons.scan.thread.VehicleBrakeThread;
import cn.lhkj.commons.tools.UUIDFactory;
import cn.lhkj.commons.util.CalendarUtil;
import cn.lhkj.commons.util.StringUtil;
import cn.lhkj.project.equipment.entity.Equipment;
import cn.lhkj.project.lane.entity.Lane;
import cn.lhkj.project.vehicle.entity.Passenger;
import cn.lhkj.project.vehicle.entity.Vehicle;
import cn.lhkj.project.vehicle.entity.VehicleBrake;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**非白名单司机刷证--起一个比对司机的线程*/
public class VehicleGather5001 {
	private BaseDao baseDao;
	private String tranNO;
	
	public VehicleGather5001(BaseDao baseDao,String tranNO){
		this.baseDao = baseDao;
		this.tranNO = tranNO;
	}
	
	public void gather(JSONObject data,String backUrl,String ip) throws Exception{
		JSONArray cardata = data.getJSONArray("cardata");//车辆数据
		if(cardata == null || cardata.size() == 0){//非机动车道刷证
			JSONArray driverdata = data.getJSONArray("driverdata");//司机数据
			if(driverdata == null || driverdata.size() == 0) return;//无司机数据
			String passageaway = StringUtil.trim(data.getString("passageaway"));//通道编号（所有系统统一编号）
			String passDate = StringUtil.trim(data.getString("passdate"));//数据采集时间
			Date passdate = CalendarUtil.toDate(passDate, "yyyy-MM-dd HH:mm:ss");
			String dataNum = UUIDFactory.getUUIDStr();
			VehicleBrake vehicleBrake = new VehicleBrake(dataNum);//经过人证合一车辆信息
			vehicleBrake.setPlateColorId("4");
			vehicleBrake.setPlateColor("未知");
			vehicleBrake.setEquipmentId(passageaway);
			vehicleBrake.setCarNum("无车牌");
			vehicleBrake.setPassdate(passdate);
			vehicleBrake.setInsertTime(new Date());
			vehicleBrake.setTranType("5001");
			vehicleBrake.setBackUrl(backUrl);
			vehicleBrake.setIp(ip);
			JSONObject driverObj = driverdata.getJSONObject(0);
			vehicleBrake.setUserName(StringUtil.trim(driverObj.getString("userName")));
			vehicleBrake.setSex(StringUtil.trim(driverObj.getString("sex")));
			vehicleBrake.setMinzu(StringUtil.trim(driverObj.getString("minzu")));
			vehicleBrake.setCardNum(StringUtil.trim(driverObj.getString("cardNum")));
			vehicleBrake.setBirthDate(StringUtil.trim(driverObj.getString("birthDate")));
			vehicleBrake.setAddress(StringUtil.trim(driverObj.getString("address")));
			vehicleBrake.setQianfa(StringUtil.trim(driverObj.getString("qianfa")));
			vehicleBrake.setYouxiaoqi(StringUtil.trim(driverObj.getString("youxiaoqi")));
			vehicleBrake.setCardImg(StringUtil.trim(driverObj.getString("cardImg")));
			addVehicleBrake(vehicleBrake,false);
		}else{
			tranType_5001_motorwar(data,backUrl,ip);//机动车道刷证
		}
	}
	
	/**机动车道传5001*/
	private void tranType_5001_motorwar(JSONObject data,String backUrl,String ip) throws Exception{
		String passageaway = StringUtil.trim(data.getString("passageaway"));//通道编号（所有系统统一编号）
		String dataNum = StringUtil.trim(data.getString("dataNum"));//guid数据组号（若果车辆、人员分开推送，同一组号为同一组数据，必须为唯一）
		String passDate = StringUtil.trim(data.getString("passdate"));//数据采集时间
		Date passdate = CalendarUtil.toDate(passDate, "yyyy-MM-dd HH:mm:ss");
		
		JSONArray cardata = data.getJSONArray("cardata");//车辆数据
		JSONObject carObj = cardata.getJSONObject(0);
		VehicleBrake vehicleBrake = new VehicleBrake(dataNum);//经过人证合一车辆信息
		vehicleBrake.setEquipmentId(passageaway);
		vehicleBrake.setCarNum(StringUtil.trim(carObj.getString("carNum")));
		vehicleBrake.setCarImg(StringUtil.trim(carObj.getString("carImg")));
		vehicleBrake.setPassdate(passdate);
		vehicleBrake.setInsertTime(new Date());
		vehicleBrake.setTranType("5001");
		vehicleBrake.setBackUrl(backUrl);
		vehicleBrake.setIp(ip);
		if(carObj.containsKey("vehicleType")){
			String vehicleType = StringUtil.trim(carObj.getString("vehicleType"));
			if(BaseDataDict.dictItemMap.get("VEHICLE_TYPE").get(vehicleType) != null){
				vehicleType = BaseDataDict.dictItemMap.get("VEHICLE_TYPE").get(vehicleType);
			}
			vehicleBrake.setVehicleType(vehicleType);
		}
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MILLISECOND, calendar.get(Calendar.MILLISECOND)-60*5000);
		String sql = "select ID as ID from CSL_VEHICLE_BRAKE where 1=1 " +
				" and MONTH_ID="+vehicleBrake.getMonthId()+"" +
				" and CAR_NUM='"+vehicleBrake.getCarNum()+"' "+
				" and IP='"+vehicleBrake.getIp()+"' "+
				" and EQUIPMENT_ID='"+vehicleBrake.getEquipmentId()+"' "+
				" and INSERT_TIME between to_date('"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime())+"','yyyy-mm-dd hh24:mi:ss') and sysdate "+
				" order by INSERT_TIME desc";
		List<Map<String,String>> templist = baseDao.findBySQL(sql);
		if(!StringUtil.isNull(templist)){
			vehicleBrake.setId(templist.get(0).get("ID"));
		}
		
		String plateColor = StringUtil.trim(carObj.getString("plateColor"));//车牌颜色
		vehicleBrake.setPlateColorId(plateColor);
		if(BaseDataDict.dictItemMap.get("PLATE_COLOR").get(plateColor) != null){
			plateColor = BaseDataDict.dictItemMap.get("PLATE_COLOR").get(plateColor);
		}
		if(StringUtil.isNull(plateColor)){//防止大有鹏翔不传颜色字段
			VehicleBrake t = (VehicleBrake)baseDao.getEntity(VehicleBrake.class, dataNum);
			if(t !=null) plateColor = t.getPlateColor();
		}
		vehicleBrake.setPlateColor(plateColor);
			
		JSONArray driverdata = data.getJSONArray("driverdata");//司机数据
		JSONObject driverObj = driverdata.getJSONObject(0);
		vehicleBrake.setUserName(StringUtil.trim(driverObj.getString("userName")));
		vehicleBrake.setSex(StringUtil.trim(driverObj.getString("sex")));
		vehicleBrake.setMinzu(StringUtil.trim(driverObj.getString("minzu")));
		vehicleBrake.setCardNum(StringUtil.trim(driverObj.getString("cardNum")));
		vehicleBrake.setBirthDate(StringUtil.trim(driverObj.getString("birthDate")));
		vehicleBrake.setAddress(StringUtil.trim(driverObj.getString("address")));
		vehicleBrake.setQianfa(StringUtil.trim(driverObj.getString("qianfa")));
		vehicleBrake.setYouxiaoqi(StringUtil.trim(driverObj.getString("youxiaoqi")));
		vehicleBrake.setCardImg(StringUtil.trim(driverObj.getString("cardImg")));
		addVehicleBrake(vehicleBrake, true);
			
		JSONArray passengerdata  = data.getJSONArray("passengerdata");//乘客数据
		if(passengerdata != null && passengerdata.size() > 0){
			for(int i=0;i<passengerdata.size();i++){
				JSONObject assangeObj = passengerdata.getJSONObject(i);
				String idCard = StringUtil.trim(assangeObj.getString("cardNum"));
				if(StringUtil.isNull(idCard)) continue;
				Passenger passenger = new Passenger(UUIDFactory.getUUIDStr());
				passenger.setIsDriver("0");
				passenger.setVehicleId(vehicleBrake.getId());
				passenger.setUserName(StringUtil.trim(assangeObj.getString("userName")));
				passenger.setSex(StringUtil.trim(assangeObj.getString("sex")));
				passenger.setMinzu(StringUtil.trim(assangeObj.getString("minzu")));
				passenger.setCardNum(StringUtil.trim(assangeObj.getString("cardNum")));
				passenger.setBirthDate(StringUtil.trim(assangeObj.getString("birthDate")));
				passenger.setAddress(StringUtil.trim(assangeObj.getString("address")));
				passenger.setQianfa(StringUtil.trim(assangeObj.getString("qianfa")));
				passenger.setYouxiaoqi(StringUtil.trim(assangeObj.getString("youxiaoqi")));
				passenger.setPassdate(passdate);
				passenger.setInsertTime(new Date());
				
				String cardImg = StringUtil.trim(assangeObj.getString("cardImg"));
				if(cardImg.startsWith("http")){
					passenger.setCardImg(cardImg);
				}else if(cardImg.contains("cetc")){
					passenger.setCardImg("http://"+BaseDataCode.config.getFileServerIp()+":8889"+
							cardImg.substring(cardImg.indexOf("cetc")+4).replace("\\", "/"));
				}
				
				baseDao.execute("delete from CSL_PASSENGER where " +
						"MONTH_ID='"+CalendarUtil.getCurrentMonth()+"' " +
						"and VEHICLE_ID='"+dataNum+"' and CARD_NUM='"+passenger.getCardNum()+"'");
				baseDao.save(passenger);
			}
		}
	}
	
	/**
	 * 添加闸机采集数据
	 * @param vehicleBrake
	 * @param startThread 是否开启比对线程
	 */
	private void addVehicleBrake(VehicleBrake t,boolean startThread)throws Exception{
		String carImg = StringUtil.trim(t.getCarImg());
		String cardImg = StringUtil.trim(t.getCardImg());
		String numImg = StringUtil.trim(t.getNumImg());
		if(carImg.startsWith("http")){
			t.setLocalCarImg(carImg);
		}else if(carImg.contains("cetc")){
			t.setLocalCarImg("http://"+BaseDataCode.config.getFileServerIp()+":8889"+
					carImg.substring(carImg.indexOf("cetc")+4).replace("\\", "/"));
		}
		
		if(cardImg.startsWith("http")){
			t.setLocalCardImg(cardImg);
		}else if(cardImg.contains("cetc")){
			t.setLocalCardImg("http://"+BaseDataCode.config.getFileServerIp()+":8889"+
					cardImg.substring(cardImg.indexOf("cetc")+4).replace("\\", "/"));
		}
		
		if(numImg.startsWith("http")){
			t.setLocalNumImg(numImg);
		}else if(numImg.contains("cetc")){
			t.setLocalNumImg("http://"+BaseDataCode.config.getFileServerIp()+":8889"+
					numImg.substring(numImg.indexOf("cetc")+4).replace("\\", "/"));
		}
		
		Equipment em = BaseDataCode.equipmentMap.get(t.getEquipmentId());
		if(StringUtil.isNull(t.getBackUrl())){
			t.setBackUrl(em.getUrl());
		}
		if(startThread){//开启比对线程
			new Thread(new VehicleBrakeThread(t,tranNO)).start();
		}
		t.setLane((Lane)baseDao.getEntity(Lane.class, em.getLaneId()));
		saveVehicleBrake(t);
	}
	
	/**
	 * 保存摄像头拍摄的车辆数据
	 * @param t
	 * @throws Exception
	 */
	private void saveVehicleBrake(VehicleBrake t) throws Exception{
		Vehicle type = new Vehicle(t.getId());
    	type.setCarImg(t.getCarImg());
    	type.setCarNum(t.getCarNum());
    	type.setClassify("VehicleBrake");
    	type.setEquipmentId(t.getEquipmentId());
    	type.setInsertTime(t.getInsertTime());
    	type.setNumImg(t.getNumImg());
    	type.setPassdate(t.getPassdate());
    	type.setPlateColor(t.getPlateColor());
    	type.setPlateColorId(t.getPlateColorId());
    	type.setVehicleType(t.getVehicleType());
    	type.setBackUrl(t.getBackUrl());
    	type.setIp(t.getIp());
    	type.setUserName(t.getUserName());
		type.setSex(t.getSex());
		type.setMinzu(t.getMinzu());
		type.setCardNum(t.getCardNum());
		type.setBirthDate(t.getBirthDate());
		type.setAddress(t.getAddress());
		type.setQianfa(t.getQianfa());
		type.setYouxiaoqi(t.getYouxiaoqi());
		type.setCarImg(t.getLocalCarImg());
		type.setCardImg(t.getLocalCardImg());
		type.setNumImg(t.getLocalNumImg());
		
    	baseDao.saveOrUpdate(type);
    	baseDao.saveOrUpdate(t);
    	
    	type.setLane(t.getLane());
    	BaseDataCode.setVehicle(type);
	}
	
}
