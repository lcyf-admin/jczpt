package cn.lhkj.project.lane.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import cn.lhkj.commons.base.BaseDataCode;
import cn.lhkj.commons.base.BaseService;
import cn.lhkj.commons.entity.PageInfo;
import cn.lhkj.commons.util.StringUtil;
import cn.lhkj.project.lane.entity.Lane;

@Service("laneService")
@Scope("prototype")
public class LaneServiceImpl extends BaseService implements LaneService {

	@SuppressWarnings("unchecked")
	@Override
	public List<Lane> listAll() throws Exception {
		String hql = "from Lane where 1=1 order by ranking";
		return (List<Lane>)baseDao.findByHQL(hql);
	}

	@Override
	public PageInfo getLanePage(Map<String, Object> requestParams,
			PageInfo pageInfo) throws Exception {
		String sql = "select l.id as ID, l.lanename as LANENAME,l.LANETYPE as LANETYPE, l.RANKING as RANKING "
				+ ",'<button type=\"button\" onclick=\"editLane(''' || l.ID || ''');\" class=\"btn btn-link btn-xs\">编辑</button>' "
				+ "|| '<button type=\"button\" onclick=\"deleteLane(''' || l.ID || ''');\" class=\"btn btn-link btn-xs\">删除</button>' as OPT "
				+ "from CSL_LANE l " + "where 1=1 ";
		String parmsMore = "";
		if(requestParams.get("inputValue") != null ){//车道名称用于查询
			String lanename = requestParams.get("inputValue").toString();
			if(lanename.length() > 0){
				parmsMore += " and (l.lanename like '%" + lanename +"%')";
			}
		}
		sql += parmsMore + " order by l.RANKING ";
		pageInfo = baseDao.findPageBySQL(pageInfo, sql);
		return pageInfo;
	}

	@Override
	public void removeLane(String id) throws Exception {
		if(StringUtil.isNull(id)) throw new NullPointerException("删除的车道ID为空！");
		baseDao.execute("delete from CSL_LANE where id='" + id + "'");
		updateBase();//更新静态内存车道类型
	}

	@Override
	public void saveLane(Lane lane) throws Exception {
		if(null == lane) return;
//		lane.setId(UUIDFactory.getUUIDStr());
		baseDao.save(lane);
		updateBase();//更新静态内存车道类型
	}

	@Override
	public Lane getLaneById(String id) throws Exception {
		return (Lane) baseDao.getEntity(Lane.class, id);
	}

	@Override
	public void udpateLane(Lane lane) throws Exception {
		if(null == lane) return;
		baseDao.update(lane);
		updateBase();//更新静态内存车道类型
	}
	
	@SuppressWarnings("unchecked")
	private void updateBase() {
		//更新静态内存车道类型
		List<Lane> res;
		try {
			res = (List<Lane>)baseDao.findByHQL("from Lane where 1=1 order by id");
			if(!StringUtil.isNull(res)){
				BaseDataCode.wayTypeMap.clear();
				BaseDataCode.showwayType.clear();
				BaseDataCode.wayTypeMap = new HashMap<String,List<String>>();
				BaseDataCode.showwayType = new ArrayList<String>();
				for(Lane lane : res){
					if (StringUtil.isNull(BaseDataCode.wayTypeMap.get(lane.getLaneType()))) {
						ArrayList<String> laneList = new ArrayList<String>();
						laneList.add(lane.getId());
						BaseDataCode.wayTypeMap.put(lane.getLaneType(), laneList);
					}else{
						BaseDataCode.wayTypeMap.get(lane.getLaneType()).add(lane.getId());
					}

					if(!StringUtil.isNull(StringUtil.trim(lane.getLaneName()))){
						BaseDataCode.showwayType.add(lane.getLaneType());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}