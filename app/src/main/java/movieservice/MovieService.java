package movieservice;

import android.app.Service;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
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
import db.MovieDatabase;
import db.MovieModel;
import http.MovParser;

public class MovieService extends Service {
    public static MovieDatabase movdb;
    public final IBinder iBinder = new LocalBinder();
    public String Title = "";
    private static String Response = "";
    private static MovieModel movFromAPI;
    RequestQueue mQueue;

    @Override
    public void onCreate() {
        Toast.makeText(getApplicationContext(), "Service Create",
                Toast.LENGTH_LONG).show();
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Toast.makeText(getApplicationContext(), "Service StartCommand",
                Toast.LENGTH_LONG).show();

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
/*        Toast.makeText(getApplicationContext(), "Saved in DB",
                Toast.LENGTH_LONG).show();*/
    }

    public void deleteMovie(MovieModel mov) {
        movdb.movdao().deleteMovie(mov);
        Toast.makeText(getApplicationContext(), mov.Title +"was deleted",
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
                        Log.d("res",movFromAPI.Title + "was added");
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
}
