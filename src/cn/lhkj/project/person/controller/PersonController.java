package cn.lhkj.project.person.controller;

import java.io.InputStream;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import cn.lhkj.commons.base.BaseController;
import cn.lhkj.commons.entity.PageInfo;
import cn.lhkj.commons.entity.SessionBean;
import cn.lhkj.commons.entity.ValidformData;
import cn.lhkj.commons.util.StringUtil;
import cn.lhkj.project.equipment.entity.Equipment;
import cn.lhkj.project.equipment.service.EquipmentService;
import cn.lhkj.project.export.ExportUtil3;
import cn.lhkj.project.hall.entity.Hall;
import cn.lhkj.project.hall.service.HallService;
import cn.lhkj.project.person.entity.CheckPersonContraband;
import cn.lhkj.project.person.entity.Person;
import cn.lhkj.project.person.service.PersonService;

@Controller
@Scope("prototype")
public class PersonController extends BaseController {
	public static final Logger logger = Logger.getLogger(PersonController.class);
	
	private @Resource PersonService personService;
	private @Resource EquipmentService equipmentService;
	private @Resource HallService hallService;
	
	private Person person;
	private CheckPersonContraband checkPersonContraband;
	private String path = "person/person_";

	@ModelAttribute()
	public void prepare() throws Exception {
		String personId = super.getParameter("person.id");
		if(StringUtil.isNull(personId)) return;
		person = personService.getPerson(personId);
	}
	
	/**人证数据采集 0:通行  1:预警*/
	@ResponseBody
	@RequestMapping(value="/person_rzcj")
	public String rzcj(){
		String ip = super.getRequest().getRemoteAddr();
		boolean  isLegal= StringUtil.isLegal(ip);
		String data = null;
		String relust = "";//返回信息
		if(isLegal){
			try {
				InputStream in = super.getRequest().getInputStream();
				data = StringUtil.convertStreamToString(in);
				if(StringUtil.isNull(data)){
					relust = "参数为空";
				}else{
					relust = personService.gather(data,ip);
				}
			} catch (Exception e) {
				relust = e.getMessage();
				logger.error("【"+ip+"】【"+e.getMessage()+"】");
			}
			super.printText(relust);
		}else{
			super.printText("非法IP地址！");
			logger.info("【"+ip+"】非法IP地址调用person_rzcj接口");
		}
		return null;
	}
	
	/**获取实时人员信息列表 */
	@ResponseBody
	@RequestMapping(value="/person_ajaxGrid")
	public String ajaxGrid() {
		try {
			PageInfo pageInfo = personService.getPriorDataPersonePage(super.getRequestParams(), super.getPageInfo());
			super.printObjs(pageInfo);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}
	
	/** 判断列表是否存在 */
	@ResponseBody
	@RequestMapping(value="/person_ajaxExist")
	public String ajaxExist() {
		try {
			String num = personService.getCountPerson(super.getRequestParams());
			if(null == num || "0".equals(num) ) {
				super.printObjs(0);
			}else{
				super.printObjs(1);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}
	
	/** 导出查询的列表 */
	@ResponseBody
	@RequestMapping(value="/person_ajaxExport")
	public String ajaxExport() {
		HttpServletRequest request = getRequest();
			try {
				List<Person> result = personService.getPriorDataPerson(super.getRequestParams());
				request.setAttribute("__exportList", result);
				ExportUtil3.Export(request, getCurentResponse());
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
			return null;
	}
	
	/**界面初始化显示时时车辆*/
	@ResponseBody
	@RequestMapping(value="/person_ajaxPersonList")
	public String ajaxPersonList(){
		try {
			String records = super.getParameter("records");
			String stationId = super.getParameter("stationId");
			super.printArray(personService.listPerson(records,stationId));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}
	
	/**手动加入通行人员信息*/
	@ResponseBody
	@RequestMapping(value="/person_ajaxAddPerson")
	public String ajaxAddPerson(Person person){
		try {
			if (null != person) {
				HttpSession	session = super.getSession();
				SessionBean bean = (SessionBean)session.getAttribute(SESSION_BEAN);
				String station_id = bean.getStationId();
				if (StringUtil.isNotNull(person.getIdcard())) {
					Person newPerson = personService.addPerson(person,station_id);
					if (StringUtil.isNotNull(newPerson.getIdcard())) {
						personService.comparePerson(newPerson); //与大数据平台比对
					}
				}				
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}
	
	///////////////////////////////////////
	@RequestMapping(value="/person_detail")
	public ModelAndView detail() throws Exception{
		ModelAndView mav = new ModelAndView(path+"detail");
		if(person != null){
			Equipment equipment = equipmentService.getEquipmentById(person.getEquipmentId());
			if(equipment != null){
				Hall hall = hallService.getHallById(equipment.getHallId());
				person.setHall(hall);
				mav.addObject("hall", hall);
				mav.addObject("equipment", equipment);
			}
		}
		
		String personId = super.getParameter("person.id");
		if(person != null){
			person = personService.getPerson(personId);
			if(person == null) person = new Person();
			if(!"0".equals(person.getIsCheck())){
				checkPersonContraband = personService.getPersonContraband(personId);
				mav.addObject("checkPersonContraband", checkPersonContraband);
			}
		}	
		
		mav.addObject("person", person);
		return mav;
	}
	
	@RequestMapping(value="/person_contraband")
	public ModelAndView contraband() throws Exception{
		ModelAndView mav = new ModelAndView(path+"contraband");
		String personId = super.getParameter("person.id");
		mav.addObject("personId", personId);
		return mav;
	}
	
	/**增加违禁物品*/
	@ResponseBody
	@RequestMapping(value="/person_ajaxAdd",method= RequestMethod.POST)
	public String ajaxAdd(MultipartFile file,HttpServletRequest request){
		try {
			personService.addPersonContraband(file,request);
			vd = new ValidformData("y","添加成功！");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			vd = new ValidformData("n","添加失败！");
		}
		super.printObj(vd);
		return null;
	}
	
	/**跳转到车辆list页面*/
	@RequestMapping(value="/person_list")
	public String list(){
		return path + "list";
	}
	
	/**跳转到手动录入页面*/
	@RequestMapping(value="/person_manualData")
	public String manualData(){
		return path + "manualData";
	}
	///////////////////////////////////////
	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}
	
}
