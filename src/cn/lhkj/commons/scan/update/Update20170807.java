package cn.lhkj.commons.scan.update;

import java.sql.SQLException;
import java.sql.Statement;
import cn.lhkj.commons.base.BaseDao;


/**
 * 本次更新内容：
 * 1、CSL_ORG表中增加detail_name字段
 */
public class Update20170807 {
	
	private BaseDao baseDao;
	
	public Update20170807(BaseDao baseDao){
		this.baseDao = baseDao;
	}
	
	public void execute(){
		/////////////////wx//////////////////
		runSQL("alter table CSL_PERSON_CONTRABAND add REMARK varchar2(128)");
		runSQL("alter table CHECK_PERSON_CONTRABAND add contraband_photo_url varchar2(200)");
		runSQL("alter table CSL_PERSON add IS_CHECK varchar2(100) default 0");
		runSQL("alter table CSL_PERSON modify IS_CHECK varchar2(100) default 0");
		
		runSQL("alter table CSL_CONTRAST_PERSON add PHONE varchar2(100)");
		runSQL("alter table CSL_CONTRAST_PERSON add MAC varchar2(100)");
		runSQL("alter table CSL_CONTRAST_PERSON add IMEI varchar2(100)");
		runSQL("alter table CSL_CONTRAST_PERSON add IMSI varchar2(100)");
		
		runSQL("alter table CSL_LANE add LANETYPE varchar2(100)");
		//////////////20171031 modify by wx//////////////////
		runSQL("alter table CHECK_VEHICLE add vnature varchar2(1000)");
		runSQL("alter table CHECK_VEHICLE add vunit varchar2(1000)");
		runSQL("alter table CHECK_VEHICLE add acheck varchar2(1000)");
		runSQL("alter table CHECK_VEHICLE add akeason varchar2(1000)");
		runSQL("alter table CHECK_VEHICLE add acontent varchar2(1000)");
		////////////////////////////////////
		
		runSQL("alter table CSL_PERSON add (INSERT_TIME date default sysdate)");
		runSQL("alter table CSL_PERSON_WARNING add (INSERT_TIME date default sysdate)");
		runSQL("alter table CSL_CONTRAST_VEHICLE add (VEHICLE_ID VARCHAR2(64))");
		
		//增大身份证地址的长度
		runSQL("alter table CSL_PASSENGER modify ADDRESS VARCHAR2(500)");
		runSQL("alter table CSL_PERSON modify ADDRESS VARCHAR2(500)");
		//预警车辆和人员增加采集的设备编号
		runSQL("alter table CSL_CONTRAST_PERSON add EQUIPMENT_ID varchar2(100)");
		runSQL("alter table CSL_CONTRAST_VEHICLE add EQUIPMENT_ID varchar2(100)");
		
		runSQL("alter table CSL_ORG add X varchar2(20)");
		runSQL("alter table CSL_ORG add Y varchar2(20)");
		runSQL("alter table CSL_ORG add DETAIL_NAME varchar2(300)");
		
		//增大数据门告警数据长度
		runSQL("alter table CSL_PERSON_WARNING modify RESERVED1 VARCHAR2(200)");
		runSQL("alter table CSL_PERSON_WARNING modify RESERVED2 VARCHAR2(200)");
		runSQL("alter table CSL_PERSON_WARNING modify RESERVED2 VARCHAR2(200)");
		runSQL("alter table CSL_PERSON_WARNING modify RESERVED3 VARCHAR2(200)");
		runSQL("alter table CSL_PERSON_WARNING modify RESERVED4 VARCHAR2(200)");
		//增大车辆类型长度
		runSQL("alter table CSL_CONTRAST_VEHICLE modify VEHICLE_TYPE VARCHAR2(200)");
		//增大车牌ID字段程度
		runSQL("alter table CSL_VEHICLE modify PLATE_COLOR_ID VARCHAR2(20)");
		runSQL("alter table CSL_VEHICLE_BRAKE modify PLATE_COLOR_ID VARCHAR2(20)");
		
		//删除无用的表
		runSQL("drop table CSL_CHECK_VEHICLE");
		runSQL("drop table CSL_CHECK_PERSON");
		runSQL("drop table CSL_PHONE");
		
		runSQL("alter table CSL_VEHICLE_SHUNT add IP varchar2(20)");
		runSQL("alter table CSL_VEHICLE_BRAKE add IP varchar2(20)");
		runSQL("alter table CSL_VEHICLE add IP varchar2(20)");
		
		runSQL("alter table CSL_VEHICLE_SHUNT add BACK_URL varchar2(200)");
		runSQL("alter table CSL_VEHICLE_BRAKE add BACK_URL varchar2(200)");
		runSQL("alter table CSL_VEHICLE add BACK_URL varchar2(200)");
		runSQL("alter table CSL_VEHICLE_SHUNT modify BACK_URL VARCHAR2(200)");
		runSQL("alter table CSL_VEHICLE_BRAKE modify BACK_URL VARCHAR2(200)");
		runSQL("alter table CSL_VEHICLE modify BACK_URL VARCHAR2(200)");
		
		//增加是否上传标识
		runSQL("alter table CHECK_PERSON add IS_UPLOAD varchar2(1000) default 0");
		runSQL("alter table CHECK_VEHICLE add IS_UPLOAD varchar2(1000) default 0");
		runSQL("alter table CSL_VEHICLE_BRAKE add IS_UPLOAD varchar2(1000) default 0");
		runSQL("alter table CSL_VEHICLE_SHUNT add IS_UPLOAD varchar2(1000) default 0");
		runSQL("alter table CSL_PERSON add IS_UPLOAD varchar2(1000) default 0");
		runSQL("alter table CSL_CONTRAST_PERSON add IS_UPLOAD varchar2(1000) default 0");
		runSQL("alter table CSL_CONTRAST_VEHICLE add IS_UPLOAD varchar2(1000) default 0");
		//上传标识增长字段
		runSQL("alter table CHECK_PERSON modify IS_UPLOAD varchar2(1000)");
		runSQL("alter table CHECK_VEHICLE modify IS_UPLOAD varchar2(1000)");
		runSQL("alter table CSL_VEHICLE_BRAKE modify IS_UPLOAD varchar2(1000)");
		runSQL("alter table CSL_VEHICLE_SHUNT modify IS_UPLOAD varchar2(1000)");
		runSQL("alter table CSL_PERSON modify IS_UPLOAD varchar2(1000)");
		runSQL("alter table CSL_CONTRAST_PERSON modify IS_UPLOAD varchar2(1000)");
		runSQL("alter table CSL_CONTRAST_VEHICLE modify IS_UPLOAD varchar2(1000)");
		
		runSQL("alter table CSL_VEHICLE_BRAKE add USER_NAME varchar2(100)");
		runSQL("alter table CSL_VEHICLE_BRAKE add SEX varchar2(10)");
		runSQL("alter table CSL_VEHICLE_BRAKE add MINZU varchar2(100)");
		runSQL("alter table CSL_VEHICLE_BRAKE add CARD_NUM varchar2(30)");
		runSQL("alter table CSL_VEHICLE_BRAKE add BIRTH_DATE varchar2(30)");
		runSQL("alter table CSL_VEHICLE_BRAKE add ADDRESS varchar2(500)");
		runSQL("alter table CSL_VEHICLE_BRAKE add QIANFA varchar2(100)");
		runSQL("alter table CSL_VEHICLE_BRAKE add YOUXIAOQI varchar2(50)");
		runSQL("alter table CSL_VEHICLE_BRAKE add CARD_IMG varchar2(500)");
		runSQL("alter table CSL_VEHICLE_BRAKE add LOCAL_CARD_IMG varchar2(500)");
		
		runSQL("alter table CSL_VEHICLE add USER_NAME varchar2(100)");
		runSQL("alter table CSL_VEHICLE add SEX varchar2(10)");
		runSQL("alter table CSL_VEHICLE add MINZU varchar2(100)");
		runSQL("alter table CSL_VEHICLE add CARD_NUM varchar2(30)");
		runSQL("alter table CSL_VEHICLE add BIRTH_DATE varchar2(30)");
		runSQL("alter table CSL_VEHICLE add ADDRESS varchar2(500)");
		runSQL("alter table CSL_VEHICLE add QIANFA varchar2(100)");
		runSQL("alter table CSL_VEHICLE add YOUXIAOQI varchar2(50)");
		runSQL("alter table CSL_VEHICLE add CARD_IMG varchar2(500)");
		
		//修改人员视图	
		runSQL("CREATE OR REPLACE VIEW CSL_USER_VIEW AS "+
				"SELECT U.ID AS ID "+
				",U.ACCOUNT AS ACCOUNT "+
				",U.PWD AS PWD "+
				",U.NAMES AS NAMES "+
				",U.GENDER AS GENDER "+
				",U.SERIALS AS SERIALS "+
				",U.EMAIL AS EMAIL "+
				",U.STATUS AS STATUS "+
				",U.CREATE_TIME AS CREATE_TIME "+
				",U.UPDATE_TIME AS UPDATE_TIME "+
				",U.LOGIN_TIME AS LOGIN_TIME "+
				",U.UPDATER AS UPDATER "+
				",U.ORG_ID AS ORG_ID "+
				",D.NAMES AS ORG_NAME "+
				",D.DETAIL_NAME AS ORG_DETAIL_NAME "+
				",D.PRIO AS PRIO "+
				",D.CODES AS ORG_CODES "+
				",D.X AS X "+
				",D.Y AS Y "+
				"FROM CSL_USER U LEFT JOIN CSL_ORG D ON(D.ID=U.ORG_ID) "+
				"ORDER BY D.PRIO,U.SERIALS");
		
		runSQL("alter table CHECK_PERSON add HCJL varchar2(200)");
		runSQL("alter table CHECK_PERSON add DIRECTION varchar2(100)");
		runSQL("alter table CHECK_PERSON add DIRECTION_REASON varchar2(100)");
		runSQL("alter table CHECK_PERSON add COPNUM varchar2(100)");
		
		runSQL("alter table CHECK_VEHICLE add PLATECOLOR varchar2(20)");
		runSQL("alter table CHECK_VEHICLE add VEHICLECOLOR varchar2(100)");
		runSQL("alter table CHECK_VEHICLE add VEHICLETYPE varchar2(100)");
		runSQL("alter table CHECK_VEHICLE add DIRECTION varchar2(100)");
		runSQL("alter table CHECK_VEHICLE add DESTINATION varchar2(100)");
		runSQL("alter table CHECK_VEHICLE add RESIDENCE_TIME varchar2(100)");
		runSQL("alter table CHECK_VEHICLE add REASON varchar2(100)");
		runSQL("alter table CHECK_VEHICLE add ENTERER varchar2(100)");
		runSQL("alter table CHECK_VEHICLE_PASSENGER add NAME varchar2(200)");
		
		runSQL("alter table CSL_EQUIPMENT add SHOWWAY varchar2(200)");
		runSQL("alter table CSL_PERSON_WARNING add CAPTURE_TIME date");
		
		//创建索引
		runSQL("create index CHECK_PERSON_IS_UPLOAD on CHECK_PERSON (IS_UPLOAD)");
		runSQL("create index CHECK_VEHICLE_IS_UPLOAD on CHECK_VEHICLE (IS_UPLOAD)");
		runSQL("create index CSL_VEHICLE_BRAKE_IS_UPLOAD on CSL_VEHICLE_BRAKE (IS_UPLOAD)");
		runSQL("create index CSL_VEHICLE_SHUNT_IS_UPLOAD on CSL_VEHICLE_SHUNT (IS_UPLOAD)");
		runSQL("create index CSL_PERSON_IS_UPLOAD on CSL_PERSON (IS_UPLOAD)");
		runSQL("create index CSL_CONTRAST_PERSON_IS_UPLOAD on CSL_CONTRAST_PERSON (IS_UPLOAD)");
		runSQL("create index CSL_CONTRAST_VEHICLE_IS_UPLOAD on CSL_CONTRAST_VEHICLE (IS_UPLOAD)");
		
		runSQL("create index CSL_PERSON_IDCARD on CSL_PERSON (IDCARD)");
		runSQL("create index CSL_VEHICLE_CAR_NUM on CSL_VEHICLE (CAR_NUM)");
		runSQL("create index CSL_VEHICLE_CARD_NUM on CSL_VEHICLE (CARD_NUM)");
		runSQL("create index CSL_VEHICLE_BRAKE_CAR_NUM on CSL_VEHICLE_BRAKE (CAR_NUM)");
		runSQL("create index CSL_VEHICLE_BRAKE_CARD_NUM on CSL_VEHICLE_BRAKE (CARD_NUM)");
		runSQL("create index CSL_PASSENGER_VEHICLE on CSL_PASSENGER (VEHICLE_ID)");
		
	}
	
	private void runSQL(String sql){
		Statement stmt = null;
		try{
			stmt = baseDao.getConn().createStatement();
			stmt.execute(sql);
		}catch (Exception e){
		}finally{
			try {
				if(stmt != null) stmt.close();
			} catch (SQLException e) { }
		}
	}
	
}
