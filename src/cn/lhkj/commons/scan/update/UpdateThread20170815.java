package cn.lhkj.commons.scan.update;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;

import cn.lhkj.commons.base.BaseDao;


/**
 * 本次更新内容：
 */
public class UpdateThread20170815 {
	
	private BaseDao baseDao;
	
	public UpdateThread20170815(BaseDao baseDao){
		this.baseDao = baseDao;
	}
	
	public void execute(){
		
		//创建用户和分配权限
		runSQL("create user vuser identified by vuser default tablespace GUAN temporary tablespace TEMP profile DEFAULT");
		runSQL("grant connect to vuser");
		runSQL("grant resource to vuser");
		runSQL("grant select to vuser");
		runSQL("grant create view to vuser");
		runSQL("grant unlimited tablespace to vuser");
		
		runSQL("grant select on CSL_CONTRAST_PERSON to vuser");//预警人员
		runSQL("grant select on CSL_CONTRAST_VEHICLE to vuser");//预警车辆
		runSQL("grant select on CSL_PERSON to vuser");//数据门通过人员
		runSQL("grant select on CSL_VEHICLE to vuser");//前置卡口和车道过车数据
		runSQL("grant select on CSL_PASSENGER to vuser");//车辆关联的司机和乘客数据
		runSQL("grant select on CSL_EXPLAIN to vuser");//车辆关联的司机和乘客数据
		createView();//为用户创建视图
		
		runSQL("DELETE FROM CSL_EXPLAIN");
		List<String> sqlList = new ArrayList<String>();
		sqlList.add("insert into CSL_EXPLAIN (tablename, indexs, columns, explain, remark) values ('V_CONTRAST_PERSON', 0, '预警人员信息表', null, null)");
		sqlList.add("insert into CSL_EXPLAIN (tablename, indexs, columns, explain, remark) values ('V_CONTRAST_PERSON', 1, 'MONTH_ID', '月份，按月分区', null)");
		sqlList.add("insert into CSL_EXPLAIN (tablename, indexs, columns, explain, remark) values ('V_CONTRAST_PERSON', 2, 'ID', '数据唯一编号UUID', null)");
		sqlList.add("insert into CSL_EXPLAIN (tablename, indexs, columns, explain, remark) values ('V_CONTRAST_PERSON', 3, 'NAMES', '姓名', null)");
		sqlList.add("insert into CSL_EXPLAIN (tablename, indexs, columns, explain, remark) values ('V_CONTRAST_PERSON', 4, 'NATION', '民族', null)");
		sqlList.add("insert into CSL_EXPLAIN (tablename, indexs, columns, explain, remark) values ('V_CONTRAST_PERSON', 5, 'GENDER', '性别', null)");
		sqlList.add("insert into CSL_EXPLAIN (tablename, indexs, columns, explain, remark) values ('V_CONTRAST_PERSON', 6, 'BIRTH', '出生年月', null)");
		sqlList.add("insert into CSL_EXPLAIN (tablename, indexs, columns, explain, remark) values ('V_CONTRAST_PERSON', 7, 'IDCARD', '身份证号', null)");
		sqlList.add("insert into CSL_EXPLAIN (tablename, indexs, columns, explain, remark) values ('V_CONTRAST_PERSON', 8, 'CAPTURE_TIME', '采集时间', null)");
		sqlList.add("insert into CSL_EXPLAIN (tablename, indexs, columns, explain, remark) values ('V_CONTRAST_PERSON', 9, 'ADDRESS', '身份证地址信息', null)");
		sqlList.add("insert into CSL_EXPLAIN (tablename, indexs, columns, explain, remark) values ('V_CONTRAST_PERSON', 10, 'TAG', '标签', null)");
		sqlList.add("insert into CSL_EXPLAIN (tablename, indexs, columns, explain, remark) values ('V_CONTRAST_PERSON', 11, 'ACTION', '处置手段', null)");
		sqlList.add("insert into CSL_EXPLAIN (tablename, indexs, columns, explain, remark) values ('V_CONTRAST_PERSON', 12, 'BK_AREA', '布控范围', null)");
		sqlList.add("insert into CSL_EXPLAIN (tablename, indexs, columns, explain, remark) values ('V_CONTRAST_PERSON', 13, 'YJ_AREA', '预警范围', null)");
		sqlList.add("insert into CSL_EXPLAIN (tablename, indexs, columns, explain, remark) values ('V_CONTRAST_PERSON', 14, 'SOURCE', '数据来源', null)");
		sqlList.add("insert into CSL_EXPLAIN (tablename, indexs, columns, explain, remark) values ('V_CONTRAST_PERSON', 15, 'IDCARD_PHOTO_URL', '身份证照片', null)");
		sqlList.add("insert into CSL_EXPLAIN (tablename, indexs, columns, explain, remark) values ('V_CONTRAST_PERSON', 16, 'GATHER_PHOTO_URL', '采集照片', null)");
		sqlList.add("insert into CSL_EXPLAIN (tablename, indexs, columns, explain, remark) values ('V_CONTRAST_PERSON', 17, 'LOCATION', '设备所在位置', null)");
		sqlList.add("insert into CSL_EXPLAIN (tablename, indexs, columns, explain, remark) values ('V_CONTRAST_PERSON', 18, 'NOTE', '备注', null)");
		sqlList.add("insert into CSL_EXPLAIN (tablename, indexs, columns, explain, remark) values ('V_CONTRAST_PERSON', 19, 'AREA_CODE', '区域编码', null)");
		sqlList.add("insert into CSL_EXPLAIN (tablename, indexs, columns, explain, remark) values ('V_CONTRAST_PERSON', 20, 'CHECK_POINT_ID', '检查站编号', null)");
		sqlList.add("insert into CSL_EXPLAIN (tablename, indexs, columns, explain, remark) values ('V_CONTRAST_PERSON', 21, 'INSERT_TIME', '数据入库时间', null)");
		sqlList.add("insert into CSL_EXPLAIN (tablename, indexs, columns, explain, remark) values ('V_CONTRAST_PERSON', 22, 'IS_CHECKED', '是否已核查', null)");
		sqlList.add("insert into CSL_EXPLAIN (tablename, indexs, columns, explain, remark) values ('V_CONTRAST_PERSON', 23, 'EQUIPMENT_ID', '设备编号', null)");
		sqlList.add("insert into CSL_EXPLAIN (tablename, indexs, columns, explain, remark) values ('V_CONTRAST_VEHICLE', 0, '预警车辆信息表', null, null)");
		sqlList.add("insert into CSL_EXPLAIN (tablename, indexs, columns, explain, remark) values ('V_CONTRAST_VEHICLE', 1, 'MONTH_ID', '按月分区字段', null)");
		sqlList.add("insert into CSL_EXPLAIN (tablename, indexs, columns, explain, remark) values ('V_CONTRAST_VEHICLE', 2, 'ID', '数据唯一编号UUID', null)");
		sqlList.add("insert into CSL_EXPLAIN (tablename, indexs, columns, explain, remark) values ('V_CONTRAST_VEHICLE', 3, 'CAR_NUM', '车牌号', null)");
		sqlList.add("insert into CSL_EXPLAIN (tablename, indexs, columns, explain, remark) values ('V_CONTRAST_VEHICLE', 4, 'PASSDATE', '采集时间', null)");
		sqlList.add("insert into CSL_EXPLAIN (tablename, indexs, columns, explain, remark) values ('V_CONTRAST_VEHICLE', 5, 'PLATE_COLOR', '车牌颜色', null)");
		sqlList.add("insert into CSL_EXPLAIN (tablename, indexs, columns, explain, remark) values ('V_CONTRAST_VEHICLE', 6, 'VEHICLE_TYPE', '车辆类型', null)");
		sqlList.add("insert into CSL_EXPLAIN (tablename, indexs, columns, explain, remark) values ('V_CONTRAST_VEHICLE', 7, 'IDCARD', '身份证号', null)");
		sqlList.add("insert into CSL_EXPLAIN (tablename, indexs, columns, explain, remark) values ('V_CONTRAST_VEHICLE', 8, 'NAMES', '所有人', null)");
		sqlList.add("insert into CSL_EXPLAIN (tablename, indexs, columns, explain, remark) values ('V_CONTRAST_VEHICLE', 9, 'TAG', '人员标签', null)");
		sqlList.add("insert into CSL_EXPLAIN (tablename, indexs, columns, explain, remark) values ('V_CONTRAST_VEHICLE', 10, 'DESCRIBE', '车辆状态', null)");
		sqlList.add("insert into CSL_EXPLAIN (tablename, indexs, columns, explain, remark) values ('V_CONTRAST_VEHICLE', 11, 'ACTION', '处置手段', null)");
		sqlList.add("insert into CSL_EXPLAIN (tablename, indexs, columns, explain, remark) values ('V_CONTRAST_VEHICLE', 12, 'PERSON_PHOTO', '人员身份证照片base64', null)");
		sqlList.add("insert into CSL_EXPLAIN (tablename, indexs, columns, explain, remark) values ('V_CONTRAST_VEHICLE', 13, 'BK_AREA', '布控范围', null)");
		sqlList.add("insert into CSL_EXPLAIN (tablename, indexs, columns, explain, remark) values ('V_CONTRAST_VEHICLE', 14, 'YJ_AREA', '预警范围', null)");
		sqlList.add("insert into CSL_EXPLAIN (tablename, indexs, columns, explain, remark) values ('V_CONTRAST_VEHICLE', 15, 'SOURCE', '数据来源', null)");
		sqlList.add("insert into CSL_EXPLAIN (tablename, indexs, columns, explain, remark) values ('V_CONTRAST_VEHICLE', 16, 'VEHICLE_COLOR', '车身颜色', null)");
		sqlList.add("insert into CSL_EXPLAIN (tablename, indexs, columns, explain, remark) values ('V_CONTRAST_VEHICLE', 17, 'PLATE_TYPE', '车牌类型', null)");
		sqlList.add("insert into CSL_EXPLAIN (tablename, indexs, columns, explain, remark) values ('V_CONTRAST_VEHICLE', 18, 'GATHER_PHOTO_URL', '采集照片', null)");
		sqlList.add("insert into CSL_EXPLAIN (tablename, indexs, columns, explain, remark) values ('V_CONTRAST_VEHICLE', 19, 'LOCATION', '设备所在位置', null)");
		sqlList.add("insert into CSL_EXPLAIN (tablename, indexs, columns, explain, remark) values ('V_CONTRAST_VEHICLE', 20, 'LABEL', '车标签', null)");
		sqlList.add("insert into CSL_EXPLAIN (tablename, indexs, columns, explain, remark) values ('V_CONTRAST_VEHICLE', 21, 'AREA_CODE', '区域编码 ', null)");
		sqlList.add("insert into CSL_EXPLAIN (tablename, indexs, columns, explain, remark) values ('V_CONTRAST_VEHICLE', 22, 'NOTE', '备注 ', null)");
		sqlList.add("insert into CSL_EXPLAIN (tablename, indexs, columns, explain, remark) values ('V_CONTRAST_VEHICLE', 23, 'ADDRESS', '户籍地址', null)");
		sqlList.add("insert into CSL_EXPLAIN (tablename, indexs, columns, explain, remark) values ('V_CONTRAST_VEHICLE', 24, 'OWNER_PHOTO_URL', '车主照片', null)");
		sqlList.add("insert into CSL_EXPLAIN (tablename, indexs, columns, explain, remark) values ('V_CONTRAST_VEHICLE', 25, 'PROBLEM_TYPE', '问题车辆类型：1 二手车，2 布控对象车辆', null)");
		sqlList.add("insert into CSL_EXPLAIN (tablename, indexs, columns, explain, remark) values ('V_CONTRAST_VEHICLE', 26, 'CHECK_POINT_ID', '接收任务的检查站', null)");
		sqlList.add("insert into CSL_EXPLAIN (tablename, indexs, columns, explain, remark) values ('V_CONTRAST_VEHICLE', 27, 'INSERT_TIME', '数据入库时间', null)");
		sqlList.add("insert into CSL_EXPLAIN (tablename, indexs, columns, explain, remark) values ('V_CONTRAST_VEHICLE', 28, 'IS_CHECKED', '是否核查', null)");
		sqlList.add("insert into CSL_EXPLAIN (tablename, indexs, columns, explain, remark) values ('V_CONTRAST_VEHICLE', 29, 'VEHICLE_ID', '对应车辆流水数据', null)");
		sqlList.add("insert into CSL_EXPLAIN (tablename, indexs, columns, explain, remark) values ('V_CONTRAST_VEHICLE', 30, 'EQUIPMENT_ID', '设备编号', null)");
		sqlList.add("insert into CSL_EXPLAIN (tablename, indexs, columns, explain, remark) values ('V_PASSENGER', 0, '车辆对应司机和乘客数据表', null, null)");
		sqlList.add("insert into CSL_EXPLAIN (tablename, indexs, columns, explain, remark) values ('V_PASSENGER', 1, 'MONTH_ID', '月份，按月分区', null)");
		sqlList.add("insert into CSL_EXPLAIN (tablename, indexs, columns, explain, remark) values ('V_PASSENGER', 2, 'ID', '数据唯一编号UUID', null)");
		sqlList.add("insert into CSL_EXPLAIN (tablename, indexs, columns, explain, remark) values ('V_PASSENGER', 3, 'VEHICLE_ID', '车辆ID', null)");
		sqlList.add("insert into CSL_EXPLAIN (tablename, indexs, columns, explain, remark) values ('V_PASSENGER', 4, 'USER_NAME', '乘客姓名', null)");
		sqlList.add("insert into CSL_EXPLAIN (tablename, indexs, columns, explain, remark) values ('V_PASSENGER', 5, 'SEX', '性别', null)");
		sqlList.add("insert into CSL_EXPLAIN (tablename, indexs, columns, explain, remark) values ('V_PASSENGER', 6, 'MINZU', '族别', null)");
		sqlList.add("insert into CSL_EXPLAIN (tablename, indexs, columns, explain, remark) values ('V_PASSENGER', 7, 'CARD_NUM', '身份证号', null)");
		sqlList.add("insert into CSL_EXPLAIN (tablename, indexs, columns, explain, remark) values ('V_PASSENGER', 8, 'BIRTH_DATE', '出生年月', null)");
		sqlList.add("insert into CSL_EXPLAIN (tablename, indexs, columns, explain, remark) values ('V_PASSENGER', 9, 'ADDRESS', '地址', null)");
		sqlList.add("insert into CSL_EXPLAIN (tablename, indexs, columns, explain, remark) values ('V_PASSENGER', 10, 'QIANFA', '签发机关', null)");
		sqlList.add("insert into CSL_EXPLAIN (tablename, indexs, columns, explain, remark) values ('V_PASSENGER', 11, 'YOUXIAOQI', '有效期', null)");
		sqlList.add("insert into CSL_EXPLAIN (tablename, indexs, columns, explain, remark) values ('V_PASSENGER', 12, 'CARD_IMG', '身份证照片', null)");
		sqlList.add("insert into CSL_EXPLAIN (tablename, indexs, columns, explain, remark) values ('V_PASSENGER', 13, 'SLEVEL', '安全级别', null)");
		sqlList.add("insert into CSL_EXPLAIN (tablename, indexs, columns, explain, remark) values ('V_PASSENGER', 14, 'IS_DRIVER', '1代表司机，0代表乘客', null)");
		sqlList.add("insert into CSL_EXPLAIN (tablename, indexs, columns, explain, remark) values ('V_PASSENGER', 15, 'RESULT', '比对结果：1成功，0失败', null)");
		sqlList.add("insert into CSL_EXPLAIN (tablename, indexs, columns, explain, remark) values ('V_PASSENGER', 16, 'PASSDATE', '数据采集时间', null)");
		sqlList.add("insert into CSL_EXPLAIN (tablename, indexs, columns, explain, remark) values ('V_PASSENGER', 17, 'INSERT_TIME', '数据入库时间', null)");
		sqlList.add("insert into CSL_EXPLAIN (tablename, indexs, columns, explain, remark) values ('V_PERSON', 0, '数据门采集数据流水表', null, null)");
		sqlList.add("insert into CSL_EXPLAIN (tablename, indexs, columns, explain, remark) values ('V_PERSON', 1, 'MONTH_ID', '月份，按月分区', null)");
		sqlList.add("insert into CSL_EXPLAIN (tablename, indexs, columns, explain, remark) values ('V_PERSON', 2, 'ID', '数据唯一编号UUID', null)");
		sqlList.add("insert into CSL_EXPLAIN (tablename, indexs, columns, explain, remark) values ('V_PERSON', 3, 'NAMES', '姓名', null)");
		sqlList.add("insert into CSL_EXPLAIN (tablename, indexs, columns, explain, remark) values ('V_PERSON', 4, 'NATION', '民族', null)");
		sqlList.add("insert into CSL_EXPLAIN (tablename, indexs, columns, explain, remark) values ('V_PERSON', 5, 'GENDER', '性别', null)");
		sqlList.add("insert into CSL_EXPLAIN (tablename, indexs, columns, explain, remark) values ('V_PERSON', 6, 'BIRTH', '出生年月', null)");
		sqlList.add("insert into CSL_EXPLAIN (tablename, indexs, columns, explain, remark) values ('V_PERSON', 7, 'IDCARD', '身份证号', null)");
		sqlList.add("insert into CSL_EXPLAIN (tablename, indexs, columns, explain, remark) values ('V_PERSON', 8, 'EQUIPMENT_ID', '采集设备编号', null)");
		sqlList.add("insert into CSL_EXPLAIN (tablename, indexs, columns, explain, remark) values ('V_PERSON', 9, 'CAPTURE_TIME', '采集时间', null)");
		sqlList.add("insert into CSL_EXPLAIN (tablename, indexs, columns, explain, remark) values ('V_PERSON', 10, 'ADDRESS', '身份证地址信息', null)");
		sqlList.add("insert into CSL_EXPLAIN (tablename, indexs, columns, explain, remark) values ('V_PERSON', 11, 'START_DATE', '身份证有效期开始', null)");
		sqlList.add("insert into CSL_EXPLAIN (tablename, indexs, columns, explain, remark) values ('V_PERSON', 12, 'END_DATE', '身份证有效期结束', null)");
		sqlList.add("insert into CSL_EXPLAIN (tablename, indexs, columns, explain, remark) values ('V_PERSON', 13, 'IS_A_PERSON', '是否人证合一', null)");
		sqlList.add("insert into CSL_EXPLAIN (tablename, indexs, columns, explain, remark) values ('V_PERSON', 14, 'IMG_URL', '抓拍人照片路径', null)");
		sqlList.add("insert into CSL_EXPLAIN (tablename, indexs, columns, explain, remark) values ('V_PERSON', 15, 'LOCAL_IMG_URL', '下载到本地的照片路径', null)");
		sqlList.add("insert into CSL_EXPLAIN (tablename, indexs, columns, explain, remark) values ('V_PERSON', 16, 'ERROR_COUNT', '图片下载错误次数', null)");
		sqlList.add("insert into CSL_EXPLAIN (tablename, indexs, columns, explain, remark) values ('V_PERSON', 17, 'LOCATION', '位置', null)");
		sqlList.add("insert into CSL_EXPLAIN (tablename, indexs, columns, explain, remark) values ('V_PERSON', 18, 'INSERT_TIME', '数据入库时间', null)");
		sqlList.add("insert into CSL_EXPLAIN (tablename, indexs, columns, explain, remark) values ('V_VEHICLE', 0, '车辆流水数据表', null, null)");
		sqlList.add("insert into CSL_EXPLAIN (tablename, indexs, columns, explain, remark) values ('V_VEHICLE', 1, 'MONTH_ID', '按月分区字段', null)");
		sqlList.add("insert into CSL_EXPLAIN (tablename, indexs, columns, explain, remark) values ('V_VEHICLE', 2, 'ID', 'ID', null)");
		sqlList.add("insert into CSL_EXPLAIN (tablename, indexs, columns, explain, remark) values ('V_VEHICLE', 3, 'CAR_NUM', '车牌号', null)");
		sqlList.add("insert into CSL_EXPLAIN (tablename, indexs, columns, explain, remark) values ('V_VEHICLE', 4, 'CAR_IMG', '车辆照片路径', null)");
		sqlList.add("insert into CSL_EXPLAIN (tablename, indexs, columns, explain, remark) values ('V_VEHICLE', 5, 'NUM_IMG', '车牌号照片路径', null)");
		sqlList.add("insert into CSL_EXPLAIN (tablename, indexs, columns, explain, remark) values ('V_VEHICLE', 6, 'EQUIPMENT_ID', '设备编号', null)");
		sqlList.add("insert into CSL_EXPLAIN (tablename, indexs, columns, explain, remark) values ('V_VEHICLE', 7, 'PASSDATE', '采集时间', null)");
		sqlList.add("insert into CSL_EXPLAIN (tablename, indexs, columns, explain, remark) values ('V_VEHICLE', 8, 'INSERT_TIME', '数据入库时间', null)");
		sqlList.add("insert into CSL_EXPLAIN (tablename, indexs, columns, explain, remark) values ('V_VEHICLE', 9, 'PLATE_COLOR_ID', '车牌颜色编码', null)");
		sqlList.add("insert into CSL_EXPLAIN (tablename, indexs, columns, explain, remark) values ('V_VEHICLE', 10, 'PLATE_COLOR', '车牌颜色', null)");
		sqlList.add("insert into CSL_EXPLAIN (tablename, indexs, columns, explain, remark) values ('V_VEHICLE', 11, 'RELUST', '比对结果', null)");
		sqlList.add("insert into CSL_EXPLAIN (tablename, indexs, columns, explain, remark) values ('V_VEHICLE', 12, 'CLASSIFY', '分类：VehicleShunt表示前置卡口VehicleBrake表示车证合一的闸机', null)");
		sqlList.add("insert into CSL_EXPLAIN (tablename, indexs, columns, explain, remark) values ('V_VEHICLE', 13, 'VEHICLE_TYPE', '车辆类型', null)");
		try {
			baseDao.execute(sqlList);
		} catch (Exception e) { }
		
		//为车牌号增加索引
		runSQL("CREATE INDEX CLS_VEHICLE_BRAKE_CAR_NUM ON CSL_VEHICLE_BRAKE(CAR_NUM)");
				
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
	
	/**为vuser用户创建视图*/
	private static void createView(){
		Statement stmt = null;
		Connection conn = null;
		try{
			Configuration config = new PropertiesConfiguration("dbinfo.properties");
			String driver = config.getString("driverClassName");
		 	String url = config.getString("url");
		 	String username = "vuser";
		 	String password = "vuser";
			Class.forName(driver);
			conn = DriverManager.getConnection(url, username, password);
			stmt = conn.createStatement();
			stmt.addBatch("CREATE OR REPLACE VIEW V_CONTRAST_PERSON AS SELECT * FROM CSL.CSL_CONTRAST_PERSON");
			stmt.addBatch("CREATE OR REPLACE VIEW V_CONTRAST_VEHICLE AS SELECT * FROM CSL.CSL_CONTRAST_VEHICLE");
			stmt.addBatch("CREATE OR REPLACE VIEW V_PERSON AS SELECT * FROM CSL.CSL_PERSON");
			stmt.addBatch("CREATE OR REPLACE VIEW V_VEHICLE AS SELECT * FROM CSL.CSL_VEHICLE");
			stmt.addBatch("CREATE OR REPLACE VIEW V_PASSENGER AS SELECT * FROM CSL.CSL_PASSENGER");
			stmt.addBatch("CREATE OR REPLACE VIEW Z_README AS SELECT * FROM CSL.CSL_EXPLAIN ORDER BY TABLENAME,INDEXS");
			stmt.executeBatch();
		}catch (Exception e){
		}finally{
			try {
				if(stmt != null){ stmt.close();}
				if(conn != null){ conn.close();}	
			} catch (SQLException e) { }
		}
	}
}
