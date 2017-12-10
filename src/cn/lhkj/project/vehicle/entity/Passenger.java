package cn.lhkj.project.vehicle.entity;

import java.util.Date;

import cn.lhkj.commons.defined.Column;
import cn.lhkj.commons.defined.Entity;
import cn.lhkj.commons.util.CalendarUtil;

/**车辆上乘客信息 */
@Entity(table="CSL_PASSENGER", key="id", partition="monthId")
public class Passenger {
	/**主键*/
	@Column(value="id")
	private String id;
	
	/**车辆ID*/
	@Column(value="VEHICLE_ID")
	private String vehicleId;
	
	/**乘客姓名*/
	@Column(value="USER_NAME")
	private String userName;
	
	/**性别*/
	@Column(value="SEX")
	private String sex;
	
	/**族别*/
	@Column(value="MINZU")
	private String minzu;
	
	/**身份证号*/
	@Column(value="CARD_NUM")
	private String cardNum;
	
	/**出生年月*/
	@Column(value="BIRTH_DATE")
	private String birthDate;
	
	/**地址*/
	@Column(value="ADDRESS")
	private String address;
	
	/**签发机关*/
	@Column(value="QIANFA")
	private String qianfa;
	
	/**有效期*/
	@Column(value="YOUXIAOQI")
	private String youxiaoqi;
	
	/**身份证照片*/
	@Column(value="CARD_IMG")
	private String cardImg;
	
	/**安全级别*/
	@Column(value="SLEVEL")
	private String slevel;
	
	/**1代表司机，0代表乘客*/
	@Column(value="IS_DRIVER")
	private String isDriver;
	
	/**比对结果：1成功，0失败*/
	@Column(value="RESULT")
	private String result;
	
	/**数据采集时间*/
	@Column(value="PASSDATE")
	private Date passdate;
	
	/**数据入库时间*/
	@Column(value="INSERT_TIME")
	private Date insertTime;
	
	/**数据按月分块*/
	@Column(value="MONTH_ID")
	private String monthId;
	
	//////////////////////////////////////
	public Passenger(){
	}
	
	public Passenger(String id){
		this.id = id;
		this.monthId = CalendarUtil.getCurrentMonth();
	}
	//////////////////////////////////////
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(String vehicleId) {
		this.vehicleId = vehicleId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getMinzu() {
		return minzu;
	}

	public void setMinzu(String minzu) {
		this.minzu = minzu;
	}

	public String getCardNum() {
		return cardNum;
	}

	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getQianfa() {
		return qianfa;
	}

	public void setQianfa(String qianfa) {
		this.qianfa = qianfa;
	}

	public String getYouxiaoqi() {
		return youxiaoqi;
	}

	public void setYouxiaoqi(String youxiaoqi) {
		this.youxiaoqi = youxiaoqi;
	}

	public String getCardImg() {
		return cardImg;
	}

	public void setCardImg(String cardImg) {
		this.cardImg = cardImg;
	}

	public String getSlevel() {
		return slevel;
	}

	public void setSlevel(String slevel) {
		this.slevel = slevel;
	}

	public String getIsDriver() {
		return isDriver;
	}

	public void setIsDriver(String isDriver) {
		this.isDriver = isDriver;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	public Date getPassdate() {
		return passdate;
	}

	public void setPassdate(Date passdate) {
		this.passdate = passdate;
	}

	public String getMonthId() {
		return monthId;
	}

	public void setMonthId(String monthId) {
		this.monthId = monthId;
	}


	
}