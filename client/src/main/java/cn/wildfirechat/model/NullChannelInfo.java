/*
 * Copyright (c) 2020 WildFireChat. All rights reserved.
 */

package cn.wildfirechat.model;

public class NullChannelInfo extends ChannelInfo {
    public NullChannelInfo(String channelId) {
        this.channelId = channelId;
        //this.name = "<" + channelId + ">";
        this.name = "频道";
        this.owner = "";
    }
}
