package cn.lhkj.project.system.entity;

import cn.lhkj.commons.defined.Column;
import cn.lhkj.commons.defined.Entity;

/**组织机构  */
@Entity(table="CSL_ORG",key="id")
public class Orgzon{
	
	/**主键 */
	@Column(value="ID")
	private String id;
	
	/**父部门ID */
	@Column(value="PID")
	private String pid;
	
	/**排序*/
	@Column(value="PRIO")
	private Integer prio;
	
	/**是否是根节点*/
	@Column(value="IS_ROOT")
	private Integer isRoot;
	
	/**是否是叶子节点*/
	@Column(value="IS_LEAF")
	private Integer isLeaf;
	
	/**组织机构名称 */
	@Column(value="NAMES")
	private String names;
	
	/**组织机构编号 */
	@Column(value="CODES")
	private String codes;
	
	/**详细名称*/
	@Column(value="DETAIL_NAME")
	private String detailName;
	
	/**经度*/
	@Column(value="X")
	private String x;
	
	/**纬度*/
	@Column(value="Y")
	private String y;
	
	/**父机构名称*/
	private String pNames;
	///////////////////////////////////////////
	public Orgzon(){
		
	}
	
	public Orgzon(String id){
		this.id = id;
	}
	///////////////////////////////////////////
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public Integer getPrio() {
		return prio;
	}

	public void setPrio(Integer prio) {
		this.prio = prio;
	}

	public Integer getIsRoot() {
		return isRoot;
	}

	public void setIsRoot(Integer isRoot) {
		this.isRoot = isRoot;
	}

	public String getNames() {
		return names;
	}

	public void setNames(String names) {
		this.names = names;
	}

	public Integer getIsLeaf() {
		return isLeaf;
	}

	public void setIsLeaf(Integer isLeaf) {
		this.isLeaf = isLeaf;
	}

	public String getpNames() {
		return pNames;
	}

	public void setpNames(String pNames) {
		this.pNames = pNames;
	}

	public String getCodes() {
		return codes;
	}

	public void setCodes(String codes) {
		this.codes = codes;
	}

	public String getDetailName() {
		return detailName;
	}

	public void setDetailName(String detailName) {
		this.detailName = detailName;
	}

	public String getX() {
		return x;
	}

	public void setX(String x) {
		this.x = x;
	}

	public String getY() {
		return y;
	}

	public void setY(String y) {
		this.y = y;
	}

}

	
