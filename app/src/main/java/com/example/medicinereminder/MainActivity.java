package com.example.medicinereminder;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.medicinereminder.notification.NotificationHelper;
import com.example.medicinereminder.ui.AddMedicineActivity;
import com.example.medicinereminder.ui.MedicineListActivity;

public class MainActivity extends AppCompatActivity {

    Button addMedicineButton;
    Button viewMedicinesButton;

    private static final int NOTIFICATION_PERMISSION_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        NotificationHelper.createChannel(this);

        requestNotificationPermission();

        addMedicineButton =
                findViewById(R.id.addMedicineButton);

        viewMedicinesButton =
                findViewById(R.id.viewMedicinesButton);

        addMedicineButton.setOnClickListener(v -> {

            Intent intent = new Intent(
                    MainActivity.this,
                    AddMedicineActivity.class
            );

            startActivity(intent);
        });

        viewMedicinesButton.setOnClickListener(v -> {

            Intent intent = new Intent(
                    MainActivity.this,
                    MedicineListActivity.class
            );

            startActivity(intent);
        });
    }

    private void requestNotificationPermission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(
                        this,
                        new String[]{
                                Manifest.permission.POST_NOTIFICATIONS
                        },
                        NOTIFICATION_PERMISSION_CODE
                );
            }
        }
    }
}