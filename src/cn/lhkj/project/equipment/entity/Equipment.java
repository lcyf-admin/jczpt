package cn.lhkj.project.equipment.entity;

import cn.lhkj.commons.defined.Column;
import cn.lhkj.commons.defined.Entity;

/**设备*/
@Entity(table="CSL_EQUIPMENT",key="ID")
public class Equipment {
	/**设备编号*/
	@Column(value="ID")
	private String id;
	
	/**设备名称*/
	@Column(value="NAMES")
	private String names;
	
	/**设备类型 - 对应数据字典的设备类型*/
	@Column(value="TYPE")
	private String type;
	
	/**车道ID*/
	@Column(value="LANEID")
	private String laneId;
	
	/**安检大厅ID*/
	@Column(value="HALLID")
	private String hallId;
	
	/**设备IP*/
	@Column(value="IP")
	private String ip;
	
	/**设备服务地址*/
	@Column(value="URL")
	private String url;
	
	/**备注*/
	@Column(value="REMARK")
	private String remark;
	
	/**分流文字*/
	@Column(value="SHOWWAY")
	private String showway;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNames() {
		return names;
	}

	public void setNames(String names) {
		this.names = names;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLaneId() {
		return laneId;
	}

	public void setLaneId(String laneId) {
		this.laneId = laneId;
	}

	public String getHallId() {
		return hallId;
	}

	public void setHallId(String hallId) {
		this.hallId = hallId;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getShowway() {
		return showway;
	}

	public void setShowway(String showway) {
		this.showway = showway;
	}
}