package cn.lhkj.commons.base;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import org.apache.log4j.Logger;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import cn.lhkj.commons.entity.PageInfo;
import cn.lhkj.commons.entity.SessionBean;
import cn.lhkj.commons.entity.ValidformData;
import cn.lhkj.commons.util.StringUtil;

@SuppressWarnings({"rawtypes"})
public abstract class BaseController{ 
	public static final Logger log = Logger.getLogger(BaseController.class);
	
	protected String resultPath;//用作struts2跳转页面路径
	protected ValidformData vd;//返回的提交结果
	protected ModelAndView mav;
	
	static protected final String SUCCESS = "success";
	static protected final String SESSION_BEAN = "SESSION_BEAN";
	
	/*****************************GRID使用参数BEGIN*******************/
	protected PageInfo pageInfo;
	protected Integer rows = 0;	//记录每页显示数据条数
	protected Integer page = 0;	//当前页
	protected String sord = "asc";	//排序 asc or desc
	protected String sidx = "";    //排序的列ID
	/*****************************GRID使用参数END*******************/
	
	/**获取上下文*/
	public ServletContext getCurentServletContext(){
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();    
		return webApplicationContext.getServletContext();  
	}
	
	/**获取上下文HttpServletRequest*/
	public HttpServletRequest getCurentRequest(){
		ServletRequestAttributes attr = (ServletRequestAttributes)RequestContextHolder.currentRequestAttributes(); 
		return attr.getRequest();
	}
	
	/**获取上下文HttpServletResponse*/
	public HttpServletResponse getCurentResponse(){
		ServletRequestAttributes attr = (ServletRequestAttributes)RequestContextHolder.currentRequestAttributes(); 
		HttpServletResponse response = attr.getResponse();
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		response.setContentType("text/plain; charset=utf-8");
		return response;
	}
	
    /**获取工程部署的根目录 */
    public String obtainRootPath(){
		String rootPath = getCurentServletContext().getRealPath("/");
		String tmp = rootPath.substring(rootPath.length()-1);
		if(!tmp.equals("\\")){
			rootPath += "\\";
		}
		return rootPath;
	}
    
    /**获取工程部署访问路径*/
    public String obtainBasePath(){
    	HttpServletRequest request = getRequest();
    	String path = request.getContextPath();
    	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    	return basePath;
    }
    
    /**将前台参数封装成Map */
    protected Map<String, Object> getRequestParams(){
    	return getRequestParams(getCurentRequest());
    }
    
	/**将前台参数封装成Map */
	@SuppressWarnings("unchecked")
	protected Map<String, Object>getRequestParams(HttpServletRequest request){
		Map parameters = new HashMap();
		Map reqParas = request.getParameterMap();
		Iterator keys = reqParas.keySet().iterator();
		while (keys.hasNext()) {
			String key = (String)keys.next();
			String[] values = (String[])reqParas.get(key);
			if (values == null) {
				parameters.put(key, "");
			} else if (values.length == 1) {
				String value = StringUtil.trim(values[0]);
				try {
					parameters.put(key, java.net.URLDecoder.decode(value, "UTF-8"));
				} catch (UnsupportedEncodingException e) {
					log.error(e.getMessage(),e);
				}
			} else {
				parameters.put(key, values);
			}
		}
		parameters.put(SESSION_BEAN, getSessionBean());
		return parameters;
	}
	
	
	/**下载附件  */
	public void downloadFile(String fileName,String filePath){
		HttpServletResponse response = getCurentResponse();
		InputStream fis = null;
		OutputStream toClient = null;
		try {
		    File fileDes = new File(filePath);
		    response.reset();
		    response.setCharacterEncoding("utf-8");
		    response.addHeader("content-Disposition", "attachment;filename="+new String(fileName.getBytes(),"ISO-8859-1"));
		    response.addHeader("content-Length", ""+fileDes.length());
		    response.setContentType("application/octet-stream");
		    
	    	fis = new BufferedInputStream(new FileInputStream(filePath));
		    toClient = new BufferedOutputStream(response.getOutputStream());
		    byte[] buffer = new byte[1024];
		    int i = 0;
		    while ((i=fis.read(buffer)) >= 0) {
		    	toClient.write(buffer,0,i);
		    }
		    toClient.flush();
		} catch (IOException e) {
			
		} finally{
			try {
				if(fis != null) fis.close();
				if(toClient != null) toClient.close();
			}catch (Exception e) {}
		}
	}	
	
