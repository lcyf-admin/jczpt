package cn.lhkj.commons.scan.update;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import cn.lhkj.commons.base.BaseDao;
import cn.lhkj.commons.base.BaseDataCode;
import cn.lhkj.commons.util.StringUtil;
import cn.lhkj.project.contrast.entity.ContrastVehicle;
import cn.lhkj.project.vehicle.entity.Passenger;
import cn.lhkj.project.vehicle.entity.Vehicle;
import cn.lhkj.project.vehicle.entity.VehicleBrake;
import cn.lhkj.project.vehicle.entity.VehicleShunt;


/**
 * 本次更新内容：将Passenger中司机数据移到Vehicle和VehicleBrake表中
 */
public class UpdateThread20170817 {
	
	private BaseDao baseDao;
	
	public UpdateThread20170817(BaseDao baseDao){
		this.baseDao = baseDao;
	}
	
	public void execute(){
		updateContrastVehicle();
		transfPassenger();
		updateVehicle();
		updateVehicleShunt();
		updateVehicleBrake();
	}
	
	/**将CONTRAST_VEHICLE采集照片补充*/
	@SuppressWarnings("unchecked")
	public void updateContrastVehicle(){
		String hql = "from ContrastVehicle t where (t.gatherPhotoURL is null or t.gatherPhotoURL = '') and t.vehicleId is not null order by t.insertTime desc ";
		try {
			for(int i=0;i<10000000;i++){
				List<ContrastVehicle> contrastList = (List<ContrastVehicle>)baseDao.findByHQL(hql, 0, 100);
				if(StringUtil.isNull(contrastList)) break;
				for(ContrastVehicle t : contrastList){
					VehicleBrake vb = (VehicleBrake)baseDao.getEntity(VehicleBrake.class, t.getVehicleId());
					if(vb != null){
						String carImg = StringUtil.trim(vb.getCarImg());
						if(carImg.startsWith("http")){
							t.setGatherPhotoURL(carImg);
						}else if(carImg.contains("cetc")){
							t.setGatherPhotoURL("http://"+BaseDataCode.config.getFileServerIp()+":8889"+
									carImg.substring(carImg.indexOf("cetc")+4).replace("\\", "/"));
						}
					}
					
					if(StringUtil.isNull(t.getGatherPhotoURL())){
						VehicleShunt vs = (VehicleShunt)baseDao.getEntity(VehicleShunt.class, t.getVehicleId());
						if(vs != null){
							String carImg = StringUtil.trim(vs.getVehicleImage());
							if(carImg.startsWith("http")){
								t.setGatherPhotoURL(carImg);
							}else if(carImg.contains("cetc")){
								t.setGatherPhotoURL("http://"+BaseDataCode.config.getFileServerIp()+":8889"+
										carImg.substring(carImg.indexOf("cetc")+4).replace("\\", "/"));
							}
						}
					}
					if(StringUtil.isNull(t.getGatherPhotoURL())){
						t.setGatherPhotoURL("no picture");
					}
					baseDao.update(t);
				}
			}
		} catch (Exception e) {}
	}
	
	@SuppressWarnings("unchecked")
	private void transfPassenger(){
		String hql = "from Passenger t where t.isDriver = '1' order by t.insertTime desc ";
		try {
			for(int i=0;i<10000000;i++){
				List<Passenger> driverList = (List<Passenger>)baseDao.findByHQL(hql, 0, 2000);
				if(StringUtil.isNull(driverList)) break;
				for(Passenger t : driverList){
					VehicleBrake vb = (VehicleBrake)baseDao.getEntity(VehicleBrake.class, t.getVehicleId());
					if(vb != null){
						vb.setUserName(t.getUserName());
						vb.setSex(t.getSex());
						vb.setMinzu(t.getMinzu());
						vb.setCardNum(t.getCardNum());
						vb.setBirthDate(t.getBirthDate());
						vb.setAddress(t.getAddress());
						vb.setQianfa(t.getQianfa());
						vb.setYouxiaoqi(t.getYouxiaoqi());
						vb.setCardImg(t.getCardImg());
						String carImg = StringUtil.trim(vb.getCarImg());
						String cardImg = StringUtil.trim(vb.getCardImg());
						String numImg = StringUtil.trim(vb.getNumImg());
						if(carImg.startsWith("http")){
							vb.setLocalCarImg(carImg);
						}else if(carImg.contains("cetc")){
							vb.setLocalCarImg("http://"+BaseDataCode.config.getFileServerIp()+":8889"+
									carImg.substring(carImg.indexOf("cetc")+4).replace("\\", "/"));
						}
						if(cardImg.startsWith("http")){
							vb.setLocalCardImg(cardImg);
						}else if(cardImg.contains("cetc")){
							vb.setLocalCardImg("http://"+BaseDataCode.config.getFileServerIp()+":8889"+
									cardImg.substring(cardImg.indexOf("cetc")+4).replace("\\", "/"));
						}
						if(numImg.startsWith("http")){
							vb.setLocalNumImg(numImg);
						}else if(numImg.contains("cetc")){
							vb.setLocalNumImg("http://"+BaseDataCode.config.getFileServerIp()+":8889"+
									numImg.substring(numImg.indexOf("cetc")+4).replace("\\", "/"));
						}
						baseDao.update(vb);
						Vehicle v = (Vehicle)baseDao.getEntity(Vehicle.class, t.getVehicleId());
						if(v != null){
							v.setUserName(t.getUserName());
							v.setSex(t.getSex());
							v.setMinzu(t.getMinzu());
							v.setCardNum(t.getCardNum());
							v.setBirthDate(t.getBirthDate());
							v.setAddress(t.getAddress());
							v.setQianfa(t.getQianfa());
							v.setYouxiaoqi(t.getYouxiaoqi());
							v.setCarImg(vb.getLocalCarImg());
							v.setCardImg(vb.getLocalCardImg());
							v.setNumImg(vb.getLocalNumImg());
							baseDao.update(v);
						}
					}
					runSQL("delete from CSL_PASSENGER where id='"+t.getId()+"'");
				}
			}
		} catch (Exception e) {}
	}
	
