package cn.lhkj.project.check.service;

import java.util.List;
import java.util.Map;

import cn.lhkj.project.check.entity.CheckVehicle;
import cn.lhkj.project.check.entity.CheckVehiclePassenger;
public interface CheckVehicleService {
	
	/**
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public CheckVehicle getCheckVehicle(String id) throws Exception;
	
	
	/**
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public CheckVehicle getCheckVehicleByTaskId(String id) throws Exception;
	
	
	/**
	 * 获取核录司机信息
	 * @param checkVehicleId
	 * @return
	 * @throws Exception
	 */
	public CheckVehiclePassenger getDriver(String checkVehicleId) throws Exception;
	
	/**
	 * 获取核录乘客列表
	 * @param checkVehicleId
	 * @return
	 * @throws Exception
	 */
	public List<CheckVehiclePassenger> getPassengerList(String checkVehicleId) throws Exception;
	
	
	/**
	 * 添加
	 * @param user
	 * @throws Exception
	 */
	public void addCheckVehicle(CheckVehicle checkVehicle,CheckVehiclePassenger driver,Map<String, Object> requestParams) throws Exception;
	
}
