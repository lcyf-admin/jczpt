package cn.lhkj.project.check.entity;

import cn.lhkj.commons.defined.Column;
import cn.lhkj.commons.defined.Entity;

/**比中人员反馈信息陪同人员数据 */
@Entity(table="CHECK_PERSON_PEERS",key="id")
public class CheckPersonPeers {
	
	/**主键*/
	@Column(value="id")
	private String id;
	
	/**身份证号*/
	@Column(value="IDCARD")
	private String idcard;

	/**手机号*/
	@Column(value="PHONENUM")
	private String phonenum;
	
	/**外键--预警人员ID*/
	@Column(value="CHECK_PERSON_ID")
	private String checkPersonId;
	
	
	//////////////////////////////////////
	public CheckPersonPeers(){
		
	}
	
	public CheckPersonPeers(String id){
		this.id = id;
	}
	//////////////////////////////////////
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getPhonenum() {
		return phonenum;
	}

	public void setPhonenum(String phonenum) {
		this.phonenum = phonenum;
	}

	public String getCheckPersonId() {
		return checkPersonId;
	}

	public void setCheckPersonId(String checkPersonId) {
		this.checkPersonId = checkPersonId;
	}

	

}