package cn.lhkj.commons.scan.thread;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import cn.lhkj.commons.base.BaseDao;
import cn.lhkj.commons.base.BaseDataCode;
import cn.lhkj.commons.tools.HttpPostSANHUI;
import cn.lhkj.commons.tools.IdCardGenerator;
import cn.lhkj.commons.tools.UUIDFactory;
import cn.lhkj.commons.util.StringUtil;
import cn.lhkj.project.contrast.entity.ContrastPerson;
import cn.lhkj.project.equipment.entity.Equipment;
import cn.lhkj.project.person.entity.Person;
import cn.lhkj.project.person.entity.PersonWarning;
import cn.lhkj.project.system.entity.Orgzon;

/**
 * 线程监听车证合一采集的车辆是否允许通过
 */
public class ComparPersonThread implements Runnable {
	
	private static final Logger logger = Logger.getLogger(ComparPersonThread.class);
	
	private PersonWarning p;
	private Equipment em;
	
	public ComparPersonThread(PersonWarning p,Equipment em){
		this.p = p;
		this.em = em;
	}
	
	@Override
	public void run(){
		BaseDao dao = null;
		try {
			if(p == null) return;
			dao = new BaseDao();
			Person person = (Person)dao.getEntity(Person.class, p.getId());
			if(person == null) return;
			
			if(StringUtil.isNull(p.getReserved1()) && StringUtil.isNull(p.getReserved2())){//品恩未提供处置措施和标签
				String strURL = BaseDataCode.config.getComparBigDataURL();
				if(StringUtil.isNull(strURL)) return;
				Orgzon orgzon = BaseDataCode.orgMap.get(person.getEquipmentId().substring(0, 13));
				Map<String,String> postMap = new HashMap<String,String>();
				postMap.put("id", StringUtil.trim(person.getIdcard()));
				String relust = HttpPostSANHUI.sendPost(strURL, "person", postMap, orgzon);
				if(StringUtil.isNull(relust)) return;
				try {
					JSONObject jsonObject = JSONObject.fromObject(relust);
					JSONArray datas = jsonObject.getJSONArray("data");
					if(datas == null || datas.size() == 0) return;
					JSONObject data = datas.getJSONObject(0);
					
					ContrastPerson type = new ContrastPerson(UUIDFactory.getUUIDStr());//人员预警信息
					type.setIdcard(StringUtil.trim(person.getIdcard()));
					type.setNames(StringUtil.trim(person.getNames()));
					type.setAddress(StringUtil.trim(person.getAddress()));
					type.setTag(StringUtil.trim(data.getString("datasource")));
					type.setAction(StringUtil.trim(data.getString("action")));
					type.setBkArea(data.getString("bkfwmc"));
					type.setYjArea(data.getString("yjfwmc"));
					type.setCaptureTime(person.getCaptureTime());
					type.setGatherPhotoURL(person.getImgUrl());
					
					type.setInsertTime(new Date());
					type.setSource("本站产生");
					type.setLocation(em.getNames());
					type.setBirth(IdCardGenerator.birth(type.getIdcard()));
					type.setGender(IdCardGenerator.gender(type.getIdcard()));
					type.setEquipmentId(em.getId());
					type.setAreaCode(em.getId().substring(0, 6));
					type.setCheckPointId(em.getId().substring(0, 13));
					dao.saveOrUpdate(type);
					BaseDataCode.putContrastPersonMap(em.getId().substring(0, 13), type);
				}catch (JSONException e2) {}
				
			}else{//品恩提供处置措施和标签
				ContrastPerson type = new ContrastPerson(UUIDFactory.getUUIDStr());//人员预警信息
				type.setIdcard(StringUtil.trim(person.getIdcard()));
				type.setNames(StringUtil.trim(person.getNames()));
				type.setAddress(StringUtil.trim(person.getAddress()));
				type.setTag(StringUtil.trim(p.getReserved1()));
				type.setAction(StringUtil.trim(p.getReserved2()));
				type.setBkArea("");
				type.setYjArea("");
				type.setCaptureTime(person.getCaptureTime());
				type.setGatherPhotoURL(person.getImgUrl());
				
				type.setInsertTime(new Date());
				type.setSource("本站产生");
				type.setLocation(em.getNames());
				type.setBirth(IdCardGenerator.birth(type.getIdcard()));
				type.setGender(IdCardGenerator.gender(type.getIdcard()));
				type.setEquipmentId(em.getId());
				type.setAreaCode(em.getId().substring(0, 6));
				type.setCheckPointId(em.getId().substring(0, 13));
				dao.saveOrUpdate(type);
				BaseDataCode.putContrastPersonMap(em.getId().substring(0, 13), type);
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage());
		}finally{
			if(dao != null){dao.close();}
		}
	}
	
}
