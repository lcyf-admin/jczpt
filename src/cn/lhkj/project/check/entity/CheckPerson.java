package cn.lhkj.project.check.entity;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import cn.lhkj.commons.defined.Column;
import cn.lhkj.commons.defined.Entity;
import cn.lhkj.commons.util.CalendarUtil;

/**比中人员反馈信息数据 */
@Entity(table="CHECK_PERSON",key="id")
public class CheckPerson {
	
	/**主键*/
	@Column(value="id")
	private String id;
	
	/**任务ID*/
	@Column(value="TASK_ID")
	private String taskId;
	
	/**预警类别(人证合一;数据门;动态人脸识别;手机号码;...)*/
	@Column(value="YJ_TYPE")
	private String yjType;
	
	/**核录时间*/
	@Column(value="CHECK_TIME")
	private Date checkTime;
	
	/**人员标签*/
	@Column(value="TAG")
	private String tag;
	
	/**是否预警对象(1是；0不是)*/
	@Column(value="IS_CONTRAST")
	private String isContrast;
	
	/**信息采集；滞留审查；立即抓捕*/
	@Column(value="ACTION")
	private String action;
	
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
	
	/**可疑描述，如可疑URL、可疑软件、暴恐音视频等等*/
	@Column(value="FINDS")
	private String finds;
	
	/**是否请假（1:已请假；0:未请假）*/
	@Column(value="IS_VACATION")
	private String isVacation;
	
	/**前往内地原因*/
	@Column(value="REASON")
	private String reason;
	
	/**返回时间*/
	@Column(value="BACKTIME")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date backtime;
	
	/**近一年是否离开本地活动（1:是；0:否）*/
	@Column(value="IS_LEAVE")
	private String isLeave;
	
	/**数据入库时间*/
	@Column(value="INSERT_TIME")
	private Date insertTime;
	
	/**行政区划*/
	@Column(value="AREA_CODE")
	private String areaCode;
	
	/**站点编号*/
	@Column(value="STATION_ID")
	private String stationId;
	
	/**站点名称*/
	@Column(value="STATION_NAME")
	private String stationName;
	
	/**系统名称*/
	@Column(value="REMARK")
	private String remark;
	
	/**是否上传*/
	@Column(value="IS_UPLOAD")
	private String isUpload;
	
	/**核查结论*/
	@Column(value="HCJL")
	private String hcjl;
	
	/**人员去向*/
	@Column(value="DIRECTION")
	private String direction;
	
	/**目的地来由*/
	@Column(value="DIRECTION_REASON")
	private String directionReason;
	
	/**管辖片区民警联系方式*/
	@Column(value="COPNUM")
	private String copnum;
	//////////////////////////////////////
	public CheckPerson(){
		
	}
	
	public CheckPerson(String id){
		this.id = id;
	}
	//////////////////////////////////////
	public String getIsContrastView(){
		if("1".equals(isContrast)) return "是";
		if("0".equals(isContrast)) return "否";
		return isContrast;
	}
	
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
	
	public String getIsVacationView(){
		if("1".equals(isVacation)) return "已请假";
		if("0".equals(isVacation)) return "未请假";
		return isVacation;
	}
	
	public String getIsLeaveView(){
		if("1".equals(isLeave)) return "是";
		if("0".equals(isLeave)) return "否";
		return isLeave;
	}
	
	public String getBacktimeView(){
		return CalendarUtil.format(backtime);
	}
	
	public String getHcjlView(){
		if("1".equals(hcjl)){
			return "不能排除嫌疑继续布控";
		}else if("2".equals(hcjl)){
			return "排除嫌疑建议撤控";
		}else{
			return hcjl;
		}
	}
	/////////////////////////////////////
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getYjType() {
		return yjType;
	}

	public void setYjType(String yjType) {
		this.yjType = yjType;
	}

	public Date getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getIsContrast() {
		return isContrast;
	}

	public void setIsContrast(String isContrast) {
		this.isContrast = isContrast;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
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

	public String getIsVacation() {
		return isVacation;
	}

	public void setIsVacation(String isVacation) {
		this.isVacation = isVacation;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Date getBacktime() {
		return backtime;
	}

	public void setBacktime(Date backtime) {
		this.backtime = backtime;
	}

	public String getIsLeave() {
		return isLeave;
	}

	public void setIsLeave(String isLeave) {
		this.isLeave = isLeave;
	}

	public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getStationId() {
		return stationId;
	}

	public void setStationId(String stationId) {
		this.stationId = stationId;
	}

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getIsUpload() {
		return isUpload;
	}

	public void setIsUpload(String isUpload) {
		this.isUpload = isUpload;
	}

	public String getHcjl() {
		return hcjl;
	}

	public void setHcjl(String hcjl) {
		this.hcjl = hcjl;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public String getDirectionReason() {
		return directionReason;
	}

	public void setDirectionReason(String directionReason) {
		this.directionReason = directionReason;
	}

	public String getCopnum() {
		return copnum;
	}

	public void setCopnum(String copnum) {
		this.copnum = copnum;
	}

}