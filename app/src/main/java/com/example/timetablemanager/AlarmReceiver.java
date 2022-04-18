package com.example.timetablemanager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.Settings;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;


public class AlarmReceiver extends BroadcastReceiver {
    public static final String EXTRA_TITLE = "com.example.timetablemanager.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION = "com.example.timetablemanager.EXTRA_DESCRIPTION";
    public static final String EXTRA_ID = "com.example.timetablemanager.EXTRA_ID";
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "task")
                .setSmallIcon(R.drawable.kisspng_computer_icons_vector_graphics_transparency_flat_d_appevent_logger_app_5d0f0ccb07e036_6610244615612674030323)
                .setContentTitle(intent.getStringExtra(EXTRA_TITLE))
                .setContentText(intent.getStringExtra(EXTRA_DESCRIPTION))
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH);
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(intent.getIntExtra(EXTRA_ID, 0),builder.build());
    }
}
