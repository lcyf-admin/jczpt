package cn.lhkj.commons.scan.thread;


import java.util.HashMap;
import java.util.Random;

import org.apache.log4j.Logger;

import cn.lhkj.commons.base.BaseDataCode;
import cn.lhkj.commons.base.BaseDataDict;
import cn.lhkj.commons.tools.HttpPost;
import cn.lhkj.commons.tools.IdCardGenerator;
import cn.lhkj.commons.tools.UUIDFactory;
import cn.lhkj.commons.util.CalendarUtil;
import cn.lhkj.commons.util.StringUtil;
import cn.lhkj.project.equipment.entity.Equipment;

/**
 * 线程监听车证合一采集的车辆是否允许通过
 */
public class TestVehicleThread implements Runnable {
	
	private static final Logger logger = Logger.getLogger(TestVehicleThread.class);
	
	private static String strURL = "http://127.0.0.1:8888/jczpt/vehicle_czhy.action";
	@Override
	public void run(){
		try {
			while(true){
				try {
					Thread.sleep(4000);
				} catch (InterruptedException e) {}
				Random random = new Random();
				int i = random.nextInt(4);
				if(i == 0){
					post1001(null);
				}else if(i==1 || i==2){
					post5001(null);
				}else{
					post9001(null);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	private static void post9001(String carNum){
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("ver", "1.0");
		map.put("tranNO", UUIDFactory.getUUIDStr());
		map.put("tranType", "9001");
		map.put("sendTime", CalendarUtil.getCurrentTime());
		
		HashMap<String, Object> data = new HashMap<String, Object>();
		data.put("passageaway", randomPassageaway());
		data.put("dataNum", UUIDFactory.getUUIDStr());
		data.put("passdate", CalendarUtil.getCurrentTime());
		
		HashMap<String, Object> cardata = new HashMap<String, Object>();
		if(carNum == null) carNum = randomPlate();
		cardata.put("carNum", carNum);
		cardata.put("vehicleType", "3");
		cardata.put("plateColor", "2");
		cardata.put("carImg",randomVehicleImage());
		
		data.put("cardata", StringUtil.obj2Array(cardata));
		data.put("driverdata", "[]");
		data.put("passengerdata", "[]");
		map.put("data", data);
		String params = StringUtil.obj2json(map);
		String relust = HttpPost.sendPost(strURL, params);
		logger.debug("HttpPost--relust:"+relust);
		
		new Thread(new TestContrastVehicleThread((String)data.get("dataNum"))).start();//起一个测试线程 产生比中数据
	}
	
	private static void post5001(String carNum){
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("ver", "1.0");
		map.put("tranNO", UUIDFactory.getUUIDStr());
		map.put("tranType", "5001");
		map.put("sendTime", CalendarUtil.getCurrentTime());
		
		HashMap<String, Object> data = new HashMap<String, Object>();
		data.put("passageaway", "6531010100002-CZHY02");//randomPassageaway());
		data.put("dataNum", UUIDFactory.getUUIDStr());
		data.put("passdate", CalendarUtil.getCurrentTime());
		
		HashMap<String, Object> cardata = new HashMap<String, Object>();
		if(carNum == null) carNum = randomPlate();
		cardata.put("carNum", carNum);
		cardata.put("plateColor", "2");
		cardata.put("carImg",randomVehicleImage());
		cardata.put("vehicleType", "3");
		
		HashMap<String, Object> driverdata = new HashMap<String, Object>();
		driverdata.put("userName", randomName());
		driverdata.put("minzu", "汉");
//		driverdata.put("cardNum",IdCardGenerator.randomIdcard());
		driverdata.put("cardNum","320404197002136387");
		driverdata.put("sex", IdCardGenerator.gender((String)driverdata.get("cardNum")));
		driverdata.put("birthDate", IdCardGenerator.birth((String)driverdata.get("cardNum")));
		driverdata.put("address", "XXX省XX市XX县XXX小区XX号楼XX单元");
		driverdata.put("qianfa", "XXX公安局");
		driverdata.put("youxiaoqi", "20年");
		driverdata.put("cardImg", randomPersonImage());
		driverdata.put("result", "1");
		
		data.put("cardata", StringUtil.obj2Array(cardata));
		data.put("driverdata", StringUtil.obj2Array(driverdata));
		data.put("passengerdata", "[]");
		map.put("data", data);
		String params = StringUtil.obj2json(map);
		String relust = HttpPost.sendPost(strURL, params);
		logger.debug("HttpPost--relust:"+relust);
		
//		new Thread(new TestContrastVehicleThread((String)data.get("dataNum"))).start();//起一个测试线程 产生比中数据
	}
	
	private static void post1001(String carNum){
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("ver", "1.0");
		map.put("tranNO", UUIDFactory.getUUIDStr());
		map.put("tranType", "1001");
		map.put("sendTime", CalendarUtil.getCurrentTime());
		HashMap<String, String> data = new HashMap<String, String>();
		data.put("deviceIp", "127.0.0.1");
		data.put("laneId", BaseDataCode.config.getFrontId());
		if(carNum == null){
			data.put("plate", randomPlate());
		}else{
			data.put("plate", carNum);
		}
		data.put("color", "2");
		data.put("vehicleType", "3");
		data.put("vehicleImage", randomVehicleImage());
		data.put("plateImage", "");
		map.put("data", data);
		String params = StringUtil.obj2json(map);
		String relust = HttpPost.sendPost(strURL, params);
		logger.debug("HttpPost--relust:"+relust);
	}
	
	private static String randomPlate(){
		String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		Random random = new Random();
		String carNo = str.charAt(random.nextInt(26))+"";
		for(int i=0;i<6;i++){
			int num = random.nextInt(10);
			if (num < 5) {// 字母与数字的概率相同
				carNo += random.nextInt(10);
			} else {
				carNo += str.charAt(random.nextInt(26));
			}
		}
		return "新"+carNo;
	}
	
	/**随机车辆照片*/
	private static String randomVehicleImage(){
		Random random = new Random();
		String relust = "C:\\cetc\\";
		relust += random.nextInt(1);
		relust += random.nextInt(10);
		relust += ".jpg";
		return relust;
	}
	
	/**随机人员照片*/
	private static String randomPersonImage(){
		Random random = new Random();
		String relust = "C:\\cetc\\";
		relust += random.nextInt(1);
		relust += random.nextInt(10);
		relust += ".png";
		return relust;
	}
	
	/**
	 * 随机获取车道编号
	 * @return
	 */
	private static String randomPassageaway(){
		HashMap<String,Equipment> equipmentMap = BaseDataCode.equipmentMap;
		int size = equipmentMap.size();
		Random r = new Random();
		Equipment e = equipmentMap.get(equipmentMap.keySet().toArray()[r.nextInt(size)]);
		if(e.getId().indexOf("CZHY") != -1){
			return e.getId();
		}
		return randomPassageaway();
	}
	
	private static String randomName() {
        String[] name1 = {"赵", "钱", "孙", "李", "李", "李", "李", "李", "李", "李", "李", "李", "李", "李", "李",
                "李", "李", "李", "李", "李", "李", "李", "李", "李", "李", "李", "李", "李", "李", "李", "李",
                "李", "李", "李", "李", "李", "李", "李", "李", "李", "李", "李", "李", "李", "李", "李", "李",
                "李", "李", "周", "吴", "吴", "吴", "吴", "吴", "吴", "吴", "吴", "吴", "吴", "吴", "吴", "吴",
                "吴", "吴", "吴", "吴", "吴", "吴", "吴", "吴", "吴", "吴", "吴", "吴", "郑", "王", "冯", "陈",
                "诸", "卫", "蒋", "沈", "韩", "杨", "杨", "杨", "杨", "杨", "杨", "杨", "杨", "杨", "杨", "杨",
                "杨", "杨", "杨", "杨", "杨", "杨", "杨", "杨", "杨", "杨", "杨", "杨", "杨", "杨", "杨", "杨",
                "杨", "杨", "杨", "杨", "杨", "杨", "杨", "杨", "杨", "杨", "杨", "杨", "杨", "杨", "杨", "杨",
                "杨", "杨", "杨", "杨", "杨", "杨", "杨", "杨", "杨", "杨", "杨", "杨", "杨", "杨", "杨", "杨",
                "杨", "杨", "王", "王", "王", "王", "王", "王", "王", "王", "王", "王", "王", "王", "王", "王",
                "王", "王", "王", "王", "王", "王", "王", "王", "王", "王", "王", "王", "王", "王", "王", "王",
                "王", "王", "王", "王", "王", "王", "王", "王", "张", "张", "张", "张", "张", "张", "张", "张",
                "于", "于", "于", "于", "于", "于", "于", "于", "于", "于", "于", "于", "于", "于", "于", "于",
                "余", "余", "余", "余", "余", "余", "余", "余", "余", "余", "余", "余", "余", "余", "余", "余",
                "张", "张", "张", "张", "张", "张", "张", "张", "张", "张", "张", "张", "张", "张", "张", "张",
                "张", "张", "陈", "陈", "陈", "陈", "陈", "陈", "陈", "陈", "陈", "陈", "陈", "陈", "陈", "陈",
                "朱", "秦", "尤", "许", "何", "吕", "施", "张", "孔", "曹", "严", "华", "金", "魏", "陶", "姜",
                "戚", "谢", "邹", "喻", "柏", "水", "窦", "章", "云", "苏", "潘", "葛", "奚", "范", "彭", "郎",
                "鲁", "韦", "昌", "马", "苗", "凤", "花", "方", "俞", "任", "袁", "柳", "酆", "鲍", "史", "唐",
                "费", "廉", "岑", "薛", "雷", "贺", "倪", "汤", "滕", "殷", "罗", "毕", "郝", "邬", "安", "常",
                "乐", "于", "时", "傅", "皮", "卡", "齐", "康", "伍", "余", "元", "卜", "顾", "孟", "平", "黄",
                "和", "穆", "萧", "尹", "姚", "邵", "堪", "汪", "祁", "毛", "禹", "狄", "米", "贝", "明", "臧",
                "计", "伏", "成", "戴", "谈", "宋", "茅", "庞", "熊", "纪", "舒", "屈", "项", "祝", "董", "粱",
                "杜", "阮", "蓝", "闵", "席", "季", "麻", "强", "贾", "路", "娄", "危", "江", "童", "颜", "郭",
                "梅", "盛", "林", "刁", "钟", "徐", "邱", "骆", "高", "夏", "蔡", "田", "樊", "胡", "凌", "霍",
                "虞", "万", "支", "柯", "咎", "管", "卢", "莫", "经", "房", "裘", "缪", "干", "解", "应", "宗",
                "丁", "宣", "贲", "邓", "郁", "单", "杭", "洪", "包", "诸", "左", "石", "崔", "吉", "钮", "龚",
                "程", "嵇", "邢", "滑", "裴", "陆", "荣", "翁", "荀", "羊", "於", "惠", "甄", "魏", "家", "封",
                "芮", "羿", "储", "靳", "汲", "邴", "糜", "松", "井", "段", "富", "巫", "乌", "焦", "巴", "弓",
                "牧", "隗", "山", "谷", "车", "侯", "宓", "蓬", "全", "郗", "班", "仰", "秋", "仲", "伊", "宫",
                "宁", "仇", "栾", "暴", "甘", "钭", "厉", "戎", "祖", "武", "符", "刘", "景", "詹", "束", "龙",
                "司马", "上官", "欧阳", "夏侯", "诸葛", "东方", "尉迟", "公孙", "长孙", "慕容", "司徒", "西门"
        };
        String[] name2 = {"超", "媛", "念", "立", "思", "嘉", "雨", "伟", "权", "秋", "佩", "雅", "联", "如", "渠", "保", "室", "铜", "梧", "胤", "昱", "佳", "伊", "校", "诗", "节", "如",
                "阁", "耕", "宫", "古", "谷", "观", "桂", "贵", "国", "广", "冠", "汉", "翰", "航", "杭", "海", "豪", "浩", "皓", "和", "河", "贺", "恒", "弘", "虹", "宏", "红",
                "厚", "鹄", "虎", "华", "欢", "怀", "辉", "惠", "会", "奇", "吉", "骥", "嘉", "佳", "甲", "稼", "江", "坚", "剑", "锦", "经", "镜", "界", "竞", "介", "京", "建",
                "净", "精", "敬", "静", "靖", "津", "进", "菁", "景", "炯", "驹", "举", "炬", "君", "俊", "军", "骏", "郡", "峻", "恺", "楷", "康", "可", "克", "珂", "逵", "魁",
                "阔", "昆", "奎", "宽", "况", "乐", "雷", "岭", "廉", "霖", "麟", "灵", "利", "良", "联", "烈", "罗", "陵", "梁", "立", "礼", "力", "莉", "烁", "隆", "龙", "禄",
                "璐", "露", "律", "茂", "梦", "密", "铭", "明", "绵", "妙", "默", "木", "能", "年", "宁", "念", "怒", "庞", "佩", "培", "朋", "鹏", "屏", "平", "魄", "珀", "璞",
                "奇", "琦", "齐", "启", "谦", "乾", "茜", "倩", "芹", "琴", "青", "卿", "秋", "权", "求", "情", "渠", "全", "荃", "群", "泉", "然", "让", "仁", "任", "荣", "儒",
                "锐", "若", "瑞", "三", "瑟", "森", "韶", "绍", "尚", "商", "珊", "善", "生", "升", "声", "晟", "胜", "盛", "诗", "时", "石", "实", "凇", "慎", "设", "守", "随",
                "世", "寿", "仕", "余", "帅", "双", "朔", "硕", "水", "誓", "适", "书", "舒", "殊", "顺", "思", "嗣", "似", "松", "颂", "素", "岁", "棠", "泰", "腾", "添", "铁",
                "同", "桐", "童", "彤", "团", "涂", "图", "土", "万", "旺", "望", "王", "闻", "威", "薇", "嵬", "伟", "卫", "蔚", "文", "微", "巍", "玮", "为", "畏", "吾", "午",
                "西", "熙", "玺", "仙", "先", "孝", "湘", "祥", "行", "献", "享", "效", "兴", "夏", "宣", "协", "向", "校", "轩", "瑕", "衔", "筱", "羡", "相", "香", "贤", "翔",
                "杏", "新", "信", "幸", "心", "星", "绣", "秀", "欣", "鑫", "兴", "行", "雄", "许", "炫", "雪", "学", "旭", "璇", "勋", "萱", "迅", "亚", "雅", "扬", "耀", "彦",
                "延", "岩", "炎", "永", "砚", "演", "焱", "洋", "阳", "曜", "耀", "夜", "译", "逸", "伊", "义", "艺", "意", "异", "怡", "翼", "毅", "银", "瑛", "仪", "易", "寅",
                "印", "苡", "野", "业", "英", "璎", "盈", "颖", "影", "雍", "勇", "悠", "由", "游", "佑", "友", "瑜", "遇", "玉", "岳", "元", "宇", "愚", "钰", "裕", "郁", "于",
        };
        int len1 = name1.length - 1;
        int len2 = name2.length - 1;
        int random1 = (int) (Math.random() * len1);
        int random2 = (int) (Math.random() * len2);
        int random2_1 = (int) (Math.random() * len2);

        String name = "";
        if (random1 < 256) {
            int randomN = (int) (Math.random() * 2);
            if (randomN == 1) {
                name = name1[random1] + name2[random2];
            } else {
                name = name1[random1] + name2[random2] + name2[random2_1];
            }
        } else {
            name = name1[random1] + name2[random2] + name2[random2_1];
        }
        return name;
    }
	
	public static void main(String[] args) {
		BaseDataDict.init();//数据字典初始化
		BaseDataCode.init();//将设备信息放入内存
//		post1001(null);
		post9001("新A0009");
//		post5001(null);
	}
}
