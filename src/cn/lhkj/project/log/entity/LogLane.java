package cn.lhkj.project.log.entity;

import java.util.Date;

import cn.lhkj.commons.defined.Column;
import cn.lhkj.commons.defined.Entity;
/** 车证合一接口调用记录  */
@Entity(table="CSL_LogLane", key="id")
public class LogLane {
	
	@Column(value="ID")
	private String id;
	
	@Column(value="TYPE")
	private String type;//接受或者发送
	
	@Column(value="IP")
	private String ip;//IP地址
	
	@Column(value="INSERT_TIME")
	private Date insertTime;//入库时间
	
	@Column(value="DATA",clob=true)
	private String data;//返回的数据对象
	
	////////////////////////////////////////////////
	public LogLane(){
		
	}
	
	public LogLane(String id){
		this.id = id;
	}
	////////////////////////////////////////////////
	public String getOpt(){
		return "<button type=\"button\" class=\"btn btn-link btn-xs\"  onclick=\"detail('"+id+"');\">详细</button>";
	}
	////////////////////////////////////////////////
	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}
}