	/**
	 * 前台图片展示
	 * @param filePath 图片路径
	 */
	public void showIdCardPhoto(String filePath){
		OutputStream os = null;
		BufferedInputStream bis = null;
		try {
			if(StringUtil.isNull(filePath)){
				filePath = obtainRootPath()+"images/defaut.jpg";
			}
			HttpServletResponse response = getCurentResponse();
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			
			File f = new File(filePath);
			if(!f.exists()){
				f = new File(obtainRootPath()+"images/defaut.jpg");
			}
			FileInputStream fileInputStream = new FileInputStream(f);
			
			bis = new BufferedInputStream(fileInputStream);
			byte[] buffer = new byte[512];
			response.reset();
			response.setCharacterEncoding("UTF-8");
			// 不同类型的文件对应不同的MIME类型
			response.setContentType("image/png");
			response.setContentLength(bis.available());
			os = response.getOutputStream();
			int n;
			while ((n = bis.read(buffer)) != -1) {
				os.write(buffer, 0, n);
			}
			os.flush();
		} catch (IOException e) {
			if(e.getMessage().indexOf("APR error") == -1){
				log.error(e.getMessage());
			}
		} finally{
			try {
				if(bis != null) bis.close();
				if(os != null) os.close();
			}catch (Exception e) {}
		}
	}
	
	/**
	 * 前台图片展示
	 * @param filePath 图片路径
	 */
	public void showVehiclePhoto(String filePath) {
		BufferedInputStream bis = null;
		OutputStream os = null;
		try {
			if(StringUtil.isNull(filePath)){
				filePath = obtainRootPath()+"images/defautVehicel.jpg";
			}
			HttpServletResponse response = getCurentResponse();
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
	
			File f = new File(filePath);
			if(!f.exists()){
				f = new File(obtainRootPath()+"images/defautVehicle.jpg");
			}
			FileInputStream fileInputStream = new FileInputStream(f);
	
			bis = new BufferedInputStream(fileInputStream);
			byte[] buffer = new byte[512];
			response.reset();
			response.setCharacterEncoding("UTF-8");
			// 不同类型的文件对应不同的MIME类型
			response.setContentType("image/png");
			response.setContentLength(bis.available());
			os = response.getOutputStream();
			int n;
			while ((n = bis.read(buffer)) != -1) {
				os.write(buffer, 0, n);
			}
			os.flush();
		} catch (IOException e) {
			if(e.getMessage().indexOf("APR error") == -1){
				log.error(e.getMessage());
			}
		} finally{
			try {
				if(bis != null) bis.close();
				if(os != null) os.close();
			}catch (Exception e) {}
		}
	}
	
	/**返回前台界面String */
	public void printText(String str){
		HttpServletResponse response = getCurentResponse();
		response.setContentType("text/plain; charset=utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();  
            out.print(StringUtil.trim(str));  
            out.flush();  
            log.debug("printText---------Data: "+str);
		} catch (IOException e) {
			log.error(e.getMessage(),e);
		} finally{
			if(out != null) out.close();
		}
	}
	
