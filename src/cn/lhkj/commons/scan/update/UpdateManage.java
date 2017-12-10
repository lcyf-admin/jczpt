package cn.lhkj.commons.scan.update;

import org.apache.log4j.Logger;
import cn.lhkj.commons.base.BaseDao;

public class UpdateManage {
	
	private static  final Logger logger = Logger.getLogger(UpdateManage.class);
	
	/**启动工程完毕前执行的初始化*/
	public void init(){
		BaseDao baseDao = null;
		try {
			baseDao = new BaseDao();
			
			new Update20170400(baseDao).execute();
			new Update20170807(baseDao).execute();
			
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}finally{
			if(baseDao != null){ baseDao.close();}
		}
	}
}
