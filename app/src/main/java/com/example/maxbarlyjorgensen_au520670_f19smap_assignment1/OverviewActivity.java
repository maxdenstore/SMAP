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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.overviewactivity);

        listView = (ListView)findViewById(R.id.listView);
        itemArrAdapt = new ItemArrAdapt(getApplicationContext(),R.layout.relative);

       // Parcelable state = listView.onSaveInstanceState();
        listView.setAdapter(itemArrAdapt);
     //   listView.onRestoreInstanceState(state);


        InputStream inputStream = getResources().openRawResource(R.raw.movielist);
        CSVinput csVinput = new CSVinput(inputStream);
        final List<String[]> movList = csVinput.read();



        for(String[] data : movList) {
            itemArrAdapt.add(data);
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(OverviewActivity.this, DetailsActivity.class);
                i.putExtra("position", itemArrAdapt.getItem(position));


                startActivity(i);
            }
        });




    }

}
