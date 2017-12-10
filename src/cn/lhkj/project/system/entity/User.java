package cn.lhkj.project.system.entity;

import java.util.Date;

import cn.lhkj.commons.defined.Column;
import cn.lhkj.commons.defined.Entity;

@Entity(table="CSL_USER",key="id")
public class User{
	
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
	/////////////////////////////////////
	public User(){
		
	}
	
	public User(String id){
		this.id = id;
	}
	
	////////////////////////////////////
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

}

	
