package com.example.maxbarlyjorgensen_au520670_f19smap_assignment2;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.gson.Gson;

import db.MovieModel;
import movieservice.MovieService;

public class EditActivity extends AppCompatActivity {
    MovieService movieService;
    boolean isBound = false;
    private Button backBtn;
    private SeekBar seek_Bar;
    private static TextView rating;
    private Intent data;
    private int recievedPosition;
    private EditText comment;
    private MovieModel mov;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        //Intent & bind
        Intent intentS = new Intent(this, MovieService.class);
        bindService(intentS, serviceConnection, Context.BIND_AUTO_CREATE);

        backBtn = (Button) findViewById(R.id.btnsave);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBackMain();
            }


        });


        backBtn = (Button) findViewById(R.id.btncancel);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }


        });

        //data = (String[]) getIntent().getSerializableExtra("DATA");
        Intent dataintent = getIntent();
        Gson gson = new Gson();
        String data = "";
        data = dataintent.getStringExtra("DATA");

        mov =gson.fromJson(data, MovieModel.class);
       // recievedPosition = (int) getIntent().getSerializableExtra("POS");


        TextView title = (TextView) findViewById(R.id.EditTitle);
        title.setText(mov.getTitle());
        setSeekbar();

        comment = (EditText) findViewById(R.id.editTextComment);

        final CheckBox checked = (CheckBox) findViewById(R.id.EditWatched);
        checked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checked.isChecked()){
                    mov.setWatched("true");
                }
                else {mov.setWatched("false");}
            }
        });
        if(TextUtils.isEmpty(mov.getWatched()))
        {mov.setWatched("false");}
        if(mov.getWatched().equals("false")){
            checked.setChecked(false);
        }else{checked.setChecked(true);}


        if(!TextUtils.isEmpty(mov.getComment())){
            comment.setText(mov.getComment());
        }





    }

    private void goBackMain() {
        mov.setComment(comment.getText().toString());
        movieService.updateMovie(mov);
        Intent intent;
        intent = new Intent(this, OverviewActivity.class);
        startActivity(intent);
    }

    private void cancel() {
        Intent intent;
        intent = new Intent(this, OverviewActivity.class);
        startActivity(intent);
    }

    //Binding to service
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MovieService.LocalBinder binder = (MovieService.LocalBinder) service;
            movieService = binder.getService();
            isBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBound = false;
        }
    };



    public void setSeekbar() {
        seek_Bar = (SeekBar) findViewById(R.id.seekBar);
        if(mov.getUrating().equals("N/A"))
        {
            mov.setUrating("0");
        }
        float flval = Float.parseFloat(mov.getUrating());
        int valCur = (int) (flval*10);
        seek_Bar.setProgress(valCur);

        rating = (TextView) findViewById(R.id.textViewRating);
        rating.setText(mov.getUrating() + " / " +seek_Bar.getMax()/10);

        seek_Bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            float progressVal;
            String valCur = String.valueOf(progressVal);
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressVal =(float) progress;
                rating.setText(progressVal/10+ " / " +seek_Bar.getMax()/10);
                mov.setUrating(String.valueOf(progressVal/10));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                rating.setText(progressVal/10+ " / " +seek_Bar.getMax()/10);
                mov.setUrating(String.valueOf(progressVal/10));
            }
        });

    }

}