package com.shawnway.nav.app.wtw.net;

import com.shawnway.nav.app.wtw.base.BaseWpResult;
import com.shawnway.nav.app.wtw.bean.QuotationsWPBean;
import com.shawnway.nav.app.wtw.bean.RefreshTokenResult;
import com.shawnway.nav.app.wtw.bean.SendSmsCodeResult;
import com.shawnway.nav.app.wtw.bean.WpKlineBean;
import com.shawnway.nav.app.wtw.bean.WpTimeLineBean;
import com.shawnway.nav.app.wtw.module.quotation.jl_wp.jl_wp_detail.jl_wp_money_history.JlWpMoneyHistoryBean;
import com.shawnway.nav.app.wtw.module.quotation.jl_wp.jl_wp_detail.jl_wp_positions.JlWpPositionsBean;
import com.shawnway.nav.app.wtw.module.quotation.jl_wp.jl_wp_detail.jl_wp_trade_history.JlWpTradeHistoryBean;
import com.shawnway.nav.app.wtw.module.quotation.jl_wp.jl_wp_detail.tran.transfer.JlWithDrawBean;
import com.shawnway.nav.app.wtw.module.quotation.jl_wp.jl_wp_detail.tran.transfer.QrCodeChargeBean;
import com.shawnway.nav.app.wtw.module.quotation.wp.WPLoginResult;
import com.shawnway.nav.app.wtw.module.quotation.wp.wp_detail.tran.bean.BuildTranBean;
import com.shawnway.nav.app.wtw.module.quotation.wp.wp_detail.tran.bean.NotLiquidateOrder;
import com.shawnway.nav.app.wtw.module.quotation.wp.wp_detail.tran.bean.TranGoodsWrapper;
import com.shawnway.nav.app.wtw.module.quotation.wp.wp_detail.tran.bean.UserAccountBean;
import com.shawnway.nav.app.wtw.module.quotation.wp.wp_detail.tran.bean.WPStateDescWrapper;
import com.shawnway.nav.app.wtw.module.user.ticket.jl_wp_ticket.JlWpTicketBean;

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
 * Created by Cicinnus on 2016/12/28.
 * 吉交所微盘接口
 */

public interface JlWpApi {
    String BASE_URL = "http://thor.opensdk.jlmmex.com";


    String APPID = "038";


    //微盘转账充值URL
    String URL_RECHARGE = BASE_URL + "/api/recharge/index?access_token=";

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
    @GET("/api/registered/user/checkUserMobileExisting/" + APPID + "/{phone_num}")
    Observable<SendSmsCodeResult> sendWPCode(@Path("phone_num") String phone,
                                             @Query("token") int token,
                                             @Query("resultCode") String resultCode,
                                             @Query("msgType") String msgType);

    //注册微盘
    @POST("/api/registered/user/{mobile}/{password}/" + APPID + "/{code}")
    Observable<BaseWpResult> register(@HeaderMap Map<String, String> headers,
                                      @Path("mobile") String mobile,
                                      @Path("password") String tradePassword,
                                      @Path("code") String verifyCode,
                                      @Query("nickname") String nickName);

    //修改微盘密码
    @GET("/api/doReset/" + APPID + "/{phone}/{verifyCode}/{newPassword}")
    Observable<BaseWpResult> updateJlWpPwd(@Path("phone") String phone,
                                           @Path("verifyCode") String oldPwd,
                                           @Path("newPassword") String newPwd);

    //重置密码发送短信
    @GET("/api/sendVerifyCode/{mobile}/" + APPID)
    Observable<BaseWpResult> sendForgetPwdCode(@Path("mobile") String phone,
                                               @Query("token") int token,
                                               @Query("resultCode") String resultCode,
                                               @Query("msgType") String type);

    //获取赢家券
    @GET("/api/winnerTicket/selectDetailUser")
    Observable<Response<JlWpTicketBean>> getTicket(@Query("pageno") int pageNo,
                                                   @Query("pagesize") int pageSize,
                                                   @Query("selectType") int type,
                                                   @Query("access_token") String access_token);


    //获取用户实时盈亏和当前仓位
    @GET("/api/order/getNotLiquidateOrder/total")
    Observable<NotLiquidateOrder> getNotLiquidateOrder(@Query("access_token") String access_token);

    //获取微盘产品列表
    @GET("/api/order/getallprds")
    Observable<TranGoodsWrapper> getTranGoods();


    //获取用户资金信息
    @GET("api/vpanuser/findUserAccountBalance")
    Observable<UserAccountBean> getUserAccountBalance(@Query("access_token") String access_token);

    //获取微盘产品最新报价
    @GET("http://thor.price.jlmmex.com/price-server/price/quote")
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

    //一键平仓
    @GET("/api/order/liquidateall")
    Observable<BaseWpResult> liquidateAll(@Query("access_token") String token);

    //获取持仓列表
    @GET("/api/order/getNotLiquidateOrder/list")
    Observable<JlWpPositionsBean> getWpPositions(@Query("access_token") String token);

    //获取收支明细
    @GET("/api/trade/record/findAcctMoneyLog/0/{page}")
    Observable<JlWpMoneyHistoryBean> getMoneyHistory(@Path("page") int page,
                                                     @Query("access_token") String token);

    //获取交易明细
    @GET("/api/trade/record/historyOrder/{pageno}/{pagesize}")
    Observable<JlWpTradeHistoryBean> getWpTradeHistory(@Path("pageno") int pageNum,
                                                       @Path("pagesize") int pageSize,
                                                       @Query("access_token") String token);

//    //获取交易明细总计
//    @GET("/api/trade/record/historyOrderStatistics")
//    Observable<JlWpTradeTotalBean> getWpTradeTotal(@Query("access_token") String token);

    //获取用户绑定的银行卡信息
    @GET("/api/usercard/findCardByUserid")
    Observable<JlWithDrawBean> getUserBankInfo(@Query("access_token") String access_token);

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
    @PUT("/api/vpanuser/unBindCard")
    Observable<WPStateDescWrapper> unBindBankCard(@Query("access_token") String access_token);

    //发送重置密码短信
    @GET("/api/sendVerifyCode/reset/" + APPID)
    Observable<BaseWpResult> sendResetPwdVerifyCode(@Query("access_token") String token,
                                                    @Query("msgType") String type);

    //发送提现验证码
    @PUT("/api/withdraw/sendMessage")
    Observable<BaseWpResult> sendWithDrawVerifyCode(@Query("access_token")String token);

    //微信充值
    @PUT("/api/recharge/wechat/createSdk/{amount}/{channels}")
    Observable<QrCodeChargeBean> WeChatCharge(@Path("amount")int amount,
                                              @Path("channels")String channels,
                                              @Query("access_token")String token);

    //支付宝充值
    @POST("/api/recharge/alipay/createQrCode/{amount}/{channels}")
    Observable<QrCodeChargeBean> AlipayCharge(@Path("amount")int amount,
                                              @Path("channels")String channels,
                                              @Query("access_token")String token);
}
