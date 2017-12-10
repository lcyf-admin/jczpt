package cn.lhkj.project.check.controller;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.lhkj.commons.base.BaseController;
import cn.lhkj.commons.entity.ValidformData;
import cn.lhkj.project.check.entity.CheckPerson;
import cn.lhkj.project.check.service.CheckPersonService;

@Controller
@Scope("prototype")
public class CheckPersonController extends BaseController{
	public static final Logger logger = Logger.getLogger(CheckPersonController.class);
	
	private @Resource CheckPersonService checkPersonService;
	
	/**增加*/
	@ResponseBody
	@RequestMapping(value="/checkPerson_ajaxAdd")
	public String ajaxAdd(@ModelAttribute CheckPerson checkPerson){
		try {
			checkPersonService.addCheckPerson(checkPerson,super.getRequestParams());
			vd = new ValidformData("y","添加成功！");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			vd = new ValidformData("n","添加失败！");
		}
		super.printObj(vd);
		return null;
	}
	
	@InitBinder("checkPerson")    
    public void initBinder(WebDataBinder binder) {    
    	binder.setFieldDefaultPrefix("checkPerson.");    
    }
}