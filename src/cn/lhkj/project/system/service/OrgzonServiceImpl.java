package cn.lhkj.project.system.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import cn.lhkj.commons.base.BaseService;
import cn.lhkj.commons.entity.ZTreeNode;
import cn.lhkj.commons.tools.UUIDFactory;
import cn.lhkj.commons.util.StringUtil;
import cn.lhkj.project.system.entity.Orgzon;

@Service("orgzonService")
@Scope("prototype")
public class OrgzonServiceImpl extends BaseService implements OrgzonService {

	@Override
	public Orgzon getOrgzon(String id) throws Exception {
		Orgzon orgzon = (Orgzon)baseDao.getEntity(Orgzon.class, id);
		if(orgzon != null){
			Orgzon pOrgzon = (Orgzon)baseDao.getEntity(Orgzon.class, orgzon.getPid());
			if(pOrgzon != null)
				orgzon.setpNames(pOrgzon.getNames());
		}
		return orgzon;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Orgzon getOrgzonByCodes(String codesId) throws Exception {
		String hql = "from Orgzon where codes='"+codesId+"'";
		List<Orgzon> l = (List<Orgzon>)baseDao.findByHQL(hql);
		if(!StringUtil.isNull(l)){
			return l.get(0);
		}
		return null;
	}
	
	@Override
	public List<ZTreeNode> getOrgzonZTree() throws Exception {
		List<ZTreeNode> resultlist = new ArrayList<ZTreeNode>();
		String sql = "select " +
				"d.ID as ID " +
				",d.PID as PID " +
				",d.NAMES as NAMES " +
				",d.CODES as CODES " +
				",d.IS_ROOT as ISROOT " +
				",d.IS_LEAF as ISLEAF " +
				",d.PRIO as PRIO " +
				",d.DETAIL_NAME as DETAIL_NAME " +
				",d.X as X " +
				",d.Y as Y " +
				"from CSL_ORG d ";
		sql += " order by d.PRIO";
		List<Map<String,String>> list = baseDao.findBySQL(sql);
		if(!StringUtil.isNull(list)){
			for(int i=0;i<list.size();i++){
				HashMap<String, String> map = (HashMap<String, String>)list.get(i);
				ZTreeNode node = new ZTreeNode();
				node.setId(map.get("ID"));
				node.setpId(map.get("PID"));
				node.setName(map.get("NAMES"));
				node.setText(map.get("NAMES")+"");
				node.setTreeCode(map.get("CODES"));
				if("1".equals(map.get("ISROOT"))){
					node.setIconSkin("rootIcon");
					node.setTreeCode("root");
					node.setIsRoot(true);
				}
				if("0".equals(map.get("ISLEAF"))){
					node.setIsLeaf(false);
				}
				if(node.getIsLeaf()){
					node.setTitle("编号："+map.get("CODES")+
							"\n"+"经度："+map.get("X")+
							"\n"+"纬度："+map.get("Y")+
							"\n"+"详细名称："+map.get("DETAIL_NAME"));
				}else{
					node.setTitle("编号："+map.get("CODES"));
				}
				
				
				node.setOpen(true);
				resultlist.add(node);
			}
		}
		return resultlist;
	}
	
	@Override
	public void saveOrUpdate(Orgzon orgzon, String flag) throws Exception {
		if(orgzon == null) return;
		if("edit".equals(flag)){//编辑
			baseDao.update(orgzon);
		}else{
			orgzon.setId(UUIDFactory.getUUIDStr());
			baseDao.save(orgzon);
			List<Map<String,String>> list = baseDao.findBySQL("select PID as PID from CSL_ORG GROUP BY PID");
			String[] array = new String[list.size()];
			for(int i=0;i<array.length;i++){
				array[i] = list.get(i).get("PID");
			}
			baseDao.execute("update CSL_ORG t SET t.is_Leaf= case WHEN t.id in("+StringUtil.getSQLCaseParam(array)+") then 0 else 1 END");
		}
	}
	
	@Override
	public void removeOrgzon(String id) throws Exception {
		if(StringUtil.isNull(id)) throw new NullPointerException("删除的组织机构ID为空！");
		baseDao.execute("delete from CSL_ORG where id='"+id+"'");
		List<Map<String,String>> list = baseDao.findBySQL("select PID as PID from CSL_ORG GROUP BY PID");
		String[] array = new String[list.size()];
		for(int i=0;i<array.length;i++){
			array[i] = list.get(i).get("PID");
		}
		baseDao.execute("update CSL_ORG t SET t.is_Leaf= case WHEN t.id in("+StringUtil.getSQLCaseParam(array)+") then 0 else 1 END");
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public HashSet<String> getStations() throws Exception {
		HashSet<String> set = new HashSet<String>();
		String hql = "from Orgzon where length( codes )=13 ";
		List<Orgzon> l = (List<Orgzon>)baseDao.findByHQL(hql);
		for(Orgzon t : l){
			set.add(t.getCodes());
		}
		return set;
	}
}
