package com.shawnway.nav.app.wtw.net;

import com.shawnway.nav.app.wtw.bean.BannerPicBean;
import com.shawnway.nav.app.wtw.bean.BindAccountResponse;
import com.shawnway.nav.app.wtw.bean.EconCalenBean;
import com.shawnway.nav.app.wtw.bean.InternationalKLineBean;
import com.shawnway.nav.app.wtw.bean.LiveListBean;
import com.shawnway.nav.app.wtw.bean.LoginResult;
import com.shawnway.nav.app.wtw.bean.LuckyDrawGoodsBean;
import com.shawnway.nav.app.wtw.bean.LuckyDrawResultBean;
import com.shawnway.nav.app.wtw.bean.NewsAddressBean;
import com.shawnway.nav.app.wtw.bean.NewsBean;
import com.shawnway.nav.app.wtw.bean.PointRuleResult;
import com.shawnway.nav.app.wtw.bean.RegisterWTXBean;
import com.shawnway.nav.app.wtw.bean.ResetPwdResult;
import com.shawnway.nav.app.wtw.bean.RetrieveInvitationCodeBean;
import com.shawnway.nav.app.wtw.bean.SignInResult;
import com.shawnway.nav.app.wtw.bean.UserPointResult;
import com.shawnway.nav.app.wtw.bean.UserWinningResult;
import com.shawnway.nav.app.wtw.bean.WpInfoResult;
import com.shawnway.nav.app.wtw.bean.YTXUserWrapper;
import com.shawnway.nav.app.wtw.module.WebViewActivity;
import com.shawnway.nav.app.wtw.module.login_register.RegisterActivity;
import com.shawnway.nav.app.wtw.module.login_register.forget_pwd.ForgetCodeResult;
import com.shawnway.nav.app.wtw.module.login_register.update_pwd.UpdatePwdResult;
import com.shawnway.nav.app.wtw.module.login_register.update_pwd.UpdateWpInfoResult;
import com.shawnway.nav.app.wtw.module.mall.address.bean.AddAddrResultBean;
import com.shawnway.nav.app.wtw.module.mall.address.bean.AddressListResultBean;
import com.shawnway.nav.app.wtw.module.mall.address.bean.DefaultAddressResultBean;
import com.shawnway.nav.app.wtw.module.mall.address.bean.DeleteAddressResultBean;
import com.shawnway.nav.app.wtw.module.mall.address.bean.SetDefaultAddrResultBean;
import com.shawnway.nav.app.wtw.module.mall.address.bean.UpdateAddressResultBean;
import com.shawnway.nav.app.wtw.module.mall.bean.ExpressDeliver;
import com.shawnway.nav.app.wtw.module.mall.bean.MallOrders;
import com.shawnway.nav.app.wtw.module.mall.bean.NewestProductBean;
import com.shawnway.nav.app.wtw.module.mall.bean.RecommendProductBean;
import com.shawnway.nav.app.wtw.module.mall.filter.bean.FilterListBean;
import com.shawnway.nav.app.wtw.module.mall.filter.bean.FilterProductBean;
import com.shawnway.nav.app.wtw.module.mall.order.ExChangeProductResultBean;
import com.shawnway.nav.app.wtw.module.mall.point_detail.PointDetailResult;
import com.shawnway.nav.app.wtw.module.mall.productDetail.ProDetailBean;
import com.shawnway.nav.app.wtw.module.mall.shopping_car.bean.ShoppingCarListBean;
import com.shawnway.nav.app.wtw.module.mall.shopping_car.bean.ShoppingCarResultBean;
import com.shawnway.nav.app.wtw.module.quotation.international.InternationalListBean;
import com.shawnway.nav.app.wtw.module.quotation.international.billingRecord.BillRecordBean;
import com.shawnway.nav.app.wtw.module.quotation.international.international_detail.UserAccountInfo;
import com.shawnway.nav.app.wtw.module.quotation.international.international_detail.UserAccountList;
import com.shawnway.nav.app.wtw.module.quotation.international.order.TradeBean;
import com.shawnway.nav.app.wtw.module.quotation.international.positions.PointValueResultBean;
import com.shawnway.nav.app.wtw.module.quotation.international.positions.PositionsBean;
import com.shawnway.nav.app.wtw.module.quotation.international.positions.today.TodayTradingBean;
import com.shawnway.nav.app.wtw.module.user.setting.FeedBackActivity;
import com.shawnway.nav.app.wtw.module.user.setting.SettingActivity;
import com.shawnway.nav.app.wtw.module.user.ticket.ticket.TicketBean;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Administrator on 2016/10/13.
 * 赢天下相关接口
 */

