package com.example.medicinereminder.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.medicinereminder.R;
import com.example.medicinereminder.data.Medicine;
import com.example.medicinereminder.data.MedicineDatabase;
import com.example.medicinereminder.notification.AlarmScheduler;

public class MedicineDetailActivity extends AppCompatActivity {

    TextView detailsTextView;
    Button deleteButton;

    Medicine medicine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(
                R.layout.activity_medicine_detail
        );

        detailsTextView =
                findViewById(R.id.detailsTextView);

        deleteButton =
                findViewById(R.id.deleteButton);

        int medicineId =
                getIntent().getIntExtra(
                        "medicineId",
                        -1
                );

        medicine =
                MedicineDatabase
                        .getInstance(this)
                        .medicineDao()
                        .getMedicineById(medicineId);

        if (medicine != null) {

            String details =
                    "Medicine: " + medicine.getName() +
                            "\n\nType: " + medicine.getType() +
                            "\nDosage: " + medicine.getDosage() +
                            "\nTime: " + medicine.getTime() +
                            "\nFrequency: " + medicine.getFrequency() +
                            "\nStart Date: " + medicine.getStartDate() +
                            "\nEnd Date: " + medicine.getEndDate() +
                            "\nFood: " + medicine.getFoodInstruction() +
                            "\nNotes: " + medicine.getNotes();

            detailsTextView.setText(details);
        }

        deleteButton.setOnClickListener(v -> {

            if (medicine != null) {

                AlarmScheduler.cancelAlarm(
                        this,
                        medicine
                );

                MedicineDatabase
                        .getInstance(this)
                        .medicineDao()
                        .delete(medicine);

                Toast.makeText(
                        this,
                        "Medicine deleted",
                        Toast.LENGTH_SHORT
                ).show();

                finish();
            }
        });
    }
}