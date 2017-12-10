package cn.lhkj.project.person.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import sun.misc.BASE64Decoder;

import cn.lhkj.commons.base.BaseDao;
import cn.lhkj.commons.base.BaseDataCode;
import cn.lhkj.commons.base.BaseService;
import cn.lhkj.commons.defined.Transactional;
import cn.lhkj.commons.entity.PageInfo;
import cn.lhkj.commons.entity.SessionBean;
import cn.lhkj.commons.tools.FileOperator;
import cn.lhkj.commons.tools.HttpPostSANHUI;
import cn.lhkj.commons.tools.IdCardGenerator;
import cn.lhkj.commons.tools.UUIDFactory;
import cn.lhkj.commons.util.CalendarUtil;
import cn.lhkj.commons.util.StringUtil;
import cn.lhkj.project.contrast.entity.ContrastPerson;
import cn.lhkj.project.equipment.entity.Equipment;
import cn.lhkj.project.hall.entity.Hall;
import cn.lhkj.project.person.entity.CheckPersonContraband;
import cn.lhkj.project.person.entity.Person;
import cn.lhkj.project.system.entity.Orgzon;

@Service("personService")
@Scope("prototype")
public class PersonServiceImpl extends BaseService implements PersonService {
	private final static Logger logger = Logger.getLogger(PersonServiceImpl.class);
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Person> listPerson(String records,String stationId) throws Exception {
		stationId = StringUtil.trim(stationId);
		if(stationId.length() > 13) stationId = stationId.substring(0, 13);
		String monthId = CalendarUtil.getCurrentMonth();
		String hql = "from Person where monthId='"+monthId+"' and (instr(substr(equipmentId,0,13), '"+stationId+"')>0) order by captureTime desc";
		List<Person> relust = (List<Person>)baseDao.findByHQL(hql, 0, Integer.parseInt(records));
		if(StringUtil.isNull(relust)){
			hql = "from Person where 1=1 and (instr(substr(equipmentId,0,13), '"+stationId+"')>0) order by monthId desc, captureTime desc";
			relust = (List<Person>)baseDao.findByHQL(hql, 0, Integer.parseInt(records));
		}
		if(!StringUtil.isNull(relust)){
			for(Person t : relust){
				Equipment em = (Equipment)baseDao.getEntity(Equipment.class, t.getEquipmentId());
				if(em != null) 
					t.setHall((Hall)baseDao.getEntity(Hall.class, em.getHallId()));
			}
		}
		return relust;
	}
	
	@Override
	public Person getPerson(String id) throws Exception {
		return (Person)baseDao.getEntity(Person.class, id);
	}
	
	public String getCountPerson(Map<String, Object> requestParams) throws Exception {
		String monthId = (String)requestParams.get("monthId");
		if(StringUtil.isNull(monthId)){
			monthId = CalendarUtil.getCurrentMonth();
		}
		String hql = "select count(1) as NUM from CSL_PERSON where MONTH_ID='"+monthId+"' ";
		String parmsMore = "";
		
		SessionBean se = (SessionBean)requestParams.get("SESSION_BEAN");
		String stationId = StringUtil.trim(se.getStationId());
		if(stationId.length() > 13) stationId = stationId.substring(0, 13);
		
		if(se != null){//站点数据权限
			parmsMore += " and (instr(substr(EQUIPMENT_ID,0,13), '"+stationId+"')>0)";//@@
		}

		if(requestParams.get("contrabandName") != null && !"".equals(requestParams.get("contrabandName"))){
			parmsMore += " and (instr(IS_CHECK, '"+requestParams.get("contrabandName")+"')>0) ";
		}
		if(requestParams.get("startdate") != null && !"".equals(requestParams.get("startdate"))){
			parmsMore += " and to_char(CAPTURE_TIME,'yyyy-mm-dd hh24:mi:ss') >='"+requestParams.get("startdate")+"'";
		}
		if(requestParams.get("enddate") != null && !"".equals(requestParams.get("enddate"))){
			parmsMore += " and to_char(CAPTURE_TIME,'yyyy-mm-dd hh24:mi:ss') <='"+requestParams.get("enddate")+"'";
		}
		if(requestParams.get("inputValue") != null ){//根据条件模糊查询
			String searchParam = requestParams.get("inputValue").toString();
			if(searchParam.length() > 0){
				parmsMore += " and (instr(NAMES, '"+searchParam+"')>0 " +
				"or instr(IDCARD,'"+searchParam+"')>0 "+
				"or instr(LOCATION,'"+searchParam+"')>0) ";
			}
		}
		hql += parmsMore + " order by CAPTURE_TIME desc";
		List<Map<String,String>> result = baseDao.findBySQL(hql);
		String num = result.get(0).get("NUM");
		return num;
		
	}	
	
