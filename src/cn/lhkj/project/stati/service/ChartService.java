package cn.lhkj.project.stati.service;

import java.util.Map;
import cn.lhkj.commons.entity.SessionBean;

public interface ChartService {
	
	/**
	 * 安时间段统计车道、数据门流量和预警信息
	 * @param startsh
	 * @param endstr
	 * @param se
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> trafficdata(String startsh ,String endstr, SessionBean se) throws Exception;
	
	/**
	 * 车道、安检设备数量
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> equipmentdata() throws Exception;
}