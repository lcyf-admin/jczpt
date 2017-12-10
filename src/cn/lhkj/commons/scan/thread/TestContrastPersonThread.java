package cn.lhkj.commons.scan.thread;


import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;

import cn.lhkj.commons.base.BaseDao;
import cn.lhkj.commons.base.BaseDataCode;
import cn.lhkj.commons.tools.IdCardGenerator;
import cn.lhkj.commons.tools.UUIDFactory;
import cn.lhkj.project.contrast.entity.ContrastPerson;
import cn.lhkj.project.equipment.entity.Equipment;

/**
 * 测试线程增加比中车辆
 */
public class TestContrastPersonThread implements Runnable {
	
	private static final Logger logger = Logger.getLogger(TestContrastPersonThread.class);
	
	@Override
	public void run(){
		try {
			BaseDao dao = new BaseDao();
			Thread.sleep(20000);
			List<Equipment> dataDoorList = BaseDataCode.dataDoorList;
			while(true){
				Random random = new Random();
				Equipment em = dataDoorList.get(random.nextInt(dataDoorList.size()));
				ContrastPerson t = new ContrastPerson(UUIDFactory.getUUIDStr());
				t.setIdcard(IdCardGenerator.randomIdcard());
				t.setNames(IdCardGenerator.randomName());
				t.setAddress("地址"+random.nextInt(100));
				t.setTag("标签"+random.nextInt(100));
				t.setAction("核录");
				t.setCaptureTime(new Date());
				t.setInsertTime(new Date());
				t.setSource("本站产生");
				t.setLocation(em.getNames());
				t.setBirth(IdCardGenerator.birth(t.getIdcard()));
				t.setGender(IdCardGenerator.gender(t.getIdcard()));
				t.setEquipmentId(em.getId());
				t.setAreaCode(em.getId().substring(0, 6));
				t.setCheckPointId(em.getId().substring(0, 13));
				dao.save(t);
				BaseDataCode.putContrastPersonMap(em.getId().substring(0, 13), t);
				try {
					Thread.sleep(50000);
				} catch (InterruptedException e) {}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
}
