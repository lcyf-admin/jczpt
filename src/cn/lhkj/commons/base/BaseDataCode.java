package cn.lhkj.commons.base;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import cn.lhkj.commons.util.CalendarUtil;
import cn.lhkj.commons.util.StringUtil;
import cn.lhkj.project.contrast.entity.ContrastPerson;
import cn.lhkj.project.contrast.entity.ContrastVehicle;
import cn.lhkj.project.equipment.entity.Equipment;
import cn.lhkj.project.lane.entity.Lane;
import cn.lhkj.project.person.entity.Person;
import cn.lhkj.project.system.entity.ConfigInfo;
import cn.lhkj.project.system.entity.Orgzon;
import cn.lhkj.project.system.service.ConfigService;
import cn.lhkj.project.vehicle.entity.Vehicle;

/**数据字典集合 */
public class BaseDataCode {
	
	public static final String version = "v1.81128";
	
	public static String indexStyle = "1";
	
	private static final Logger lpgger = Logger.getLogger(BaseDataCode.class);
	
	public static String[] motoTypes = {};/*{"爱立新","奥古斯塔","阿普利亚","安第斯","巴山","邦德","宝雕","豹王","本菱",
		"滨崎","比亚乔","贝纳利","巴贾杰","比摩塔","波速尔","布尔","贝塔","奔达","宝田","本一","白洋淀","北翔","长铃","长洪",
		"春风","重骑","重庆","川崎","创台","卡吉瓦","昌裕","川铃","川田","川井","川野","川豹","大运","大阳","大地鹰王","大福",
		"达飞尔","大江","大力神","大龙","东方","东宏","东力","东毅","飞肯","飞鹰","飞翎","飞狐","丰豪","风火轮","峰光","峰田",
		"佛斯弟","富先达","菲鹰","凤帅","光阳 ","冠军","光速","光威","广丰","国本","国威","豪爵","豪进","豪江","豪剑","哈力爱俊达",
		"海戈","海陵","豪宝","豪豹","豪达","豪福","豪门","豪诺","豪日","豪天","豪鹰","豪悦","好奔","好美","恒胜","轰轰烈","洪雅",
		"鸿通","鸿怡","鸿宇","鸿 华田","华威龙","华鹰","环松","黄川","火鸟","金洪","哈雷","胡思瓦纳","宏佳腾","哈里威","海渝","华仔",
		"济南铃木","嘉陵","建设","嘉冠","劲隆","金城","骥达","佳劲","嘉吉","嘉爵","嘉隆","嘉鹏","嘉渝","建豪","杰士达","金典","金峰",
		"金福","金冠","金舰","金捷","金轮","金马","金狮","劲锋","劲力","劲扬","巨能","爵康","金潮","佳娃","金霸王","金翌","劲野",
		"金山","金浪","金彭","金河星","卡西亚","凯尔","凯诺","康超","KTM","凯旋","轲博","力帆","隆鑫","力之星","林海","兰盾","雷利诺",
		"联统","麟龙","凌肯","龙嘉","洛嘉","凌本","铃田","陆嘉","蓝鹰","蓝野","陆爵","陆慷光洋","乐剑","美多","美田","蒙德王","明波",
		"木兰","摩托","麦科特","名雅","磨霸","南方","南雅","南益","诺顿","南爵","欧豹","鹏城","庞巴迪","轻骑","钱江","气派","群豪",
		"日雅","润腾","三雅","双庆","三铃","三本","三鑫","森科","山崎","速卡迪","三阳","胜利","三迪","上本","三野","圣峰","申仑","神蹼",
		"塞夫","天地游侠","天本","五羊","王野","万虎","望江","望龙","乌拉尔","新大洲本田","鑫源","喜马","先锋","新动力","新感觉","新鸽","新捷",
		"新陵","新世纪","鑫轮","迅龙","晓星","行星","新钿","希迪","兴邦","银钢","雅迪","雅奇","乙本","义鹰","益豪","银河","远方","远豪","粤豪",
		"雅马哈","粤华 ","意塔杰特","印地安","宇锋","豫永盛","宗申","正好","中能","众星","珠峰","珠江","尊隆","宗隆","兆润","鸿雁","华日","长春","松益"};*/
	/////////////////////////////////////////////////////////////////////////////////////////
	/**数据门通过的人员信息<站点编号，人员>*/
	private static Map<String,Person> personMap = new HashMap<String,Person>();
	public synchronized static void putPersonMap(String key,Person value){
		personMap.put(key, value);
		personMap.put(StringUtil.trim(value.getEquipmentId()), value);
	}
	public static Map<String,Person> getPersonMap(){
		return personMap;
	}
	
	/**预警车辆数据<站点编号，预警车辆>*/
	private static Map<String,ContrastVehicle> contrastVehicleMap = new HashMap<String,ContrastVehicle>();;
	public static Map<String,ContrastVehicle> getContrastVehicleMap(){
		return contrastVehicleMap;
	}
	public synchronized static void putContrastVehicleMap(String key,ContrastVehicle value){
		contrastVehicleMap.put(key, value);
		contrastVehicleMap.put(StringUtil.trim(value.getEquipmentId()), value);
	}
	
