package cn.lhkj.project.system.entity;

/**
 * 系统的配置信息
 * 对应配置文件：config.properties
 */
public class ConfigInfo {
	
	/**系统管理员密码*/
	private String syspwd;
	
	/**是否开启模拟*/
	private String testModel;
	
	/**数据门请求数据轮询时间（毫秒）*/
	private Integer dataDoorPostSleep;
	
	/**三汇比对地址*/
	private String comparBigDataURL;
	
	/**前置卡口的设备ID*/
	private String frontId;
	
	/**合法的ip才接收数据*/
	private String legalIds;
	
	/**特检通道比对地址*/
	private String comparSpecialURL;
	
	/**特检通道设备编号*/
	private String deviceCode;
	
	/**核录上传的URL*/
	private String checkUploadURL;
	
	/**全量数据上传的URL*/
	private String vehicleUploadURL;
	
	/**人员数据上传的URL*/
	private String personUploadURL;
	
	/**预警数据上传URL*/
	private String warningUploadURL;
	
	/**请求超时时间(毫秒)*/
	private Integer timeOut;
	
	/**文件服务器IP*/
	private String fileServerIp;
	
	/**前置卡口是否普通车辆也分流*/
	private String isShunt;
	///////////////////////////////////////
	public String getSyspwd() {
		return syspwd;
	}

	public void setSyspwd(String syspwd) {
		this.syspwd = syspwd;
	}

	public String getTestModel() {
		return testModel;
	}

	public void setTestModel(String testModel) {
		this.testModel = testModel;
	}

	public Integer getDataDoorPostSleep() {
		return dataDoorPostSleep;
	}

	public void setDataDoorPostSleep(Integer dataDoorPostSleep) {
		this.dataDoorPostSleep = dataDoorPostSleep;
	}

	public String getComparBigDataURL() {
		return comparBigDataURL;
	}

	public void setComparBigDataURL(String comparBigDataURL) {
		this.comparBigDataURL = comparBigDataURL;
	}

	public String getFrontId() {
		return frontId;
	}

	public void setFrontId(String frontId) {
		this.frontId = frontId;
	}

	public String getLegalIds() {
		return legalIds;
	}

	public void setLegalIds(String legalIds) {
		this.legalIds = legalIds;
	}

	public String getComparSpecialURL() {
		return comparSpecialURL;
	}

	public void setComparSpecialURL(String comparSpecialURL) {
		this.comparSpecialURL = comparSpecialURL;
	}

	public String getDeviceCode() {
		return deviceCode;
	}

	public void setDeviceCode(String deviceCode) {
		this.deviceCode = deviceCode;
	}

	public String getCheckUploadURL() {
		return checkUploadURL;
	}

	public void setCheckUploadURL(String checkUploadURL) {
		this.checkUploadURL = checkUploadURL;
	}

	public Integer getTimeOut() {
		return timeOut;
	}

	public void setTimeOut(Integer timeOut) {
		this.timeOut = timeOut;
	}

	public String getVehicleUploadURL() {
		return vehicleUploadURL;
	}

	public void setVehicleUploadURL(String vehicleUploadURL) {
		this.vehicleUploadURL = vehicleUploadURL;
	}

	public String getFileServerIp() {
		return fileServerIp;
	}

	public void setFileServerIp(String fileServerIp) {
		this.fileServerIp = fileServerIp;
	}

	public String getIsShunt() {
		return isShunt;
	}

	public void setIsShunt(String isShunt) {
		this.isShunt = isShunt;
	}

	public String getPersonUploadURL() {
		return personUploadURL;
	}

	public void setPersonUploadURL(String personUploadURL) {
		this.personUploadURL = personUploadURL;
	}

	public String getWarningUploadURL() {
		return warningUploadURL;
	}

	public void setWarningUploadURL(String warningUploadURL) {
		this.warningUploadURL = warningUploadURL;
	}
	
}
