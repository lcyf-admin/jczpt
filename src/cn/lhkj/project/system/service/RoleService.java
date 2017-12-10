package cn.lhkj.project.system.service;

import java.util.Map;

import cn.lhkj.commons.entity.PageInfo;
import cn.lhkj.project.system.entity.Role;

public interface RoleService {
	
	/**获取角色实例  */
	public Role getRole(String id) throws Exception;
	
	/**
	 * 获取角色信息
	 * @param requestParams 查询条件
	 * @param pageInfo Grid基本参数
	 * @return
	 * @throws Exception
	 */
	public PageInfo getRolePage(Map<String, Object> requestParams,PageInfo pageInfo)throws Exception;
	
	/**
	 * 添加角色
	 * @param role
	 * @throws Exception
	 */
	public void addRole(Role role) throws Exception;
	
	/**
	 * 更新角色
	 * @param role
	 * @throws Exception
	 */
	public void updateRole(Role role) throws Exception;
	
	/**
	 * 删除角色
	 * @param id
	 * @throws Exception
	 */
	public void deleteRole(String id) throws Exception;
	
	/**
	 * 删除角色中的用户
	 * @param id
	 * @throws Exception
	 */
	public void deleteRoleUser(String id) throws Exception;
	
	/**
	 * 添加色色用户
	 * @param userId 用户ID
	 * @param roleId 角色ID
	 * @throws Exception
	 */
	public void addRoleUser(String userId,String roleId) throws Exception;
	
	/**
	 * 更新角色菜单
	 * @param 
	 */
	public void updateRoleMenu(String roleId,String menuIds) throws Exception;
	
	/**
	 * 获取角色中用户信息
	 * @param requestParams
	 * @param pageInfo
	 * @param sessionBean
	 * @return
	 * @throws Exception
	 */
	public PageInfo getRoleUserPage(Map<String, Object> requestParams,PageInfo pageInfo)throws Exception;
	
	/**
	 * 获取待角色添加的用户信息
	 * @param requestParams
	 * @param pageInfo
	 * @param sessionBean
	 * @return
	 * @throws Exception
	 */
	public PageInfo getUserForSelectPage(Map<String, Object> requestParams,PageInfo pageInfo)throws Exception;
	
	
	
}
