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
import cn.lhkj.project.check.entity.CheckPerson;
import cn.lhkj.project.check.entity.CheckPersonPeers;
import cn.lhkj.project.check.entity.CheckPersonPeersVehicle;
import cn.lhkj.project.contrast.entity.ContrastPerson;
import cn.lhkj.project.system.entity.UserView;

@Service("checkPersonService")
@Scope("prototype")
public class CheckPersonServiceImpl extends BaseService implements CheckPersonService {
	
	@Transactional
	@Override
	public void addCheckPerson(CheckPerson checkPerson,Map<String, Object> requestParams) throws Exception {
		SessionBean bean = (SessionBean)requestParams.get("SESSION_BEAN");
		if(checkPerson == null || bean == null) throw new NullPointerException();
		UserView user = bean.getUserView();
		checkPerson.setId(UUIDFactory.getUUIDStr());
		checkPerson.setAreaCode(bean.getStationId().substring(0, 6));
		checkPerson.setStationId(bean.getStationId());
		checkPerson.setStationName(user.getOrgName());
		checkPerson.setCheckTime(new Date());
		checkPerson.setRemark(user.getOrgDetailName());
		checkPerson.setIsUpload("0");
		ContrastPerson cp = (ContrastPerson)baseDao.getEntity(ContrastPerson.class, checkPerson.getTaskId());
		if(cp !=null) checkPerson.setTag(cp.getTag());
		baseDao.save(checkPerson);
		Set<String>keySet = requestParams.keySet();
		for(String key : keySet){
			if (key.startsWith("peers_idcard")) {
				String uuid = key.substring("peers_idcard".length());
				CheckPersonPeers peer = new CheckPersonPeers(UUIDFactory.getUUIDStr());
				peer.setCheckPersonId(checkPerson.getId());
				peer.setId(UUIDFactory.getUUIDStr());
				peer.setIdcard(requestParams.get("peers_idcard" + uuid) == null ? null
								: requestParams.get("peers_idcard" + uuid).toString());
				peer.setPhonenum(requestParams.get("peers_phonenum" + uuid) == null ? null
								: requestParams.get("peers_phonenum" + uuid).toString());
				baseDao.save(peer);
			}
			
			//添加同行车辆的信息
			if (key.startsWith("peerVehicle_carNum")) {
				String uuid = key.substring("peerVehicle_carNum".length());
				CheckPersonPeersVehicle peersVehicle = new CheckPersonPeersVehicle(UUIDFactory.getUUIDStr());
				peersVehicle.setCheckPersonId(checkPerson.getId());
				peersVehicle.setId(UUIDFactory.getUUIDStr());
				peersVehicle.setCarNum(requestParams.get("peerVehicle_carNum" + uuid) == null ? null
								: requestParams.get("peerVehicle_carNum" + uuid).toString());
				peersVehicle.setPlateColor(requestParams.get("peerVehicle_plateColor" + uuid) == null ? null
								: requestParams.get("peerVehicle_plateColor" + uuid).toString());
				peersVehicle.setVehicleColor(requestParams.get("peerVehicle_color" + uuid) == null ? null
								: requestParams.get("peerVehicle_color" + uuid).toString());
				peersVehicle.setVehicleType(requestParams.get("peerVehicle_type" + uuid) == null ? null
								: requestParams.get("peerVehicle_type" + uuid).toString());
				baseDao.save(peersVehicle);
			}		
		}
		String sql = "update CSL_CONTRAST_PERSON set IS_CHECKED='1' where id='"+checkPerson.getTaskId()+"'";
		baseDao.execute(sql);
	}
	
	
	@Override
	public CheckPerson getCheckPerson(String id) throws Exception {
		return (CheckPerson)baseDao.getEntity(CheckPerson.class, id);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public CheckPerson getCheckPersonByTaskId(String id) throws Exception {
		String hql = "from CheckPerson t where t.taskId='"+id+"'";
		List<CheckPerson> list = (List<CheckPerson>)baseDao.findByHQL(hql);
		if(!StringUtil.isNull(list)){
			return  list.get(0);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CheckPersonPeers> getPeersList(String checkPersonId)
			throws Exception {
		String hql = "from CheckPersonPeers t where t.checkPersonId='"+checkPersonId+"' ";
		return (List<CheckPersonPeers>)baseDao.findByHQL(hql);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CheckPersonPeersVehicle> getPeersVehicleList(String checkPersonId)
			throws Exception {
		String hql = "from CheckPersonPeersVehicle t where t.checkPersonId='"+checkPersonId+"' ";
		return (List<CheckPersonPeersVehicle>)baseDao.findByHQL(hql);
	}
}
