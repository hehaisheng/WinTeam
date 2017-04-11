package com.shawnway.nav.app.wtw.module.integral_mall;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shawnway.nav.app.wtw.R;
import com.shawnway.nav.app.wtw.base.BaseActivity;
import com.shawnway.nav.app.wtw.base.BaseSubscriber;
import com.shawnway.nav.app.wtw.bean.LuckyDrawGoodsBean;
import com.shawnway.nav.app.wtw.bean.LuckyDrawResultBean;
import com.shawnway.nav.app.wtw.bean.UserPointResult;
import com.shawnway.nav.app.wtw.module.login_register.login.LoginActivity;
import com.shawnway.nav.app.wtw.net.RetrofitClient;
import com.shawnway.nav.app.wtw.tool.Constants;
import com.shawnway.nav.app.wtw.tool.SPUtils;
import com.shawnway.nav.app.wtw.tool.SchedulersCompat;
import com.shawnway.nav.app.wtw.tool.StringUtils;
import com.shawnway.nav.app.wtw.tool.ToastUtil;
import com.shawnway.nav.app.wtw.view.LuckyPanView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;



public class TurnTableActivity extends BaseActivity {

    private static final String TAG = "转盘";
    private LuckyPanView mLuckyPanView;
    private ImageView mStartBtn;
    private boolean isClick;
    private int integral;
    private ArrayList<LuckyDrawGoodsBean.LotteryRaffleBean> mDatas = new ArrayList<>();
    private TextView tvIntegral;
    private TextView tvWinnerPhone;
    private TextView tvWinnerGoods;
    private ImageButton btnBack;
    private TextView tvTitle;
    private TextView tvRight;
    private String result;
    private BroadcastReceiver receiver;
    Bitmap[] imgs;

    private Handler handler = new Handler();
    Runnable run = new Runnable() {
        @Override
        public void run() {
            mStartBtn.performClick();
        }
    };
    Runnable ran = new Runnable() {
        @Override
        public void run() {
            if (StringUtils.isNotEmpty(mLuckyPanView.getWinningGoods())) {
                ToastUtil.showShort(TurnTableActivity.this, "恭喜你获得：" + mLuckyPanView.getWinningGoods());
                initIntegralAndResult();
            }
        }
    };
    Runnable runResult = new Runnable() {
        @Override
        public void run() {
            if (StringUtils.isNotEmpty(result) && result.contains("抽中")) {
                int index = result.indexOf("抽");
                tvWinnerGoods.setVisibility(View.VISIBLE);
                tvWinnerPhone.setVisibility(View.VISIBLE);
                tvWinnerPhone.setText(result.substring(0, index));
                tvWinnerGoods.setText(result.substring(index));
            }
        }
    };

    public static void getInstance(Context context) {
        Intent intent = new Intent(context, TurnTableActivity.class);
        context.startActivity(intent);
    }

    public static void getInstance(ArrayList<LuckyDrawGoodsBean.LotteryRaffleBean> mDatas, Context context) {
        Intent intent = new Intent(context, TurnTableActivity.class);
        intent.putExtra("data", mDatas);
        context.startActivity(intent);
    }


    @Override
    protected int getLayout() {
        return R.layout.activity_turntable;
    }

    @Override
    protected void initEventAndData() {
        mDatas = (ArrayList<LuckyDrawGoodsBean.LotteryRaffleBean>) getIntent().getSerializableExtra("data");

        imgs = new Bitmap[mDatas.size()];
        initImags();
        initToolBar();
        initView();
        mLuckyPanView = (LuckyPanView) findViewById(R.id.id_luckypan);
        //判断是否登录 ，登录了之后才进入抽奖
        if (SPUtils.getInstance(mContext,SPUtils.SP_ACCOUNT).getString(SPUtils.PHONE,"").equals("")) {
            finish();
            LoginActivity.getInstance(this);
            return;
        }
        initReceiver();
    }