	/**返回前台界面的json格式如{} */
	public void printObj(Object obj){
		JsonConfig config = new JsonConfig();
		config.registerJsonValueProcessor(Date.class, new JsonProcessor("yyyy-MM-dd"));
		JSONArray jsonarray = JSONArray.fromObject(obj,config);
		String resultstr = jsonarray.toString();
		HttpServletResponse response = getCurentResponse();
		response.setContentType("text/plain; charset=utf-8");
		try {
			PrintWriter out = response.getWriter();
			out.print(resultstr.substring(1,resultstr.length()-1));
			out.flush();  
            out.close(); 
			log.debug("printObjs---------Data: "+resultstr.substring(1,resultstr.length()-1));
		} catch (IOException e) {
			log.error(e.getMessage(),e);
		}
	}
	
	/**返回前台界面的json格式如{} */
	public void printObjs(Object obj){
		JsonConfig config = new JsonConfig();
		config.registerJsonValueProcessor(Date.class, new JsonProcessor("yyyy-MM-dd HH:mm:ss"));
		JSONArray jsonarray = JSONArray.fromObject(obj,config);
		String resultstr = jsonarray.toString();
		HttpServletResponse response = getCurentResponse();
		response.setContentType("text/plain; charset=utf-8");
		try {
			PrintWriter out = response.getWriter();
			out.print(resultstr.substring(1,resultstr.length()-1));
			out.flush();  
            out.close();
			log.debug("printObjs---------Data: "+resultstr.substring(1,resultstr.length()-1));
		} catch (IOException e) {
			log.error(e.getMessage(),e);
		}
	}
	
	/**返回前台界面的json格式如："[{},{}...]" */
	public void printArray(Object resultobj){
		JsonConfig config = new JsonConfig();
		config.registerJsonValueProcessor(Date.class, new JsonProcessor("yyyy-MM-dd"));
		JSONArray jsonarray = JSONArray.fromObject(resultobj,config);
		String resultstr = jsonarray.toString();
		HttpServletResponse response = getCurentResponse();
		response.setContentType("text/plain; charset=utf-8");
		try {
			PrintWriter out =  response.getWriter();
			out.print(resultstr);
			out.flush();  
            out.close();
			log.debug("printArray---------Data: "+resultstr);
		} catch (IOException e) {
			log.error(e.getMessage(),e);
		}
	}	
	
	public HttpServletRequest getRequest(){
		return getCurentRequest();
	}
	
	public HttpSession getSession() {
		HttpSession	session = getRequest().getSession();
		return session;
	}
	
	/**获取SESSION_BEAN */
	public SessionBean getSessionBean(){
		try{
			HttpSession session = getSession();
			if(session != null && session.getAttribute(SESSION_BEAN) != null){
				return (SessionBean)session.getAttribute(SESSION_BEAN);
			}
		} catch(Exception e){
			log.error(e.getMessage(),e);
		}
		return null;
	}
	
	/**获取前台页面返回的参数 */
	public String getParameter(String param){
		HttpServletRequest request = getRequest();
		return StringUtil.trim(request.getParameter(param));
	}
	
	//////////////get set
	public PageInfo getPageInfo() {
		String page = this.getParameter("page");
		String rows = this.getParameter("rows");
		String sord = this.getParameter("sord");
		String sidx = this.getParameter("sidx");
		if(!StringUtil.isNull(page)) this.page = Integer.parseInt(page);
		if(!StringUtil.isNull(rows)) this.rows = Integer.parseInt(rows);
		if(!StringUtil.isNull(sord)) this.sord = sord;
		if(!StringUtil.isNull(sidx)) this.sidx = sidx;
		return new PageInfo (this.page, this.rows, this.sord, this.sidx);
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public String getSord() {
		return sord;
	}

	public void setSord(String sord) {
		this.sord = sord;
	}

	public String getSidx() {
		return sidx;
	}

	public void setSidx(String sidx) {
		this.sidx = sidx;
	}
	public String getResultPath() {
		return resultPath;
	}
	public void setResultPath(String resultPath) {
		this.resultPath = resultPath;
	}
	public ValidformData getVd() {
		return vd;
	}
	public void setVd(ValidformData vd) {
		this.vd = vd;
	}
}
