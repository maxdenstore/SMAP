package com.example.maxbarlyjorgensen_au520670_f19smap_assignment1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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
        plot.setText("Plot: \n" + data[1]);

        TextView rating = (TextView) findViewById(R.id.rating);
        rating.setText("Rating: \n " +data[3]);

        TextView urating = (TextView) findViewById(R.id.rating2);
        urating.setText( "Din rating : \n" +data[4]);



        String TAG = "værdi";
        Log.e(TAG, data[0]);
        TextView title = (TextView) findViewById(R.id.titelDetails);
        title.setText( "Titel: \n"+ data[0]);

        CheckBox checked = (CheckBox) findViewById(R.id.detailswatched);

        if(data[5].equals("true")){
            checked.setChecked(true);
        }


        TextView comment = (TextView) findViewById(R.id.detailcomment);
        comment.setText(data[6]);

    }
    private void goBackMain() {
        Intent intent;
        intent = new Intent(this, OverviewActivity.class);
        intent.putExtra("DATA", data);
        intent.putExtra("POS", recievedPosition);
        startActivity(intent);
    }




}
