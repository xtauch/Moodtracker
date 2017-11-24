package com.example.youpiman.moodtracker.view;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.youpiman.moodtracker.R;
import com.example.youpiman.moodtracker.controller.Comment;
import com.example.youpiman.moodtracker.controller.DBManagement;
import com.example.youpiman.moodtracker.controller.History;
import com.example.youpiman.moodtracker.model.SQLiteDataBaseHelper;


import static com.example.youpiman.moodtracker.controller.ScreenSlidePagerActivity.PREF_KEY_COMMENT;
import static com.example.youpiman.moodtracker.controller.ScreenSlidePagerActivity.mSharedPreferences;

/**
 * Created by Youpiman on 15/11/2017.
 */

public class Disappointed extends Fragment implements View.OnClickListener {

    private ImageButton HistoryButton;
    private ImageButton NoteButton;
    public static Comment mComment = new Comment();
    DBManagement mDBManagement = new DBManagement(getActivity());

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.history3:
                final AlertDialog.Builder history = new AlertDialog.Builder(getActivity());

                history.setTitle("History");
                history.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent chartIntent = new Intent(Disappointed.this.getActivity(), History.class);
                        startActivity(chartIntent);
                    }
                });
                break;

            case R.id.note3:
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                final EditText input = new EditText(getActivity());

                mSharedPreferences.edit().putString(PREF_KEY_COMMENT, Disappointed.mComment.getComment()).apply();
                String prefComment = mSharedPreferences.getString(PREF_KEY_COMMENT, null);
                if ( prefComment != null){
                    input.setText(prefComment);
                }

                if (mComment != null){
                    mDBManagement.open();
                    input.setText(mDBManagement.getComment().toString());
                    mDBManagement.close();

                }
                builder.setTitle("Commentaire")
                        .setView(input)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mDBManagement.open();
                                String comment = input.getText().toString();
                                mComment.setComment(comment);
                                mDBManagement.insertComment(mComment);
                                mDBManagement.close();

                            }
                        })
                        .create()
                        .show();
                break;

        }
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.disappointed, container, false);

        HistoryButton = rootView.findViewById(R.id.history3);
        NoteButton = rootView.findViewById(R.id.note3);

        HistoryButton.setOnClickListener(this);
        NoteButton.setOnClickListener(this);




        return rootView;


    }
}

