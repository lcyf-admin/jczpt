package cn.lhkj.project.vehicle.service;

import java.util.Date;

import cn.lhkj.commons.base.BaseDao;
import cn.lhkj.commons.base.BaseDataCode;
import cn.lhkj.commons.base.BaseDataDict;
import cn.lhkj.commons.scan.thread.VehicleBrakeThread;
import cn.lhkj.commons.scan.thread.VehicleSpecialThread;
import cn.lhkj.commons.util.CalendarUtil;
import cn.lhkj.commons.util.StringUtil;
import cn.lhkj.project.equipment.entity.Equipment;
import cn.lhkj.project.lane.entity.Lane;
import cn.lhkj.project.vehicle.entity.Vehicle;
import cn.lhkj.project.vehicle.entity.VehicleBrake;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**闸机处刷身份证之前摄像头抓拍数据传输*/
public class VehicleGather9001 {
	private BaseDao baseDao;
	private String tranNO;
	
	public VehicleGather9001(BaseDao baseDao,String tranNO){
		this.baseDao = baseDao;
		this.tranNO = tranNO;
	}
	
	public void gather(JSONObject data,String backUrl,String ip) throws Exception{
		String passDate = StringUtil.trim(data.getString("passdate"));//数据采集时间
		String dataNum = StringUtil.trim(data.getString("dataNum"));//guid数据组号（若果车辆、人员分开推送，同一组号为同一组数据，必须为唯一）
		Date passdate = CalendarUtil.toDate(passDate, "yyyy-MM-dd HH:mm:ss");
		JSONArray cardata = data.getJSONArray("cardata");//车辆数据
		JSONObject carObj = cardata.getJSONObject(0);
		
		VehicleBrake t = new VehicleBrake(dataNum);
		String plateColor = StringUtil.trim(carObj.getString("plateColor"));//车牌颜色
		t.setPlateColorId(plateColor);
		if(BaseDataDict.dictItemMap.get("PLATE_COLOR").get(plateColor) != null){
			t.setPlateColor(BaseDataDict.dictItemMap.get("PLATE_COLOR").get(plateColor));
		}else{
			t.setPlateColor(plateColor);
		}
		t.setEquipmentId(StringUtil.trim(data.getString("passageaway")));//通道编号（所有系统统一编号）
		t.setCarNum(StringUtil.trim(carObj.getString("carNum")));
		t.setCarImg(StringUtil.trim(carObj.getString("carImg")));
		if(carObj.containsKey("vehicleType")){
			String vehicleType = StringUtil.trim(carObj.getString("vehicleType"));
			if(BaseDataDict.dictItemMap.get("VEHICLE_TYPE").get(vehicleType) != null){
				vehicleType = BaseDataDict.dictItemMap.get("VEHICLE_TYPE").get(vehicleType);
			}
			t.setVehicleType(vehicleType);
		}
		t.setPassdate(passdate);
		t.setInsertTime(new Date());
		t.setTranType("9001");
		t.setBackUrl(backUrl);
		t.setIp(ip);
		
		Equipment em = BaseDataCode.equipmentMap.get(t.getEquipmentId());
		if(StringUtil.isNull(backUrl)){
			t.setBackUrl(em.getUrl());
		}
		t.setLane((Lane)baseDao.getEntity(Lane.class, em.getLaneId()));
		
		String carImg = StringUtil.trim(t.getCarImg());
		String numImg = StringUtil.trim(t.getNumImg());
		if(carImg.startsWith("http")){
			t.setLocalCarImg(carImg);
		}else if(carImg.contains("cetc")){
			t.setLocalCarImg("http://"+BaseDataCode.config.getFileServerIp()+":8889"+
					carImg.substring(carImg.indexOf("cetc")+4).replace("\\", "/"));
		}
		if(numImg.startsWith("http")){
			t.setLocalNumImg(numImg);
		}else if(numImg.contains("cetc")){
			t.setLocalNumImg("http://"+BaseDataCode.config.getFileServerIp()+":8889"+
					numImg.substring(numImg.indexOf("cetc")+4).replace("\\", "/"));
		}
		saveVehicleBrake(t);
		
		if(StringUtil.trim(em.getRemark()).contains("快检") 
				|| "快检通道".equals(em.getRemark())){
			new Thread(new VehicleSpecialThread(t,tranNO)).start();
		}else{
			new Thread(new VehicleBrakeThread(t,tranNO)).start();
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
		type.setCarImg(t.getLocalCarImg());
		type.setNumImg(t.getLocalNumImg());
		
    	baseDao.saveOrUpdate(type);
    	baseDao.saveOrUpdate(t);
    	
    	type.setLane(t.getLane());
		BaseDataCode.setVehicle(type);
	}
}
