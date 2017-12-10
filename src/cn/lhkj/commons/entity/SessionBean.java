package cn.lhkj.commons.entity;

import cn.lhkj.commons.util.StringUtil;
import cn.lhkj.project.system.entity.UserView;



/**
 * Desc：SessionBean 用户登陆后存放相关信息
 */
public class SessionBean {
	private UserView userView;
	/**站点编号*/
	private String stationId;
	/**帐号*/
	private String account;
	/**名称*/
	private String names;
	
//	private List<Role> roleList; //当前用户角色列表	
//	private Integer pendInfoSize;//当前用户待办的个数
//	private HashMap<String,Menu> menuMap;
	private String dataAuth;//当前菜单的数据权限
	
	//////////////////////////////////////
	public String getDataAuth() {
		return dataAuth;
	}

	public void setDataAuth(String dataAuth) {
		this.dataAuth = dataAuth;
	}

	public UserView getUserView() {
		return userView;
	}

	public void setUserView(UserView userView) {
		this.userView = userView;
	}

	public String getStationId() {
		if(StringUtil.isNull(stationId) && userView !=null){
			return userView.getOrgCodes();
		}
		return stationId;
	}

	public void setStationId(String stationId) {
		this.stationId = stationId;
	}

	public String getAccount() {
		if(StringUtil.isNull(account) && userView !=null){
			return userView.getAccount();
		}
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getNames() {
		if(StringUtil.isNull(names) && userView !=null){
			return userView.getNames();
		}
		return names;
	}

	public void setNames(String names) {
		this.names = names;
	}

	
}
