package cn.lhkj.webservice;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.codehaus.xfire.transport.http.XFireServletController;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import cn.lhkj.commons.base.BaseService;
import cn.lhkj.commons.tools.UUIDFactory;
import cn.lhkj.commons.util.CalendarUtil;
import cn.lhkj.commons.util.StringUtil;
import cn.lhkj.project.vehicle.entity.Uvss;
import cn.lhkj.project.vehicle.entity.UvssPath;
import cn.lhkj.project.vehicle.service.VehicleService;

/**
 * 车底扫描web接口
 */
@Service("uvssWebService")
@Scope("prototype")
public class UVSSWebServiceImpl extends BaseService implements UVSSWebService {
	
	private static final Logger logger = Logger.getLogger(UVSSWebServiceImpl.class);
	
	private @Resource VehicleService vehicleService;
	
	@Override
	public void SendCheckInfo(String UVSSImage, String PlateImage,
		String PlateNumber, String CheckDateTime) {
		try {
			HttpServletRequest request = XFireServletController.getRequest();
		    String ip = request.getRemoteAddr();
		    boolean  isLegal= StringUtil.isLegal(ip);
		    if(isLegal){
			    Uvss uvss = new Uvss(UUIDFactory.getUUIDStr());
			    //uvss.setUvssImage(UVSSImage);
			    //uvss.setPlateImage(PlateImage);
			    uvss.setPlateNumber(PlateNumber);
			    uvss.setCheckDateTime(CalendarUtil.toDate(CheckDateTime, "yyyy/m/d h:mm:ss"));
			    uvss.setIpAddress(ip);
				vehicleService.saveUvss(uvss);
		    }
		} catch (Exception e) {
			logger.error("车底扫描接口调用失败："+e.getMessage());
		}
		
	}
	
	@Override
	public void SendCheckPathInfo(String UVSSImagePath, String PlateImagePath,
			String PlateNumber, String CheckDateTime) {
		try {
			HttpServletRequest request = XFireServletController.getRequest();
		    String ip = request.getRemoteAddr();
		    boolean  isLegal= StringUtil.isLegal(ip);
		    if(isLegal){
			    UvssPath uvss = new UvssPath(UUIDFactory.getUUIDStr());
			    uvss.setUvssImage(UVSSImagePath);
			    uvss.setPlateImage(PlateImagePath);
			    uvss.setPlateNumber(PlateNumber);
			    uvss.setCheckDateTime(CalendarUtil.toDate(CheckDateTime, "yyyy/m/d h:mm:ss"));
			    uvss.setIpAddress(ip);
				vehicleService.saveUvssPath(uvss);
		    }
		} catch (Exception e) {
			logger.error("车底扫描接口调用失败："+e.getMessage());
		}
		
	}
}
