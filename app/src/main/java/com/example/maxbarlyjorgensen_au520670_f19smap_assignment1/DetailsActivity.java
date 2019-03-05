package com.example.maxbarlyjorgensen_au520670_f19smap_assignment1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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

    }
    private void goBackMain() {
        Intent intent;
        intent = new Intent(this, OverviewActivity.class);
        startActivity(intent);
    }




}
