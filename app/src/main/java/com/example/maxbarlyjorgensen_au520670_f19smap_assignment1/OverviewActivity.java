package com.example.maxbarlyjorgensen_au520670_f19smap_assignment1;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.IBinder;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.opencsv.CSVReader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

import DB.MovieModel;
import MovieService.MovieService;


public class OverviewActivity extends AppCompatActivity {

    MovieService movieService;
    boolean isBound = false;
    private ListView listView;
    private ItemArrAdapt itemArrAdapt;
    private Button exitBtn;
    private Button addBtn;
/*    private String[] data;
    private List<String[]> dataPref;
    private static final String PREFS_TAG = "SharedPrefs";
    private static final String LIST_TAG = "Liste";
    public int position;
    public int recievedPosition = 0;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.overviewactivity);

        //Intent & bind
        Intent intentS = new Intent(this, MovieService.class);
        bindService(intentS, serviceConnection, Context.BIND_AUTO_CREATE);

        exitBtn = (Button) findViewById(R.id.button3);

        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });


        addBtn = (Button) findViewById(R.id.addBtn);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMovieToDB(v);
            }
        });

        listView = (ListView)findViewById(R.id.listView);
        itemArrAdapt = new ItemArrAdapt(getApplicationContext(),R.layout.relative);
        listView.setAdapter(itemArrAdapt);

       // this.getPrefs();
/*
        this.getListFromDataba;
        this.intentHandler();
        this.setPrefs();*/

      //  getListFromDatabase(listView);


        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(OverviewActivity.this, EditActivity.class);
/*                i.putExtra("DATA", itemArrAdapt.getItem(position));
                i.putExtra("POS", position);*/
                startActivity(i);
                return true;
            }

        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(OverviewActivity.this, DetailsActivity.class);
/*                i.putExtra("DATA", itemArrAdapt.getItem(position));
                i.putExtra("POS", position);*/


                startActivity(i);
            }
        });




    }
    

    public void LoadFromCSV(){
        //Import list fra CSV
        InputStream inputStream = getResources().openRawResource(R.raw.movielist);
        CSVinput csVinput = new CSVinput(inputStream);
        final List<String[]> movList = csVinput.read();

        for(String[] data : movList) {
            MovieModel mov = new MovieModel();
            mov.Title = data[0];
            mov.Plot = data[1];
            mov.Genre = data[2];
            mov.Rating = data[3];
            mov.Urating = "N/A";
            movieService.addNew(mov);
            itemArrAdapt.add(mov);
        }
    }

    public void startService(View view) {

        Intent serviceintent = new Intent(this, MovieService.class);
        serviceintent.putExtra("test", "test");
        startService(serviceintent);
    }


    public void stopService(View view) {

        Intent serviceintent = new Intent(this, MovieService.class);
        stopService(serviceintent);
    }

    //Binding to service
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MovieService.LocalBinder binder = (MovieService.LocalBinder) service;
            movieService = binder.getService();
            isBound = true;

            getListFromDatabase();


        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBound = false;
        }
    };

    //Add a new movie by title
    public void addMovieToDB(View view) {
        MovieModel mod = new MovieModel();
        mod.setTitle("TestTitle");
        movieService.addNew(mod);
    }

    //testView
    public void addToListView(MovieModel mod){
        this.itemArrAdapt.add(mod);
    }

    //Gets all to adapter
    public void getListFromDatabase() {
        //First Start
        movieService.buildDb();
        if(movieService.getAll().size() < 1) {
            Toast.makeText(getApplicationContext(), "Database was new",
                    Toast.LENGTH_LONG).show();

            LoadFromCSV();
        }
        else{
           List<MovieModel> data = movieService.getAll();
            Toast.makeText(getApplicationContext(), "Database was loaded",
                    Toast.LENGTH_LONG).show();
            for (MovieModel mov : data)
            {
                addToListView(mov);
            }

        }
    }

    /*    public void intentHandler() {
        //Data modtaget fra Edit
        Intent intent = getIntent();
        if (intent.hasExtra("DATA")) {
            data = (String[]) getIntent().getSerializableExtra("DATA");
            recievedPosition = (int) getIntent().getSerializableExtra("POS");
            if(recievedPosition != 0){
                IOException e = new  IOException();
                try {
                    itemArrAdapt.updateItem(recievedPosition, data, e);
                    this.setPrefs();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        } else {
            // Start Normal
        }
    }*/


    //Shared prefs inspired by https://www.youtube.com/watch?v=fJEFZ6EOM9o
/*    private void getPrefs(){
        SharedPreferences sharedPreferences = getSharedPreferences(LIST_TAG, MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(PREFS_TAG, null);
        Type type = new TypeToken<ArrayList<String[]>>() {}.getType();
        dataPref = gson.fromJson(json, type);

        if(dataPref == null){
            LoadFromCSV();
        }
        else {
            Log.d("getPrefs", "was called ");
            itemArrAdapt.setList(dataPref);
        }

    }*/

/*    private void setPrefs(){

        SharedPreferences sharedPreferences = getSharedPreferences(LIST_TAG, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(itemArrAdapt.getMainList());
        editor.putString(PREFS_TAG, json);
        editor.apply();
        Log.d("setPrefs", "was called ");

    }*/
}


