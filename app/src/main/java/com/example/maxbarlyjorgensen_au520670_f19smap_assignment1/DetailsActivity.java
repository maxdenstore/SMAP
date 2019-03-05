package com.example.maxbarlyjorgensen_au520670_f19smap_assignment1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity {
    private Button backBtn;

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

        String[] data = (String[]) getIntent().getSerializableExtra("DATA");


        TextView plot = (TextView) findViewById(R.id.detailsplot);
        plot.setText(data[1]);

        TextView rating = (TextView) findViewById(R.id.rating);
        rating.setText(data[3]);

        TextView urating = (TextView) findViewById(R.id.rating2);
      //  urating.setText(data[1]);

        TextView title = (TextView) findViewById(R.id.detailstitle);
        title.setText(data[0]);

    }
    private void goBackMain() {
        Intent intent;
        intent = new Intent(this, OverviewActivity.class);
        startActivity(intent);
    }




}
