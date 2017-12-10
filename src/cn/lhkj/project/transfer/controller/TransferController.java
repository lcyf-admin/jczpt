package cn.lhkj.project.transfer.controller;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.lhkj.commons.base.BaseController;
import cn.lhkj.commons.entity.PageInfo;
import cn.lhkj.commons.util.StringUtil;
import cn.lhkj.project.transfer.entity.Transfer;
import cn.lhkj.project.transfer.service.TransferService;

@Controller
@Scope("prototype")
public class TransferController extends BaseController{
	private @Resource TransferService transferService;
	
	private String path = "transfer/transfer_";
	private Transfer transfer;
	
	@ModelAttribute()
	public void prepare() throws Exception {
		String transferId = super.getParameter("transfer.id");
		if(StringUtil.isNull(transferId)) return;
		transfer = transferService.getTransfer(transferId);
	}
	
	
	/**获取用户列表数据*/
	@ResponseBody
	@RequestMapping(value="/transfer_ajaxGrid")
	public String ajaxGrid(){
		try {
			PageInfo pageInfo = transferService.getTransferPage(super.getRequestParams(), super.getPageInfo());
			super.printObj(pageInfo);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}
	
	
	///////////////////////////////////////////////////
	@RequestMapping(value="/transfer_list")
	public String list(){
		return path+"list";
	}
	
	@RequestMapping(value="/transfer_detail")
	public String detail(){
		return path+"detail";
	}
	//////////////////////////////////////////////////

	public Transfer getTransfer() {
		return transfer;
	}

	public void setTransfer(Transfer transfer) {
		this.transfer = transfer;
	}
}
