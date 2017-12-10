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
import cn.lhkj.project.check.entity.CheckPerson;
import cn.lhkj.project.check.entity.CheckPersonPeers;
import cn.lhkj.project.check.entity.CheckPersonPeersVehicle;
import cn.lhkj.project.check.service.CheckPersonService;
import cn.lhkj.project.contrast.entity.ContrastPerson;
import cn.lhkj.project.contrast.service.ContrastPersonService;

@Controller
@Scope("prototype")
public class ContrastPersonController extends BaseController {
	private static final Logger logger = Logger.getLogger(ContrastPersonController.class);
	
	private @Resource ContrastPersonService contrastPersonService;
	private @Resource CheckPersonService checkPersonService;
	
	private ContrastPerson contrastPerson;
	private String path = "contrast/contrast_";

	@ModelAttribute()
	public void prepare() throws Exception {
		String id = super.getParameter("contrastPerson.id");
		if (StringUtil.isNull(id)) return;
		contrastPerson = contrastPersonService.getContrastPerson(id);
	}
	
	/**获取预警人员列表数据*/
	@ResponseBody
	@RequestMapping(value="/contrastPerson_ajaxGrid")
	public String ajaxGrid(){
		try {
			PageInfo pageInfo = contrastPersonService.getContrastPersonPage(super.getRequestParams(), super.getPageInfo());
			super.printObjs(pageInfo);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}
	
	/**初始化首页时带入预警车辆信息 */
	@ResponseBody
	@RequestMapping(value="/contrastPerson_ajaxContrastPersonList")
	public String ajaxContrastPersonList(){
		try {
			String records = super.getParameter("records");
			String stationId = super.getParameter("stationId");
			super.printArray(contrastPersonService.listContrastPerson(records,stationId));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}
	
	/**跳转到人员预警详情信息页面  */
	@RequestMapping(value="/contrastPerson_detail")
	public ModelAndView detail() throws Exception{
		ModelAndView mav = new ModelAndView(path+"personDetail");
		if(contrastPerson != null){
			if("1".equals(contrastPerson.getIsChecked())){
				CheckPerson checkPerson = checkPersonService.getCheckPersonByTaskId(contrastPerson.getId());
				if(checkPerson == null) checkPerson = new CheckPerson();
				List<CheckPersonPeers> peersList = checkPersonService.getPeersList(checkPerson.getId());
				if(peersList == null) peersList = new ArrayList<CheckPersonPeers>();
				
				List<CheckPersonPeersVehicle> peersVehicleList = checkPersonService.getPeersVehicleList(checkPerson.getId());
				if(peersVehicleList == null) peersVehicleList = new ArrayList<CheckPersonPeersVehicle>();
				mav.addObject("checkPerson",checkPerson);
				mav.addObject("peersList",peersList);
				mav.addObject("peersVehicleList",peersVehicleList);
			}
		}
		mav.addObject("contrastPerson",contrastPerson);
		return mav;
	}
	
	/**跳转到列表界面 */
	@RequestMapping(value="/contrastPerson_list")
	public String list() {
		return path+"personList";
	}
	
	@RequestMapping(value="/contrastPerson_check")
	public ModelAndView check() throws Exception{
		ModelAndView mav = new ModelAndView(path+"personCheck");
		mav.addObject("contrastPerson",contrastPerson);
		return mav;
	}
	///////////////////////////////////////////////////////
	public ContrastPerson getContrastPerson() {
		return contrastPerson;
	}

	public void setContrastPerson(ContrastPerson contrastPerson) {
		this.contrastPerson = contrastPerson;
	}
}