    private void initImags() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < mDatas.size(); i++) {
                    Log.d(TAG, "图片地址：" + i + "," + mDatas.get(i).getImgurl());
                    try {
                        imgs[i] = Glide.with(TurnTableActivity.this)
                                .load(mDatas.get(i).getImgurl())
                                .asBitmap() //必须
                                .centerCrop()
                                .into(50, 50)
                                .get();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                    Log.d(TAG, "bitmap图片：" + i + "," + imgs[i]);
                }
            }
        }).start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (result == null || result.equals("")) {
            tvWinnerPhone.setVisibility(View.GONE);
            tvWinnerGoods.setVisibility(View.GONE);
        }
        handler.removeCallbacks(run);
        initTurnTable(mDatas);
        initIntegralAndResult();
    }

    private void initReceiver() {
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(Constants.ACTION_ADD_LUCKYDRAW_RESULT)) {
                    Log.d(TAG, "MyReceiver 显示抽奖结果");
                    result = intent.getStringExtra("luckydrawresult");
                    handler.postDelayed(runResult, 3 * 1000);
                }
            }
        };
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constants.ACTION_ADD_LUCKYDRAW_RESULT);
        registerReceiver(receiver, filter);
    }


    private void initToolBar() {
        findViewById(R.id.toolbar).setVisibility(View.VISIBLE);
        btnBack = (ImageButton) findViewById(R.id.top_back);
        tvTitle = (TextView) findViewById(R.id.top_text_center);
        tvRight = (TextView) findViewById(R.id.top_text_right);

        btnBack.setVisibility(View.VISIBLE);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvTitle.setVisibility(View.VISIBLE);
        tvTitle.setText("幸运大转盘");
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setText("我的中奖");
        tvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, UserWinningRecordActivity.class));
            }
        });
    }

    private void initView() {
        tvIntegral = (TextView) findViewById(R.id.activity_turntable_tv_integral);
        tvWinnerPhone = (TextView) findViewById(R.id.activity_turntable_tv_winner_phone);
        tvWinnerGoods = (TextView) findViewById(R.id.activity_turntable_tv_winner_goods);
    }

    private void initTurnTable(final ArrayList<LuckyDrawGoodsBean.LotteryRaffleBean> data) {
        final String[] goods = new String[data.size()];
        int[] colors = new int[data.size()];
        final int[] ids = new int[data.size()];
        for (int i = 0; i < data.size(); i++) {
            goods[i] = data.get(i).getName();
            ids[i] = data.get(i).getId();
            if (i % 2 == 0) {
                colors[i] = 0xFFFFC300;
            } else {
                colors[i] = 0xFFF17E01;
            }
        }
        mLuckyPanView.setmTextSize(32.0f);//抽奖商品字体大小，单位sp
        mLuckyPanView.setmItemCount(goods.length);//抽奖商品个数
        mLuckyPanView.setmImgsBitmap(imgs);
        mLuckyPanView.setmStrs(goods);//每个商品的中文说明
        mLuckyPanView.setmColors(colors);//每个商品对应的背景色
        mLuckyPanView.setmBgBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.bg_turn));
        mStartBtn = (ImageView) findViewById(R.id.id_start_btn);
        Log.d(TAG, "按钮是否可以点击：" + isClick);
        if (isClick) {
            Log.d(TAG, "不可点击");
            mStartBtn.setClickable(false);
            ToastUtil.showShort(this, "网络卡顿，请退出重进");
        }
        mStartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (integral < 200) {
                    ToastUtil.showShort(TurnTableActivity.this, "积分不够两百，不可以抽奖");
                    return;
                }
                if (!mLuckyPanView.isStart()) {
                    mStartBtn.setImageResource(R.mipmap.start);
                    RetrofitClient
                            .getInstance()
                            .api()
                            .getPrizeResult()
                            .compose(SchedulersCompat.<LuckyDrawResultBean>applyIoSchedulers())
                            .subscribe(new BaseSubscriber<LuckyDrawResultBean>() {
                                @Override
                                public void onSuccess(LuckyDrawResultBean response) {
                                    int id = response.getPrize();
                                    Log.d(TAG, "中奖商品id：" + id);
                                    for (int i = 0; i < ids.length; i++) {
                                        if (id == ids[i]) {
                                            isClick = true;
                                            Log.d(TAG, "开始旋转");
                                            mLuckyPanView.luckyStart(i);
                                            handler.postDelayed(run, 1000);
                                        }
                                    }
                                    if (integral > 200) {
                                        tvIntegral.setText(integral - 200 + "");
                                    }
                                }
                            });
                } else {
                    isClick = false;
                    if (!mLuckyPanView.isShouldEnd()) {
                        Log.d(TAG, "结束旋转");
                        mStartBtn.setImageResource(R.mipmap.start);
                        mLuckyPanView.luckyEnd();
                        handler.postDelayed(ran, 3 * 1000);
                    }
                }
            }
        });
    }

    private void initIntegralAndResult() {
        RetrofitClient
                .getInstance()
                .api()
                .getUserPoint()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<retrofit2.Response<UserPointResult>>() {
                    @Override
                    public void onSuccess(retrofit2.Response<UserPointResult> response) {
                        if (response.code() == 200) {
                            integral = response.body().getPoint();
                            tvIntegral.setText(response.body().getPoint() + "");
                        }
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(run);
        handler.removeCallbacks(ran);
        handler.removeCallbacks(runResult);
        if (receiver != null ) {
            unregisterReceiver(receiver);
        }
    }


}
