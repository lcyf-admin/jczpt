package cn.lhkj.project.contrast.entity;

import java.util.Date;

import cn.lhkj.commons.defined.Column;
import cn.lhkj.commons.defined.Entity;
import cn.lhkj.commons.util.CalendarUtil;

/**比中车辆数据 */
@Entity(table="CSL_CONTRAST_PERSON", key="id", partition="monthId")
public class ContrastPerson {
	
	/**数据按月分块*/
	@Column(value="MONTH_ID")
	private String monthId;
	
	/**主键*/
	@Column(value="id")
	private String id;
	
	/**姓名*/
	@Column(value="NAMES")
	private String names;
	
	/**民族*/
	@Column(value="NATION")
	private String nation;
	
	/**性别*/
	@Column(value="GENDER")
	private String gender;
	
	/**出生年月*/
	@Column(value="BIRTH")
	private String birth;
	
	/**身份证号*/
	@Column(value="IDCARD")
	private String idcard;
	
	/**采集时间*/
	@Column(value="CAPTURE_TIME")
	private Date captureTime;
	
	/**身份证地址信息*/
	@Column(value="ADDRESS")
	private String address;
	
	/**人员标签*/
	@Column(value = "TAG")
	private String tag;
	
	/**处置手段*/
	@Column(value="ACTION")
	private String action;
	
	/**布控范围*/
	@Column(value="BK_AREA")
	private String bkArea;
	
	/**预警范围*/
	@Column(value="YJ_AREA")
	private String yjArea;
	
	/**数据来源：大数据平台；本地产生*/
	@Column(value="SOURCE")
	private String source;
	
	/**身份证照片tz网.访问时需加入前缀：http://134.32.1.194:18080/SmartECAP/*/
	@Column(value="IDCARD_PHOTO_URL")
	private String idcardPhotoURL;
	
	/**采集照片tz网.访问时需加入前缀：http://134.32.1.194:18080/SmartECAP/*/
	@Column(value="GATHER_PHOTO_URL")
	private String gatherPhotoURL;
	
	/**采集设备编号*/
	@Column(value="EQUIPMENT_ID")
	private String equipmentId;
	
	/**设备所在位置*/
	@Column(value="LOCATION")
	private String location;		
	
	/**备注 */
	@Column(value = "NOTE")
	private String note;
	
	/**区域编码 */
	@Column(value = "AREA_CODE")
	private String areaCode;
	
	/**接收任务的检查站 */
	@Column(value = "CHECK_POINT_ID")
	private String checkPointId;
	
	/**数据入库时间*/
	@Column(value="INSERT_TIME")
	private Date insertTime;
	
	/**是否核查*/
	@Column(value="IS_CHECKED")
	private String isChecked;
	
	/**是否上传*/
	@Column(value="IS_UPLOAD")
	private String isUpload;
	
	/**手机号*/
	@Column(value="PHONE")
	private String phone;

	/**MAC*/
	@Column(value="MAC")
	private String mac;
	
	/**IMEI*/
	@Column(value="IMEI")
	private String imei;
	
	/**IMSI*/
	@Column(value="IMSI")
	private String imsi;
	//////////////////////////////////////
	public ContrastPerson(){
		
	}
	
	public ContrastPerson(String id){
		this.id = id;
		this.monthId = CalendarUtil.getCurrentMonth();
	}
	//////////////////////////////////////
	public String getCaptureTimeView(){
		return CalendarUtil.format(this.captureTime,"MM-dd HH:mm:ss");
	}
	
	public String getIsCheckedView(){
		if("1".equals(this.isChecked)) return "已核查";
		return "未核查";
	}
	
	public String getEquipmentId() {
		return equipmentId;
	}

	public void setEquipmentId(String equipmentId) {
		this.equipmentId = equipmentId;
	}

	public String getLocationOmit() {
		if(location == null || location.length()==0) return "-";
		if(location.length() > 9) return location.substring(0, 9)+".";
		return location;
	}
	
	public String getNamesOmit() {
		if(names == null || names.length()==0) return "-";
		if(names.length() > 9) return names.substring(0,9)+".";
		return names;
	}
	
	public String getOpt(){
		return "<button type=\"button\" class=\"btn btn-link btn-xs\"  onclick=\"dealWarnPerson('"+id+"');\">详细</button>";
	}
	
	public String getIdcardOmit() {
		if(idcard == null || idcard.length()==0) return "-";
		return idcard;
	}
	
	public String getActionOmit() {
		if(action == null || action.length()==0) return "-";
		if(action.length() > 6) return action.substring(0,6)+".";
		return action;
	}
	/////////////////////////////////////////////
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMonthId() {
		return monthId;
	}

	public void setMonthId(String monthId) {
		this.monthId = monthId;
	}

	public String getNames() {
		return names;
	}

	public void setNames(String names) {
		this.names = names;
	}

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public Date getCaptureTime() {
		return captureTime;
	}

	public void setCaptureTime(Date captureTime) {
		this.captureTime = captureTime;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
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

	public String getIdcardPhotoURL() {
		return idcardPhotoURL;
	}

	public void setIdcardPhotoURL(String idcardPhotoURL) {
		this.idcardPhotoURL = idcardPhotoURL;
	}

	public String getGatherPhotoURL() {
		return gatherPhotoURL;
	}

	public void setGatherPhotoURL(String gatherPhotoURL) {
		this.gatherPhotoURL = gatherPhotoURL;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
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

	public String getIsUpload() {
		return isUpload;
	}

	public void setIsUpload(String isUpload) {
		this.isUpload = isUpload;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
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

}