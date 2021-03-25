/*
 * Copyright (c) 2020 WildFireChat. All rights reserved.
 */

package com.pentatea.uniplugin;

import android.content.Context;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import cn.wildfirechat.remote.ChatManager;
import io.dcloud.feature.uniapp.AbsSDKInstance;

//import cn.wildfirechat.avenginekit.AVEngineKit;

/**
 * Created by WF Chat on 2017/12/12.
 * 全局状态
 */
public class ChatManagerHolder {
    public static WildfireModule gWildfireModule;
    public static ChatManager gChatManager;
    public static Context gContext;
    public static AbsSDKInstance mUniSDKInstance;
    public static Queue<ArrayList> qGlobalEvent = new LinkedList<>();
//    public static AVEngineKit gAVEngine;
}
