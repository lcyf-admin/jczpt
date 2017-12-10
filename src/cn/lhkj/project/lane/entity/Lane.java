package cn.lhkj.project.lane.entity;

import cn.lhkj.commons.defined.Column;
import cn.lhkj.commons.defined.Entity;

/** 车道 */
@Entity(table = "CSL_LANE", key = "ID")
public class Lane {
	/** 主键 */
	@Column(value = "id")
	private String id;
	/** 车道名称 */
	@Column(value = "LANENAME")
	private String laneName;
	/** 排列顺序 */
	@Column(value = "RANKING")
	private Integer ranking;

	/**车道类型 0空/1快检车道/2特检车道/3客车车道/4货车车道/5其他*/
	@Column(value="LANETYPE")
	private String laneType;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLaneName() {
		return laneName;
	}

	public void setLaneName(String laneName) {
		this.laneName = laneName;
	}

	public Integer getRanking() {
		return ranking;
	}

	public void setRanking(Integer ranking) {
		this.ranking = ranking;
	}

	public String getLaneType() {
		return laneType;
	}

	public void setLaneType(String laneType) {
		this.laneType = laneType;
	}
}