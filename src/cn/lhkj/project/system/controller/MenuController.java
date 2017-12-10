package cn.lhkj.project.system.controller;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.lhkj.commons.base.BaseController;
import cn.lhkj.project.system.entity.Menu;
import cn.lhkj.project.system.service.MenuService;

@Controller
@Scope("prototype")
public class MenuController extends BaseController{
	
	private @Resource MenuService menuService;
	
	@ResponseBody
	@RequestMapping(value="/menu_ajaxMenus")
	public String ajaxMenus(){
		try {
			List<Menu> menuList = menuService.getMenus(super.getSessionBean());
			super.printArray(menuList);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}
	
	/**获取角色下的菜单树带复选框 */
	@ResponseBody
	@RequestMapping(value="/menu_ajaxZTree")
	public String ajaxZTree() {
		try {
			String roleId = super.getParameter("roleId");
			super.printArray(menuService.getMenuZtree(roleId));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}
	
}
