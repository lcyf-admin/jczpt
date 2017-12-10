package cn.lhkj.project.stati.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import cn.lhkj.commons.base.BaseDataDict;
import cn.lhkj.commons.base.BaseService;
import cn.lhkj.commons.entity.SessionBean;
import cn.lhkj.commons.util.StringUtil;
import cn.lhkj.project.system.entity.DictItem;
import cn.lhkj.project.system.entity.UserView;

@Service("chartService")
@Scope("prototype")
public class ChartServiceImpl extends BaseService implements ChartService {

	@Override
	public Map<String,Object> trafficdata(String startsh ,String endstr, SessionBean se) throws Exception {
		if(se == null) return null;
		UserView uv = se.getUserView();
		if(uv == null) return null;
		if(StringUtil.isNull(startsh) || StringUtil.isNull(endstr)) return null;
		String startMonth = startsh.substring(0, 7).replaceAll("-", "");
		String endMonth = endstr.substring(0, 7).replaceAll("-", "");;
		
		List<DictItem> list = BaseDataDict.dictMap.get("WQUIP_TYPE");
		if(StringUtil.isNull(list)) return null;
		String shujumen = "";
		String czhy = "";
		for(DictItem d : list){
			if("SHUJUMEN".equals(d.getCodes())){ shujumen = d.getId(); }
			if("CHEZHENGHEYI".equals(d.getCodes())){ czhy = d.getId(); }
		}
		
		StringBuffer pcategories = new StringBuffer();//数据门类目
		StringBuffer vcategories = new StringBuffer();//安检车道类目
		int vehicleTotal = 0;//车流量
		int personTotal = 0;//人流量
		int vehicleShuntCount = 0;//前置卡口过车
		int male = 0;//数据门男性数量
		int female = 0;//数据门女性数量
		
		Map<String,Integer> datemap1 = new HashMap<String,Integer>();//数据门
		Map<String,Integer> datemap2 = new HashMap<String,Integer>();//车道名
		Map<String,Integer> datemap3 = new HashMap<String,Integer>();//车道ID
		Set<String> datadoorSet = new HashSet<String>();//所有的数据门-NAME
		Set<String> laneSet = new HashSet<String>();//所有的安检车道-NAME
		Set<String> laneIdSet = new HashSet<String>();//所有的安检车道-ID
		ArrayList<Integer> array1 = new ArrayList<Integer>();//数据门人员
		ArrayList<Integer> array2 = new ArrayList<Integer>();//数据门预警人员
		ArrayList<Integer> array3 = new ArrayList<Integer>();//车道人员
		ArrayList<Integer> array4 = new ArrayList<Integer>();//车道预警人员
		ArrayList<Integer> array5 = new ArrayList<Integer>();//车道车辆
		ArrayList<Integer> array6 = new ArrayList<Integer>();//车道预警车辆
		
		String stationId = StringUtil.trim(uv.getOrgCodes());
		if(stationId.length() > 13) stationId = stationId.substring(0, 13);
		//查询所有的数据门
		String sql_datadoorList = "select t.id as ID, t.names as NAMES" +
				" from csl_equipment t" +
				" where t.type='"+shujumen+"'" +
				" and instr(t.id,'"+stationId+"') > 0 order by t.id";
		List<Map<String,String>> result = baseDao.findBySQL(sql_datadoorList);
		for(int i=0;i<result.size();i++){
			Map<String,String> tmp = result.get(i);
			if(i!=0){ pcategories.append(","); }
			pcategories.append("'"+tmp.get("NAMES")+"'");
			datemap1.put(tmp.get("NAMES"), i);//数据门
			array1.add(0);//数据门人员
			array2.add(0);//数据门预警人员
			datadoorSet.add(tmp.get("NAMES"));
		}
		
		//查询车道信息
		String sql_laneList = "select e.id as ID,l.laneName as NAMES " +
				" from csl_equipment e left join csl_lane l on(e.laneid=l.id) where 1=1" +
				" and e.type='"+czhy+"'" +
				" and instr(e.id,'"+stationId+"') > 0 " +
				" order by e.id";
		result = baseDao.findBySQL(sql_laneList);
		for(int i=0;i<result.size();i++) {
			Map<String, String> tmp = result.get(i);
			if(i!=0){ vcategories.append(","); }
			vcategories.append("'"+tmp.get("NAMES")+"'");
			datemap2.put(tmp.get("NAMES"), i);//车道名
			datemap3.put(tmp.get("ID"), i);//车道ID
			array3.add(0);//车道人员
			array4.add(0);//车道预警人员
			array5.add(0);//车道车辆
			array6.add(0);//车道预警车辆
			laneSet.add(tmp.get("NAMES"));
			laneIdSet.add(tmp.get("ID"));
		}
		
		//查询数据门通行人员
		String sql_datadoorPerson = "select count(1) as NUM,t.location as LOCATION" +
				" from csl_person t where 1=1 " +
				" and t.month_id between " + startMonth + " and "+ endMonth +
				" and instr(t.equipment_id,'"+stationId+"') > 0  " +
				" and t.capture_time between to_date('"+startsh+"','yyyy-mm-dd hh24:mi:ss') and to_date('"+endstr+"','yyyy-mm-dd hh24:mi:ss')" +
				" group by t.location";
		result = baseDao.findBySQL(sql_datadoorPerson);
		for(Map<String, String> tmp : result){
			if(!datadoorSet.contains(tmp.get("LOCATION"))) continue;
			array1.set(datemap1.get(tmp.get("LOCATION")), Integer.parseInt(tmp.get("NUM")));
			personTotal += Integer.parseInt(tmp.get("NUM"));//人流量
		}
		
		//查询预警人员数量
		String sql_personContrast ="select count(1) as NUM,t.location as LOCATION" +
				" from csl_contrast_person t where 1=1" +
				" and t.month_id between " + startMonth + " and " + endMonth +
				" and instr(t.equipment_id,'"+stationId+"') > 0 " +
				" and t.insert_time between to_date('"+startsh+"','yyyy-mm-dd hh24:mi:ss') and to_date('"+endstr+"','yyyy-mm-dd hh24:mi:ss')" +
				" group by t.location ";
		result = baseDao.findBySQL(sql_personContrast);
		for(Map<String, String> tmp : result){
			if(datadoorSet.contains(tmp.get("LOCATION"))){//数据门
				array2.set(datemap1.get(tmp.get("LOCATION")), Integer.parseInt(tmp.get("NUM")));
			}
			if(laneSet.contains(tmp.get("LOCATION"))){//车道
				array4.set(datemap2.get(tmp.get("LOCATION")), Integer.parseInt(tmp.get("NUM")));
			}
		}
		
		//车道车辆数据
		String sql_vehicleList = "select count(1) as NUM,t.equipment_id as EQUIPMENT" +
				" from csl_vehicle_brake t where 1=1" +
				" and t.month_id between "+startMonth+" and "+endMonth+"" +
				" and instr(t.equipment_id,'"+stationId+"') > 0 " +
				" and t.insert_time between to_date('"+startsh+"','yyyy-mm-dd hh24:mi:ss') and to_date('"+endstr+"','yyyy-mm-dd hh24:mi:ss')" +
				" group by t.equipment_id";
		result = baseDao.findBySQL(sql_vehicleList);
		for(Map<String, String> tmp : result){
			if(!laneIdSet.contains(tmp.get("EQUIPMENT"))) continue;
			array5.set(datemap3.get(tmp.get("EQUIPMENT")), Integer.parseInt(tmp.get("NUM")));
			vehicleTotal += Integer.parseInt(tmp.get("NUM"));//车流量
		}
		
		//车辆预警数据
		String csl_vehicleContrast = "select count(1) as NUM,t.location as LOCATION" +
				" from csl_contrast_vehicle t where 1=1" +
				" and t.month_id between "+startMonth+" and "+endMonth+"" +
				" and instr(t.equipment_id,'"+stationId+"') > 0 " +
				" and t.insert_time between to_date('"+startsh+"','yyyy-mm-dd hh24:mi:ss') and to_date('"+endstr+"','yyyy-mm-dd hh24:mi:ss')" +
				" group by t.location";
		result = baseDao.findBySQL(csl_vehicleContrast);
		for(Map<String, String> tmp : result){
			if(!laneSet.contains(tmp.get("LOCATION"))) continue;
			array6.set(datemap2.get(tmp.get("LOCATION")), Integer.parseInt(tmp.get("NUM")));
		}
		
		//车道人员数据
		/*String sql_passengerList = "select count(1) as num,v.equipment_id as equipment " +
				" from csl_passenger t,csl_vehicle_brake v where v.id = t.vehicle_id " +
				" and t.month_id between "+startMonth+" and "+endMonth+" " +
				" and v.month_id between "+startMonth+" and "+endMonth+"" +
				" and instr(v.equipment_id,'"+stationId+"') > 0 " +
				" and t.insert_time between to_date('"+startsh+"','yyyy-mm-dd hh24:mi:ss') and to_date('"+endstr+"','yyyy-mm-dd hh24:mi:ss')" +
				" group by v.equipment_id";*/
		String sql_passengerList = "select count(1) as NUM,t.equipment_id as EQUIPMENT" +
				" from csl_vehicle_brake t where 1=1" +
				" and t.month_id between "+startMonth+" and "+endMonth+"" +
				" and t.card_num is not null " +
				" and instr(t.equipment_id,'"+stationId+"') > 0 " +
				" and t.insert_time between to_date('"+startsh+"','yyyy-mm-dd hh24:mi:ss') and to_date('"+endstr+"','yyyy-mm-dd hh24:mi:ss')" +
				" group by t.equipment_id";
		
		result = baseDao.findBySQL(sql_passengerList);
		for(Map<String, String> tmp : result){
			if(!laneIdSet.contains(tmp.get("EQUIPMENT"))) continue;
			array3.set(datemap3.get(tmp.get("EQUIPMENT")), Integer.parseInt(tmp.get("NUM")));
		}
		
		//前置卡口车辆数据
		String sql_vehicleShunt = "select count(1) as count " +
				" from csl_vehicle_shunt t where 1=1" +
				" and t.month_id between "+startMonth+" and "+endMonth+"" +
				" and instr(t.eqipment_id,'"+stationId+"') > 0 " +
				" and t.insert_time between to_date('"+startsh+"','yyyy-mm-dd hh24:mi:ss') and to_date('"+endstr+"','yyyy-mm-dd hh24:mi:ss')";
		vehicleShuntCount = baseDao.getCount(sql_vehicleShunt);
		
		//查询数据门通行人员-女
		String sql_datadoorPersonFemale = "select count(1) as count " +
				" from csl_person t where gender='女' " +
				" and t.month_id between " + startMonth + " and "+ endMonth +
				" and instr(t.equipment_id,'"+stationId+"') > 0 " +
				" and t.capture_time between to_date('"+startsh+"','yyyy-mm-dd hh24:mi:ss') and to_date('"+endstr+"','yyyy-mm-dd hh24:mi:ss')";
		female = baseDao.getCount(sql_datadoorPersonFemale);
		male = personTotal-female;
		
		
		String pseries = "[{name:\"人员流量\",data:"+array1.toString()+"}," +
				"{name:\"预警人员\",data:"+array2.toString()+"}]";
	
		String vseries = "[{name:\"车辆流量\",data:"+array5.toString()+"}," +
				"{name:\"人员流量\",data:"+array3.toString()+"}," +
				"{name:\"预警车辆\",data:"+array6.toString()+"}," +
				"{name:\"预警人员\",data:"+array4.toString()+"}]";
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("ptitle", startsh + " 至 " + endstr + " 数据门统计");
		map.put("vtitle", startsh + " 至 " + endstr + " 安检车道统计");
		map.put("pcategories", "["+pcategories+"]");
		map.put("vcategories", "["+vcategories+"]");
		map.put("pseries", pseries);
		map.put("vseries", vseries);
		map.put("vehicle", vehicleTotal);
		map.put("person", personTotal);
		map.put("female", female);
		map.put("male", male);
		map.put("vehicleShunt", vehicleShuntCount);
		return map;
	}
	
	@Override
	public Map<String,Object> equipmentdata() throws Exception {
		
		String nums1 = "0";//安检车道
		String nums2 = "0";//安检设备
		String sql1 = "select count(t.id) as num from CSL_LANE t where instr(t.id,'CD00')=0 ";//安检车道
		String sql2 = "select count(t.id) as num from CSL_EQUIPMENT t where instr(t.id,'CZHY00')=0";//安检设备
		List<Map<String,String>> result = baseDao.findBySQL(sql1);
		if(result != null){
			Map<String, String> tmp = result.get(0);
			nums1 = tmp.get("NUM");
		}
		
		result = baseDao.findBySQL(sql2);
		if(result != null){
			Map<String, String> tmp = result.get(0);
			nums2 = tmp.get("NUM");
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("lane", nums1);//车道数量
		map.put("check", nums2);//安检设备数量
		return map;
	}
}