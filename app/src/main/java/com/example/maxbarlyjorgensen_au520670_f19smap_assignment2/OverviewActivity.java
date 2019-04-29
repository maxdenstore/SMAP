package com.example.maxbarlyjorgensen_au520670_f19smap_assignment2;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.InputStream;
import java.util.List;
import db.MovieModel;
import movieservice.MovieService;


public class OverviewActivity extends AppCompatActivity {

    MovieService movieService;
    boolean isBound = false;
    private ListView listView;
    private ItemArrAdapt itemArrAdapt;
    private Button exitBtn;
    private Button addBtn;
    private EditText TitleToadd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.overviewactivity);

        //Intent & bind
        Intent intentS = new Intent(this, MovieService.class);
        bindService(intentS, serviceConnection, Context.BIND_AUTO_CREATE);

        //Inspiration fra: https://stackoverflow.com/questions/8802157/how-to-use-localbroadcastmanager
        LocalBroadcastManager.getInstance(this).registerReceiver( mMessageReceiver,
                new IntentFilter("Update"));

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


        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(OverviewActivity.this, EditActivity.class);
                Gson dataJson = new Gson();
                i.putExtra("DATA", dataJson.toJson(itemArrAdapt.getItem(position)));
                startActivity(i);
                return true;
            }

        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(OverviewActivity.this, DetailsActivity.class);
                Gson dataJson = new Gson();
                i.putExtra("DATA", dataJson.toJson(itemArrAdapt.getItem(position)));
                startActivity(i);
            }
        });




    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            String message = intent.getStringExtra("message");
            Toast.makeText(getApplicationContext(), message+ getResources().getString(R.string.wasadd),
                    Toast.LENGTH_LONG).show();
            getListFromDatabase();

        }
    };
    @Override
    protected void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
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
            mov.imdbRating = data[3];
            mov.Urating = "N/A";
            movieService.addNew(mov);
        }
        getListFromDatabase();

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
        TitleToadd = (EditText) findViewById(R.id.addTitle);
        movieService.AddFromHttp(TitleToadd.getText().toString());
        sendBroadcast(new Intent(this, BroadcastReceiver.class).setAction("Cast"));
    }

    //testView
    public void addToListView(MovieModel mod){
        this.itemArrAdapt.add(mod);
    }

    //Gets all to adapter
    public void getListFromDatabase() {
        //First Start
        if(movieService.getAll().isEmpty()) {
            LoadFromCSV();
        }
        //Normal start
        else{
            this.itemArrAdapt.clear();
           List<MovieModel> data = movieService.getAll();
            for (MovieModel mov : data)
            {
                addToListView(mov);
            }

        }
    }
}


