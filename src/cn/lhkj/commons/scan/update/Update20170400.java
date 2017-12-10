package cn.lhkj.commons.scan.update;

import java.sql.SQLException;
import java.sql.Statement;
import cn.lhkj.commons.base.BaseDao;


/**
 * 2017年07月01日之前更新的库和字段
 */
public class Update20170400 {
	
	private BaseDao baseDao;
	
	public Update20170400(BaseDao baseDao){
		this.baseDao = baseDao;
	}
	
	public void execute(){
		runSQL("create table CHECK_PERSON_CONTRABAND("+
				  "id           VARCHAR2(64) not null,"+
				  "person_id      VARCHAR2(64),"+
				  "name      VARCHAR2(32),"+
				  "remark      VARCHAR2(128),"+
				  "contraband_photo_url   VARCHAR2(200),"+
				  "check_time   DATE)tablespace GUAN");
		runSQL("alter table CHECK_PERSON_CONTRABAND add primary key (ID) using index  tablespace GUAN");		
////////////////////////////////////////////////////////////////////////////////////////////				
		runSQL("create table CHECK_PERSON_PEERS_VEHICLE("+
				"id              VARCHAR2(64) not null,"+
				"car_num      VARCHAR2(64),"+
				"plate_color      VARCHAR2(20),"+
				"vehicle_color      VARCHAR2(100),"+
				"vehicle_type      VARCHAR2(100),"+
				"check_person_id VARCHAR2(64))tablespace GUAN");
		runSQL("alter table CHECK_PERSON_PEERS_VEHICLE add primary key (ID) using index tablespace GUAN");
////////////////////////////////////////////////////////////////////////////////////////////
		runSQL("create table CHECK_PERSON("+
			  "id           VARCHAR2(64) not null,"+
			  "task_id      VARCHAR2(64),"+
			  "yj_type      VARCHAR2(200),"+
			  "check_time   DATE,"+
			  "tag          VARCHAR2(500),"+
			  "is_contrast  VARCHAR2(20),"+
			  "action       VARCHAR2(20),"+
			  "idcard       VARCHAR2(20),"+
			  "phonenum     VARCHAR2(100),"+
			  "match        VARCHAR2(20),"+
			  "is_dubious   VARCHAR2(20),"+
			  "finds        VARCHAR2(1000),"+
			  "is_vacation  VARCHAR2(20),"+
			  "reason       VARCHAR2(200),"+
			  "backtime     DATE,"+
			  "is_leave     VARCHAR2(20),"+
			  "insert_time  DATE default sysdate,"+
			  "area_code    VARCHAR2(100),"+
			  "station_id   VARCHAR2(100),"+
			  "station_name VARCHAR2(300),"+
			  "remark       VARCHAR2(1000),"+
			  "is_upload    VARCHAR2(1) default 0)tablespace GUAN");
		runSQL("alter table CHECK_PERSON add primary key (ID) using index  tablespace GUAN");
		///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		runSQL("create table CHECK_PERSON_PEERS("+
				"id              VARCHAR2(64) not null,"+
				"idcard          VARCHAR2(20),"+
				"phonenum        VARCHAR2(100),"+
				"check_person_id VARCHAR2(64))tablespace GUAN");
		runSQL("alter table CHECK_PERSON_PEERS add primary key (ID) using index tablespace GUAN");
		///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		runSQL("create table CHECK_VEHICLE("+
			  "id           VARCHAR2(64) not null,"+
			  "yj_type      VARCHAR2(100),"+
			  "task_id      VARCHAR2(64),"+
			  "car_num      VARCHAR2(64),"+
			  "forbids      VARCHAR2(200),"+
			  "relations    VARCHAR2(50),"+
			  "explains     VARCHAR2(2000),"+
			  "check_time   DATE,"+
			  "insert_time  DATE default sysdate,"+
			  "area_code    VARCHAR2(100),"+
			  "station_id   VARCHAR2(100),"+
			  "station_name VARCHAR2(300),"+
			  "remark       VARCHAR2(1000),"+
			  "is_upload    VARCHAR2(1) default 0)tablespace GUAN");
		runSQL("alter table CHECK_VEHICLE add primary key (ID) using index tablespace GUAN");
		///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		runSQL("create table CHECK_VEHICLE_PASSENGER("+
		  "id               VARCHAR2(64) not null,"+
		  "check_vehicle_id VARCHAR2(50),"+
		  "idcard           VARCHAR2(50),"+
		  "phonenum         VARCHAR2(50),"+
		  "match            VARCHAR2(500),"+
		  "is_dubious       VARCHAR2(100),"+
		  "finds            VARCHAR2(2000),"+
		  "is_driver        VARCHAR2(1) default 0,"+
		  "insert_time      DATE default sysdate)tablespace GUAN");
		runSQL("alter table CHECK_VEHICLE_PASSENGER add primary key (ID) using index tablespace GUAN");
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		runSQL("create table CSL_CONTRAST_PERSON("+
		  "month_id         VARCHAR2(6),"+
		  "id               VARCHAR2(64) not null,"+
		  "names            VARCHAR2(50),"+
		  "nation           VARCHAR2(40),"+
		  "gender           VARCHAR2(10),"+
		  "birth            VARCHAR2(20),"+
		  "idcard           VARCHAR2(18),"+
		  "capture_time     DATE,"+
		  "address          VARCHAR2(200),"+
		  "tag              VARCHAR2(500),"+
		  "action           VARCHAR2(200),"+
		  "bk_area          VARCHAR2(200),"+
		  "yj_area          VARCHAR2(200),"+
		  "source           VARCHAR2(100) default '本站产生',"+
		  "idcard_photo_url VARCHAR2(200),"+
		  "gather_photo_url VARCHAR2(200),"+
		  "location         VARCHAR2(100),"+
		  "note             VARCHAR2(1000),"+
		  "area_code        VARCHAR2(6),"+
		  "check_point_id   VARCHAR2(30),"+
		  "insert_time      DATE default sysdate,"+
		  "is_checked       VARCHAR2(1) default 0,"+
		  "equipment_id     VARCHAR2(100) ) partition by range (MONTH_ID)("+
		  "partition MONTH_PART_201703 values less than ('201704'),"+
		  "partition MONTH_PART_201704 values less than ('201705'),"+
		  "partition MONTH_PART_201705 values less than ('201706'),"+
		  "partition MONTH_PART_201706 values less than ('201707'),"+
		  "partition MONTH_PART_201707 values less than ('201708'),"+
		  "partition MONTH_PART_201708 values less than ('201709'),"+
		  "partition MONTH_PART_201709 values less than ('201710'),"+
		  "partition MONTH_PART_201710 values less than ('201711'),"+
		  "partition MONTH_PART_201711 values less than ('201712'),"+
		  "partition MONTH_PART_201712 values less than ('201801'),"+
		  "partition MONTH_PART_201801 values less than ('201802'),"+
		  "partition MONTH_PART_201802 values less than ('201803'),"+
		  "partition MONTH_PART_201803 values less than ('201804'),"+
		  "partition MONTH_PART_201804 values less than ('201805'),"+
		  "partition MONTH_PART_201805 values less than ('201806'),"+
		  "partition MONTH_PART_201806 values less than ('201807'),"+
		  "partition MONTH_PART_201807 values less than ('201808'),"+
		  "partition MONTH_PART_201808 values less than ('201809'),"+
		  "partition MONTH_PART_201809 values less than ('201810'),"+
		  "partition MONTH_PART_201810 values less than ('201811'),"+
		  "partition MONTH_PART_201811 values less than ('201812'),"+
		  "partition MONTH_PART_201812 values less than ('201901'),"+
		  "partition MONTH_PART_201901 values less than ('201902'),"+
		  "partition MONTH_PART_201902 values less than ('201903'),"+
		  "partition MONTH_PART_201903 values less than ('201904'),"+
		  "partition MONTH_PART_201904 values less than ('201905'),"+
		  "partition MONTH_PART_201905 values less than ('201906'),"+
		  "partition MONTH_PART_201906 values less than ('201907'),"+
		  "partition MONTH_PART_201907 values less than ('201908'),"+
		  "partition MONTH_PART_201908 values less than ('201909'),"+
		  "partition MONTH_PART_201909 values less than ('201910'),"+
		  "partition MONTH_PART_201910 values less than ('201911'),"+
		  "partition MONTH_PART_201911 values less than ('201912'),"+
		  "partition MONTH_PART_201912 values less than ('202001'),"+
		  "partition MONTH_PART_202001 values less than ('202002'),"+
		  "partition MONTH_PART_202002 values less than ('202003'),"+
		  "partition MONTH_PART_202003 values less than ('202004'),"+
		  "partition MONTH_PART_202004 values less than ('202005'),"+
		  "partition MONTH_PART_202005 values less than ('202006'),"+
		  "partition MONTH_PART_202006 values less than ('202007'),"+
		  "partition MONTH_PART_202007 values less than ('202008'),"+
		  "partition MONTH_PART_202008 values less than ('202009'),"+
		  "partition MONTH_PART_202009 values less than ('202010'),"+
		  "partition MONTH_PART_202010 values less than ('202011'),"+
		  "partition MONTH_PART_202011 values less than ('202012'),"+
		  "partition MONTH_PART_202012 values less than ('202101') )");
		runSQL("alter table CSL_CONTRAST_PERSON add primary key (ID) using index tablespace GUAN");
		//////////////////////////////////////////////////////////////////////////////////////////
		runSQL("create table CSL_CONTRAST_VEHICLE("+
		  "month_id         VARCHAR2(6),"+
		  "id               VARCHAR2(64) not null,"+
		  "car_num          VARCHAR2(20),"+
		  "passdate         DATE,"+
		  "plate_color      VARCHAR2(20),"+
		  "vehicle_type     VARCHAR2(200),"+
		  "idcard           VARCHAR2(20),"+
		  "names            VARCHAR2(100),"+
		  "tag              VARCHAR2(500),"+
		  "describe         VARCHAR2(100),"+
		  "action           VARCHAR2(100),"+
		  "person_photo     CLOB,"+
		  "bk_area          VARCHAR2(100),"+
		  "yj_area          VARCHAR2(100),"+
		  "source           VARCHAR2(100) default '本站产生',"+
		  "vehicle_color    VARCHAR2(10),"+
		  "plate_type       VARCHAR2(10),"+
		  "gather_photo_url VARCHAR2(200),"+
		  "location         VARCHAR2(100),"+
		  "label            VARCHAR2(100),"+
		  "area_code        VARCHAR2(6),"+
		  "note             VARCHAR2(100),"+
		  "address          VARCHAR2(100),"+
		  "owner_photo_url  VARCHAR2(100),"+
		  "problem_type     VARCHAR2(20),"+
		  "check_point_id   VARCHAR2(20),"+
		  "insert_time      DATE default sysdate,"+
		  "is_checked       VARCHAR2(1) default 0,"+
		  "vehicle_id       VARCHAR2(64),"+
		  "equipment_id     VARCHAR2(100) ) partition by range (MONTH_ID)("+
		  "partition MONTH_PART_201703 values less than ('201704'),"+
		  "partition MONTH_PART_201704 values less than ('201705'),"+
		  "partition MONTH_PART_201705 values less than ('201706'),"+
		  "partition MONTH_PART_201706 values less than ('201707'),"+
		  "partition MONTH_PART_201707 values less than ('201708'),"+
		  "partition MONTH_PART_201708 values less than ('201709'),"+
		  "partition MONTH_PART_201709 values less than ('201710'),"+
		  "partition MONTH_PART_201710 values less than ('201711'),"+
		  "partition MONTH_PART_201711 values less than ('201712'),"+
		  "partition MONTH_PART_201712 values less than ('201801'),"+
		  "partition MONTH_PART_201801 values less than ('201802'),"+
		  "partition MONTH_PART_201802 values less than ('201803'),"+
		  "partition MONTH_PART_201803 values less than ('201804'),"+
		  "partition MONTH_PART_201804 values less than ('201805'),"+
		  "partition MONTH_PART_201805 values less than ('201806'),"+
		  "partition MONTH_PART_201806 values less than ('201807'),"+
		  "partition MONTH_PART_201807 values less than ('201808'),"+
		  "partition MONTH_PART_201808 values less than ('201809'),"+
		  "partition MONTH_PART_201809 values less than ('201810'),"+
		  "partition MONTH_PART_201810 values less than ('201811'),"+
		  "partition MONTH_PART_201811 values less than ('201812'),"+
		  "partition MONTH_PART_201812 values less than ('201901'),"+
		  "partition MONTH_PART_201901 values less than ('201902'),"+
		  "partition MONTH_PART_201902 values less than ('201903'),"+
		  "partition MONTH_PART_201903 values less than ('201904'),"+
		  "partition MONTH_PART_201904 values less than ('201905'),"+
		  "partition MONTH_PART_201905 values less than ('201906'),"+
		  "partition MONTH_PART_201906 values less than ('201907'),"+
		  "partition MONTH_PART_201907 values less than ('201908'),"+
		  "partition MONTH_PART_201908 values less than ('201909'),"+
		  "partition MONTH_PART_201909 values less than ('201910'),"+
		  "partition MONTH_PART_201910 values less than ('201911'),"+
		  "partition MONTH_PART_201911 values less than ('201912'),"+
		  "partition MONTH_PART_201912 values less than ('202001'),"+
		  "partition MONTH_PART_202001 values less than ('202002'),"+
		  "partition MONTH_PART_202002 values less than ('202003'),"+
		  "partition MONTH_PART_202003 values less than ('202004'),"+
		  "partition MONTH_PART_202004 values less than ('202005'),"+
		  "partition MONTH_PART_202005 values less than ('202006'),"+
		  "partition MONTH_PART_202006 values less than ('202007'),"+
		  "partition MONTH_PART_202007 values less than ('202008'),"+
		  "partition MONTH_PART_202008 values less than ('202009'),"+
		  "partition MONTH_PART_202009 values less than ('202010'),"+
		  "partition MONTH_PART_202010 values less than ('202011'),"+
		  "partition MONTH_PART_202011 values less than ('202012'),"+
		  "partition MONTH_PART_202012 values less than ('202101'))");
		runSQL("alter table CSL_CONTRAST_VEHICLE add primary key (ID) using index tablespace GUAN");
		///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		runSQL("create table CSL_DICT("+
				"id    VARCHAR2(64) not null,"+
				"names VARCHAR2(100) ) tablespace GUAN");
		runSQL("alter table CSL_DICT add primary key (ID) using index tablespace GUAN");
		runSQL("insert into CSL_DICT (id, names) values ('PLATE_TYPE', '车牌类型')");
		runSQL("insert into CSL_DICT (id, names) values ('VEHICLE_TYPE', '车辆类型')");
		runSQL("insert into CSL_DICT (id, names) values ('VEHICLE_COLOR', '车身颜色')");
		runSQL("insert into CSL_DICT (id, names) values ('WQUIP_TYPE', '设备类型')");
		runSQL("insert into CSL_DICT (id, names) values ('PLATE_COLOR', '车牌颜色')");
		///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		runSQL("create table CSL_DICT_ITEM("+
		  "id      VARCHAR2(64) not null,"+
		  "dict_id VARCHAR2(64),"+
		  "options VARCHAR2(100),"+
		  "ranking NUMBER default 0,"+
		  "codes   VARCHAR2(50) ) tablespace GUAN");
		runSQL("alter table CSL_DICT_ITEM add primary key (ID) using index tablespace GUAN");
		
		runSQL("delete from CSL_DICT_ITEM");
		runSQL("insert into CSL_DICT_ITEM (id, dict_id, options, ranking, codes) values ('C389CC2AC4534E978866AF1C22B4DBC4', 'PLATE_TYPE', '未知', 0, '0')");
		runSQL("insert into CSL_DICT_ITEM (id, dict_id, options, ranking, codes) values ('FB54C1651383452AB171EC0A3C961FF3', 'PLATE_TYPE', '92式民用车', 1, '1')");
		runSQL("insert into CSL_DICT_ITEM (id, dict_id, options, ranking, codes) values ('C19F4C7CDF464F2FBCFAADCEA3466249', 'PLATE_TYPE', '警车', 2, '2')");
		runSQL("insert into CSL_DICT_ITEM (id, dict_id, options, ranking, codes) values ('0BAA88336BA14C30B5EA05909F190BDB', 'PLATE_TYPE', '上下军车', 3, '3')");
		runSQL("insert into CSL_DICT_ITEM (id, dict_id, options, ranking, codes) values ('B7F590AA080043DD805A5AE3058E9646', 'PLATE_TYPE', '92式武警车', 4, '4')");
		runSQL("insert into CSL_DICT_ITEM (id, dict_id, options, ranking, codes) values ('15A4FCC06D8E4113A3D2FEB147FF3651', 'PLATE_TYPE', '左右军车', 5, '5')");
		runSQL("insert into CSL_DICT_ITEM (id, dict_id, options, ranking, codes) values ('1BBA9109AA064D7A862553723D99A294', 'PLATE_TYPE', '02式个性化车', 7, '7')");
		runSQL("insert into CSL_DICT_ITEM (id, dict_id, options, ranking, codes) values ('854BC1D866D34D50AD2B7271FDEB5F73', 'PLATE_TYPE', '黄色双行尾牌', 8, '8')");
		runSQL("insert into CSL_DICT_ITEM (id, dict_id, options, ranking, codes) values ('3DAA7DB045E7401297A1D1B654334E11', 'PLATE_TYPE', '04式新军车', 9, '9')");
		runSQL("insert into CSL_DICT_ITEM (id, dict_id, options, ranking, codes) values ('A388981CFE284252B9B70155BA8305BC', 'PLATE_TYPE', '使馆车', 10, '10')");
		runSQL("insert into CSL_DICT_ITEM (id, dict_id, options, ranking, codes) values ('0ADF4A336D814C478B62376D42E0508C', 'PLATE_TYPE', '一行结构的新武警车', 11, '11')");
		runSQL("insert into CSL_DICT_ITEM (id, dict_id, options, ranking, codes) values ('F98A8D95C54A4965A0E57E4831FC42C5', 'PLATE_TYPE', '两行结构的新武警车', 12, '12')");
		runSQL("insert into CSL_DICT_ITEM (id, dict_id, options, ranking, codes) values ('C601098602E04A51A555B4697E4BF7FA', 'PLATE_TYPE', '黄色1225农用车', 13, '13')");
		runSQL("insert into CSL_DICT_ITEM (id, dict_id, options, ranking, codes) values ('3D280BFB302B4361BF7D35C4D4EE4E93', 'PLATE_TYPE', '绿色1325农用车', 14, '14')");
		runSQL("insert into CSL_DICT_ITEM (id, dict_id, options, ranking, codes) values ('CD6A9D24F57E4E85936711F69E3C4ED6', 'PLATE_TYPE', '黄色1325农用车', 15, '15')");
		runSQL("insert into CSL_DICT_ITEM (id, dict_id, options, ranking, codes) values ('BB33558EA423448BA053E12B59FE1777', 'PLATE_TYPE', '摩托车', 16, '16')");
		runSQL("insert into CSL_DICT_ITEM (id, dict_id, options, ranking, codes) values ('4D3C3FD29BD1424DA0BD93689DED3005', 'PLATE_TYPE', '教练车', 100, '100')");
		runSQL("insert into CSL_DICT_ITEM (id, dict_id, options, ranking, codes) values ('071C7577B2DB4810B05586B419A2F6F3', 'PLATE_TYPE', '临时行驶车', 101, '101')");
		runSQL("insert into CSL_DICT_ITEM (id, dict_id, options, ranking, codes) values ('EDDB2F75A22B463ABAC7A50416532F7F', 'PLATE_TYPE', '挂车', 102, '102')");
		runSQL("insert into CSL_DICT_ITEM (id, dict_id, options, ranking, codes) values ('CE40B52E82D64345B3AC385D6B881CC9', 'PLATE_TYPE', '领馆汽车', 103, '103')");
		runSQL("insert into CSL_DICT_ITEM (id, dict_id, options, ranking, codes) values ('B34DCF46467E4BD69B85330759AEE55E', 'PLATE_TYPE', '港澳出入车', 104, '104')");
		runSQL("insert into CSL_DICT_ITEM (id, dict_id, options, ranking, codes) values ('6ED1C9E793D642688971AB96BCEA437F', 'PLATE_TYPE', '临时入境车', 105, '105')");
		runSQL("insert into CSL_DICT_ITEM (id, dict_id, options, ranking, codes) values ('4D156A3F07884F0F9829295CC04EE78D', 'VEHICLE_COLOR', '白', 1, '1')");
		runSQL("insert into CSL_DICT_ITEM (id, dict_id, options, ranking, codes) values ('31E921F4D56C4D2CB4B99CAE551946DA', 'VEHICLE_COLOR', '银', 2, '2')");
		runSQL("insert into CSL_DICT_ITEM (id, dict_id, options, ranking, codes) values ('CDB9FF0247ED4A5E926AF5CE033BE94A', 'VEHICLE_COLOR', '灰', 3, '3')");
		runSQL("insert into CSL_DICT_ITEM (id, dict_id, options, ranking, codes) values ('77C2AB9C4D8D463B95E9EB948AE0F762', 'VEHICLE_COLOR', '黑', 4, '4')");
		runSQL("insert into CSL_DICT_ITEM (id, dict_id, options, ranking, codes) values ('DE053257861F416589EE4DADE452CF59', 'VEHICLE_COLOR', '红', 5, '5')");
		runSQL("insert into CSL_DICT_ITEM (id, dict_id, options, ranking, codes) values ('2F16922C549A42419AB0A41BFB0C595F', 'VEHICLE_COLOR', '深蓝', 6, '6')");
		runSQL("insert into CSL_DICT_ITEM (id, dict_id, options, ranking, codes) values ('4F31973187BA4E59BD2DB5C9712C34CF', 'VEHICLE_COLOR', '蓝', 7, '7')");
		runSQL("insert into CSL_DICT_ITEM (id, dict_id, options, ranking, codes) values ('311085577C1B417B884B0ACC666C6FC0', 'VEHICLE_COLOR', '黄', 8, '8')");
		runSQL("insert into CSL_DICT_ITEM (id, dict_id, options, ranking, codes) values ('1DAF3534467346AD9786491B9A60EF7E', 'VEHICLE_COLOR', '绿', 9, '9')");
		runSQL("insert into CSL_DICT_ITEM (id, dict_id, options, ranking, codes) values ('C4DA7D67FAF14DBB86BDE52A6F43CDD7', 'VEHICLE_COLOR', '棕', 10, '10')");
		runSQL("insert into CSL_DICT_ITEM (id, dict_id, options, ranking, codes) values ('235C650DB9BF4BE4923A8772A5A1946D', 'VEHICLE_COLOR', '粉', 11, '11')");
		runSQL("insert into CSL_DICT_ITEM (id, dict_id, options, ranking, codes) values ('CC479E53A323442B8AB2A4DCE43A9A52', 'VEHICLE_COLOR', '紫', 12, '12')");
		runSQL("insert into CSL_DICT_ITEM (id, dict_id, options, ranking, codes) values ('7829B6347C6E4823A2AC2A557027F497', 'VEHICLE_COLOR', '深灰', 13, '13')");
		runSQL("insert into CSL_DICT_ITEM (id, dict_id, options, ranking, codes) values ('8B171F8D50594D69822B5F1B12091C14', 'VEHICLE_COLOR', '青', 14, '14')");
		runSQL("insert into CSL_DICT_ITEM (id, dict_id, options, ranking, codes) values ('AC7C11AD0CFC4476AD4F7F596C4EB657', 'VEHICLE_COLOR', '橙', 15, '15')");
		runSQL("insert into CSL_DICT_ITEM (id, dict_id, options, ranking, codes) values ('581EE277A1794077B55780BCF2F701C1', 'VEHICLE_TYPE', '未知', 0, '0')");
		runSQL("insert into CSL_DICT_ITEM (id, dict_id, options, ranking, codes) values ('23FEE58543B24E6F918F179A8E9F68C7', 'VEHICLE_TYPE', '大型客车', 1, '1')");
		runSQL("insert into CSL_DICT_ITEM (id, dict_id, options, ranking, codes) values ('2011898154314EEFA2FC3C66E09CAD90', 'VEHICLE_TYPE', '货车', 2, '2')");
		runSQL("insert into CSL_DICT_ITEM (id, dict_id, options, ranking, codes) values ('4D78D1C582DC4CAE808A6AFA788D3F62', 'VEHICLE_TYPE', '轿车', 3, '3')");
		runSQL("insert into CSL_DICT_ITEM (id, dict_id, options, ranking, codes) values ('76ACE878DA0944BDA18054F46F0EB7FF', 'VEHICLE_TYPE', '面包车', 4, '4')");
		runSQL("insert into CSL_DICT_ITEM (id, dict_id, options, ranking, codes) values ('ED826399B99C475CBA02503C58FAA908', 'VEHICLE_TYPE', '小货车', 5, '5')");
		runSQL("insert into CSL_DICT_ITEM (id, dict_id, options, ranking, codes) values ('49A52DA355C84C1E8501B791B3F7E85B', 'VEHICLE_TYPE', '行人', 6, '6')");
		runSQL("insert into CSL_DICT_ITEM (id, dict_id, options, ranking, codes) values ('F6AF54D2D31547DF869C65CD5B3B6B21', 'VEHICLE_TYPE', '二轮车', 7, '7')");
		runSQL("insert into CSL_DICT_ITEM (id, dict_id, options, ranking, codes) values ('063768AB287041828949CEFFE3C75D91', 'VEHICLE_TYPE', '三轮车', 8, '8')");
		runSQL("insert into CSL_DICT_ITEM (id, dict_id, options, ranking, codes) values ('BB66CB83038748A49289B0BDA0C9474D', 'VEHICLE_TYPE', 'SUV/MPV', 9, '9')");
		runSQL("insert into CSL_DICT_ITEM (id, dict_id, options, ranking, codes) values ('57CA81B81F744446A78A95AA9EC2102B', 'VEHICLE_TYPE', '中型客车', 10, '10')");
		runSQL("insert into CSL_DICT_ITEM (id, dict_id, options, ranking, codes) values ('1CF362A5274844FAA12B9166655C1CC4', 'VEHICLE_COLOR', '未知', 0, '0')");
		runSQL("insert into CSL_DICT_ITEM (id, dict_id, options, ranking, codes) values ('D13CDE448ECF4DD58AA1272CC6354FB5', 'PLATE_COLOR', '白色', 0, '0')");
		runSQL("insert into CSL_DICT_ITEM (id, dict_id, options, ranking, codes) values ('3B91D536CB55435780F932C36AC3A3C7', 'PLATE_COLOR', '黄色', 1, '1')");
		runSQL("insert into CSL_DICT_ITEM (id, dict_id, options, ranking, codes) values ('41780606E03B49F7AD191C69584177FB', 'PLATE_COLOR', '蓝色', 2, '2')");
		runSQL("insert into CSL_DICT_ITEM (id, dict_id, options, ranking, codes) values ('A0B6CE7382844FC8B2CDE33365EF6465', 'PLATE_COLOR', '黑色', 3, '3')");
		runSQL("insert into CSL_DICT_ITEM (id, dict_id, options, ranking, codes) values ('4A2B19E63A87497182271925642D905E', 'PLATE_COLOR', '其他颜色', 4, '4')");
		runSQL("insert into CSL_DICT_ITEM (id, dict_id, options, ranking, codes) values ('F7C7A215BDBF4FFDBFF1E940BD9C71C7', 'WQUIP_TYPE', '前置卡口', 1, 'QIANZHIKAKOU')");
		runSQL("insert into CSL_DICT_ITEM (id, dict_id, options, ranking, codes) values ('3BDB096DDA7B4C08842E76C5E30D6FA1', 'WQUIP_TYPE', '车证合一', 2, 'CHEZHENGHEYI')");
		runSQL("insert into CSL_DICT_ITEM (id, dict_id, options, ranking, codes) values ('233E65838DB846A2A57D36094B428808', 'WQUIP_TYPE', '数据门', 3, 'SHUJUMEN')");
		runSQL("insert into CSL_DICT_ITEM (id, dict_id, options, ranking, codes) values ('1D89757028484123A9010E9C6331890C', 'WQUIP_TYPE', '人证机', 4, 'RENZHENGJI')");
		runSQL("insert into CSL_DICT_ITEM (id, dict_id, options, ranking, codes) values ('ABB8A46E57BD48DBBF33114DBAE26E52', 'WQUIP_TYPE', '太赫兹', 5, 'TAIHEZI')");
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		runSQL("create table CSL_EQUIPMENT ("+
		  "id      VARCHAR2(64) not null,"+
		  "names   VARCHAR2(60),"+
		  "type    VARCHAR2(50),"+
		  "laneid  VARCHAR2(32),"+
		  "hallid  VARCHAR2(32),"+
		  "ip      VARCHAR2(20),"+
		  "url     VARCHAR2(200),"+
		  "showway VARCHAR2(200),"+
		  "remark VARCHAR2(50) ) tablespace GUAN");
		runSQL("alter table CSL_EQUIPMENT add primary key (ID) using index  tablespace GUAN");
		try {
			if(baseDao.getCount("select count(1) as count from CSL_EQUIPMENT") == 0){
				runSQL("insert into CSL_EQUIPMENT (id, names, type, laneid, hallid, ip, url, remark) values ('6531010100001-SJM01', '一号数据门', '233E65838DB846A2A57D36094B428808', null, '01', null, null, 'http://22.129.76.21:9000/collection/v1.0/')");
				runSQL("insert into CSL_EQUIPMENT (id, names, type, laneid, hallid, ip, url, remark) values ('6531010100001-CZHY00', '前置卡口', 'F7C7A215BDBF4FFDBFF1E940BD9C71C7', '6531010100001-CD00', null, null, 'http://22.129.76.32:8801/api/htapi/', '请走1号车道')");
				runSQL("insert into CSL_EQUIPMENT (id, names, type, laneid, hallid, ip, url, remark) values ('6531010100001-CZHY01', '一号安检车道', '3BDB096DDA7B4C08842E76C5E30D6FA1', '6531010100001-CD01', null, null, 'http://22.129.76.126/getdata.ashx', '有人证机')");
			}
		} catch (Exception e) {}
		///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		runSQL("create table CSL_EXPLAIN ( "+
			"tablename VARCHAR2(200), "+
			"indexs    NUMBER, "+
			"columns   VARCHAR2(200), "+
			"explain   VARCHAR2(200), "+
			"remark    VARCHAR2(200) )tablespace GUAN ");
		///////////////////////////////////////////////////////////////////////
		runSQL("create table CSL_HALL ("+
		  "id       VARCHAR2(64) not null,"+
		  "hallname VARCHAR2(60),"+
		  "ranking  NUMBER )	tablespace GUAN");
		runSQL("alter table CSL_HALL add primary key (ID) using index tablespace GUAN");
		try {
			if(baseDao.getCount("select count(1) as count from CSL_HALL") == 0){
				runSQL("insert into CSL_HALL (id, hallname, ranking) values ('01', '南关站安检厅', 1)");
			}
		} catch (Exception e) {}
		///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		runSQL("create table CSL_IMEI("+
			  "id              VARCHAR2(64) not null,"+
			  "mac             VARCHAR2(50),"+
			  "imei            VARCHAR2(50),"+
			  "imsi            VARCHAR2(50),"+
			  "phone_num       VARCHAR2(50),"+
			  "insert_time     DATE default sysdate,"+
			  "pass_time       DATE,"+
			  "ip              VARCHAR2(20),"+
			  "month_id        VARCHAR2(6) ) partition by range (month_id)("+
		    "partition MONTH_PART_201707 values less than ('201708'),"+
		    "partition MONTH_PART_201708 values less than ('201709'),"+
		    "partition MONTH_PART_201709 values less than ('201710'),"+
		    "partition MONTH_PART_201710 values less than ('201711'),"+
		    "partition MONTH_PART_201711 values less than ('201712'),"+
		    "partition MONTH_PART_201712 values less than ('201801'),"+
		    "partition MONTH_PART_201801 values less than ('201802'),"+
		    "partition MONTH_PART_201802 values less than ('201803'),"+
		    "partition MONTH_PART_201803 values less than ('201804'),"+
		    "partition MONTH_PART_201804 values less than ('201805'),"+
		    "partition MONTH_PART_201805 values less than ('201806'),"+
		    "partition MONTH_PART_201806 values less than ('201807'),"+
		    "partition MONTH_PART_201807 values less than ('201808'),"+
		    "partition MONTH_PART_201808 values less than ('201809'),"+
		    "partition MONTH_PART_201809 values less than ('201810'),"+
		    "partition MONTH_PART_201810 values less than ('201811'),"+
		    "partition MONTH_PART_201811 values less than ('201812'),"+
		    "partition MONTH_PART_201812 values less than ('201901'),"+
		    "partition MONTH_PART_201901 values less than ('201902'),"+
		    "partition MONTH_PART_201902 values less than ('201903'),"+
		    "partition MONTH_PART_201903 values less than ('201904'),"+
		    "partition MONTH_PART_201904 values less than ('201905'),"+
		    "partition MONTH_PART_201905 values less than ('201906'),"+
		    "partition MONTH_PART_201906 values less than ('201907'),"+
		    "partition MONTH_PART_201907 values less than ('201908'),"+
		    "partition MONTH_PART_201908 values less than ('201909'),"+
		    "partition MONTH_PART_201909 values less than ('201910'),"+
		    "partition MONTH_PART_201910 values less than ('201911'),"+
		    "partition MONTH_PART_201911 values less than ('201912'),"+
		    "partition MONTH_PART_201912 values less than ('202001'),"+
		    "partition MONTH_PART_202001 values less than ('202002'),"+
		    "partition MONTH_PART_202002 values less than ('202003'),"+
		    "partition MONTH_PART_202003 values less than ('202004'),"+
		    "partition MONTH_PART_202004 values less than ('202005'),"+
		    "partition MONTH_PART_202005 values less than ('202006'),"+
		    "partition MONTH_PART_202006 values less than ('202007'),"+
		    "partition MONTH_PART_202007 values less than ('202008'),"+
		    "partition MONTH_PART_202008 values less than ('202009'),"+
		    "partition MONTH_PART_202009 values less than ('202010'),"+
		    "partition MONTH_PART_202010 values less than ('202011'),"+
		    "partition MONTH_PART_202011 values less than ('202012'),"+
		    "partition MONTH_PART_202012 values less than ('202101')"+
		")");
		runSQL("alter table CSL_IMEI add primary key (ID) using index  tablespace GUAN");
		/////////////////////////////////////////////////////////////////////////////////
		runSQL("create table CSL_LANE(id VARCHAR2(32) not null,lanename VARCHAR2(60), ranking  NUMBER )	tablespace GUAN");
		runSQL("alter table CSL_LANE add primary key (ID) using index tablespace GUAN");
		try {
			if(baseDao.getCount("select count(1) as count from CSL_LANE") == 0){
				runSQL("insert into CSL_LANE (id, lanename, ranking) values ('6531010100001-CD00', '前置卡口', 0)");
				runSQL("insert into CSL_LANE (id, lanename, ranking) values ('6531010100001-CD01', '一号安检车道', 1)");
			}
		} catch (Exception e) {}
		///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		runSQL("create table CSL_MENU ("+
		  "id        VARCHAR2(32) not null,"+
		  "menu_name VARCHAR2(100),"+
		  "ranking   NUMBER,"+
		  "href      VARCHAR2(200),"+
		  "icon      VARCHAR2(200) ) tablespace GUAN");
		runSQL("alter table CSL_MENU add primary key (ID) using index");
		runSQL("delete from CSL_MENU");
		runSQL("insert into CSL_MENU (id, menu_name, ranking, href, icon)values ('3BB4D2DDDE9F44DC922BB5993E0F7E99', '组织机构', 1, 'orgzon_list', null)");
		runSQL("insert into CSL_MENU (id, menu_name, ranking, href, icon)values ('92BDE591ACAF4B64815E90B6FEBD5934', '用户管理', 2, 'user_list', null)");
		runSQL("insert into CSL_MENU (id, menu_name, ranking, href, icon)values ('8A46C9DBB6D249EA93F18E91FA6CC320', '权限管理', 3, 'role_list', null)");
		runSQL("insert into CSL_MENU (id, menu_name, ranking, href, icon)values ('7FF39C35912C475DA6A515DCD2E5CFDB', '数据字典', 4, 'dict_list', null)");
		runSQL("insert into CSL_MENU (id, menu_name, ranking, href, icon)values ('3BD3F300F44443889B7BE85E7335B6E2', '安检厅管理', 5, 'hall_list', null)");
		runSQL("insert into CSL_MENU (id, menu_name, ranking, href, icon)values ('E91689DFAC5342EDBAF86367C74F801B', '车道管理', 6, 'lane_list', null)");
		runSQL("insert into CSL_MENU (id, menu_name, ranking, href, icon)values ('4DA67186E1E641CC86B02F5941C4B537', '安检厅设备', 7, 'equipment_list', null)");
		runSQL("insert into CSL_MENU (id, menu_name, ranking, href, icon)values ('22DF48D313AB4515A50C5C0EC318B914', '车道设备', 8, 'equipment_list', null)");
		///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		runSQL("create table CSL_ORG("+
		  "id          VARCHAR2(64) not null,"+
		  "pid         VARCHAR2(64),"+
		  "prio        NUMBER,"+
		  "is_root     NUMBER default 0,"+
		  "names       VARCHAR2(100),"+
		  "is_leaf     NUMBER default 1,"+
		  "codes       VARCHAR2(50),"+
		  "detail_name VARCHAR2(300),"+
		  "x           VARCHAR2(20),"+
		  "y           VARCHAR2(20))tablespace GUAN");
		runSQL("alter table CSL_ORG add primary key (ID) using index tablespace GUAN");
		try {
			if(baseDao.getCount("select count(1) as count from CSL_ORG") == 0){
				runSQL("insert into CSL_ORG (id, pid, prio, is_root, names, is_leaf, codes, detail_name, x, y)values ('4F550067FE354693839820BF412B9459', '00000', 0, 1, '新疆维吾尔自治区', 0, '650', null, null, null)");
				runSQL("insert into CSL_ORG (id, pid, prio, is_root, names, is_leaf, codes, detail_name, x, y)values ('2DBBC274D07F41858D50911781F8AD59', '4F550067FE354693839820BF412B9459', 653100, 0, '喀什地区', 0, '653100', null, null, null)");
				runSQL("insert into CSL_ORG (id, pid, prio, is_root, names, is_leaf, codes, detail_name, x, y)values ('B6253CB25E0C4F46A305ECF1C0ACD913', '2DBBC274D07F41858D50911781F8AD59', 653101, 0, '喀什市', 0, '653101', null, null, null)");
				runSQL("insert into CSL_ORG (id, pid, prio, is_root, names, is_leaf, codes, detail_name, x, y)values ('44230688DD7044178D13870FC415FF30', 'B6253CB25E0C4F46A305ECF1C0ACD913', 100001, 0, '南关检查站', 1, '6531010100001', null, null, null)");
			}
		} catch (Exception e) {}
		////////////////////////////////////////////////////////////////////////////////////////////////////////
		runSQL("create table CSL_PASSENGER("+
		  "month_id    VARCHAR2(6),"+
		  "id          VARCHAR2(64) not null,"+
		  "vehicle_id  VARCHAR2(50),"+
		  "user_name   VARCHAR2(100),"+
		  "sex         VARCHAR2(20),"+
		  "minzu       VARCHAR2(100),"+
		  "card_num    VARCHAR2(50),"+
		  "birth_date  VARCHAR2(50),"+
		  "address     VARCHAR2(500),"+
		  "qianfa      VARCHAR2(100),"+
		  "youxiaoqi   VARCHAR2(50),"+
		  "card_img    VARCHAR2(200),"+
		  "slevel      VARCHAR2(50),"+
		  "is_driver   VARCHAR2(1) default 0,"+
		  "result      VARCHAR2(50),"+
		  "passdate    DATE,"+
		  "insert_time DATE default sysdate)partition by range (MONTH_ID)("+
		  "partition MONTH_PART_201703 values less than ('201704'),"+
		  "partition MONTH_PART_201704 values less than ('201705'),"+
		  "partition MONTH_PART_201705 values less than ('201706'),"+
		  "partition MONTH_PART_201706 values less than ('201707'),"+
		  "partition MONTH_PART_201707 values less than ('201708'),"+
		  "partition MONTH_PART_201708 values less than ('201709'),"+
		  "partition MONTH_PART_201709 values less than ('201710'),"+
		  "partition MONTH_PART_201710 values less than ('201711'),"+
		  "partition MONTH_PART_201711 values less than ('201712'),"+
		  "partition MONTH_PART_201712 values less than ('201801'),"+
		  "partition MONTH_PART_201801 values less than ('201802'),"+
		  "partition MONTH_PART_201802 values less than ('201803'),"+
		  "partition MONTH_PART_201803 values less than ('201804'),"+
		  "partition MONTH_PART_201804 values less than ('201805'),"+
		  "partition MONTH_PART_201805 values less than ('201806'),"+
		  "partition MONTH_PART_201806 values less than ('201807'),"+
		  "partition MONTH_PART_201807 values less than ('201808'),"+
		  "partition MONTH_PART_201808 values less than ('201809'),"+
		  "partition MONTH_PART_201809 values less than ('201810'),"+
		  "partition MONTH_PART_201810 values less than ('201811'),"+
		  "partition MONTH_PART_201811 values less than ('201812'),"+
		  "partition MONTH_PART_201812 values less than ('201901'),"+
		  "partition MONTH_PART_201901 values less than ('201902'),"+
		  "partition MONTH_PART_201902 values less than ('201903'),"+
		  "partition MONTH_PART_201903 values less than ('201904'),"+
		  "partition MONTH_PART_201904 values less than ('201905'),"+
		  "partition MONTH_PART_201905 values less than ('201906'),"+
		  "partition MONTH_PART_201906 values less than ('201907'),"+
		  "partition MONTH_PART_201907 values less than ('201908'),"+
		  "partition MONTH_PART_201908 values less than ('201909'),"+
		  "partition MONTH_PART_201909 values less than ('201910'),"+
		  "partition MONTH_PART_201910 values less than ('201911'),"+
		  "partition MONTH_PART_201911 values less than ('201912'),"+
		  "partition MONTH_PART_201912 values less than ('202001'),"+
		  "partition MONTH_PART_202001 values less than ('202002'),"+
		  "partition MONTH_PART_202002 values less than ('202003'),"+
		  "partition MONTH_PART_202003 values less than ('202004'),"+
		  "partition MONTH_PART_202004 values less than ('202005'),"+
		  "partition MONTH_PART_202005 values less than ('202006'),"+
		  "partition MONTH_PART_202006 values less than ('202007'),"+
		  "partition MONTH_PART_202007 values less than ('202008'),"+
		  "partition MONTH_PART_202008 values less than ('202009'),"+
		  "partition MONTH_PART_202009 values less than ('202010'),"+
		  "partition MONTH_PART_202010 values less than ('202011'),"+
		  "partition MONTH_PART_202011 values less than ('202012'),"+
		  "partition MONTH_PART_202012 values less than ('202101'))");
		runSQL("alter table CSL_PASSENGER add primary key (ID) using index tablespace GUAN");
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////
		runSQL("create table CSL_PERSON("+
		  "month_id      VARCHAR2(6),"+
		  "id            VARCHAR2(64) not null,"+
		  "names         VARCHAR2(50),"+
		  "nation        VARCHAR2(40),"+
		  "gender        VARCHAR2(10),"+
		  "birth         DATE,"+
		  "idcard        VARCHAR2(18),"+
		  "equipment_id  VARCHAR2(32),"+
		  "capture_time  DATE,"+
		  "address       VARCHAR2(500),"+
		  "start_date    VARCHAR2(20),"+
		  "end_date      VARCHAR2(20),"+
		  "is_a_person   VARCHAR2(1),"+
		  "img_url       VARCHAR2(200),"+
		  "local_img_url VARCHAR2(200),"+
		  "error_count   NUMBER default 0,"+
		  "location      VARCHAR2(100),"+
		  "is_check      VARCHAR2(100),"+
		  "insert_time   DATE default sysdate)partition by range (MONTH_ID)("+
		  "partition MONTH_PART_201703 values less than ('201704'),"+
		  "partition MONTH_PART_201704 values less than ('201705'),"+
		  "partition MONTH_PART_201705 values less than ('201706'),"+
		  "partition MONTH_PART_201706 values less than ('201707'),"+
		  "partition MONTH_PART_201707 values less than ('201708'),"+
		  "partition MONTH_PART_201708 values less than ('201709'),"+
		  "partition MONTH_PART_201709 values less than ('201710'),"+
		  "partition MONTH_PART_201710 values less than ('201711'),"+
		  "partition MONTH_PART_201711 values less than ('201712'),"+
		  "partition MONTH_PART_201712 values less than ('201801'),"+
		  "partition MONTH_PART_201801 values less than ('201802'),"+
		  "partition MONTH_PART_201802 values less than ('201803'),"+
		  "partition MONTH_PART_201803 values less than ('201804'),"+
		  "partition MONTH_PART_201804 values less than ('201805'),"+
		  "partition MONTH_PART_201805 values less than ('201806'),"+
		  "partition MONTH_PART_201806 values less than ('201807'),"+
		  "partition MONTH_PART_201807 values less than ('201808'),"+
		  "partition MONTH_PART_201808 values less than ('201809'),"+
		  "partition MONTH_PART_201809 values less than ('201810'),"+
		  "partition MONTH_PART_201810 values less than ('201811'),"+
		  "partition MONTH_PART_201811 values less than ('201812'),"+
		  "partition MONTH_PART_201812 values less than ('201901'),"+
		  "partition MONTH_PART_201901 values less than ('201902'),"+
		  "partition MONTH_PART_201902 values less than ('201903'),"+
		  "partition MONTH_PART_201903 values less than ('201904'),"+
		  "partition MONTH_PART_201904 values less than ('201905'),"+
		  "partition MONTH_PART_201905 values less than ('201906'),"+
		  "partition MONTH_PART_201906 values less than ('201907'),"+
		  "partition MONTH_PART_201907 values less than ('201908'),"+
		  "partition MONTH_PART_201908 values less than ('201909'),"+
		  "partition MONTH_PART_201909 values less than ('201910'),"+
		  "partition MONTH_PART_201910 values less than ('201911'),"+
		  "partition MONTH_PART_201911 values less than ('201912'),"+
		  "partition MONTH_PART_201912 values less than ('202001'),"+
		  "partition MONTH_PART_202001 values less than ('202002'),"+
		  "partition MONTH_PART_202002 values less than ('202003'),"+
		  "partition MONTH_PART_202003 values less than ('202004'),"+
		  "partition MONTH_PART_202004 values less than ('202005'),"+
		  "partition MONTH_PART_202005 values less than ('202006'),"+
		  "partition MONTH_PART_202006 values less than ('202007'),"+
		  "partition MONTH_PART_202007 values less than ('202008'),"+
		  "partition MONTH_PART_202008 values less than ('202009'),"+
		  "partition MONTH_PART_202009 values less than ('202010'),"+
		  "partition MONTH_PART_202010 values less than ('202011'),"+
		  "partition MONTH_PART_202011 values less than ('202012'),"+
		  "partition MONTH_PART_202012 values less than ('202101'))");
		runSQL("alter table CSL_PERSON add primary key (ID)using index tablespace GUAN");
		/////////////////////////////////////////////////////////////////////////////////////
		runSQL("create table CSL_PERSON_WARNING("+
		  "id          VARCHAR2(64) not null,"+
		  "names       VARCHAR2(50),"+
		  "tag         VARCHAR2(1000),"+
		  "gender      VARCHAR2(10),"+
		  "idcard      VARCHAR2(18),"+
		  "message     VARCHAR2(1000),"+
		  "reserved1   VARCHAR2(200),"+
		  "reserved2   VARCHAR2(200),"+
		  "reserved3   VARCHAR2(200),"+
		  "reserved4   VARCHAR2(200),"+
		  "month_id    VARCHAR2(6),"+
		  "insert_time DATE default sysdate)	partition by range (MONTH_ID)("+
		  "partition MONTH_PART_201703 values less than ('201704'),"+
		  "partition MONTH_PART_201704 values less than ('201705'),"+
		  "partition MONTH_PART_201705 values less than ('201706'),"+
		  "partition MONTH_PART_201706 values less than ('201707'),"+
		  "partition MONTH_PART_201707 values less than ('201708'),"+
		  "partition MONTH_PART_201708 values less than ('201709'),"+
		  "partition MONTH_PART_201709 values less than ('201710'),"+
		  "partition MONTH_PART_201710 values less than ('201711'),"+
		  "partition MONTH_PART_201711 values less than ('201712'),"+
		  "partition MONTH_PART_201712 values less than ('201801'),"+
		  "partition MONTH_PART_201801 values less than ('201802'),"+
		  "partition MONTH_PART_201802 values less than ('201803'),"+
		  "partition MONTH_PART_201803 values less than ('201804'),"+
		  "partition MONTH_PART_201804 values less than ('201805'),"+
		  "partition MONTH_PART_201805 values less than ('201806'),"+
		  "partition MONTH_PART_201806 values less than ('201807'),"+
		  "partition MONTH_PART_201807 values less than ('201808'),"+
		  "partition MONTH_PART_201808 values less than ('201809'),"+
		  "partition MONTH_PART_201809 values less than ('201810'),"+
		  "partition MONTH_PART_201810 values less than ('201811'),"+
		  "partition MONTH_PART_201811 values less than ('201812'),"+
		  "partition MONTH_PART_201812 values less than ('201901'),"+
		  "partition MONTH_PART_201901 values less than ('201902'),"+
		  "partition MONTH_PART_201902 values less than ('201903'),"+
		  "partition MONTH_PART_201903 values less than ('201904'),"+
		  "partition MONTH_PART_201904 values less than ('201905'),"+
		  "partition MONTH_PART_201905 values less than ('201906'),"+
		  "partition MONTH_PART_201906 values less than ('201907'),"+
		  "partition MONTH_PART_201907 values less than ('201908'),"+
		  "partition MONTH_PART_201908 values less than ('201909'),"+
		  "partition MONTH_PART_201909 values less than ('201910'),"+
		  "partition MONTH_PART_201910 values less than ('201911'),"+
		  "partition MONTH_PART_201911 values less than ('201912'),"+
		  "partition MONTH_PART_201912 values less than ('202001'),"+
		  "partition MONTH_PART_202001 values less than ('202002'),"+
		  "partition MONTH_PART_202002 values less than ('202003'),"+
		  "partition MONTH_PART_202003 values less than ('202004'),"+
		  "partition MONTH_PART_202004 values less than ('202005'),"+
		  "partition MONTH_PART_202005 values less than ('202006'),"+
		  "partition MONTH_PART_202006 values less than ('202007'),"+
		  "partition MONTH_PART_202007 values less than ('202008'),"+
		  "partition MONTH_PART_202008 values less than ('202009'),"+
		  "partition MONTH_PART_202009 values less than ('202010'),"+
		  "partition MONTH_PART_202010 values less than ('202011'),"+
		  "partition MONTH_PART_202011 values less than ('202012'),"+
		  "partition MONTH_PART_202012 values less than ('202101'))");
		runSQL("alter table CSL_PERSON_WARNING add primary key (ID) using index tablespace GUAN");
		////////////////////////////////////////////////////////////////////////////////////////////
		runSQL("create table CSL_ROLE(id VARCHAR2(32) not null,role_name VARCHAR2(100),is_base VARCHAR2(1) default 0)tablespace GUAN");
		runSQL("alter table CSL_ROLE add primary key (ID) using index tablespace GUAN");
		try {
			if(baseDao.getCount("select count(1) as count from CSL_ROLE") == 0){
				runSQL("insert into CSL_ROLE (id, role_name, is_base)values ('1', '普通用户', '1')");
				runSQL("insert into CSL_ROLE (id, role_name, is_base)values ('D18873735CFE4EE890D77BE409BCA0C5', '系统管理员', '0')");
			}
		} catch (Exception e) {}
		///////////////////////////////////////////////////////////////////////////////////////////////////////
		runSQL("create table CSL_ROLE_MENU(id VARCHAR2(64) not null,role_id   VARCHAR2(64),menu_id   VARCHAR2(64),data_auth VARCHAR2(10) default 1)tablespace GUAN");
		runSQL("alter table CSL_ROLE_MENU add primary key (ID) using index tablespace GUAN");
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////
		runSQL("create table CSL_ROLE_USER(id VARCHAR2(32) not null,role_id VARCHAR2(32),user_id VARCHAR2(32))tablespace GUAN");
		runSQL("alter table CSL_ROLE_USER add primary key (ID) using index tablespace GUAN");
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////
		runSQL("create table CSL_TRANSFER("+
		  "id          VARCHAR2(64) not null,"+
		  "ver         VARCHAR2(50),"+
		  "tran_no     VARCHAR2(50),"+
		  "tran_type   VARCHAR2(10),"+
		  "tran_result VARCHAR2(10),"+
		  "tran_msg    VARCHAR2(2000),"+
		  "send_time   VARCHAR2(30),"+
		  "key         VARCHAR2(200),"+
		  "data        CLOB,"+
		  "month_id    VARCHAR2(6))partition by range (MONTH_ID)("+
		  "partition MONTH_PART_201703 values less than ('201704'),"+
		  "partition MONTH_PART_201704 values less than ('201705'),"+
		  "partition MONTH_PART_201705 values less than ('201706'),"+
		  "partition MONTH_PART_201706 values less than ('201707'),"+
		  "partition MONTH_PART_201707 values less than ('201708'),"+
		  "partition MONTH_PART_201708 values less than ('201709'),"+
		  "partition MONTH_PART_201709 values less than ('201710'),"+
		  "partition MONTH_PART_201710 values less than ('201711'),"+
		  "partition MONTH_PART_201711 values less than ('201712'),"+
		  "partition MONTH_PART_201712 values less than ('201801'),"+
		  "partition MONTH_PART_201801 values less than ('201802'),"+
		  "partition MONTH_PART_201802 values less than ('201803'),"+
		  "partition MONTH_PART_201803 values less than ('201804'),"+
		  "partition MONTH_PART_201804 values less than ('201805'),"+
		  "partition MONTH_PART_201805 values less than ('201806'),"+
		  "partition MONTH_PART_201806 values less than ('201807'),"+
		  "partition MONTH_PART_201807 values less than ('201808'),"+
		  "partition MONTH_PART_201808 values less than ('201809'),"+
		  "partition MONTH_PART_201809 values less than ('201810'),"+
		  "partition MONTH_PART_201810 values less than ('201811'),"+
		  "partition MONTH_PART_201811 values less than ('201812'),"+
		  "partition MONTH_PART_201812 values less than ('201901'),"+
		  "partition MONTH_PART_201901 values less than ('201902'),"+
		  "partition MONTH_PART_201902 values less than ('201903'),"+
		  "partition MONTH_PART_201903 values less than ('201904'),"+
		  "partition MONTH_PART_201904 values less than ('201905'),"+
		  "partition MONTH_PART_201905 values less than ('201906'),"+
		  "partition MONTH_PART_201906 values less than ('201907'),"+
		  "partition MONTH_PART_201907 values less than ('201908'),"+
		  "partition MONTH_PART_201908 values less than ('201909'),"+
		  "partition MONTH_PART_201909 values less than ('201910'),"+
		  "partition MONTH_PART_201910 values less than ('201911'),"+
		  "partition MONTH_PART_201911 values less than ('201912'),"+
		  "partition MONTH_PART_201912 values less than ('202001'),"+
		  "partition MONTH_PART_202001 values less than ('202002'),"+
		  "partition MONTH_PART_202002 values less than ('202003'),"+
		  "partition MONTH_PART_202003 values less than ('202004'),"+
		  "partition MONTH_PART_202004 values less than ('202005'),"+
		  "partition MONTH_PART_202005 values less than ('202006'),"+
		  "partition MONTH_PART_202006 values less than ('202007'),"+
		  "partition MONTH_PART_202007 values less than ('202008'),"+
		  "partition MONTH_PART_202008 values less than ('202009'),"+
		  "partition MONTH_PART_202009 values less than ('202010'),"+
		  "partition MONTH_PART_202010 values less than ('202011'),"+
		  "partition MONTH_PART_202011 values less than ('202012'),"+
		  "partition MONTH_PART_202012 values less than ('202101'))");
		runSQL("alter table CSL_TRANSFER add primary key (ID) using index tablespace GUAN");
		//////////////////////////////////////////////////////////////////////////////////////////////////////////
		runSQL("create table CSL_USER("+
		  "id          VARCHAR2(32) not null,"+
		  "account     VARCHAR2(20),"+
		  "pwd         VARCHAR2(50),"+
		  "names       VARCHAR2(100),"+
		  "gender      VARCHAR2(10),"+
		  "serials     NUMBER,"+
		  "email       VARCHAR2(50),"+
		  "status      VARCHAR2(1),"+
		  "create_time DATE,"+
		  "update_time DATE,"+
		  "login_time  DATE,"+
		  "updater     VARCHAR2(32),"+
		  "org_id      VARCHAR2(32))tablespace GUAN");
		runSQL("alter table CSL_USER add primary key (ID) using index tablespace GUAN");
		//////////////////////////////////////////////////////////////////////////////////////////////////
		runSQL("create table CSL_UVSS("+
		  "month_id        VARCHAR2(6),"+
		  "id              VARCHAR2(64) not null,"+
		  "uvss_image      VARCHAR2(500),"+
		  "plate_number    VARCHAR2(20),"+
		  "check_date_time DATE,"+
		  "ip_address      VARCHAR2(20))partition by range (MONTH_ID)("+
		  "partition MONTH_PART_201703 values less than ('201704'),"+
		  "partition MONTH_PART_201704 values less than ('201705'),"+
		  "partition MONTH_PART_201705 values less than ('201706'),"+
		  "partition MONTH_PART_201706 values less than ('201707'),"+
		  "partition MONTH_PART_201707 values less than ('201708'),"+
		  "partition MONTH_PART_201708 values less than ('201709'),"+
		  "partition MONTH_PART_201709 values less than ('201710'),"+
		  "partition MONTH_PART_201710 values less than ('201711'),"+
		  "partition MONTH_PART_201711 values less than ('201712'),"+
		  "partition MONTH_PART_201712 values less than ('201801'),"+
		  "partition MONTH_PART_201801 values less than ('201802'),"+
		  "partition MONTH_PART_201802 values less than ('201803'),"+
		  "partition MONTH_PART_201803 values less than ('201804'),"+
		  "partition MONTH_PART_201804 values less than ('201805'),"+
		  "partition MONTH_PART_201805 values less than ('201806'),"+
		  "partition MONTH_PART_201806 values less than ('201807'),"+
		  "partition MONTH_PART_201807 values less than ('201808'),"+
		  "partition MONTH_PART_201808 values less than ('201809'),"+
		  "partition MONTH_PART_201809 values less than ('201810'),"+
		  "partition MONTH_PART_201810 values less than ('201811'),"+
		  "partition MONTH_PART_201811 values less than ('201812'),"+
		  "partition MONTH_PART_201812 values less than ('201901'),"+
		  "partition MONTH_PART_201901 values less than ('201902'),"+
		  "partition MONTH_PART_201902 values less than ('201903'),"+
		  "partition MONTH_PART_201903 values less than ('201904'),"+
		  "partition MONTH_PART_201904 values less than ('201905'),"+
		  "partition MONTH_PART_201905 values less than ('201906'),"+
		  "partition MONTH_PART_201906 values less than ('201907'),"+
		  "partition MONTH_PART_201907 values less than ('201908'),"+
		  "partition MONTH_PART_201908 values less than ('201909'),"+
		  "partition MONTH_PART_201909 values less than ('201910'),"+
		  "partition MONTH_PART_201910 values less than ('201911'),"+
		  "partition MONTH_PART_201911 values less than ('201912'),"+
		  "partition MONTH_PART_201912 values less than ('202001'),"+
		  "partition MONTH_PART_202001 values less than ('202002'),"+
		  "partition MONTH_PART_202002 values less than ('202003'),"+
		  "partition MONTH_PART_202003 values less than ('202004'),"+
		  "partition MONTH_PART_202004 values less than ('202005'),"+
		  "partition MONTH_PART_202005 values less than ('202006'),"+
		  "partition MONTH_PART_202006 values less than ('202007'),"+
		  "partition MONTH_PART_202007 values less than ('202008'),"+
		  "partition MONTH_PART_202008 values less than ('202009'),"+
		  "partition MONTH_PART_202009 values less than ('202010'),"+
		  "partition MONTH_PART_202010 values less than ('202011'),"+
		  "partition MONTH_PART_202011 values less than ('202012'),"+
		  "partition MONTH_PART_202012 values less than ('202101'))");
		runSQL("alter table CSL_UVSS add primary key (ID) using index tablespace GUAN");
		/////////////////////////////////////////////////////////////////////////////////////
		//创建表CSL_UVSS_PATH
		runSQL("create table CSL_UVSS_PATH ( "+
		  "month_id        VARCHAR2(6), "+
		  "id              VARCHAR2(64) not null, "+
		  "uvss_image      VARCHAR2(500), "+
		  "plate_number    VARCHAR2(20), "+
		  "check_date_time DATE, "+
		  "ip_address      VARCHAR2(20)) partition by range (MONTH_ID) ( "+
		  "partition MONTH_PART_201701 values less than ('201702'), "+
		  "partition MONTH_PART_201702 values less than ('201703'), "+
		  "partition MONTH_PART_201703 values less than ('201704'), "+
		  "partition MONTH_PART_201704 values less than ('201705'), "+
		  "partition MONTH_PART_201705 values less than ('201706'), "+
		  "partition MONTH_PART_201706 values less than ('201707'), "+
		  "partition MONTH_PART_201707 values less than ('201708'), "+
		  "partition MONTH_PART_201708 values less than ('201709'), "+
		  "partition MONTH_PART_201709 values less than ('201710'), "+
		  "partition MONTH_PART_201710 values less than ('201711'), "+
		  "partition MONTH_PART_201711 values less than ('201712'), "+
		  "partition MONTH_PART_201712 values less than ('201801'), "+
		  "partition MONTH_PART_201801 values less than ('201802'), "+
		  "partition MONTH_PART_201802 values less than ('201803'), "+
		  "partition MONTH_PART_201803 values less than ('201804'), "+
		  "partition MONTH_PART_201804 values less than ('201805'), "+
		  "partition MONTH_PART_201805 values less than ('201806'), "+
		  "partition MONTH_PART_201806 values less than ('201807'), "+
		  "partition MONTH_PART_201807 values less than ('201808'), "+
		  "partition MONTH_PART_201808 values less than ('201809'), "+
		  "partition MONTH_PART_201809 values less than ('201810'), "+
		  "partition MONTH_PART_201810 values less than ('201811'), "+
		  "partition MONTH_PART_201811 values less than ('201812'), "+
		  "partition MONTH_PART_201812 values less than ('201901'), "+
		  "partition MONTH_PART_201901 values less than ('201902'), "+
		  "partition MONTH_PART_201902 values less than ('201903'), "+
		  "partition MONTH_PART_201903 values less than ('201904'), "+
		  "partition MONTH_PART_201904 values less than ('201905'), "+
		  "partition MONTH_PART_201905 values less than ('201906'), "+
		  "partition MONTH_PART_201906 values less than ('201907'), "+
		  "partition MONTH_PART_201907 values less than ('201908'), "+
		  "partition MONTH_PART_201908 values less than ('201909'), "+
		  "partition MONTH_PART_201909 values less than ('201910'), "+
		  "partition MONTH_PART_201910 values less than ('201911'), "+
		  "partition MONTH_PART_201911 values less than ('201912'), "+
		  "partition MONTH_PART_201912 values less than ('202001'), "+
		  "partition MONTH_PART_202001 values less than ('202002'), "+
		  "partition MONTH_PART_202002 values less than ('202003'), "+
		  "partition MONTH_PART_202003 values less than ('202004'), "+
		  "partition MONTH_PART_202004 values less than ('202005'), "+
		  "partition MONTH_PART_202005 values less than ('202006'), "+
		  "partition MONTH_PART_202006 values less than ('202007'), "+
		  "partition MONTH_PART_202007 values less than ('202008'), "+
		  "partition MONTH_PART_202008 values less than ('202009'), "+
		  "partition MONTH_PART_202009 values less than ('202010'), "+
		  "partition MONTH_PART_202010 values less than ('202011'), "+
		  "partition MONTH_PART_202011 values less than ('202012'), "+
		  "partition MONTH_PART_202012 values less than ('202101') )");
		//设置主键
		runSQL("alter table CSL_UVSS_PATH add primary key (ID) using index tablespace GUAN ");
		////////////////////////////////////////////////////////////////////////////////////
		runSQL("create table CSL_VEHICLE("+
		  "month_id       VARCHAR2(6),"+
		  "id             VARCHAR2(64) not null,"+
		  "car_num        VARCHAR2(20),"+
		  "car_img        VARCHAR2(200),"+
		  "num_img        VARCHAR2(200),"+
		  "equipment_id   VARCHAR2(100),"+
		  "passdate       DATE,"+
		  "insert_time    DATE,"+
		  "plate_color_id VARCHAR2(10),"+
		  "plate_color    VARCHAR2(20),"+
		  "relust         VARCHAR2(100),"+
		  "classify       VARCHAR2(20) default 'VehicleShunt',"+
		  "vehicle_type   VARCHAR2(50),"+
		  "ip             VARCHAR2(20),"+
		  "back_url       VARCHAR2(200),"+
		  "user_name      VARCHAR2(100),"+
		  "sex            VARCHAR2(10),"+
		  "minzu          VARCHAR2(100),"+
		  "card_num       VARCHAR2(30),"+
		  "birth_date     VARCHAR2(30),"+
		  "address        VARCHAR2(500),"+
		  "qianfa         VARCHAR2(100),"+
		  "youxiaoqi      VARCHAR2(50),"+
		  "card_img       VARCHAR2(500))partition by range (MONTH_ID)("+
		  "partition MONTH_PART_201701 values less than ('201702'), "+
		  "partition MONTH_PART_201702 values less than ('201703'), "+
		  "partition MONTH_PART_201703 values less than ('201704'), "+
		  "partition MONTH_PART_201704 values less than ('201705'), "+
		  "partition MONTH_PART_201705 values less than ('201706'), "+
		  "partition MONTH_PART_201706 values less than ('201707'), "+
		  "partition MONTH_PART_201707 values less than ('201708'), "+
		  "partition MONTH_PART_201708 values less than ('201709'), "+
		  "partition MONTH_PART_201709 values less than ('201710'), "+
		  "partition MONTH_PART_201710 values less than ('201711'), "+
		  "partition MONTH_PART_201711 values less than ('201712'), "+
		  "partition MONTH_PART_201712 values less than ('201801'), "+
		  "partition MONTH_PART_201801 values less than ('201802'), "+
		  "partition MONTH_PART_201802 values less than ('201803'), "+
		  "partition MONTH_PART_201803 values less than ('201804'), "+
		  "partition MONTH_PART_201804 values less than ('201805'), "+
		  "partition MONTH_PART_201805 values less than ('201806'), "+
		  "partition MONTH_PART_201806 values less than ('201807'), "+
		  "partition MONTH_PART_201807 values less than ('201808'), "+
		  "partition MONTH_PART_201808 values less than ('201809'), "+
		  "partition MONTH_PART_201809 values less than ('201810'), "+
		  "partition MONTH_PART_201810 values less than ('201811'), "+
		  "partition MONTH_PART_201811 values less than ('201812'), "+
		  "partition MONTH_PART_201812 values less than ('201901'), "+
		  "partition MONTH_PART_201901 values less than ('201902'), "+
		  "partition MONTH_PART_201902 values less than ('201903'), "+
		  "partition MONTH_PART_201903 values less than ('201904'), "+
		  "partition MONTH_PART_201904 values less than ('201905'), "+
		  "partition MONTH_PART_201905 values less than ('201906'), "+
		  "partition MONTH_PART_201906 values less than ('201907'), "+
		  "partition MONTH_PART_201907 values less than ('201908'), "+
		  "partition MONTH_PART_201908 values less than ('201909'), "+
		  "partition MONTH_PART_201909 values less than ('201910'), "+
		  "partition MONTH_PART_201910 values less than ('201911'), "+
		  "partition MONTH_PART_201911 values less than ('201912'), "+
		  "partition MONTH_PART_201912 values less than ('202001'), "+
		  "partition MONTH_PART_202001 values less than ('202002'), "+
		  "partition MONTH_PART_202002 values less than ('202003'), "+
		  "partition MONTH_PART_202003 values less than ('202004'), "+
		  "partition MONTH_PART_202004 values less than ('202005'), "+
		  "partition MONTH_PART_202005 values less than ('202006'), "+
		  "partition MONTH_PART_202006 values less than ('202007'), "+
		  "partition MONTH_PART_202007 values less than ('202008'), "+
		  "partition MONTH_PART_202008 values less than ('202009'), "+
		  "partition MONTH_PART_202009 values less than ('202010'), "+
		  "partition MONTH_PART_202010 values less than ('202011'), "+
		  "partition MONTH_PART_202011 values less than ('202012'), "+
		  "partition MONTH_PART_202012 values less than ('202101') )");
		runSQL("alter table CSL_VEHICLE add primary key (ID) using index tablespace GUAN");
		//////////////////////////////////////////////////////////////////////////////////////
		runSQL("create table CSL_VEHICLE_BRAKE("+
		  "month_id       VARCHAR2(6),"+
		  "id             VARCHAR2(64) not null,"+
		  "car_num        VARCHAR2(20),"+
		  "car_img        VARCHAR2(200),"+
		  "num_img        VARCHAR2(200),"+
		  "equipment_id   VARCHAR2(100),"+
		  "passdate       DATE,"+
		  "insert_time    DATE,"+
		  "plate_color_id VARCHAR2(10),"+
		  "plate_color    VARCHAR2(20),"+
		  "states         VARCHAR2(10) default -1,"+
		  "remark         VARCHAR2(500),"+
		  "relust         VARCHAR2(100),"+
		  "vehicle_type   VARCHAR2(50),"+
		  "local_car_img  VARCHAR2(200),"+
		  "local_num_img  VARCHAR2(200),"+
		  "error_count    NUMBER default 0,"+
		  "tran_type      VARCHAR2(20),"+
		  "ip             VARCHAR2(20),"+
		  "back_url       VARCHAR2(200),"+
		  "is_upload      VARCHAR2(1000) default 0,"+
		  "user_name      VARCHAR2(100),"+
		  "sex            VARCHAR2(10),"+
		  "minzu          VARCHAR2(100),"+
		  "card_num       VARCHAR2(30),"+
		  "birth_date     VARCHAR2(30),"+
		  "address        VARCHAR2(500),"+
		  "qianfa         VARCHAR2(100),"+
		  "youxiaoqi      VARCHAR2(50),"+
		  "card_img       VARCHAR2(500),"+
		  "local_card_img VARCHAR2(500))	partition by range (MONTH_ID)("+
		  "partition MONTH_PART_201701 values less than ('201702'), "+
		  "partition MONTH_PART_201702 values less than ('201703'), "+
		  "partition MONTH_PART_201703 values less than ('201704'), "+
		  "partition MONTH_PART_201704 values less than ('201705'), "+
		  "partition MONTH_PART_201705 values less than ('201706'), "+
		  "partition MONTH_PART_201706 values less than ('201707'), "+
		  "partition MONTH_PART_201707 values less than ('201708'), "+
		  "partition MONTH_PART_201708 values less than ('201709'), "+
		  "partition MONTH_PART_201709 values less than ('201710'), "+
		  "partition MONTH_PART_201710 values less than ('201711'), "+
		  "partition MONTH_PART_201711 values less than ('201712'), "+
		  "partition MONTH_PART_201712 values less than ('201801'), "+
		  "partition MONTH_PART_201801 values less than ('201802'), "+
		  "partition MONTH_PART_201802 values less than ('201803'), "+
		  "partition MONTH_PART_201803 values less than ('201804'), "+
		  "partition MONTH_PART_201804 values less than ('201805'), "+
		  "partition MONTH_PART_201805 values less than ('201806'), "+
		  "partition MONTH_PART_201806 values less than ('201807'), "+
		  "partition MONTH_PART_201807 values less than ('201808'), "+
		  "partition MONTH_PART_201808 values less than ('201809'), "+
		  "partition MONTH_PART_201809 values less than ('201810'), "+
		  "partition MONTH_PART_201810 values less than ('201811'), "+
		  "partition MONTH_PART_201811 values less than ('201812'), "+
		  "partition MONTH_PART_201812 values less than ('201901'), "+
		  "partition MONTH_PART_201901 values less than ('201902'), "+
		  "partition MONTH_PART_201902 values less than ('201903'), "+
		  "partition MONTH_PART_201903 values less than ('201904'), "+
		  "partition MONTH_PART_201904 values less than ('201905'), "+
		  "partition MONTH_PART_201905 values less than ('201906'), "+
		  "partition MONTH_PART_201906 values less than ('201907'), "+
		  "partition MONTH_PART_201907 values less than ('201908'), "+
		  "partition MONTH_PART_201908 values less than ('201909'), "+
		  "partition MONTH_PART_201909 values less than ('201910'), "+
		  "partition MONTH_PART_201910 values less than ('201911'), "+
		  "partition MONTH_PART_201911 values less than ('201912'), "+
		  "partition MONTH_PART_201912 values less than ('202001'), "+
		  "partition MONTH_PART_202001 values less than ('202002'), "+
		  "partition MONTH_PART_202002 values less than ('202003'), "+
		  "partition MONTH_PART_202003 values less than ('202004'), "+
		  "partition MONTH_PART_202004 values less than ('202005'), "+
		  "partition MONTH_PART_202005 values less than ('202006'), "+
		  "partition MONTH_PART_202006 values less than ('202007'), "+
		  "partition MONTH_PART_202007 values less than ('202008'), "+
		  "partition MONTH_PART_202008 values less than ('202009'), "+
		  "partition MONTH_PART_202009 values less than ('202010'), "+
		  "partition MONTH_PART_202010 values less than ('202011'), "+
		  "partition MONTH_PART_202011 values less than ('202012'), "+
		  "partition MONTH_PART_202012 values less than ('202101') )");
		runSQL("create index CLS_VEHICLE_BRAKE_CAR_NUM on CSL_VEHICLE_BRAKE (CAR_NUM) tablespace GUAN");
		runSQL("alter table CSL_VEHICLE_BRAKE add primary key (ID) using index tablespace GUAN");
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		runSQL("create table CSL_VEHICLE_SHUNT("+
		  "id                  VARCHAR2(64) not null,"+
		  "eqipment_id         VARCHAR2(64),"+
		  "plate               VARCHAR2(20),"+
		  "vehicle_type        VARCHAR2(50),"+
		  "vehicle_image       VARCHAR2(500),"+
		  "plate_image         VARCHAR2(500),"+
		  "passdate            DATE,"+
		  "states              VARCHAR2(10) default -1,"+
		  "plate_color_id      VARCHAR2(10),"+
		  "plate_color         VARCHAR2(20),"+
		  "remark              VARCHAR2(500),"+
		  "insert_time         DATE,"+
		  "relust              VARCHAR2(500),"+
		  "month_id            VARCHAR2(6),"+
		  "local_vehicle_image VARCHAR2(500),"+
		  "local_plate_image   VARCHAR2(500),"+
		  "error_count         NUMBER default 0,"+
		  "ip                  VARCHAR2(20),"+
		  "back_url            VARCHAR2(200),"+
		  "is_upload           VARCHAR2(1000) default 0) partition by range (MONTH_ID)("+
		  "partition MONTH_PART_201701 values less than ('201702'), "+
		  "partition MONTH_PART_201702 values less than ('201703'), "+
		  "partition MONTH_PART_201703 values less than ('201704'), "+
		  "partition MONTH_PART_201704 values less than ('201705'), "+
		  "partition MONTH_PART_201705 values less than ('201706'), "+
		  "partition MONTH_PART_201706 values less than ('201707'), "+
		  "partition MONTH_PART_201707 values less than ('201708'), "+
		  "partition MONTH_PART_201708 values less than ('201709'), "+
		  "partition MONTH_PART_201709 values less than ('201710'), "+
		  "partition MONTH_PART_201710 values less than ('201711'), "+
		  "partition MONTH_PART_201711 values less than ('201712'), "+
		  "partition MONTH_PART_201712 values less than ('201801'), "+
		  "partition MONTH_PART_201801 values less than ('201802'), "+
		  "partition MONTH_PART_201802 values less than ('201803'), "+
		  "partition MONTH_PART_201803 values less than ('201804'), "+
		  "partition MONTH_PART_201804 values less than ('201805'), "+
		  "partition MONTH_PART_201805 values less than ('201806'), "+
		  "partition MONTH_PART_201806 values less than ('201807'), "+
		  "partition MONTH_PART_201807 values less than ('201808'), "+
		  "partition MONTH_PART_201808 values less than ('201809'), "+
		  "partition MONTH_PART_201809 values less than ('201810'), "+
		  "partition MONTH_PART_201810 values less than ('201811'), "+
		  "partition MONTH_PART_201811 values less than ('201812'), "+
		  "partition MONTH_PART_201812 values less than ('201901'), "+
		  "partition MONTH_PART_201901 values less than ('201902'), "+
		  "partition MONTH_PART_201902 values less than ('201903'), "+
		  "partition MONTH_PART_201903 values less than ('201904'), "+
		  "partition MONTH_PART_201904 values less than ('201905'), "+
		  "partition MONTH_PART_201905 values less than ('201906'), "+
		  "partition MONTH_PART_201906 values less than ('201907'), "+
		  "partition MONTH_PART_201907 values less than ('201908'), "+
		  "partition MONTH_PART_201908 values less than ('201909'), "+
		  "partition MONTH_PART_201909 values less than ('201910'), "+
		  "partition MONTH_PART_201910 values less than ('201911'), "+
		  "partition MONTH_PART_201911 values less than ('201912'), "+
		  "partition MONTH_PART_201912 values less than ('202001'), "+
		  "partition MONTH_PART_202001 values less than ('202002'), "+
		  "partition MONTH_PART_202002 values less than ('202003'), "+
		  "partition MONTH_PART_202003 values less than ('202004'), "+
		  "partition MONTH_PART_202004 values less than ('202005'), "+
		  "partition MONTH_PART_202005 values less than ('202006'), "+
		  "partition MONTH_PART_202006 values less than ('202007'), "+
		  "partition MONTH_PART_202007 values less than ('202008'), "+
		  "partition MONTH_PART_202008 values less than ('202009'), "+
		  "partition MONTH_PART_202009 values less than ('202010'), "+
		  "partition MONTH_PART_202010 values less than ('202011'), "+
		  "partition MONTH_PART_202011 values less than ('202012'), "+
		  "partition MONTH_PART_202012 values less than ('202101') )");
		runSQL("alter table CSL_VEHICLE_SHUNT add primary key (ID) using index tablespace GUAN");
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
