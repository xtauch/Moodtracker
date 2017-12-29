package com.example.youpiman.moodtracker.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class SQLiteDataBaseHelper extends SQLiteOpenHelper{


    private static final String TABLE_NAME = "Comment_table";
    private static final String ID = "ID";
    private static final String Date = "Date";
    private static final String Mood = "Mood";
    private static final String Comment = "Comment";

    private static final String TABLE_CREATE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + Date + " INTEGER," + Mood + " TEXT," + Comment + " TEXT);";

    private static final String TABLE_DROP = "DROP TABLE IF EXISTS " + TABLE_NAME;


    SQLiteDataBaseHelper (Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(TABLE_DROP);
        onCreate(db);
    }
}
