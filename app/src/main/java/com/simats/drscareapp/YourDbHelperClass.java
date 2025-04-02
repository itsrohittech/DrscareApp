package com.simats.drscareapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class YourDbHelperClass extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "drscareapp.db";
    private static final int DATABASE_VERSION = 1;

    public YourDbHelperClass(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create your table
        String SQL_CREATE_ENTRIES =
                "CREATE TABLE your_table_name (" +
                        "id INTEGER PRIMARY KEY," +
                        "name TEXT," +
                        "age INTEGER)";

        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Upgrade your database if needed
        db.execSQL("DROP TABLE IF EXISTS your_table_name");
        onCreate(db);
    }
}
