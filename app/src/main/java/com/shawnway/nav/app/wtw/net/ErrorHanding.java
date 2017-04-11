package com.shawnway.nav.app.wtw.net;

import com.shawnway.nav.app.wtw.application.WinApplication;
import com.shawnway.nav.app.wtw.tool.NetWorkUtil;

/**
 * Created by Administrator on 2016/9/19.
 */

public class ErrorHanding {
    public ErrorHanding() {
    }

    public static String handleError(Throwable throwable) {
        throwable.printStackTrace();
        String message;
        if (!NetWorkUtil.isNetworkConnected(WinApplication.getInstance())) {
            message = "无网络连接";
        } else if (throwable instanceof ServerException) {
            message = throwable.getMessage();
        } else {
            message = "连接服务器失败";
        }
        return message;
    }

    private class ServerException extends Exception {
        public ServerException(String msg) {
            super(msg);
        }
    }
}
