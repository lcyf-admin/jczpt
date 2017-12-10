package cn.lhkj.project.system.service;

import java.util.Map;

import cn.lhkj.commons.entity.PageInfo;
import cn.lhkj.project.system.entity.Dict;

public interface DictService {
	
	/**获取数据字典实例  */
	public Dict getDict(String id) throws Exception;
	
	/**
	 * 获取数据字典信息
	 * @param requestParams 查询条件
	 * @param pageInfo Grid基本参数
	 * @return
	 * @throws Exception
	 */
	public PageInfo getDictPage(Map<String, Object> requestParams,PageInfo pageInfo)throws Exception;
	
	/**
	 * 添加数据字典
	 * @param dict
	 * @throws Exception
	 */
	public void addDict(Dict dict) throws Exception;
	
	/**
	 * 更新数据字典
	 * @param dict
	 * @throws Exception
	 */
	public void updateDict(Dict dict) throws Exception;
	
	/**
	 * 删除数据字典
	 * @param id 字典ID
	 * @throws Exception
	 */
	public void deleteDict(String id) throws Exception;
	
	
}
