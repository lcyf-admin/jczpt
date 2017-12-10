package cn.lhkj.project.contrast.service;

import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import cn.lhkj.commons.base.BaseService;
import cn.lhkj.commons.entity.PageInfo;
import cn.lhkj.commons.entity.SessionBean;
import cn.lhkj.commons.tools.UUIDFactory;
import cn.lhkj.commons.util.StringUtil;
import cn.lhkj.project.contrast.entity.ContrastVehicle;

@Service("contrastVehicleService")
@Scope("prototype")
public class ContrastVehicleServiceImpl extends BaseService implements ContrastVehicleService {

	@SuppressWarnings("unchecked")
	@Override
	public List<ContrastVehicle> listContrastVehicle(String records,String stationId) throws Exception {
		String hql = "from ContrastVehicle where 1=1 and (instr(equipmentId, '"+stationId+"')>0) order by monthId desc, insertTime desc";
		List<ContrastVehicle> list = (List<ContrastVehicle>)baseDao.findByHQL(hql,0,Integer.parseInt(records));
		return list;
	}
	

	@Override
	public void removeContrastVehicle(String id) throws Exception {
		if(StringUtil.isNull(id)) return;
		String sql = "delete from CSL_CONTRAST_VEHICLE where id = '" + id + "' ";
		baseDao.execute(sql);
	}

	@Override
	public void saveContrastVehicle(ContrastVehicle data) throws Exception {
		if(data == null) throw new NullPointerException();
		if(StringUtil.isNull(data.getId())) data.setId(UUIDFactory.getUUIDStr());
		baseDao.save(data);
	}

	@Override
	public ContrastVehicle getContrastVehicle(String id) throws Exception {
		return (ContrastVehicle) baseDao.getEntity(ContrastVehicle.class, id);
	}
	
	public PageInfo getContrastVehiclePage(Map<String, Object> requestParams,
			PageInfo pageInfo) throws Exception {
		String hql =  "from ContrastVehicle where 1=1 ";
		String parmsMore = "";
		
		SessionBean se = (SessionBean)requestParams.get("SESSION_BEAN");
		if(se != null){//站点数据权限
			parmsMore += " and (instr(equipmentId, '"+se.getStationId()+"')>0)";//@@
		}
		
		if(requestParams.get("startdate") != null && !"".equals(requestParams.get("startdate"))){
			parmsMore += " and to_char(passdate,'yyyy-mm-dd hh24:mi:ss') >='"+requestParams.get("startdate")+"'";
		}
		if(requestParams.get("enddate") != null && !"".equals(requestParams.get("enddate"))){
			parmsMore += " and to_char(passdate,'yyyy-mm-dd hh24:mi:ss') <='"+requestParams.get("enddate")+"'";
		}
		if(requestParams.get("inputValue") != null ){//用户姓名用于查询
			String searchParam = requestParams.get("inputValue").toString();
			if(searchParam.length() > 0){
				parmsMore += " and (instr(carNum, '"+searchParam+"')>0 " +
				"or instr(names, '"+searchParam+"')>0 " +
			    "or instr(idcard, '"+searchParam+"')>0 " +
				"or instr(source, '"+searchParam+"')>0 " +
				"or instr(location, '"+searchParam+"')>0 " +
				"or instr(action, '"+searchParam+"')>0)";
			}
		}
		hql += parmsMore;
		hql += " order by passdate desc ";
		pageInfo = baseDao.findPageByHQL(pageInfo, hql);
		return pageInfo;
	}
	
}