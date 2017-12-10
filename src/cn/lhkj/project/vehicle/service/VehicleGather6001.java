package cn.lhkj.project.vehicle.service;

import java.util.Date;

import cn.lhkj.commons.base.BaseDao;
import cn.lhkj.commons.base.BaseDataCode;
import cn.lhkj.commons.base.BaseDataDict;
import cn.lhkj.commons.tools.UUIDFactory;
import cn.lhkj.commons.util.CalendarUtil;
import cn.lhkj.commons.util.StringUtil;
import cn.lhkj.project.vehicle.entity.Passenger;
import cn.lhkj.project.vehicle.entity.Vehicle;
import cn.lhkj.project.vehicle.entity.VehicleBrake;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**断网后续传 司机和乘客的数据*/
public class VehicleGather6001 {
	private BaseDao baseDao;
	
	public VehicleGather6001(BaseDao baseDao){
		this.baseDao = baseDao;
	}
	
	public void gather(JSONObject data,String ip) throws Exception{
		String passageaway = StringUtil.trim(data.getString("passageaway"));//通道编号（所有系统统一编号）
		String dataNum = StringUtil.trim(data.getString("dataNum"));//guid数据组号（若果车辆、人员分开推送，同一组号为同一组数据，必须为唯一）
		String passDate = StringUtil.trim(data.getString("passdate"));//数据采集时间
		Date passdate = CalendarUtil.toDate(passDate, "yyyy-MM-dd HH:mm:ss");
		
		JSONArray cardata = data.getJSONArray("cardata");//车辆数据
		JSONObject carObj = cardata.getJSONObject(0);
		
		VehicleBrake vehicleBrake = new VehicleBrake(dataNum);
		
		String plateColor = StringUtil.trim(carObj.getString("plateColor"));//车牌颜色
		vehicleBrake.setPlateColorId(plateColor);
		if(BaseDataDict.dictItemMap.get("PLATE_COLOR").get(plateColor) != null){
			vehicleBrake.setPlateColor(BaseDataDict.dictItemMap.get("PLATE_COLOR").get(plateColor));
		}else{
			vehicleBrake.setPlateColor(plateColor);
		}
		vehicleBrake.setEquipmentId(passageaway);
		vehicleBrake.setCarNum(StringUtil.trim(carObj.getString("carNum")));
		vehicleBrake.setCarImg(StringUtil.trim(carObj.getString("carImg")));
		vehicleBrake.setPassdate(passdate);
		vehicleBrake.setInsertTime(new Date());
		vehicleBrake.setTranType("6001");
		vehicleBrake.setIp(ip);
		JSONArray driverdata = data.getJSONArray("driverdata");//司机数据
		if(driverdata != null && driverdata.size() != 0){
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
		}
		saveVehicleBrake(vehicleBrake);
		
		JSONArray passengerdata  = data.getJSONArray("passengerdata");//乘客数据
		if(passengerdata == null || passengerdata.size() == 0) return;
		for(int i=0;i<passengerdata.size();i++){
			JSONObject assangeObj = passengerdata.getJSONObject(i);
			String idCard = StringUtil.trim(assangeObj.getString("cardNum"));
			if(StringUtil.isNull(idCard)) continue;
			Passenger passenger = new Passenger(UUIDFactory.getUUIDStr());
			passenger.setIsDriver("0");
			passenger.setVehicleId(dataNum);
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
			
			baseDao.save(passenger);
		}
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
		type.setCarImg(t.getLocalCarImg());
		type.setCardImg(t.getLocalCardImg());
		type.setNumImg(t.getLocalNumImg());
		
		baseDao.saveOrUpdate(t);
    	baseDao.saveOrUpdate(type);
	}
}
