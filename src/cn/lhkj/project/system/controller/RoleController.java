package cn.lhkj.project.system.controller;


import javax.annotation.Resource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.lhkj.commons.base.BaseController;
import cn.lhkj.commons.entity.PageInfo;
import cn.lhkj.commons.entity.ValidformData;
import cn.lhkj.commons.util.StringUtil;
import cn.lhkj.project.system.entity.Role;
import cn.lhkj.project.system.service.RoleService;

@Controller
@Scope("prototype")
public class RoleController extends BaseController{
	
	private @Resource RoleService roleService;
	private String path = "role/role_";
	
	@ModelAttribute()
	public void prepare(){
		super.mav = new ModelAndView();
		String roleId = super.getParameter("role.id");
		if(StringUtil.isNull(roleId)) return;
		try {
			Role role = roleService.getRole(roleId);
			mav.addObject("role", role);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
	/**获取角色列表数据*/
	@ResponseBody
	@RequestMapping(value="/role_ajaxGrid")
	public String ajaxGrid(){
		try {
			PageInfo pageInfo = roleService.getRolePage(super.getRequestParams(), super.getPageInfo());
			super.printObj(pageInfo);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}
	
	/**增加角色*/
	@ResponseBody
	@RequestMapping(value="/role_ajaxAdd")
	public String ajaxAdd(Role role){
		try {
			roleService.addRole(role);
			vd = new ValidformData("y","添加成功！");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			vd = new ValidformData("n","添加失败！");
		}
		super.printObj(vd);
		return null;
	}
	
	/**更新角色*/
	@ResponseBody
	@RequestMapping(value="/role_ajaxUpdate")
	public String ajaxUpdate(Role role){
		try {
			roleService.updateRole(role);
			vd = new ValidformData("y","更新成功！");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			vd = new ValidformData("n","更新失败！");
		}
		super.printObj(vd);
		return null;
	}
	
	/**删除角色*/
	@ResponseBody
	@RequestMapping(value="/role_ajaxDelete")
	public String ajaxDelete(){
		try {
			String roleId = super.getParameter("roleId");
			roleService.deleteRole(roleId);
			super.printText(SUCCESS);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}
	
	/**删除角色中的用户*/
	@ResponseBody
	@RequestMapping(value="/role_ajaxDeleteRoleUser")
	public String ajaxDeleteRoleUser(){
		try {
			String roleUserId = super.getParameter("roleUserId");
			roleService.deleteRoleUser(roleUserId);
			super.printText(SUCCESS);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}
	
	/**更新角色菜单*/
	@ResponseBody
	@RequestMapping(value="/role_ajaxUpdateRoleMenu")
	public String ajaxUpdateRoleMenu(){
		try {
			String roleId = super.getParameter("roleId");
			String menuIds = super.getParameter("menuIds");
			roleService.updateRoleMenu(roleId,menuIds);
			vd = new ValidformData("y","设置菜单成功！");
		} catch (Exception e) {
			vd = new ValidformData("n","设置菜单失败！");
			log.error(e.getMessage(), e);
		}
		super.printObj(vd);
		return null;
	}
	
	/**获取角色用户列表数据*/
	@ResponseBody
	@RequestMapping(value="/role_ajaxRoleUserGrid")
	public String ajaxRoleUserGrid(){
		try {
			PageInfo pageInfo = roleService.getRoleUserPage(super.getRequestParams(), super.getPageInfo());
			super.printObj(pageInfo);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}
	
	/**待角色添加的用户信息*/
	@ResponseBody
	@RequestMapping(value="/role_ajaxUserForSelectGrid")
	public String ajaxUserForSelectGrid(){
		try {
			PageInfo pageInfo = roleService.getUserForSelectPage(super.getRequestParams(), super.getPageInfo());
			super.printObj(pageInfo);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}
	
	/**增加角色用户*/
	@ResponseBody
	@RequestMapping(value="/role_ajaxAddUser")
	public String ajaxAddUser(){
		try {
			String userId = super.getParameter("userId");
			String roleId = super.getParameter("roleId");
			roleService.addRoleUser(userId,roleId);
			vd = new ValidformData("y","添加成功！");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			vd = new ValidformData("n","添加失败！");
		}
		super.printObj(vd);
		return null;
	}
	
	/**
	 * 非ajax请求调转到/WEB-INF/pages/{param}.jsp页面
	 * 参考springmvc-servlet.xml的配置
	 */
	@RequestMapping("/role_{param}")
    public ModelAndView userDirection(@PathVariable("param")String param){
		mav.setViewName(path+param);
        return mav;
    }
}
