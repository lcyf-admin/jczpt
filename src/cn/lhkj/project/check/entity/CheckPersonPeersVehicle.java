package cn.lhkj.project.check.entity;

import cn.lhkj.commons.defined.Column;
import cn.lhkj.commons.defined.Entity;

//核录预警人员的同行车辆
@Entity(table="CHECK_PERSON_PEERS_VEHICLE",key="id")
public class CheckPersonPeersVehicle {
	/**主键*/
	@Column(value="id")
	private String id;
	
	/**车牌号*/
	@Column(value="CAR_NUM")
	private String carNum;

	/**车牌颜色*/
	@Column(value="PLATE_COLOR")
	private String plateColor;
	
	/**车辆颜色*/
	@Column(value="VEHICLE_COLOR")
	private String vehicleColor;

	/**车辆类型*/
	@Column(value="VEHICLE_TYPE")
	private String vehicleType;
	
	/**外键--预警人员ID*/
	@Column(value="CHECK_PERSON_ID")
	private String checkPersonId;

	//////////////////////////////////////
	public CheckPersonPeersVehicle(){
		
	}
	
	public CheckPersonPeersVehicle(String id){
		this.id = id;
	}
	//////////////////////////////////////	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCarNum() {
		return carNum;
	}

	public void setCarNum(String carNum) {
		this.carNum = carNum;
	}

	public String getPlateColor() {
		return plateColor;
	}

	public void setPlateColor(String plateColor) {
		this.plateColor = plateColor;
	}

	public String getVehicleColor() {
		return vehicleColor;
	}

	public void setVehicleColor(String vehicleColor) {
		this.vehicleColor = vehicleColor;
	}

	public String getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}

	public String getCheckPersonId() {
		return checkPersonId;
	}

	public void setCheckPersonId(String checkPersonId) {
		this.checkPersonId = checkPersonId;
	}
}