public interface Api {
//    String BASE_URL = "https://test.winteams.cn/";
   // String BASE_URL = "https://www.winteams.cn/";
    String BASE_URL = "http://120.76.246.29/";
    //代理商Id
    String AGENTID = "/1";
    int AGENT_ID = 1;
    //接口版本
    String URL_VERSION = "1_1_0";

    //登录赢天下
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("userLogin")
    Observable<Response<LoginResult>> login(@Field("username") String username,
                                            @Field("password") String password,
                                            @Field("agentId") int id);

    //发送注册赢天下验证码
    @POST("api/public/requestVerificationCodeForytx" + AGENTID)
    Observable<Response<RegisterActivity.SendResult>> sendWTWCode(@Body RequestBody body);

    //注册赢天下
    @POST("api/public/ytxRegist" + AGENTID)
    Observable<RegisterWTXBean> registerWTW(@Body RequestBody body);

    //修改赢天下密码
    @POST("ytx/api/public/updateYtxRegistInfo")
    Observable<UpdatePwdResult> updatePwd(@Body RequestBody requestBody);

    //发送赢天下忘记密码验证码
    @POST("api/public/requestVerficationCodeForResetPassord")
    Observable<Response<ForgetCodeResult>> sendForgetCode(@Body RequestBody body);

    //重置赢天下密码
    @POST("api/public/resetNewPassWordWithVerificationCode")
    Observable<ResetPwdResult> resetPassword(@Body RequestBody body);


    //获取赢天下注册信息
    @POST("ytx/api/public/retrieveYtxPersonalInfo")
    Observable<YTXUserWrapper> getPersonInfo();

    //获取微盘注册信息
    @POST("ytx/api/customer/retrieveWeipanRegistInfo" + URL_VERSION)
    Observable<WpInfoResult> getWpInfo();

    //更新微盘信息
    @POST("ytx/api/customer/updateYtxWeipanRegistInfo")
    Observable<UpdateWpInfoResult> updateWpInfo(@Body RequestBody requestBody);

    //获取吉交所微盘注册信息
    @POST("ytx/api/customer/retrieveJijiaosuoWeipanRegistInfo" + URL_VERSION)
    Observable<WpInfoResult> getJlWpInfo();

    //更新吉交所微盘信息
    @POST("ytx/api/customer/updateJijiaosuoWeipanRegistInfo")
    Observable<UpdateWpInfoResult> updateJlWpInfo(@Body RequestBody requestBody);


    //获取轮播图数据
    @GET("api/public/retrieveBannerPic" + AGENTID)
    Observable<BannerPicBean> getBannerUrl();

    //获取国际期货列表
    @GET("api/public/getMarketPriceByCodes" + AGENTID)
    Observable<InternationalListBean> getInternationalList();

    //获取国际期货的点数对应值和汇率
    @POST("api/public/retrieveYTXSecurityMovementPointValue" + AGENTID)
    Observable<PointValueResultBean> getPointValue();

    //获取现有持仓情况
    @POST("api/customer/retrieveWarehouseInfo")
    Observable<Response<PositionsBean>> getAllPositions();

    //获取历史持仓记录
    @POST("api/customer/retrieveCustomerWarehouseRecord")
    Observable<Response<BillRecordBean>> getHistoryBilling();

