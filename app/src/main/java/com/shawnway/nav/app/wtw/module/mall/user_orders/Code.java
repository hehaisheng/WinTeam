package com.shawnway.nav.app.wtw.module.mall.user_orders;

/**
 * Created by Kevin on 2016/11/22
 */

public interface Code {

    //不处理
    int TRADE_STATUS_NOHANDLE = 0;
    //未发货
    int TRADE_STATUS_NOSHIPPED = 1;
    //待收货
    int TRADE_STATUS_NORECEIVED = 2;

    //不处理
    int SIGN_STATUS_NOHANDLE = 0;
    //已收货
    int SIGN_STATUS_RECEIVED = 1;


    //加载数目
    int PAGE_SIZE = 3;

    //未发货状态
    int STATUS_NOSHIPPED = 0;
    //已发货状态
    int STATUS_SHIPPED = 1;
    //已收货状态
    int STATUS_RECEVIED = 2;

    //刷新
    String CODE_REFRESH = "refresh";
    //加载
    String CODE_LOAD = "load";



    String SUCCESS_CODE = "SUCCESS";
    String DELETE_DESC = "订单已删除";
    String CONFIRM_DESC = "已确认收货";
}
