package cn.lhkj.commons.scan;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.lhkj.commons.base.BaseDataCode;
import cn.lhkj.commons.base.BaseDataDict;
import cn.lhkj.commons.entity.LKLicense;
import cn.lhkj.commons.scan.thread.TestContrastPersonThread;
import cn.lhkj.commons.scan.thread.TestPersonThread;
import cn.lhkj.commons.scan.thread.TestVehicleThread;
import cn.lhkj.commons.scan.update.UpdateManage;
import cn.lhkj.commons.scan.update.UpdateManageThread;

/**
 * 系统监听
 */
public class AppInitListener implements ServletContextListener {
	public final Logger log = Logger.getLogger(AppInitListener.class);
	
	@Override
	public void contextInitialized(ServletContextEvent servletContexEvent) {
		// 获得应用Spring环境
		ApplicationContext context = WebApplicationContextUtils
				.getRequiredWebApplicationContext(servletContexEvent
						.getServletContext());
		if (context == null) {
			log.error("Spring环境未能建立");
			throw new RuntimeException("");
		}
		
		new UpdateManage().init();//执行更新
		BaseDataDict.init();//数据字典初始化
		BaseDataCode.init();//将设备信息放入内存
		
		try{
			LKLicense.usable = true;
			/*VerifyLicense vLicense = new VerifyLicense();
			vLicense.setParam("./param.properties");//获取参数
			LKLicense.usable = vLicense.verify();//验证证书*/
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		
		if("1".equals(BaseDataCode.config.getTestModel())){
			new Thread(new TestVehicleThread()).start();//起一个测试线程 随机车辆数据
			new Thread(new TestPersonThread()).start();//起一个测试线程 随机人员数据
			new Thread(new TestContrastPersonThread()).start();//起一个测试线程 随机比中人员数据
		}
		new Thread(new UpdateManageThread()).start();
	}

	@Override
	public void contextDestroyed(ServletContextEvent servletContexEvent) {}
	
}