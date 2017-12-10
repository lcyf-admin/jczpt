package cn.lhkj.project.export;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;

import cn.lhkj.project.person.entity.Person;



/**
 * 导出Excel工具类（优化版）
 * 原来的ExportUtil在导出Excel后打不开文件，提示“您尝试打开的文件xxx的格式与文件扩展名指定的格式不一致。打开文件前请验证文件没有损坏且来源可信”。这次修复了这个问题。
 * @author 
 * @date 
 * @version 
 * @since 
 */
public class ExportUtil3 {
	
	
	@SuppressWarnings("unchecked")
	public static void Export(HttpServletRequest req, HttpServletResponse resp) {
	    	try {
	    		//获取当前时间给xls命名
	    		long l = System.currentTimeMillis();
	    		Date date = new Date(l);
	    		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
	    		//System.out.println(dateFormat.format(date));
	    		
	    		String exportType = (String)req.getAttribute("exportType");
		        if(exportType ==null || exportType.length()<=0){
		        	exportType = "xls";}
		        String fileName = dateFormat.format(date);
		        List<Map<String, Object>> resultMap =  (List<Map<String, Object>>)req.getAttribute("__exportList");
		        OutputStream os = resp.getOutputStream();
		        String colName = "姓名,年龄,民族,性别,采集设备编号,采集时间,身份证号,身份证地址信息,身份证有效期开始,身份证有效期结束,位置信息,违禁物品名称";
		        if("xls".equals(exportType)){       
		            resp.setContentType("application/x-msdownload");
		            resp.setHeader("Content-Disposition","attachment;filename=" + fileName + ".xls");
		            ExportXls(resultMap,os,colName);
		        } else {
		            resp.setContentType("application/x-msdownload");
		            resp.setHeader("Content-Disposition","attachment;filename=" + fileName + ".pdf");
		           // ExportPdf(list,os,colId,colName);
		        }
	    	} catch(Exception e) {
	    		e.printStackTrace();
	    	}
	    }
		
