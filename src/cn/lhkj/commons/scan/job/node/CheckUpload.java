package cn.lhkj.commons.scan.job.node;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.apache.log4j.Logger;

import net.sf.json.JSONObject;
import cn.lhkj.commons.base.BaseDao;
import cn.lhkj.commons.base.BaseDataCode;
import cn.lhkj.commons.tools.IdCardGenerator;
import cn.lhkj.commons.util.CalendarUtil;
import cn.lhkj.commons.util.StringUtil;
import cn.lhkj.project.check.entity.CheckPerson;
import cn.lhkj.project.check.entity.CheckPersonPeers;
import cn.lhkj.project.check.entity.CheckVehicle;
import cn.lhkj.project.check.entity.CheckVehiclePassenger;
import cn.lhkj.project.contrast.entity.ContrastPerson;
import cn.lhkj.project.contrast.entity.ContrastVehicle;
import cn.lhkj.project.equipment.entity.Equipment;
import cn.lhkj.project.person.entity.Person;
import cn.lhkj.project.system.entity.Orgzon;
import cn.lhkj.project.vehicle.entity.Passenger;
import cn.lhkj.project.vehicle.entity.VehicleBrake;

/**
 * 核录结果上传--每三分钟上传一次
 */
public class CheckUpload {
	
	private static final Logger logger = Logger.getLogger(CheckUpload.class);
	
	private String startTime;
	private String startMonth;
	private String endTime;
	private String endMonth;
	private int day = 200;//上传最近200天的数据
	/**此方法是配置在applicationContext.xml中 定时执行 */
	
