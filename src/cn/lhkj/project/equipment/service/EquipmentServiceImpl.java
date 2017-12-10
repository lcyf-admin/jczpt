package cn.lhkj.project.equipment.service;

import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import cn.lhkj.commons.base.BaseDataCode;
import cn.lhkj.commons.base.BaseService;
import cn.lhkj.commons.entity.PageInfo;
import cn.lhkj.commons.util.StringUtil;
import cn.lhkj.project.equipment.entity.Equipment;

@Service("equipmentService")
@Scope("prototype")
public class EquipmentServiceImpl extends BaseService implements EquipmentService {

	@Override
	public PageInfo getLaneEquipmentPage(Map<String, Object> requestParams,
			PageInfo pageInfo) throws Exception {
		String sql = "select " +
					 "e.ID as ID" +
					 ",e.NAMES as NAMES " +
					 ",e.IP as IP " +
					 ",e.URL as URL " +
					 ",e.REMARK as REMARK " +
					 ",e.SHOWWAY as SHOWWAY " +
					 ",i.OPTIONS as TYPE " +
					 ",l.LANETYPE as LANETYPE " +
					 ",l.LANENAME as LANENAME " +
					 ",'<button type=\"button\" onclick=\"editEquipment(''' || e.ID || ''');\" class=\"btn btn-link btn-xs\">编辑</button>' " +
					 "|| '<button type=\"button\" onclick=\"deleteEquipment(''' || e.ID || ''');\" class=\"btn btn-link btn-xs\">删除</button>' as OPT " +
					 "from CSL_EQUIPMENT e " +
					 "left join CSL_LANE l on (e.LANEID=l.ID) " +
					 "left join CSL_DICT_ITEM i on (e.TYPE=i.ID) " +
					 "where e.LANEID is not null ";
		String parmsMore = "";
		if(requestParams.get("inputValue") != null ){//设备名称、设备编号用于查询
			String inputValue = requestParams.get("inputValue").toString();
			if(inputValue.length() > 0){
				parmsMore += " and ((e.names like '%" + inputValue +"%') or (e.id like '%" + inputValue + "%'))";
			}
		}
		sql += parmsMore + " order by e.id ";
		pageInfo = baseDao.findPageBySQL(pageInfo, sql);
		return pageInfo;
	}
	
	@Override
	public PageInfo getHallEquipmentPage(Map<String, Object> requestParams,
			PageInfo pageInfo) throws Exception {
		String sql = "select "
				+ "e.ID as ID"
				+ ",e.NAMES as NAMES "
				+ ",e.IP as IP "
				+ ",e.URL as URL "
				+ ",e.REMARK as REMARK "
				+ ",i.OPTIONS as TYPE "
				+ ",h.HALLNAME as HALLNAME "
				+ ",'<button type=\"button\" onclick=\"editEquipment(''' || e.ID || ''');\" class=\"btn btn-link btn-xs\">编辑</button>' "
				+ "|| '<button type=\"button\" onclick=\"deleteEquipment(''' || e.ID || ''');\" class=\"btn btn-link btn-xs\">删除</button>' as OPT "
				+ "from CSL_EQUIPMENT e "
				+ "left join CSL_HALL h on (e.HALLID=h.ID) "
				+ "left join CSL_DICT_ITEM i on (e.TYPE=i.ID) " + "where e.HALLID is not null ";
		String parmsMore = "";
		if (requestParams.get("inputValue") != null) {// 设备名称、设备编号用于查询
			String inputValue = requestParams.get("inputValue").toString();
			if (inputValue.length() > 0) {
				parmsMore += " and ((e.names like '%" + inputValue
						+ "%') or (e.id like '%" + inputValue + "%'))";
			}
		}
		sql += parmsMore + " order by e.id ";
		pageInfo = baseDao.findPageBySQL(pageInfo, sql);
		return pageInfo;
	}

	@Override
	public void removeEquipment(String id) throws Exception {
		if(StringUtil.isNull(id)) throw new NullPointerException("删除的设备ID为空！");
		baseDao.execute("delete from CSL_EQUIPMENT where id='" + id + "'");
		BaseDataCode.init();
	}

	@Override
	public void saveEquipment(Equipment equipment) throws Exception {
		if(null == equipment) return;
		baseDao.save(equipment);
		BaseDataCode.init();
	}

	@Override
	public Equipment getEquipmentById(String id) throws Exception {
		return (Equipment) baseDao.getEntity(Equipment.class, id);
	}

	@Override
	public void updateEquipment(Equipment equipment) throws Exception {
		if(null == equipment) return;
		baseDao.update(equipment);
		BaseDataCode.init();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Equipment> listAll() throws Exception {
		String hql = "from Equipment order by names";
		return (List<Equipment>) baseDao.findByHQL(hql);
	}
}