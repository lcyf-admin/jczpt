package cn.lhkj.commons.filter;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.apache.log4j.Logger;
import cn.lhkj.commons.util.StringUtil;

public class LoginFilter extends HandlerInterceptorAdapter {
	
	private static Logger log = Logger.getLogger(LoginFilter.class);
	private static Properties unvalidate;
	
	// 重写 preHandle()方法，在业务处理器处理请求之前对该请求进行拦截处理
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler){
		try{
			//访问链接
			String accessURI = request.getRequestURI();
			String contextPath = request.getContextPath();
			if(accessURI.equals(contextPath)) return true;
			if(accessURI.contains(contextPath+"/css/")) return true;//静态资源
			if(accessURI.contains(contextPath+"/images/")) return true;//静态资源
			if(accessURI.contains(contextPath+"/img/")) return true;//静态资源
			if(accessURI.contains(contextPath+"/js/")) return true;//静态资源
			if(accessURI.contains(contextPath+"/source/")) return true;//静态资源
			if(accessURI.contains("pages/default/")) return true;//404、500等页面
			if(accessURI.contains("WebService")) return true;//WebService服务不在此验证
			
			if(unvalidate == null){
				inits();
			}
			String key = accessURI.substring(contextPath.length());
			int index = key.lastIndexOf(".");
			if(index > 0){
				key = key.substring(0, index);
			}
			
			String value = unvalidate.getProperty(key);
			//判断如果是不需验证，则直接导向访问页面
			if(StringUtil.trim(value).equalsIgnoreCase("NONE")) return true;
			
			HttpSession session = request.getSession();
			//如果需要登陆验证，则首先进行第一次验证
			if(null == session.getAttribute("SESSION_BEAN")){
				response.sendRedirect(contextPath);
				return false;
			}
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return true;
	}
	
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object o, ModelAndView mav)
			throws Exception {
	}

	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object o, Exception excptn)
			throws Exception {
	}
	
	
	private void inits() throws ServletException {
		InputStream in = LoginFilter.class.getResourceAsStream("/unvalidate.properties");
		try {
			unvalidate = new Properties();
			unvalidate.load(in);
			in.close();
		} catch (IOException e) {
			log.error("读取配置文件【unvalidate.properties】失败，请检查文件路径是否正确");
			log.error(e.getMessage(),e);
		}
	}
	
}
