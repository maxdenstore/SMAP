package com.example.maxbarlyjorgensen_au520670_f19smap_assignment1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity {
    private Button backBtn;
    private String[] data;
    private int recievedPosition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailsactivity);

        backBtn = (Button) findViewById(R.id.button);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBackMain();
            }


        });

        data = (String[]) getIntent().getSerializableExtra("DATA");
        recievedPosition = (int) getIntent().getSerializableExtra("POS");

        TextView plot = (TextView) findViewById(R.id.detailsplot);
        String plot2 = getResources().getString(R.string.plot);
        plot.setText(plot2+"\n" + data[1]);

        TextView rating = (TextView) findViewById(R.id.rating);
        String rate = getResources().getString(R.string.rateing);
        rating.setText(rate+"\n" +data[3]);

        TextView urating = (TextView) findViewById(R.id.rating2);
        String urrate = getResources().getString(R.string.urrate);

        if(data[4].equals("0")) {
            urating.setText("N/A");
        }else {
            urating.setText(urrate+ "\n" +data[4]);
        }





        String TAG = "værdi";
        Log.e(TAG, data[0]);
        TextView title = (TextView) findViewById(R.id.titelDetails);
        String titlestr = getResources().getString(R.string.title);
        title.setText( titlestr+"\n"+ data[0]);

        CheckBox checked = (CheckBox) findViewById(R.id.detailswatched);

        if(data[5].equals("true")){
            checked.setChecked(true);
        }


        TextView comment = (TextView) findViewById(R.id.detailcomment);

        if(data[6].equals("0")) {
            String com = getResources().getString(R.string.nocomment);
            comment.setText((com));
        }
        if(data[6].equals("Comment")) {
            String com = getResources().getString(R.string.nocomment);
            comment.setText((com));
        }
        else {
            comment.setText(data[6]);
        }

        ImageView imgset = (ImageView) findViewById(R.id.imageDetail);

        //ICO
        if(data[2] == null){
            imgset.setImageResource(R.mipmap.unknown2fl_round);
        }else{

            String[] firstCategoty = data[2].split(",");

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
        intent.putExtra("DATA", data);
        intent.putExtra("POS", recievedPosition);
        startActivity(intent);
    }




}
