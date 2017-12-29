package com.example.youpiman.moodtracker.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


public class DBManagement {

    private static final int NUM_Date = 1;
    private static final int NUM_Mood = 2;
    private static final int NUM_Comment = 3;

    private static final String TABLE_NAME = "Comment_table";
    private static final String ID = "ID";
    private static final String Date = "Date";
    private static final String Mood = "Mood";
    private static final String Comment = "Comment";


    private static SQLiteDatabase db;

    private SQLiteDataBaseHelper myBaseSQLite;


    public DBManagement(Context context){

        //Create database
        myBaseSQLite = new SQLiteDataBaseHelper(context);
    }

    public void open(){
        //Open database
        db = myBaseSQLite.getWritableDatabase();
    }

    public void close(){
        //Close database
        db.close();
    }

    /*
    public SQLiteDatabase getBDD(){
        return db;
    }
    */

     public void insertData(MoodData moodData){
        //Create ContentValues
        ContentValues values = new ContentValues();
        //Add values
        values.put(Date, moodData.getFormatDate());
        values.put(Mood, moodData.getMood());
        values.put(Comment, moodData.getComment());
        //Insert object in database
        db.insert(TABLE_NAME, null, values);
    }

     public void updateData(String date, MoodData moodData){
        ContentValues values = new ContentValues();
        values.put(Date, moodData.getFormatDate());
        values.put(Mood, moodData.getMood());
        values.put(Comment, moodData.getComment());
         db.update(TABLE_NAME, values, Date + " LIKE \"" + date + "\"", null);
     }

    /*
    public int removeData(String date){
        //Delete data at the indicated date
        return db.delete(TABLE_NAME, Date + " LIKE \"" + date +"\"", null);
    }
    */

    public MoodData getData(String date){
        //Get data at the indicated date
        Cursor c = db.query(TABLE_NAME, new String[] {ID, Date, Mood, Comment}, Date + " LIKE \"" + date +"\"", null, null, null, null);
        return this.cursorToData(c);
    }

    //Convert cursor to MoodData
        private MoodData cursorToData(Cursor c){
        //return null if nothing in query
        if (c.getCount() == 0)
            return null;

        //Move to first element
        c.moveToFirst();
        //Create MoodData
        MoodData moodData = new MoodData();
        //Set data in the cursor to object
        moodData.setFormatDate(c.getString(NUM_Date));
        moodData.setMood(c.getString(NUM_Mood));
        moodData.setComment(c.getString(NUM_Comment));
        //Close cursor
        c.close();
        //Return object
        return moodData;
    }

}
