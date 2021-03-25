/*
 * Copyright (c) 2020 WildFireChat. All rights reserved.
 */

package cn.wildfirechat.model;

public class NullChatRoomInfo extends ChatRoomInfo {
    public NullChatRoomInfo(String chatRoomId) {
        this.chatRoomId = chatRoomId;
        //this.title = "<" + chatRoomId + ">";
        this.title = "聊天室";
    }
}