	@SuppressWarnings("unchecked")
	public List<Person> getPriorDataPerson(Map<String, Object> requestParams) throws Exception {
		String monthId = (String)requestParams.get("monthId");
		if(StringUtil.isNull(monthId)){
			monthId = CalendarUtil.getCurrentMonth();
		}
		String hql = "from Person where monthId='"+monthId+"' ";
		String parmsMore = "";
		
		SessionBean se = (SessionBean)requestParams.get("SESSION_BEAN");
		String stationId = StringUtil.trim(se.getStationId());
		if(stationId.length() > 13) stationId = stationId.substring(0, 13);
		
		if(se != null){//站点数据权限
			parmsMore += " and (instr(substr(equipmentId,0,13), '"+stationId+"')>0)";//@@
		}

		if(requestParams.get("contrabandName") != null && !"".equals(requestParams.get("contrabandName"))){
			parmsMore += " and (instr(isCheck, '"+requestParams.get("contrabandName")+"')>0) ";
		}
		if(requestParams.get("startdate") != null && !"".equals(requestParams.get("startdate"))){
			parmsMore += " and to_char(captureTime,'yyyy-mm-dd hh24:mi:ss') >='"+requestParams.get("startdate")+"'";
		}
		if(requestParams.get("enddate") != null && !"".equals(requestParams.get("enddate"))){
			parmsMore += " and to_char(captureTime,'yyyy-mm-dd hh24:mi:ss') <='"+requestParams.get("enddate")+"'";
		}
		if(requestParams.get("inputValue") != null ){//根据条件模糊查询
			String searchParam = requestParams.get("inputValue").toString();
			if(searchParam.length() > 0){
				parmsMore += " and (instr(names, '"+searchParam+"')>0 " +
				"or instr(idcard,'"+searchParam+"')>0 "+
				"or instr(location,'"+searchParam+"')>0) ";
			}
		}
		hql += parmsMore + " order by captureTime desc";

		List<Person> result = (List<Person>)baseDao.findByHQL(hql);
		return result;
		
	}	
	
	public PageInfo getPriorDataPersonePage(Map<String, Object> requestParams,PageInfo pageInfo) throws Exception {
		String monthId = (String)requestParams.get("monthId");
		if(StringUtil.isNull(monthId)){
			monthId = CalendarUtil.getCurrentMonth();
		}
		String hql = "from Person where monthId='"+monthId+"' ";
		String parmsMore = "";
		
		SessionBean se = (SessionBean)requestParams.get("SESSION_BEAN");
		String stationId = StringUtil.trim(se.getStationId());
		if(stationId.length() > 13) stationId = stationId.substring(0, 13);
		
		
		if(se != null){//站点数据权限
			parmsMore += " and (instr(substr(equipmentId,0,13), '"+stationId+"')>0)";//@@
		}
		
		if(requestParams.get("contrabandName") != null && !"".equals(requestParams.get("contrabandName"))){
			parmsMore += " and (instr(isCheck, '"+requestParams.get("contrabandName")+"')>0) ";
		}
		
		if(requestParams.get("startdate") != null && !"".equals(requestParams.get("startdate"))){
			parmsMore += " and to_char(captureTime,'yyyy-mm-dd hh24:mi:ss') >='"+requestParams.get("startdate")+"'";
		}
		if(requestParams.get("enddate") != null && !"".equals(requestParams.get("enddate"))){
			parmsMore += " and to_char(captureTime,'yyyy-mm-dd hh24:mi:ss') <='"+requestParams.get("enddate")+"'";
		}
		if(requestParams.get("inputValue") != null ){//根据条件模糊查询
			String searchParam = requestParams.get("inputValue").toString();
			if(searchParam.length() > 0){
				parmsMore += " and (instr(names, '"+searchParam+"')>0 " +
				"or instr(idcard,'"+searchParam+"')>0 "+
				"or instr(location,'"+searchParam+"')>0) ";
			}
		}
		hql += parmsMore + " order by captureTime desc";
		pageInfo = baseDao.findPageByHQL(pageInfo, hql);
		return pageInfo;
	}
	
