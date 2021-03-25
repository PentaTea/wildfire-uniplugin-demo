package com.pentatea.uniplugin;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import java.util.ArrayList;
import java.util.List;

import cn.wildfirechat.model.Conversation;

import static androidx.core.app.NotificationCompat.CATEGORY_MESSAGE;
import static androidx.core.app.NotificationCompat.DEFAULT_ALL;

public class WfcNotificationManager {

    private WfcNotificationManager() {

    }

    private static WfcNotificationManager notificationManager;

    public synchronized static WfcNotificationManager getInstance() {
        if (notificationManager == null) {
            notificationManager = new WfcNotificationManager();
        }
        return notificationManager;
    }

    public void showNotification(Context context, String tag, int id, String title, String content, PendingIntent pendingIntent) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        String channelId = "wfc_notification";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "wildfire chat message",
                    NotificationManager.IMPORTANCE_HIGH);

            channel.enableLights(true); //是否在桌面icon右上角展示小红点
            channel.setLightColor(Color.GREEN); //小红点颜色
            channel.setShowBadge(true); //是否在久按桌面图标时显示此渠道的通知
            notificationManager.createNotificationChannel(channel);
        }


        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.icon)
                .setAutoCancel(true)
                .setCategory(CATEGORY_MESSAGE)
                .setDefaults(DEFAULT_ALL);
        builder.setContentIntent(pendingIntent);
        builder.setContentTitle(title);
        builder.setContentText(content);

        notificationManager.notify(tag, id, builder.build());
    }

    private List<Conversation> notificationConversations = new ArrayList<>();

    public int notificationId(Conversation conversation) {
        if (!notificationConversations.contains(conversation)) {
            notificationConversations.add(conversation);
        }
        return notificationConversations.indexOf(conversation);
    }

    public static String appName(Context context) {
        String name = "";
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(ChatManagerHolder.gContext.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            name = ChatManagerHolder.gContext.getResources().getString(labelRes);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return name;
    }

}
