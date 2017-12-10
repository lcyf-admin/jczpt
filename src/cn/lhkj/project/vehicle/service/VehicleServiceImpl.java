package cn.lhkj.project.vehicle.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import cn.lhkj.commons.base.BaseDataCode;
import cn.lhkj.commons.base.BaseService;
import cn.lhkj.commons.defined.Transactional;
import cn.lhkj.commons.entity.PageInfo;
import cn.lhkj.commons.entity.SessionBean;
import cn.lhkj.commons.tools.IdCardGenerator;
import cn.lhkj.commons.tools.UUIDFactory;
import cn.lhkj.commons.util.CalendarUtil;
import cn.lhkj.commons.util.StringUtil;
import cn.lhkj.project.equipment.entity.Equipment;
import cn.lhkj.project.lane.entity.Lane;
import cn.lhkj.project.transfer.entity.Backinfo;
import cn.lhkj.project.transfer.entity.Transfer;
import cn.lhkj.project.vehicle.entity.Passenger;
import cn.lhkj.project.vehicle.entity.Uvss;
import cn.lhkj.project.vehicle.entity.UvssPath;
import cn.lhkj.project.vehicle.entity.Vehicle;
import cn.lhkj.project.vehicle.entity.VehicleBrake;
import cn.lhkj.project.vehicle.entity.VehicleShunt;
import net.sf.json.JSONObject;

@Service("vehicleService")
@Scope("prototype")
public class VehicleServiceImpl extends BaseService implements VehicleService {
	
	private static final Logger logger = Logger.getLogger(VehicleServiceImpl.class);
	
	@Override
	public Vehicle getVehicle(String id) throws Exception {
		return (Vehicle)baseDao.getEntity(Vehicle.class, id);
	}
	
	@Override
	public void savePassenger(Passenger t) throws Exception {
		if(t!=null){
			t.setId(UUIDFactory.getUUIDStr());
			Vehicle v = getVehicle(t.getVehicleId());
			if(v == null) return;
			t.setPassdate(v.getPassdate());
			t.setMonthId(v.getMonthId());
			t.setSex(IdCardGenerator.gender(t.getCardNum()));
			t.setBirthDate(IdCardGenerator.birth(t.getCardNum()));
			t.setInsertTime(new Date());
			baseDao.save(t);
		}
	}
	
	@Override
	public void deletePassenger(String id) throws Exception {
		baseDao.execute("delete from CSL_PASSENGER where id='"+id+"'");
	}
	
	@Override
	public VehicleBrake getVehicleBrake(String id) throws Exception {
		return (VehicleBrake)baseDao.getEntity(VehicleBrake.class, id);
	}
	
	@Override
	public VehicleShunt getVehicleShunt(String id) throws Exception {
		return (VehicleShunt)baseDao.getEntity(VehicleShunt.class, id);
	}
	
	
	@Transactional
	@Override
	public String gather(String infos,String ip) throws Exception{
		Transfer transfer = new Transfer(UUIDFactory.getUUIDStr());
		transfer.setSendTime(CalendarUtil.getCurrentTime());
		try {
			JSONObject jsonObject = JSONObject.fromObject(infos);
			transfer.setVer(StringUtil.trim(jsonObject.getString("ver")));//版本号
			transfer.setTranNO(StringUtil.trim(jsonObject.getString("tranNO")));//通讯号，此次的唯一标识
			String tranNO = transfer.getTranNO();
			String backUrl = jsonObject.containsKey("backUrl") ? StringUtil.trim(jsonObject.getString("backUrl")) : "";//比中回传数据的厂家接口
			String tranType = StringUtil.trim(jsonObject.getString("tranType"));//代表不同的通讯请求
			transfer.setTranType(tranType);
			String[] tranTypes = {"1001","3001","5001","6001","9001"};
			if(!StringUtil.contains(tranTypes, tranType)){
				transfer.setTranResult("400");//失败
				transfer.setTranMsg("通讯请求数据错误tranType="+tranType);
				return Backinfo.backMsg(transfer);
			}
			if("1001".equals(tranType)){//前置卡口车辆分流接口
				new VehicleGather1001(baseDao,tranNO).gather(jsonObject, backUrl, ip);
			}else{
				JSONObject data = (JSONObject)jsonObject.getJSONObject("data");
				String passageaway = StringUtil.trim(data.getString("passageaway"));//通道编号（所有系统统一编号）
				boolean isRight = BaseDataCode.isRight(passageaway);
				if(!isRight){
					transfer.setTranResult("400");//失败
					transfer.setTranMsg("车道编号错误passageaway="+passageaway);
					return Backinfo.backMsg(transfer);
				}
				String dataNum = StringUtil.trim(data.getString("dataNum"));
				if(StringUtil.isNull(dataNum)){
					transfer.setTranResult("400");//失败
					transfer.setTranMsg("dataNum不能为空");
					return Backinfo.backMsg(transfer);
				}
				
				if("9001".equals(tranType)){//摄像头抓拍后验证信息
					new VehicleGather9001(baseDao,tranNO).gather(data, backUrl, ip);
				}else if("6001".equals(tranType)){//断网后续传
					new VehicleGather6001(baseDao).gather(data, ip);
				}else if("5001".equals(tranType)){//司机刷证
					new VehicleGather5001(baseDao,tranNO).gather(data ,backUrl,ip);
				}else if("3001".endsWith(tranType)){//上传乘客数据
					new VehicleGather3001(baseDao).gather(data, backUrl,ip);
				}
			}
		}catch (Exception e) {
			transfer.setTranResult("400");
			transfer.setTranMsg("接口调用失败："+e.getMessage());
			logger.error(transfer.getTranMsg()+"\t\n参数"+infos);
			return Backinfo.backMsg(transfer);
		}
		transfer.setTranResult("200");
		transfer.setTranMsg("正常");
		transfer.setTranNO(UUIDFactory.getUUIDStr());
		return Backinfo.backMsg(transfer);
	}
	
