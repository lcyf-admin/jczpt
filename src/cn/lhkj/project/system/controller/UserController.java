package cn.lhkj.project.system.controller;
import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import net.sf.json.JSONObject;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import cn.lhkj.commons.base.BaseController;
import cn.lhkj.commons.base.BaseDataCode;
import cn.lhkj.commons.entity.PageInfo;
import cn.lhkj.commons.entity.SessionBean;
import cn.lhkj.commons.entity.ValidformData;
import cn.lhkj.commons.util.StringUtil;
import cn.lhkj.project.system.entity.Orgzon;
import cn.lhkj.project.system.entity.User;
import cn.lhkj.project.system.entity.UserView;
import cn.lhkj.project.system.service.OrgzonService;
import cn.lhkj.project.system.service.UserService;

@Controller
@Scope("prototype")
public class UserController extends BaseController{
	
	private @Resource UserService userService;
	private @Resource OrgzonService orgzonService;
	
	private String path = "user/user_";
	private User user;
	private UserView userView;
	private ModelAndView mav;

	@ModelAttribute()
	public void prepare() throws Exception {
		mav = new ModelAndView();
		String userId = super.getParameter("user.id");
		if(StringUtil.isNull(userId)) return;
		try {
			userView = userService.getUserView(userId);
			user = userService.getUser(userView);
			mav.addObject("userView", userView);
			mav.addObject("user", user);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
	/**注销登录 */
	@ResponseBody
	@RequestMapping(value="/logout")
	public String logout(){
		try {
			userService.logout(super.getSession());
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return null;
	}
	
	/** 登录跳转到主页面 */
	@ResponseBody
	@RequestMapping(value="/ajaxLogin")
	public String ajaxLogin(){
		vd = new ValidformData();
		try {
			String username = super.getParameter("account");//登录账号
			String password = super.getParameter("password");//登录密码
			vd = userService.login(username, password, vd , super.getSession());
			SessionBean bean = super.getSessionBean();
			if (bean != null) {
				String area = "";
				if (StringUtil.isNotNull(bean.getStationId())) {
					area = bean.getStationId().substring(0, 4);
				}
				if (area.indexOf("6531") == -1) {
					vd.setPackagePath("index1");
				}else{
					vd.setPackagePath("index"+BaseDataCode.indexStyle);
				}				
			}
		}catch (Exception e) {
			log.error(e.getMessage(),e);
			vd = new ValidformData("n","登录失败！");
			vd.setPackagePath("login");
		}
		super.printObj(vd);
		return null;
	}
	
	/** 单点登陆 */
	@RequestMapping(value="/logon")
	public String logon(){
		try {
			String oid = super.getParameter("oid");//登录账号
			boolean b = userService.logon(oid, super.getSession());
			if(b){
				return "redirect:index"+BaseDataCode.indexStyle;
			}else{
				return "redirect:login";			
			}
		}catch (Exception e) {
		}
		return "redirect:login";	
	}
	
	/**获取用户列表数据*/
	@ResponseBody
	@RequestMapping(value="/user_ajaxGrid")
	public String ajaxGrid(){
		try {
			PageInfo pageInfo = userService.getUserViewPage(super.getRequestParams(), super.getPageInfo());
			super.printObj(pageInfo);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}
	
	/**增加用户*/
	@ResponseBody
	@RequestMapping(value="/user_ajaxAdd")
	public String ajaxAdd(User user){
		try {
			userService.addUser(user);
			vd = new ValidformData("y","添加成功！");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			vd = new ValidformData("n","添加失败！");
		}
		super.printObj(vd);
		return null;
	}
	
	/**更新用户*/
	@ResponseBody
	@RequestMapping(value="/user_ajaxUpdate")
	public String ajaxUpdate(User user){
		try {
			userService.updateUser(user);
			vd = new ValidformData("y","更新成功！");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			vd = new ValidformData("n","更新失败！");
		}
		super.printObj(vd);
		return null;
	}
	
	
	/**删除用户*/
	@ResponseBody
	@RequestMapping(value="/user_ajaxDelete")
	public String ajaxDelete(){
		try {
			String userId = super.getParameter("userId");
			userService.deleteUser(userId);
			super.printText(SUCCESS);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}
	
	/**判断用户账号是否存在*/
	@ResponseBody
	@RequestMapping(value="/user_ajaxExist")
	public String ajaxExist(){
		String account = super.getParameter("account");
		JSONObject jsonObj = new JSONObject();
		try {
			boolean exist = userService.existUser(account);
			if(exist == false){
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
	
	/**
	 * 系统管理员选择站点
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/user_ajaxUpdateSession")
	public String ajaxUpdateSession(){
		try {
			HttpSession	session = super.getSession();
			SessionBean bean = (SessionBean)session.getAttribute(SESSION_BEAN);
			String orgId = super.getParameter("orgId");
			Orgzon org = orgzonService.getOrgzon(orgId);
			bean.getUserView().setOrgId(orgId);
			bean.getUserView().setOrgCodes(org.getCodes());
			bean.getUserView().setOrgName(org.getNames());
			bean.getUserView().setPrio(org.getPrio());
			session.setAttribute("SESSION_BEAN", bean);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}
	
	/**
	 * 非ajax请求调转到/WEB-INF/pages/{param}.jsp页面
	 * 参考springmvc-servlet.xml的配置
	 */
	@RequestMapping("/user_{param}")
    public ModelAndView userDirection(@PathVariable("param")String param){
		mav.setViewName(path+param);
        return mav;
    }
	
	@RequestMapping("/index")
    public String index(){
		return  "index"+BaseDataCode.indexStyle;
    }
	
	@RequestMapping("/index{param}")
    public String index(@PathVariable("param")String param){
		if("1".equals(param) || "2".equals(param) || "3".equals(param) || "4".equals(param) ){
			BaseDataCode.indexStyle = param;
		}
		return  "redirect:index.do";
    }
	
	
	@RequestMapping("/manage")
    public String manage(){
        return "manage";
    }
	
	@RequestMapping("/statistics")
    public String statistics(){
        return "statistics";
    }
	
	@RequestMapping("/home")
    public String home(){
        return "home";
    }
	
	@RequestMapping("/login")
    public String login(){
        return "login";
    }
	//////////////////////////////////////////////////
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public UserView getUserView() {
		return userView;
	}

	public void setUserView(UserView userView) {
		this.userView = userView;
	}

	public ModelAndView getMav() {
		return mav;
	}

	public void setMav(ModelAndView mav) {
		this.mav = mav;
	}
}