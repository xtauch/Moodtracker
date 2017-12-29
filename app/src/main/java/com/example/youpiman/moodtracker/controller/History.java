package com.example.youpiman.moodtracker.controller;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import com.example.youpiman.moodtracker.R;
import com.example.youpiman.moodtracker.model.CustomHorizontalBarChartRenderer;
import com.example.youpiman.moodtracker.model.DBManagement;
import com.example.youpiman.moodtracker.model.MoodData;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class History extends Activity {

    HorizontalBarChart mChart;
    DBManagement mDBManagement = new DBManagement(this);
    MoodData mMoodData;


    private static final String SUPER_HAPPY = "Super happy";
    private static final String HAPPY = "Happy";
    private static final String NORMAL = "Normal";
    private static final String DISAPPOINTED = "Disappointed";
    private static final String SAD = "Sad";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);


        mChart = findViewById(R.id.chart);

        mDBManagement.open();




        @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Calendar mCalendar = Calendar.getInstance();

        //Create an array for each entry to affect a color changing if mood is modified
        ArrayList<BarEntry> entries0 = new ArrayList<>();
        ArrayList<BarEntry> entries1 = new ArrayList<>();
        ArrayList<BarEntry> entries2 = new ArrayList<>();
        ArrayList<BarEntry> entries3 = new ArrayList<>();
        ArrayList<BarEntry> entries4 = new ArrayList<>();
        ArrayList<BarEntry> entries5 = new ArrayList<>();
        ArrayList<BarEntry> entries6 = new ArrayList<>();

        //Create an array for the loop
        ArrayList<ArrayList<BarEntry>> entries = new ArrayList<>();
        entries.add(entries0);
        entries.add(entries1);
        entries.add(entries2);
        entries.add(entries3);
        entries.add(entries4);
        entries.add(entries5);
        entries.add(entries6);

        //Create an array for the loop
        final ArrayList<BarDataSet> dataSets = new ArrayList<>();

        for (int i = 0; i < 7; i++) {

            int count = 1;
            mCalendar.add(Calendar.DATE, -count);
            count++;
            Date mDate = mCalendar.getTime();

            if (mDBManagement.getData(dateFormat.format(mDate)) != null) {
                mMoodData = mDBManagement.getData(dateFormat.format(mDate));
                setBar(mMoodData, entries.get(i),i);
                dataSets.add(new BarDataSet(entries.get(i), ""));
                setBarColor(mMoodData, dataSets.get(i));
                dataSets.get(i).setDrawValues(false);

            } else{ mMoodData = new MoodData(null, "Happy", dateFormat.format(mDate));
                mDBManagement.insertData(mMoodData);
                setBar(mMoodData, entries.get(i),i);
                dataSets.add(new BarDataSet(entries.get(i), ""));
                setBarColor(mMoodData, dataSets.get(i));
                dataSets.get(i).setDrawValues(false);
            }
        }

        //Create HorizontalBarChart
        mChart = new HorizontalBarChart(this);

        //Indicate the data to use
        BarData data = new BarData(dataSets.get(0),dataSets.get(1),dataSets.get(2),dataSets.get(3),dataSets.get(4),dataSets.get(5),dataSets.get(6));

        //Send data to the chart
        mChart.setData(data);

        //Refresh chart
        mChart.invalidate();

        //Chart's design
        mChart.getXAxis().setAxisMinimum(-0.5f);
        mChart.getXAxis().setAxisMaximum(6.5f);
        mChart.getXAxis().setDrawGridLines(false);
        mChart.getXAxis().setDrawLabels(false);
        mChart.getXAxis().setDrawAxisLine(false);

        mChart.getAxisLeft().setAxisMinimum(0);
        mChart.getAxisLeft().setAxisMaximum(100);
        mChart.getAxisLeft().setDrawGridLines(false);
        mChart.getAxisLeft().setDrawLabels(false);
        mChart.getAxisLeft().setDrawAxisLine(false);

        mChart.getAxisRight().setDrawLabels(false);
        mChart.getAxisRight().setDrawGridLines(false);

        mChart.setBackgroundColor(Color.rgb(211, 211, 211));

        mChart.setDescription(null);
        mChart.getLegend().setEnabled(false);

        mChart.setDoubleTapToZoomEnabled(false);
        mChart.setHighlightPerDragEnabled(true);
        mChart.setHighlightPerTapEnabled(true);
        mChart.setTouchEnabled(true);

        data.setBarWidth(0.99f);


        //Link comment image to a Bitmap
        Bitmap commentBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_comment_black_48px);

        //Set the custom renderer to the chart
        mChart.setRenderer(new CustomHorizontalBarChartRenderer(mChart, mChart.getAnimator(), mChart.getViewPortHandler(), commentBitmap, this));

        //Display custom design
        setContentView(mChart);

        //Set interaction with the chart
        mChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {

            @Override
            public void onValueSelected(Entry e, Highlight h) {

                // return the corresponding comment if it exists in a message Toast when bar is clicked

                float x = e.getX();
                @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

                if (x == 0) {
                    Calendar mCalendar = Calendar.getInstance();
                    mCalendar.add(Calendar.DATE, -1);
                    Date mDate = mCalendar.getTime();
                    mMoodData = mDBManagement.getData(dateFormat.format(mDate));
                    if (mMoodData != null) {
                        if (mMoodData.getComment() != null) {
                            Toast.makeText(History.this, mMoodData.getComment(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }  else if (x == 1) {
                    Calendar mCalendar = Calendar.getInstance();
                    mCalendar.add(Calendar.DATE, -2);
                    Date mDate = mCalendar.getTime();
                    mMoodData = mDBManagement.getData(dateFormat.format(mDate));
                    if (mMoodData != null) {
                        if (mMoodData.getComment() != null) {
                            Toast.makeText(History.this, mMoodData.getComment(), Toast.LENGTH_SHORT).show();
                        }
                    }
                } else if (x == 2) {
                    Calendar mCalendar = Calendar.getInstance();
                    mCalendar.add(Calendar.DATE, -3);
                    Date mDate = mCalendar.getTime();
                    mMoodData = mDBManagement.getData(dateFormat.format(mDate));
                    if (mMoodData != null) {
                        if (mMoodData.getComment() != null) {
                            Toast.makeText(History.this, mMoodData.getComment(), Toast.LENGTH_SHORT).show();
                        }
                    }
                } else if (x == 3) {
                    Calendar mCalendar = Calendar.getInstance();
                    mCalendar.add(Calendar.DATE, -4);
                    Date mDate = mCalendar.getTime();
                    mMoodData = mDBManagement.getData(dateFormat.format(mDate));
                    if (mMoodData != null) {
                        if (mMoodData.getComment() != null) {
                            Toast.makeText(History.this, mMoodData.getComment(), Toast.LENGTH_SHORT).show();
                        }
                    }
                } else if (x == 4) {
                    Calendar mCalendar = Calendar.getInstance();
                    mCalendar.add(Calendar.DATE, -5);
                    Date mDate = mCalendar.getTime();
                    mMoodData = mDBManagement.getData(dateFormat.format(mDate));
                    if (mMoodData != null) {
                        if (mMoodData.getComment() != null) {
                            Toast.makeText(History.this, mMoodData.getComment(), Toast.LENGTH_SHORT).show();
                        }
                    }
                } else if (x == 5) {
                    Calendar mCalendar = Calendar.getInstance();
                    mCalendar.add(Calendar.DATE, -6);
                    Date mDate = mCalendar.getTime();
                    mMoodData = mDBManagement.getData(dateFormat.format(mDate));
                    if (mMoodData != null) {
                        if (mMoodData.getComment() != null) {
                            Toast.makeText(History.this, mMoodData.getComment(), Toast.LENGTH_SHORT).show();
                        }
                    }
                } else if (x == 6) {
                    Calendar mCalendar = Calendar.getInstance();
                    mCalendar.add(Calendar.DATE, -7);
                    Date mDate = mCalendar.getTime();
                    mMoodData = mDBManagement.getData(dateFormat.format(mDate));
                    if (mMoodData != null) {
                        if (mMoodData.getComment() != null) {
                            Toast.makeText(History.this, mMoodData.getComment(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            @Override
            public void onNothingSelected() {}

        });
    }

    // Set bar color
    private void setBarColor(MoodData moodData, BarDataSet dataSet) {
        switch (moodData.getMood()) {
            case SUPER_HAPPY:
                dataSet.setColor(Color.parseColor("#fff9ec4f"));
                break;
            case HAPPY:
                dataSet.setColor(Color.parseColor("#ffb8e986"));
                break;
            case NORMAL:
                dataSet.setColor(Color.parseColor("#a5468ad9"));
                break;
            case DISAPPOINTED:
                dataSet.setColor(Color.parseColor("#ff9b9b9b"));
                break;
            case SAD:
                dataSet.setColor(Color.parseColor("#ffde3c50"));
                break;
        }
    }

    // Set bar width
    private void setBar(MoodData moodData, ArrayList<BarEntry> barEntry, float x) {
        switch (moodData.getMood()) {
            case SUPER_HAPPY:
                barEntry.add(new BarEntry(x, 100));
                break;
            case HAPPY:
                barEntry.add(new BarEntry(x, 80));
                break;
            case NORMAL:
                barEntry.add(new BarEntry(x, 60));
                break;
            case DISAPPOINTED:
                barEntry.add(new BarEntry(x, 45));
                break;
            case SAD:
                barEntry.add(new BarEntry(x, 32));
                break;
        }
    }
}
