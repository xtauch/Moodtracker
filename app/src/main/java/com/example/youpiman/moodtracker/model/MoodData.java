package com.example.youpiman.moodtracker.model;

import android.annotation.SuppressLint;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;



public class MoodData {

    @SuppressLint("SimpleDateFormat")
    private DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
    private Calendar mCalendar = Calendar.getInstance();
    private Date mDate = mCalendar.getTime();
    private String mFormatDate = dateFormat.format(mDate);

    private String mComment;
    private String mMood;

    MoodData() {
    }


    public MoodData(String comment, String mood, String formatDate) {
        mFormatDate = formatDate;
        mComment = comment;
        mMood = mood;
    }

    String getFormatDate() {
        return mFormatDate;
    }

    void setFormatDate(String formatDate) {
        mFormatDate = formatDate;
    }

    public String getComment() {
        return mComment;
    }

    public void setComment(String comment) {
        mComment = comment;
    }

    public String getMood() {
        return mMood;
    }

    public void setMood(String mood) {
        mMood = mood;
    }
}

