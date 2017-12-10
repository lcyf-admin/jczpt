package cn.lhkj.project.equipment.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
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
import cn.lhkj.project.equipment.entity.Equipment;
import cn.lhkj.project.equipment.service.EquipmentService;
import cn.lhkj.project.hall.entity.Hall;
import cn.lhkj.project.hall.service.HallService;
import cn.lhkj.project.lane.entity.Lane;
import cn.lhkj.project.lane.service.LaneService;

/**设备管理 */
@Controller
@Scope("prototype")
public class EquipmentController extends BaseController {
	public static final Logger logger = Logger.getLogger(EquipmentController.class);
	
	private @Resource EquipmentService equipmentService;
	private @Resource LaneService laneService;
	private @Resource HallService hallService;
	private Equipment equipment;

	private String path = "equipment/equipment_";
	
	@ModelAttribute()
	public void prepare() throws Exception {
		String equipmentId = super.getParameter("equipment.id");
		if(StringUtil.isNull(equipmentId)) return;
		try {
			equipment = equipmentService.getEquipmentById(equipmentId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**获取车道设备列表*/
	@ResponseBody
	@RequestMapping(value="/equipment_ajaxOfLaneGrid")
	public String ajaxOfLaneGrid(){
		try {
			PageInfo pageInfo = equipmentService.getLaneEquipmentPage(super.getRequestParams(), super.getPageInfo());
			super.printObj(pageInfo);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}
	
	/**获取安检厅设备列表*/
	@ResponseBody
	@RequestMapping(value="/equipment_ajaxGridOfHall")
	public String ajaxGridOfHall(){
		try {
			PageInfo pageInfo = equipmentService.getHallEquipmentPage(super.getRequestParams(), super.getPageInfo());
			super.printObj(pageInfo);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}
	
	/**获取所有设备*/
	@ResponseBody
	@RequestMapping(value="/equipment_ajaxEquipmentList")
	public String ajaxEquipmentList(){
		try {
			super.printArray(equipmentService.listAll());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}
	
	/**保存设备*/
	@ResponseBody
	@RequestMapping(value="/equipment_ajaxSave")
	public String ajaxSave(Equipment equipment){
		try {
			equipmentService.saveEquipment(equipment);
			vd = new ValidformData("y","添加成功！");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			vd = new ValidformData("n","添加失败！");
		}
		super.printObj(vd);
		return null;
	}
	
	/**更新设备信息*/
	@ResponseBody
	@RequestMapping(value="/equipment_ajaxUpdate")
	public String ajaxUpdate(Equipment equipment){
		try {
			equipmentService.updateEquipment(equipment);
			vd = new ValidformData("y","更新成功！");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			vd = new ValidformData("n","更新失败！");
		}
		super.printObj(vd);
		return null;
	}
	
	/**删除设备*/
	@ResponseBody
	@RequestMapping(value="/equipment_ajaxRemove")
	public String ajaxRemove(){
		String equipmentId = super.getParameter("equipmentId");
		try {
			equipmentService.removeEquipment(equipmentId);
			super.printText(null);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}
	
	/**判断该设备编号是否存在*/
	@ResponseBody
	@RequestMapping(value="/equipment_ajaxExist")
	public String ajaxExist(){
		String equipmentId = super.getParameter("id");
		JSONObject jsonObj = new JSONObject();
		try {
			equipment = equipmentService.getEquipmentById(equipmentId);
			if(equipment == null){
				jsonObj.put("valid", true);
			}else{
				jsonObj.put("valid", false);
			}
			super.printText(jsonObj.toString());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}
	
	/**跳转到添加车道设备界面*/
	@RequestMapping("/equipment_addOfLane")
	public ModelAndView addOfLane(){
		ModelAndView mav = new ModelAndView(path+"addOfLane");
		try {
			List<Lane> laneList = laneService.listAll();
			mav.addObject("laneList", laneList);
		} catch (Exception e) {
			mav.addObject("laneList", new ArrayList<Lane>());
			logger.error(e.getMessage(), e);
		}
		return mav;
	}
	
	/**跳转到添加安检厅设备界面*/
	@RequestMapping("/equipment_addOfHall")
	public ModelAndView addOfHall(){
		ModelAndView mav = new ModelAndView(path+"addOfHall");
		try {
			List<Hall> hallList = hallService.listAll();
			mav.addObject("hallList", hallList);
		}catch (Exception e){
			mav.addObject("hallList", new ArrayList<Hall>());
			logger.error(e.getMessage(), e);
		}
		return mav;
	}
	
	@RequestMapping("/equipment_edit{param}")
	public ModelAndView edit(@PathVariable("param")String param) throws Exception{
		ModelAndView mav = new ModelAndView(path+"edit"+param);
		if(StringUtil.isNull(equipment.getLaneId())){
			List<Hall> hallList = hallService.listAll();
			mav.addObject("hallList",hallList);
		}else{
			List<Lane> laneList = laneService.listAll();
			mav.addObject("laneList",laneList);
		}
		mav.addObject("equipment", equipment);
		return mav;
	}
	
	@RequestMapping("/equipment_{param}")
    public String direction(@PathVariable("param")String param){
        return path+param;
    }
	/////////////////////////////////////////////////////////
	
	public Equipment getEquipment() {
		return equipment;
	}

	public void setEquipment(Equipment equipment) {
		this.equipment = equipment;
	}
}