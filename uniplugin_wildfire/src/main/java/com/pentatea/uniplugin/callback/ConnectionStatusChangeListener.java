package com.pentatea.uniplugin.callback;

import android.util.Log;

import cn.wildfirechat.remote.OnConnectionStatusChangeListener;

/**
 * 连接状态回调
 */
public class ConnectionStatusChangeListener implements OnConnectionStatusChangeListener {

    private static final String TAG = "ConnectionListener";

    @Override
    public void onConnectionStatusChange(int status) {
        Log.i(TAG, "im连接状态:" + status);
    }
}
