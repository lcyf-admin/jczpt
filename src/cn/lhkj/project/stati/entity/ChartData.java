package cn.lhkj.project.stati.entity;

import java.util.ArrayList;

public class ChartData {

	private String name;
	private ArrayList<Object> data;
	private String stack;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Object> getData() {
		return data;
	}

	public void setData(ArrayList<Object> data) {
		this.data = data;
	}

	public String getStack() {
		return stack;
	}

	public void setStack(String stack) {
		this.stack = stack;
	}

}
