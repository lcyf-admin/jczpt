package cn.lhkj.project.check.entity;

import cn.lhkj.commons.defined.Column;
import cn.lhkj.commons.defined.Entity;

/**比中车辆返回-乘客和司机信息数据 */
@Entity(table="CHECK_VEHICLE_PASSENGER",key="id")
public class CheckVehiclePassenger {
	
	/**主键*/
	@Column(value="id")
	private String id;
	
	/**车辆ID*/
	@Column(value="CHECK_VEHICLE_ID")
	private String checkVehicleId;
	
	/**姓名*/
	@Column(value="NAME")
	private String name;
	
	/**身份证号*/
	@Column(value="IDCARD")
	private String idcard;
	
	/**手机号*/
	@Column(value="PHONENUM")
	private String phonenum;
	
	/**核验是否一致（1:核验一致；0:核验不一致)*/
	@Column(value="MATCH")
	private String match;
	
	/**手机是否可疑（1:可疑；0:无可疑)*/
	@Column(value="IS_DUBIOUS")
	private String isDubious;
	
	/**可疑描述(VPN;小众;可疑URL等)*/
	@Column(value="FINDS")
	private String finds;
	
	/**1代表司机，0代表乘客*/
	@Column(value="IS_DRIVER")
	private String isDriver;
	
	//////////////////////////////////////
	public CheckVehiclePassenger(){
		
	}
	
	public CheckVehiclePassenger(String id){
		this.id = id;
	}
	//////////////////////////////////////
	public String getMatchView(){
		if("1".equals(match)) return "核验一致";
		if("0".equals(match)) return "核验不一致";
		return match;
	}
	
	public String getIsDubiousView(){
		if("1".equals(isDubious)) return "有可疑";
		if("0".equals(isDubious)) return "无可疑";
		return isDubious;
	}
	//////////////////////////////////////
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCheckVehicleId() {
		return checkVehicleId;
	}

	public void setCheckVehicleId(String checkVehicleId) {
		this.checkVehicleId = checkVehicleId;
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

	public String getMatch() {
		return match;
	}

	public void setMatch(String match) {
		this.match = match;
	}

	public String getIsDubious() {
		return isDubious;
	}

	public void setIsDubious(String isDubious) {
		this.isDubious = isDubious;
	}

	public String getFinds() {
		return finds;
	}

	public void setFinds(String finds) {
		this.finds = finds;
	}

	public String getIsDriver() {
		return isDriver;
	}

	public void setIsDriver(String isDriver) {
		this.isDriver = isDriver;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	

}