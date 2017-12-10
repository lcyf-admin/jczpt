package cn.lhkj.project.system.controller;

import javax.annotation.Resource;
import net.sf.json.JSONObject;
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
import cn.lhkj.project.system.entity.Dict;
import cn.lhkj.project.system.service.DictService;


@Controller("dictAction")
@Scope("prototype")
public class DictController extends BaseController{
	
	private @Resource DictService dictService;
	private String path = "dict/dict_";
	
	@ModelAttribute()
	public void prepare(){
		super.mav = new ModelAndView();
		String dictId = super.getParameter("dict.id");
		if(StringUtil.isNull(dictId)) return;
		try {
			Dict dict = dictService.getDict(dictId);
			mav.addObject("dict", dict);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
	/**获取数据字典列表数据*/
	@ResponseBody
	@RequestMapping(value="/dict_ajaxGrid")
	public String ajaxGrid(){
		try {
			PageInfo pageInfo = dictService.getDictPage(super.getRequestParams(), super.getPageInfo());
			super.printObj(pageInfo);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}
	
	/**增加数据字典*/
	@ResponseBody
	@RequestMapping(value="/dict_ajaxAdd")
	public String ajaxAdd(Dict dict){
		try {
			dictService.addDict(dict);
			vd = new ValidformData("y","添加成功！");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			vd = new ValidformData("n","添加失败！");
		}
		super.printObj(vd);
		return null;
	}
	
	/**更新数据字典*/
	@ResponseBody
	@RequestMapping(value="/dict_ajaxUpdate")
	public String ajaxUpdate(Dict dict){
		try {
			dictService.updateDict(dict);
			vd = new ValidformData("y","更新成功！");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			vd = new ValidformData("n","更新失败！");
		}
		super.printObj(vd);
		return null;
	}
	
	/**删除数据字典*/
	@ResponseBody
	@RequestMapping(value="/dict_ajaxDelete")
	public String ajaxDelete(){
		try {
			String dictId = super.getParameter("dictId");
			dictService.deleteDict(dictId);
			super.printText(SUCCESS);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}
	
	/**判断数据字典编号是否存在*/
	@ResponseBody
	@RequestMapping(value="/dict_ajaxExist")
	public String ajaxExist(){
		String dictId = super.getParameter("id");
		JSONObject jsonObj = new JSONObject();
		try {
			Dict dict = dictService.getDict(dictId);
			if(dict == null){
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
	 * 非ajax请求调转到/WEB-INF/pages/{param}.jsp页面
	 * 参考springmvc-servlet.xml的配置
	 */
	@RequestMapping("/dict_{param}")
    public ModelAndView userDirection(@PathVariable("param")String param){
		mav.setViewName(path+param);
        return mav;
    }
}
