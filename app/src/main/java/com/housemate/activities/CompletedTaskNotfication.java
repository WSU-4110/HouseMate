package com.housemate.activities;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

public class CompletedTaskNotfication extends Context {
    String message = " A task has been Completed!";
    NotificationCompat.Builder builder = new NotificationCompat.Builder(
            CompletedTaskNotfication.this
    )
            .setSmallIcon(R.drawable.ic_baseline)
            .setContentTitle("New Notification")
            .setContentText(message)
            .setAutoCancel(true);

    Intent intent2 = new Intent(CompletedTaskNotfication.this,
            NotificationActivity.class);

                intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent2.putExtra("message",message);

    PendingIntent pendingIntent = PendingIntent.getActivity(CompletedTaskNotfication.this, 0, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
                builder.setContentIntent(pendingIntent);

    NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

                notificationManager.notify(0, builder.build());
}
