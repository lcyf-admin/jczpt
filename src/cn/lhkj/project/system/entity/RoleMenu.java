package cn.lhkj.project.system.entity;

import cn.lhkj.commons.defined.Column;
import cn.lhkj.commons.defined.Entity;

@Entity(table="CSL_ROLE_MENU",key="id")
public class RoleMenu{
	
	/**主键 */
	@Column(value="id")
	private String id;
	
	/**角色ID */
	@Column(value="ROLE_ID")
	private String roleId;
	
	/**菜单ID */
	@Column(value="MENU_ID")
	private String menuId;
	
	/**菜单权限 1个人，2部门，3所有，4其他*/
	@Column(value="DATA_AUTH")
	private String dataAuth;
	
	/////////////////////////////////////
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getDataAuth() {
		return dataAuth;
	}

	public void setDataAuth(String dataAuth) {
		this.dataAuth = dataAuth;
	}
	
}

	
