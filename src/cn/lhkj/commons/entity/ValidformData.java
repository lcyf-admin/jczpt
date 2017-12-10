package cn.lhkj.commons.entity;

public class ValidformData {
	
	private String status;//校验状态 成功设置为y 不成功为n 注意需要小写
	private String info;//不成功时候 设置的提示信息
	private String packagePath;//成功后跳转的地址
	private Object relustObj;//返回的其他信息
	
	public ValidformData(){
		
	}
	
	public ValidformData(String status, String info) {
		this.status = status;
		this.info = info;
	}
	
	public void setData(String status,String info){
		this.status = status;
		this.info = info;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getPackagePath() {
		return packagePath;
	}

	public void setPackagePath(String packagePath) {
		this.packagePath = packagePath;
	}

	public Object getRelustObj() {
		return relustObj;
	}

	public void setRelustObj(Object relustObj) {
		this.relustObj = relustObj;
	}

}
