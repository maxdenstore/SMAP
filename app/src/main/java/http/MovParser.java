package http;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import db.MovieModel;

public class MovParser {

    public static MovieModel parse(String Response) {
        Gson gson = new GsonBuilder().create();
        MovieModel mov = gson.fromJson(Response, MovieModel.class);
        mov.setWatched("false");
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
