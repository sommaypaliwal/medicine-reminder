package com.example.medicinereminder.notification;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.net.Uri;

import com.example.medicinereminder.data.Medicine;

import java.util.Calendar;

public class AlarmScheduler {

    public static void scheduleAlarm(
            Context context,
            Medicine medicine) {

        String[] time =
                medicine.getTime().split(":");

        int hour =
                Integer.parseInt(time[0]);

        int minute =
                Integer.parseInt(time[1]);

        Calendar calendar =
                Calendar.getInstance();

        calendar.set(
                Calendar.HOUR_OF_DAY,
                hour
        );

        calendar.set(
                Calendar.MINUTE,
                minute
        );

        calendar.set(
                Calendar.SECOND,
                0
        );

        calendar.set(
                Calendar.MILLISECOND,
                0
        );

        if (calendar.getTimeInMillis()
                <= System.currentTimeMillis()) {

            calendar.add(
                    Calendar.DAY_OF_YEAR,
                    1
            );
        }

        Intent intent =
                new Intent(
                        context,
                        ReminderReceiver.class
                );

        intent.putExtra(
                "medicineId",
                medicine.getId()
        );

        intent.putExtra(
                "medicineName",
                medicine.getName()
        );

        intent.putExtra(
                "dosage",
                medicine.getDosage()
        );

        PendingIntent pendingIntent =
                PendingIntent.getBroadcast(
                        context,
                        medicine.getId(),
                        intent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                                | PendingIntent.FLAG_IMMUTABLE
                );

        AlarmManager alarmManager =
                (AlarmManager)
                        context.getSystemService(
                                Context.ALARM_SERVICE
                        );

        if (alarmManager == null) {
            return;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {

            if (!alarmManager.canScheduleExactAlarms()) {

                Intent settingsIntent =
                        new Intent(
                                Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM
                        );

                settingsIntent.setData(
                        Uri.parse(
                                "package:" +
                                        context.getPackageName()
                        )
                );

                settingsIntent.addFlags(
                        Intent.FLAG_ACTIVITY_NEW_TASK
                );

                context.startActivity(settingsIntent);

                return;
            }
        }

        alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(),
                pendingIntent
        );
    }

    public static void cancelAlarm(
            Context context,
            Medicine medicine) {

        Intent intent =
                new Intent(
                        context,
                        ReminderReceiver.class
                );

        PendingIntent pendingIntent =
                PendingIntent.getBroadcast(
                        context,
                        medicine.getId(),
                        intent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                                | PendingIntent.FLAG_IMMUTABLE
                );

        AlarmManager alarmManager =
                (AlarmManager)
                        context.getSystemService(
                                Context.ALARM_SERVICE
                        );

        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }
    }
}