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
import cn.lhkj.project.check.entity.CheckVehicle;
import cn.lhkj.project.check.entity.CheckVehiclePassenger;
import cn.lhkj.project.check.service.CheckVehicleService;

@Controller
@Scope("prototype")
public class CheckVehicleController extends BaseController{
	public static final Logger logger = Logger.getLogger(CheckVehicleController.class);
	
	private @Resource CheckVehicleService checkVehicleService;
	
	/**增加*/
	@ResponseBody
	@RequestMapping(value="/checkVehicle_ajaxAdd")
	public String ajaxAdd(@ModelAttribute CheckVehiclePassenger driver,@ModelAttribute CheckVehicle checkVehicle){
		try {
			checkVehicleService.addCheckVehicle(checkVehicle,driver,super.getRequestParams());
			vd = new ValidformData("y","添加成功！");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			vd = new ValidformData("n","添加失败！");
		}
		super.printObj(vd);
		return null;
	}
	
	@InitBinder("checkVehiclePassenger")    
    public void initBinder2(WebDataBinder binder) {    
    	binder.setFieldDefaultPrefix("driver.");    
    }
	
	@InitBinder("checkVehicle")    
    public void initBinder1(WebDataBinder binder) {    
		binder.setFieldDefaultPrefix("checkVehicle.");    
    }
}