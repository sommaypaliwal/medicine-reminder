package com.example.medicinereminder.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "medicine_history")
public class MedicineHistory {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int medicineId;
    private String medicineName;
    private String date;
    private String time;
    private String status;

    public MedicineHistory(
            int medicineId,
            String medicineName,
            String date,
            String time,
            String status) {

        this.medicineId = medicineId;
        this.medicineName = medicineName;
        this.date = date;
        this.time = time;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMedicineId() {
        return medicineId;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getStatus() {
        return status;
    }
}