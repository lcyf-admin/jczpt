package cn.lhkj.project.vehicle.entity;

import java.util.Date;
import java.util.List;

import cn.lhkj.commons.defined.Column;
import cn.lhkj.commons.defined.Entity;
import cn.lhkj.commons.util.CalendarUtil;
import cn.lhkj.project.lane.entity.Lane;

/**经过闸机车辆信息 */
@Entity(table="CSL_VEHICLE_BRAKE", key="id", partition="monthId")
public class VehicleBrake {
	
	/**主键*/
	@Column(value="id")
	private String id;
	
	/**车牌号*/
	@Column(value="CAR_NUM")
	private String carNum;
	
	/**车辆照片路径*/
	@Column(value="CAR_IMG")
	private String carImg;
	
	/**车牌号照片路径*/
	@Column(value="NUM_IMG")
	private String numImg;
	
	/**采集设备编号*/
	@Column(value="EQUIPMENT_ID")
	private String equipmentId;
	
	/**采集时间*/
	@Column(value="PASSDATE")
	private Date passdate;
	
	/**数据入库时间*/
	@Column(value="INSERT_TIME")
	private Date insertTime;
	
	/**数据按月分块*/
	@Column(value="MONTH_ID")
	private String monthId;
	
	/**车牌颜色编码*/
	@Column(value="PLATE_COLOR_ID")
	private String plateColorId;
	
	/**车牌颜色*/
	@Column(value="PLATE_COLOR")
	private String plateColor;
	
	/**-1表示未比对；0表示比对放行；1表示比对需进一步检查*/
	@Column(value="STATES")
	private String states;
	
	/**车辆类型*/
	@Column(value="VEHICLE_TYPE")
	private String vehicleType;
	
	/**备注*/
	@Column(value="REMARK")
	private String remark;
	
	/**比对结果*/
	@Column(value="RELUST")
	private String relust;
	
	/**下载到本地的车辆照片路径*/
	@Column(value = "LOCAL_CAR_IMG")
	private String localCarImg;
	
	/**下载到本地车牌照片路径*/
	@Column(value = "LOCAL_NUM_IMG")
	private String localNumImg;
	
	/**图片下载错误次数*/
	@Column(value = "ERROR_COUNT")
	private Integer errorCount;
	
	/**通讯序号：9001或者5001或者6001*/
	@Column(value = "TRAN_TYPE")
	private String tranType;
	
	/**上传数据的IP*/
	@Column(value = "IP")
	private String ip;
	
	/**中后回调接口URL*/
	@Column(value = "BACK_URL")
	private String backUrl;
	
	/**是否已上传*/
	@Column(value = "IS_UPLOAD")
	private String isUpload;
	////////////////////////////////////////
	/**司机姓名*/
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
	
	/**身份证照片*/
	@Column(value="LOCAL_CARD_IMG")
	private String localCardImg;
	
	/**乘客信息*/
	private List<Passenger> passengerList;
	
	/**车道信息*/
	private Lane lane;
	
	//////////////////////////////////////
	public VehicleBrake(){
		
	}
	
	public VehicleBrake(String id){
		this.id = id;
		this.monthId = CalendarUtil.getCurrentMonth();
	}
	//////////////////////////////////////
	public String getPassdateView(){
		return CalendarUtil.format(this.passdate,"yy-MM-dd HH:mm:ss");
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCarNum() {
		return carNum;
	}

	public void setCarNum(String carNum) {
		this.carNum = carNum;
	}

	public String getCarImg() {
		return carImg;
	}

	public void setCarImg(String carImg) {
		this.carImg = carImg;
	}

	public String getNumImg() {
		return numImg;
	}

	public void setNumImg(String numImg) {
		this.numImg = numImg;
	}

	public String getEquipmentId() {
		return equipmentId;
	}

	public void setEquipmentId(String equipmentId) {
		this.equipmentId = equipmentId;
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

	public List<Passenger> getPassengerList() {
		return passengerList;
	}

	public void setPassengerList(List<Passenger> passengerList) {
		this.passengerList = passengerList;
	}

	public String getMonthId() {
		return monthId;
	}

	public void setMonthId(String monthId) {
		this.monthId = monthId;
	}

	public String getPlateColorId() {
		return plateColorId;
	}

	public void setPlateColorId(String plateColorId) {
		this.plateColorId = plateColorId;
	}

	public String getPlateColor() {
		return plateColor;
	}

	public void setPlateColor(String plateColor) {
		this.plateColor = plateColor;
	}

	public String getStates() {
		return states;
	}

	public void setStates(String states) {
		this.states = states;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Lane getLane() {
		return lane;
	}

	public void setLane(Lane lane) {
		this.lane = lane;
	}

	public String getRelust() {
		return relust;
	}

	public void setRelust(String relust) {
		this.relust = relust;
	}

	public String getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}

	public String getLocalCarImg() {
		return localCarImg;
	}

	public void setLocalCarImg(String localCarImg) {
		this.localCarImg = localCarImg;
	}

	public String getLocalNumImg() {
		return localNumImg;
	}

	public void setLocalNumImg(String localNumImg) {
		this.localNumImg = localNumImg;
	}

	public Integer getErrorCount() {
		return errorCount;
	}

	public void setErrorCount(Integer errorCount) {
		this.errorCount = errorCount;
	}

	public String getTranType() {
		return tranType;
	}

	public void setTranType(String tranType) {
		this.tranType = tranType;
	}

	public String getBackUrl() {
		return backUrl;
	}

	public void setBackUrl(String backUrl) {
		this.backUrl = backUrl;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getIsUpload() {
		return isUpload;
	}

	public void setIsUpload(String isUpload) {
		this.isUpload = isUpload;
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

	public String getLocalCardImg() {
		return localCardImg;
	}

	public void setLocalCardImg(String localCardImg) {
		this.localCardImg = localCardImg;
	}

}