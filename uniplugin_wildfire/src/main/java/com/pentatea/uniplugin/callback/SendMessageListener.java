package com.pentatea.uniplugin.callback;

import cn.wildfirechat.message.Message;
import cn.wildfirechat.remote.OnSendMessageListener;

/**
 * 发送消息监听
 */
public class SendMessageListener implements OnSendMessageListener {
    @Override
    public void onSendSuccess(Message message) {

    }

    @Override
    public void onSendFail(Message message, int errorCode) {

    }

    @Override
    public void onSendPrepare(Message message, long savedTime) {

    }
}
