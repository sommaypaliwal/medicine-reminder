package com.example.medicinereminder.ui;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.medicinereminder.R;
import com.example.medicinereminder.data.MedicineDatabase;
import com.example.medicinereminder.data.MedicineHistory;

import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    ListView historyListView;
    TextView emptyTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_history);

        historyListView =
                findViewById(R.id.historyListView);

        emptyTextView =
                findViewById(R.id.emptyTextView);

        loadHistory();
    }

    private void loadHistory() {

        List<MedicineHistory> history =
                MedicineDatabase
                        .getInstance(this)
                        .medicineDao()
                        .getAllHistory();

        if (history.isEmpty()) {

            emptyTextView.setText(
                    "No medicine history yet"
            );

            historyListView.setAdapter(null);

            return;
        }

        emptyTextView.setText("");

        String[] historyItems =
                new String[history.size()];

        for (int i = 0; i < history.size(); i++) {

            MedicineHistory item =
                    history.get(i);

            historyItems[i] =
                    item.getMedicineName() +
                            "\nDate: " + item.getDate() +
                            "\nTime: " + item.getTime() +
                            "\nStatus: " + item.getStatus();
        }

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_list_item_1,
                        historyItems
                );

        historyListView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (historyListView != null) {
            loadHistory();
        }
    }
}