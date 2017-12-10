package cn.lhkj.project.equipment.service;

import java.util.List;
import java.util.Map;

import cn.lhkj.commons.entity.PageInfo;
import cn.lhkj.project.equipment.entity.Equipment;

public interface EquipmentService {

	/**
	 * 获取车道设备数据
	 * @param requestParams 查询条件
	 * @param pageInfo Grid 基本参数
	 * @return
	 * @throws Exception
	 */
	public PageInfo getLaneEquipmentPage(Map<String, Object> requestParams, PageInfo pageInfo) throws Exception;
	
	/**
	 * 获取安检厅设备数据
	 * @param requestParams 查询条件
	 * @param pageInfo Grid 基本参数
	 * @return
	 * @throws Exception
	 */
	public PageInfo getHallEquipmentPage(Map<String, Object> requestParams, PageInfo pageInfo) throws Exception;
	
	
	/**
	 * 删除设备
	 * @param id 设备id
	 * @throws Exception
	 */
	public void removeEquipment(String id) throws Exception;
	
	/**
	 * 增加设备
	 * @param equipment 设备实体
	 * @throws Exception
	 */
	public void saveEquipment(Equipment equipment) throws Exception;
	
	/**
	 * 根据id查询设备
	 * @param id 设备id
	 * @return
	 * @throws Exception 
	 */
	public Equipment getEquipmentById(String id) throws Exception;
	
	/**
	 * 更新设备
	 * @param equipment 设备实体
	 * @throws Exception 
	 */
	public void updateEquipment(Equipment equipment) throws Exception;
	
	/**
	 * 获取所有设备
	 * @return
	 * @throws Exception
	 */
	public List<Equipment> listAll() throws Exception;
	
}