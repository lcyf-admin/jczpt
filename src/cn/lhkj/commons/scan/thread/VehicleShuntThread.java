package cn.lhkj.commons.scan.thread;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.apache.log4j.Logger;
import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import cn.lhkj.commons.base.BaseDao;
import cn.lhkj.commons.base.BaseDataCode;
import cn.lhkj.commons.tools.HttpPost;
import cn.lhkj.commons.tools.HttpPostSANHUI;
import cn.lhkj.commons.tools.UUIDFactory;
import cn.lhkj.commons.util.CalendarUtil;
import cn.lhkj.commons.util.StringUtil;
import cn.lhkj.project.contrast.entity.ContrastVehicle;
import cn.lhkj.project.equipment.entity.Equipment;
import cn.lhkj.project.system.entity.Orgzon;
import cn.lhkj.project.vehicle.entity.VehicleShunt;

/**前置卡口通过车辆比对线程 */
public class VehicleShuntThread implements Runnable {
	
	private static final Logger logger = Logger.getLogger(VehicleShuntThread.class);
	
	private VehicleShunt t;
	private String backUrl;
	private String tranNO;
	
	public VehicleShuntThread(VehicleShunt t,String tranNO){
		this.t = t;
		this.backUrl = t.getBackUrl();
		this.tranNO = tranNO;
	}
	
