package cn.lhkj.project.check.entity;

import java.util.Date;

import cn.lhkj.commons.defined.Column;
import cn.lhkj.commons.defined.Entity;
import cn.lhkj.commons.util.StringUtil;

/**比中车辆反馈数据 */
@Entity(table="CHECK_VEHICLE",key="id")
public class CheckVehicle {
	
	/**主键*/
	@Column(value="id")
	private String id;
	
	/**预警描述：二手车；布控对象车辆*/
	@Column(value="YJ_TYPE")
	private String yjType;
	
	/**任务ID*/
	@Column(value="TASK_ID")
	private String taskId;
	
	/**车牌号*/
	@Column(value="CAR_NUM")
	private String carNum;
	
	/**违禁物品*/
	@Column(value="FORBIDS")
	private String forbids;
	
	/**与车主关系：本人；亲属或社会关系；无关*/
	@Column(value="RELATIONS")
	private String relations;
	
	/**驾驶员非车主：借亲属、朋友车辆；公务车辆；车辆未过户；其它*/
	@Column(value="EXPLAINS")
	private String explains;
	
	/**核查时间*/
	@Column(value="CHECK_TIME")
	private Date checkTime;
	
	
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
	
	/**车牌颜色*/
	@Column(value="PLATECOLOR")
	private String platecolor;
	
	/**车身颜色*/
	@Column(value="VEHICLECOLOR")
	private String vehiclecolor;
	
	/**车辆类型*/
	@Column(value="VEHICLETYPE")
	private String vehicletype;
	
	/**车辆去向*/
	@Column(value="DIRECTION")
	private String direction;
	
	/**目的地*/
	@Column(value="DESTINATION")
	private String destination;
	
	/**滞留时间*/
	@Column(value="RESIDENCE_TIME")
	private String residenceTime;
	
	/**办事是由*/
	@Column(value="REASON")
	private String reason;
	
	/**核录人*/
	@Column(value="ENTERER")
	private String enterer;
	
	
	/**车辆性质*/
	@Column(value="VNATURE")
	private String vnature;
	
	/**车辆所属单位*/
	@Column(value="VUNIT")
	private String vunit;
	
	/**是否需要再次核查*/
	@Column(value="ACHECK")
	private String acheck;
	
	/**不再次核查原因*/
	@Column(value="AKEASON")
	private String akeason;
	
	/**不再次核查其他原因*/
	@Column(value="ACONTENT")
	private String acontent;
	
	//////////////////////////////////////
	public CheckVehicle(){
		
	}
	
	public CheckVehicle(String id){
		this.id = id;
	}
	//////////////////////////////////////
	public String getVnatureView(){
		if ("1".equals(vnature)) {
			return "私家车";
		}else if("2".equals(vnature)){
			return "运营车";
		}else if("3".equals(vnature)){
			return "党政军企事业车辆";
		}else{
			return vnature;
		}
	}
	
	public String getAkeasonView(){
		if ("1".equals(akeason)) {
			return "借用亲属车辆";
		}else if("2".equals(akeason)){
			return "借用社会关系车辆";
		}else if("3".equals(akeason)){
			return "正在办理过户车辆";
		}else if("4".equals(akeason)){
			return "租赁公司";
		}else if("5".equals(akeason)){
			return "企事业单位车辆";
		}else if("6".equals(akeason)){
			return "其他";
		}else{
			return akeason;
		}
	}
	
	public String getRelationNum(){
		if ("一致".equals(relations)) {
			return "1";
		}else if("不一致，借亲属、朋友车辆".equals(relations)){
			return "2";
		}else if("不一致，公务车辆".equals(relations)){
			return "3";
		}else if("不一致，车辆未过户".equals(relations)){
			return "4";
		}else if("不一致，其他".equals(relations)){
			return "5";
		}
		return null;
	}
	
	public String getRelationsView(){
		if ("1".equals(relations)) {
			return "一致";
		}else if("2".equals(relations)){
			return "不一致，借亲属、朋友车辆";
		}else if("3".equals(relations)){
			return "不一致，公务车辆";
		}else if("4".equals(relations)){
			return "不一致，车辆未过户";
		}else if("5".equals(relations)){
			return "不一致，其他";
		}else{
			return relations;
		}
	}
	
	public String getExplainsView(){
		if(StringUtil.isNull(explains)) return "一致";
		return "不一致,"+explains;
	}
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getYjType() {
		return yjType;
	}

	public void setYjType(String yjType) {
		this.yjType = yjType;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getCarNum() {
		return carNum;
	}

	public void setCarNum(String carNum) {
		this.carNum = carNum;
	}

	public String getForbids() {
		return forbids;
	}

	public void setForbids(String forbids) {
		this.forbids = forbids;
	}

	public String getRelations() {
		return relations;
	}

	public void setRelations(String relations) {
		this.relations = relations;
	}

	public String getExplains() {
		return explains;
	}

	public void setExplains(String explains) {
		this.explains = explains;
	}

	public Date getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
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

	public String getPlatecolor() {
		return platecolor;
	}

	public void setPlatecolor(String platecolor) {
		this.platecolor = platecolor;
	}

	public String getVehiclecolor() {
		return vehiclecolor;
	}

	public void setVehiclecolor(String vehiclecolor) {
		this.vehiclecolor = vehiclecolor;
	}

	public String getVehicletype() {
		return vehicletype;
	}

	public void setVehicletype(String vehicletype) {
		this.vehicletype = vehicletype;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getResidenceTime() {
		return residenceTime;
	}

	public void setResidenceTime(String residenceTime) {
		this.residenceTime = residenceTime;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getEnterer() {
		return enterer;
	}

	public void setEnterer(String enterer) {
		this.enterer = enterer;
	}

	public String getVnature() {
		return vnature;
	}

	public void setVnature(String vnature) {
		this.vnature = vnature;
	}

	public String getVunit() {
		return vunit;
	}

	public void setVunit(String vunit) {
		this.vunit = vunit;
	}

	public String getAcheck() {
		return acheck;
	}

	public void setAcheck(String acheck) {
		this.acheck = acheck;
	}

	public String getAkeason() {
		return akeason;
	}

	public void setAkeason(String akeason) {
		this.akeason = akeason;
	}

	public String getAcontent() {
		return acontent;
	}

	public void setAcontent(String acontent) {
		this.acontent = acontent;
	}

}