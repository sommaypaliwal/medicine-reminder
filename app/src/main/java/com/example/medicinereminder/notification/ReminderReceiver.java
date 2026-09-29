package com.example.medicinereminder.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ReminderReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(
            Context context,
            Intent intent) {

        String medicineName =
                intent.getStringExtra(
                        "medicineName"
                );

        String dosage =
                intent.getStringExtra(
                        "dosage"
                );

        NotificationHelper.showNotification(
                context,
                medicineName,
                dosage
        );
    }
}