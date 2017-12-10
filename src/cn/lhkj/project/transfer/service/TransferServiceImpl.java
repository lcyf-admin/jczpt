package cn.lhkj.project.transfer.service;


import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import cn.lhkj.commons.base.BaseService;
import cn.lhkj.commons.entity.PageInfo;
import cn.lhkj.commons.util.StringUtil;
import cn.lhkj.project.transfer.entity.Transfer;

@Service("transferService")
@Scope("prototype")
public class TransferServiceImpl extends BaseService implements TransferService {
	
	@Override
	public Transfer getTransfer(String id) throws Exception {
		return (Transfer)baseDao.getEntity(Transfer.class, id);
	}
	
	@Override
	public PageInfo getTransferPage(Map<String, Object> requestParams,
			PageInfo pageInfo) throws Exception {
		String hql =  "from Transfer where 1=1 " + StringUtil.getSQLByRequest(requestParams, Transfer.class);;
		String parmsMore = "";
		if(requestParams.get("inputValue") != null ){//用户姓名用于查询
			String searchParam = requestParams.get("inputValue").toString();
			if(searchParam.length() > 0){
				parmsMore += " and (instr(ver, '"+searchParam+"')>0 " +
			    "or instr(tranType, '"+searchParam+"')>0 " +
				"or instr(tranResult, '"+searchParam+"')>0 " +
				"or instr(tranMsg, '"+searchParam+"')>0)";
			}
		}
		hql += parmsMore;
		hql += " order by sendTime desc";
		pageInfo = baseDao.findPageByHQL(pageInfo, hql);
		return pageInfo;
	}
}
