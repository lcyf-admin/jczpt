package cn.lhkj.project.vehicle.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.lhkj.commons.base.BaseDao;
import cn.lhkj.commons.base.BaseDataCode;
import cn.lhkj.commons.tools.UUIDFactory;
import cn.lhkj.commons.util.CalendarUtil;
import cn.lhkj.commons.util.StringUtil;
import cn.lhkj.project.vehicle.entity.Passenger;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**前置卡口传来的数据处理逻辑*/
public class VehicleGather3001 {
	private BaseDao baseDao;
	
	public VehicleGather3001(BaseDao baseDao){
		this.baseDao = baseDao;
	}
	
	public void gather(JSONObject data,String backUrl,String ip) throws Exception{
		String passageaway = StringUtil.trim(data.getString("passageaway"));//通道编号（所有系统统一编号）
		String dataNum = StringUtil.trim(data.getString("dataNum"));//guid数据组号（若果车辆、人员分开推送，同一组号为同一组数据，必须为唯一）
		String passDate = StringUtil.trim(data.getString("passdate"));//数据采集时间
		Date passdate = CalendarUtil.toDate(passDate, "yyyy-MM-dd HH:mm:ss");
		
		JSONArray cardata = data.getJSONArray("cardata");//车辆数据
		JSONObject carObj = cardata.getJSONObject(0);
		String carNum = StringUtil.trim(carObj.getString("carNum"));
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MILLISECOND, calendar.get(Calendar.MILLISECOND)-60*5000);
		String sql = "select ID as ID from CSL_VEHICLE_BRAKE where 1=1 " +
				" and MONTH_ID="+CalendarUtil.getCurrentMonth()+"" +
				" and CAR_NUM='"+carNum+"' "+
				" and IP='"+ip+"' "+
				" and EQUIPMENT_ID='"+passageaway+"' "+
				" and INSERT_TIME between to_date('"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime())+"','yyyy-mm-dd hh24:mi:ss') and to_date('"+CalendarUtil.getCurrentTime()+"','yyyy-mm-dd hh24:mi:ss') "+
				" order by INSERT_TIME desc";
		List<Map<String,String>> templist = baseDao.findBySQL(sql);
		if(!StringUtil.isNull(templist)){
			dataNum = templist.get(0).get("ID");
		}
		JSONArray passengerdata  = data.getJSONArray("passengerdata");//乘客数据
		if(passengerdata == null || passengerdata.size() == 0) return;
		for(int i=0;i<passengerdata.size();i++){
			JSONObject assangeObj = passengerdata.getJSONObject(i);
			String idCard = StringUtil.trim(assangeObj.getString("cardNum"));
			if(StringUtil.isNull(idCard)) continue;
			Passenger passenger = new Passenger(UUIDFactory.getUUIDStr());
			passenger.setIsDriver("0");
			passenger.setVehicleId(dataNum);
			passenger.setUserName(StringUtil.trim(assangeObj.getString("userName")));
			passenger.setSex(StringUtil.trim(assangeObj.getString("sex")));
			passenger.setMinzu(StringUtil.trim(assangeObj.getString("minzu")));
			passenger.setCardNum(StringUtil.trim(assangeObj.getString("cardNum")));
			passenger.setBirthDate(StringUtil.trim(assangeObj.getString("birthDate")));
			passenger.setAddress(StringUtil.trim(assangeObj.getString("address")));
			passenger.setQianfa(StringUtil.trim(assangeObj.getString("qianfa")));
			passenger.setYouxiaoqi(StringUtil.trim(assangeObj.getString("youxiaoqi")));
			passenger.setPassdate(passdate);
			passenger.setInsertTime(new Date());
			
			String cardImg = StringUtil.trim(assangeObj.getString("cardImg"));
			if(cardImg.startsWith("http")){
				passenger.setCardImg(cardImg);
			}else if(cardImg.contains("cetc")){
				passenger.setCardImg("http://"+BaseDataCode.config.getFileServerIp()+":8889"+
						cardImg.substring(cardImg.indexOf("cetc")+4).replace("\\", "/"));
			}
			
			
			baseDao.execute("delete from CSL_PASSENGER where " +
					"MONTH_ID="+CalendarUtil.getCurrentMonth()+" " +
					"and VEHICLE_ID='"+dataNum+"' " +
					"and CARD_NUM='"+passenger.getCardNum()+"'");
			baseDao.save(passenger);
		}
	}
}
