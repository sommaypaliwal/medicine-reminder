package com.example.medicinereminder.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "medicines")
public class Medicine {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;
    private String type;
    private String dosage;
    private String time;
    private String frequency;
    private String startDate;
    private String endDate;
    private String foodInstruction;
    private String notes;

    public Medicine(
            String name,
            String type,
            String dosage,
            String time,
            String frequency,
            String startDate,
            String endDate,
            String foodInstruction,
            String notes) {

        this.name = name;
        this.type = type;
        this.dosage = dosage;
        this.time = time;
        this.frequency = frequency;
        this.startDate = startDate;
        this.endDate = endDate;
        this.foodInstruction = foodInstruction;
        this.notes = notes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getDosage() {
        return dosage;
    }

    public String getTime() {
        return time;
    }

    public String getFrequency() {
        return frequency;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getFoodInstruction() {
        return foodInstruction;
    }

    public String getNotes() {
        return notes;
    }
}