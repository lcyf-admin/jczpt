package cn.lhkj.project.check.service;

import java.util.List;
import java.util.Map;

import cn.lhkj.project.check.entity.CheckPerson;
import cn.lhkj.project.check.entity.CheckPersonPeers;
import cn.lhkj.project.check.entity.CheckPersonPeersVehicle;
public interface CheckPersonService {
	
	/**
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public CheckPerson getCheckPerson(String id) throws Exception;
	
	
	/**
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public CheckPerson getCheckPersonByTaskId(String id) throws Exception;
	
	
	/**
	 * 添加
	 * @param user
	 * @throws Exception
	 */
	public void addCheckPerson(CheckPerson checkPerson,Map<String, Object> requestParams) throws Exception;
	
	/**
	 * 获取反馈核录的同行人
	 * @param checkPersonId
	 * @return
	 * @throws Exception
	 */
	public List<CheckPersonPeers> getPeersList(String checkPersonId)  throws Exception;

	/**
	 * 获取反馈核录的同行车
	 * @param checkPersonId
	 * @return
	 * @throws Exception
	 */
	public List<CheckPersonPeersVehicle> getPeersVehicleList(String checkPersonId)  throws Exception;
}
