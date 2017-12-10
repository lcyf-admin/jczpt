package cn.lhkj.project.system.service;

import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import cn.lhkj.commons.base.BaseService;
import cn.lhkj.commons.defined.Transactional;
import cn.lhkj.commons.entity.PageInfo;
import cn.lhkj.commons.tools.UUIDFactory;
import cn.lhkj.commons.util.StringUtil;
import cn.lhkj.project.system.entity.Role;
import cn.lhkj.project.system.entity.RoleMenu;
import cn.lhkj.project.system.entity.RoleUser;

@Service("roleService")
@Scope("prototype")
public class RoleServiceImpl extends BaseService implements RoleService {

	@Override
	public Role getRole(String id) throws Exception {
		return (Role)baseDao.getEntity(Role.class, id);
	}

	@Override
	public PageInfo getRolePage(Map<String, Object> requestParams,
			PageInfo pageInfo) throws Exception {
		String hql =  "from Role where 1=1 order by roleName";
		pageInfo = baseDao.findPageByHQL(pageInfo, hql);
		return pageInfo;
	}

	@Override
	public void addRole(Role role) throws Exception {
		if(role == null) throw new NullPointerException();
		role.setId(UUIDFactory.getUUIDStr());
		baseDao.save(role);

	}

	@Override
	public void updateRole(Role role) throws Exception {
		if(role == null) throw new NullPointerException();
		baseDao.update(role);

	}

	@Override
	@Transactional
	public void deleteRole(String id) throws Exception {
		if(StringUtil.isNull(id)) return;
		baseDao.execute("delete from CSL_ROLE where id='"+id+"'");
		baseDao.execute("delete from CSL_ROLE_MENU where ROLE_ID='"+id+"'");
		baseDao.execute("delete from CSL_ROLE_USER where ROLE_ID='"+id+"'");
	}
	
	@Override
	public void deleteRoleUser(String id) throws Exception {
		if(StringUtil.isNull(id)) return;
		baseDao.execute("delete from CSL_ROLE_USER where id='"+id+"'");
	}
	
	@Override
	public void addRoleUser(String userId, String roleId) throws Exception {
		if(StringUtil.isNull(userId) || StringUtil.isNull(roleId)){
			throw new NullPointerException("角色ID为空，或者用户ID为空！");
		}
		baseDao.execute("delete from CSL_ROLE_USER where USER_ID='"+userId+"' and ROLE_ID='"+roleId+"'");
		RoleUser roleUser = new RoleUser(UUIDFactory.getUUIDStr());
		roleUser.setRoleId(roleId);
		roleUser.setUserId(userId);
		baseDao.save(roleUser);
	}
	
	@Override
	public void updateRoleMenu(String roleId, String menuIds) throws Exception {
		if(StringUtil.isNull(roleId)) return;
		String sql = "delete from CSL_ROLE_MENU where ROLE_ID='"+roleId+"' ";
		baseDao.execute(sql);
		if(StringUtil.isNull(menuIds)) return;
		String[] nodeIds = menuIds.split(",");
		RoleMenu roleMenu;
		for(String menuId :nodeIds){
			roleMenu = new RoleMenu();
			roleMenu.setId(UUIDFactory.getUUIDStr());
			roleMenu.setRoleId(roleId);
			roleMenu.setMenuId(menuId);
			baseDao.save(roleMenu);
		}
	}
	
	@Override
	public PageInfo getRoleUserPage(Map<String, Object> requestParams,
			PageInfo pageInfo) throws Exception {
		String parms = StringUtil.getSQLByRequest(requestParams, RoleUser.class);
		String sql = "select " +
				"t.id as ID" +
				",t.USER_ID as USER_ID" +
				",t.ROLE_ID as ROLE_ID" +
				",u.ACCOUNT as ACCOUNT" +
				",u.GENDER as GENDER" +
				",u.NAMES as NAMES" +
				",u.ORG_NAME as ORG_NAME " +
				",'<button type=\"button\" class=\"btn btn-link btn-xs\" onclick=\"javascript: deleteUser(''' || t.ID || ''');\" >删除</button>' as OPT " +
				" from CSL_ROLE_USER t left join CSL_USER_VIEW u on(u.id=t.USER_ID) " +
				" where 1=1 " + parms +
				" order by u.prio,u.serials ";
		pageInfo = baseDao.findPageBySQL(pageInfo, sql);
		return pageInfo;
	}
	
	@Override
	public PageInfo getUserForSelectPage(Map<String, Object> requestParams,
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
		if(requestParams.get("roleId") != null ){
			String roleId = requestParams.get("roleId").toString();;
			hql += " and id not in (select USER_ID from CSL_ROLE_USER where ROLE_ID='"+roleId+"')";
			pageInfo = baseDao.findPageByHQL(pageInfo, hql);
		}
		return pageInfo;
	}

}
