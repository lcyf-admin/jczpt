package cn.lhkj.project.person.entity;

import java.util.Date;

import cn.lhkj.commons.defined.Column;
import cn.lhkj.commons.defined.Entity;
import cn.lhkj.commons.util.CalendarUtil;

/**品恩数据门采集人员的警告数据*/
@Entity(table = "CSL_PERSON_WARNING", key = "ID")
public class PersonWarning {
	
	/** 主键 */
	@Column(value = "ID")
	private String id;// 主键
	
	/** 姓名 */
	@Column(value = "NAMES")
	private String names;
	
	/** 性别 */
	@Column(value = "GENDER")
	private String gender;
	
	/** 身份证号 */
	@Column(value = "IDCARD")
	private String idcard;
	
	/**人员标签 多个以竖线隔开 */
	@Column(value = "tag")
	private String tag;
	
	/**信息描述*/
	@Column(value = "message")
	private String message;
	
	/**三汇黑名单返回值*/
	@Column(value = "RESERVED1")
	private String reserved1;
	
	/**三汇黑名单返回值*/
	@Column(value = "RESERVED2")
	private String reserved2;
	
	/**三汇黑名单返回值*/
	@Column(value = "RESERVED3")
	private String reserved3;
	
	/**三汇黑名单返回值*/
	@Column(value = "RESERVED4")
	private String reserved4;
	
	/** 数据按月分块 */
	@Column(value = "MONTH_ID")
	private String monthId;
	
	@Column(value = "INSERT_TIME")
	private Date insertTime;
	
	/**采集时间 */
	@Column(value = "CAPTURE_TIME")
	private Date captureTime;

	// ////////////////////////////////////
	public PersonWarning() {
	}

	public PersonWarning(String id) {
		this.id = id;
		this.monthId = CalendarUtil.getCurrentMonth();
	}
	// ////////////////////////////////////
	
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

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getReserved1() {
		return reserved1;
	}

	public void setReserved1(String reserved1) {
		this.reserved1 = reserved1;
	}

	public String getReserved2() {
		return reserved2;
	}

	public void setReserved2(String reserved2) {
		this.reserved2 = reserved2;
	}

	public String getReserved3() {
		return reserved3;
	}

	public void setReserved3(String reserved3) {
		this.reserved3 = reserved3;
	}

	public String getReserved4() {
		return reserved4;
	}

	public void setReserved4(String reserved4) {
		this.reserved4 = reserved4;
	}

	public String getMonthId() {
		return monthId;
	}

	public void setMonthId(String monthId) {
		this.monthId = monthId;
	}

	public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	public Date getCaptureTime() {
		return captureTime;
	}

	public void setCaptureTime(Date captureTime) {
		this.captureTime = captureTime;
	}

}