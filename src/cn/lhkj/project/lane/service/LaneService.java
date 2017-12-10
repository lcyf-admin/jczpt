package cn.lhkj.project.lane.service;

import java.util.List;
import java.util.Map;

import cn.lhkj.commons.entity.PageInfo;
import cn.lhkj.project.lane.entity.Lane;

public interface LaneService {
	
	/**
	 * 获取所有车道
	 * @return
	 * @throws Exception
	 */
	public List<Lane> listAll() throws Exception;
	
	/**
	 * 获取车道数据
	 * @param requestParams 查询条件
	 * @param pageInfo 基本参数
	 * @return
	 */
	public PageInfo getLanePage(Map<String, Object> requestParams, PageInfo pageInfo) throws Exception;
	
	/**
	 * 删除车道
	 * @param id 车道ID
	 * @throws Exception
	 */
	public void removeLane(String id) throws Exception;
	
	/**
	 * 保存车道
	 * @param lane
	 * @throws Exception
	 */
	public void saveLane(Lane lane) throws Exception;
	
	/**
	 * 根据id查询车道
	 * @param id 车道ID
	 * @return
	 * @throws Exception
	 */
	public Lane getLaneById(String id) throws Exception;
	
	/**
	 * 更新车道
	 * @param lane 车道实体
	 * @throws Exception
	 */
	public void udpateLane(Lane lane) throws Exception;
}