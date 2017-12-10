package cn.lhkj.project.log.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.lhkj.commons.base.BaseController;

@Scope("prototype")
public class LogController extends BaseController {

	private String path = "log/log_";


	//////////////////////////////////////
	@RequestMapping(value="/log_list")
	public String list() {
		return path + "list";
	}
	//////////////////////////////////////
}
