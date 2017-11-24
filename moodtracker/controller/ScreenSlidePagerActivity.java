package com.example.youpiman.moodtracker.controller;

import java.util.List;
import java.util.Vector;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;

import com.example.youpiman.moodtracker.R;
import com.example.youpiman.moodtracker.view.Disappointed;
import com.example.youpiman.moodtracker.view.Happy;
import com.example.youpiman.moodtracker.view.Normal;
import com.example.youpiman.moodtracker.view.Sad;
import com.example.youpiman.moodtracker.view.Super_happy;

public class ScreenSlidePagerActivity extends FragmentActivity{


    public static SharedPreferences mSharedPreferences;
    public static final String PREF_KEY_COMMENT = "PREF_KEY_COMMENT";
    //Pager widget
    private VerticalViewPager mPager;
    //Pager adapter
    private PagerAdapter mPagerAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.screen_slide);

        mSharedPreferences = getPreferences(MODE_PRIVATE);

        List fragments = new Vector();

        fragments.add(Fragment.instantiate(this, Super_happy.class.getName()));
        fragments.add(Fragment.instantiate(this, Happy.class.getName()));
        fragments.add(Fragment.instantiate(this, Normal.class.getName()));
        fragments.add(Fragment.instantiate(this, Disappointed.class.getName()));
        fragments.add(Fragment.instantiate(this, Sad.class.getName()));


        mPager = findViewById(R.id.pager);
        //Create Adapter
        mPagerAdapter = new ScreenSlidePagerAdapter(super.getSupportFragmentManager(), fragments);
        //Adapter to ViewPager
        mPager.setAdapter(mPagerAdapter);
        mPager.setCurrentItem(1);




    }
}