	public void run(){
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, (0-day));
		this.startTime = df.format(calendar.getTime());
		this.startMonth = startTime.substring(0, 7).replace("-", "");
		calendar.add(Calendar.DATE, day);
		calendar.add(Calendar.SECOND, -300);
		this.endTime = df.format(calendar.getTime());
		this.endMonth = endTime.substring(0, 7).replace("-", "");
		
		
		uploadHLSC();//上传预警核录数据
		uploadPerson();//上传数据门采集人员数据
		uploadVehicle();//上传车道采集车辆数据
		uploadPersonWarning();//预警人员数据上传
		uploadVehicleWarning();//预警车辆数据上传
	}	
	/////////////////////////////////////////////////////////////////
	/**上传核录数据*/
	@SuppressWarnings("unchecked")
	private void uploadHLSC(){
		BaseDao dao = null;
		int errorCount = 0;
		try{
			if(BaseDataCode.config == null) return;
			String checkUploadURL = StringUtil.trim(BaseDataCode.config.getCheckUploadURL());
			if(StringUtil.isNull(checkUploadURL)) return;
			String ipAddress = checkUploadURL.replaceAll(ConnectJudge.reg, "$1");
			boolean b = ConnectJudge.isReachable(ipAddress);
			if(!b){//接口地址不通
				logger.error("【connect timed out】【"+checkUploadURL+"】");
				return;
			}
			
			dao = new BaseDao();
			String hql = "from CheckVehicle t where instr( t.isUpload ,'"+ipAddress+"') = 0 or t.isUpload is null order by t.checkTime desc";
			List<CheckVehicle> vehicleList = (List<CheckVehicle>)dao.findByHQL(hql, 0, 50);
			if(!StringUtil.isNull(vehicleList)){
				for(CheckVehicle t : vehicleList){
					if(errorCount > 4) break;
					String relust = null;
					try{
						Map<String,Object> map = new HashMap<String,Object>();
						map.put("sysname", "检查站综合管理平台");
						map.put("type", "vehicle");
						map.put("id", StringUtil.trim(t.getId()));
						map.put("task_id", StringUtil.trim(t.getTaskId()));
						map.put("yj_type", StringUtil.trim(t.getYjType()));
						map.put("car_num", StringUtil.trim(t.getCarNum()));
						String forbids = StringUtil.trim(t.getForbids());
						if("无".equals(forbids) || "".equals(forbids)){
							map.put("forbids", "1");
						}else{
							map.put("forbids", "2");
						}
						if("12345".contains(StringUtil.trim(t.getRelations()))) {
							map.put("relations", StringUtil.trim(t.getRelations()));
						}else {
							map.put("relations", StringUtil.trim(t.getRelationNum()));
						}
						map.put("explains", StringUtil.trim(t.getExplains()));
						map.put("check_time", StringUtil.trim(CalendarUtil.formatToHMS(t.getCheckTime())));
						map.put("area_code", StringUtil.trim(t.getAreaCode()));
						map.put("station_id", StringUtil.trim(t.getStationId()));
						map.put("station_name", StringUtil.trim(t.getStationName()));
						map.put("remark", "4");
						map.put("vehiclecolor", StringUtil.trim(t.getVehiclecolor()));
						map.put("platecolor", StringUtil.trim(t.getPlatecolor()));
						map.put("vehicletype", StringUtil.trim(t.getVehicletype()));
						//////////////////////////20171031 by wx////////////////////////
						map.put("vnature", StringUtil.trim(t.getVnature()));
						map.put("vunit", StringUtil.trim(t.getVunit()));
						String acheck = StringUtil.trim(t.getAcheck());
						String akeason = StringUtil.trim(t.getAkeason());
						if("0".equals(acheck)){
							map.put("acheck", "0");
							if ("".equals(akeason)) {
								map.put("akeason", "0");
							}else{
								map.put("akeason", StringUtil.trim(t.getAkeason()));
							}
						}else{
							map.put("acheck", "1");
							map.put("akeason", "");
						}
						
						if("1".equals(acheck)){
							map.put("acontent", "1");
						}else{
							if ("6".equals(akeason)) {
								map.put("acontent", StringUtil.trim(t.getAcontent()));
							}else {
								map.put("acontent", "");
							}
						}
						//////////////////////////20171031 by wx////////////////////////
						List<CheckVehiclePassenger> driverList = (List<CheckVehiclePassenger>)dao.findByHQL("from CheckVehiclePassenger t where t.checkVehicleId='"+t.getId()+"' and t.isDriver = '1'");
						if(!StringUtil.isNull(driverList)){
							CheckVehiclePassenger driver = driverList.get(0);
							map.put("driverphone", StringUtil.trim(driver.getPhonenum()));
							map.put("drivername", StringUtil.trim(driver.getName()));
							map.put("driverid", StringUtil.trim(driver.getIdcard()));
							String isphonesus = StringUtil.trim(driver.getIsDubious());
							if("0".equals(isphonesus)){
								map.put("isphonesus", "1");
							}else{
								map.put("isphonesus", "2");
							}
							String driverrzyz = StringUtil.trim(driver.getMatch());
							if ("0".equals(driverrzyz)) {
								map.put("driverrzyz", "2");
							}else{
								map.put("driverrzyz", "1");
							}
							
						}else{//未采集司机信息
							dao.execute("update CHECK_VEHICLE set IS_UPLOAD=IS_UPLOAD || ',' || '"+ipAddress+"' where id='"+t.getId()+"'");
							continue;
						}
						map.put("sfcw", "false");
						map.put("nffk", "1");
						map.put("bnfkyy", "");
						/*List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
						List<CheckVehiclePassenger> passengerList = (List<CheckVehiclePassenger>)dao.findByHQL("from CheckVehiclePassenger t where t.checkVehicleId='"+t.getId()+"'");
						if(!StringUtil.isNull(passengerList)){
							for(CheckVehiclePassenger p : passengerList){
								Map<String,Object> m = new HashMap<String,Object>();
								m.put("id", StringUtil.trim(p.getId()));
								m.put("idcard", StringUtil.trim(p.getIdcard()));
								m.put("phonenum", StringUtil.trim(p.getPhonenum()));
								m.put("match", StringUtil.trim(p.getMatch()));
								m.put("is_dubious", StringUtil.trim(p.getIsDubious()));
								m.put("finds", StringUtil.trim(p.getFinds()));
								m.put("is_driver", StringUtil.trim(p.getIsDriver()));
								data.add(m);
							}
						}
						map.put("data", data);*/
						relust = UploadPost.sendPost(checkUploadURL, StringUtil.obj2json(map));
						if(StringUtil.isNull(relust)){
							errorCount ++;
							continue;
						}
						JSONObject jsonObject = JSONObject.fromObject(relust);
						String msg = jsonObject.getString("msg");
						if("OK".equalsIgnoreCase(msg)){
							dao.execute("update CHECK_VEHICLE set IS_UPLOAD=IS_UPLOAD || ',' || '"+ipAddress+"' where id='"+t.getId()+"'");
						}else{
							logger.info("【车辆核录上传失败】【"+msg+"】");
						}
					}catch (Exception e) {
						logger.error("relust="+relust);
						logger.error(e.getMessage(),e);
					}
				}
			}
			
			hql = "from CheckPerson t where instr( t.isUpload ,'"+ipAddress+"') = 0 or t.isUpload is null order by t.checkTime desc";
			List<CheckPerson> personList = (List<CheckPerson>)dao.findByHQL(hql, 0, 50);
			errorCount = 0;
			if(!StringUtil.isNull(personList)){
				for(CheckPerson t : personList){
					try{
						if(errorCount > 4) break;
						Map<String,Object> map = new HashMap<String,Object>();
						map.put("sysname", "检查站综合管理平台");
						map.put("type", "person");
						map.put("id", StringUtil.trim(t.getId()));
						map.put("task_id", StringUtil.trim(t.getTaskId()));
						map.put("yj_type", StringUtil.trim(t.getYjType()));
						map.put("check_time", StringUtil.trim(CalendarUtil.formatToHMS(t.getCheckTime())));
						map.put("tag", StringUtil.trim(t.getTag()));
						map.put("is_contrast", StringUtil.trim(t.getIsContrast()));
						map.put("action", StringUtil.trim(t.getAction()));
						map.put("idcard", StringUtil.trim(t.getIdcard()));
						map.put("phonenum", StringUtil.trim(t.getPhonenum()));
						map.put("match", StringUtil.trim(t.getMatch()));
						map.put("is_dubious", StringUtil.trim(t.getIsDubious()));
						map.put("finds", StringUtil.trim(t.getFinds()));
						map.put("is_vacation", StringUtil.trim(t.getIsVacation()));
						map.put("reason", StringUtil.trim(t.getReason()));
						map.put("backtime", StringUtil.trim(CalendarUtil.formatToHMS(t.getBacktime())));
						map.put("is_leave", StringUtil.trim(t.getIsLeave()));
						map.put("area_code", StringUtil.trim(t.getAreaCode()));
						map.put("station_id", StringUtil.trim(t.getStationId()));
						map.put("station_name", StringUtil.trim(t.getStationName()));
						map.put("remark", "4");
						ContrastPerson cp = (ContrastPerson)dao.getEntity(ContrastPerson.class, StringUtil.trim(t.getTaskId()));
						if(cp == null){
							dao.execute("update CHECK_PERSON set IS_UPLOAD=IS_UPLOAD || ',' || '"+ipAddress+"' where id='"+t.getId()+"'");
							continue;
						}else{
							map.put("name", cp.getNames());
						}
						List<Map<String,Object>> peers = new ArrayList<Map<String,Object>>();
						//map.put("peers", peers);
						List<CheckPersonPeers> peersList = (List<CheckPersonPeers>)dao.findByHQL("from CheckPersonPeers t where t.checkPersonId='"+t.getId()+"'");
						if(!StringUtil.isNull(peersList)){
							for(CheckPersonPeers p : peersList){
								Map<String,Object> m = new HashMap<String,Object>();
								m.put("id", StringUtil.trim(p.getId()));
								m.put("idcard", StringUtil.trim(p.getIdcard()));
								m.put("phonenum", StringUtil.trim(p.getPhonenum()));
								peers.add(m);
							}
							map.put("ifhavepeer", "1");
							map.put("peergather", "1");
						}else{
							map.put("ifhavepeer", "2");
							map.put("peergather", "2");
						}
						
						map.put("toinlanddesc", StringUtil.trim(t.getReason()));
						if("立即抓捕".equals(t.getAction())){
							map.put("arrest", "1");
						}else{
							map.put("arrest", "2");
						}
						if(StringUtil.isNull(t.getHcjl())){//无核查意见的
							dao.execute("update CHECK_PERSON set IS_UPLOAD=IS_UPLOAD || ',' || '"+ipAddress+"' where id='"+t.getId()+"'");
							continue;
						}
						map.put("hcjl", StringUtil.trim(t.getHcjl()));
						String relust = UploadPost.sendPost(checkUploadURL, StringUtil.obj2json(map));
						if(StringUtil.isNull(relust)){
							errorCount ++;
							continue;
						}
						JSONObject jsonObject = JSONObject.fromObject(relust);
						String msg = jsonObject.getString("msg");
						if("OK".equalsIgnoreCase(msg)){
							dao.execute("update CHECK_PERSON set IS_UPLOAD=IS_UPLOAD || ',' || '"+ipAddress+"' where id='"+t.getId()+"'");
						}else{
							logger.info("【人员核录上传失败】【"+msg+"】");
						}
					}catch (Exception e) {
						logger.error(e.getMessage(),e);
					}
				}
			}
		}catch (Exception e) {
			logger.error(e.getMessage(),e);
		}finally{
			if(dao != null) dao.close();
		}
	}
	
	/////////////////////////////////////////////////////////////////
	@SuppressWarnings("unchecked")
	private void uploadPerson(){
		BaseDao dao = null;
		int errorCount = 0;
		try{
			if(BaseDataCode.config == null) return;
			String strURL = StringUtil.trim(BaseDataCode.config.getPersonUploadURL());
			if(StringUtil.isNull(strURL)) return;
			String ipAddress = strURL.replaceAll(ConnectJudge.reg, "$1");
			boolean b = ConnectJudge.isReachable(ipAddress);
			if(!b){//接口地址不通
				logger.error("【connect timed out】【"+strURL+"】");
				return;
			}
			
			dao = new BaseDao();
			for(int i=0;i<5;i++){
				if(errorCount >4) return;
				Thread.sleep((new Random()).nextInt(5000));
				String hql = "from Person t where t.monthId between "+startMonth+" and "+endMonth+" " +
						" and (instr( t.isUpload ,'"+ipAddress+"')=0 or t.isUpload is null) "+
						" and t.captureTime between to_date('"+startTime+"','yyyy-mm-dd hh24:mi:ss') and to_date('"+endTime+"','yyyy-mm-dd hh24:mi:ss') " +
						" order by t.captureTime desc ";
				List<Person> personList = (List<Person>)dao.findByHQL(hql, 0, 200);
				if(StringUtil.isNull(personList)) return;
				Orgzon org = BaseDataCode.orgMap.get(personList.get(0).getEquipmentId().substring(0, 13));
				if(org == null) return;
				if(StringUtil.isNull(org.getX()) || StringUtil.isNull(org.getY())) return;
				String deatilName = org.getDetailName();
				if(StringUtil.isNull(deatilName)){
					deatilName = org.getNames();
				}
				Map<String,Object> map = new HashMap<String, Object>();
				map.put("sendTime", CalendarUtil.getCurrentTime());
				map.put("total", personList.size());
				map.put("areaCode", org.getCodes().substring(0, 6));
				map.put("x", StringUtil.trim(org.getX()));
				map.put("y", StringUtil.trim(org.getY()));
				map.put("stationId", org.getCodes());
				map.put("stationName", org.getNames());
				List<Map<String,String>> data = new ArrayList<Map<String,String>>();
				map.put("data", data);
				for(Person p : personList){
					Map<String,String> m = new HashMap<String, String>();
					data.add(m);
					m.put("id", p.getId());
					m.put("passTime", CalendarUtil.formatToHMS(p.getCaptureTime()));
					String idcard = StringUtil.trim(p.getIdcard());
					m.put("idcard", idcard);
					m.put("name", StringUtil.trim(p.getNames()));
					m.put("gender", IdCardGenerator.gender(idcard));
					m.put("nation", StringUtil.trim(p.getNation()));
					m.put("birthday", IdCardGenerator.birth(idcard));
					m.put("address", StringUtil.trim(p.getAddress()));
					m.put("authority", "");
					m.put("validDate", "");
					m.put("personImgUrl", StringUtil.trim(p.getImgUrl()));
					m.put("idcardImgUrl", StringUtil.trim(p.getImgUrl()));
					m.put("equipmentId", "");
					m.put("equipmentName", "");
					m.put("equipmentType", "数据门");
					m.put("location", deatilName+ StringUtil.trim(p.getLocation()));
				}
				String relust = UploadPost.sendPost(strURL, StringUtil.obj2json(map));
				try{
	    			JSONObject jsonObject = JSONObject.fromObject(relust);
	    			String code = jsonObject.getString("code");
	    			if("200".equals(code)){
	    				logger.info("【通行人员成功上传"+personList.size()+"条】");
	    				for(Person p : personList){
	    	    			dao.execute("update CSL_PERSON set IS_UPLOAD=IS_UPLOAD || ',' || '"+ipAddress+"' where id='"+p.getId()+"'");
	    				}
	    			}else{
						logger.info("【通行人员上传】【"+jsonObject.getString("msg")+"】");
					}
	    		}catch (Exception e) {
	    			errorCount ++;
	    		}
			}
		}catch (Exception e) {
			logger.error(e.getMessage(),e);
		}finally{
			if(dao != null) dao.close();
		}
	}
	
	 /**上传车辆全量数据*/
    @SuppressWarnings("unchecked")
	private void uploadVehicle(){
    	BaseDao dao = null;
    	int errorCount = 0;
    	try {
    		if(BaseDataCode.config == null) return;
			String strURL = StringUtil.trim(BaseDataCode.config.getVehicleUploadURL());
			if(StringUtil.isNull(strURL)) return;
			String ipAddress = strURL.replaceAll(ConnectJudge.reg, "$1");
			boolean b = ConnectJudge.isReachable(ipAddress);
			if(!b){//接口地址不通
				logger.error("【connect timed out】【"+strURL+"】");
				return;
			}
			
    		dao = new BaseDao();
    		for(int i=0;i<5;i++){
    			if(errorCount > 4) break;
				Thread.sleep((new Random()).nextInt(5000));
				String hql = "from VehicleBrake t where t.monthId between "+startMonth+" and "+endMonth+
	    				" and (instr( t.isUpload ,'"+ipAddress+"')=0 or t.isUpload is null)" +
	    				" and t.carNum <> '无车牌' " +
	    				" and t.passdate between to_date('"+startTime+"','yyyy-mm-dd hh24:mi:ss') and to_date('"+endTime+"','yyyy-mm-dd hh24:mi:ss') " +
	    				" order by t.passdate desc";
				List<VehicleBrake> list = (List<VehicleBrake>)dao.findByHQL(hql, 0, 200);
				if(StringUtil.isNull(list)) return;
				Orgzon org = BaseDataCode.orgMap.get(list.get(0).getEquipmentId().substring(0, 13));
				if(org == null) return;
				if(StringUtil.isNull(org.getX()) || StringUtil.isNull(org.getY())) return;
				String deatilName = org.getDetailName();
				if(StringUtil.isNull(deatilName)){
					deatilName = org.getNames();
				}
				Map<String,Object> map = new HashMap<String, Object>();
				map.put("areaCode", org.getCodes().substring(0, 6));
	    		map.put("x", org.getX());
				map.put("y", org.getY());
				map.put("stationId", org.getCodes());
				map.put("stationName", org.getNames());
				List<Map<String,Object>> dataList = new ArrayList<Map<String,Object>>();
				map.put("data", dataList);
				int total = 0;
				for(VehicleBrake t: list){
	    			if(!t.getEquipmentId().contains(org.getCodes())) continue;
	    			Equipment e = BaseDataCode.equipmentMap.get(t.getEquipmentId());
	    			if(e == null) continue;
	    			total ++;
	    			Map<String,Object> data = new HashMap<String,Object>();
	    			List<Map<String,Object>> driverDataList = new ArrayList<Map<String,Object>>();
	    			data.put("driverData", driverDataList);
	    			List<Map<String,Object>> passengerDataList = new ArrayList<Map<String,Object>>();
	    			data.put("passengerData", passengerDataList);
	    			data.put("id", StringUtil.trim(t.getId()));
	    			data.put("passTime", StringUtil.trim(CalendarUtil.formatToHMS(t.getPassdate())));
	    			data.put("plateNum", StringUtil.trim(t.getCarNum()));
	    			data.put("plateColor", StringUtil.trim(t.getPlateColor()));
	    			data.put("vehicleType", StringUtil.trim(t.getVehicleType()));
	    			data.put("vehicleImgUrl", StringUtil.trim(t.getLocalCarImg()));
	    			data.put("plateImgUrl", StringUtil.trim(t.getLocalNumImg()));
	    			data.put("equipmentId", StringUtil.trim(t.getEquipmentId()));
	    			data.put("equipmentName", StringUtil.trim(e.getNames()));
	    			data.put("equipmentType", "道闸");
	    			data.put("location", deatilName+StringUtil.trim(e.getNames()));
	    			if(StringUtil.trim(t.getRelust()).contains("黑")){
	    				data.put("result", "黑名单");
	    			}else if(StringUtil.trim(t.getRelust()).contains("白")){
	    				data.put("result", "红名单");
	    			}else if(StringUtil.trim(t.getRelust()).contains("红")){
	    				data.put("result", "红名单");
	    			}else{
	    				data.put("result", "一般车辆");
	    			}
	    			if(!StringUtil.isNull(t.getCarNum())){
	    				Map<String,Object> driverDataMap = new HashMap<String, Object>();
	    				driverDataMap.put("name", StringUtil.trim(t.getUserName()));
	    				driverDataMap.put("gender", StringUtil.trim(t.getSex()));
	    				driverDataMap.put("nation", StringUtil.trim(t.getMinzu()));
	    				driverDataMap.put("idcard", StringUtil.trim(t.getCardNum()));
	    				driverDataMap.put("birthday", StringUtil.trim(t.getBirthDate()));
	    				driverDataMap.put("address", StringUtil.trim(t.getAddress()));
	    				driverDataMap.put("authority", StringUtil.trim(t.getQianfa()));
	    				driverDataMap.put("validDate", StringUtil.trim(t.getYouxiaoqi()));
	    				driverDataMap.put("personImgUrl", StringUtil.trim(t.getLocalCardImg()));
	    				driverDataMap.put("idcardImgUrl", StringUtil.trim(t.getLocalCardImg()));
	    				driverDataList.add(driverDataMap);
	    			}
	    			List<Passenger> passengerList = getPassengerList(t.getId(), dao);
	    			if(!StringUtil.isNull(passengerList)){
	    				for(Passenger p : passengerList){
	    					Map<String,Object> passengerDataMap = new HashMap<String, Object>();
		    				passengerDataMap.put("name", StringUtil.trim(p.getUserName()));
		    				passengerDataMap.put("gender", StringUtil.trim(p.getSex()));
		    				passengerDataMap.put("nation", StringUtil.trim(p.getMinzu()));
		    				passengerDataMap.put("idcard", StringUtil.trim(p.getCardNum()));
		    				passengerDataMap.put("birthday", StringUtil.trim(p.getBirthDate()));
		    				passengerDataMap.put("address", StringUtil.trim(p.getAddress()));
		    				passengerDataMap.put("authority", StringUtil.trim(p.getQianfa()));
		    				passengerDataMap.put("validDate", StringUtil.trim(p.getYouxiaoqi()));
		    				passengerDataMap.put("personImgUrl", StringUtil.trim(p.getCardImg()));
		    				passengerDataMap.put("idcardImgUrl", StringUtil.trim(p.getCardImg()));
		    				passengerDataList.add(passengerDataMap);
	    				}
	    			}
	    			dataList.add(data);
	    		}
				map.put("sendTime", CalendarUtil.getCurrentTime());
	    		map.put("total", String.valueOf(total));
	    		String relust = UploadPost.sendPost(strURL, StringUtil.obj2json(map));
	    		try{
	    			JSONObject jsonObject = JSONObject.fromObject(relust);
	    			String code = jsonObject.getString("code");
	    			if("200".equals(code)){
	    				logger.info("【通行车辆成功上传"+list.size()+"条】");
	    				for(VehicleBrake t: list){
	    	    			dao.execute("update CSL_VEHICLE_BRAKE set IS_UPLOAD=IS_UPLOAD || ',' || '"+ipAddress+"' where id='"+t.getId()+"'");
	    				}
	    			}else{
						logger.info("【通行车辆上传】【"+jsonObject.getString("msg")+"】");
					}
	    		}catch (Exception e) {
	    			errorCount ++;
	    		}
    		}
    	}catch (Exception e) {
			logger.error(e.getMessage(),e);
		}finally{
			if(dao != null) dao.close();
		}
    }
	
    /**上传预警人员数据*/
    @SuppressWarnings("unchecked")
	private void uploadPersonWarning(){
    	BaseDao dao = null;
    	int errorCount = 0;
    	try {
    		if(BaseDataCode.config == null) return;
			String strURL = StringUtil.trim(BaseDataCode.config.getWarningUploadURL());
			if(StringUtil.isNull(strURL)) return;
			String ipAddress = strURL.replaceAll(ConnectJudge.reg, "$1");
			boolean b = ConnectJudge.isReachable(ipAddress);
			if(!b){//接口地址不通
				logger.error("【connect timed out】【"+strURL+"】");
				return;
			}
			
    		dao = new BaseDao();
    		for(int i=0;i<5;i++){
    			if(errorCount > 4) break;
				Thread.sleep((new Random()).nextInt(5000));
				String hql = "from ContrastPerson t where t.monthId between "+startMonth+" and "+endMonth+
	    				" and (instr( t.isUpload ,'"+ipAddress+"')=0 or t.isUpload is null) " +
	    				" and t.captureTime between to_date('"+startTime+"','yyyy-mm-dd hh24:mi:ss') and to_date('"+endTime+"','yyyy-mm-dd hh24:mi:ss') " +
	    				" order by t.captureTime desc";
				List<ContrastPerson> list = (List<ContrastPerson>)dao.findByHQL(hql, 0, 50);
				if(StringUtil.isNull(list)) return;
				for(ContrastPerson t: list){
					if(errorCount > 4) break;
					Orgzon org = BaseDataCode.orgMap.get(list.get(0).getCheckPointId());
					if(org == null) continue;
					if(StringUtil.isNull(org.getX()) || StringUtil.isNull(org.getY())) continue;
					String deatilName = org.getDetailName();
					if(StringUtil.isNull(deatilName)){
						deatilName = org.getNames();
					}
					Map<String,Object> map = new HashMap<String, Object>();
					map.put("sendTime", CalendarUtil.getCurrentTime());
					map.put("areaCode", org.getCodes().substring(0, 6));
		    		map.put("x", org.getX());
					map.put("y", org.getY());
					map.put("stationId", org.getCodes());
					map.put("stationName", org.getNames());
					map.put("type", "person");
					Map<String,String> data = new HashMap<String, String>();
					map.put("data", data);
					data.put("passTime", StringUtil.trim(CalendarUtil.formatToHMS(t.getCaptureTime())));
					data.put("name", StringUtil.trim(t.getNames()));
					data.put("idcard", StringUtil.trim(t.getIdcard()));
					data.put("gender", IdCardGenerator.gender(data.get("idcard")));
					data.put("nation", StringUtil.trim(t.getNation()));
					data.put("birthday", IdCardGenerator.birth(data.get("idcard")));
					data.put("address", StringUtil.trim(t.getAddress()));
					data.put("authority", "");
					data.put("validDate", "");
					data.put("personImgUrl", StringUtil.trim(t.getGatherPhotoURL()));
					data.put("equipmentId", StringUtil.trim(t.getEquipmentId()));
					data.put("equipmentName", "");
					data.put("equipmentType", "");
					data.put("location", deatilName+StringUtil.trim(t.getLocation()));
					data.put("uuid", StringUtil.trim(t.getId()));
					data.put("datasource", StringUtil.trim(t.getTag()));
					data.put("action", StringUtil.trim(t.getAction()));
					data.put("bkfwdm", "");
					data.put("bkfwmc", StringUtil.trim(t.getBkArea()));
					data.put("yjfwdm", "");
					data.put("yjfwmc", StringUtil.trim(t.getYjArea()));
					String relust = UploadPost.sendPost(strURL, StringUtil.obj2json(map));
		    		try{
		    			JSONObject jsonObject = JSONObject.fromObject(relust);
		    			String code = jsonObject.getString("code");
		    			if("200".equals(code)){
		    				logger.info("【预警人员上传成功】【"+t.getId()+"】");
		    	    		dao.execute("update CSL_CONTRAST_PERSON set IS_UPLOAD=IS_UPLOAD || ',' || '"+ipAddress+"' where id='"+t.getId()+"'");
		    			}else{
							logger.info("【预警人员上传】【"+jsonObject.getString("msg")+"】");
						}
		    		}catch (Exception e) {
		    			errorCount ++;
		    		}
				}
    		}
    	}catch (Exception e) {
			logger.error(e.getMessage(),e);
		}finally{
			if(dao != null) dao.close();
		}
    }
    
    /**上传预警车辆数据*/
    @SuppressWarnings("unchecked")
	private void uploadVehicleWarning(){
    	BaseDao dao = null;
    	int errorCount = 0;
    	try {
    		if(BaseDataCode.config == null) return;
			String strURL = StringUtil.trim(BaseDataCode.config.getWarningUploadURL());
			if(StringUtil.isNull(strURL)) return;
			String ipAddress = strURL.replaceAll(ConnectJudge.reg, "$1");
			boolean b = ConnectJudge.isReachable(ipAddress);
			if(!b){//接口地址不通
				logger.error("【connect timed out】【"+strURL+"】");
				return;
			}
			
    		dao = new BaseDao();
    		for(int i=0;i<5;i++){
    			if(errorCount > 4) break;
				Thread.sleep((new Random()).nextInt(5000));
				String hql = "from ContrastVehicle t where t.monthId between "+startMonth+" and "+endMonth+
	    				" and (instr( t.isUpload ,'"+ipAddress+"')=0 or t.isUpload is null)" +
	    				" and instr( t.location , '前置卡口') = 0 " +
	    				" and t.passdate between to_date('"+startTime+"','yyyy-mm-dd hh24:mi:ss') and to_date('"+endTime+"','yyyy-mm-dd hh24:mi:ss') " +
	    				" order by t.passdate desc";
				List<ContrastVehicle> list = (List<ContrastVehicle>)dao.findByHQL(hql, 0, 50);
				if(StringUtil.isNull(list)) return;
				for(ContrastVehicle t: list){
					if(errorCount > 4) i++;
					Orgzon org = BaseDataCode.orgMap.get(list.get(0).getCheckPointId());
					if(org == null) continue;
					if(StringUtil.isNull(org.getX()) || StringUtil.isNull(org.getY())) continue;
					VehicleBrake vb = (VehicleBrake)dao.getEntity(VehicleBrake.class, t.getVehicleId());
					if(vb == null){
						dao.execute("update CSL_CONTRAST_PERSON set IS_UPLOAD='1' where id='"+t.getId()+"'");
						continue;
					}
					String deatilName = org.getDetailName();
					if(StringUtil.isNull(deatilName)){
						deatilName = org.getNames();
					}
					
					Map<String,Object> map = new HashMap<String, Object>();
					map.put("sendTime", CalendarUtil.getCurrentTime());
					map.put("areaCode", org.getCodes().substring(0, 6));
		    		map.put("x", org.getX());
					map.put("y", org.getY());
					map.put("stationId", org.getCodes());
					map.put("stationName", org.getNames());
					map.put("type", "vehicle");
					Map<String,String> data = new HashMap<String, String>();
					map.put("data", data);
					data.put("passTime", StringUtil.trim(CalendarUtil.formatToHMS(t.getPassdate())));
					data.put("plateNum", StringUtil.trim(t.getCarNum()));
					data.put("plateColor", StringUtil.trim(vb.getPlateColor()));
					data.put("vehicleType", StringUtil.trim(vb.getVehicleType()));
					data.put("vehicleImgUrl", StringUtil.trim(vb.getLocalCarImg()));
					data.put("plateImgUrl", StringUtil.trim(vb.getLocalNumImg()));
					data.put("equipmentId", StringUtil.trim(t.getEquipmentId()));
					data.put("equipmentName", "");
					data.put("equipmentType", "车证合一");
					data.put("location", deatilName+StringUtil.trim(t.getLocation()));
					data.put("uuid", StringUtil.trim(t.getId()));
					data.put("car_type", StringUtil.trim(t.getVehicleType()));
					data.put("sfzh", StringUtil.trim(t.getIdcard()));
					data.put("name", StringUtil.trim(t.getNames()));
					data.put("identlocation", StringUtil.trim(t.getAddress()));
					data.put("tag", StringUtil.trim(t.getLabel()));
					data.put("datasource", StringUtil.trim(t.getTag()));
					data.put("action", StringUtil.trim(t.getAction()));
					data.put("person_photo", StringUtil.trim(t.getPersonPhoto()));
					data.put("bkfwdm", "");
					data.put("bkfwmc", StringUtil.trim(t.getBkArea()));
					data.put("yjfwdm", "");
					data.put("yjfwmc", StringUtil.trim(t.getYjArea()));
					String relust = UploadPost.sendPost(strURL, StringUtil.obj2json(map));
		    		try{
		    			JSONObject jsonObject = JSONObject.fromObject(relust);
		    			String code = jsonObject.getString("code");
		    			if("200".equals(code)){
		    				logger.info("【预警车辆上传成功】【"+t.getId()+"】");
		    	    		dao.execute("update CSL_CONTRAST_VEHICLE set IS_UPLOAD=IS_UPLOAD || ',' || '"+ipAddress+"' where id='"+t.getId()+"'");
		    			}else{
							logger.info("【预警车辆上传】【"+jsonObject.getString("msg")+"】");
						}
		    		}catch (Exception e) {
		    			errorCount ++;
		    		}
				}
    		}
    	}catch (Exception e) {
			logger.error(e.getMessage(),e);
		}finally{
			if(dao != null) dao.close();
		}
    }
    
    @SuppressWarnings("unchecked")
	private static List<Passenger> getPassengerList(String vehicleId,BaseDao baseDao) throws Exception {
		String hql = "from Passenger t where t.vehicleId='"+vehicleId+"' and t.isDriver='0'";
		return (List<Passenger>)baseDao.findByHQL(hql);
	}
	
}
