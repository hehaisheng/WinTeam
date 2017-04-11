package com.shawnway.nav.app.wtw.net;

import com.shawnway.nav.app.wtw.BuildConfig;
import com.shawnway.nav.app.wtw.base.BaseWpResult;
import com.shawnway.nav.app.wtw.bean.QuotationsWPBean;
import com.shawnway.nav.app.wtw.bean.RefreshTokenResult;
import com.shawnway.nav.app.wtw.bean.SendSmsCodeResult;
import com.shawnway.nav.app.wtw.bean.WpKlineBean;
import com.shawnway.nav.app.wtw.bean.WpTimeLineBean;
import com.shawnway.nav.app.wtw.module.quotation.wp.WPLoginResult;
import com.shawnway.nav.app.wtw.module.quotation.wp.wp_detail.tran.bean.BuildTranBean;
import com.shawnway.nav.app.wtw.module.quotation.wp.wp_detail.tran.bean.NotLiquidateOrder;
import com.shawnway.nav.app.wtw.module.quotation.wp.wp_detail.tran.bean.TranGoodsWrapper;
import com.shawnway.nav.app.wtw.module.quotation.wp.wp_detail.tran.bean.UserAccountBean;
import com.shawnway.nav.app.wtw.module.quotation.wp.wp_detail.tran.bean.UserBalanceBean;
import com.shawnway.nav.app.wtw.module.quotation.wp.wp_detail.tran.bean.WPStateDescWrapper;
import com.shawnway.nav.app.wtw.module.quotation.wp.wp_detail.wp_money_history.WpMoneyHistoryBean;
import com.shawnway.nav.app.wtw.module.quotation.wp.wp_detail.wp_positions.WpPositionsBean;
import com.shawnway.nav.app.wtw.module.quotation.wp.wp_detail.wp_trade_history.WpTradeHistoryBean;
import com.shawnway.nav.app.wtw.module.quotation.wp.wp_detail.wp_trade_history.WpTradeTotalBean;
import com.shawnway.nav.app.wtw.module.user.ticket.wp_ticket.WpTicketBean;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Cicinnus on 2016/12/7.
 * 粤国际微盘接口
 */

public interface WpApi {
  //#define Base154_URL @"http://120.76.246.29"//生产环境
 // #define Base154_URL @"http://120.76.153.154:9088"//测试环境
   String BASE_URL = "https://vpan20.opensdk.zfu188.com";
  //String BASE_URL = "http://120.76.246.29";
//    String BASE_URL = "http://dev.vpan20.opensdk.zfu188.com";

    String APPID = "yingtianxia";
   String url = BuildConfig.DEBUG ? "http://dev.vpan20.opensdk.zfu188.com" : BASE_URL;

 // String url =BASE_URL;

    //微盘转账充值URL
    String URL_RECHARGE = url + "/api/recharge/index?access_token=";

    //登录微盘
    @POST("/api/oauth/token")
    Observable<Response<WPLoginResult>> loginWP(@Query("client_id") String clientId,
                                                @Query("client_secret") String app_secure,
                                                @Query("grant_type") String grant_type,
                                                @Query("appId") String yingtianxia,
                                                @Query("username") String userName,
                                                @Query("password") String password);

    //刷新微盘token
    @POST("/api/oauth/token")
    Observable<Response<RefreshTokenResult>> refreshToken(@Query("client_id") String client_id,
                                                          @Query("client_secret") String client_secret,
                                                          @Query("grant_type") String grant_type,
                                                          @Query("refresh_token") String refreshToken);


    //发送微盘验证码
    @GET("api/registered/user/checkUserMobileExisting/yingtianxia/{phone_num}")
    Observable<SendSmsCodeResult> sendWPCode(@Path("phone_num") String phone);

    //注册微盘
    @PUT("/api/registered/user/{mobile}/{password}/" + APPID + "/{code}")
    Observable<BaseWpResult> register(@HeaderMap Map<String, String> headers,
                                      @Path("mobile") String mobile,
                                      @Path("password") String tradePassword,
                                      @Path("code") String verifyCode,
                                      @Body RequestBody requestBody);

    //重置微盘密码-发送验证码
    @GET("/api/passwordreset/sendVerifyCode/{mobile}/" + APPID)
    Observable<BaseWpResult> sendResetPwdCode(@Path("mobile") String mobile);

    //重置密码
    @GET("/api/passwordreset/doReset/" + APPID + "/{mobile}/{verifyCode}/{newPassword}")
    Observable<BaseWpResult> resetWpPwd(@Path("mobile") String phone,
                                        @Path("verifyCode") String code,
                                        @Path("newPassword") String newPWd);

