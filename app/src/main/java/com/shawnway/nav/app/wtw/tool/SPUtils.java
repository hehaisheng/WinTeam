package com.shawnway.nav.app.wtw.tool;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2016/10/14.
 * Administrator github = "https://github.com/Cicinnus0407"
 */

public class SPUtils {
    private static SharedPreferences sp;
    public static final String SP_COOKIES = "cookies";//cookies
    public static final String SP_ACCOUNT = "sp_account";//赢天下账号
    public static final String SP_WP_HOT_SELECTED = "sp_wp_hot_selected";//微盘热门交易
    public static final String SP_JL_WP_HOT_SELECTED = "sp_jl_wp_hot_selected";//吉林微盘热门交易
    public static final String SP_INT_HOT_SELECTED = "sp_int_hot_selected";//国际期货热门交易
    public static final String SP_WP = "sp_wp_info";//微盘数据
    public static final String SP_JL_WP = "sp_jl_wp_info";//吉林微盘数据
    public static final String SP_UPDATE_INFO = "sp_update_acc_info";//是否修改密码

    /******************
     * Cookie
     ******************/
    public static final String TOKEN = "XSRF-TOKEN";
    public static final String JSESSIONID = "JSESSIONID";

    /***************************
     * 微盘信息
     ***********************************************/
    public static final String WP_TOKEN = "access_token";
    public static final String WP_REFRESH_TOKEN = "refresh_token";

    /**
     * 吉林微盘
     */
    public static final String JL_WP_TOKEN = "access_token";
    public static final String JL_WP_REFRESH_TOKEN = "refresh_token";

    /****************************
     * 账户信息
     ***********************************************/
    public static final String PHONE = "phone";
    public static final String PWD = "pwd";
    public static final String USER_POINT = "user_point";
    public static final String UPDATE_PWD = "update_pwd";//修改密码
    public static final String RESET_PWD = "reset_pwd";//重置密码
    public static final String RESET_JL_WP_PWD = "reset_jl_wp_pwd";//重置吉林微盘密码



    private static SharedPreferences.Editor editor;
    private static SPUtils instance;


    @SuppressLint("CommitPrefEdits")
    public static SPUtils getInstance(Context context, String fileName) {
        if (instance == null) {
            instance = new SPUtils();
        }
        sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        editor = sp.edit();

        return instance;
    }


    public  boolean isLogin(){
        return !SPUtils.sp.getString(SPUtils.PHONE, "").equals("");
    }

    /**
     * 最后需要调用，否则无法保存
     */
    public void apply() {
        editor.apply();
    }

    /**
     * 删除所有数据
     */
    public void deleteAllSPData() {
        editor.clear().apply();
    }

    public SPUtils putString(String key, String value) {
        editor.putString(key, value);
        return this;
    }

    public String getString(String key, String defValue) {
        return sp.getString(key, defValue);
    }


    public SPUtils putInt(String key, int value) {
        editor.putInt(key, value);
        return this;
    }

    public int getInt(String key, int defValue) {
        return sp.getInt(key, defValue);
    }

    public SPUtils putLong(String key, Long value) {
        editor.putLong(key, value);
        return this;
    }

    public SPUtils putDouble(String key, Double value) {
        editor.putLong(key, Double.doubleToRawLongBits(value));
        return this;
    }

    public Double getDouble(String key, Double defValue) {
        return Double.longBitsToDouble(sp.getLong(key, Double.doubleToLongBits(defValue)));
    }


    public Long getLong(String key, long defValue) {
        return sp.getLong(key, defValue);
    }

    public SPUtils putBoolean(String key, boolean isAccess) {
        editor.putBoolean(key, isAccess);

        return this;
    }

    public Boolean getBoolean(String key, boolean defValue) {
        return sp.getBoolean(key, defValue);
    }
}
