package com.example.maxbarlyjorgensen_au520670_f19smap_assignment1;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.opencsv.CSVReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class OverviewActivity extends AppCompatActivity {

    private ListView listView;

    private ItemArrAdapt itemArrAdapt;

    private Button exitBtn;

    private RelativeLayout relativeLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.overviewactivity);


        exitBtn = (Button) findViewById(R.id.button2);
        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDetails();
            }

        });

        relativeLayout = (RelativeLayout)findViewById(R.id.layoutRel);




        listView = (ListView)findViewById(R.id.listView);
        itemArrAdapt = new ItemArrAdapt(getApplicationContext(),R.layout.relative);

        Parcelable state = listView.onSaveInstanceState();
        listView.setAdapter(itemArrAdapt);
        listView.onRestoreInstanceState(state);

        InputStream inputStream = getResources().openRawResource(R.raw.movielist);
        CSVinput csVinput = new CSVinput(inputStream);
        List<String[]> movList = csVinput.read();

        for(String[] data : movList) {
            itemArrAdapt.add(data);
        }


    }
    private void openDetails() {
        Intent intent;
        intent = new Intent(this, DetailsActivity.class);
        startActivity(intent);
    }



}
