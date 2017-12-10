package cn.lhkj.project.hall.controller;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import cn.lhkj.commons.base.BaseController;
import cn.lhkj.commons.entity.PageInfo;
import cn.lhkj.commons.entity.ValidformData;
import cn.lhkj.project.hall.entity.Hall;
import cn.lhkj.project.hall.service.HallService;

@Controller
@Scope("prototype")
public class HallController extends BaseController {

	private @Resource HallService hallService;
	private Hall hall;
	private String path = "hall/hall_";

	/** 获取所有安检大厅 */
	@ResponseBody
	@RequestMapping(value="/hall_ajaxHallList")
	public String ajaxHallList() {
		try {
			super.printArray(hallService.listAll());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**获取安检厅列表 */
	@ResponseBody
	@RequestMapping(value="/hall_ajaxGrid")
	public String ajaxGrid() {
		try {
			PageInfo pageInfo = hallService.getHallPage(super.getRequestParams(), super.getPageInfo());
			super.printObj(pageInfo);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}

	/** 保存安检厅 */
	@ResponseBody
	@RequestMapping(value="/hall_ajaxSave")
	public String ajaxSave(Hall hall) {
		try {
			hallService.saveHall(hall);
			vd = new ValidformData("y","添加成功！");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			vd = new ValidformData("n","添加失败！");
		}
		super.printObj(vd);
		return null;
	}

	/** 更新安检厅信息 */
	@ResponseBody
	@RequestMapping(value="/hall_ajaxUpdate")
	public String ajaxUpdate(Hall hall) {
		try {
			hallService.udpateHall(hall);
			vd = new ValidformData("y","更新成功！");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			vd = new ValidformData("y","更新成功！");
		}
		super.printObj(vd);
		return null;
	}

	/** 删除安检厅 */
	@ResponseBody
	@RequestMapping(value="/hall_ajaxRemove")
	public String ajaxRemove(String hallId) {
		try {
			hallService.removeHall(hallId);
			super.printText(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**跳转到列表界面 */
	@RequestMapping(value="/hall_list")
	public String list() {
		return path + "list";
	}

	/**跳转到添加安检厅界面 */
	@RequestMapping(value="/hall_add")
	public String add() {
		return path + "add";
	}

	/** 跳转到编辑页面 */
	@RequestMapping(value="/hall_edit")
	public ModelAndView edit(String hallId) {
		ModelAndView mav = new ModelAndView(path+"edit");
		try {
			hall = hallService.getHallById(hallId);
			mav.addObject("hall", hall);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return mav;
	}

	public Hall getHall() {
		return hall;
	}

	public void setHall(Hall hall) {
		this.hall = hall;
	}
}