	@Override
	public void saveUvss(Uvss uvss) throws Exception {
		if(uvss == null) return;
		baseDao.save(uvss);
	}
	
	@Override
	public void saveUvssPath(UvssPath uvss) throws Exception {
		if(uvss == null) return;
		baseDao.save(uvss);
		
	}
	
	public PageInfo getPriorDataVehiclePage(Map<String, Object> requestParams,
			PageInfo pageInfo) throws Exception {
		String monthId = (String)requestParams.get("monthId");
		String passengerIdcard = StringUtil.trim((String)requestParams.get("passengerIdcard"));				
		if(StringUtil.isNull(monthId)){
			monthId = CalendarUtil.getCurrentMonth();
		}
		
		String param = ""; 
		if (StringUtil.isNotNull(passengerIdcard)){
			String s = "select distinct t.vehicle_id as  VEHICLE_ID from CSL_PASSENGER t where t.month_id='"+monthId+"' and t.card_num = '"+passengerIdcard+"' ";
			if(requestParams.get("startdate") != null && !"".equals(requestParams.get("startdate"))){
				s += " and to_char(t.passdate,'yyyy-mm-dd hh24:mi:ss') >='"+requestParams.get("startdate")+"'";
			}
			if(requestParams.get("enddate") != null && !"".equals(requestParams.get("enddate"))){
				s += " and to_char(t.passdate,'yyyy-mm-dd hh24:mi:ss') <='"+requestParams.get("enddate")+"'";
			}
			List<Map<String,String>> passengerList =  baseDao.findBySQL(s);
			if (StringUtil.isNull(passengerList)) {
				return pageInfo;
			}
			for (int i = 0; i < passengerList.size(); i++) {
				String id = passengerList.get(i).get("VEHICLE_ID");
				if(param.length() == 0){
					param += "'"+id+"'";
				}else{
					param += ",'"+id+"'";
				}
			}
			
		}
		
		
		String sql = "select " +
				"v.id as ID," +
				"v.car_num as CARNUM," +
				"v.plate_color as PLATECOLOR," +
				"v.passdate as PASSDATE," +
				"v.card_num as CARDNUM," +
				"e.names as NAMES " +
				"from CSL_VEHICLE v left join CSL_EQUIPMENT e on(e.id=v.equipment_id)" +
				"where v.month_id='"+monthId+"' ";
		String parmsMore = "";
		
		SessionBean se = (SessionBean)requestParams.get("SESSION_BEAN");
		if(se != null){//站点数据权限
			parmsMore += " and instr(v.equipment_id, '"+se.getStationId()+"')>0 ";//@@
		}
		if(requestParams.get("startdate") != null && !"".equals(requestParams.get("startdate"))){
			parmsMore += " and to_char(v.passdate,'yyyy-mm-dd hh24:mi:ss') >='"+requestParams.get("startdate")+"'";
		}
		if(requestParams.get("enddate") != null && !"".equals(requestParams.get("enddate"))){
			parmsMore += " and to_char(v.passdate,'yyyy-mm-dd hh24:mi:ss') <='"+requestParams.get("enddate")+"'";
		}
		if(requestParams.get("inputValue") != null ){//根据条件模糊查询
			String searchParam = requestParams.get("inputValue").toString();
			if(searchParam.length() > 0){
				parmsMore += " and (instr(v.car_num, '"+searchParam+"')>0 " +
				"or instr(v.plate_color,'"+searchParam+"')>0 "+
				"or instr(v.card_num,'"+searchParam+"')>0 "+
			    "or instr(e.names, '"+searchParam+"')>0) ";
			}
		}
		if(param.length() > 0){
			parmsMore += " and v.id in("+param+")";
		}
		sql = sql + parmsMore + " order by v.passdate desc";
		pageInfo = baseDao.findPageBySQL(pageInfo, sql);
		return pageInfo;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Vehicle> listVehicle(String records,String stationId) throws Exception {
		String monthId = CalendarUtil.getCurrentMonth();
		String hql = "from Vehicle where monthId='"+monthId+"' and instr( equipmentId , '"+stationId+"')>0 order by insertTime desc";
		List<Vehicle> relust = (List<Vehicle>)baseDao.findByHQL(hql, 0, Integer.parseInt(records));
		if(StringUtil.isNull(relust)){
			hql = "from Vehicle where 1=1 and instr( equipmentId , '"+stationId+"')>0 order by monthId desc, insertTime desc";
			relust = (List<Vehicle>)baseDao.findByHQL(hql, 0, Integer.parseInt(records));
		}
		
		if(!StringUtil.isNull(relust)){
			for(Vehicle t : relust){
				Equipment em = (Equipment)baseDao.getEntity(Equipment.class, t.getEquipmentId());
				if(em != null) 
					t.setLane((Lane)baseDao.getEntity(Lane.class, em.getLaneId()));
			}
		}
		return relust;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Passenger> getPassengerList(String vehicleId) throws Exception {
		String hql = "from Passenger t where t.vehicleId='"+vehicleId+"' and t.isDriver='0'";
		return (List<Passenger>)baseDao.findByHQL(hql);
	}
}
