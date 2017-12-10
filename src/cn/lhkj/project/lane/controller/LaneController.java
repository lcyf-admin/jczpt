package cn.lhkj.project.lane.controller;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.lhkj.commons.base.BaseController;
import cn.lhkj.commons.entity.PageInfo;
import cn.lhkj.commons.entity.ValidformData;
import cn.lhkj.commons.util.StringUtil;
import cn.lhkj.project.lane.entity.Lane;
import cn.lhkj.project.lane.service.LaneService;

@Controller
@Scope("prototype")
public class LaneController extends BaseController {
	
	private Lane lane;
	private @Resource LaneService laneService;
	
	private String path = "lane/lane_";

	@ModelAttribute()
	public void prepare() throws Exception {
		String laneId = super.getParameter("lane.id");
		if(StringUtil.isNull(laneId)) return;
		try {
			lane = laneService.getLaneById(laneId);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
	/**获取所有车道*/
	@ResponseBody
	@RequestMapping(value="/lane_ajaxLaneList")
	public String ajaxLaneList(){
		try {
			super.printArray(laneService.listAll());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**获取车道列表*/
	@ResponseBody
	@RequestMapping(value="/lane_ajaxGrid")
	public String ajaxGrid(){
		try {
			PageInfo pageInfo = laneService.getLanePage(super.getRequestParams(), super.getPageInfo());
			super.printObj(pageInfo);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}
	
	/**保存车道*/
	@ResponseBody
	@RequestMapping(value="/lane_ajaxSave")
	public String ajaxSave(Lane lane){
		try {
			laneService.saveLane(lane);
			vd = new ValidformData("y","添加成功！");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			vd = new ValidformData("n","添加失败！");
		}
		super.printObj(vd);
		return null;
	}
	
	/**更新车道信息*/
	@ResponseBody
	@RequestMapping(value="/lane_ajaxUpdate")
	public String ajaxUpdate(Lane lane){
		try {
			laneService.udpateLane(lane);
			vd = new ValidformData("y","更新成功！");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			vd = new ValidformData("n","更新失败！");
		}
		super.printObj(vd);
		return null;
	}
	
	/**删除车道*/
	@ResponseBody
	@RequestMapping(value="/lane_ajaxRemove")
	public String ajaxRemove(){
		String laneId = super.getParameter("laneId");
		try {
			laneService.removeLane(laneId);
			super.printText(SUCCESS);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}
	
	/**判断该车道编号是否存在*/
	@ResponseBody
	@RequestMapping(value="/lane_ajaxExist")
	public String ajaxExist(){
		String laneId = super.getParameter("id");
		JSONObject jsonObj = new JSONObject();
		try {
			lane = laneService.getLaneById(laneId);
			if(lane == null){
				jsonObj.put("valid", true);
			}else{
				jsonObj.put("valid", false);
			}
			super.printText(jsonObj.toString());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}
	
	///////////////////////////////////////////////////
	/**跳转到列表界面*/
	@RequestMapping(value="/lane_list")
	public String list(){
		return path + "list";
	}
	
	/**跳转到添加车道界面*/
	@RequestMapping(value="/lane_add")
	public String add(){
		return path + "add";
	}
	
	/**跳转到编辑页面*/
	@RequestMapping(value="/lane_edit")
	public ModelAndView edit(){
		ModelAndView mav = new ModelAndView(path+"edit");
		mav.addObject("lane", lane);
		return mav;
	}
	////////////////////////////////////////////////////
	public Lane getLane() {
		return lane;
	}

	public void setLane(Lane lane) {
		this.lane = lane;
	}
}