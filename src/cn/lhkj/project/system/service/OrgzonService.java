package cn.lhkj.project.system.service;

import java.util.HashSet;
import java.util.List;

import cn.lhkj.commons.entity.ZTreeNode;
import cn.lhkj.project.system.entity.Orgzon;

public interface OrgzonService {
	
	/**获取组织机构实例  */
	public Orgzon getOrgzon(String id) throws Exception;
	
	/**
	 * 通过站点编号获得站点信息
	 * @param codesId
	 * @return
	 * @throws Exception
	 */
	public Orgzon getOrgzonByCodes(String codesId) throws Exception;
	
	/**
	 * 获取组织机构树
	 * @return
	 * @throws Exception
	 */
	public List<ZTreeNode> getOrgzonZTree() throws Exception;
	
	/**
	 * 添加、编辑部门
	 * @param dept
	 * @param flag sibling增加同级；children增加下级；edit编辑
	 * @throws Exception
	 */
	public void saveOrUpdate(Orgzon orgzon,String flag) throws Exception;
	
	/**
	 * 删除组织机构
	 * @param id
	 * @throws Exception
	 */
	public void removeOrgzon(String id) throws Exception;
	
	/**
	 * 获取所有的检查站
	 * @return
	 * @throws Exception
	 */
	public HashSet<String> getStations() throws Exception; 
	
}
