package cn.lhkj.project.vehicle.entity;

import java.util.Date;
import cn.lhkj.commons.defined.Column;
import cn.lhkj.commons.defined.Entity;
import cn.lhkj.commons.util.CalendarUtil;

/**车底扫描 */
@Entity(table="CSL_UVSS_PATH", key="id", partition="monthId")
public class UvssPath {
	/**主键*/
	@Column(value="id")
	private String id;
	
	/**Base64编码的车底图像文件*/
	@Column(value="UVSS_IMAGE")
	private String uvssImage;
	
	/**Base64编码的车牌图像文件 */
	@Column(value="PLATE_IMAGE")
	private String plateImage;
	
	/**车牌号*/
	@Column(value="PLATE_NUMBER")
	private String plateNumber;
	
	/**采集时间*/
	@Column(value="CHECK_DATE_TIME")
	private Date checkDateTime;
	
	/**设备IP地址*/
	@Column(value="IP_ADDRESS")
	private String ipAddress;
	
	/**分区字段*/
	@Column(value="MONTH_ID")
	private String monthId;
	
	//////////////////////////////////////
	public UvssPath(){
		
	}
	
	public UvssPath(String id){
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

	public String getUvssImage() {
		return uvssImage;
	}

	public void setUvssImage(String uvssImage) {
		this.uvssImage = uvssImage;
	}

	public String getPlateImage() {
		return plateImage;
	}

	public void setPlateImage(String plateImage) {
		this.plateImage = plateImage;
	}

	public String getPlateNumber() {
		return plateNumber;
	}

	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}

	public Date getCheckDateTime() {
		return checkDateTime;
	}

	public void setCheckDateTime(Date checkDateTime) {
		this.checkDateTime = checkDateTime;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getMonthId() {
		return monthId;
	}

	public void setMonthId(String monthId) {
		this.monthId = monthId;
	}

}