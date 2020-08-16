package com.tdkj.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.apache.commons.lang3.time.DateUtils;
/**
 * Created by len on 2017-12-29.
 */
public class DateUtil {

    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    //可能的时间格式
    private static final String[] format = {" HH:mm:ss", "yyyy-MM-dd", "yyyyMMdd", "yyyy/MM/dd"};
    //时间匹配正则表达式
    private static final String[] dateRegex = {"^[\\d]{4}+[-]+[\\d]{1,2}+[-]+[\\d]{1,2}$", "\\d{8}", "^[\\d]{4}+[/]+[\\d]{1,2}+[/]+[\\d]{1,2}$"};
    //时间前后分割
    private static String space = "[A-Za-z\\s.]+"; //字母，空格，小数点

    private static String toTwo(String value) {
        return String.format("%02d", Integer.parseInt(value));
    }
    /**
     * 转换时间格式
     * [A-Za-z.]+ 排除其他异常输入格式
     */
    public static Date parseDate(String date) {
        Date value = null;
        if (date != null && !date.matches("[A-Za-z.]+")) {
            try {
                date = date.split("[A-Za-z.]+")[0].trim();
                String pre = date.split("[\\s]+")[0];
                if (pre.matches(dateRegex[0])) {
                    value = DateUtils.parseDate(date, format[1], format[1] + format[0]);
                } else if (pre.matches(dateRegex[1])) {
                    value = DateUtils.parseDate(date, format[2], format[2] + format[0]);
                } else if (pre.matches(dateRegex[2])) {
                    value = DateUtils.parseDate(date, format[3], format[3] + format[0]);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return value;
    }




    /**
     * 将日期转换为字符串
     *
     * @param date   DATE日期
     * @param format 转换格式
     * @return 字符串日期
     */
    public static String formatDate(Date date, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(date);
    }

    /**
     * 将日期转换成yyyy-MM-dd格式的字符串
     * @param date
     * @return
     */
    public static String formatDate(Date date){
        if(date==null){
            return null;
        }
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }


    /**
     * 将日期转换成年-月-日 时:分:秒格式的字符串
     * @param date
     * @return
     */
    public static String formatDateTime(Date date){
        if(date==null){
            return null;
        }
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

    /**
     * 将日期转换成年月日时分秒格式的字符串
     * @param date
     * @return
     */
    public static String formatDateTime2(Date date){
        if(date==null){
            return null;
        }
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(date);
    }

    /**
     * 将日期转换成年月日格式的字符串
     * @param date
     * @return
     */
    public static String formatDateTime3(Date date){
        if(date==null){
            return null;
        }
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
        return sdf.format(date);
    }


    /**
     * 将格式为yyyy-MM-dd的字符串转换成日期
     * @param
     * @return
     * @throws Exception
     */
    public static Date parseDate2(String strDate) throws Exception{
        if(strDate==null){
            return null;
        }
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        return sdf.parse(strDate);
    }


    public static Date parseDate(String strDate,String format) throws Exception{
        if(strDate==null){
            return null;
        }
        SimpleDateFormat sdf=new SimpleDateFormat(format);
        return sdf.parse(strDate);
    }


    /**
     * 将格式为年-月-日 时:分:秒格式的字符串转换成日期
     * @param strDate
     * @return
     * @throws Exception
     */
    public static Date parseDateTime(String strDate) throws Exception{
        if(strDate==null){
            return null;
        }
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.parse(strDate);
    }


    /**
     * 将字符串日期转换成yyyyMM的形式，strDate格式必须"yyyy-MM-dd"。
     * @param strDate
     * @return
     * @throws Exception
     */
    public static Integer getYM(String strDate) throws Exception{
        if(strDate==null){
            return null;
        }
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Date date=sdf.parse(strDate);
        return getYM(date);
    }


    /**
     * 将日期转换成yyyyMM的形式
     * @param date
     * @return
     */
    public static Integer getYM(Date date){
        if(date==null){
            return null;
        }
        Calendar cal=Calendar.getInstance();
        cal.setTime(date);
        int yearno=cal.get(Calendar.YEAR);
        int monthno=cal.get(Calendar.MONTH)+1;
        return new Integer(yearno*100+monthno);
    }

    /**
     * 获取某天是星期几
     * @param date
     * @return
     */
    public static String getWeekDayString(Date date) {
        String weekDay = "";
        final String[] dayNames = {"星期日","星期一","星期二","星期三","星期四","星期五","星期六"};
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int dayofweek = c.get(Calendar.DAY_OF_WEEK);
        weekDay = dayNames[dayofweek-1];
        return weekDay;

    }

    /**
     * 获取当月的第一天
     * @param date
     * @return
     */
    public static String getFirstDay(Date date) {
        Calendar cal=Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DATE, 1);
        return formatDate(cal.getTime());
    }

    /**
     * 获取当月的最后一天
     * @param date
     * @return
     */
    public static String getLatDay(Date date) {
        Calendar cal=Calendar.getInstance();
        cal.add(Calendar.MONTH, 1);
        cal.add(Calendar.DATE, -1);
        return formatDate(cal.getTime());
    }


    /**
     * 年月往前往后变化几个月
     * @param date
     * @param i
     * @return
     */

    public static Integer addMonth(Date date,int i) {
        if(date==null){
            return null;
        }
        Calendar cal=Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH,i);
        int yearno=cal.get(Calendar.YEAR);
        int monthno=cal.get(Calendar.MONTH)+1;
        return new Integer(yearno*100+monthno);
    }

    /**
     * 年月往前往后变化几个月
     * @param idate
     * @param i
     * @return
     * @throws Exception
     */

    public static Integer addMonth(Integer idate,int i) throws Exception{
        if(idate==null){
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date date = sdf.parse(idate+"01");
        return addMonth(date,i);

    }
    /**
     * 获取当前时间的前一周
     * @return
     */
    public static String getday(){
        Calendar cal1 = Calendar.getInstance();
        cal1.set(Calendar.DAY_OF_YEAR, cal1.get(Calendar.DAY_OF_YEAR)-7);
        Date tranenddate = cal1.getTime();
        String date=DateUtil.formatDate(tranenddate);
        return date;
    }

    /**
     * 将yyyyMMdd格式的字符串转换成yyyy-MM-dd日期类型
     * @param ymd yyyyMMdd格式的字符串
     * @return
     * @throws Exception
     */
    public static Date praseYMDToDate(String ymd) throws Exception{
        String yyyy=ymd.substring(0,4);
        String mm=ymd.substring(4,6);
        String dd=ymd.substring(6,8);
        ymd=yyyy+"-"+mm+"-"+dd;
        Date date=parseDate(ymd);
        return date;
    }

    /**
     * 日期相减获得间隔月数
     * @param mindate
     * @param maxdate
     * @return
     */
    public static int getMonthNum(Date mindate,Date maxdate) {
        Calendar cal1= Calendar.getInstance();
        cal1.setTime(mindate);
        Calendar cal2=Calendar.getInstance();
        cal2.setTime(maxdate);
        return (cal2.get(1)-cal1.get(1))*12+(cal2.get(2)-cal1.get(2));
    }
    /**
     * 比较两个String型日期格式大小
     * @param
     * @throws Exception
     */
    public static boolean compare(String var1,String var2){
        boolean flag=false;
        if(!var1.equals("")&&!var2.equals("")){
            try {
                Date begin_date=DateUtil.parseDate(var1);
                Date end_date=DateUtil.parseDate(var2);
                if(begin_date.after(end_date)){
                    flag=true;
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return flag;
    }

    public static void main(String[] agrs) throws Exception {
//		Date date1 = parseDate("2011-01-01");
//		Calendar cal=Calendar.getInstance();
//		cal.setTime(date1);
//		int yearno=cal.get(Calendar.YEAR);
//		int monthno=cal.get(Calendar.MONTH)+1;
//		cal.add(Calendar.MONTH, 2);
//		cal.add(Calendar.DATE, 3);
//		Date date2 = new Date(cal.getTimeInMillis());
//		System.out.println(formatDate(date2));
//
//		System.out.println(getWeekDayString(date2));
//		System.out.println(getWeekDayString(new Date()));
//
//		//本月第一天
//		Date date3 = new Date();
//		cal.setTime(date3);
//		cal.set(Calendar.DATE, 1);
//		System.out.println(formatDate(cal.getTime()));
//
//		//本月最后一天
//		cal.add(Calendar.MONTH, 1);
//		cal.add(Calendar.DATE, -1);
//		System.out.println(formatDate(cal.getTime()));
        String d1 = "20190601";
        String d2 = "20190601   12:12:12";
        String d3 = "2019-12-40";
        String d4 = "20190601T12:12:12 ww 44.58&99#00";
        String d5 = "2019/06/03";
        String d6 = "2019/6/3";
        String d7 = "2019-6-3";
        String reg1 = "\\d{8}";
        String reg2 = "[0-9]+";

        System.out.println(DateUtil.parseDate(d1));
        System.out.println(DateUtil.parseDate(d2));
        System.out.println(DateUtil.parseDate(d4));
        System.out.println(DateUtil.parseDate(d3));
        System.out.println(DateUtil.parseDate(d5));
        System.out.println(DateUtil.parseDate(d6));
        System.out.println(DateUtil.parseDate(d7));
        System.out.println(d4.matches("[A-Za-z.]+"));

        Date date1 =praseYMDToDate("20100501");
        Date date2 =praseYMDToDate("20100601");
        System.out.println(getMonthNum(date2, date1));
    }




}
