package HTTP;

import android.graphics.Movie;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import DB.MovieModel;

public class MovParser {

    public static MovieModel parse(String Response) {
        Gson gson = new GsonBuilder().create();
        Log.d("parser", Response);
        MovieModel mov = gson.fromJson(Response, MovieModel.class);
        mov.Watched = "false";
        mov.setUrating("0.0");
        mov.setComment("Kommentar");

        if (mov != null) {
            return mov;

        } else
        {
            return null;
        }

    }
}
