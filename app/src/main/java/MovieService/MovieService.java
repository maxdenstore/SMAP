package MovieService;

import android.app.Service;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

import com.example.maxbarlyjorgensen_au520670_f19smap_assignment1.CSVinput;
import com.example.maxbarlyjorgensen_au520670_f19smap_assignment1.R;

import java.io.InputStream;
import java.util.List;
import DB.MovieDatabase;
import DB.MovieModel;

public class MovieService extends Service {
    public static MovieDatabase movdb;
    public final IBinder iBinder = new LocalBinder();

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
        movdb = Room.databaseBuilder(getApplicationContext(), MovieDatabase.class, "moviedb1")
                .allowMainThreadQueries()
                .build();

    }

    public void addNew(MovieModel mod){
        buildDb();
        movdb.movdao().addMovie(mod);
/*        Toast.makeText(getApplicationContext(), "Saved in DB",
                Toast.LENGTH_LONG).show();*/
    }

    public List<MovieModel> getAll(){
        buildDb();
        List<MovieModel> result = movdb.movdao().getAllMovies();
        return result;
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
            addNew(mov);

            Toast.makeText(getApplicationContext(), mov.Title+" was added to DB",
                    Toast.LENGTH_LONG).show();
        }
    }
}
