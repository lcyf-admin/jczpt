package cn.lhkj.commons.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CalendarUtil {
	
	/**
	 * 得到某个月的最后一天
	 * @param format 格式
	 * @param date
	 * @return
	 */
	@SuppressWarnings("static-access")
	public static String getLastDayOfMonth(Date date,String format){
		Calendar  calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(calendar.DAY_OF_MONTH, calendar.getActualMaximum(calendar.DAY_OF_MONTH));
		return format(calendar.getTime(),format);
	}
	
	/**
	 * 格式化日期
	 * @param format
	 * @param dt
	 * @return
	 */
	public static String format(Date date,String format){
		if(format==null || date ==null) return "";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}
	
	/**
	 * 格式化日期为"yyyy-MM-dd"
	 * @param date
	 * @return
	 */
	public static String format(Date date){
		if(date ==null) return "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(date);
	}
	
	/**
	 * 格式化日期为"yyyy-MM-dd HH:mm:ss"
	 * @param date
	 * @return
	 */
	public static String formatToHMS(Date date){
		if(date ==null) return "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}
	
	/**
	 * 格式化日期为"yyyy-MM-dd HH:mm"
	 * @param date
	 * @return
	 */
	public static String formatToHM(Date date){
		if(date ==null) return "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return sdf.format(date);
	}
	
	/**
	 * 将日期字符串按指定格式转换为日期类型
	 * @param format
	 * @param dateString
	 * @return
	 */
	public static Date toDate(String dateString,String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			return sdf.parse(dateString);
		} catch (ParseException e) {}
		return null;
	}
	
	
	/**
	 * 获取系统当前时间"yyyy-MM-dd HH:mm:ss"
	 * @return
	 */
	public static String getCurrentTime(){
		return format(new Date(),"yyyy-MM-dd HH:mm:ss" );
	}
	
	/**
	 * 获取系统当前时间"yyyy-MM-dd"
	 * @return
	 */
	public static String getCurrentDate(){
		return format(new Date(),"yyyy-MM-dd" );
	}
	
	/**
	 * 获取系统当前时间"yyyyMM"
	 * @return
	 */
	public static String getCurrentMonth(){
		return format(new Date(),"yyyyMM" );
	}
	
	/**
	 * 获取当前季度.
	 * @return
	 */
	public static int getQuarter(){
		int quarter = 1;
		int month =  getMonth();
		if(month >= 1 && month <=3) quarter = 1;
		if(month >= 4 && month <=6) quarter = 2;
		if(month >= 7 && month <=9) quarter = 3;
		if(month >= 10 && month <=12) quarter = 4;
		return quarter;
	}
	
	/**
	 * 返回java.sql.Date类型的实例
	 * @param utilDate(java.util.Date实例)
	 * @return
	 */
	public static java.sql.Date getSqlDate(Date utilDate){
		if(utilDate == null) return null;
		return new java.sql.Date(utilDate.getTime());
	}
	
	/**
	 * 返回java.util.Date类型的实例
	 * @param sqlDate(java.sql.Date实例)
	 * @return
	 */
	public static Date getUtilDate(java.sql.Date sqlDate){
		if(sqlDate == null) return null;
		return new Date(sqlDate.getTime());
	}
	
	/**
	 * 获得当前年
	 * @return
	 */
	public static int getYear(){
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.YEAR);
	}
	
	/**
	 * 获得当前月
	 * @return
	 */
	public static int getMonth(){
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.MONTH) + 1;
	}
	
	/**
	 * 获取当前的下一天,非日期直接返回
	 * @param s
	 * @return
	 */
	public static String getNextDay(String s){
		try{
			Date date = toDate(s,"yyyy-MM-dd");
			Long l = date.getTime() + 24*60*60*1000;
			Date newDate = new Date(l);
			return format(newDate);
		}catch (Exception e) {}
		return s;
	}
	
	
	/**
	 * 获取星期
	 * @param week
	 * @return
	 */
	public static String getWeekDay(int week){
		String weekString = "";
		switch(week){
			case 1:
				weekString = "周日";
				break;
			case 2:
				weekString = "周一";
				break;
			case 3:
				weekString = "周二";
				break;
			case 4:
				weekString = "周三";
				break;
			case 5:
				weekString = "周四";
				break;
			case 6:
				weekString = "周五";
				break;
			case 7:
				weekString = "周六";
				break;
			default:
				weekString = "周日";
				break;
		}
		return weekString;
	}
	
	/**
	 * 获得序号 格式：2014_04_14_2231
	 * @return
	 */
	public static String getSerial(){
		Date date = new Date();
		String result =  format(date,"yyyy_MM_dd_HHmmss" );
		return "LHKJ_"+ result;
	}
}
