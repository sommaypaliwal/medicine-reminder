package com.example.medicinereminder.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.medicinereminder.R;
import com.example.medicinereminder.data.Medicine;
import com.example.medicinereminder.data.MedicineDatabase;

import java.util.List;

public class MedicineListActivity extends AppCompatActivity {

    ListView medicineListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_medicine_list);

        medicineListView =
                findViewById(R.id.medicineListView);

        loadMedicines();
    }

    private void loadMedicines() {

        List<Medicine> medicines =
                MedicineDatabase
                        .getInstance(this)
                        .medicineDao()
                        .getAllMedicines();

        String[] medicineNames =
                new String[medicines.size()];

        for (int i = 0; i < medicines.size(); i++) {

            Medicine medicine = medicines.get(i);

            medicineNames[i] =
                    medicine.getName()
                            + " - "
                            + medicine.getDosage()
                            + " - "
                            + medicine.getTime();
        }

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_list_item_1,
                        medicineNames
                );

        medicineListView.setAdapter(adapter);

        medicineListView.setOnItemClickListener(
                (parent, view, position, id) -> {

                    Medicine selected =
                            medicines.get(position);

                    Intent intent =
                            new Intent(
                                    this,
                                    MedicineDetailActivity.class
                            );

                    intent.putExtra(
                            "medicineId",
                            selected.getId()
                    );

                    startActivity(intent);
                }
        );
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadMedicines();
    }
}