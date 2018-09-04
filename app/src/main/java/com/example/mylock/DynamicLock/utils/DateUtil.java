package com.example.mylock.DynamicLock.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil{
	/**
	 * ��ȡ���������
	 * @return yyyy-MM-dd��ʽ�ĵ��������
	 */
	public static String getNowDate(){
		Date date=new Date();
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		return format.format(date);
	}
	/**
	 * ��ȡ������·�
	 * @return yyyy-MM��ʽ�ĵ��������
	 */
	public static String getMonth() {
		Date date = new Date();
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM");
		String d = format.format(date);
		return d;
	}
	/**
	 * ��ȡ��������
	 * @return yyyy-MM-dd��ʽ�ĵ��������
	 */
	public static String getNowDatedd(){
		Date date=new Date();
		SimpleDateFormat format=new SimpleDateFormat("dd");
		return format.format(date);
	}

	/**
	 * ��ȡ��ǰ��ʱ��
	 * @return HH:mm:ss��ʽ�ĵ�ǰ��24Сʱ��ʱ��
	 */
	public static String getTime(){
		Date date=new Date();
		SimpleDateFormat format=new SimpleDateFormat("HH:mm:ss");
		return format.format(date);
	}
	
	/**
	 * ��ȡ�����Сʱ
	 * @return yyyy-MM-dd��ʽ�ĵ��������
	 */
	public static String getNowDateHH(){
		Date date=new Date();
		SimpleDateFormat format=new SimpleDateFormat("HH");
		return format.format(date);
	}
	
	/**
	 * ��ȡ��ǰ�����ں�ʱ��
	 * @return yyyy-MM-dd HH:mm:ss��ʽ�ĵ�ǰ��24Сʱ�Ƶ����ں�ʱ��
	 */
	public static String getDateAndTime(){
		Date date=new Date();
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(date);
	}
	/**
	 * ��ȡ��ǰ�����ں�ʱ��
	 * @return yyyy-MM-dd HH:mm:ss.SSS��ʽ�ĵ�ǰ��24Сʱ�Ƶ����ں�ʱ��
	 */
	public static String getNowTime(){
		Date date=new Date();
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		return format.format(date);
	}
	
	/**
	 * ��ȡ��ǰ�����ڼ�
	 * @return ������ һ �� �� �� �� �� ˳��
	 */
	public static int getIntWeek() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		int week = calendar.get(Calendar.DAY_OF_WEEK);
		return week;
	}
	
	/**
	 * ��ȡ��ǰ��һ������ں�ʱ��
	 * @return yyyy-MM-dd HH:mm:ss��ʽ�ĵ�ǰ��24Сʱ�Ƶ����ں�ʱ��
	 */
	public static String getLastDay() {
		Calendar calendar = Calendar.getInstance();
		Date date = new Date();
		calendar.setTime(new Date());
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		date = calendar.getTime();
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		String d = format.format(date);
		return d;
	}
	
	/**
	 * ��ȡ��ǰ�����ڼ�
	 * @return yyyy-MM-dd + һ����ĵڼ���
	 */
	public static String getWeek() {
		Calendar calendar = Calendar.getInstance();
		Date date = new Date();
		calendar.setTime(new Date());
		date = calendar.getTime();
		int week = calendar.get(Calendar.WEEK_OF_YEAR);
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		String d = format.format(date);
		return d + "-" + week;
	}
	
	public static String getLastMonth() {
		Calendar calendar = Calendar.getInstance();
		Date date = new Date();
		calendar.setTime(new Date());
		calendar.add(Calendar.MONTH, -1);
		date = calendar.getTime();
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM");
		String d = format.format(date);
		return d;
	}
	
	/**  
     * ������������֮����������  
     * @param smdate ��С��ʱ�� 
     * @param bdate  �ϴ��ʱ�� 
     * @return ������� 
     * @throws ParseException  
     */    
    public static int daysBetween(Date smdate,Date bdate) throws ParseException    {    
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
        smdate=sdf.parse(sdf.format(smdate));  
        bdate=sdf.parse(sdf.format(bdate));  
        Calendar cal = Calendar.getInstance();    
        cal.setTime(smdate);    
        long time1 = cal.getTimeInMillis();                 
        cal.setTime(bdate);    
        long time2 = cal.getTimeInMillis();         
        long between_days=(time2-time1)/(1000*3600*24);  
            
        return Integer.parseInt(String.valueOf(between_days));           
    }
    
    /**  
     * ������������֮������ʱ��  
     * @param smdate ��С��ʱ�� 
     * @param bdate  �ϴ��ʱ�� 
     * @return �����
     */    
    public static int TimesBetween(String smdate,String bdate){    
    	
    	Date d1 = strToDate("yyyy-MM-dd HH:mm:ss",smdate);
    	Date d2 = strToDate("yyyy-MM-dd HH:mm:ss",bdate);
    	Calendar cal = Calendar.getInstance();    
        cal.setTime(d1);    
        long time1 = cal.getTimeInMillis();                 
        cal.setTime(d2);    
        long time2 = cal.getTimeInMillis();      
        long between_sss=(time2-time1)/1000;  
        return Integer.parseInt(String.valueOf(between_sss));                
    }
    
    /**  
     * ������������֮�����ĺ��� 
     * @param smdate ��С��ʱ�� 
     * @param bdate  �ϴ��ʱ�� 
     * @return ������� 
     * @throws ParseException  
     */    
    public static int longBetween(String smdate,String bdate) throws ParseException    {    
    	
    	Date d1 = strToDate("yyyy-MM-dd HH:mm:ss.SSS",smdate);
    	Date d2 = strToDate("yyyy-MM-dd HH:mm:ss.SSS",bdate);
    	Calendar cal = Calendar.getInstance();    
        cal.setTime(d1);    
        long time1 = cal.getTimeInMillis();                 
        cal.setTime(d2);    
        long time2 = cal.getTimeInMillis();      
        long between_sss=(time2-time1);  
        return Integer.parseInt(String.valueOf(between_sss));           
    }
    
    /**  
     * �ַ���ת��ʱ������
     * @return ʱ������
     */    
	public static Date strToDate(String style,String mon) {  
	    SimpleDateFormat formatter = new SimpleDateFormat(style);  
	    try {  
	        return formatter.parse(mon);  
	    } catch (Exception e) {  
	        e.printStackTrace();  
	        return new Date();  
	    }  
	}  
}
