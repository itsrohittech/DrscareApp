package com.simats.drscareapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "drscareapp.db";
    private static final int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create your database tables if they don't exist
        db.execSQL("CREATE TABLE IF NOT EXISTS checkboxes_values (s_no INTEGER PRIMARY KEY, checkbox_id TEXT, value TEXT, total TEXT, clicked_checkbox TEXT, patient_id TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Upgrade your database if needed
    }

    // Method to fetch total score from the database
    @SuppressLint("Range")
    public int fetchTotalScore() {
        int totalScore = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(value) AS total FROM checkboxes_values", null);
        if (cursor.moveToFirst()) {
            totalScore = cursor.getInt(cursor.getColumnIndex("total"));
        }
        cursor.close();
        db.close();
        return totalScore;
    }

    // Method to fetch scored values from the database
    public String fetchScoredValues() {
        StringBuilder scoredValues = new StringBuilder();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT value FROM checkboxes_values", null);
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String value = cursor.getString(cursor.getColumnIndex("value"));
                scoredValues.append(value).append(", ");
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return scoredValues.toString();
    }
}

