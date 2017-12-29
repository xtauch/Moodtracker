package com.example.youpiman.moodtracker.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.youpiman.moodtracker.R;
import com.example.youpiman.moodtracker.model.DBManagement;
import com.example.youpiman.moodtracker.model.MoodData;
import com.example.youpiman.moodtracker.model.ScreenSlidePagerAdapter;
import com.example.youpiman.moodtracker.model.VerticalViewPager;
import com.example.youpiman.moodtracker.view.Disappointed;
import com.example.youpiman.moodtracker.view.Happy;
import com.example.youpiman.moodtracker.view.Normal;
import com.example.youpiman.moodtracker.view.Sad;
import com.example.youpiman.moodtracker.view.Super_happy;

public class ScreenSlidePagerActivity extends FragmentActivity implements View.OnClickListener {

    //Pager widget
    public static VerticalViewPager mPager;

    MoodData mMoodData;

    DBManagement mDBManagement = new DBManagement(this);

    @SuppressLint("SimpleDateFormat")
    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
    Calendar mCalendar = Calendar.getInstance();
    Date mDate = mCalendar.getTime();


    public static final String PREF_KEY_VIEW = "PREF_KEY_VIEW";
    public static final String PREF_KEY_DATE = "PREF_KEY_DATE";

    SharedPreferences mPreferences;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.history:
                Intent chartIntent = new Intent(this, History.class);
                startActivity(chartIntent);
                break;

            case R.id.note:
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);

                final EditText input = new EditText(this);

                mDBManagement = new DBManagement(this);
                mDBManagement.open();

                if (mDBManagement.getData(dateFormat.format(mDate)) != null){
                    input.setText(mDBManagement.getData(dateFormat.format(mDate)).getComment());
                }
                builder.setTitle("Commentaire")
                        .setView(input)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mMoodData = mDBManagement.getData(dateFormat.format(mDate));
                                String comment = input.getText().toString();
                                mMoodData.setComment(comment);
                                mDBManagement.updateData(dateFormat.format(mDate), mMoodData);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .create()
                        .show();

                break;

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.screen_slide);

        ImageButton historyButton = findViewById(R.id.history);
        ImageButton noteButton = findViewById(R.id.note);

        historyButton.setOnClickListener(this);
        noteButton.setOnClickListener(this);

        mPreferences = getPreferences(MODE_PRIVATE);

        //Link each music to its MediaPlayer
        final MediaPlayer mediaPlayer1 = MediaPlayer.create(this, R.raw.jobro_piano_1);
        final MediaPlayer mediaPlayer2 = MediaPlayer.create(this, R.raw.jobro_piano_2);
        final MediaPlayer mediaPlayer3 = MediaPlayer.create(this, R.raw.jobro_piano_3);
        final MediaPlayer mediaPlayer4 = MediaPlayer.create(this, R.raw.jobro_piano_4);
        final MediaPlayer mediaPlayer5 = MediaPlayer.create(this, R.raw.jobro_piano_5);


        //Create List of fragments used in adapter
        List<Fragment> fragments = new Vector<>();
        fragments.add(Fragment.instantiate(this, Sad.class.getName()));
        fragments.add(Fragment.instantiate(this, Disappointed.class.getName()));
        fragments.add(Fragment.instantiate(this, Normal.class.getName()));
        fragments.add(Fragment.instantiate(this, Happy.class.getName()));
        fragments.add(Fragment.instantiate(this, Super_happy.class.getName()));


        mPager = findViewById(R.id.pager);

        //Create Adapter
        PagerAdapter pagerAdapter = new ScreenSlidePagerAdapter(super.getSupportFragmentManager(), fragments);

        //Adapter to ViewPager
        mPager.setAdapter(pagerAdapter);
        //If day has changed -> set current view to 3
        String prefDate = mPreferences.getString(PREF_KEY_DATE, null);

        if (prefDate != null && !prefDate.equals(dateFormat.format(mDate))) {
            mPreferences.edit().clear().apply();
        }
        //Get saved view
        int i = mPreferences.getInt(PREF_KEY_VIEW, 3);

        //Display saved view
        mPager.setCurrentItem(i);

        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                //Play a music when view is changed

                switch (mPager.getCurrentItem()){
                    case 0:
                        mediaPlayer5.start();
                        break;
                    case 1:
                        mediaPlayer4.start();
                        break;
                    case 2:
                        mediaPlayer3.start();
                        break;
                    case 3:
                        mediaPlayer2.start();
                        break;
                    case 4:
                        mediaPlayer1.start();
                        break;
                }
            }

            @Override
            public void onPageSelected(int position) {}
            @Override
            public void onPageScrollStateChanged(int state) {}
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        mDBManagement.open();
        //Create new MoodData if today you open the application for the first time
        if (mDBManagement.getData(dateFormat.format(mDate)) == null){
            mMoodData = new MoodData(null, null, dateFormat.format(mDate));
            mDBManagement.insertData(mMoodData);
        } else {
        //Or get today's data
            mMoodData = mDBManagement.getData(dateFormat.format(mDate));
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        //Save current view
        mPreferences.edit().putInt(PREF_KEY_VIEW, mPager.getCurrentItem()).apply();

        //Save today's date
        mPreferences.edit().putString(PREF_KEY_DATE, dateFormat.format(mDate)).apply();

        //Update today's mood when view is changed
        switch (mPager.getCurrentItem()){
            case 0:
                mMoodData.setMood("Sad");
                mDBManagement.updateData(dateFormat.format(mDate), mMoodData);
                break;
            case 1:
                mMoodData.setMood("Disappointed");
                mDBManagement.updateData(dateFormat.format(mDate), mMoodData);
                break;
            case 2:
                mMoodData.setMood("Normal");
                mDBManagement.updateData(dateFormat.format(mDate), mMoodData);
                break;
            case 3:
                mMoodData.setMood("Happy");
                mDBManagement.updateData(dateFormat.format(mDate), mMoodData);
                break;
            case 4:
                mMoodData.setMood("Super happy");
                mDBManagement.updateData(dateFormat.format(mDate), mMoodData);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDBManagement.close();
    }
}



    /*
    Data to test the chart
    Define new dates for the test

    MoodData mMoodData1 = new MoodData("Oui", "Disappointed", "2017/12/21");
    MoodData mMoodData2 = new MoodData(null, "Happy", "2017/12/22");
    MoodData mMoodData3 = new MoodData("Bonjour", "Sad", "2017/12/23");
    MoodData mMoodData4 = new MoodData(null, "Super happy", "2017/12/24");
    MoodData mMoodData5 = new MoodData(null, "Normal", "2017/12/25");
    MoodData mMoodData6 = new MoodData("Hi", "Sad", "2017/12/26");
    MoodData mMoodData7 = new MoodData("Non", "Super happy", "2017/12/27");

    mDBManagement.insertData(mMoodData1);
    mDBManagement.insertData(mMoodData2);
    mDBManagement.insertData(mMoodData3);
    mDBManagement.insertData(mMoodData4);
    mDBManagement.insertData(mMoodData5);
    mDBManagement.insertData(mMoodData6);
    mDBManagement.insertData(mMoodData7);

*/