	@Transactional
	@Override
	public void addPersonContraband(MultipartFile file,HttpServletRequest request) throws Exception {
		String name = request.getParameter("name");
        String personId = request.getParameter("personId");
        String remark = request.getParameter("remark");
        String time = request.getParameter("checkTime");
		String imgUrl = "";
		if(null != file && file.getSize()>0 && !file.isEmpty()){   //图片不为空上传并保存路径
			String root =  "\\\\"+BaseDataCode.config.getFileServerIp()+"\\cetc\\contraband\\";
			Date date = new Date();
			String path_url = root + CalendarUtil.format(date, "yyyyMM")+"\\"+CalendarUtil.format(date, "yyyyMMdd")+"\\";
			FileOperator.newFolder(path_url);
			String fileName = CalendarUtil.format(date, "yyyyMMddHHmmss")+".jpg";
			File newfile=new File(path_url,fileName);
			file.transferTo(newfile);
			imgUrl = "http://"+BaseDataCode.config.getFileServerIp()+":8889/contraband/"+
					 CalendarUtil.format(date, "yyyyMM")+"/"+CalendarUtil.format(date, "yyyyMMdd")+"/"
					 +fileName; 			
		}

        CheckPersonContraband checkPersonContraband = new CheckPersonContraband();
        if (!StringUtil.isNull(time)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
            java.util.Date checkTime = sdf.parse(time);
            checkPersonContraband.setCheckTime(checkTime);
		}
        
        if (!StringUtil.isNull(name)) {
        	checkPersonContraband.setName(name);
        }
        
        if (!StringUtil.isNull(personId)) {
        	checkPersonContraband.setPersonId(personId);
        }
        
        if (!StringUtil.isNull(remark)) {
        	checkPersonContraband.setRemark(remark);
        }
        
        if (!StringUtil.isNull(imgUrl)) {
        	checkPersonContraband.setContrabandPhotoUrl(imgUrl);
        }
		checkPersonContraband.setId(UUIDFactory.getUUIDStr());
		baseDao.save(checkPersonContraband);
		String sql = "update CSL_PERSON set IS_CHECK='"+name+"' where id='"+personId+"'";
		baseDao.execute(sql);
	}
	
	@Transactional
	@Override
	public Person addPerson(Person person,String station) throws Exception {
		String idcard = person.getIdcard();
		person.setGender(IdCardGenerator.gender(idcard));
		person.setId(UUIDFactory.getUUIDStr());
		person.setMonthId(CalendarUtil.getCurrentMonth());
		person.setCaptureTime(new Date());
		person.setEquipmentId(station);
		//person.setEquipmentId("6531010100001-SJM01");
		person.setLocation("登记窗口");
		baseDao.save(person);
		BaseDataCode.putPersonMap(person.getEquipmentId().substring(0, 13), person);
		return person;
	}
	
