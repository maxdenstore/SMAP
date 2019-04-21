package movieservice;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.maxbarlyjorgensen_au520670_f19smap_assignment1.CSVinput;
import com.example.maxbarlyjorgensen_au520670_f19smap_assignment1.R;

import java.io.InputStream;
import java.util.List;
import java.util.Random;

import db.MovieDatabase;
import db.MovieModel;
import http.MovParser;

import static NotificationChannel.MovieNotificationChannel.CHANNEL_ID_1;

public class MovieService extends Service {
    private NotificationManagerCompat notificationManagerCompat;
    public static MovieDatabase movdb;
    public final IBinder iBinder = new LocalBinder();
    public String Title = "";
    private static String Response = "";
    private static MovieModel movFromAPI;
    RequestQueue mQueue;

    @Override
    public void onCreate() {
        super.onCreate();
        buildDb();
        StartBGProcess();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

/*        Toast.makeText(getApplicationContext(), "Service StartCommand",
                Toast.LENGTH_LONG).show();*/

        return super.onStartCommand(intent, flags, startId);
    }

    public class LocalBinder extends Binder{
        public MovieService getService(){
            return MovieService.this;
        }
    }

    public MovieService() {
        //
    }

    @Override
    public IBinder onBind(Intent intent) {
        return iBinder;
    }

    public void buildDb(){
        movdb = Room.databaseBuilder(getApplicationContext(), MovieDatabase.class, "test5")
                .allowMainThreadQueries()
                .build();

    }

    public void addNew(MovieModel mod){
        buildDb();
        movdb.movdao().addMovie(mod);
    }

    public void deleteMovie(MovieModel mov) {
        movdb.movdao().deleteMovie(mov);
        Toast.makeText(getApplicationContext(), mov.Title +getResources().getString(R.string.wasdel),
                Toast.LENGTH_LONG).show();
    }

    public void updateMovie(final MovieModel mov) {
        movdb.movdao().update(mov);
    }

    public void UpdateAllMovies(List<MovieModel> mov) {
        movdb.movdao().updateAll(mov);
    }

    public List<MovieModel> getAll(){
        buildDb();
        List<MovieModel> result = movdb.movdao().getAllMovies();
        return result;
    }

    public void AddFromHttp(final String title) {
        String apiKey = "7dd52750";

        if(mQueue == null) {
            mQueue = Volley.newRequestQueue(MovieService.this);
        }
        String apiStr = "http://www.omdbapi.com/?t=" + title + "&apikey=" + apiKey;
        String url = apiStr;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Response = response;
                        movFromAPI = MovParser.parse(Response);
                        addNew(movFromAPI);
                        UpdateUI(movFromAPI.Title);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Response = null;
                Log.d("ErrorResponse",error.getMessage() + "Fy for satan! :)");
            }
        });

        mQueue.add(stringRequest);

    }

    private void UpdateUI(String movName) {
        Intent intent = new Intent("Update");
        intent.putExtra("message", movName);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }


    public void Notifier(String MovieTitle)
    {
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID_1)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setAutoCancel(true)
                .setPriority(1)
                .setContentTitle(MovieTitle)
                .build();
       // startForeground(1, notification);
        notificationManagerCompat.notify(1, notification);
    }

    public void StartBGProcess() {
        notificationManagerCompat = NotificationManagerCompat.from(this);
        StartAsyncNotifer StartAsyncNotifier = new StartAsyncNotifer();
        List<MovieModel> data = movdb.movdao().getAllMovies();
        StartAsyncNotifier.execute(data);
/*
        StartAsyncNotifier.doInBackground(data);*/
    }

    private class StartAsyncNotifer extends AsyncTask<List<MovieModel>, Integer,Integer> {

        String MovieToNotify;
        int minValue = 0;
        int maxValue = 0;
        int random = 0;


        @Override
        protected Integer doInBackground(List<MovieModel>... lists) {
            while(true) {
                try {
                    List<MovieModel> data = movdb.movdao().getAllMovies();
                    if(!data.isEmpty())
                    {
                        maxValue = data.size() -1;
                        // inspireret af https://stackoverflow.com/questions/21049747/how-can-i-generate-a-random-number-in-a-certain-range/21049922
                        random = random = new Random().nextInt((maxValue - minValue) + 1) + minValue;

                        MovieToNotify = data.get(random).Title;
                        Notifier(getResources().getString(R.string.notseen) + MovieToNotify);
                        Thread.sleep(120000);
                    }


                    //Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
