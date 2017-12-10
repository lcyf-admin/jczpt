package cn.lhkj.project.system.entity;

import cn.lhkj.commons.defined.Column;
import cn.lhkj.commons.defined.Entity;

/**数据字典  */
@Entity(table="CSL_DICT",key="id")
public class Dict{
	
	/**主键--字典编号 */
	@Column(value="ID")
	private String id;
	
	/**字典名称 */
	@Column(value="NAMES")
	private String names;
	
	/////////////////////////////////////
	public Dict(){
		
	}
	
	public Dict(String id){
		this.id = id;
	}
	
	////////////////////////////////////
	public String getOpt(){
		String btn = "<button type=\"button\" class=\"btn btn-link btn-xs\"  onclick=\"setDictItem('"+id+"');\">数据集</button>"+
				     "<button type=\"button\" class=\"btn btn-link btn-xs\"  onclick=\"editDict('"+id+"');\">编辑</button>"+
				     "<button type=\"button\" class=\"btn btn-link btn-xs\" onclick=\"deleteDict('"+id+"')\";>删除</button>";	
		return btn;
	}
	///////////////////////////////////
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNames() {
		return names;
	}

	public void setNames(String names) {
		this.names = names;
	}

}

	
