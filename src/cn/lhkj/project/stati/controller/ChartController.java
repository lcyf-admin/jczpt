package cn.lhkj.project.stati.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.lhkj.commons.base.BaseController;
import cn.lhkj.project.stati.service.ChartService;

@Controller
@Scope("prototype")
public class ChartController extends BaseController {
	public static final Logger logger = Logger.getLogger(ChartController.class);
	
	private @Resource ChartService ChartService;
	
	/**车道、安检设备数量*/
	@ResponseBody
	@RequestMapping(value="/chart_ajaxEquipment")
	public String ajaxEquipment(){
		try {
			Map<String,Object> map = ChartService.equipmentdata();
			super.printObj(map);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}
	
	/**车辆人员流量*/
	@ResponseBody
	@RequestMapping(value="/chart_ajaxTraffic")
	public String ajaxTraffic(){
		
		try {
			Map<String,Object> map = ChartService.trafficdata(super.getParameter("startsh"),super.getParameter("endstr"),super.getSessionBean());
			super.printObj(map);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

}