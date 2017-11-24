package com.example.youpiman.moodtracker.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Youpiman on 17/11/2017.
 */

public class SQLiteDataBaseHelper extends SQLiteOpenHelper{

    public static final String TABLE_NAME = "Comment_table";
    public static final String COL_0= "ID";
    public static final String COL_1 = "Il y'a une semaine";
    public static final String COL_2 = "Il y'a six jours";
    public static final String COL_3 = "Il y'a cinq jours";
    public static final String COL_4 = "Il y'a quatre jours";
    public static final String COL_5 = "Il y'a trois jours";
    public static final String COL_6 = "Avant-hier";
    public static final String COL_7 = "Hier";
    public static final String COL_8 = "Aujourd'hui";
    public static final String TABLE_CREATE = "CREATE TABLE" + TABLE_NAME + "(" + COL_0 + "INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COL_1 + "TEXT," + COL_2 + "TEXT," + COL_3 + "TEXT," + COL_4 + "TEXT," + COL_5 + "TEXT," + COL_6 + "TEXT," + COL_7 + "TEXT," + COL_7 + "TEXT);";

    public static final String TABLE_DROP = "DROP TABLE IF EXISTS " + TABLE_NAME;


    public SQLiteDataBaseHelper (Context context) {
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
