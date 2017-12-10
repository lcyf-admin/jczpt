package cn.lhkj.project.hall.service;

import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import cn.lhkj.commons.base.BaseService;
import cn.lhkj.commons.entity.PageInfo;
import cn.lhkj.commons.tools.UUIDFactory;
import cn.lhkj.commons.util.StringUtil;
import cn.lhkj.project.hall.entity.Hall;

@Service("hallService")
@Scope("prototype")
public class HallServiceImpl extends BaseService implements HallService {

	@SuppressWarnings("unchecked")
	@Override
	public List<Hall> listAll() throws Exception {
		String hql = "from Hall order by ranking";
		return (List<Hall>) baseDao.findByHQL(hql);
	}

	@Override
	public PageInfo getHallPage(Map<String, Object> requestParams,
			PageInfo pageInfo) throws Exception {
		String sql = "select h.id as ID, h.hallname as HALLNAME, h.RANKING as RANKING "
				+ ",'<button type=\"button\" onclick=\"editHall(''' || h.ID || ''');\" class=\"btn btn-link btn-xs\">编辑</button>' "
				+ "|| '<button type=\"button\" onclick=\"deleteHall(''' || h.ID || ''');\" class=\"btn btn-link btn-xs\">删除</button>' as OPT "
				+ "from CSL_HALL h " + "where 1=1 ";
		String parmsMore = "";
		if(requestParams.get("inputValue") != null ){//安检厅名称用于查询
			String hallname = requestParams.get("inputValue").toString();
			if(hallname.length() > 0){
				parmsMore += " and (h.hallname like '%" + hallname +"%')";
			}
		}
		sql += parmsMore + " order by h.RANKING ";
		pageInfo = baseDao.findPageBySQL(pageInfo, sql);
		return pageInfo;
	}

	@Override
	public void removeHall(String id) throws Exception {
		if(StringUtil.isNull(id)) throw new NullPointerException("删除的安检厅ID为空！");
		baseDao.execute("delete from CSL_HALL where id='" + id + "'");
	}

	@Override
	public void saveHall(Hall hall) throws Exception {
		if(null == hall) return;
		hall.setId(UUIDFactory.getUUIDStr());
		baseDao.save(hall);
	}

	@Override
	public Hall getHallById(String id) throws Exception {
		return (Hall) baseDao.getEntity(Hall.class, id);
	}

	@Override
	public void udpateHall(Hall hall) throws Exception {
		if(null == hall) return;
		baseDao.update(hall);
	}
}