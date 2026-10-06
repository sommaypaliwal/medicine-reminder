package com.example.medicinereminder.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.medicinereminder.R;
import com.example.medicinereminder.data.Medicine;
import com.example.medicinereminder.data.MedicineDatabase;
import com.example.medicinereminder.data.MedicineHistory;
import com.example.medicinereminder.notification.AlarmScheduler;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MedicineDetailActivity extends AppCompatActivity {

    TextView detailsTextView;
    Button takenButton;
    Button missedButton;
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

        takenButton =
                findViewById(R.id.takenButton);

        missedButton =
                findViewById(R.id.missedButton);

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

        takenButton.setOnClickListener(
                v -> saveHistory("Taken")
        );

        missedButton.setOnClickListener(
                v -> saveHistory("Missed")
        );

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

    private void saveHistory(String status) {

        if (medicine == null) {
            return;
        }

        String date =
                new SimpleDateFormat(
                        "dd/MM/yyyy",
                        Locale.getDefault()
                ).format(new Date());

        String time =
                new SimpleDateFormat(
                        "HH:mm",
                        Locale.getDefault()
                ).format(new Date());

        MedicineHistory history =
                new MedicineHistory(
                        medicine.getId(),
                        medicine.getName(),
                        date,
                        time,
                        status
                );

        MedicineDatabase
                .getInstance(this)
                .medicineDao()
                .insertHistory(history);

        Toast.makeText(
                this,
                "Marked as " + status,
                Toast.LENGTH_SHORT
        ).show();
    }
}