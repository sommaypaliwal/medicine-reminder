package com.example.medicinereminder.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;

import androidx.core.app.NotificationCompat;

import com.example.medicinereminder.R;

public class NotificationHelper {

    private static final String CHANNEL_ID =
            "medicine_reminder";

    public static void createChannel(
            Context context) {

        NotificationChannel channel =
                new NotificationChannel(
                        CHANNEL_ID,
                        "Medicine Reminders",
                        NotificationManager.IMPORTANCE_HIGH
                );

        NotificationManager manager =
                (NotificationManager)
                        context.getSystemService(
                                Context.NOTIFICATION_SERVICE
                        );

        manager.createNotificationChannel(
                channel
        );
    }

    public static void showNotification(
            Context context,
            String medicineName,
            String dosage) {

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(
                        context,
                        CHANNEL_ID
                )
                        .setSmallIcon(
                                R.drawable.ic_medicine
                        )
                        .setContentTitle(
                                "💊 Medicine Reminder"
                        )
                        .setContentText(
                                "Time to take "
                                        + medicineName
                                        + " - "
                                        + dosage
                        )
                        .setPriority(
                                NotificationCompat.PRIORITY_HIGH
                        )
                        .setAutoCancel(true);

        NotificationManager manager =
                (NotificationManager)
                        context.getSystemService(
                                Context.NOTIFICATION_SERVICE
                        );

        manager.notify(
                (int) System.currentTimeMillis(),
                builder.build()
        );
    }
}