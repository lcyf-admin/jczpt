package cn.lhkj.project.contrast.service;

import java.util.List;
import java.util.Map;

import cn.lhkj.commons.entity.PageInfo;
import cn.lhkj.project.contrast.entity.ContrastPerson;

public interface ContrastPersonService {
	
	/**
	 * 获取人员预警信息
	 * @param requestParams
	 * @param pageInfo
	 * @return
	 * @throws Exception
	 */
	public PageInfo getContrastPersonPage(Map<String, Object> requestParams,PageInfo pageInfo)throws Exception;
	
	/**
	 * 获取最新的人员预警数据
	 * @param records 获取数据的条数
	 * @return
	 * @throws Exception
	 */
	public List<ContrastPerson> listContrastPerson(String records,String stationId) throws Exception;
	
	/**
	 * 删除人员预警信息
	 * @param id 人员预警信息ID
	 * @throws Exception
	 */
	public void removeContrastPerson(String id) throws Exception;
	
	/**
	 * 保存人员预警信息
	 * @param data
	 * @throws Exception
	 */
	public void saveContrastPerson(ContrastPerson type) throws Exception;
	
	/**
	 * 根据id查询人员预警信息
	 * @param id 人员预警信息ID
	 * @return
	 * @throws Exception
	 */
	public ContrastPerson getContrastPerson(String id) throws Exception;
	
}