package cn.lhkj.project.system.service;

import java.util.Map;

import cn.lhkj.commons.entity.PageInfo;
import cn.lhkj.project.system.entity.DictItem;

public interface DictItemService {
	
	/**获取数据字典项实例  */
	public DictItem getDictItem(String id) throws Exception;
	
	/**
	 * 获取数据字典项信息
	 * @param requestParams 查询条件
	 * @param pageInfo Grid基本参数
	 * @return
	 * @throws Exception
	 */
	public PageInfo getDictItemPage(Map<String, Object> requestParams,PageInfo pageInfo)throws Exception;
	
	/**
	 * 添加数据字典项
	 * @param dictItem
	 * @throws Exception
	 */
	public void addDictItem(DictItem dictItem) throws Exception;
	
	/**
	 * 更新数据字典项
	 * @param dictItem
	 * @throws Exception
	 */
	public void updateDictItem(DictItem dictItem) throws Exception;
	
	/**
	 * 删除数据字典项
	 * @param id 字典ID
	 * @throws Exception
	 */
	public void deleteDictItem(String id) throws Exception;
	
	
}
