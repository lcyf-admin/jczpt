package cn.lhkj.project.transfer.entity;

import cn.lhkj.commons.defined.Column;
import cn.lhkj.commons.defined.Entity;
import cn.lhkj.commons.util.CalendarUtil;

/** 车证合一接口调用记录  */
@Entity(table="CSL_TRANSFER", key="id", partition="monthId")
public class Transfer {
	
	@Column(value="ID")
	private String id;
	
	@Column(value="TRAN_NO")
	private String tranNO;//通讯号，此次的唯一标识
	
	@Column(value="VER")
	private String ver;//接口版本，如1.0
	
	@Column(value="TRAN_TYPE")
	private String tranType;//代表不同的通讯请求
	
	@Column(value="TRAN_RESULT")
	private String tranResult;//200:成功；400：失败
	
	@Column(value="TRAN_MSG")
	private String tranMsg;//信息描述：正常 。。。
	
	@Column(value="SEND_TIME")
	private String sendTime;//回复时间
	
	@Column(value="KEY")
	private String key;//MD5签名，不同厂家不同
	
	@Column(value="DATA",clob=true)
	private String data;//返回的数据对象
	
	/**数据按月分块*/
	@Column(value="MONTH_ID")
	private String monthId;//
	////////////////////////////////////////////////
	public Transfer(){
		
	}
	
	public Transfer(String id){
		this.id = id;
		this.monthId = CalendarUtil.getCurrentMonth();
	}
	////////////////////////////////////////////////
	public String getOpt(){
		return "<button type=\"button\" class=\"btn btn-link btn-xs\"  onclick=\"detail('"+id+"');\">详细</button>";
	}
	////////////////////////////////////////////////
	public String getVer() {
		return ver;
	}

	public void setVer(String ver) {
		this.ver = ver;
	}

	public String getTranNO() {
		return tranNO;
	}

	public void setTranNO(String tranNO) {
		this.tranNO = tranNO;
	}

	public String getTranType() {
		return tranType;
	}

	public void setTranType(String tranType) {
		this.tranType = tranType;
	}

	public String getTranResult() {
		return tranResult;
	}

	public void setTranResult(String tranResult) {
		this.tranResult = tranResult;
	}

	public String getTranMsg() {
		return tranMsg;
	}

	public void setTranMsg(String tranMsg) {
		this.tranMsg = tranMsg;
	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

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
	
}
