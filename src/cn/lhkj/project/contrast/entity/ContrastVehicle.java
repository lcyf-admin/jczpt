package cn.lhkj.project.contrast.entity;

import java.util.Date;

import cn.lhkj.commons.defined.Column;
import cn.lhkj.commons.defined.Entity;
import cn.lhkj.commons.util.CalendarUtil;

/**比中车辆数据 */
@Entity(table="CSL_CONTRAST_VEHICLE", key="id", partition="monthId")
public class ContrastVehicle {
	
	/**数据按月分块*/
	@Column(value="MONTH_ID")
	private String monthId;
	
	/**主键*/
	@Column(value="id")
	private String id;
	
	/**车牌号*/
	@Column(value="CAR_NUM")
	private String carNum;
	
	/**采集时间*/
	@Column(value="PASSDATE")
	private Date passdate;
	
	/**车牌颜色*/
	@Column(value="PLATE_COLOR")
	private String plateColor;
	
	/**车辆类型*/
	@Column(value="VEHICLE_TYPE")
	private String vehicleType;
	
	/**身份证号*/
	@Column(value="IDCARD")
	private String idcard;
	
	/**所有人*/
	@Column(value="NAMES")
	private String names;
	
	/**人员标签*/
	@Column(value = "TAG")
	private String tag;
	
	/**车辆状态*/
	@Column(value = "DESCRIBE")
	private String describe;
	
	/**处置手段*/
	@Column(value="ACTION")
	private String action;
	
	/**人员身份证照片base64*/
	@Column(value="PERSON_PHOTO",clob=true)
	private String personPhoto;
	
	/**布控范围*/
	@Column(value="BK_AREA")
	private String bkArea;
	
	/**预警范围*/
	@Column(value="YJ_AREA")
	private String yjArea;
	
	/**数据来源：大数据平台；本站产生*/
	@Column(value="SOURCE")
	private String source;
	
	/**车身颜色*/
	@Column(value="VEHICLE_COLOR")
	private String vehicleColor;
	
	/**车牌类型*/
	@Column(value="PLATE_TYPE")
	private String plateType;
	
	/**采集照片tz网.访问时需加入前缀：http://134.32.1.194:18080/SmartECAP/*/
	@Column(value="GATHER_PHOTO_URL")
	private String gatherPhotoURL;
	
	/**采集设备编号*/
	@Column(value="EQUIPMENT_ID")
	private String equipmentId;
	
	/**设备所在位置 当数据source是本站产生时，字段数据为：前置卡口，xx车道 */
	@Column(value="LOCATION")
	private String location;
	
	/**车标签*/
	@Column(value="LABEL")
	private String label;
	
	/**区域编码 */
	@Column(value = "AREA_CODE")
	private String areaCode;
	
	/**备注 */
	@Column(value = "NOTE")
	private String note;
	
	
	/**户籍地址 */
	@Column(value = "ADDRESS")
	private String address;
	
	/**车主照片*/
	@Column(value = "OWNER_PHOTO_URL")
	private String ownerPhotoUrl;
	
	/**问题车辆类型：1 二手车，2 布控对象车辆*/
	@Column(value = "PROBLEM_TYPE")
	private String problemType;
	
	/**接收任务的检查站 */
	@Column(value = "CHECK_POINT_ID")
	private String checkPointId;
	
	/**数据入库时间*/
	@Column(value="INSERT_TIME")
	private Date insertTime;
	
	/**是否核查*/
	@Column(value="IS_CHECKED")
	private String isChecked;
	
	/**对应的车辆ID*/
	@Column(value="VEHICLE_ID")
	private String vehicleId;
	
	/**是否上传*/
	@Column(value="IS_UPLOAD")
	private String isUpload;
	//////////////////////////////////////
	public ContrastVehicle(){
		
	}
	
	public ContrastVehicle(String id){
		this.id = id;
		this.monthId = CalendarUtil.getCurrentMonth();
	}
	//////////////////////////////////////
	public String getPassdateView(){
		return CalendarUtil.format(this.passdate,"MM-dd HH:mm:ss");
	}
	
	public String getIsCheckedView(){
		if("1".equals(this.isChecked)) return "已核查";
		return "未核查";
	}
	
	public String getLocationOmit() {
		if(location==null || location.length() == 0) return "-";
		if(location.length() > 9) return location.substring(0, 9)+".";
		return location;
	}
	
	public String getNamesOmit(){
		if(names==null || names.length() == 0) return "-";
		if(names.length() > 9) return names.substring(0, 9)+".";
		return names;
	}
	
	public String getIdcardOmit(){
		if(idcard==null || idcard.length() == 0) return "-";
		return idcard;
	}
	
	public String getActionOmit(){
		if(action==null || action.length() == 0) return "-";
		if(action.length() > 6) return action.substring(0, 6)+".";
		return action;
	}
	
	//////////////////////////////////////////////////
	
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

	public String getPlateColor() {
		return plateColor;
	}

	public void setPlateColor(String plateColor) {
		this.plateColor = plateColor;
	}

	public String getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getNames() {
		return names;
	}

	public void setNames(String names) {
		this.names = names;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getPersonPhoto() {
		return personPhoto;
	}

	public void setPersonPhoto(String personPhoto) {
		this.personPhoto = personPhoto;
	}

	public String getBkArea() {
		return bkArea;
	}

	public void setBkArea(String bkArea) {
		this.bkArea = bkArea;
	}

	public String getYjArea() {
		return yjArea;
	}

	public void setYjArea(String yjArea) {
		this.yjArea = yjArea;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getVehicleColor() {
		return vehicleColor;
	}

	public void setVehicleColor(String vehicleColor) {
		this.vehicleColor = vehicleColor;
	}

	public String getPlateType() {
		return plateType;
	}

	public void setPlateType(String plateType) {
		this.plateType = plateType;
	}

	public String getGatherPhotoURL() {
		return gatherPhotoURL;
	}

	public void setGatherPhotoURL(String gatherPhotoURL) {
		this.gatherPhotoURL = gatherPhotoURL;
	}

	public String getEquipmentId() {
		return equipmentId;
	}

	public void setEquipmentId(String equipmentId) {
		this.equipmentId = equipmentId;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getOwnerPhotoUrl() {
		return ownerPhotoUrl;
	}

	public void setOwnerPhotoUrl(String ownerPhotoUrl) {
		this.ownerPhotoUrl = ownerPhotoUrl;
	}

	public String getProblemType() {
		return problemType;
	}

	public void setProblemType(String problemType) {
		this.problemType = problemType;
	}

	public String getCheckPointId() {
		return checkPointId;
	}

	public void setCheckPointId(String checkPointId) {
		this.checkPointId = checkPointId;
	}

	public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	public String getIsChecked() {
		return isChecked;
	}

	public void setIsChecked(String isChecked) {
		this.isChecked = isChecked;
	}

	public String getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(String vehicleId) {
		this.vehicleId = vehicleId;
	}

	public String getIsUpload() {
		return isUpload;
	}

	public void setIsUpload(String isUpload) {
		this.isUpload = isUpload;
	}


}