	@Override
	public void run(){
		BaseDao dao = null;
		if(t == null) return;
		try {
			if(StringUtil.isNull(t.getPlate()) || "无车牌".equals(t.getPlate())) return;
			
			String strURL = BaseDataCode.config.getComparBigDataURL();
			if("1".equals(BaseDataCode.config.getTestModel())) return;
			if(StringUtil.isNull(strURL)) return;
			
			Equipment em = BaseDataCode.equipmentMap.get(t.getEqipmentId());
			if(em == null) return;
			if(StringUtil.isNull(backUrl)){
				backUrl = em.getUrl();
				t.setBackUrl(backUrl);
			}
			//和大数据平台比对抓拍到的车辆
			Map<String,String> postMap = new HashMap<String,String>();
			postMap.put("plate", t.getPlate());
			postMap.put("plate_color", HttpPostSANHUI.dealWithPlateColor(t.getPlateColor()));
			if(StringUtil.trim(postMap.get("plate_color")).contains("黄")){
				if("1".equals(BaseDataCode.config.getIsShunt())){ sendMessageNormal(t,"normal"); }//黄牌直接分流
				return;//黄牌车不需要比对
			}
			String relust = BaseDataCode.todayVehicleShuntContrastMap.get(t.getPlate());
			String codes = t.getEqipmentId().substring(0, 13); //获取当前组织机构
			Orgzon org = BaseDataCode.orgMap.get(codes);
			if(StringUtil.isNull(relust)){
				relust = HttpPostSANHUI.sendPost(strURL, "plate", postMap, org);//和大数据平台比对抓拍到的车辆
			}
			if(StringUtil.isNull(relust)){
				if("1".equals(BaseDataCode.config.getIsShunt())){ sendMessageNormal(t,"normal"); }//比对失败分流
				return;
			}	
			try {
				JSONObject jsonObject = JSONObject.fromObject(relust);
				String flag = jsonObject.getString("flag");
				JSONArray datas = jsonObject.getJSONArray("data");
				if(datas != null && datas.size() >0){//根据data是否有数据才能作为是否黑名单的依据
					flag = "black";
				}else{
					if(!"white".equals(flag)){
						flag = "normal";
					}
				}
				
				t.setStates("0");
				t.setRelust("普通车辆");
				dao = new BaseDao();
				if("normal".equals(flag)){
					t.setRelust("普通车辆");
					if("1".equals(BaseDataCode.config.getIsShunt())){ sendMessageNormal(t,"normal"); }//普通车辆也分流
				}else if("white".equals(flag)){
					t.setRelust("红名单车辆");
					if("1".equals(BaseDataCode.config.getIsShunt())){ sendMessageNormal(t,"red"); }//红名单车辆也分流
				}else if("black".equals(flag)){//黑名单车辆
					t = addContrastVehicle(jsonObject, dao, t, em);
					BaseDataCode.todayVehicleShuntContrastMap.put(t.getPlate(), relust);
				}
				dao.execute("update CSL_VEHICLE_SHUNT set relust='"+t.getRelust()+"',BACK_URL='"+backUrl+"' " +
						"where MONTH_ID='"+t.getMonthId()+"' and ID='"+t.getId()+"'");
				dao.execute("update CSL_VEHICLE set relust='"+t.getRelust()+"',BACK_URL='"+backUrl+"' " +
						"where MONTH_ID='"+t.getMonthId()+"' and ID='"+t.getId()+"'");
			}catch (JSONException e) {
				logger.error("调用三汇车辆比对接口错误，解析json错误："+relust);
				logger.error("调用三汇车辆比对接口错误，解析json错误："+e.getMessage());
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}finally{
			if(dao != null){ dao.close(); }
		}
	}
	
	/**
	 * 增加预警车辆
	 * @param data
	 * @param dao
	 * @param t
	 */
	private VehicleShunt addContrastVehicle(JSONObject jsonObject, BaseDao dao, VehicleShunt t,Equipment em) throws Exception{
		JSONArray datas = jsonObject.getJSONArray("data");
		if(datas != null && datas.size() >0){
			JSONObject data = datas.getJSONObject(0);
			String car_type = StringUtil.trim(data.getString("car_type"));//比中返回的车辆品牌
			if(StringUtil.containsFyzzy(BaseDataCode.motoTypes, car_type)){//比中得到是摩托车数据
				return t;
			}
			//if(BaseDataCode.getContrasBrake().containsKey(t.getPlate())){//今日已通行过的黑名单车-不再预警
			//	return t;
			//}
			//ContrastVehicle type = new ContrastVehicle(data.getString("uuid"));//车辆预警信息
			ContrastVehicle type = new ContrastVehicle(UUIDFactory.getUUIDStr());//车辆预警信息
			type.setVehicleId(t.getId());
			type.setCarNum(data.getString("plate"));
			type.setVehicleType(car_type);
			type.setIdcard(data.getString("sfzh"));
			type.setNames(data.getString("name"));
			type.setAddress(data.getString("identlocation"));
			type.setLabel(data.getString("tag"));
			type.setDescribe(data.getString("tag"));
			type.setTag(data.getString("datasource"));
			type.setPersonPhoto(data.getString("person_photo"));
			type.setAction(data.getString("action"));
			type.setBkArea(data.getString("bkfwmc"));
			type.setYjArea(data.getString("yjfwmc"));
			type.setPassdate(t.getPassdate());
			type.setInsertTime(new Date());
			type.setLocation("前置卡口");
			type.setSource("本站产生");
			type.setEquipmentId(em.getId());
			type.setAreaCode(em.getId().substring(0, 6));
			type.setCheckPointId(em.getId().substring(0, 13));
			type.setGatherPhotoURL(t.getLocalVehicleImage());
			dao.saveOrUpdate(type);
			sendMessage(em, t);//向大屏发送信息
			t.setRelust("黑名单车辆");
			t.setStates("1");
			BaseDataCode.putContrastVehicleMap(em.getId().substring(0, 13), type);
		}
		return t;
	}
	
	/**
	 * 比中车辆 向分流屏发送信息
	 * @param em
	 * @param t
	 * @throws Exception
	 */
	private void sendMessage(Equipment em,VehicleShunt t){
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("tranNO", tranNO);
		map.put("ver", "1.0");
		map.put("flag", "black");
		map.put("tranType", "2001");
		map.put("sendTime", CalendarUtil.getCurrentTime());
		map.put("key", UUIDFactory.getUUIDStr());
		if (BaseDataCode.showwayType.contains("特检车道")) {
			map.put("data", t.getPlate() + " "+getLane("特检车道"));
		}else{
			if(!StringUtil.isNull(em.getShowway())){
				map.put("data", t.getPlate() + " "+em.getShowway());
			}else {
				map.put("data", t.getPlate() + " "+getRandomLane());
			}
		}	
		String par = StringUtil.obj2json(map);
		postFenLiu(backUrl, par);
	}
	
	/**
	 * 一般车辆向分流屏发送信息
	 * @param em
	 * @param t
	 * @throws Exception
	 */
	private void sendMessageNormal(VehicleShunt t,String flag){
		if(BaseDataCode.showwayList.size() == 0) return;
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("tranNO", tranNO);
		map.put("ver", "1.0");
		map.put("flag", flag);
		map.put("tranType", "2001");
		map.put("sendTime", CalendarUtil.getCurrentTime());
		map.put("key", UUIDFactory.getUUIDStr());
		String color = StringUtil.trim(t.getPlateColor());//车牌颜色
		String type = StringUtil.trim(t.getVehicleType());//车辆类型
		String keche = "大型客车";
		String huoche = "货车，小货车";
		String jiaoche = "未知，轿车，面包车，行人，二轮车，三轮车,小车";
		
		if(keche.contains(type)){//客车
			if (BaseDataCode.showwayType.contains("客车车道")) {
				map.put("data", t.getPlate() + " "+getLane("客车车道"));
			}else{
				map.put("data", t.getPlate() + " "+getRandomLane());
			}
			postFenLiu(backUrl, StringUtil.obj2json(map));
			return;
		}
		
		if(huoche.contains(type)) {//货车
			if (BaseDataCode.showwayType.contains("货车车道")) {
				map.put("data", t.getPlate() + " "+getLane("货车车道"));
			}else{
				map.put("data", t.getPlate() + " "+getRandomLane());
			}
			postFenLiu(backUrl, StringUtil.obj2json(map));
			return;
		}
		
		//针对富士不能区分车辆类型，如果是黄牌，默认是大车，走货车车道，如果没有货车车道，走客车车道     add by jkk
		if(color.contains("黄")){//黄色车牌
			if (BaseDataCode.showwayType.contains("货车车道")) {
				map.put("data", t.getPlate() + " "+getLane("货车车道"));
			}else if (BaseDataCode.showwayType.contains("客车车道")) {
				map.put("data", t.getPlate() + " "+getLane("客车车道"));
			}else{
				map.put("data", t.getPlate() + " "+getRandomLane());
			}
			postFenLiu(backUrl, StringUtil.obj2json(map));
			return;
		}
		
		if (jiaoche.contains(type)) {//轿车
			if (BaseDataCode.showwayType.contains("轿车车道")) {
				map.put("data", t.getPlate() + " "+getLane("轿车车道"));
			}else{
				map.put("data", t.getPlate() + " "+getRandomLane());
			}
			postFenLiu(backUrl, StringUtil.obj2json(map));
			return;
		}
		
		//其他情况
		map.put("data", t.getPlate() + " "+getRandomLane());
		postFenLiu(backUrl, StringUtil.obj2json(map));
	}
	
	/**返回随机分流信息*/
	@SuppressWarnings("unchecked")
	private String getRandomLane(){
		if(BaseDataCode.showwayList.size() == 0) return null;
		BaseDao baseDao = null;
		Random r = new Random();
		if (!StringUtil.isNull(BaseDataCode.showwayList)) {
			String randomLane = BaseDataCode.showwayList.get(r.nextInt(BaseDataCode.showwayList.size()));
			if (BaseDataCode.showwayType.contains("特检车道")) {
				String tejian = BaseDataCode.wayTypeMap.get("特检车道").get(0);
				List<Equipment> list;
				try {
					baseDao = new BaseDao();
					String hql = "from Equipment e where 1=1 ";
					if(!StringUtil.isNull(tejian)){
						hql += "and e.laneId = '"+tejian+"' order by id";
					}
					list = (List<Equipment>)baseDao.findByHQL(hql);
					if (!StringUtil.isNull(list)) {//特检和快检车道分流展示
						if (list.get(0).getShowway().equals(randomLane) || StringUtil.isNull(randomLane)) {
							return getRandomLane();
						}else {
							return randomLane;
						}
					}
				} catch (Exception e) {
					logger.info(e);
					return randomLane;
				} finally{
					if(baseDao != null){ baseDao.close(); }
				}
			}else{
				return randomLane;
			}			
		}else {
			return null;
		}
		return null;	
	}
	
	/**返回分流信息*/
	@SuppressWarnings("unchecked")
	private String getLane(String name){
		BaseDao baseDao = null;
		Random r = new Random();
		List<Equipment> list;
		try {
			List<String> laneId = BaseDataCode.wayTypeMap.get(name);
			baseDao = new BaseDao();
			String hqlString = "from Equipment e where 1=1 ";
			for (int i = 0; i < laneId.size(); i++) {
				if (i==0) {
					hqlString += " and e.laneId in ('"+laneId.get(i)+"' ";
				}else {
					hqlString += ",'"+laneId.get(i)+"'";
				}
			}
			list = (List<Equipment>)baseDao.findByHQL(hqlString+") order by id");
			if (!StringUtil.isNull(list)) {
				return BaseDataCode.equipmentMap.get(list.get(r.nextInt(list.size())).getId()).getShowway();
			}
		} catch (Exception e) {
			logger.info(e);
			return BaseDataCode.showwayList.get(r.nextInt(BaseDataCode.showwayList.size()));
		} finally{
			if(baseDao != null){ baseDao.close(); }
		}
		return null;
	}
	
	/**向大屏发送分流信息*/
	private static String postFenLiu(String backUrl,String par){
		logger.info("【"+backUrl+"】【"+par+"】");
		return HttpPost.sendPost(backUrl, par);
	}
}
