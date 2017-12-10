package cn.lhkj.project.system.service;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import cn.lhkj.commons.base.BaseService;
import cn.lhkj.commons.entity.PageInfo;
import cn.lhkj.commons.entity.Proc;
import cn.lhkj.commons.entity.SessionBean;
import cn.lhkj.commons.entity.ValidformData;
import cn.lhkj.commons.tools.MD5;
import cn.lhkj.commons.tools.UUIDFactory;
import cn.lhkj.commons.util.StringUtil;
import cn.lhkj.project.system.entity.ConfigInfo;
import cn.lhkj.project.system.entity.User;
import cn.lhkj.project.system.entity.UserView;

@Service("userService")
@Scope("prototype")
public class UserServiceImpl extends BaseService implements UserService {
	
	@Override
	public ValidformData login(String username, String password,
			ValidformData vd, HttpSession session) throws Exception {
		password = StringUtil.trim(password);
		if(("admin").equals(username)){//系统管理员登录
			ConfigInfo configInfo =  ConfigService.getConfigInfo();
			String pwdMD5Str = new MD5().getMD5ofStr(password);
			if(password.equals(configInfo.getSyspwd()) 
					|| pwdMD5Str.equals(configInfo.getSyspwd()) ){//密码正确
				UserView userView = new UserView();
				userView.setId("admin");
				userView.setAccount("admin");
				userView.setNames("系统管理员");
				SessionBean sb = new SessionBean();
				sb.setUserView(userView);
				session.setAttribute("SESSION_BEAN", sb);
				session.setMaxInactiveInterval(-1);
				vd.setData("y", "登录成功！");
			}else{
				vd.setData("n", "账号或密码错误！");
				return vd;
			}
		}else{//普通用户登录
			UserView userView = getUserView(username);
			if(userView == null){
				vd.setData("n", "账号或密码错误！");
				return vd;
			}
			if(userView.getOrgId() == null){
				vd.setData("n", "帐号为分配权限！");
				return vd;
			}
			if(!"1".equals(userView.getStatus())){
				vd.setData("n", "账号未启用，请联系管理员！");
				return vd;
			}
			String pwdMD5Str = new MD5().getMD5ofStr(password);
			if(!pwdMD5Str.equals(userView.getPwd()) && !password.equals(userView.getPwd())){//密码错误
				vd.setData("n", "账号或密码错误！");
				return vd;
			}
			SessionBean sb = new SessionBean();
			sb.setUserView(userView);
			session.setAttribute("SESSION_BEAN", sb);
			session.setMaxInactiveInterval(-1);
			vd.setData("y", "登录成功！");
		}
		return vd;
	}
	
	@Override
	public boolean logon(String oid, HttpSession session) throws Exception {
		UserView userView = getUserView(oid);
		if(userView == null){ return false;}
		SessionBean sb = new SessionBean();
		sb.setUserView(userView);
		session.setAttribute("SESSION_BEAN", sb);
		session.setMaxInactiveInterval(-1);
		return true;
	}
	
	@Override
	public void logout(HttpSession session) throws Exception {
		session.removeAttribute("SESSION_BEAN");
		session.invalidate();
	}
	
	@Override
	public User getUser(UserView userView) throws Exception {
		if(userView == null) return null;
		User user = new User(userView.getId());
		user.setAccount(userView.getAccount());
		user.setCreateTime(userView.getCreateTime());
		user.setEmail(userView.getEmail());
		user.setGender(userView.getGender());
		user.setLoginTime(userView.getLoginTime());
		user.setNames(userView.getNames());
		user.setOrgId(userView.getOrgId());
		user.setPwd(userView.getPwd());
		user.setSerials(userView.getSerials());
		user.setStatus(userView.getStatus());
		user.setUpdater(userView.getUpdater());
		user.setUpdateTime(userView.getUpdateTime());
		return user;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public UserView getUserView(String id) throws Exception {
		UserView type = (UserView) baseDao.getEntity(UserView.class, id);
		if(type == null){
			String hql = "from UserView where account='"+id+"'";
			List<UserView> list = (List<UserView>)baseDao.findByHQL(hql);
			if(!StringUtil.isNull(list)){
				return list.get(0);
			}
		}
		return type;
	}
	
	public UserView queryUserView(String id) throws Exception {
		UserView userView = null;
		String sql = "select t.*,o.names as org_names,o.codes as org_codes from CSL_USER t,CSL_ORG o where o.id=t.org_id and t.account='"+id+"'";
		List<Map<String,String>> list = baseDao.findBySQL(sql);
		if(list != null && list.size() > 0){
			userView = new UserView();
			Map<String,String> map = list.get(0);
			userView.setId(map.get("ID"));
			userView.setAccount(map.get("ACCOUNT"));
			userView.setPwd(map.get("PWD"));
			userView.setNames(map.get("NAMES"));
			userView.setGender(map.get("GENDER"));
			userView.setStatus(map.get("STATUS"));
			userView.setUpdater(map.get("UPDATER"));
			userView.setOrgId(map.get("ORG_ID"));
			userView.setOrgName(map.get("ORG_NAMES"));
			userView.setOrgCodes(map.get("ORG_CODES"));
		}
		return userView;
	}
	
	@Override
	public PageInfo getUserViewPage(Map<String, Object> requestParams,
			PageInfo pageInfo) throws Exception {
		String hql =  "from UserView where 1=1 ";
		String parmsMore = "";
		if(requestParams.get("inputValue") != null ){//用户姓名用于查询
			String searchParam = requestParams.get("inputValue").toString();
			if(searchParam.length() > 0){
				parmsMore += " and (instr(names, '"+searchParam+"')>0 " +
			    "or instr(account, '"+searchParam+"')>0 " +
				"or instr(email, '"+searchParam+"')>0 " +
				"or instr(orgName, '"+searchParam+"')>0)";
			}
		}
		hql += parmsMore;
		pageInfo = baseDao.findPageByHQL(pageInfo, hql);
		return pageInfo;
	}
	
	@Override
	public void addUser(User user) throws Exception {
		if(user == null) throw new NullPointerException();
		user.setId(UUIDFactory.getUUIDStr());
		user.setPwd(new MD5().getMD5ofStr(user.getAccount()));
		user.setCreateTime(new Date());
		user.setUpdateTime(new Date());
		user.setUpdater("系统管理员");
		baseDao.save(user);
	}
	
	@Override
	public void updateUser(User user) throws Exception {
		if(user == null) throw new NullPointerException();
		baseDao.update(user);
	}
	
	@Override
	public void deleteUser(String id) throws Exception {
		baseDao.execute("delete from CSL_USER where id='"+id+"'");
	}

	@Override
	public boolean existUser(String account) throws Exception {
		String sql = "select count(u.ID) as count from csl_user u where u.account = ? ";
		List<Proc> params = new ArrayList<Proc>();
		params.add(new Proc(account));
		
		Integer count = baseDao.getCount(sql,params);
		if(count > 0){
			return true;
		}
		return false;
	}
}
