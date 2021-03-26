package com.pentatea.uniplugin;

import android.app.Activity;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import io.dcloud.feature.uniapp.annotation.UniJSMethod;
import io.dcloud.feature.uniapp.bridge.UniJSCallback;
import io.dcloud.feature.uniapp.common.UniModule;

/**
 * wildfire扩展的uniapp模块
 */
public class WildfireModule extends UniModule {

    /**
     * 请求码
     */
    public static final int REQUEST_CODE = 1000;

    private static final String TAG = "WildfireModule";

    /**
     * 前端接收消息的回调函数名
     */
    private static final String KEY_MSG_LISTENER = "receive";

    /**
     * sdk后处理
     */
    @Override
    public void onActivityDestroy() {
        super.onActivityDestroy();
        // 删除监听器
        Log.i(TAG, "应用销毁后处理");
        // ChatManager chatManager = ChatManager.Instance();
        // chatManager.removeOnReceiveMessageListener(receiveMessageListener);
    }

    @UniJSMethod(uiThread = false)
    public JSONObject init() {
        JSONObject info = new JSONObject();
        ChatManagerHolder.mUniSDKInstance = mUniSDKInstance;

        while (ChatManagerHolder.qGlobalEvent.peek() != null) {
            ArrayList e = ChatManagerHolder.qGlobalEvent.poll();
            mUniSDKInstance.fireGlobalEventCallback((String) e.get(0), (JSONObject) e.get(1));
        }

        if (mUniSDKInstance.getContext() instanceof Activity) {
            Log.i(TAG, "前端连接成功");
            info.put("clientId", ChatManagerHolder.gChatManager.getClientId());
            /**
             * 平台类型iOS 1, Android 2, Windows 3, OSX 4, WEB 5, 小程序 6，linux 7
             */
            info.put("platform", 2);
        } else {
            Log.e(TAG, "未能获得有效的Context");
        }

        return info;
    }


    @UniJSMethod(uiThread = false)
    public void connect(JSONObject jsonObject) {
        if (mUniSDKInstance.getContext() instanceof Activity) {
            // 通讯连接
            String userId = jsonObject.getString("userId");
            String token = jsonObject.getString("token");
            if (null == userId || null == token) {
                Log.e(TAG, "没有野火的 userId 或者 token");
                return;
            }

            ChatManagerHolder.gChatManager.connect(userId, token);
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Log.i(TAG, "连接状态: " + String.valueOf(ChatManagerHolder.gChatManager.getConnectionStatus()));
                }
            }, 1000); // 延时1秒
            // 推送设置设备类型和通讯token
            /*ChatManagerHolder.gChatManager
                    .setDeviceToken(token, PushService.getPushServiceType().ordinal());*/
        }
    }
}
