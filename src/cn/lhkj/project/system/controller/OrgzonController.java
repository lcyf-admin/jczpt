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
import cn.lhkj.commons.entity.ValidformData;
import cn.lhkj.commons.util.StringUtil;
import cn.lhkj.project.system.entity.Orgzon;
import cn.lhkj.project.system.service.OrgzonService;


@Controller
@Scope("prototype")
public class OrgzonController extends BaseController{
	
	private @Resource OrgzonService orgzonService;
	private String path = "orgzon/orgzon_";
	
	@ModelAttribute()
	public void prepare(){
		super.mav = new ModelAndView();
		String orgzonId = super.getParameter("orgzon.id");
		if(StringUtil.isNull(orgzonId)) return;
		try {
			Orgzon orgzon = orgzonService.getOrgzon(orgzonId);
			mav.addObject("orgzon", orgzon);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
	/**获取组织机构数信息 */
	@ResponseBody
	@RequestMapping(value="/orgzon_ajaxOrgzonZTree")
	public String ajaxOrgzonZTree() {
		try {
			super.printArray(orgzonService.getOrgzonZTree());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			super.printText("");
		}
		return null;
	}
	
	/**增加、编辑组织机构*/
	@ResponseBody
	@RequestMapping(value="/orgzon_ajaxUpdate")
	public String ajaxUpdate(Orgzon orgzon){
		ValidformData vd = null;
		String flag = super.getParameter("flag");//sibling增加同级；children增加下级；edit编辑
		try {
			orgzonService.saveOrUpdate(orgzon,flag);
			vd = new ValidformData("y","提交成功！");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			vd = new ValidformData("n","提交失败！");
		}
		super.printObj(vd);
		return null;
	}
	
	/**删除组织机构 */
	@ResponseBody
	@RequestMapping(value="/orgzon_ajaxRemove")
	public String ajaxRemove(){
		try {
			String orgzonId = super.getParameter("orgzonId");
			orgzonService.removeOrgzon(orgzonId);
			super.printText(SUCCESS);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}
	
	@RequestMapping("/orgzon_edit")
	public ModelAndView edit(){
		mav.setViewName(path+"edit");
		//sibling增加同级；children增加下级；edit编辑
		String flag = super.getParameter("flag");
		mav.addObject("flag", flag);
		return mav;
	}
	
	/**
	 * 非ajax请求调转到/WEB-INF/pages/{param}.jsp页面
	 * 参考springmvc-servlet.xml的配置
	 */
	@RequestMapping("/orgzon_{param}")
    public ModelAndView direction(@PathVariable("param")String param){
		mav.setViewName(path+param);
        return mav;
    }
}
