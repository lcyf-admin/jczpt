package cn.lhkj.project.system.service;

import java.util.Map;

import javax.servlet.http.HttpSession;

import cn.lhkj.commons.entity.PageInfo;
import cn.lhkj.commons.entity.ValidformData;
import cn.lhkj.project.system.entity.User;
import cn.lhkj.project.system.entity.UserView;

public interface UserService {
	
	/**
	 * 登录系统
	 * @param username 登录账号
	 * @param password 登录密码
	 */
	public ValidformData login(String username ,String password, ValidformData vd, HttpSession session) throws Exception;
	
	/**单点登录 */
	public boolean logon(String oid , HttpSession session) throws Exception;
	
	/**注销登录 */
	public void logout(HttpSession session) throws Exception;
	
	/**获取用户实例  */
	public UserView getUserView(String id) throws Exception;
	
	public UserView queryUserView(String id) throws Exception;
	
	/**获取用户实例  */
	public User getUser(UserView userView) throws Exception;
	
	/**
	 * 获取用户信息
	 * @param requestParams 查询条件
	 * @param pageInfo Grid基本参数
	 * @return
	 * @throws Exception
	 */
	public PageInfo getUserViewPage(Map<String, Object> requestParams,PageInfo pageInfo)throws Exception;
	
	/**
	 * 添加用户
	 * @param user
	 * @throws Exception
	 */
	public void addUser(User user) throws Exception;
	
	/**
	 * 更新用户
	 * @param user
	 * @throws Exception
	 */
	public void updateUser(User user) throws Exception;
	
	/**
	 * 删除用户
	 * @param id 用户ID
	 * @throws Exception
	 */
	public void deleteUser(String id) throws Exception;
	
	/**
	 * 判断用户账号是否存在
	 * @param account
	 * @return
	 * @throws Exception 
	 */
	public boolean existUser(String account) throws Exception;
}
