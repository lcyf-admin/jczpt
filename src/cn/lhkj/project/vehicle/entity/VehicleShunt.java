package cn.lhkj.project.vehicle.entity;

import java.util.Date;

import cn.lhkj.commons.defined.Column;
import cn.lhkj.commons.defined.Entity;
import cn.lhkj.commons.util.CalendarUtil;
import cn.lhkj.project.lane.entity.Lane;

/**车辆分流 */
@Entity(table="CSL_VEHICLE_SHUNT", key="id", partition="monthId")
public class VehicleShunt {
	
	/**主键*/
	@Column(value="id")
	private String id;
	
	/**设备ID*/
	@Column(value="EQIPMENT_ID")
	private String eqipmentId;
	
	/**车牌号码*/
	@Column(value="PLATE")
	private String plate;
	
	/**车辆类型*/
	@Column(value="VEHICLE_TYPE")
	private String vehicleType;
	
	/**车辆图片绝对路径*/
	@Column(value="VEHICLE_IMAGE")
	private String vehicleImage;
	
	/**车辆图片绝对路径*/
	@Column(value="PLATE_IMAGE")
	private String plateImage;
	
	/**采集时间*/
	@Column(value="PASSDATE")
	private Date passdate;
	
	/**数据入库时间*/
	@Column(value="INSERT_TIME")
	private Date insertTime;
	
	/**车牌颜色编码*/
	@Column(value="PLATE_COLOR_ID")
	private String plateColorId;
	
	/**车牌颜色*/
	@Column(value="PLATE_COLOR")
	private String plateColor;
	
	/**-1表示未比对；0表示比对放行；1表示比对发送至分流屏幕*/
	@Column(value="STATES")
	private String states;
	
	/**处理结果*/
	@Column(value="RELUST")
	private String relust;
	
	/**备注*/
	@Column(value="REMARK")
	private String remark;
	
	/**数据按月分块*/
	@Column(value="MONTH_ID")
	private String monthId;
	
	
	/**下载到本地的车辆照片路径*/
	@Column(value = "LOCAL_VEHICLE_IMAGE")
	private String localVehicleImage;
	
	/**下载到本地车牌照片路径*/
	@Column(value = "LOCAL_PLATE_IMAGE")
	private String localPlateImage;
	
	/**图片下载错误次数*/
	@Column(value = "ERROR_COUNT")
	private Integer errorCount;
	
	/**上传数据的IP*/
	@Column(value = "IP")
	private String ip;
	
	/**中后回调接口URL*/
	@Column(value = "BACK_URL")
	private String backUrl;
	
	/**是否已上传*/
	@Column(value = "IS_UPLOAD")
	private String isUpload;
	
	/**车道信息*/
	private Lane lane;
	//////////////////////////////////////
	public VehicleShunt(){
		
	}
	
	public VehicleShunt(String id){
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

	public String getPlate() {
		return plate;
	}

	public void setPlate(String plate) {
		this.plate = plate;
	}

	public String getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}

	public String getVehicleImage() {
		return vehicleImage;
	}

	public void setVehicleImage(String vehicleImage) {
		this.vehicleImage = vehicleImage;
	}

	public String getPlateImage() {
		return plateImage;
	}

	public void setPlateImage(String plateImage) {
		this.plateImage = plateImage;
	}

	public Date getPassdate() {
		return passdate;
	}

	public void setPassdate(Date passdate) {
		this.passdate = passdate;
	}

	public String getStates() {
		return states;
	}

	public void setStates(String states) {
		this.states = states;
	}

	public String getEqipmentId() {
		return eqipmentId;
	}

	public void setEqipmentId(String eqipmentId) {
		this.eqipmentId = eqipmentId;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	public String getRelust() {
		return relust;
	}

	public void setRelust(String relust) {
		this.relust = relust;
	}

	public String getMonthId() {
		return monthId;
	}

	public void setMonthId(String monthId) {
		this.monthId = monthId;
	}

	public String getLocalVehicleImage() {
		return localVehicleImage;
	}

	public void setLocalVehicleImage(String localVehicleImage) {
		this.localVehicleImage = localVehicleImage;
	}

	public String getLocalPlateImage() {
		return localPlateImage;
	}

	public void setLocalPlateImage(String localPlateImage) {
		this.localPlateImage = localPlateImage;
	}

	public Integer getErrorCount() {
		return errorCount;
	}

	public void setErrorCount(Integer errorCount) {
		this.errorCount = errorCount;
	}

	public Lane getLane() {
		return lane;
	}

	public void setLane(Lane lane) {
		this.lane = lane;
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

}