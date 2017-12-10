package cn.lhkj.project.person.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import cn.lhkj.commons.entity.PageInfo;
import cn.lhkj.project.person.entity.CheckPersonContraband;
import cn.lhkj.project.person.entity.Person;

public interface PersonService {
	
	/**
	 * 获取经过的人员的数据信息
	 * @param pageInfo
	 * @return
	 * @throws Exception
	 */
	public List<Person> listPerson(String records,String stationId) throws Exception;
	
	/**
	 * 获取数据门通过的人员信息
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Person getPerson(String id) throws Exception;
	
	/**
	 * 获取经过的人员的数据信息
	 * @param pageInfo
	 * @return
	 * @throws Exception
	 */
	public PageInfo getPriorDataPersonePage(Map<String, Object> requestParams,PageInfo pageInfo) throws Exception;

	/**
	 * 获取人员数量
	 * @param pageInfo
	 * @return
	 * @throws Exception
	 */
	public String getCountPerson(Map<String, Object> requestParams) throws Exception;
	
	/**
	 * 经过的人员全部数据信息不分页
	 * @param pageInfo
	 * @return
	 * @throws Exception
	 */
	public List<Person> getPriorDataPerson(Map<String, Object> requestParams) throws Exception;		
	
	/**
	 * 添加违禁品
	 * @param user
	 * @throws Exception
	 */
	public void addPersonContraband(MultipartFile file,HttpServletRequest request) throws Exception;
	
	/**
	 * 根据用户id获取违禁品
	 * @param user
	 * @throws Exception
	 */
	public CheckPersonContraband getPersonContraband(String personId) throws Exception;
	
	/**人证采集-数据采集接口*/
	public String gather(String infos,String ip) throws Exception;
	
	/**
	 * 添加通行人员信息
	 * @param user
	 * @return 
	 * @throws Exception
	 */
	public Person addPerson(Person person,String station) throws Exception;
	
	/**手动录入与大数据平台接口比对*/
	public String comparePerson(Person person) throws Exception;
}
