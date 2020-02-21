package com.lsun.myp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Person;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.ArrayList;
import java.util.Map;

public class FCM extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        SharedPreferences sp=getSharedPreferences("userName",MODE_PRIVATE);
        String notiTitle=sp.getString("userNickname",null);
        String notiBody="d";
        if(remoteMessage.getNotification()!=null){
            notiTitle=remoteMessage.getNotification().getTitle();
            notiBody=remoteMessage.getNotification().getBody();
            Log.i("message11",notiTitle+":"+notiBody);
        }
        String[] user={"nana","haha","lala"};
        NotificationManager notificationManager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder;//오레오 이상부터 필요로 함
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel channel=new NotificationChannel("ch01","channel",NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
            builder=new NotificationCompat.Builder(this,"ch01");
        }else {
            builder=new NotificationCompat.Builder(this,null);
        }



        builder.setSmallIcon(R.mipmap.ic_launcher_gotowork);
        builder.setContentTitle(notiTitle);
        builder.setContentText(notiBody);
        builder.setAutoCancel(true);
        Map<String,String> datas=remoteMessage.getData();
        if(datas!=null){
            String name=null;
            String msg=null;
            if(datas.size()>0){
                name=datas.get("name");
                msg=datas.get("msg");
                Log.i("message11",name+":"+msg);
            }
            Intent intent=new Intent(this,MainActivity.class);
            intent.putExtra("name",name);
            intent.putExtra("msg",msg);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            PendingIntent pendingIntent=PendingIntent.getActivity(this,100,intent,PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(pendingIntent);
        }



        Notification notification=builder.build();
        notificationManager.notify(10,notification);


    }

}