    //获取今天下单
    @POST("api/customer/retrieveTodayCustomerWarehouseRecord")
    Observable<TodayTradingBean> getTodayTrading();

    //交易(建仓,平仓)
    @POST("api/customer/performTrade")
    Observable<Response<TradeBean>> order(@Body RequestBody requestBody);

    //获取国际期货账号列表
    @POST("api/customer/retrieveCustomerTradingAccountList")
    Observable<Response<UserAccountList>> getUserAccountList();

    //获取账号数据
    @POST("api/customer/retrieveCustomerTradingAccount")
    Observable<UserAccountInfo> getUserAccountInfo(@Body RequestBody requestBody);

    //国际期货K线
    @POST("http://ytx.marketdata.fatwo.cn:5550/api/public/MarketDataCandleChart")
    Observable<InternationalKLineBean> getInternationalKLine(@Body RequestBody requestBody);

    //获取30条重要消息
    @POST("api/public/findTop30ImportantNews1_1_0" + AGENTID)
    Observable<NewsBean[]> getTop30Important(@Body RequestBody requestBody);

    //获取30条系统消息
    @POST("api/public/findTop30SystemNews1_1_0" + AGENTID)
    Observable<NewsBean[]> get30SystemMessages(@Body RequestBody requestBody);


    //通过id获取新闻信息
    @POST("api/public/getNewsById")
    Observable<NewsAddressBean> getNewsById(@Body RequestBody requestBody);

    //获取最新活动
    @POST("api/public/findTop30Activiites" + URL_VERSION + AGENTID)
    Observable<NewsBean[]> get30Activities(@Body RequestBody requestBody);

    //交易攻略
    @POST("api/public/findTop30TradeStrategy"+ URL_VERSION + AGENTID)
    Observable<NewsBean[]> get30Trade(@Body RequestBody requestBody);

    //财经日历
    @POST("api/public/retrieveFinancialCalendar")
    Observable<EconCalenBean> getFinancial(@Body RequestBody requestBody);

    //绑定国际期货账号
    @POST("api/customer/tieUpCTA")
    Observable<BindAccountResponse> bindAccount();

    //获取直播列表
    @POST("api/public/retrieveLiveShowList")
    Observable<LiveListBean> getLiveList();

    //检查版本号
    @GET("api/public/retrieveAppVersion")
    Observable<SettingActivity.VersionResponse> checkVersion();

    //反馈
    @POST("personal-center/complaint")
    Observable<FeedBackActivity.BasicResponse> sendFeedBack(@Body RequestBody body);

    //参加活动
    @POST("ytx/api/customer/activityRegister/{id}")
    Observable<WebViewActivity.JoinActivityBean> joinActivity(@Path("id") String id);

    //退出登录
    @POST("logout")
    Observable<SettingActivity.LogoutResponse> logOut();

    //心跳包
    @POST("api/public/heartbeatDetection")
    Observable<ResponseBody> sendHeartBeat();

    //签到
    @POST("api/customer/signIn")
    Observable<Response<SignInResult>> signIn();

    //获取积分规则
    @GET("api/public/retrieveConsumptionPointSetting" + AGENTID)
    Observable<PointRuleResult> getPointRule();

    //获取用户剩余积分
    @POST("/ytx/api/public/retrieveConsumptionPoint")
    Observable<Response<UserPointResult>> getUserPoint();

    //获取抽奖结果
    @POST("ytx/api/public/drawALotteryOrRaffle")
    Observable<LuckyDrawResultBean> getPrizeResult();

    //奖品列表
    @GET("api/public/retrievePrizesList" + AGENTID)
    Observable<LuckyDrawGoodsBean> getPrizesList();

    //用户中奖记录
    @POST("ytx/api/public/luckyDrawRecord")
    Observable<UserWinningResult> getUserWinningRecord(@Body RequestBody body);

    //获取用户积分明细
    @POST("ytx/api/public/retrieveConsumptionPointDetails")
    Observable<Response<PointDetailResult>> getPointDetail(@Body RequestBody body);

