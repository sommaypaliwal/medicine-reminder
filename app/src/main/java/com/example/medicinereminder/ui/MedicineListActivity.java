package com.example.medicinereminder.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.medicinereminder.R;
import com.example.medicinereminder.data.Medicine;
import com.example.medicinereminder.data.MedicineDatabase;

import java.util.ArrayList;
import java.util.List;

public class MedicineListActivity extends AppCompatActivity {

    ListView medicineListView;
    SearchView searchView;
    Spinner typeFilterSpinner;

    List<Medicine> allMedicines = new ArrayList<>();
    List<Medicine> filteredMedicines = new ArrayList<>();

    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_medicine_list);

        medicineListView = findViewById(R.id.medicineListView);
        searchView = findViewById(R.id.searchView);
        typeFilterSpinner = findViewById(R.id.typeFilterSpinner);

        setupTypeFilter();
        setupSearch();

        medicineListView.setOnItemClickListener(
                (parent, view, position, id) -> {

                    Medicine selected =
                            filteredMedicines.get(position);

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

        loadMedicines();
    }

    private void setupTypeFilter() {

        String[] types = {
                "All Types",
                "Tablet",
                "Capsule",
                "Syrup",
                "Drops",
                "Injection",
                "Other"
        };

        ArrayAdapter<String> typeAdapter =
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_spinner_dropdown_item,
                        types
                );

        typeFilterSpinner.setAdapter(typeAdapter);

        typeFilterSpinner.setOnItemSelectedListener(
                new android.widget.AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(
                            android.widget.AdapterView<?> parent,
                            android.view.View view,
                            int position,
                            long id) {

                        applyFilters();
                    }

                    @Override
                    public void onNothingSelected(
                            android.widget.AdapterView<?> parent) {
                    }
                }
        );
    }

    private void setupSearch() {

        searchView.setOnQueryTextListener(
                new SearchView.OnQueryTextListener() {

                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        applyFilters();
                        return true;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        applyFilters();
                        return true;
                    }
                }
        );
    }

    private void loadMedicines() {

        allMedicines =
                MedicineDatabase
                        .getInstance(this)
                        .medicineDao()
                        .getAllMedicines();

        applyFilters();
    }

    private void applyFilters() {

        String searchText =
                searchView == null ||
                        searchView.getQuery() == null
                        ? ""
                        : searchView.getQuery()
                        .toString()
                        .trim()
                        .toLowerCase();

        String selectedType =
                typeFilterSpinner == null ||
                        typeFilterSpinner.getSelectedItem() == null
                        ? "All Types"
                        : typeFilterSpinner
                        .getSelectedItem()
                        .toString();

        filteredMedicines.clear();

        for (Medicine medicine : allMedicines) {

            boolean matchesName =
                    medicine.getName()
                            .toLowerCase()
                            .contains(searchText);

            boolean matchesType =
                    selectedType.equals("All Types") ||
                            medicine.getType()
                                    .equals(selectedType);

            if (matchesName && matchesType) {
                filteredMedicines.add(medicine);
            }
        }

        String[] medicineNames =
                new String[filteredMedicines.size()];

        for (int i = 0; i < filteredMedicines.size(); i++) {

            Medicine medicine =
                    filteredMedicines.get(i);

            medicineNames[i] =
                    medicine.getName()
                            + " - "
                            + medicine.getDosage()
                            + " - "
                            + medicine.getTime();
        }

        adapter =
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_list_item_1,
                        medicineNames
                );

        medicineListView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (medicineListView != null) {
            loadMedicines();
        }
    }
}