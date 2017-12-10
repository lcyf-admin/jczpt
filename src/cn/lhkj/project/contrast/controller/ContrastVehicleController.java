package cn.lhkj.project.contrast.controller;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.lhkj.commons.base.BaseController;
import cn.lhkj.commons.entity.PageInfo;
import cn.lhkj.commons.util.StringUtil;
import cn.lhkj.project.check.entity.CheckVehicle;
import cn.lhkj.project.check.entity.CheckVehiclePassenger;
import cn.lhkj.project.check.service.CheckVehicleService;
import cn.lhkj.project.contrast.entity.ContrastVehicle;
import cn.lhkj.project.contrast.service.ContrastVehicleService;
import cn.lhkj.project.vehicle.entity.Vehicle;
import cn.lhkj.project.vehicle.service.VehicleService;

@Controller
@Scope("prototype")
public class ContrastVehicleController extends BaseController {
	private static final Logger logger = Logger.getLogger(ContrastVehicleController.class);
	
	private @Resource ContrastVehicleService contrastVehicleService;
	private @Resource VehicleService vehicleService;
	private @Resource CheckVehicleService checkVehicleService;
	
	private ContrastVehicle contrastVehicle;
	private String path = "contrast/contrast_";

	@ModelAttribute()
	public void prepare() throws Exception {
		String id = super.getParameter("contrastVehicle.id");
		if (StringUtil.isNull(id)) return;
		contrastVehicle = contrastVehicleService.getContrastVehicle(id);
	}
	
	/**获取预警车辆列表数据*/
	@ResponseBody
	@RequestMapping(value="/contrastVehicle_ajaxGrid")
	public String ajaxGrid(){
		try {
			PageInfo pageInfo = contrastVehicleService.getContrastVehiclePage(super.getRequestParams(), super.getPageInfo());
			super.printObjs(pageInfo);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}
	
	/**初始化首页时带入预警车辆信息 */
	@ResponseBody
	@RequestMapping(value="/contrastVehicle_ajaxContrastVehicleList")
	public String ajaxContrastVehicleList(){
		try {
			String records = super.getParameter("records");
			String stationId = super.getParameter("stationId");//站点编号
			super.printArray(contrastVehicleService.listContrastVehicle(records,stationId));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	///////////////////////////////////////////////////
	/**跳转到车辆预警信息页面 */
	@RequestMapping(value="/contrastVehicle_detail")
	public ModelAndView detail() throws Exception{
		ModelAndView mav = new ModelAndView(path+"vehicleDetail");
		if(contrastVehicle != null){
			Vehicle vehicle = vehicleService.getVehicle(contrastVehicle.getVehicleId());
			if(vehicle == null) vehicle = new Vehicle();
			if("1".equals(contrastVehicle.getIsChecked())){
				CheckVehicle checkVehicle = checkVehicleService.getCheckVehicleByTaskId(contrastVehicle.getId());
				if(checkVehicle == null) checkVehicle = new CheckVehicle();
				CheckVehiclePassenger driver = checkVehicleService.getDriver(checkVehicle.getId());
				List<CheckVehiclePassenger> passengerList = checkVehicleService.getPassengerList(checkVehicle.getId());
				if(passengerList == null) passengerList = new ArrayList<CheckVehiclePassenger>();
				
				mav.addObject("checkVehicle",checkVehicle);
				mav.addObject("driver",driver);
				mav.addObject("passengerList",passengerList);
			}
			mav.addObject("vehicle",vehicle);
		}
		mav.addObject("contrastVehicle",contrastVehicle);
		return mav;
	}
	
	/**跳转到列表界面 */
	@RequestMapping(value="/contrastVehicle_list")
	public String list() {
		return path + "vehicleList";
	}
	
	@RequestMapping(value="/contrastVehicle_check")
	public ModelAndView check() throws Exception{
		ModelAndView mav = new ModelAndView(path+"vehicleCheck"); 
		Vehicle vehicle = vehicleService.getVehicle(contrastVehicle.getVehicleId());
		if(vehicle == null) vehicle = new Vehicle();
		mav.addObject("vehicle",vehicle);
		mav.addObject("contrastVehicle",contrastVehicle);
		return mav;
	}
	///////////////////////////////////////////////////////
	public ContrastVehicle getContrastVehicle() {
		return contrastVehicle;
	}

	public void setContrastVehicle(ContrastVehicle contrastVehicle) {
		this.contrastVehicle = contrastVehicle;
	}
}