    //获取首页推荐产品列表
    @POST("api/public/retrieveRecommendedProductList" + AGENTID)
    Observable<RecommendProductBean> getRecommendProduct(@Body RequestBody requestBody);

    //获取首页新品列表
    @POST("api/public/retrieveLatestProductList" + AGENTID)
    Observable<NewestProductBean> getNewestProduct(@Body RequestBody requestBody);

    //获取分类列表
    @POST("api/public/retrieveProductTypeList" + AGENTID)
    Observable<FilterListBean> getFilterList();

    //排序或查询商品
    @POST("api/public/retrieveProductListByNameAndType" + AGENTID)
    Observable<FilterProductBean> queryOrSortProduct(@Body RequestBody requestBody);

    //根据Id获取商品详情
    @POST("api/public/retrieveProductEntityInfoByProId/{id}")
    Observable<ProDetailBean> getProDetail(@Path("id") int id);

    //添加收货地址
    @POST("api/customer/addDeliveryAddress")
    Observable<AddAddrResultBean> addAddr(@Body RequestBody requestBody);

    //设置默认地址
    @POST("api/customer/setDefaultDeliveryAddress")
    Observable<SetDefaultAddrResultBean> setDefaultAddress(@Body RequestBody requestBody);

    //获取收货地址
    @POST("api/customer/retrieveDeliveryAddressList")
    Observable<Response<AddressListResultBean>> getAddressList();

    //删除收货地址
    @POST("api/customer/deleteDeliveryAddress")
    Observable<DeleteAddressResultBean> deleteAddress(@Body RequestBody requestBody);

    //更新收货地址
    @POST("api/customer/updateDeliveryAddress")
    Observable<UpdateAddressResultBean> updateAddress(@Body RequestBody requestBody);

    //获取默认收货地址
    @POST("api/customer/retrieveDefaultDeliveryAddress")
    Observable<Response<DefaultAddressResultBean>> getDefaultAdress();

    //获取购物车内容
    @POST("api/customer/retrieveShoppingCartList")
    Observable<Response<ShoppingCarListBean>> getShoppingCarList();

    //添加到购物车
    @POST("api/customer/addToShoppingCart")
    Observable<Response<ShoppingCarResultBean>> addShoppingCarProduct(@Body RequestBody requestBody);

    //更新购物车商品
    @POST("api/customer/updateShoppingCart")
    Observable<ShoppingCarResultBean> updateShoppingCarProduct(@Body RequestBody requestBody);

    //删除购物车的某样商品
    @POST("api/customer/deleteFromShoppingCart")
    Observable<ShoppingCarResultBean> deleteShoppingCarProdutc(@Body RequestBody requestBody);

    //购物车下订单
    @POST("api/customer/exchangeForProduct")
    Observable<ExChangeProductResultBean> exchangeForProduct(@Body RequestBody requestBody);


    //获取用户邀请码
    @POST("api/customer/retrieveInvitationCode")
    Observable<Response<RetrieveInvitationCodeBean>> getInviteCode();

    //获取用户积分商城所有订单信息
    @POST("api/customer/retrieveExchangeTransactions")
    Observable<Response<MallOrders>> getMallOrders(@Body RequestBody requestBody);

    //确认收货
    @POST("/api/customer/confirmProductIntegralOrderCompleted")
    Observable<MallOrders> confirmRceipt(@Body RequestBody requestBody);

    //删除订单
    @POST("/api/customer/deleteProductIntegralOrder")
    Observable<MallOrders> deleteOrder(@Body RequestBody requestBody);

    //物流信息
    @GET("/api/public/retrieveExpressInfo/{orderId}")
    Observable<ExpressDeliver> getOrderExpressByOrderId(@Path("orderId") String orderId);


    //获取赢天下现金券
    @POST("api/customer/retrieveCacheCoupon")
    Observable<TicketBean> getCoupon(@Body RequestBody requestBody);


}
