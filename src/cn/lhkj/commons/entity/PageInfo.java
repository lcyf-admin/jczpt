package cn.lhkj.commons.entity;

import java.util.List;



@SuppressWarnings("rawtypes")
public class PageInfo  {
	
	private Integer	rows = 0;	//记录每页显示数据条数
	private Integer	page = 0;	//记录当前的页码
	private String	sord = "";      //排序 asc or desc
	private String	sidx = "";    //排序的列名
	/**************分割线上为前台传入参数 下返回前台的数据******************************/
	private Integer	total = 0;	//记录总页数
	private Integer	record = 0;	//记录总数据条数
	private List gridModel;   //用于当前页显示的数据记录集合JSON
	private String info;
	
	public PageInfo(int page,int rows,String sord,String sidx){
		this.page = page;
		this.rows = rows;
		this.sord = sord;
		this.sidx = sidx;
	}
	
	/////////////////////////////////////////
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

	public Integer getTotal() {
		if(rows != 0)this.total = (int)Math.ceil((double)record/(double)rows);
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getRecord() {
		return record;
	}

	public void setRecord(Integer record) {
		this.record = record;
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

	public List getGridModel() {
		return gridModel;
	}

	public void setGridModel(List gridModel) {
		this.gridModel = gridModel;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

}
