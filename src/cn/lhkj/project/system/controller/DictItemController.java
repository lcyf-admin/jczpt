package cn.lhkj.project.system.controller;


import javax.annotation.Resource;
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
import cn.lhkj.project.system.entity.DictItem;
import cn.lhkj.project.system.service.DictItemService;
import cn.lhkj.project.system.service.DictService;


@Controller
@Scope("prototype")
public class DictItemController extends BaseController{
	
	private @Resource DictItemService dictItemService;
	private @Resource DictService dictService;
	private String path = "dict/dictItem_";
	
	@ModelAttribute()
	public void prepare() throws Exception{
		super.mav = new ModelAndView();
		String dictItemId = super.getParameter("dictItem.id");
		if(!StringUtil.isNull(dictItemId)){
			DictItem dictItem = dictItemService.getDictItem(dictItemId);
			mav.addObject("dictItem", dictItem);
		}
		String dictId = super.getParameter("dict.id");
		if(!StringUtil.isNull(dictId)){
			Dict dict = dictService.getDict(dictId);
			mav.addObject("dict", dict);
		}
	}
	
	/**获取数据字典列表数据*/
	@ResponseBody
	@RequestMapping(value="/dictItem_ajaxGrid")
	public String ajaxGrid(){
		try {
			PageInfo pageInfo = dictItemService.getDictItemPage(super.getRequestParams(), super.getPageInfo());
			super.printObj(pageInfo);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}
	
	/**增加数据字典*/
	@ResponseBody
	@RequestMapping(value="/dictItem_ajaxAdd")
	public String ajaxAdd(DictItem dictItem){
		try {
			dictItemService.addDictItem(dictItem);
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
	@RequestMapping(value="/dictItem_ajaxUpdate")
	public String ajaxUpdate(DictItem dictItem){
		try {
			dictItemService.updateDictItem(dictItem);
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
	@RequestMapping(value="/dictItem_ajaxDelete")
	public String ajaxDelete(){
		try {
			String dictItemId = super.getParameter("dictItemId");
			dictItemService.deleteDictItem(dictItemId);
			super.printText(SUCCESS);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}
	
	/**
	 * 非ajax请求调转到/WEB-INF/pages/{param}.jsp页面
	 * 参考springmvc-servlet.xml的配置
	 */
	@RequestMapping("/dictItem_{param}")
    public ModelAndView userDirection(@PathVariable("param")String param){
		mav.setViewName(path+param);
        return mav;
    }
}
