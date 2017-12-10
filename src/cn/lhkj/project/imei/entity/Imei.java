package cn.lhkj.project.imei.entity;

import java.util.Date;

import cn.lhkj.commons.defined.Column;
import cn.lhkj.commons.defined.Entity;
import cn.lhkj.commons.util.CalendarUtil;

@Entity(table="CSL_IMEI", key="id", partition="monthId")
public class Imei {
	
	/**主键*/
	@Column(value="id")
	private String id;
	
	@Column(value="MAC")
	private String mac;
	
	@Column(value="IMEI")
	private String imei;
	
	@Column(value="IMSI")
	private String imsi;
	
	@Column(value="PHONE_NUM")
	private String phoneNum;
	
	@Column(value="INSERT_TIME")
	private Date insertTime;
	
	@Column(value="PASS_TIME")
	private Date passTime;
	
	@Column(value="IP")
	private String ip;
	
	@Column(value="MONTH_ID")
	private String monthId;
	
	public Imei(){
		
	}
	
	public Imei(String id){
		this.id = id;
		this.monthId = CalendarUtil.getCurrentMonth();
	}
	///////////////////////////////////////////////////

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getImsi() {
		return imsi;
	}

	public void setImsi(String imsi) {
		this.imsi = imsi;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getMonthId() {
		return monthId;
	}

	public void setMonthId(String monthId) {
		this.monthId = monthId;
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

	public Date getPassTime() {
		return passTime;
	}

	public void setPassTime(Date passTime) {
		this.passTime = passTime;
	}

}
