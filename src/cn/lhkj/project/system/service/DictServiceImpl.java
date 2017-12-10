package cn.lhkj.project.system.service;

import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import cn.lhkj.commons.base.BaseDataDict;
import cn.lhkj.commons.base.BaseService;
import cn.lhkj.commons.entity.PageInfo;
import cn.lhkj.commons.util.StringUtil;
import cn.lhkj.project.system.entity.Dict;

@Service("dictService")
@Scope("prototype")
public class DictServiceImpl extends BaseService implements DictService {

	@Override
	public Dict getDict(String id) throws Exception {
		return (Dict)baseDao.getEntity(Dict.class, id);
	}
	
	@Override
	public PageInfo getDictPage(Map<String, Object> requestParams,
			PageInfo pageInfo) throws Exception {
		String hql =  "from Dict where 1=1 order by names";
		pageInfo = baseDao.findPageByHQL(pageInfo, hql);
		return pageInfo;
	}
	
	@Override
	public void addDict(Dict dict) throws Exception {
		if(dict == null) throw new NullPointerException();
		baseDao.save(dict);
		BaseDataDict.init();
	}
	
	@Override
	public void updateDict(Dict dict) throws Exception {
		if(dict == null) throw new NullPointerException();
		baseDao.update(dict);
		BaseDataDict.init();
	}
	
	@Override
	public void deleteDict(String id) throws Exception {
		if(StringUtil.isNull(id)) return;
		String sql = "delete from CSL_DICT where id='"+id+"'";
		baseDao.execute(sql);
		sql = "delete from CSL_DICT_ITEM where DICT_ID='"+id+"'";
		baseDao.execute(sql);
		BaseDataDict.init();
	}
}
