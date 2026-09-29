package com.example.medicinereminder;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.medicinereminder.notification.NotificationHelper;
import com.example.medicinereminder.ui.AddMedicineActivity;
import com.example.medicinereminder.ui.MedicineListActivity;

public class MainActivity extends AppCompatActivity {

    Button addMedicineButton;
    Button viewMedicinesButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        NotificationHelper.createChannel(this);

        addMedicineButton = findViewById(R.id.addMedicineButton);
        viewMedicinesButton = findViewById(R.id.viewMedicinesButton);

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
}