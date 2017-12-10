package cn.lhkj.project.vehicle.service;

import java.util.Date;

import cn.lhkj.commons.base.BaseDao;
import cn.lhkj.commons.base.BaseDataCode;
import cn.lhkj.commons.base.BaseDataDict;
import cn.lhkj.commons.scan.thread.VehicleShuntThread;
import cn.lhkj.commons.tools.UUIDFactory;
import cn.lhkj.commons.util.StringUtil;
import cn.lhkj.project.equipment.entity.Equipment;
import cn.lhkj.project.lane.entity.Lane;
import cn.lhkj.project.vehicle.entity.Vehicle;
import cn.lhkj.project.vehicle.entity.VehicleShunt;
import net.sf.json.JSONObject;

/**前置卡口传来的数据处理逻辑*/
public class VehicleGather1001 {
	private BaseDao baseDao;
	private String tranNO;
	
	public VehicleGather1001(BaseDao baseDao,String tranNO){
		this.baseDao = baseDao;
		this.tranNO = tranNO;
	}
	
	public void gather(JSONObject jsonObject, String backUrl,String ip) throws Exception{
		JSONObject data = (JSONObject)jsonObject.getJSONObject("data");
		VehicleShunt vehicleShunt = new VehicleShunt(UUIDFactory.getUUIDStr());
		String color = StringUtil.trim(data.getString("color"));
		String plate = StringUtil.trim(data.getString("plate"));
		String plateImage = StringUtil.trim(data.getString("plateImage"));
		String vehicleImage = StringUtil.trim(data.getString("vehicleImage"));
		String vehicleType = StringUtil.trim(data.getString("vehicleType"));
		if(BaseDataDict.dictItemMap.get("VEHICLE_TYPE").get(vehicleType) != null){
			vehicleType = BaseDataDict.dictItemMap.get("VEHICLE_TYPE").get(vehicleType);
		}
		vehicleShunt.setPlateColorId(color);
		if(BaseDataDict.dictItemMap.get("PLATE_COLOR").get(color) != null){
			vehicleShunt.setPlateColor(BaseDataDict.dictItemMap.get("PLATE_COLOR").get(color));
		}else{
			vehicleShunt.setPlateColor(color);
		}
		//如果前值卡口传了车道编号
		String eqipmentId = data.containsKey("passageaway") ? StringUtil.trim(data.getString("passageaway")) : "";
		if(StringUtil.isNull(eqipmentId)){//如果没传
			eqipmentId = BaseDataCode.config.getFrontId();//前置卡口车道约定的ID
		}
		vehicleShunt.setEqipmentId(eqipmentId);
		vehicleShunt.setPassdate(new Date());
		vehicleShunt.setPlate(plate);
		vehicleShunt.setPlateImage(plateImage);
		vehicleShunt.setVehicleImage(vehicleImage);
		vehicleShunt.setVehicleType(vehicleType);
		vehicleShunt.setInsertTime(new Date());
		vehicleShunt.setBackUrl(backUrl);
		vehicleShunt.setIp(ip);
		if(plateImage.startsWith("http")){
			vehicleShunt.setLocalPlateImage(plateImage);
		}else if(plateImage.contains("cetc")){
			vehicleShunt.setLocalPlateImage("http://"+BaseDataCode.config.getFileServerIp()+":8889"+
					plateImage.substring(plateImage.indexOf("cetc")+4).replace("\\", "/"));
		}
		
		if(vehicleImage.startsWith("http")){
			vehicleShunt.setLocalVehicleImage(vehicleImage);
		}else if(vehicleImage.contains("cetc")){
			vehicleShunt.setLocalVehicleImage("http://"+BaseDataCode.config.getFileServerIp()+":8889"+
					vehicleImage.substring(vehicleImage.indexOf("cetc")+4).replace("\\", "/"));
		}
		
		Equipment em = BaseDataCode.equipmentMap.get(eqipmentId);
		if(em != null){
			if(StringUtil.isNull(backUrl)){
				vehicleShunt.setBackUrl(em.getUrl());
			}
			vehicleShunt.setLane((Lane)baseDao.getEntity(Lane.class, em.getLaneId()));
		}
		saveVehicleShunt(vehicleShunt);//保存前置卡口数据
		new Thread(new VehicleShuntThread(vehicleShunt,tranNO)).start();
	}
	
	/**
	 * 保存前置卡口数据
	 * @param t
	 * @throws Exception
	 */
	private void saveVehicleShunt(VehicleShunt t) throws Exception{
		baseDao.save(t);
		Vehicle type = new Vehicle(t.getId());
		type.setMonthId(t.getMonthId());
		type.setCarImg(StringUtil.isNull(t.getLocalVehicleImage()) ? t.getVehicleImage() : t.getLocalVehicleImage());
    	type.setCarNum(t.getPlate());
    	type.setClassify("VehicleShunt");
    	type.setEquipmentId(t.getEqipmentId());
    	type.setInsertTime(t.getInsertTime());
    	type.setNumImg(StringUtil.isNull(t.getLocalPlateImage()) ? t.getPlateImage() : t.getLocalPlateImage());
    	type.setPassdate(t.getPassdate());
    	type.setPlateColor(t.getPlateColor());
    	type.setPlateColorId(t.getPlateColorId());
    	type.setVehicleType(t.getVehicleType());
    	type.setBackUrl(t.getBackUrl());
    	type.setIp(t.getIp());
    	baseDao.save(type);
    	type.setLane(t.getLane());
    	BaseDataCode.setVehicle(type);
	}
}
