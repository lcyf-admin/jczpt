package cn.lhkj.commons.scan.job;

import java.util.HashSet;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.lhkj.commons.base.BaseDataCode;
import cn.lhkj.commons.scan.job.datadoor.DataDoorNodeThread;
import cn.lhkj.commons.scan.job.node.CheckUpload;
import cn.lhkj.commons.scan.job.node.ConnectJudge;
import cn.lhkj.commons.scan.job.node.DataDoorCode;
import cn.lhkj.commons.util.StringUtil;
import cn.lhkj.project.equipment.entity.Equipment;

@Component
@EnableScheduling
@Lazy(false)
public class ScheduledJob {
	private static final Logger logger = Logger.getLogger(ScheduledJob.class);
	
	/**预警核录数据等等数据的上传*/
	@Scheduled(fixedDelay=180000,initialDelay=15*1000)//每180秒执行一次，延迟30秒执行
	public void CheckUpload(){
		new CheckUpload().run();
	}
	
	/**判断数据门是否连通*/
	@Scheduled(fixedDelay=20000,initialDelay=2000)//每30秒执行一次，延迟2秒执行
	public void ConnertJudge(){
		new ConnectJudge().run();
	}
	
	/**数据门轮询通行数据*/
	@Scheduled(fixedDelay=1000,initialDelay=15000)//每秒执行一次，延迟10秒执行
	public void DataDoor(){
		try {
    		HashSet<String> equipmentSet = DataDoorCode.getEquipmentSet();
    		List<Equipment> dataDoorList = BaseDataCode.dataDoorList;
	    	if(StringUtil.isNull(dataDoorList)) return;
	    	for(Equipment em : dataDoorList){
	    		if(DataDoorCode.connectedDataDoor == null) return;
	    		if(!DataDoorCode.connectedDataDoor.contains(StringUtil.trim(em.getId()))) continue;
				String url = em.getUrl();
				if(StringUtil.isNull(url)) continue;
				if(equipmentSet.contains(StringUtil.trim(em.getId()))) continue;
				new Thread(new DataDoorNodeThread(em)).start();
			}
    	} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	/**数据门轮询预警数据 (合并到DataDoorNodeThread中了)
	@Scheduled(fixedDelay=1000,initialDelay=15000)//每秒执行一次，延迟10秒执行
	public void DataDoorWarning(){
		try {
    		HashSet<String> equipmentWarningSet = DataDoorCode.getEquipmentWarningSet();
    		List<Equipment> dataDoorList = BaseDataCode.dataDoorList;
	    	if(StringUtil.isNull(dataDoorList)) return;
	    	for(Equipment em : dataDoorList){
	    		if(DataDoorCode.connectedDataDoor == null) return;
	    		if(!DataDoorCode.connectedDataDoor.contains(StringUtil.trim(em.getId()))) continue;
				String url = em.getUrl();
				if(StringUtil.isNull(url)) continue;
				if(equipmentWarningSet.contains(StringUtil.trim(em.getId()))) continue;
				new Thread(new DataDoorWarningNodeThread(em)).start();
			}
    	} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}*/
	
	@Scheduled(cron="0 0/30 * * * ?")//每30分钟执行一次
    public void resetDataDoor() {
		try {
			DataDoorCode.resetEquipmentSet();
			DataDoorCode.resetEquipmentWarningSet();
		}catch (Exception e) {}
    }
	
	@Scheduled(cron="0 0 0 * * ?")//每天凌晨0点执行
	public void clear(){
		try{//清除前一天的内存中的预警数据 
			BaseDataCode.todayVehicleShuntContrastMap.clear();
		}catch (Exception e) {}
	}
	
}
