package cn.lhkj.project.vehicle.controller;

import java.io.InputStream;
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
import cn.lhkj.project.equipment.entity.Equipment;
import cn.lhkj.project.equipment.service.EquipmentService;
import cn.lhkj.project.lane.entity.Lane;
import cn.lhkj.project.lane.service.LaneService;
import cn.lhkj.project.transfer.entity.Transfer;
import cn.lhkj.project.vehicle.entity.Passenger;
import cn.lhkj.project.vehicle.entity.Vehicle;
import cn.lhkj.project.vehicle.service.VehicleService;

@Controller
@Scope("prototype")
public class VehicleController extends BaseController {
	public static final Logger logger = Logger.getLogger(VehicleController.class);
	
	private @Resource VehicleService vehicleService;
	private @Resource LaneService laneService;
	private @Resource EquipmentService equipmentService;
	
	private Vehicle vehicle;
	private String path = "vehicle/vehicle_";
	
	@ModelAttribute()
	public void prepare() throws Exception {
		String vehicleId = super.getParameter("vehicle.id");
		if(StringUtil.isNull(vehicleId)) return;
		vehicle = vehicleService.getVehicle(vehicleId);
	}
	
	/**车证合一数据采集*/
	@ResponseBody
	@RequestMapping(value="/vehicle_czhy")
	public String czhy(){
		String ip = super.getRequest().getRemoteAddr();
		boolean  isLegal= StringUtil.isLegal(ip);
		String data = null;
		if(isLegal){
			String relust = "";
			try {
				InputStream in = super.getRequest().getInputStream();
				data = StringUtil.convertStreamToString(in);
				logger.info("【"+ip+"】【"+data+"】");
				if(StringUtil.isNull(data)){
					relust = "参数为空";
				}else{
					relust = vehicleService.gather(data,ip);
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				Transfer t = new Transfer();
				t.setTranResult("400");
				t.setTranMsg("系统出错，请联系管理员！");
				relust = StringUtil.obj2json(t);
				logger.error("【"+ip+"】【"+e.getMessage()+"】");
			}
			if(relust.indexOf("\"tranResult\":\"400\"") != -1){
				logger.info("【"+ip+"】【"+relust+"】");
			}
			super.printText(relust);
		}else{
			super.printText("非法IP地址！");
			logger.info("【"+ip+"】非法IP地址调用vehicle/czhy.action接口");
		}
		return null;
	}
	
	/** 获取实时车辆信息列表 */
	@ResponseBody
	@RequestMapping(value="/vehicle_ajaxGrid")
	public String ajaxGrid() {
		try {
			PageInfo pageInfo = vehicleService.getPriorDataVehiclePage(super.getRequestParams(), super.getPageInfo());
			super.printObj(pageInfo);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}
	
	/**界面初始化显示时时车辆*/
	@ResponseBody
	@RequestMapping(value="/vehicle_ajaxVehicleList")
	public String ajaxVehicleList(){
		try {
			String records = super.getParameter("records");
			String stationId = super.getParameter("stationId");
			super.printArray(vehicleService.listVehicle(records,stationId));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**增加乘客*/
	@ResponseBody
	@RequestMapping(value="/vehicle_ajaxAddPassenger")
	public String ajaxAddPassenger(Passenger passenger) {
		try {
			vehicleService.savePassenger(passenger);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}
	
	/**删除乘客*/
	@ResponseBody
	@RequestMapping(value="/vehicle_ajaxDeletePassenger")
	public String ajaxDeletePassenger() {
		try {
			String id = super.getParameter("id");
			vehicleService.deletePassenger(id);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}
	//////////////////////////////////////////////////
	/**跳转到车辆list页面*/
	@RequestMapping(value="/vehicle_list")
	public String list(){
		return path + "list";
	}
	
	@RequestMapping(value="/vehicle_detail")
	public ModelAndView detail() throws Exception{
		ModelAndView mav = new ModelAndView(path+"detail");
		if(vehicle != null){
			List<Passenger> passengerList = vehicleService.getPassengerList(vehicle.getId());
			vehicle.setPassengerList(passengerList);
			mav.addObject("passengerList", passengerList);
			
			Equipment equipment = equipmentService.getEquipmentById(vehicle.getEquipmentId());
			if(equipment != null){
				Lane lane =laneService.getLaneById(equipment.getLaneId());
				vehicle.setLane(lane);
				mav.addObject("lane", lane);
				mav.addObject("equipment", equipment);
			}
		}
		mav.addObject("vehicle", vehicle);
		return mav;
	}
	
	@RequestMapping(value="/vehicle_editDetail")
	public ModelAndView editDetail() throws Exception{
		ModelAndView mav = new ModelAndView(path+"editDetail");
		String id = super.getParameter("id");
		Vehicle vehicle = vehicleService.getVehicle(id);
		if(vehicle != null){
			List<Passenger> passengerList = vehicleService.getPassengerList(vehicle.getId());
			vehicle.setPassengerList(passengerList);
			mav.addObject("passengerList",passengerList);
			
			Equipment equipment = equipmentService.getEquipmentById(vehicle.getEquipmentId());
			if(equipment != null){
				Lane lane =laneService.getLaneById(equipment.getLaneId());
				vehicle.setLane(lane);
				mav.addObject("lane",lane);
				mav.addObject("equipment",equipment);
			}
		}	
		mav.addObject("vehicle",vehicle);
		return mav;
	}
	
	/**跳转到车辆预警页面*/
	@RequestMapping(value="/vehicle_addPassenger")
	public ModelAndView addPassenger(){
		ModelAndView mav = new ModelAndView(path+"addPassenger");
		String id = super.getParameter("id");
		mav.addObject("id", id);
		return mav;
	}
	//////////////////////////////////////////////////
	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}
}
