package cn.lhkj.commons.scan.job.node;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import cn.lhkj.commons.base.BaseDataCode;
import cn.lhkj.commons.util.StringUtil;
import cn.lhkj.project.equipment.entity.Equipment;

/**
 * 检测数据门属否连通
 */
public class ConnectJudge {  
	
	private static final Logger logger = Logger.getLogger(ConnectJudge.class);
	public static String reg = ".*\\/\\/([^\\/\\:]*).*";
	
	
    public void run() {
    	try {
    		HashSet<String> connectedDataDoor = new HashSet<String>();
    		List<Equipment> dataDoorList = BaseDataCode.dataDoorList;
	    	if(StringUtil.isNull(dataDoorList)) return;
	    	for(Equipment em : dataDoorList){
				String url = em.getUrl();
				if(StringUtil.isNull(url)) continue;
				String ip = StringUtil.trim(url).replaceAll(reg, "$1");//正则表达式提取IP
				boolean status = isReachable(ip);
				if(status){
					connectedDataDoor.add(em.getId());
				}
			}
	    	DataDoorCode.connectedDataDoor = connectedDataDoor;
    	} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
    }
	
    /**
	 * 判断IP是否连通
	 * @param ipAddress
	 * @return
	 */
	public static boolean isReachable(String ipAddress){
		try {
			return InetAddress.getByName(ipAddress).isReachable(2500);
		} catch (IOException e) {
			return false;
		}
	}
	
	/**
	 * ping命令
	 * @param ipAddress IP地址
	 * @param pingTimes 次数
	 * @return
	 */
	public static boolean pings(String ipAddress, int pingTimes) {  
        BufferedReader in = null;
        int timeOut = 3000;
        Runtime r = Runtime.getRuntime();  // 将要执行的ping命令,此命令是windows格式的命令  
        String pingCommand = "ping " + ipAddress + " -n " + pingTimes    + " -w " + timeOut;  
        try {   // 执行命令并获取输出  
            Process p = r.exec(pingCommand);   
            if (p == null) {    
                return false;   
            }
            in = new BufferedReader(new InputStreamReader(p.getInputStream()));// 逐行检查输出,计算类似出现=23ms TTL=62字样的次数  
            int connectedCount = 0;   
            String line = null;   
            while ((line = in.readLine()) != null) {    
                connectedCount += getCheckResult(line);   
            }   // 如果出现类似=23ms TTL=62这样的字样,出现的次数=测试次数则返回真  
            return connectedCount == pingTimes;  
        } catch (Exception ex) {   
        	logger.error(ex.getMessage(), ex);// 出现异常则返回假  
            return false;  
        } finally {   
            try { in.close(); } catch (IOException e) {}  
        }
    }
    //若line含有=18ms TTL=16字样,说明已经ping通,返回1,否則返回0.
    private static int getCheckResult(String line) {  // System.out.println("控制台输出的结果为:"+line);  
        Pattern pattern = Pattern.compile("(\\d+ms)(\\s+)(TTL=\\d+)",    Pattern.CASE_INSENSITIVE);  
        Matcher matcher = pattern.matcher(line);  
        while (matcher.find()) {
            return 1;
        }
        return 0; 
    }
}  