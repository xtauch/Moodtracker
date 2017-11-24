package com.example.youpiman.moodtracker.view;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.youpiman.moodtracker.R;

/**
 * Created by Youpiman on 15/11/2017.
 */

public class Happy extends Fragment implements View.OnClickListener {

    private ImageButton HistoryButton;
    private ImageButton NoteButton;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.history:
                AlertDialog.Builder history = new AlertDialog.Builder(getActivity());

                history.setTitle("History");
                history.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                break;

            case R.id.note:
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                final EditText comment = new EditText(getActivity());
                builder.setTitle("Commentaire")
                        .setView(comment)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                getActivity().finish();
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
        View rootView = inflater.inflate(R.layout.happy, container, false);

        HistoryButton = rootView.findViewById(R.id.history);
        NoteButton = rootView.findViewById(R.id.note);

        HistoryButton.setOnClickListener(this);
        NoteButton.setOnClickListener(this);

        return rootView;


    }
}

