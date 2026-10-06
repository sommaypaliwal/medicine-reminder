package com.example.medicinereminder.ui;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.medicinereminder.R;
import com.example.medicinereminder.data.Medicine;
import com.example.medicinereminder.data.MedicineDatabase;
import com.example.medicinereminder.notification.AlarmScheduler;

import java.util.Calendar;

public class AddMedicineActivity extends AppCompatActivity {

    EditText nameEditText;
    EditText dosageEditText;
    EditText notesEditText;

    Spinner typeSpinner;
    Spinner frequencySpinner;
    Spinner foodSpinner;

    TextView timeTextView;
    TextView startDateTextView;
    TextView endDateTextView;

    Button saveButton;

    String selectedTime = "";
    String selectedStartDate = "";
    String selectedEndDate = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_medicine);

        nameEditText = findViewById(R.id.nameEditText);
        dosageEditText = findViewById(R.id.dosageEditText);
        notesEditText = findViewById(R.id.notesEditText);

        typeSpinner = findViewById(R.id.typeSpinner);
        frequencySpinner = findViewById(R.id.frequencySpinner);
        foodSpinner = findViewById(R.id.foodSpinner);

        timeTextView = findViewById(R.id.timeTextView);
        startDateTextView = findViewById(R.id.startDateTextView);
        endDateTextView = findViewById(R.id.endDateTextView);

        saveButton = findViewById(R.id.saveButton);

        setupSpinners();

        timeTextView.setOnClickListener(v -> showTimePicker());

        startDateTextView.setOnClickListener(v ->
                showDatePicker(startDateTextView));

        endDateTextView.setOnClickListener(v ->
                showDatePicker(endDateTextView));

        saveButton.setOnClickListener(v -> saveMedicine());
    }

    private void setupSpinners() {

        String[] types = {
                "Tablet",
                "Capsule",
                "Syrup",
                "Drops",
                "Injection",
                "Other"
        };

        String[] frequencies = {
                "Once Daily",
                "Twice Daily",
                "Three Times Daily",
                "Custom"
        };

        String[] foodOptions = {
                "Before Food",
                "After Food",
                "With Food",
                "Anytime"
        };

        typeSpinner.setAdapter(
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_spinner_dropdown_item,
                        types));

        frequencySpinner.setAdapter(
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_spinner_dropdown_item,
                        frequencies));

        foodSpinner.setAdapter(
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_spinner_dropdown_item,
                        foodOptions));
    }

    private void showTimePicker() {

        Calendar calendar = Calendar.getInstance();

        TimePickerDialog dialog = new TimePickerDialog(
                this,
                (view, hourOfDay, minute) -> {

                    selectedTime = String.format(
                            "%02d:%02d",
                            hourOfDay,
                            minute
                    );

                    timeTextView.setText(selectedTime);

                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true
        );

        dialog.show();
    }

    private void showDatePicker(TextView textView) {

        Calendar calendar = Calendar.getInstance();

        DatePickerDialog dialog = new DatePickerDialog(
                this,
                (view, year, month, day) -> {

                    String date =
                            day + "/" +
                                    (month + 1) + "/" +
                                    year;

                    textView.setText(date);

                    if (textView == startDateTextView) {
                        selectedStartDate = date;
                    } else {
                        selectedEndDate = date;
                    }

                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );

        dialog.show();
    }

    private void saveMedicine() {

        String name = nameEditText.getText().toString().trim();
        String dosage = dosageEditText.getText().toString().trim();
        String notes = notesEditText.getText().toString().trim();

        if (name.isEmpty()) {
            nameEditText.setError("Enter medicine name");
            return;
        }

        if (dosage.isEmpty()) {
            dosageEditText.setError("Enter dosage");
            return;
        }

        if (selectedTime.isEmpty()) {
            Toast.makeText(
                    this,
                    "Select reminder time",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        MedicineDatabase database =
                MedicineDatabase.getInstance(this);

        Medicine existingMedicine =
                database
                        .medicineDao()
                        .getMedicineByNameAndTime(
                                name,
                                selectedTime
                        );

        if (existingMedicine != null) {

            Toast.makeText(
                    this,
                    "Medicine already exists at this time",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        Medicine medicine = new Medicine(
                name,
                typeSpinner.getSelectedItem().toString(),
                dosage,
                selectedTime,
                frequencySpinner.getSelectedItem().toString(),
                selectedStartDate,
                selectedEndDate,
                foodSpinner.getSelectedItem().toString(),
                notes
        );

        database
                .medicineDao()
                .insert(medicine);

        AlarmScheduler.scheduleAlarm(
                this,
                medicine
        );

        Toast.makeText(
                this,
                "Medicine added successfully",
                Toast.LENGTH_SHORT
        ).show();

        finish();
    }
}