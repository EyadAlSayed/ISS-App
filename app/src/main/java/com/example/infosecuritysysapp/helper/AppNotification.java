package com.example.infosecuritysysapp.helper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.widget.RemoteViews;

import androidx.annotation.RequiresApi;

import com.example.infosecuritysysapp.R;

public class AppNotification {

    private NotificationManager notificationManager;
    private NotificationChannel notificationChannel;
    private Notification.Builder builder;
    private final String channelId = "i.apps.notifications";
    private final String description = "Test notification";

    private Activity activity;


    public AppNotification(Activity activity) {
        notificationManager = (NotificationManager) activity.getSystemService(Context.NOTIFICATION_SERVICE);


    }
    @SuppressLint("RemoteViewLayout")
    public void build(String message) {
        RemoteViews remoteViews = new RemoteViews(activity.getPackageName(), R.layout.app_notification_layout);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
           buildOreoVersion(remoteViews);
        }
        else {
            buildOtherVersion(remoteViews);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void buildOreoVersion(RemoteViews remoteViews){
        notificationChannel = new  NotificationChannel(channelId, description, NotificationManager.IMPORTANCE_HIGH);
        notificationChannel.enableLights(true);
        notificationChannel.setLightColor(Color.GREEN);
        notificationChannel.enableVibration(false);
        notificationManager.createNotificationChannel(notificationChannel);

        builder = new Notification.Builder((Context) activity, channelId)
                .setContent(remoteViews)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setLargeIcon(BitmapFactory.decodeResource(activity.getResources(), R.drawable.ic_launcher_background));
//                    .setContentIntent(pendingIntent);
    }

    private void buildOtherVersion(RemoteViews remoteViews){
        builder =new  Notification.Builder((Context) activity)
                .setContent(remoteViews)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setLargeIcon(BitmapFactory.decodeResource(activity.getResources(), R.drawable.ic_launcher_background));
//                .setContentIntent(pendingIntent)
    }
}
