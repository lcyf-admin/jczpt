package cn.lhkj.commons.scan.update;

import org.apache.log4j.Logger;

import cn.lhkj.commons.base.BaseDao;

public class UpdateManageThread implements Runnable {
	private static  final Logger logger = Logger.getLogger(UpdateManageThread.class);
	
	@Override
	public void run() {
		BaseDao baseDao = null;
		try {
			baseDao = new BaseDao();
			new UpdateThread20170400(baseDao).execute();
			new UpdateThread20170815(baseDao).execute();
			new UpdateThread20170817(baseDao).execute();
			
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}finally{
			if(baseDao != null){ baseDao.close();}
		}
	}
}
