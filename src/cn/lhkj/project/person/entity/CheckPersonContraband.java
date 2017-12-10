package cn.lhkj.project.person.entity;

import java.util.Date;

import cn.lhkj.commons.defined.Column;
import cn.lhkj.commons.defined.Entity;
import cn.lhkj.commons.util.CalendarUtil;

/**违禁品信息数据 */
@Entity(table="CHECK_PERSON_CONTRABAND",key="id")
public class CheckPersonContraband {
	
	/**主键*/
	@Column(value="ID")
	private String id;
	
	/**人员ID*/
	@Column(value="PERSON_ID")
	private String personId;
	
	/**采集时间*/
	@Column(value="CHECK_TIME")
	private Date checkTime;
	
	/**违禁品名*/
	@Column(value="NAME")
	private String name;
	
	/**备注*/
	@Column(value="REMARK")
	private String remark;

	/**图片*/
	@Column(value="CONTRABAND_PHOTO_URL")
	private String contrabandPhotoUrl;
	//////////////////////////////////////	
	public String getCheckTimeView(){
		return CalendarUtil.format(this.checkTime,"yy-MM-dd HH:mm:ss");
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public Date getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getContrabandPhotoUrl() {
		return contrabandPhotoUrl;
	}

	public void setContrabandPhotoUrl(String contrabandPhotoUrl) {
		this.contrabandPhotoUrl = contrabandPhotoUrl;
	}
	


}