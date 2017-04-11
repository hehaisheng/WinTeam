package com.shawnway.nav.app.wtw.tool;

/**
 * Created by Cicinnus on 2016/12/12.
 * 微盘的返回参数对应的实际意义
 */

public class WpUtils {


    /**
     * 单子方向，下单类型
     * @param type type
     * @return
     */
    public static String getTradeType(int type) {
        String tradeType = "";
        switch (type) {
            case 1:
                tradeType = "买涨";
                break;
            case 2:
                tradeType = "买跌";
                break;
        }
        return tradeType;
    }

    /**
     * 获取平仓类型
     * @param type 类型
     * @return
     */
    public static String getLiquiDateType(int type){
        String liquiDateType = "";
        switch (type){
            case 1:
                liquiDateType = "爆仓";
                break;
            case 2:
                liquiDateType = "客户手动平仓";
                break;
            case 3:
                liquiDateType = "止盈平仓";
                break;
            case 4:
                liquiDateType = "止损平仓";
                break;
            case 5:
                liquiDateType = "结算平仓";
                break;
        }
        return liquiDateType;
    }


}
