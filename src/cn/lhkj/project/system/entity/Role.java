package cn.lhkj.project.system.entity;

import cn.lhkj.commons.defined.Column;
import cn.lhkj.commons.defined.Entity;

@Entity(table="CSL_ROLE",key="id")
public class Role{
	
	/**主键 */
	@Column(value="ID")
	private String id;
	
	/**角色名称 */
	@Column(value="ROLE_NAME")
	private String roleName;
	
	/**是否所有人员享有此角色的菜单权限*/
	@Column(value="IS_BASE")
	private String isBase;
	
	/////////////////////////////////////
	public String getOpt(){
		String btn = "<button type=\"button\" class=\"btn btn-link btn-xs\" onclick=\"editRole('"+id+"');\">编辑</button>"+
				     "<button type=\"button\" class=\"btn btn-link btn-xs\" onclick=\"deleteRole('"+id+"')\";>删除</button>"+
				     "<button type=\"button\" class=\"btn btn-link btn-xs\" onclick=\"menu('"+id+"');\">菜单</button>";
		if("1".equals(isBase)){
			btn +=   "<button type=\"button\" class=\"btn btn-link btn-xs\" style=\"color:gray;\">用户</button>";
		}else{
			btn +=   "<button type=\"button\" class=\"btn btn-link btn-xs\" onclick=\"user('"+id+"');\">用户</button>";
		}
		return btn;
	}
	
	public String getIsBaseView(){
		if("1".equals(isBase)) return "是";
		return "否";
	}
	////////////////////////////////////
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getIsBase() {
		return isBase;
	}

	public void setIsBase(String isBase) {
		this.isBase = isBase;
	}
	
}

	