    //修改微盘密码
    @GET("/api/vpanuser/doReset/" + APPID + "/{oldPassword}/{newPassword}")
    Observable<BaseWpResult> updateWpPwd(@Path("oldPassword") String oldPwd,
                                         @Path("newPassword") String newPwd,
                                         @Query("access_token") String token);


    //获取赢家券
    @GET("/api/Securities/selectDetailUser/1")
    Observable<Response<WpTicketBean>> getTicket(@Query("pageno") int pageNo,
                                                 @Query("pagesize") int pageSize,
                                                 @Query("access_token") String access_token);


    //获取用户实时盈亏和当前仓位
    @GET("/api/order/getNotLiquidateOrder/total")
    Observable<NotLiquidateOrder> getNotLiquidateOrder(@Query("access_token") String access_token);

    //获取微盘产品列表
    @GET("/api/order/getallprds")
    Observable<TranGoodsWrapper> getTranGoods();

    //获取用户赢家券
    @GET("/api/Securities/selectDetailUser/{type}")
    Observable<WpTicketBean> getUserTicket(@Path("type") int type,
                                           @Query("pageno") int pageNo,
                                           @Query("pagesize") int pageSize,
                                           @Query("access_token") String access_token);

    //获取用户资金信息
    @GET("api/vpanuser/findUserAccountBalance")
    Observable<UserAccountBean> getUserAccountBalance(@Query("access_token") String access_token);

    //获取微盘产品最新报价
    @GET("http://vpan20.batchjob.zfu188.com/batch-server/price/quote")
    Observable<QuotationsWPBean[]> getWPPrice();

    //建仓
    @GET("/api/order/create/{productId}/{tradeType}/{amount}")
    Observable<BuildTranBean> createOrder(@Path("productId") int productId,
                                          @Path("tradeType") int tradeType,
                                          @Path("amount") int amount,
                                          @Query("targetProfit") double targetProfit,
                                          @Query("stopLoss") double stopLoss,
                                          @Query("useTicket") int useTicket,
                                          @Query("access_token") String access_token);

    //平仓
    @GET("/api/order/liquidate/{orderId}")
    Observable<BaseWpResult> liquidate(@Path("orderId") String orderId,
                                       @Query("access_token") String token);

    //意见平仓
    @GET("/api/order/liquidateall")
    Observable<BaseWpResult> liquidateAll(@Query("access_token") String token);

    //获取持仓列表
    @GET("/api/order/getNotLiquidateOrder/list")
    Observable<WpPositionsBean> getWpPositions(@Query("access_token") String token);

    //获取收支明细
    @GET("/api/trade/record/findAcctMoneyLog/{page}")
    Observable<WpMoneyHistoryBean> getMoneyHistory(@Path("page") int page,
                                                   @Query("access_token") String token);

    //获取交易明细
    @GET("/api/trade/record/historyOrder/{pageno}/{pagesize}")
    Observable<WpTradeHistoryBean> getWpTradeHistory(@Path("pageno") int pageNum,
                                                     @Path("pagesize") int pageSize,
                                                     @Query("access_token") String token);

    //获取交易明细总计
    @GET("/api/trade/record/historyOrderStatistics")
    Observable<WpTradeTotalBean> getWpTradeTotal(@Query("access_token") String token);

    //获取用户绑定的银行卡信息
    @GET("/api/usercard/findCardByUserid")
    Observable<UserBalanceBean> getUserBalanceInfo(@Query("access_token") String access_token);

    //微盘提现
    @Headers("Content-Type: application/json;charset=UTF-8")
    @PUT("/api/withdraw/create")
    Observable<WPStateDescWrapper> putWithdrawTran(@Query("access_token") String access_token, @Body RequestBody body);

    //微盘K线
    @POST("http://vpan20.batchjob.zfu188.com/batch-server/price/kliner")
    Observable<WpKlineBean> getWpKLine(@Query("type") int type,
                                       @Query("product") String product);

    //微盘分时图
    @POST("https://vpan20.batchjob.zfu188.com/batch-server/price/kliner")
    Observable<WpTimeLineBean> getTimeLine(@Query("type") int type,
                                           @Query("product") String product);

    //解绑银行卡
    @POST("/api/vpanuser/unBindCard/{tradepasswrd}")
    Observable<WPStateDescWrapper> unBundBankCard(@Path("tradepasswrd") String tradepassword,
                                                  @Query("access_token") String access_token);

}
