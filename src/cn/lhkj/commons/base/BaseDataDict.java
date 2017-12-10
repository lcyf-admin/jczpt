package cn.lhkj.commons.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import cn.lhkj.commons.util.StringUtil;
import cn.lhkj.project.system.entity.Dict;
import cn.lhkj.project.system.entity.DictItem;

/**数据字典集合 */
public class BaseDataDict {
	
	private static final Logger logger = Logger.getLogger(BaseDataDict.class);
	
	/**key对应数据字典ID，value对应数据字典项列表  */
	public static Map<String,List<DictItem>> dictMap;
	
	/**key对应数据字典ID，value对应数据字典项map  */
	public static Map<String,Map<String,String>> dictItemMap;
	
	@SuppressWarnings("unchecked")
	public static void init(){
		BaseDao baseDao = null;
		try {
			dictMap = new HashMap<String, List<DictItem>>();
			dictItemMap = new HashMap<String, Map<String,String>>();
			baseDao = new BaseDao();
			List<Dict> dictList = (List<Dict>)baseDao.findByHQL("from Dict where 1=1 order by names");
			if(!StringUtil.isNull(dictList)){
				for(Dict dict : dictList){
					List<DictItem> itemList = (List<DictItem>)baseDao.findByHQL("from DictItem where dictId='"+dict.getId()+"' order by ranking");
					dictMap.put(dict.getId(), itemList);
					if(!StringUtil.isNull(itemList)){
						Map<String,String> m = new HashMap<String, String>();
						for(DictItem t : itemList){
							m.put(t.getCodes(), t.getOptions());
						}
						dictItemMap.put(dict.getId(), m);
					}
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}finally{
			if(baseDao != null){ baseDao.close();}
		}
	}

}
