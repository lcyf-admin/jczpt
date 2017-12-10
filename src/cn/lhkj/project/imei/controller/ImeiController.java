package cn.lhkj.project.imei.controller;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.lhkj.commons.base.BaseController;
import cn.lhkj.commons.util.CalendarUtil;
import cn.lhkj.commons.util.StringUtil;
import cn.lhkj.project.imei.entity.Imei;
import cn.lhkj.project.imei.service.ImeiService;

@Controller
@Scope("prototype")
public class ImeiController extends BaseController {
	public static final Logger logger = Logger.getLogger(ImeiController.class);
	
	private @Resource ImeiService imeiService;
	
	private Imei imei;
	
	@ModelAttribute()
	public void prepare() throws Exception {
		String imeiId = super.getParameter("imei.id");
		if(StringUtil.isNull(imeiId)) return;
		imei = imeiService.getImei(imeiId);
	}
	
	/**上传侦码采集数据接口*/
	@ResponseBody
	@RequestMapping(value="/imei_upload")
	public String upload(){		
		Map<String, String> map = new HashMap<String, String>();
		String ip = super.getRequest().getRemoteAddr();
		boolean  isLegal= StringUtil.isLegal(ip);
		String data = null;
		if(isLegal){
			try {
				InputStream in = super.getRequest().getInputStream();
				data = StringUtil.convertStreamToString(in);
				JSONObject jsonObject = JSONObject.fromObject(data);
				String passTime = StringUtil.trim(jsonObject.getString("passTime"));//数据采集时间
				SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		        Date passDate = sf.parse(passTime);
		        long passLong = passDate.getTime();
		        long sysTime = System.currentTimeMillis();
		        if((passLong-sysTime) > 5000 || (sysTime-passLong) > 90000){//采集时间大于真实时间 或 采集时间和真实时间相超过一分半钟
		        	logger.error("未较时IP="+ip+",passTime="+passTime+",sysTime="+CalendarUtil.getCurrentTime());
		        	map.put("sendTime", CalendarUtil.getCurrentTime());
					map.put("code", "400");
					map.put("msg", "请将服务器和设备较时，视频专网较时服务器地址：21.0.2.8");
					super.printObj(map);
		        }
		        map = imeiService.gather(jsonObject,ip);
			}catch (Exception e) {
				map.put("sendTime", CalendarUtil.getCurrentTime());
				map.put("code", "400");
				map.put("msg", e.getMessage());
				logger.error(e.getMessage()+"\t\n 来自IP为"+ip+"的设备调用接口失败！参数：\t\n"+data);
			}
			super.printObj(map);
		}else{
			map.put("sendTime", CalendarUtil.getCurrentTime());
			map.put("code", "400");
			map.put("msg", "非法IP地址"+ip);
			logger.error("非法IP地址:"+ip+" 调用imei_uoload.action接口");
			super.printObj(map);
		}
		return null;
	}
	
	/**上传侦码采集数据接口*/
	@ResponseBody
	@RequestMapping(value="/phone_upload")
	public String phone_upload(){		
		Map<String, String> map = new HashMap<String, String>();
		String ip = super.getRequest().getRemoteAddr();
		boolean  isLegal= StringUtil.isLegal(ip);
		String data = null;
		if(isLegal){
			try {
				InputStream in = super.getRequest().getInputStream();
				data = StringUtil.convertStreamToString(in);
				JSONObject jsonObject = JSONObject.fromObject(data);
				String passTime = StringUtil.trim(jsonObject.getString("passTime"));//数据采集时间
				SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		        Date passDate = sf.parse(passTime);
		        long passLong = passDate.getTime();
		        long sysTime = System.currentTimeMillis();
		        if((passLong-sysTime) > 5000 || (sysTime-passLong) > 90000){//采集时间大于真实时间 或 采集时间和真实时间相超过一分半钟
		        	logger.error("未较时IP="+ip+",passTime="+passTime+",sysTime="+CalendarUtil.getCurrentTime());
		        	map.put("sendTime", CalendarUtil.getCurrentTime());
					map.put("code", "400");
					map.put("msg", "请将服务器和设备较时，视频专网较时服务器地址：21.0.2.8");
					super.printObj(map);
		        }
		        map = imeiService.uploadPhone(jsonObject,ip);
			}catch (Exception e) {
				map.put("sendTime", CalendarUtil.getCurrentTime());
				map.put("code", "400");
				map.put("msg", e.getMessage());
				map.put("warnPhone", "");
				logger.error(e.getMessage()+"\t\n 来自IP为"+ip+"的设备调用接口失败！参数：\t\n"+data);
			}
			super.printObj(map);
		}else{
			map.put("sendTime", CalendarUtil.getCurrentTime());
			map.put("code", "400");
			map.put("msg", "非法IP地址"+ip);
			map.put("warnPhone", "");
			logger.error("非法IP地址:"+ip+" 调用imei_uoload.action接口");
			super.printObj(map);
		}
		return null;
	}
	
	
	////////////////////////////////////////////////////////////
	public Imei getImei() {
		return imei;
	}

	public void setImei(Imei imei) {
		this.imei = imei;
	}

}
