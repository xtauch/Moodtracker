package com.example.youpiman.moodtracker.controller;

import android.app.Activity;
import android.os.Bundle;

import com.example.youpiman.moodtracker.R;
import com.github.mikephil.charting.charts.BarChart;

public class History extends Activity {

    BarChart mChart;

    public History(BarChart chart) {
        mChart = chart;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);


         mChart = findViewById(R.id.chart);

        //Toast.makeText(this, livreFromBdd.toString(), Toast.LENGTH_SHORT).show();

    }
}
