/*
 * Copyright (c) 2020 WildFireChat. All rights reserved.
 */

package cn.wildfirechat.remote;

import cn.wildfirechat.model.ChatRoomMembersInfo;

public interface GetChatRoomMembersInfoCallback {
    void onSuccess(ChatRoomMembersInfo chatRoomMembersInfo);

    void onFail(int errorCode);
}
