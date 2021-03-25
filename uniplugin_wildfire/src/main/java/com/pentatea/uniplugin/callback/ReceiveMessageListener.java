package com.pentatea.uniplugin.callback;

import android.util.Log;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cn.wildfirechat.message.Message;
import cn.wildfirechat.message.core.PersistFlag;
import cn.wildfirechat.message.notification.PCLoginRequestMessageContent;
import cn.wildfirechat.remote.ChatManager;
import cn.wildfirechat.remote.OnReceiveMessageListener;
import io.dcloud.feature.uniapp.AbsSDKInstance;
import com.pentatea.uniplugin.ChatManagerHolder;

/**
 * 接受消息回调
 */
public class ReceiveMessageListener implements OnReceiveMessageListener {

    private static final String TAG = "ReceiveMessageListener";

    private boolean isBackground = true;

    @Override
    public void onReceiveMessage(List<Message> messages, boolean hasMore) {
        // TODO 返回消息给前端

        Log.i(TAG, "接收消息:" + messages.size());
        // 触发前端监听器
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("data", messages);
        AbsSDKInstance mUniSDKInstance = ChatManagerHolder.mUniSDKInstance;
        /*if (null == mUniSDKInstance) {
            return;
        }*/
        long now = System.currentTimeMillis();
        long delta = ChatManager.Instance().getServerDeltaTime();
        if (messages != null) {
            for (Message msg : messages) {
                if (msg.content instanceof PCLoginRequestMessageContent && (now - (msg.serverTime - delta)) < 60 * 1000) {
                    PCLoginRequestMessageContent content = ((PCLoginRequestMessageContent) msg.content);
                    // appServiceProvider.showPCLoginActivity(ChatManager.Instance().getUserId(), content.getSessionId(), content.getPlatform());
                    break;
                }
            }
        }

        if (isBackground && !ChatManager.Instance().isMuteNotificationWhenPcOnline()) {
            if (messages == null) {
                return;
            }

            List<Message> msgs = new ArrayList<>(messages);
            Iterator<Message> iterator = msgs.iterator();
            while (iterator.hasNext()) {
                Message message = iterator.next();
                if (message.content.getPersistFlag() == PersistFlag.No_Persist
                        || now - (message.serverTime - delta) > 10 * 1000) {
                    iterator.remove();
                }
            }
            // WfcNotificationManager.getInstance().handleReceiveMessage(application, msgs);
        } else {
            // do nothing
        }
    }
}