		@SuppressWarnings("deprecation")
		public static void ExportXls(List<Map<String, Object>> resultMap, OutputStream os, String colName) {
//			String colName = "地区,接收总条数,未核查条数,已核查条数";
			//2003版本  
	        Workbook wb = new HSSFWorkbook();
	        HSSFSheet sheet0 = (HSSFSheet) wb.createSheet("sheet0");
	        
	        HSSFRow row = sheet0.createRow(0);
	        row.setHeightInPoints(30); //行高
	        HSSFCell cell = null;
	        CellStyle style = wb.createCellStyle();
	        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); //水平居中
	        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); //垂直居中   
	        style.setWrapText(true); //自动换行     
	        org.apache.poi.ss.usermodel.Font font = wb.createFont();
	        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); 
	        style.setFont(font); //加粗
	        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);   //下边框
	        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);     //左边框
	        style.setBorderTop(HSSFCellStyle.BORDER_THIN);      //上边框
	        style.setBorderRight(HSSFCellStyle.BORDER_THIN);    //右边框
	        
	        // 设置最长的宽度（最长300像素/42）
	        List<int[]> widths = new ArrayList<int[]>();
	        String[] cName = colName.split(",");
	        for(int index = 0, len = cName.length; index < len; index++) {
	            cell = row.createCell(index);
	            cell.setCellStyle(style);
	            cell.setCellValue(cName[index]);            
	            widths.add(index, new int[] {index, cName[index].getBytes().length});
	        }

	     // 第五步，写入实体数据 实际应用中这些数据从数据库得到
	        
	        for (int i = 0; i < resultMap.size(); i++)  
	        {  
	        	
	            row = sheet0.createRow((int) i + 1); 
	            // 第四步，创建单元格，并设置值  
	            Person p = (Person) resultMap.get(i);
	            //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	            
	            row.createCell((short) 0).setCellValue( (!" ".equals(p.getNames()))?(p.getNames()):("") ); 
	            row.createCell((short) 1).setCellValue( (!" ".equals(p.getAge()))?(p.getAge()):("") ); 
	            row.createCell((short) 2).setCellValue( (!" ".equals(p.getNation()))?(p.getNation()):("") ); 
	            row.createCell((short) 3).setCellValue( (!" ".equals(p.getGender()))?(p.getGender()):("") );
	            //row.createCell((short) 4).setCellValue( (!" ".equals(sdf.format(p.getBirth()))?(sdf.format(p.getBirth())):("")) );
	            row.createCell((short) 4).setCellValue( (!" ".equals(p.getEquipmentId()))?(p.getEquipmentId()):("") ); 
	            row.createCell((short) 5).setCellValue( (!" ".equals(p.getCaptureTimeView()))?(p.getCaptureTimeView()):("") ); 
	            row.createCell((short) 6).setCellValue( (!" ".equals(p.getIdcard()))?(p.getIdcard()):("") );
	            row.createCell((short) 7).setCellValue( (!" ".equals(p.getAddress()))?(p.getAddress()):("") ); 
	            row.createCell((short) 8).setCellValue( (!" ".equals(p.getStartDate()))?(p.getStartDate()):("") );
	            row.createCell((short) 9).setCellValue( (!" ".equals(p.getEndDate()))?(p.getEndDate()):("") ); 
	            row.createCell((short) 10).setCellValue( (!" ".equals(p.getLocation()))?(p.getLocation()):("") ); 
	            row.createCell((short) 11).setCellValue( (!"0".equals(p.getIsCheck()))?(p.getIsCheck()):("") ); 
 
	            ///////////设置单元格的宽度///////////////
	            if( null != p.getNames() && !" ".equals(p.getNames())  ){
	            	//////获取最大的宽度
	            	if(widths.get(0)[1] <= p.getNames().getBytes().length){
	            		widths.set(0, new int[] {0, p.getNames().getBytes().length});
	            	}
	            }
	            
	            if(null != p.getAge() && !" ".equals(p.getAge())){
	            	if(widths.get(1)[1] <= p.getAge().getBytes().length){
	            		widths.set(1, new int[] {1, p.getAge().getBytes().length});
	            	}
	            }
	            
	            if(null != p.getNation() && !" ".equals(p.getNation())){
	            	if(widths.get(2)[1] <= p.getNation().getBytes().length){
	            		widths.set(2, new int[] {1, p.getNation().getBytes().length});
	            	}
	            }
	            
	            if(null != p.getGender() && !" ".equals(p.getGender())){
	            	if(widths.get(3)[1] <= p.getGender().getBytes().length){
	            		widths.set(3, new int[] {3, p.getGender().getBytes().length});
	            	}
	            }

	            if(null != p.getEquipmentId() && !" ".equals(p.getEquipmentId())){
	            	if(widths.get(4)[1] <= p.getEquipmentId().getBytes().length){
	            		widths.set(4, new int[] {4, p.getEquipmentId().getBytes().length});
	            	}
	            }
	            
	            if(null != p.getCaptureTimeView() && !" ".equals(p.getCaptureTimeView())){
	            	if(widths.get(5)[1] <= p.getCaptureTimeView().getBytes().length){
	            		widths.set(5, new int[] {5, p.getCaptureTimeView().getBytes().length});
	            	}
	            }	 
	            
	            if(null != p.getIdcard() && !" ".equals(p.getIdcard())){
	            	if(widths.get(6)[1] <= p.getIdcard().getBytes().length){
	            		widths.set(6, new int[] {6, p.getIdcard().getBytes().length});
	            	}
	            }
	            
	            if(null != p.getAddress() && !" ".equals(p.getAddress())){
	            	if(widths.get(7)[1] <= p.getAddress().getBytes().length){
	            		widths.set(7, new int[] {7, p.getAddress().getBytes().length});
	            	}
	            }
	            
	            if(null != p.getStartDate() && !" ".equals(p.getStartDate())){
	            	if(widths.get(8)[1] <= p.getStartDate().getBytes().length){
	            		widths.set(8, new int[] {8, p.getStartDate().getBytes().length});
	            	}
	            }
	            
	            if(null != p.getEndDate() && !" ".equals(p.getEndDate())){
	            	if(widths.get(9)[1] <= p.getEndDate().getBytes().length){
	            		widths.set(9, new int[] {9, p.getEndDate().getBytes().length});
	            	}
	            }

	            if(null != p.getLocation() && !" ".equals(p.getLocation())){
	            	if(widths.get(10)[1] <= p.getLocation().getBytes().length){
	            		widths.set(10, new int[] {10, p.getLocation().getBytes().length});
	            	}
	            }
	            
	            if(null != p.getIsCheck() && !"0".equals(p.getIsCheck())){
	            	if(widths.get(11)[1] <= p.getIsCheck().getBytes().length){
	            		widths.set(11, new int[] {11, p.getIsCheck().getBytes().length});
	            	}
	            }
	            
	            
	            /*widths.add(i, new int[] {i, p.getNames().getBytes().length});*/
	            /*widths.add(i, new int[] {i, p.getAge().getBytes().length});*/
	            /*widths.add(i, new int[] {i, p.getNation().getBytes().length});*/
	            /*widths.add(i, new int[] {i, p.getEquipmentId().getBytes().length});*/
	            /*widths.add(i, new int[] {i, p.getCaptureTimeView().getBytes().length});*/
	            /*widths.add(i, new int[] {i, p.getAddress().getBytes().length});*/
	            /*widths.add(i, new int[] {i, p.getStartDate().getBytes().length});*/
	            /*widths.add(i, new int[] {i, p.getEndDate().getBytes().length});*/
	            /*widths.add(i, new int[] {i, p.getLocation().getBytes().length});*/
	            /*widths.add(i, new int[] {i, p.getIsCheck().getBytes().length});*/
	            
	            
	        }  
	        // 第六步，将文件存到指定位置    
	        try {          
	        	
	            // 设置单元格的宽度
	            for(int i=0, len = widths.size(); i < len; i++) {
	                int arr[] = widths.get(i);
	                //System.out.println(arr[1]);
	                arr[1] = arr[1] > 40 ? 40 : arr[1];
	                sheet0.setColumnWidth(i, (arr[1] + 2) * 256);
	            }
	        	
	        	wb.write(os);
	            //wb.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		}
}

