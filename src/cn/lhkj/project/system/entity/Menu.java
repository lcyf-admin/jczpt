package cn.lhkj.project.system.entity;

import cn.lhkj.commons.defined.Column;
import cn.lhkj.commons.defined.Entity;

@Entity(table="CSL_MENU",key="id")
public class Menu{
	
	@Column(value="ID")
	private String id;
	
	/**菜单名称*/
	@Column(value="MENU_NAME")
	private String menuName;
	
	/**排序*/
	@Column(value="RANKING")
	private Integer ranking;
	
	@Column(value="HREF")
	private String href;
	
	/**菜单图标*/
	@Column(value="ICON")
	private String icon;
	
	/**菜单权限 1个人，2部门，3所有，4其他*/
	private String dataAuth = "1";
	/////////////////////////////////////
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Integer getRanking() {
		return ranking;
	}

	public void setRanking(Integer ranking) {
		this.ranking = ranking;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getDataAuth() {
		return dataAuth;
	}

	public void setDataAuth(String dataAuth) {
		this.dataAuth = dataAuth;
	}

}