	/**今日前值卡口通行的预警车辆<车牌号,大数据平台比对结果>*/
	public static Map<String,String> todayVehicleShuntContrastMap = new HashMap<String, String>();
	
	/**预警人员数据<站点编号，预警人员>*/
	private static Map<String,ContrastPerson> contrastPersonMap = new HashMap<String,ContrastPerson>();
	public static Map<String,ContrastPerson> getContrastPersonMap(){
		return contrastPersonMap;
	}
	public synchronized static void putContrastPersonMap(String key,ContrastPerson value){
		contrastPersonMap.put(key, value);
		contrastPersonMap.put(StringUtil.trim(value.getEquipmentId()), value);
	}
	
	///////////////////////////////////////////////////////////////////
	/**系统的配置信心*/
	public static ConfigInfo config;
	/**所有的设备*/
	public static HashMap<String,Equipment> equipmentMap;
	/**分流文字列表*/
	public static List<String> showwayList;
	/**车道类型*/
	public static List<String> showwayType;
	/**所有的车道类型*/
	public static HashMap<String, List<String>> wayTypeMap;
	/**所有的数据门*/
	public static List<Equipment> dataDoorList;
	/**所有组织机构*/
	public static Map<String,Orgzon> orgMap;
	@SuppressWarnings("unchecked")
	public static void init(){
		BaseDao baseDao = null;
		try {
			showwayList = new ArrayList<String>();
			showwayType = new ArrayList<String>();
			config = ConfigService.getConfigInfo();
			equipmentMap = new HashMap<String,Equipment>();
			wayTypeMap = new HashMap<String,List<String>>();
			dataDoorList = new ArrayList<Equipment>();
			orgMap = new HashMap<String, Orgzon>();
			baseDao = new BaseDao();
			List<Equipment> list = (List<Equipment>)baseDao.findByHQL("from Equipment where 1=1 order by id");
			if(!StringUtil.isNull(list)){
				for(Equipment equipment : list){
					equipmentMap.put(equipment.getId(), equipment);
					if(!StringUtil.isNull(StringUtil.trim(equipment.getShowway())) && 
						equipment.getId().indexOf("CZHY00") == -1){
						showwayList.add(equipment.getShowway());
					}
				}
			}
			List<Lane> res = (List<Lane>)baseDao.findByHQL("from Lane where 1=1 order by id");
			if(!StringUtil.isNull(res)){
				for(Lane lane : res){
					if (StringUtil.isNull(wayTypeMap.get(lane.getLaneType()))) {
						ArrayList<String> laneList = new ArrayList<String>();
						laneList.add(lane.getId());
						wayTypeMap.put(lane.getLaneType(), laneList);
					}else{
						wayTypeMap.get(lane.getLaneType()).add(lane.getId());
					}

					if(!StringUtil.isNull(StringUtil.trim(lane.getLaneName()))){
						showwayType.add(lane.getLaneType());
					}
				}
			}
			
			String hql = "from Equipment e where e.type in" +
					"(select d.id from CSL_DICT_ITEM d where d.DICT_ID='WQUIP_TYPE' and d.CODES='SHUJUMEN')";
			dataDoorList =  (List<Equipment>)baseDao.findByHQL(hql);
			
			List<Orgzon> orgList = (List<Orgzon>)baseDao.findByHQL("from Orgzon where 1=1 order by id");
			for(Orgzon t: orgList){
				orgMap.put(t.getCodes(), t);
			}
		} catch (Exception e) {
			lpgger.error(e.getMessage(),e);
		}finally{
			if(baseDao != null){ baseDao.close(); }
		}
			
	}
	/**
	 * 判断车证合一所传的车道编号是否合法
	 * @param passageaway
	 * @return
	 */
	public static boolean isRight(String passageaway){
		for(Equipment em : equipmentMap.values()){
			if(em.getId().equals(passageaway) 
					&& StringUtil.isNotNull(em.getLaneId())){
				return true;
			}
		}
		return false;
	}

	///////////////////////////////////////////////////////////////////
	/**前置卡口和车道通过车辆信息<站点编号，车辆>*/
	public static Map<String,Vehicle> vehicleMap = new HashMap<String,Vehicle>();
	
	public synchronized static void setVehicle(Vehicle vehicle) {
		if(vehicle == null) return;
		vehicleMap.put(vehicle.getEquipmentId(), vehicle);
    	vehicleMap.put(vehicle.getEquipmentId().substring(0, 13), vehicle);
	}
	///////////////////////////////////////////////////////////////////
	/**获取数据月份列表*/
	public static List<String> getMonthList(){
		List<String> list = new ArrayList<String>();
		String value = CalendarUtil.format(new Date(), "yyyyMM");
		list.add(value);
		for(int i=0;i<100;i++){
			value = perMonth(value);
			list.add(value);
			if(value.equals("201705")) break;
		}
		return list;
	}
	
	private static String perMonth(String value){
		Integer y = Integer.valueOf(value.substring(0,4));
		Integer m = Integer.valueOf(value.substring(4,6));
		m = m-1;
		if(m==0){
			y = y-1;
			m = 12;
		}
		if(m<10){
			return y+"0"+m;
		}else{
			return y+""+m;
		}
	}
}
