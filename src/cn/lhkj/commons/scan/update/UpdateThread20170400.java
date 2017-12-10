package cn.lhkj.commons.scan.update;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import cn.lhkj.commons.base.BaseDao;
import cn.lhkj.commons.util.StringUtil;


/**
 * 本次更新内容：
 */
public class UpdateThread20170400 {
	
	private BaseDao baseDao;
	
	public UpdateThread20170400(BaseDao baseDao){
		this.baseDao = baseDao;
	}
	
	public void execute(){
		/////by wx/////
		runSQL("comment on table CHECK_PERSON_PEERS_VEHICLE is '核录预警人员的同行车辆'");
		runSQL("comment on column CHECK_PERSON_PEERS_VEHICLE.car_num is '车牌号'");
		runSQL("comment on column CHECK_PERSON_PEERS_VEHICLE.plate_color is '车牌颜色'");
		runSQL("comment on column CHECK_PERSON_PEERS_VEHICLE.vehicle_color is '车辆颜色'");
		runSQL("comment on column CHECK_PERSON_PEERS_VEHICLE.vehicle_type is '车辆类型'");
		runSQL("comment on column CHECK_PERSON_PEERS_VEHICLE.check_person_id is '核查人员id'");
		/////////
		
		runSQL("comment on table CHECK_PERSON is '预警人员核录表'");
		runSQL("comment on column CHECK_PERSON.task_id is '任务ID'");
		runSQL("comment on column CHECK_PERSON.yj_type is '预警类别(动态人脸识别；手机号码；...)'");
		runSQL("comment on column CHECK_PERSON.check_time is '核录时间'");
		runSQL("comment on column CHECK_PERSON.tag is '人员标签'");
		runSQL("comment on column CHECK_PERSON.is_contrast is '是否预警对象(1是；0不是)'");
		runSQL("comment on column CHECK_PERSON.action is '采取措施（信息采集；滞留审查；立即抓捕）'");
		runSQL("comment on column CHECK_PERSON.idcard is '身份证号'");
		runSQL("comment on column CHECK_PERSON.phonenum is '手机号'");
		runSQL("comment on column CHECK_PERSON.match is '核验是否一致（1:核验一致；0:核验不一致)'");
		runSQL("comment on column CHECK_PERSON.is_dubious is '手机是否可疑（1:可疑；0:无可疑)'");
		runSQL("comment on column CHECK_PERSON.finds is '可疑描述，如可疑URL、可疑软件、暴恐音视频等等'");
		runSQL("comment on column CHECK_PERSON.is_vacation is '是否请假（1:已请假；0:未请假）'");
		runSQL("comment on column CHECK_PERSON.reason is '前往内地原因'");
		runSQL("comment on column CHECK_PERSON.backtime is '返回时间'");
		runSQL("comment on column CHECK_PERSON.is_leave is '近一年是否离开本地活动（1:是；0:否）'");
		runSQL("comment on column CHECK_PERSON.insert_time is '数据插入时间'");
		runSQL("comment on column CHECK_PERSON.area_code is '行政区划'");
		runSQL("comment on column CHECK_PERSON.station_id is '站点编号'");
		runSQL("comment on column CHECK_PERSON.station_name is '站点名称'");
		runSQL("comment on column CHECK_PERSON.remark is '系统名称'");
		runSQL("comment on column CHECK_PERSON.is_upload is '是否上传'");
		runSQL("comment on column CHECK_PERSON.hcjl is '核查结论：1：不能排除嫌疑继续布控，2：排除嫌疑建议撤控'");
		runSQL("comment on column CHECK_PERSON.direction is '人员去向'");
		runSQL("comment on column CHECK_PERSON.direction_reason is '目的地来由'");
		runSQL("comment on column CHECK_PERSON.copnum is '管辖片区民警联系方式'");
		
		runSQL("comment on table CHECK_PERSON_PEERS is '核录预警人员的同行人员'");
		runSQL("comment on column CHECK_PERSON_PEERS.idcard is '身份证号'");
		runSQL("comment on column CHECK_PERSON_PEERS.phonenum is '手机号'");
		
		runSQL("comment on table CHECK_VEHICLE is '预警车辆核录表'");
		runSQL("comment on column CHECK_VEHICLE.yj_type is '预警描述：二手车；布控对象车辆'");
		runSQL("comment on column CHECK_VEHICLE.task_id is '任务ID'");
		runSQL("comment on column CHECK_VEHICLE.car_num is '车牌号'");
		runSQL("comment on column CHECK_VEHICLE.forbids is '违禁物品'");
		runSQL("comment on column CHECK_VEHICLE.relations is '与车主关系：本人；亲属或社会关系；无关'");
		runSQL("comment on column CHECK_VEHICLE.explains is '驾驶员非车主：借亲属、朋友车辆	；公务车辆；车辆未过户；其它_____'");
		runSQL("comment on column CHECK_VEHICLE.check_time is '核查时间'");
		runSQL("comment on column CHECK_VEHICLE.insert_time is '数据插入时间'");
		runSQL("comment on column CHECK_VEHICLE.area_code is '行政区划'");
		runSQL("comment on column CHECK_VEHICLE.station_id is '站点编号'");
		runSQL("comment on column CHECK_VEHICLE.station_name is '站点名称'");
		runSQL("comment on column CHECK_VEHICLE.remark is '系统名称'");
		runSQL("comment on column CHECK_VEHICLE.is_upload is '是否上传'");
		runSQL("comment on column CHECK_VEHICLE.platecolor is '车牌颜色'");
		runSQL("comment on column CHECK_VEHICLE.vehiclecolor is '车身颜色'");
		runSQL("comment on column CHECK_VEHICLE.vehicletype is '车辆类型'");
		runSQL("comment on column CHECK_VEHICLE.direction is '车辆去向'");
		runSQL("comment on column CHECK_VEHICLE.destination is '目的地'");
		runSQL("comment on column CHECK_VEHICLE.residence_time is '滞留时间'");
		runSQL("comment on column CHECK_VEHICLE.reason is '办事是由'");
		runSQL("comment on column CHECK_VEHICLE.enterer is '核录人'");
		
		runSQL("comment on table CHECK_VEHICLE_PASSENGER is '核录车辆中司机和乘客的核录信息'");
		runSQL("comment on column CHECK_VEHICLE_PASSENGER.check_vehicle_id is '车辆ID'");
		runSQL("comment on column CHECK_VEHICLE_PASSENGER.name is '姓名'");
		runSQL("comment on column CHECK_VEHICLE_PASSENGER.idcard is '身份证号'");
		runSQL("comment on column CHECK_VEHICLE_PASSENGER.phonenum is '手机号'");
		runSQL("comment on column CHECK_VEHICLE_PASSENGER.match is '核验是否一致（1:核验一致；0:核验不一致)'");
		runSQL("comment on column CHECK_VEHICLE_PASSENGER.is_dubious is '手机是否可疑'");
		runSQL("comment on column CHECK_VEHICLE_PASSENGER.finds is '可疑描述(VPN；小众；可疑URL等)'");
		runSQL("comment on column CHECK_VEHICLE_PASSENGER.is_driver is '1代表司机，0代表乘客'");
		runSQL("comment on column CHECK_VEHICLE_PASSENGER.insert_time is '数据入库时间'");
		
		runSQL("comment on table CSL_CONTRAST_PERSON is '品恩数据门采集的人员数据'");
		runSQL("comment on column CSL_CONTRAST_PERSON.id is '任务ID'");
		runSQL("comment on column CSL_CONTRAST_PERSON.names is '姓名'");
		runSQL("comment on column CSL_CONTRAST_PERSON.nation is '民族'");
		runSQL("comment on column CSL_CONTRAST_PERSON.gender is '性别'");
		runSQL("comment on column CSL_CONTRAST_PERSON.birth is '出生年月'");
		runSQL("comment on column CSL_CONTRAST_PERSON.idcard is '身份证号'");
		runSQL("comment on column CSL_CONTRAST_PERSON.capture_time is '采集时间'");
		runSQL("comment on column CSL_CONTRAST_PERSON.address is '身份证地址信息'");
		runSQL("comment on column CSL_CONTRAST_PERSON.tag is '标签'");
		runSQL("comment on column CSL_CONTRAST_PERSON.action is '处置手段'");
		runSQL("comment on column CSL_CONTRAST_PERSON.bk_area is '布控范围'");
		runSQL("comment on column CSL_CONTRAST_PERSON.yj_area is '预警范围'");
		runSQL("comment on column CSL_CONTRAST_PERSON.source is '数据来源：大数据平台；本地产生'");
		runSQL("comment on column CSL_CONTRAST_PERSON.idcard_photo_url is '身份证照片'");
		runSQL("comment on column CSL_CONTRAST_PERSON.gather_photo_url is '采集照片'");
		runSQL("comment on column CSL_CONTRAST_PERSON.location is '设备所在位置'");
		runSQL("comment on column CSL_CONTRAST_PERSON.note is '备注'");
		runSQL("comment on column CSL_CONTRAST_PERSON.area_code is '区域编码'");
		runSQL("comment on column CSL_CONTRAST_PERSON.check_point_id is '检查站编号'");
		runSQL("comment on column CSL_CONTRAST_PERSON.insert_time is '数据入库时间'");
		runSQL("comment on column CSL_CONTRAST_PERSON.is_checked is '是否已核查'");
		runSQL("comment on column CSL_CONTRAST_PERSON.equipment_id is '设备编号'");
		
		runSQL("comment on table CSL_CONTRAST_VEHICLE is '经过闸机的车辆数据'");
		runSQL("comment on column CSL_CONTRAST_VEHICLE.month_id is '按月分区字段'");
		runSQL("comment on column CSL_CONTRAST_VEHICLE.id is '任务ID'");
		runSQL("comment on column CSL_CONTRAST_VEHICLE.car_num is '车牌号'");
		runSQL("comment on column CSL_CONTRAST_VEHICLE.passdate is '采集时间'");
		runSQL("comment on column CSL_CONTRAST_VEHICLE.plate_color is '车牌颜色'");
		runSQL("comment on column CSL_CONTRAST_VEHICLE.vehicle_type is '车辆类型'");
		runSQL("comment on column CSL_CONTRAST_VEHICLE.idcard is '身份证号'");
		runSQL("comment on column CSL_CONTRAST_VEHICLE.names is '所有人'");
		runSQL("comment on column CSL_CONTRAST_VEHICLE.tag is '人员标签'");
		runSQL("comment on column CSL_CONTRAST_VEHICLE.describe is '车辆状态'");
		runSQL("comment on column CSL_CONTRAST_VEHICLE.action is '处置手段'");
		runSQL("comment on column CSL_CONTRAST_VEHICLE.person_photo is '人员身份证照片base64'");
		runSQL("comment on column CSL_CONTRAST_VEHICLE.bk_area is '布控范围'");
		runSQL("comment on column CSL_CONTRAST_VEHICLE.yj_area is '预警范围'");
		runSQL("comment on column CSL_CONTRAST_VEHICLE.source is '数据来源：大数据平台；本站产生'");
		runSQL("comment on column CSL_CONTRAST_VEHICLE.vehicle_color is '车身颜色'");
		runSQL("comment on column CSL_CONTRAST_VEHICLE.plate_type is '车牌类型'");
		runSQL("comment on column CSL_CONTRAST_VEHICLE.gather_photo_url is '采集照片tz网'");
		runSQL("comment on column CSL_CONTRAST_VEHICLE.location is '设备所在位置'");
		runSQL("comment on column CSL_CONTRAST_VEHICLE.label is '车标签'");
		runSQL("comment on column CSL_CONTRAST_VEHICLE.area_code is '区域编码 '");
		runSQL("comment on column CSL_CONTRAST_VEHICLE.note is '备注 '");
		runSQL("comment on column CSL_CONTRAST_VEHICLE.address is '户籍地址'");
		runSQL("comment on column CSL_CONTRAST_VEHICLE.owner_photo_url is '车主照片'");
		runSQL("comment on column CSL_CONTRAST_VEHICLE.problem_type is '问题车辆类型：1 二手车，2 布控对象车辆'");
		runSQL("comment on column CSL_CONTRAST_VEHICLE.check_point_id is '接收任务的检查站'");
		runSQL("comment on column CSL_CONTRAST_VEHICLE.insert_time is '数据入库时间'");
		runSQL("comment on column CSL_CONTRAST_VEHICLE.is_checked is '是否核查'");
		runSQL("comment on column CSL_CONTRAST_VEHICLE.equipment_id is '设备编号'");
		
		runSQL("comment on table CSL_DICT is '数据字典'");
		runSQL("comment on column CSL_DICT.id is '数据字典编号'");
		runSQL("comment on column CSL_DICT.names is '数据字典名称'");
		
		runSQL("comment on table CSL_DICT_ITEM is '数据字典字典项'");
		runSQL("comment on column CSL_DICT_ITEM.dict_id is '数据字典编号'");
		runSQL("comment on column CSL_DICT_ITEM.options is '选项名称'");
		runSQL("comment on column CSL_DICT_ITEM.ranking is '选项顺序'");
		runSQL("comment on column CSL_DICT_ITEM.codes is '字典代码'");
		
		runSQL("comment on column CSL_EQUIPMENT.id is '设备编号'");
		runSQL("comment on column CSL_EQUIPMENT.names is '设备名称'");
		runSQL("comment on column CSL_EQUIPMENT.type is '设备类型-对应数据字典的设备类型'");
		runSQL("comment on column CSL_EQUIPMENT.laneid is '车道ID'");
		runSQL("comment on column CSL_EQUIPMENT.hallid is '安检大厅ID'");
		runSQL("comment on column CSL_EQUIPMENT.ip is '设备IP地址'");
		runSQL("comment on column CSL_EQUIPMENT.url is '设备服务地址'");
		runSQL("comment on column CSL_EQUIPMENT.remark is '备注'");
		runSQL("comment on column CSL_EQUIPMENT.showway is '分流文字'");
		
		runSQL("comment on column CSL_EXPLAIN.tablename is '表名'");
		runSQL("comment on column CSL_EXPLAIN.indexs is '顺序'");
		runSQL("comment on column CSL_EXPLAIN.columns is '字段名'");
		runSQL("comment on column CSL_EXPLAIN.explain is '字段说明'");
		runSQL("comment on column CSL_EXPLAIN.remark is '备注'");
		
		runSQL("comment on column CSL_HALL.id is 'ID'");
		runSQL("comment on column CSL_HALL.hallname is '大厅编号'");
		
		runSQL("comment on column CSL_LANE.id is 'ID'");
		runSQL("comment on column CSL_LANE.lanename is '车道名称'");
		
		runSQL("comment on table CSL_MENU is '菜单信息'");
		runSQL("comment on column CSL_MENU.menu_name is '菜单名称'");
		runSQL("comment on column CSL_MENU.ranking is '菜单顺序'");
		runSQL("comment on column CSL_MENU.href is '菜单页面地址'");
		runSQL("comment on column CSL_MENU.icon is '菜单图标'");
		
		runSQL("comment on table CSL_ORG is '组织机构'");
		runSQL("comment on column CSL_ORG.pid is '上级机构ID'");
		runSQL("comment on column CSL_ORG.prio is '顺序'");
		runSQL("comment on column CSL_ORG.is_root is '是否是根节点'");
		runSQL("comment on column CSL_ORG.names is '机构名称'");
		runSQL("comment on column CSL_ORG.is_leaf is '是否是叶子节点'");
		runSQL("comment on column CSL_ORG.codes is '机构编号'");
		runSQL("comment on column CSL_ORG.detail_name is '详细名称'");
		runSQL("comment on column CSL_ORG.x is '经度'");
		runSQL("comment on column CSL_ORG.y is '纬度'");
		
		runSQL("comment on table CSL_PASSENGER is '乘客信息'");
		runSQL("comment on column CSL_PASSENGER.vehicle_id is '车辆ID'");
		runSQL("comment on column CSL_PASSENGER.user_name is '乘客姓名'");
		runSQL("comment on column CSL_PASSENGER.sex is '性别'");
		runSQL("comment on column CSL_PASSENGER.minzu is '族别'");
		runSQL("comment on column CSL_PASSENGER.card_num is '身份证号'");
		runSQL("comment on column CSL_PASSENGER.birth_date is '出生年月'");
		runSQL("comment on column CSL_PASSENGER.address is '地址'");
		runSQL("comment on column CSL_PASSENGER.qianfa is '签发机关'");
		runSQL("comment on column CSL_PASSENGER.youxiaoqi is '有效期'");
		runSQL("comment on column CSL_PASSENGER.card_img is '身份证照片'");
		runSQL("comment on column CSL_PASSENGER.slevel is '安全级别'");
		runSQL("comment on column CSL_PASSENGER.is_driver is '1代表司机，0代表乘客'");
		runSQL("comment on column CSL_PASSENGER.result is '比对结果：1成功，0失败'");
		runSQL("comment on column CSL_PASSENGER.passdate is '数据采集时间'");
		runSQL("comment on column CSL_PASSENGER.insert_time is '数据入库时间'");
		
		runSQL("comment on table CSL_PERSON is '品恩数据门采集的人员数据'");
		runSQL("comment on column CSL_PERSON.id is 'ID'");
		runSQL("comment on column CSL_PERSON.names is '姓名'");
		runSQL("comment on column CSL_PERSON.nation is '民族'");
		runSQL("comment on column CSL_PERSON.gender is '性别'");
		runSQL("comment on column CSL_PERSON.birth is '出生年月'");
		runSQL("comment on column CSL_PERSON.idcard is '身份证号'");
		runSQL("comment on column CSL_PERSON.equipment_id is '采集设备编号'");
		runSQL("comment on column CSL_PERSON.capture_time is '采集时间'");
		runSQL("comment on column CSL_PERSON.address is '身份证地址信息'");
		runSQL("comment on column CSL_PERSON.start_date is '身份证有效期开始'");
		runSQL("comment on column CSL_PERSON.end_date is '身份证有效期结束'");
		runSQL("comment on column CSL_PERSON.is_a_person is '是否人证合一'");
		runSQL("comment on column CSL_PERSON.img_url is '抓拍人照片路径'");
		runSQL("comment on column CSL_PERSON.local_img_url is '下载到本地的照片路径'");
		runSQL("comment on column CSL_PERSON.error_count is '图片下载错误次数'");
		runSQL("comment on column CSL_PERSON.location is '位置'");
		
		runSQL("comment on column CSL_PERSON_WARNING.id is '通行人员的惟一标识号'");
		runSQL("comment on column CSL_PERSON_WARNING.names is '姓名'");
		runSQL("comment on column CSL_PERSON_WARNING.tag is '人员标签 多个以竖线隔开'");
		runSQL("comment on column CSL_PERSON_WARNING.gender is '性别'");
		runSQL("comment on column CSL_PERSON_WARNING.idcard is '身份证'");
		runSQL("comment on column CSL_PERSON_WARNING.message is '信息描述'");
		runSQL("comment on column CSL_PERSON_WARNING.reserved1 is '三汇黑名单返回值'");
		runSQL("comment on column CSL_PERSON_WARNING.reserved2 is '三汇黑名单返回值'");
		runSQL("comment on column CSL_PERSON_WARNING.reserved3 is '三汇黑名单返回值'");
		runSQL("comment on column CSL_PERSON_WARNING.reserved4 is '三汇黑名单返回值'");
		
		runSQL("comment on table CSL_ROLE is '角色信息'");
		runSQL("comment on column CSL_ROLE.role_name is '角色名称'");
		runSQL("comment on column CSL_ROLE.is_base is '是否基础角色（所有人员享有此角色的菜单权限）'");
		
		runSQL("comment on table CSL_ROLE_MENU is '角色菜单信息'");
		runSQL("comment on column CSL_ROLE_MENU.role_id is '角色ID'");
		runSQL("comment on column CSL_ROLE_MENU.menu_id is '菜单ID'");
		runSQL("comment on column CSL_ROLE_MENU.data_auth is '菜单权限 1个人，2部门，3所有，4其他'");
		
		runSQL("comment on table CSL_ROLE_USER is '角色用户信息'");
		runSQL("comment on column CSL_ROLE_USER.role_id is '角色ID'");
		runSQL("comment on column CSL_ROLE_USER.user_id is '用户ID'");
		
		runSQL("comment on table CSL_TRANSFER is '车证合一接口调用记录'");
		runSQL("comment on column CSL_TRANSFER.ver is '版本号'");
		runSQL("comment on column CSL_TRANSFER.tran_no is '通讯号，每次通讯的唯一标识'");
		runSQL("comment on column CSL_TRANSFER.tran_type is '代表不同的通讯请求'");
		runSQL("comment on column CSL_TRANSFER.tran_result is '200:成功；400：失败'");
		runSQL("comment on column CSL_TRANSFER.tran_msg is '信息描述：正常 。。。'");
		runSQL("comment on column CSL_TRANSFER.send_time is '回复时间'");
		runSQL("comment on column CSL_TRANSFER.key is 'MD5签名，不同厂家不同'");
		runSQL("comment on column CSL_TRANSFER.data is '传输的数据'");
		
		runSQL("comment on table CSL_USER is '用户信息'");
		runSQL("comment on column CSL_USER.account is '登录帐号'");
		runSQL("comment on column CSL_USER.pwd is '登录密码'");
		runSQL("comment on column CSL_USER.names is '用户名'");
		runSQL("comment on column CSL_USER.gender is '用户性别'");
		runSQL("comment on column CSL_USER.serials is '显示顺序'");
		runSQL("comment on column CSL_USER.email is '用户邮箱'");
		runSQL("comment on column CSL_USER.status is '用户状态：1启用，0锁定'");
		runSQL("comment on column CSL_USER.create_time is '用户创建时间'");
		runSQL("comment on column CSL_USER.update_time is '最后一次修改时间'");
		runSQL("comment on column CSL_USER.login_time is '最后一次登录时间'");
		runSQL("comment on column CSL_USER.updater is '修改人'");
		runSQL("comment on column CSL_USER.org_id is '组织机构ID'");
		
		runSQL("comment on table CSL_UVSS is '车底扫描'");
		runSQL("comment on column CSL_UVSS.month_id is '分区字段'");
		runSQL("comment on column CSL_UVSS.uvss_image is '车底图片路径'");
		runSQL("comment on column CSL_UVSS.plate_number is '车牌号路径'");
		runSQL("comment on column CSL_UVSS.check_date_time is '扫描时间'");
		runSQL("comment on column CSL_UVSS.ip_address is 'ip地址'");
		
		runSQL("comment on table CSL_UVSS_PATH is '车底扫描'");
		runSQL("comment on column CSL_UVSS_PATH.month_id is '分区字段'");
		runSQL("comment on column CSL_UVSS_PATH.uvss_image is '车底图片路径'");
		runSQL("comment on column CSL_UVSS_PATH.plate_number is '车牌号路径'");
		runSQL("comment on column CSL_UVSS_PATH.check_date_time is '扫描时间'");
		runSQL("comment on column CSL_UVSS_PATH.ip_address is 'IP地址'");
		
		runSQL("comment on table CSL_VEHICLE is '所有通过闸机和前置卡口的车辆'");
		runSQL("comment on column CSL_VEHICLE.month_id is '按月分区字段'");
		runSQL("comment on column CSL_VEHICLE.id is 'ID'");
		runSQL("comment on column CSL_VEHICLE.car_num is '车牌号'");
		runSQL("comment on column CSL_VEHICLE.car_img is '车辆照片路径'");
		runSQL("comment on column CSL_VEHICLE.num_img is '车牌号照片路径'");
		runSQL("comment on column CSL_VEHICLE.equipment_id is '设备编号'");
		runSQL("comment on column CSL_VEHICLE.passdate is '采集时间'");
		runSQL("comment on column CSL_VEHICLE.insert_time is '数据入库时间'");
		runSQL("comment on column CSL_VEHICLE.plate_color_id is '车牌颜色编码'");
		runSQL("comment on column CSL_VEHICLE.plate_color is '车牌颜色'");
		runSQL("comment on column CSL_VEHICLE.relust is '比对结果'");
		runSQL("comment on column CSL_VEHICLE.classify is '分类：VehicleShunt表示前置卡口VehicleBrake表示车证合一的闸机'");
		runSQL("comment on column CSL_VEHICLE.vehicle_type is '车辆类型'");
		runSQL("comment on column CSL_VEHICLE.user_name is '司机姓名'");
		runSQL("comment on column CSL_VEHICLE.sex is '性别'");
		runSQL("comment on column CSL_VEHICLE.minzu is '民族'");
		runSQL("comment on column CSL_VEHICLE.card_num is '身份证号'");
		runSQL("comment on column CSL_VEHICLE.birth_date is '出生年月'");
		runSQL("comment on column CSL_VEHICLE.address is '地址'");
		runSQL("comment on column CSL_VEHICLE.qianfa is '签发机关'");
		runSQL("comment on column CSL_VEHICLE.youxiaoqi is '有效期'");
		runSQL("comment on column CSL_VEHICLE.card_img is '身份证照片'");
		
		runSQL("comment on table CSL_VEHICLE_BRAKE is '经过闸机的车辆数据'");
		runSQL("comment on column CSL_VEHICLE_BRAKE.month_id is '按月分区字段'");
		runSQL("comment on column CSL_VEHICLE_BRAKE.id is 'ID'");
		runSQL("comment on column CSL_VEHICLE_BRAKE.car_num is '车牌号'");
		runSQL("comment on column CSL_VEHICLE_BRAKE.car_img is '车辆照片路径'");
		runSQL("comment on column CSL_VEHICLE_BRAKE.num_img is '车牌号照片路径'");
		runSQL("comment on column CSL_VEHICLE_BRAKE.equipment_id is '设备编号'");
		runSQL("comment on column CSL_VEHICLE_BRAKE.passdate is '采集时间'");
		runSQL("comment on column CSL_VEHICLE_BRAKE.insert_time is '数据入库时间'");
		runSQL("comment on column CSL_VEHICLE_BRAKE.plate_color_id is '车牌颜色编码'");
		runSQL("comment on column CSL_VEHICLE_BRAKE.plate_color is '车牌颜色'");
		runSQL("comment on column CSL_VEHICLE_BRAKE.states is '-1表示未比对；0表示比对放行；1表示比对需进一步检查'");
		runSQL("comment on column CSL_VEHICLE_BRAKE.remark is '备注'");
		runSQL("comment on column CSL_VEHICLE_BRAKE.relust is '比对结果'");
		runSQL("comment on column CSL_VEHICLE_BRAKE.vehicle_type is '车辆类型'");
		runSQL("comment on column CSL_VEHICLE_BRAKE.local_car_img is '下载到本地的车辆照片路径'");
		runSQL("comment on column CSL_VEHICLE_BRAKE.local_num_img is '下载到本地车牌照片路径'");
		runSQL("comment on column CSL_VEHICLE_BRAKE.error_count is '下载错误次数'");
		runSQL("comment on column CSL_VEHICLE_BRAKE.tran_type is '通讯序号：9001或者5001或者6001'");
		runSQL("comment on column CSL_VEHICLE_BRAKE.user_name is '司机姓名'");
		runSQL("comment on column CSL_VEHICLE_BRAKE.sex is '性别'");
		runSQL("comment on column CSL_VEHICLE_BRAKE.minzu is '民族'");
		runSQL("comment on column CSL_VEHICLE_BRAKE.card_num is '身份证号'");
		runSQL("comment on column CSL_VEHICLE_BRAKE.birth_date is '出生年月'");
		runSQL("comment on column CSL_VEHICLE_BRAKE.address is '地址'");
		runSQL("comment on column CSL_VEHICLE_BRAKE.qianfa is '签发机关'");
		runSQL("comment on column CSL_VEHICLE_BRAKE.youxiaoqi is '有效期'");
		runSQL("comment on column CSL_VEHICLE_BRAKE.card_img is '身份证照片'");
		runSQL("comment on column CSL_VEHICLE_BRAKE.local_card_img is '本地身份证照片'");
		
		runSQL("comment on table CSL_VEHICLE_SHUNT is '经过前置卡口的车辆数据'");
		runSQL("comment on column CSL_VEHICLE_SHUNT.eqipment_id is '设备ID'");
		runSQL("comment on column CSL_VEHICLE_SHUNT.plate is '车牌号码'");
		runSQL("comment on column CSL_VEHICLE_SHUNT.vehicle_type is '车辆类型'");
		runSQL("comment on column CSL_VEHICLE_SHUNT.vehicle_image is '车辆图片绝对路径'");
		runSQL("comment on column CSL_VEHICLE_SHUNT.plate_image is '车牌图片绝对路径'");
		runSQL("comment on column CSL_VEHICLE_SHUNT.passdate is '采集时间'");
		runSQL("comment on column CSL_VEHICLE_SHUNT.states is '-1表示未比对；0表示比对放行；1表示比对需发送至分流屏幕'");
		runSQL("comment on column CSL_VEHICLE_SHUNT.plate_color_id is '车牌颜色编码'");
		runSQL("comment on column CSL_VEHICLE_SHUNT.plate_color is '车牌颜色'");
		runSQL("comment on column CSL_VEHICLE_SHUNT.remark is '备注'");
		runSQL("comment on column CSL_VEHICLE_SHUNT.insert_time is '数据入库的时间'");
		runSQL("comment on column CSL_VEHICLE_SHUNT.relust is '处理结果'");
		runSQL("comment on column CSL_VEHICLE_SHUNT.month_id is '按月分区'");
		runSQL("comment on column CSL_VEHICLE_SHUNT.local_vehicle_image is '下载到本地车辆图片绝对路径'");
		runSQL("comment on column CSL_VEHICLE_SHUNT.local_plate_image is '下载到本地车牌图片绝对路径'");
		runSQL("comment on column CSL_VEHICLE_SHUNT.error_count is '下载错误次数'");
		try{
			List<Map<String,String>> l = baseDao.findBySQL("select t.file_name as FILENAME from dba_data_files t where t.tablespace_name='GUAN' and t.autoextensible='NO'");
			if(!StringUtil.isNull(l)){
				for(Map<String,String> m : l){
					baseDao.execute("alter database datafile '"+m.get("FILENAME")+"' autoextend on");
				}
			}
		}catch(Exception e){}
		
		
		///创建日期索引
		runSQL("create index CSL_PERSON_PASSDATE on CSL_PERSON (CAPTURE_TIME DESC)");
		runSQL("create index CSL_VEHICLE_PASSDATE on CSL_VEHICLE (PASSDATE DESC)");
		runSQL("create index CSL_VEHICLE_BRAKE_PASSDATE on CSL_VEHICLE_BRAKE (PASSDATE DESC)");
		runSQL("create index CSL_PASSENGER_PASSDATE on CSL_PASSENGER (PASSDATE DESC)");
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
