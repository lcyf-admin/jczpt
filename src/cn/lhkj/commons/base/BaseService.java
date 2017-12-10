package cn.lhkj.commons.base;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import cn.lhkj.commons.entity.SessionBean;


public abstract class BaseService {
	
	public BaseDao baseDao;
	public SessionBean bean;

	
	public void closeDao() throws Exception{
		if(baseDao != null){
			baseDao.commit();
			baseDao.close();
			baseDao = null;
		}	
	}
	
	public void openDao() throws Exception{
		baseDao = new BaseDao();
		try{
			ServletRequestAttributes attr = (ServletRequestAttributes)RequestContextHolder.currentRequestAttributes(); 
	        HttpServletRequest request = attr.getRequest();
			bean = (SessionBean)request.getSession().getAttribute("SESSION_BEAN");
		}catch (Exception e) {}
	}
	
	public void beginTranscation() throws Exception{
		if(baseDao != null)
			baseDao.beginTranscation();
	}
	
	public void rollback() throws Exception{
		if(baseDao != null)
			baseDao.rollback();
	}
	
	public void commit() throws Exception{
		if(baseDao != null)
			baseDao.commit();
	}
}
