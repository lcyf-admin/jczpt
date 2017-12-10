package cn.lhkj.project.system.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import cn.lhkj.commons.base.BaseService;
import cn.lhkj.commons.entity.SessionBean;
import cn.lhkj.commons.entity.ZTreeNode;
import cn.lhkj.commons.util.StringUtil;
import cn.lhkj.project.system.entity.Menu;


@Service("menuService")
@Scope("prototype")
public class MenuServiceImpl extends BaseService implements MenuService {
	
	@Override
	public List<ZTreeNode> getMenuZtree(String roleId) throws Exception {
		List<ZTreeNode> resultlist = new ArrayList<ZTreeNode>();
		if(StringUtil.isNull(roleId)) return resultlist;
		String sql = "select " +
				  "mu.id as ID" +
				  ",mu.MENU_NAME as MENUNAME" +
				  ",mr.ID as MID " +
				  "from CSL_MENU mu " +
				  "left join CSL_ROLE_MENU mr on(mr.MENU_ID=mu.id and mr.ROLE_ID='"+roleId+"') " +
				  "where 1=1 order by mu.ranking";
		List<Map<String,String>> list = baseDao.findBySQL(sql);
		if(!StringUtil.isNull(list)){
			for(int i=0;i<list.size();i++){
				HashMap<String, String> map = (HashMap<String, String>)list.get(i);
				ZTreeNode node = new ZTreeNode();
				node.setId(map.get("ID"));
				node.setName(map.get("MENUNAME"));
				node.setpId("root");
				node.setOpen(true);
				if(StringUtil.isNull(map.get("MID"))){
					node.setChecked(false);
				}else{
					node.setChecked(true);
				}
				resultlist.add(node);
			}
		}
		return resultlist;
	}
	
	
	@Override
	public List<Menu> getMenus(SessionBean sessionBean) throws Exception {
		List<Menu> relustList = new ArrayList<Menu>();
		if(sessionBean != null){
			
		}
		return relustList;
	}
}
