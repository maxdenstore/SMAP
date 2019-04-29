package com.example.maxbarlyjorgensen_au520670_f19smap_assignment1;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;

import db.MovieModel;
import movieservice.MovieService;

public class DetailsActivity extends AppCompatActivity {
    MovieService movieService;
    boolean isBound = false;
    private MovieModel mov;
    private Button backBtn;
    private Button deleteBtn;
  //  private String[] data;
  //  private int recievedPosition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailsactivity);
        //Intent & bind
        Intent intentS = new Intent(this, MovieService.class);
        bindService(intentS, serviceConnection, Context.BIND_AUTO_CREATE);

        backBtn = (Button) findViewById(R.id.button);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBackMain();
            }


        });

        deleteBtn = (Button) findViewById(R.id.delete);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete();
            }
        });

        Intent dataintent = getIntent();
        Gson gson = new Gson();
        String data = "";
        data = dataintent.getStringExtra("DATA");

        mov = gson.fromJson(data, MovieModel.class);

        TextView plot = (TextView) findViewById(R.id.detailsplot);
        String plot2 = getResources().getString(R.string.plot);
        plot.setText(plot2+"\n" + mov.getPlot());

        TextView rating = (TextView) findViewById(R.id.rating);
        String rate = getResources().getString(R.string.rateing);
        rating.setText(rate+"\n" + mov.getRating());

        TextView urating = (TextView) findViewById(R.id.rating2);
        String urrate = getResources().getString(R.string.yourrating);
        urating.setText((urrate+"\n" +mov.getUrating()));

        TextView title = (TextView) findViewById(R.id.titelDetails);
        String titlestr = getResources().getString(R.string.title);
        title.setText( titlestr+"\n"+ mov.getTitle());

        CheckBox checked = (CheckBox) findViewById(R.id.detailswatched);


        if(TextUtils.isEmpty(mov.getWatched()))
        {mov.setWatched("false");}
        if(mov.getWatched().equals("false")){
            checked.setChecked(false);
        }else{checked.setChecked(true);}



        TextView comment = (TextView) findViewById(R.id.detailcomment);
        if(!TextUtils.isEmpty(mov.getComment())){
            comment.setText(mov.getComment());
        }

        ImageView imgset = (ImageView) findViewById(R.id.imageDetail);

        //ICO
        if(mov.getGenre() == null){
            imgset.setImageResource(R.mipmap.unknown2fl_round);
        }else{

            String[] firstCategoty = mov.getGenre().split(",");

            switch (firstCategoty[0]) {
                case "Action":
                    imgset.setImageResource(R.mipmap.action2fl_round);
                    break;

                case "History":
                    imgset.setImageResource(R.mipmap.history2fl_round);
                    break;

                case "Animation":
                    imgset.setImageResource(R.mipmap.animation2fl_round);
                    break;

                case "Drama":
                    imgset.setImageResource(R.mipmap.drama2fl_round);
                    break;

                case "Thriller":
                    imgset.setImageResource(R.mipmap.thrilller2fl_round);
                    break;

                case "Comedy":
                    imgset.setImageResource(R.mipmap.comedy2fl_round);
                    break;


                default:
                    imgset.setImageResource(R.mipmap.history2fl_round);
                    break;

            }


        }
    }
    private void goBackMain() {
        Intent intent;
        intent = new Intent(this, OverviewActivity.class);
        startActivity(intent);
    }

    private void delete() {
        movieService.deleteMovie(mov);
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



}
