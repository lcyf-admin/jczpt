package cn.lhkj.project.system.service;

import java.util.List;

import cn.lhkj.commons.entity.SessionBean;
import cn.lhkj.commons.entity.ZTreeNode;
import cn.lhkj.project.system.entity.Menu;


public interface MenuService {
	
	/**获取系统的菜单*/
	public List<Menu> getMenus(SessionBean sessionBean) throws Exception;
	
	/**
	 * 获取角色下的菜单树-带复选框
	 * @param roleId
	 * @return
	 * @throws Exception
	 */
	public List<ZTreeNode> getMenuZtree(String roleId) throws Exception;
}
