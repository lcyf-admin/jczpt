package cn.lhkj.commons.scan.job.node;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import cn.lhkj.commons.util.StringUtil;
import cn.lhkj.project.person.entity.Person;
import cn.lhkj.project.person.entity.PersonWarning;

public class DataDoorCode {  
	public static HashSet<String> connectedDataDoor;//当前连通状态的数据门
	
	 ///////////////////////////////////////////////////////////////////
	private static HashSet<String> equipmentSet = new HashSet<String>();
	public static long resetTime = 0;
	public synchronized static void addEquipmentSet(String emId) {
    	equipmentSet.add(StringUtil.trim(emId));
	}
    
    public synchronized static void removeEquipmentSet(String emId) {
    	equipmentSet.remove(StringUtil.trim(emId));
	}
	
    public static HashSet<String> getEquipmentSet() {
		return equipmentSet;
	}
    
    public synchronized static void resetEquipmentSet(){
    	equipmentSet.clear();
    	resetTime = System.currentTimeMillis();
    }
	
	////////////////////////////////////////////////////////////////////////////
	private static HashSet<String> equipmentWarningSet = new HashSet<String>();
	public static long resetWarningTime = 0;
	
	public synchronized static void addEquipmentWarningSet(String emId) {
		equipmentWarningSet.add(StringUtil.trim(emId));
	}
    
    public synchronized static void removeEquipmentWarningSet(String emId) {
    	equipmentWarningSet.remove(StringUtil.trim(emId));
	}
    
    public static HashSet<String> getEquipmentWarningSet() {
		return equipmentWarningSet;
	}
    
    public synchronized static void resetEquipmentWarningSet(){
    	equipmentWarningSet.clear();
    	resetWarningTime = System.currentTimeMillis();
    }
    
    ///////////////////////////////////////////////////////////////////////////////////
    private static Map<String,Person> personMap = new HashMap<String, Person>();
	private static Map<String,PersonWarning> personWarningMap = new HashMap<String, PersonWarning>();
	
	public static Map<String, Person> getPersonMap() {
		return personMap;
	}

	public synchronized static void putPersonMap(String meId,Person person) {
		personMap.put(meId, person);
	}

	public static Map<String, PersonWarning> getPersonWarningMap() {
		return personWarningMap;
	}

	public synchronized static void putPersonWarningMap(String emId, PersonWarning pw) {
		personWarningMap.put(emId, pw);
	}

}  