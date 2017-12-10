package cn.lhkj.project.hall.service;

import java.util.List;
import java.util.Map;

import cn.lhkj.commons.entity.PageInfo;
import cn.lhkj.project.hall.entity.Hall;

public interface HallService {
	/**
	 * 获取所有安检厅
	 * @return
	 * @throws Exception
	 */
	public List<Hall> listAll() throws Exception;
	
	/**
	 * 获取安检厅数据
	 * @param requestParams 查询条件
	 * @param pageInfo 基本参数
	 * @return
	 */
	public PageInfo getHallPage(Map<String, Object> requestParams, PageInfo pageInfo) throws Exception;
	
	/**
	 * 删除安检厅
	 * @param id 安检厅ID
	 * @throws Exception
	 */
	public void removeHall(String id) throws Exception;
	
	/**
	 * 保存安检厅
	 * @param hall
	 * @throws Exception
	 */
	public void saveHall(Hall hall) throws Exception;
	
	/**
	 * 根据id查询安检厅
	 * @param id 安检厅ID
	 * @return
	 * @throws Exception
	 */
	public Hall getHallById(String id) throws Exception;
	
	/**
	 * 更新安检厅
	 * @param hall 安检厅实体
	 * @throws Exception
	 */
	public void udpateHall(Hall hall) throws Exception;
}