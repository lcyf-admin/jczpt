package cn.lhkj.project.person.entity;

import java.util.Date;

import cn.lhkj.commons.defined.Column;
import cn.lhkj.commons.defined.Entity;
import cn.lhkj.commons.util.CalendarUtil;
import cn.lhkj.commons.util.StringUtil;
import cn.lhkj.project.hall.entity.Hall;

/**品恩数据门采集人员数据*/
@Entity(table = "CSL_PERSON", key = "ID")
public class Person {
	
	/** 主键 */
	@Column(value = "ID")
	private String id;// 主键
	
	/** 姓名 */
	@Column(value = "NAMES")
	private String names;
	
	/** 民族 */
	@Column(value = "NATION")
	private String nation;
	
	/** 性别 */
	@Column(value = "GENDER")
	private String gender;
	
	/** 出生年月 */
	@Column(value = "BIRTH")
	private Date birth;
	
	/** 身份证号 */
	@Column(value = "IDCARD")
	private String idcard;
	
	
	/** 采集设备编号 */
	@Column(value = "EQUIPMENT_ID")
	private String equipmentId;
	
	/**采集时间 */
	@Column(value = "CAPTURE_TIME")
	private Date captureTime;
	
	/**身份证地址信息*/
	@Column(value = "ADDRESS")
	private String address;
	
	/**身份证有效期开始*/
	@Column(value = "START_DATE")
	private String startDate;
	
	/**身份证有效期结束*/
	@Column(value = "END_DATE")
	private String endDate;
	
	/**是否人证合一*/
	@Column(value = "IS_A_PERSON")
	private String isAPerson;
	
	/**抓拍人照片路径*/
	@Column(value = "IMG_URL")
	private String imgUrl;
	
	/**下载到本地的照片路径*/
	@Column(value = "LOCAL_IMG_URL")
	private String localImgUrl;
	
	/**图片下载错误次数*/
	@Column(value = "ERROR_COUNT")
	private Integer errorCount;
	
	/**位置*/
	@Column(value = "LOCATION")
	private String location;
	
	/** 数据按月分块 */
	@Column(value = "MONTH_ID")
	private String monthId;
	
	/**数据入库时间 */
	@Column(value = "INSERT_TIME")
	private Date insertTime;
	
	/** 是否核录*/
	@Column(value = "IS_CHECK")
	private String isCheck;
	
	/** 是否上传*/
	@Column(value = "IS_UPLOAD")
	private String isUpload;

	/**安检通道*/
	private Hall hall;
	// ////////////////////////////////////
	public Person() {
	}

	public Person(String id) {
		this.id = id;
		this.monthId = CalendarUtil.getCurrentMonth();
	}
	// ////////////////////////////////////
	public String getCaptureTimeView(){
		return CalendarUtil.format(this.captureTime,"yy-MM-dd HH:mm:ss");
	}
	
	public String getAge(){
		return StringUtil.getAge(idcard);
	}
	///////////////////////////////////
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getMonthId() {
		return monthId;
	}

	public void setMonthId(String monthId) {
		this.monthId = monthId;
	}

	public Hall getHall() {
		return hall;
	}

	public void setHall(Hall hall) {
		this.hall = hall;
	}

	public String getEquipmentId() {
		return equipmentId;
	}

	public void setEquipmentId(String equipmentId) {
		this.equipmentId = equipmentId;
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

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	public String getIsCheck() {
		return isCheck;
	}

	public void setIsCheck(String isCheck) {
		this.isCheck = isCheck;
	}

	public String getIsAPerson() {
		if("1".equals(isAPerson))
			return "是";
		return "否";
	}

	public void setIsAPerson(String isAPerson) {
		this.isAPerson = isAPerson;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getLocalImgUrl() {
		return localImgUrl;
	}

	public void setLocalImgUrl(String localImgUrl) {
		this.localImgUrl = localImgUrl;
	}

	public Integer getErrorCount() {
		return errorCount;
	}

	public void setErrorCount(Integer errorCount) {
		this.errorCount = errorCount;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getIsUpload() {
		return isUpload;
	}

	public void setIsUpload(String isUpload) {
		this.isUpload = isUpload;
	}

	public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}
}