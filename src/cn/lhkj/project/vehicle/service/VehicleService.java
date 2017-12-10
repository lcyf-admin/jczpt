package cn.lhkj.project.vehicle.service;

import java.util.List;
import java.util.Map;

import cn.lhkj.commons.entity.PageInfo;
import cn.lhkj.project.vehicle.entity.Passenger;
import cn.lhkj.project.vehicle.entity.Uvss;
import cn.lhkj.project.vehicle.entity.UvssPath;
import cn.lhkj.project.vehicle.entity.Vehicle;
import cn.lhkj.project.vehicle.entity.VehicleBrake;
import cn.lhkj.project.vehicle.entity.VehicleShunt;

public interface VehicleService {
	
	/**
	 * 获取车辆信息
	 * @param id 车辆ID
	 * @return 车辆实例
	 * @throws Exception
	 */
	public Vehicle getVehicle(String id) throws Exception;
	
	public void savePassenger(Passenger t) throws Exception;
	public void deletePassenger(String id) throws Exception;
	
	/**
	 * 获取车证合一闸机通过的车辆
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public VehicleBrake getVehicleBrake(String id) throws Exception;
	
	/**
	 * 获取前置卡口通过的车辆
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public VehicleShunt getVehicleShunt(String id) throws Exception;
	
	/**富士车证合一和分流-数据采集接口*/
	public String gather(String infos,String ip) throws Exception;
	
	/**
	 * 保存车底扫描结果
	 * @param uvss
	 * @throws Exception
	 */
	public void saveUvss(Uvss uvss) throws Exception;
	
	/**
	 * 保存车底扫描结果
	 * @param uvss
	 * @throws Exception
	 */
	public void saveUvssPath(UvssPath uvss) throws Exception;
	
	/**
	 * 获取实时车辆信息列表
	 * @return
	 * @throws Exception
	 */
	public PageInfo getPriorDataVehiclePage(Map<String, Object> requestParams,
			PageInfo pageInfo) throws Exception;
	
	/**
	 * 获取经过的车辆数据
	 * @return
	 * @throws Exception
	 */
	public List<Vehicle> listVehicle(String records,String stationId) throws Exception;
	
	/**
	 * 获取车辆的乘客信息
	 * @param vehicleId
	 * @return
	 * @throws Exception
	 */
	public List<Passenger> getPassengerList(String vehicleId) throws Exception;
	 
}
