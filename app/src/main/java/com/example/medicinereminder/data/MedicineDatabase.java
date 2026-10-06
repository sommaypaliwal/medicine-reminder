package com.example.medicinereminder.data;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(
        entities = {Medicine.class, MedicineHistory.class},
        version = 2,
        exportSchema = false
)
public abstract class MedicineDatabase extends RoomDatabase {

    public abstract MedicineDao medicineDao();

    private static MedicineDatabase instance;

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {

            database.execSQL(
                    "CREATE TABLE IF NOT EXISTS medicine_history (" +
                            "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                            "medicineId INTEGER NOT NULL, " +
                            "medicineName TEXT, " +
                            "date TEXT, " +
                            "time TEXT, " +
                            "status TEXT)"
            );
        }
    };

    public static synchronized MedicineDatabase getInstance(Context context) {

        if (instance == null) {

            instance = Room.databaseBuilder(
                            context.getApplicationContext(),
                            MedicineDatabase.class,
                            "medicine_database"
                    )
                    .addMigrations(MIGRATION_1_2)
                    .allowMainThreadQueries()
                    .build();
        }

        return instance;
    }
}