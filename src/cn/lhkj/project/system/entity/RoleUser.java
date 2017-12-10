package cn.lhkj.project.system.entity;

import cn.lhkj.commons.defined.Column;
import cn.lhkj.commons.defined.Entity;

@Entity(table="CSL_ROLE_USER",key="id")
public class RoleUser{
	
	/**主键 */
	@Column(value="id")
	private String id;
	
	/**角色ID */
	@Column(value="ROLE_ID")
	private String roleId;
	
	/**用户ID */
	@Column(value="USER_ID")
	private String userId;
	
	
	/////////////////////////////////////
	public RoleUser(){
		
	}
	
	public RoleUser(String id) {
		this.id = id;
	}
	////////////////////////////////////
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
}

	