	@Transactional
	@Override
	public String comparePerson(Person person) throws Exception{
		comparison(person);
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public CheckPersonContraband getPersonContraband(String personId) throws Exception {
		String hql = "from CheckPersonContraband t where t.personId='"+personId+"'";
		List<CheckPersonContraband> list = (List<CheckPersonContraband>)baseDao.findByHQL(hql);
		if(!StringUtil.isNull(list)){
			return  list.get(0);
		}
		return null;
	}
	
	//保存人证信息  0:通行  1:预警
	@Transactional
	@Override
	public String gather(String infos,String ip) throws Exception{
		Person person = new Person(UUIDFactory.getUUIDStr());
		Date date = new Date();
		person.setCaptureTime(date);
		try {
			JSONObject jsonObject = JSONObject.fromObject(infos);
			String idcard = StringUtil.trim(jsonObject.getString("idcard"));
			if (StringUtil.isNull(idcard)) {
				return "身份证号为空";
			}
			String img = StringUtil.trim(jsonObject.getString("img_url"));
			String equipment_id = StringUtil.trim(jsonObject.getString("equipment_id"));
			Equipment equipment = BaseDataCode.equipmentMap.get(equipment_id);
			if (equipment != null) {
				person.setLocation(equipment.getNames());
			}
			person.setNames(StringUtil.trim(jsonObject.getString("names")));
			person.setNation(StringUtil.trim(jsonObject.getString("nation")));
			person.setGender(StringUtil.trim(jsonObject.getString("gender")));
			person.setIdcard(idcard);
			person.setEquipmentId(equipment_id);			
			person.setAddress(StringUtil.trim(jsonObject.getString("address")));
			person.setStartDate(StringUtil.trim(jsonObject.getString("start_date")));
			person.setEndDate(StringUtil.trim(jsonObject.getString("end_date")));
			person.setIsAPerson(StringUtil.trim(jsonObject.getString("is_a_person")));
			String fileName = CalendarUtil.format(date, "yyyyMMddHHmmss")+idcard+".jpg";
			//String root =  "\\\\192.168.57.128\\share\\cetc\\rzhj\\";
			String root =  "\\\\"+BaseDataCode.config.getFileServerIp()+"\\cetc\\rzhj\\";
			String path_url = root + CalendarUtil.format(date, "yyyyMM")+"\\"+CalendarUtil.format(date, "yyyyMMdd")+"\\";
			GenerateImage(img, path_url,fileName);
			
	        String img_url = "http://"+BaseDataCode.config.getFileServerIp()+":8889/rzhj/"+
	        		CalendarUtil.format(date, "yyyyMM")+"/"+
	        		CalendarUtil.format(date, "yyyyMMdd")+"/"+fileName;
			person.setImgUrl(img_url);
			baseDao.save(person);
			BaseDataCode.putPersonMap(person.getEquipmentId().substring(0, 13), person);
			return  comparison(person);//与大数据平台比对
		}catch (Exception e) {
			logger.error(e.getMessage());
			return e.getMessage();
		}
	}
    
    public static boolean GenerateImage(String imgStr, String imgFilePath,String fileName) {// 对字节数组字符串进行Base64解码并生成图片
		if (imgStr == null) return false; // 图像数据为空
		try {
			BASE64Decoder decoder = new BASE64Decoder();
			FileOperator.newFolder(imgFilePath);
			// Base64解码
			byte[] bytes = decoder.decodeBuffer(imgStr);
			for (int i = 0; i < bytes.length; ++i) {
				if (bytes[i] < 0) {// 调整异常数据
					bytes[i] += 256;
				}
			}
			// 生成jpeg图片
			OutputStream out = new FileOutputStream(imgFilePath + fileName);
			out.write(bytes);
			out.flush();
			out.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	//与大数据平台比对 0:通行  1:预警
	public String comparison(Person person){
		try {
			String strURL = BaseDataCode.config.getComparBigDataURL();
			Map<String,String> map = new HashMap<String,String>();
			map.put("id", person.getIdcard());
			String codes = "";
			if (StringUtil.isNull(person.getEquipmentId())) {
				codes = BaseDataCode.config.getFrontId().substring(0, 13); //获取当前组织机构
			}else{
				codes = person.getEquipmentId().substring(0, 13); //获取当前组织机构
			}
			Orgzon org = BaseDataCode.orgMap.get(codes);
			String relust = HttpPostSANHUI.sendPost(strURL, "person", map ,org);
			if(StringUtil.isNull(relust)) return "0";
			try {
				JSONObject jsonObject = JSONObject.fromObject(relust);
				String flag = jsonObject.getString("flag");//【白名单、黑名单、普通】的标志
				JSONArray datas = jsonObject.getJSONArray("data");
				if(datas == null || datas.size()==0){
					if(!"white".equals(flag)){
						flag = "normal";
					}
				}
				if("black".equals(flag)){
					addContrastPerson(jsonObject,person); //添加人员预警数据
					return "1";
				}else{
					return "0";
				}
			}catch (JSONException e) {
				logger.error("调用三汇通行人比对接口错误，解析json错误："+relust);
				logger.error("调用三汇通行人比对接口错误，解析json错误："+e.getMessage());
				return "0";
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			return "0";
		}
	}
	
	/**
	 * 三汇比中人员 保存数据
	 * @param dao
	 * @param datas
	 * @param t
	 * @param em
	 * @throws Exception
	 */
	private void addContrastPerson(JSONObject jsonObject,Person person) throws Exception{
		BaseDao dao = null;
		try {
			JSONArray datas = jsonObject.getJSONArray("data");
			if(datas != null && datas.size() >0){
				JSONObject data = datas.getJSONObject(0);
				String idcard = data.getString("sfzh");
				
				ContrastPerson type = new ContrastPerson(data.getString("uuid"));//人员预警信息
				type.setIdcard(idcard);
				type.setNames(data.getString("name"));
				type.setAddress(data.getString("identlocation"));
				type.setTag(data.getString("datasource"));
				type.setAction(data.getString("action"));
				type.setBkArea(data.getString("bkfwmc"));
				type.setYjArea(data.getString("yjfwmc"));
				if(person == null){
					type.setCaptureTime(new Date());
				}else{
					type.setCaptureTime(person.getCaptureTime());
					type.setGatherPhotoURL(person.getImgUrl());
				}
				type.setInsertTime(new Date());
				type.setSource("本站产生");
				type.setLocation(person.getLocation());
				type.setBirth(IdCardGenerator.birth(type.getIdcard()));
				type.setGender(IdCardGenerator.gender(type.getIdcard()));
				type.setEquipmentId(person.getEquipmentId());
				type.setAreaCode(person.getEquipmentId().substring(0, 6));
				type.setCheckPointId(person.getEquipmentId().substring(0, 13));
				baseDao.saveOrUpdate(type);
				BaseDataCode.putContrastPersonMap(person.getEquipmentId().substring(0, 13), type);
			}
		}catch (Exception e) {
			logger.error(e.getMessage(),e);
		}finally{
			if(dao != null){dao.close();}
		}
	}

}
