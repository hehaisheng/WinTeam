package com.shawnway.nav.app.wtw.tool;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Administrator on 2016/5/3 0003.
 */
public class TimeUtils {

    public static long dataOne(String time) {
        //2016-05-03T17:47:40.000+08:00
        time = time.trim();
        String[] strs = time.split("T");
        String[] strings = strs[1].split("'+'");
        time = strs[0].concat(" ").concat(strings[0]);
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS",
                Locale.CHINA);
        Date date;
        long times = 0;
        try {
            date = sdr.parse(time);
            long l = date.getTime();
            String stf = String.valueOf(l);
            times = Long.valueOf(stf);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return times;
    }

    public static long startTimeToLong(String time){
        if(StringUtils.isEmpty(time)){
            time = "1990-01-01";
        }else{
            time = time.concat(" 00:00:00.000");
        }
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS",
                Locale.CHINA);
        Date date;
        long times = 0;
        try {
            date = sdr.parse(time);
            long l = date.getTime();
            String stf = String.valueOf(l);
            times = Long.valueOf(stf);
        }catch (ParseException e){
            e.printStackTrace();
        }
        return times;
    }

    public static long endTimeToLong(String time){
        if(StringUtils.isEmpty(time)){
            time = "1990-01-01";
        }else{
            time = time.concat(" 23:59:59.999");
        }
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS",
                Locale.CHINA);
        Date date;
        long times = 0;
        try {
            date = sdr.parse(time);
            long l = date.getTime();
            String stf = String.valueOf(l);
            times = Long.valueOf(stf);
        }catch (ParseException e){
            e.printStackTrace();
        }
        return times;
    }

    public static String dateDay(long time){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd",Locale.CHINA);
        String day = format.format(time);
        return day;
    }

    public static String dateChartDay(long time){
        SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm",Locale.CHINA);
        String day = format.format(time);
        return day;
    }

    public static String YMDToDate(long time){
        SimpleDateFormat format = new SimpleDateFormat("MM-dd",Locale.CHINA);
        return format.format(time);
    }

    public static String dateYMDHM(long time){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm",Locale.CHINA);
        String day = format.format(time);
        return day;
    }

    /**
     * 在分时图中计算间隔时间
     * @param time 开始时间
     * @param n 间隔时间
     * @return 间隔n个单位后的间隔时间
     */
    public static String timeAddTwoHour(String time,int n){
        String header = time.substring(0,2);
        String end = time.substring(2);
        int tou = 0;
        if(header.substring(0,1).equals("0")){
            tou = Integer.parseInt(header.substring(1,2));
            tou = tou + n;
        }else{
            tou = Integer.parseInt(header);
            tou = tou + n;
        }
        while(tou>=24){
            tou -=24;
        }

        if(tou<10){
            return "0"+tou+end;
        }else{
            return tou+end;
        }
    }

    public static String timeAddTwoMinute(String time,int n){
        String header = time.substring(0,2);
        String end = time.substring(2);
        int tou = 0;
        if(header.substring(0,1).equals("0")){
            tou = Integer.parseInt(header.substring(1,2));
            tou = tou + n;
        }else{
            tou = Integer.parseInt(header);
            tou = tou + n;
        }
        while(tou>=60){
            tou -=60;
        }
        if(tou<10){
            return "0"+tou+end;
        }else{
            return tou+end;
        }
    }

}
