package cn.lhkj.project.system.entity;

import java.util.Date;

import cn.lhkj.commons.defined.Column;
import cn.lhkj.commons.defined.Entity;
import cn.lhkj.commons.util.StringUtil;

@Entity(table="CSL_USER_VIEW",key="id")
public class UserView{
	
	/**主键 */
	@Column(value="ID")
	private String id;
	
	/**登录账号 */
	@Column(value="ACCOUNT")
	private String account;
	
	/**登录密码 */
	@Column(value="PWD")
	private String pwd;
	
	/**用户名称 */
	@Column(value="NAMES")
	private String names;
	
	/**用户性别*/
	@Column(value="GENDER")
	private String gender;
	
	/**用户排序*/
	@Column(value="serials")
	private Integer serials;
	
	/**用户邮箱*/
	@Column(value="email")
	private String email;
	
	/**用户状态 1启用，0锁定*/
	@Column(value="status")
	private String status;
	
	/**用户创建时间*/
	@Column(value="CREATE_TIME")
	private Date createTime;
	
	/**用户修改时间*/
	@Column(value="UPDATE_TIME")
	private Date updateTime;
	
	/**最后登录时间*/
	@Column(value="LOGIN_TIME")
	private Date loginTime;
	
	/**修改人*/
	@Column(value="UPDATER")
	private String updater;
	
	/**组织机构ID*/
	@Column(value="ORG_ID")
	private String orgId;
	
	/**组织机构名称*/
	@Column(value="ORG_NAME")
	private String orgName;
	
	/**组织机构详细名称*/
	@Column(value="ORG_DETAIL_NAME")
	private String orgDetailName;
	
	/**组织机构代码*/
	@Column(value="ORG_CODES")
	private String orgCodes;
	
	/**组织机构顺序*/
	@Column(value="PRIO")
	private Integer prio;
	
	/**经度*/
	@Column(value="x")
	private String x;
	
	/**纬度*/
	@Column(value="y")
	private String y;
	
	////////////////////////////////////
	public String getStatusView(){
		if("1".equals(status)) return "启用";
		if("0".equals(status)) return "<font color=red>锁定</font>";
		return "";
	}
	
	public String getOpt(){
		String btn = "<button type=\"button\" class=\"btn btn-link btn-xs\"  onclick=\"editUser('"+id+"');\">编辑</button>"+
				   "<button type=\"button\" class=\"btn btn-link btn-xs\" onclick=\"deleteUser('"+id+"')\";>删除</button>";	
		return btn;
	}
	
	public String getSelectOpt(){
		String btn = "<button type=\"button\" class=\"btn btn-link btn-xs\"  onclick=\"selectUser('"+id+"');\">添加</button>";
		return btn;
	}
	///////////////////////////////////
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
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

	public Integer getSerials() {
		return serials;
	}

	public void setSerials(Integer serials) {
		this.serials = serials;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public String getUpdater() {
		return updater;
	}

	public void setUpdater(String updater) {
		this.updater = updater;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public Integer getPrio() {
		return prio;
	}

	public void setPrio(Integer prio) {
		this.prio = prio;
	}

	public String getOrgCodes() {
		return orgCodes;
	}

	public void setOrgCodes(String orgCodes) {
		this.orgCodes = orgCodes;
	}

	public String getOrgDetailName() {
		if(StringUtil.isNull(orgDetailName)) return orgName;
		return orgDetailName;
	}

	public void setOrgDetailName(String orgDetailName) {
		this.orgDetailName = orgDetailName;
	}

	public String getX() {
		if(StringUtil.isNull(x)){
			return "--";
		}
		return x;
	}

	public void setX(String x) {
		this.x = x;
	}

	public String getY() {
		if(StringUtil.isNull(y)){
			return "--";
		}
		return y;
	}

	public void setY(String y) {
		this.y = y;
	}

}

	
