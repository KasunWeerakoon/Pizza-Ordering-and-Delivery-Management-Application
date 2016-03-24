package com.genius.pizzahutmanager;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

// You must change these imports to yours
//import com.inventio.android.uludag.MainActivity;
//import com.inventio.android.uludag.R;

public class CustomNotificationReceiver extends BroadcastReceiver {

    NotificationCompat.Builder mBuilder;
    Intent resultIntent;
    int mNotificationId = 001;
    Uri notifySound;

    String alert; // This is the message string that send from push console

    @Override
    public void onReceive(Context context, Intent intent) {

//Get JSON data and put them into variables
        try {

            JSONObject json = new JSONObject(intent.getExtras().getString(
                    "com.parse.Data"));

            alert = json.getString("alert").toString();
            if(alert.equals("new order")){
                MainActivity.remoteTask.execute();
            }else{
                /*notifySound = RingtoneManager
                        .getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

                mBuilder = new NotificationCompat.Builder(context);
                mBuilder.setSmallIcon(R.drawable.ic_launcher); //You can change your icon
                mBuilder.setContentText(alert);
                mBuilder.setContentTitle("Your notification title");
                mBuilder.setSound(notifySound);
                mBuilder.setAutoCancel(true);

// this is the activity that we will send the user, change this to anything you want
                resultIntent = new Intent(context, MainActivity.class);

                PendingIntent resultPendingIntent = PendingIntent.getActivity(context,
                        0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                mBuilder.setContentIntent(resultPendingIntent);

                NotificationManager notificationManager = (NotificationManager) context
                        .getSystemService(context.NOTIFICATION_SERVICE);

                notificationManager.notify(mNotificationId, mBuilder.build());*/
            }
        } catch (JSONException e) {

        }

//You can specify sound


    }

}