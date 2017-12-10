package cn.lhkj.project.system.entity;

import cn.lhkj.commons.defined.Column;
import cn.lhkj.commons.defined.Entity;

/**数据字典项*/
@Entity(table="CSL_DICT_ITEM",key="id")
public class DictItem{
	
	/**主键 */
	@Column(value="ID")
	private String id;
	
	/**选项代码 */
	@Column(value="CODES")
	private String codes;
	
	/**字典编号 */
	@Column(value="DICT_ID")
	private String dictId;
	
	/**选项名称 */
	@Column(value="OPTIONS")
	private String options;

	/**选项顺序 */
	@Column(value="RANKING")
	private Integer ranking;
	
	/////////////////////////////////////
	public DictItem(){
		
	}
	
	public DictItem(String id){
		this.id = id;
	}
	
	////////////////////////////////////
	public String getOpt(){
		String btn = "<button type=\"button\" class=\"btn btn-link btn-xs\" onclick=\"editDictItem('"+id+"');\">编辑</button>"+
				     "<button type=\"button\" class=\"btn btn-link btn-xs\" onclick=\"deleteDictItem('"+id+"')\";>删除</button>";	
		return btn;
	}
	////////////////////////////////////
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDictId() {
		return dictId;
	}

	public void setDictId(String dictId) {
		this.dictId = dictId;
	}

	public String getOptions() {
		return options;
	}

	public void setOptions(String options) {
		this.options = options;
	}

	public Integer getRanking() {
		return ranking;
	}

	public void setRanking(Integer ranking) {
		this.ranking = ranking;
	}

	public String getCodes() {
		return codes;
	}

	public void setCodes(String codes) {
		this.codes = codes;
	}

}

	
