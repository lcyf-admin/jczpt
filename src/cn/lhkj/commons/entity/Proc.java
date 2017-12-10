package cn.lhkj.commons.entity;

public class Proc {
	
	/**类别string/int/double...*/
	private String type;
	
	private Object value;

	public String getType() {
		return type;
	}
	
	public Proc(){
		
	}
	
	public Proc(Object value){
		this.type = "string";
		this.value = value;
	}
	
	public Proc(String type,Object value){
		this.type = type;
		this.value = value;
	}
	///////////////////////////////////
	public void setType(String type) {
		this.type = type;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}
	

}
