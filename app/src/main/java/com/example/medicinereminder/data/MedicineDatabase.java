package com.example.medicinereminder.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(
        entities = {Medicine.class},
        version = 1,
        exportSchema = false
)
public abstract class MedicineDatabase extends RoomDatabase {

    public abstract MedicineDao medicineDao();

    private static MedicineDatabase instance;

    public static synchronized MedicineDatabase getInstance(Context context) {

        if (instance == null) {

            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    MedicineDatabase.class,
                    "medicine_database"
            ).allowMainThreadQueries().build();
        }

        return instance;
    }
}