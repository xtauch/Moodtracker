package com.example.youpiman.moodtracker.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.youpiman.moodtracker.R;



public class Super_happy extends Fragment{

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState){

        return inflater.inflate(R.layout.super_happy, container, false);


    }
}

