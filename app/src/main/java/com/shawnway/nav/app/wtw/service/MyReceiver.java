package com.shawnway.nav.app.wtw.service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.orhanobut.logger.Logger;
import com.shawnway.nav.app.wtw.bean.NewsAddressBean;
import com.shawnway.nav.app.wtw.module.home.ImportanceNewsFragment;
import com.shawnway.nav.app.wtw.module.information.NewsActivity;
import com.shawnway.nav.app.wtw.net.JsonRequestBody;
import com.shawnway.nav.app.wtw.net.RetrofitClient;
import com.shawnway.nav.app.wtw.tool.Constants;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import cn.jpush.android.api.JPushInterface;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * 自定义接收器
 * 
 * 如果不定义这个 Receiver，则：
 * 极光推送服务
 */
public class MyReceiver extends BroadcastReceiver {
	private static final String TAG = "JPush";

	@Override
	public void onReceive(final Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
		Log.d(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));
		String bundleStr = printBundle(bundle);
		String agentId = null;
		if(bundleStr.contains("抽奖结果") || bundleStr.contains("agentId")){
			int start = bundleStr.lastIndexOf("[");
			int end = bundleStr.lastIndexOf("]");
			String str = bundleStr.substring(start, end);
			int idStart = str.indexOf("-");
			agentId = str.substring(idStart,str.length()).replace("-"," ").trim();
		}
        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            Log.d(TAG, "[MyReceiver] 接收Registration Id : " + regId);
            //send the Registration Id to your server...
        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
			/**
			 * [MyReceiver] onReceive - cn.jpush.android.intent.MESSAGE_RECEIVED, extras:
			 key:cn.jpush.android.TITLE, value:抽奖结果
			 key:cn.jpush.android.MSG_ID, value:2535142681
			 key:cn.jpush.android.APPKEY, value:6ab6580189b13087ae4c6b09
			 key:cn.jpush.android.MESSAGE, value:恭喜188****2236抽中谢谢参与
			 key:cn.jpush.android.CONTENT_TYPE, value:
			 key:cn.jpush.android.EXTRA, value: [agentId - 1]
			 10-25 18:08:18.065 2790-2790/com.shawnway.nav.app.wtw D/JPush: [MyReceiver] 接
			 收到推送下来的自定义消息: 恭喜188****2236抽中谢谢参与
			 */
			if(agentId.equals("1")){
				Log.d(TAG, "MyReceiver 发送抽奖结果广播");
				Intent i = new Intent(Constants.ACTION_ADD_LUCKYDRAW_RESULT);
				i.putExtra("luckydrawresult",bundle.getString(JPushInterface.EXTRA_MESSAGE));
				context.sendBroadcast(i);
			}
        	Log.d(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
			processCustomMessage(context, bundle);
        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 接收到推送下来的通知");
            int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
			int start = bundleStr.lastIndexOf("[");
			int end = bundleStr.lastIndexOf("]");

			String str = "";
			if (start != -1 && end != -1) {
				str = bundleStr.substring(start, end);
			}
			Log.d(TAG, "第一次截取111："+str);
			int idStart = -1;
			if (!str.equals("")) {
				idStart = str.indexOf("-");
			}
			String newsIdNotify = "";
			if (idStart != -1) {
				newsIdNotify = str.substring(idStart, str.length()).replace("-", " ").trim();
			}
            Log.d(TAG, "[MyReceiver] 接收到推送下来的newsId: " + newsIdNotify);
			Log.d(TAG, "[MyReceiver]新闻的id:"+newsIdNotify);
			if (!newsIdNotify.equals("")) {
				JSONObject json = new JSONObject();
				try {
					json.put("id", newsIdNotify);
				} catch (JSONException e) {
					e.printStackTrace();
				}

				RetrofitClient
						.getInstance()
						.api()
						.getNewsById(JsonRequestBody.getInstance().convertJsonContent(json.toString()))
						.subscribeOn(Schedulers.io())
						.observeOn(AndroidSchedulers.mainThread())
						.subscribe(new Action1<NewsAddressBean>() {
							@Override
							public void call(NewsAddressBean response) {
								if (response != null) {
									String categoryName = response.categoryName;
									int type = response.newsCategoryId;
									Log.d(TAG, "categoryName:" + categoryName);
									if (categoryName.equals("重要信息") || type == 2) {
										EventBus.getDefault().post(new ImportanceNewsFragment.ImportanceNews());
									} else if (categoryName.equals("系统消息") || type == 1) {
										Log.d(TAG, "发送系统消息广播");
										Intent intent = new Intent(Constants.ACTION_ADD_SYSTEMINFO);
										context.sendBroadcast(intent);
									} else if (categoryName.equals("最新活动") || type == 42) {
										Log.d(TAG, "发送最新活动广播");
										Intent intent = new Intent(Constants.ACTION_ADD_LATESTACT);
										context.sendBroadcast(intent);
									} else if (categoryName.equals("交易攻略") || type == 43) {
										Log.d(TAG, "发送交易攻略广播");
										Intent intent = new Intent(Constants.ACTION_ADD_TRANSTRATEGY);
										context.sendBroadcast(intent);
									}
								}
							}
						}, new Action1<Throwable>() {
							@Override
							public void call(Throwable throwable) {
								Logger.e(throwable.getMessage());
							}
						});
			}
        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
			Log.d(TAG, "[MyReceiver] 用户点击打开了通知");
			int start = bundleStr.lastIndexOf("[");
			int end = bundleStr.lastIndexOf("]");
			String str = "";
			if (start != -1 && end != -1) {
				str = bundleStr.substring(start, end);
			}
			Log.d(TAG, "第一次截取222：" + str);
			int idStart = -1;
			if (!str.equals("")) {
				idStart = str.indexOf("-");
			}
			String newsIdNotify = "";
			if (idStart != -1) {
				newsIdNotify = str.substring(idStart, str.length()).replace("-", " ").trim();
			}
			if (!newsIdNotify.equals("")) {
				JSONObject json = new JSONObject();
				try {
					json.put("id", newsIdNotify);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				RetrofitClient
						.getInstance()
						.api()
						.getNewsById(JsonRequestBody.getInstance().convertJsonContent(json.toString()))
						.subscribeOn(Schedulers.io())
						.observeOn(AndroidSchedulers.mainThread())
						.subscribe(new Action1<NewsAddressBean>() {
							@Override
							public void call(NewsAddressBean response) {
								if (response != null) {
									String categoryName = response.categoryName;
									int type = response.newsCategoryId;
									Log.d(TAG, "categoryName:" + categoryName);
									if (categoryName.equals("重要信息") || type == 2) {
										Log.d(TAG, "发送广播");
										Intent intent = new Intent(Constants.ACTION_ADD_IMPORTANCENEWS);
										context.sendBroadcast(intent);
										enterActivity(context, response.newsAddress, response.title);
									} else if (categoryName.equals("系统消息") || type == 1) {
										Log.d(TAG, "发送系统消息广播");
										Intent intent = new Intent(Constants.ACTION_ADD_SYSTEMINFO);
										context.sendBroadcast(intent);
										enterActivity(context, response.newsAddress, response.title);
									} else if (categoryName.equals("最新活动") || type == 42) {
										Log.d(TAG, "发送最新活动广播");
										Intent intent = new Intent(Constants.ACTION_ADD_LATESTACT);
										context.sendBroadcast(intent);
										enterActivity(context, response.newsAddress, response.title);
									} else if (categoryName.equals("交易攻略") || type == 43) {
										Log.d(TAG, "发送交易攻略广播");
										Intent intent = new Intent(Constants.ACTION_ADD_TRANSTRATEGY);
										context.sendBroadcast(intent);
										enterActivity(context, response.newsAddress, response.title);
									}
								}
							}
						}, new Action1<Throwable>() {
							@Override
							public void call(Throwable throwable) {
								Logger.e(throwable.getMessage());
							}
						});

			} else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
				Log.d(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
				//在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..
			} else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
				boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
				Log.w(TAG, "[MyReceiver]" + intent.getAction() + " connected state change to " + connected);
			} else {
				Log.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
			}
		}
	}

	private void enterActivity(Context context, String address,String title) {
		Intent i = new Intent(context, NewsActivity.class);
		i.putExtra("url", address);
		i.putExtra("title",title);
		i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(i);
	}

	// 打印所有的 intent extra 数据
	private static String printBundle(Bundle bundle) {
		StringBuilder sb = new StringBuilder();
		for (String key : bundle.keySet()) {
			if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
				sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
			}else if(key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)){
				sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
			} else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
				if (bundle.getString(JPushInterface.EXTRA_EXTRA).isEmpty()) {
					Log.i(TAG, "This message has no Extra data");
					continue;
				}

				try {
					JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
					Iterator<String> it =  json.keys();

					while (it.hasNext()) {
						String myKey = it.next().toString();
						sb.append("\nkey:" + key + ", value: [" +
								myKey + " - " +json.optString(myKey) + "]");
					}
				} catch (JSONException e) {
					Log.e(TAG, "Get message extra JSON error!");
				}

			} else {
				sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
			}
		}
		return sb.toString();
	}
	
	//send msg to MainActivity
	private void processCustomMessage(Context context, Bundle bundle) {
		/*if (MainActivity.isForeground) {
			String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
			String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
			Intent msgIntent = new Intent(MainActivity.MESSAGE_RECEIVED_ACTION);
			msgIntent.putExtra(MainActivity.KEY_MESSAGE, message);
			if (!ExampleUtil.isEmpty(extras)) {
				try {
					JSONObject extraJson = new JSONObject(extras);
					if (null != extraJson && extraJson.length() > 0) {
						msgIntent.putExtra(MainActivity.KEY_EXTRAS, extras);
					}
				} catch (JSONException e) {

				}

			}
			context.sendBroadcast(msgIntent);
		}*/
	}
}
