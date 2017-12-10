package cn.lhkj.project.check.service;


import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;


import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import cn.lhkj.commons.base.BaseService;
import cn.lhkj.commons.defined.Transactional;
import cn.lhkj.commons.entity.SessionBean;
import cn.lhkj.commons.tools.UUIDFactory;
import cn.lhkj.commons.util.StringUtil;
import cn.lhkj.project.check.entity.CheckVehicle;
import cn.lhkj.project.check.entity.CheckVehiclePassenger;
import cn.lhkj.project.system.entity.UserView;

@Service("checkVehicleService")
@Scope("prototype")
public class CheckVehicleServiceImpl extends BaseService implements CheckVehicleService {
	
	@Transactional
	@Override
	public void addCheckVehicle(CheckVehicle checkVehicle,CheckVehiclePassenger driver,
			Map<String, Object> requestParams) throws Exception {
		SessionBean bean = (SessionBean)requestParams.get("SESSION_BEAN");
		if(checkVehicle == null || bean == null) throw new NullPointerException();
		UserView user = bean.getUserView();
		checkVehicle.setId(UUIDFactory.getUUIDStr());
		checkVehicle.setAreaCode(bean.getStationId().substring(0, 6));
		checkVehicle.setStationId(bean.getStationId());
		checkVehicle.setStationName(user.getOrgName());
		checkVehicle.setCheckTime(new Date());
		checkVehicle.setRemark(user.getOrgDetailName());
		checkVehicle.setIsUpload("0");
		String relations = checkVehicle.getRelations();
		if (StringUtil.isNotNull(relations)) {
			checkVehicle.setExplains(checkVehicle.getRelationsView());
		}
		baseDao.save(checkVehicle);
		driver.setId(UUIDFactory.getUUIDStr());
		driver.setCheckVehicleId(checkVehicle.getId());
		baseDao.save(driver);
		Set<String>keySet = requestParams.keySet();
		for(String key : keySet){
			if (key.startsWith("passenger_idcard")) {
				String uuid = key.substring("passenger_idcard".length());
				CheckVehiclePassenger passenger = new CheckVehiclePassenger(
						UUIDFactory.getUUIDStr());
				passenger.setCheckVehicleId(checkVehicle.getId());
				passenger.setFinds(requestParams.get("passenger_finds" + uuid) == null ? null
								: requestParams.get("passenger_finds" + uuid).toString());
				passenger.setIdcard(requestParams.get("passenger_idcard" + uuid) == null ? null 
								: requestParams.get("passenger_idcard" + uuid).toString());
				passenger.setIsDriver("0");
				passenger.setIsDubious(requestParams.get("passenger_isDubious" + uuid) == null ? null
								: requestParams.get("passenger_isDubious" + uuid).toString());
				passenger.setMatch(requestParams.get("passenger_match" + uuid) == null ? null 
								: requestParams.get("passenger_match" + uuid).toString());
				passenger.setPhonenum(requestParams.get("passenger_phonenum" + uuid) == null ? null 
								: requestParams.get("passenger_phonenum" + uuid).toString());
				baseDao.save(passenger);
			}
		}
		String sql = "update CSL_CONTRAST_VEHICLE set IS_CHECKED='1' where id='"+checkVehicle.getTaskId()+"'";
		baseDao.execute(sql);
	}
	
	
	@Override
	public CheckVehicle getCheckVehicle(String id) throws Exception {
		return (CheckVehicle)baseDao.getEntity(CheckVehicle.class, id);
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public CheckVehicle getCheckVehicleByTaskId(String id) throws Exception {
		String hql = "from CheckVehicle t where t.taskId='"+id+"'";
		List<CheckVehicle> list = (List<CheckVehicle>)baseDao.findByHQL(hql);
		if(!StringUtil.isNull(list)){
			return  list.get(0);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public CheckVehiclePassenger getDriver(String checkVehicleId)
			throws Exception {
		String hql = "from CheckVehiclePassenger t where t.checkVehicleId='"+checkVehicleId+"' and t.isDriver='1'";
		List<CheckVehiclePassenger> l = (List<CheckVehiclePassenger>)baseDao.findByHQL(hql);
		if(!StringUtil.isNull(l)) return l.get(0);
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CheckVehiclePassenger> getPassengerList(String checkVehicleId)
			throws Exception {
		String hql = "from CheckVehiclePassenger t where t.checkVehicleId='"+checkVehicleId+"' and t.isDriver='0'";
		return (List<CheckVehiclePassenger>)baseDao.findByHQL(hql);
	}
	
}
