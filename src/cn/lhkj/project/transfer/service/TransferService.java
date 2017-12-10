package cn.lhkj.project.transfer.service;

import java.util.Map;

import cn.lhkj.commons.entity.PageInfo;
import cn.lhkj.project.transfer.entity.Transfer;
public interface TransferService {
	
	public Transfer getTransfer(String id) throws Exception;
	
	/**
	 * 获取接口调用记录
	 * @param requestParams 查询条件
	 * @param pageInfo Grid基本参数
	 * @return
	 * @throws Exception
	 */
	public PageInfo getTransferPage(Map<String, Object> requestParams,PageInfo pageInfo)throws Exception;
	
}
