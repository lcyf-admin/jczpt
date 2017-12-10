package cn.lhkj.project.system.service;

import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import cn.lhkj.commons.base.BaseDataDict;
import cn.lhkj.commons.base.BaseService;
import cn.lhkj.commons.entity.PageInfo;
import cn.lhkj.commons.tools.UUIDFactory;
import cn.lhkj.commons.util.StringUtil;
import cn.lhkj.project.system.entity.DictItem;

@Service("dictItemService")
@Scope("prototype")
public class DictItemServiceImpl extends BaseService implements DictItemService {

	@Override
	public DictItem getDictItem(String id) throws Exception {
		return (DictItem)baseDao.getEntity(DictItem.class, id);
	}
	
	@Override
	public PageInfo getDictItemPage(Map<String, Object> requestParams,
			PageInfo pageInfo) throws Exception {
		String hql =  "from DictItem where 1=1 " + StringUtil.getSQLByRequest(requestParams, DictItem.class);
		hql += "order by ranking";
		pageInfo = baseDao.findPageByHQL(pageInfo, hql);
		return pageInfo;
	}
	
	@Override
	public void addDictItem(DictItem dictItem) throws Exception {
		if(dictItem == null) throw new NullPointerException();
		dictItem.setId(UUIDFactory.getUUIDStr());
		baseDao.save(dictItem);
		BaseDataDict.init();
	}
	
	@Override
	public void updateDictItem(DictItem dictItem) throws Exception {
		if(dictItem == null) throw new NullPointerException();
		baseDao.update(dictItem);
		BaseDataDict.init();
	}
	
	@Override
	public void deleteDictItem(String id) throws Exception {
		if(StringUtil.isNull(id)) return;
		String sql = "delete from CSL_DICT_ITEM where id='"+id+"'";
		baseDao.execute(sql);
		BaseDataDict.init();
	}
}
