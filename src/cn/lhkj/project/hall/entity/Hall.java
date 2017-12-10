package cn.lhkj.project.hall.entity;

import cn.lhkj.commons.defined.Column;
import cn.lhkj.commons.defined.Entity;

/** 安检厅 */
@Entity(table = "CSL_HALL", key = "ID")
public class Hall {
	/** 主键 */
	@Column(value = "ID")
	private String id;
	/** 大厅编号 */
	@Column(value = "HALLNAME")
	private String hallName;
	/** 排列顺序 */
	@Column(value = "RANKING")
	private Integer ranking;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getHallName() {
		return hallName;
	}

	public void setHallName(String hallName) {
		this.hallName = hallName;
	}

	public Integer getRanking() {
		return ranking;
	}

	public void setRanking(Integer ranking) {
		this.ranking = ranking;
	}
}