	@SuppressWarnings("unchecked")
	private void updateVehicle(){
		String hql = "from Vehicle t where instr( t.carImg , 'cetc' ) > 0 or instr( t.numImg , 'cetc' ) > 0 order by t.insertTime desc ";
		try {
			for(int i=0;i<10000000;i++){
				List<Vehicle> vehicleList = (List<Vehicle>)baseDao.findByHQL(hql, 0, 2000);
				if(StringUtil.isNull(vehicleList)) break;
				for(Vehicle t : vehicleList){
					String carImg = StringUtil.trim(t.getCarImg());
					String numImg = StringUtil.trim(t.getNumImg());
					if(carImg.contains("cetc")){
						t.setCarImg("http://"+BaseDataCode.config.getFileServerIp()+":8889"+
								carImg.substring(carImg.indexOf("cetc")+4).replace("\\", "/"));
					}
					if(numImg.contains("cetc")){
						t.setNumImg("http://"+BaseDataCode.config.getFileServerIp()+":8889"+
								numImg.substring(numImg.indexOf("cetc")+4).replace("\\", "/"));
					}
					baseDao.update(t);
				}
			}
		} catch (Exception e) {}
	}
	
	@SuppressWarnings("unchecked")
	private void updateVehicleShunt(){
		String hql = "from VehicleShunt t where (instr( t.vehicleImage , 'cetc' ) > 0 or instr( t.plateImage , 'cetc' ) > 0)" +
				" and t.localVehicleImage is null and t.localPlateImage is null  order by t.insertTime desc ";
		try {
			for(int i=0;i<10000000;i++){
				List<VehicleShunt> vehicleShuntList = (List<VehicleShunt>)baseDao.findByHQL(hql, 0, 2000);
				if(StringUtil.isNull(vehicleShuntList)) break;
				for(VehicleShunt t : vehicleShuntList){
					String carImg = StringUtil.trim(t.getVehicleImage());
					String numImg = StringUtil.trim(t.getPlateImage());
					if(carImg.contains("cetc")){
						t.setLocalVehicleImage("http://"+BaseDataCode.config.getFileServerIp()+":8889"+
								carImg.substring(carImg.indexOf("cetc")+4).replace("\\", "/"));
					}
					if(numImg.contains("cetc")){
						t.setLocalPlateImage("http://"+BaseDataCode.config.getFileServerIp()+":8889"+
								numImg.substring(numImg.indexOf("cetc")+4).replace("\\", "/"));
					}
					baseDao.update(t);
				}
			}
		} catch (Exception e) {}
	}
	
	@SuppressWarnings("unchecked")
	private void updateVehicleBrake(){
		String hql = "from VehicleBrake t where (instr( t.carImg , 'cetc' ) > 0 or instr( t.numImg , 'cetc' ) > 0)" +
				" and t.localCarImg is null and t.localNumImg is null  order by t.insertTime desc ";
		try {
			for(int i=0;i<10000000;i++){
				List<VehicleBrake> vehicleBrakeList = (List<VehicleBrake>)baseDao.findByHQL(hql, 0, 2000);
				if(StringUtil.isNull(vehicleBrakeList)) break;
				for(VehicleBrake t : vehicleBrakeList){
					String carImg = StringUtil.trim(t.getCardImg());
					String numImg = StringUtil.trim(t.getNumImg());
					if(carImg.contains("cetc")){
						t.setLocalCardImg("http://"+BaseDataCode.config.getFileServerIp()+":8889"+
								carImg.substring(carImg.indexOf("cetc")+4).replace("\\", "/"));
					}
					if(numImg.contains("cetc")){
						t.setLocalNumImg("http://"+BaseDataCode.config.getFileServerIp()+":8889"+
								numImg.substring(numImg.indexOf("cetc")+4).replace("\\", "/"));
					}
					baseDao.update(t);
				}
			}
		} catch (Exception e) {}
	}
	
	private void runSQL(String sql){
		Statement stmt = null;
		try{
			stmt = baseDao.getConn().createStatement();
			stmt.execute(sql);
		}catch (Exception e){
		}finally{
			try {
				if(stmt != null) stmt.close();
			} catch (SQLException e) { }
		}
	}
	
}
