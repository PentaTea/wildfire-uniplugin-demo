package com.pentatea.uniplugin;

import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pentatea.uniplugin.callback.ConnectionStatusChangeListener;
import com.pentatea.uniplugin.callback.ReceiveMessageListener;
import com.pentatea.uniplugin.callback.WFAVEngineCallback;

import java.io.File;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.wildfirechat.client.NotInitializedExecption;
import cn.wildfirechat.remote.ChatManager;
import io.dcloud.feature.uniapp.UniAppHookProxy;

public class WildfireUniAppHookProxy implements UniAppHookProxy {

    private static final String TAG = "WildfireUniAppHookProxy";

    private static ReceiveMessageListener receiveMessageListener = new ReceiveMessageListener();

    private static ConnectionStatusChangeListener connectionStatusChangeListener = new ConnectionStatusChangeListener();

    private static WFAVEngineCallback wfavEngineCallback = new WFAVEngineCallback();

    @Override
    public void onSubProcessCreate(Application application) {
        initWFClient(application);
    }

    @Override
    public void onCreate(Application application) {
        initWFClient(application);
    }

    private void initWFClient(Application application) {
        String IM_SERVER_HOST = null;
        try {
            ApplicationInfo mApplicationInfo = application.getApplicationContext().getPackageManager().getApplicationInfo(application.getApplicationContext().getPackageName(),
                    PackageManager.GET_META_DATA);
            if (mApplicationInfo.metaData != null) {
                IM_SERVER_HOST = (String) mApplicationInfo.metaData.getString("IM_SERVER_HOST");
            }

        } catch (NullPointerException e) {
            Log.e("crsh:", "报空指针异常");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (IM_SERVER_HOST == null) {
            Log.e(TAG, "必须填写 IM_SERVER_HOST");
            Toast.makeText(application, IM_SERVER_HOST + "缺少IM_SERVER_HOST,应用即将关闭...", Toast.LENGTH_LONG).show();
            new Handler().postDelayed(() -> {
                throw new IllegalArgumentException("缺少IM_SERVER_HOST,应用即将关闭...\n");
            }, 5 * 1000);
        }

        // 只在主进程进行初始化
        // 通讯初始化
        Log.i(TAG, "初始化野火sdk");
        ChatManager.init(application, IM_SERVER_HOST);
        try {
            // 状态保存
            ChatManagerHolder.gChatManager = ChatManager.Instance();
            ChatManagerHolder.gContext = application;

            /*ChatManagerHolder.gChatManager.addOnReceiveMessageListener(receiveMessageListener);
            // ChatManagerHolder.gChatManager.addRecallMessageListener(this);
            // ChatManagerHolder.gChatManager.addFriendUpdateListener(this);
            ChatManagerHolder.gChatManager.addConnectionChangeListener(connectionStatusChangeListener);*/
            ChatManagerHolder.gChatManager.startLog();
        } catch (NotInitializedExecption notInitializedExecption) {
            notInitializedExecption.printStackTrace();
        }

        Log.i(TAG, "初始化事件处理");
        try {
            Class<?> ChatManagerClazz = ChatManagerHolder.gChatManager.getClass();
            Method[] ChatManagerMethods = ChatManagerClazz.getDeclaredMethods();

            Pattern pattern = Pattern.compile("add(.*)Listener");

            for (Method method : ChatManagerMethods) {
                Matcher matcher = pattern.matcher(method.getName());
                if (matcher.find()) {
                    Class[] paramTypes = method.getParameterTypes();
                    Log.i(TAG, paramTypes[0].getDeclaredMethods()[0].getName());
                    WildfireListenerHandler wildfireListenerHandler = new WildfireListenerHandler();
                    Object Listener = Proxy.newProxyInstance(
                            WildfireModule.class.getClassLoader(),
                            new Class[]{paramTypes[0]},
                            wildfireListenerHandler);
                    method.invoke(ChatManagerHolder.gChatManager, Listener);
                }
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        // PushService.init(application,  BuildConfig.LIBRARY_PACKAGE_NAME);
        // PushService.init(application, application.getPackageName());

        Log.i(TAG, "初始化文件系统");
        setupWFCDirs();

        Log.i(TAG, "cid: " + ChatManagerHolder.gChatManager.getClientId());
    }

    private void setupWFCDirs() {
        File file = new File(Config.VIDEO_SAVE_DIR);
        if (!file.exists()) {
            file.mkdirs();
        }
        file = new File(Config.AUDIO_SAVE_DIR);
        if (!file.exists()) {
            file.mkdirs();
        }
        file = new File(Config.FILE_SAVE_DIR);
        if (!file.exists()) {
            file.mkdirs();
        }
        file = new File(Config.PHOTO_SAVE_DIR);
        if (!file.exists()) {
            file.mkdirs();
        }
    }
}

class WildfireListenerHandler implements InvocationHandler {
    private static final String TAG = "WildfireListenerHandler";

    /**
     * @param proxy  所代理的那个真实对象
     * @param method 我们所要调用真实对象的某个方法的Method对象
     * @param args   调用真实对象某个方法时接受的参数
     * @return 代理执行完方法所返回的对象
     * @throws Throwable 执行过程抛出的各种异常
     */
    @RequiresApi(Build.VERSION_CODES.O)
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        JSONArray array = new JSONArray();
        array.add(method.getName());
        if (args != null)
            for (Object e : args) {
                array.add(e);
            }
        JSONObject object = new JSONObject();
        object.put("data", array);
        object.put("timestamp", System.currentTimeMillis());
        Log.i(TAG, MessageFormat.format("事件[{0}]:{1}", method.getName(), array.toJSONString()));
        if (ChatManagerHolder.mUniSDKInstance == null)
            ChatManagerHolder.qGlobalEvent.offer(new ArrayList() {{
                add("wildfire");
                add(object);
            }});
        else ChatManagerHolder.mUniSDKInstance.fireGlobalEventCallback("wildfire", object);
        return null;
    }
}

