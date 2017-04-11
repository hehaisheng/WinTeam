package com.shawnway.nav.app.wtw.tool;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/27 0027.
 */
public class NumberUtils {

    public static boolean isHundredNum(String number){
        int num = Integer.valueOf(number);
        return ((num % 100 == 0) && (num / 100 >= 1));
    }

    public static boolean isHandredNum(int number){
        return ((number % 100 == 0) && (number / 100 >= 1));
    }

    public static boolean isNotHundredNum(String number){
        return !isHundredNum(number);
    }

    public static boolean isNotHundredNum(int number){
        return !isHandredNum(number);
    }

    /**
     * 求一个集合中的平均值
     * @return
     */
    public static float avOfCollection(List<String> data){
        int count = data.size();
        float sum = 0f;
        for(int i = 0;i<data.size();i++){
            sum += Float.parseFloat(data.get(i));
        }
        return sum/count;
    }

    public static String bytes2kb(long bytes) {
        BigDecimal filesize = new BigDecimal(bytes);
        BigDecimal megabyte = new BigDecimal(1024 * 1024);
        float returnValue = filesize.divide(megabyte, 2, BigDecimal.ROUND_UP)
                .floatValue();
        if (returnValue > 1)
            return (returnValue + "MB");
        BigDecimal kilobyte = new BigDecimal(1024);
        returnValue = filesize.divide(kilobyte, 2, BigDecimal.ROUND_UP)
                .floatValue();
        return (returnValue + "KB");
    }
}
