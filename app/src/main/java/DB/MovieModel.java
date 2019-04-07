package DB;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "movies")
public class MovieModel {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo
    public String Title;

    @ColumnInfo
    public String Rating;

    @ColumnInfo
    public String Urating;

    @ColumnInfo
    public String Watched;

    @ColumnInfo
    public String Plot;

    @ColumnInfo
    public String Genre;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getRating() {
        return Rating;
    }

    public void setRating(String rating) {
        Rating = rating;
    }

    public String getUrating() {
        return Urating;
    }

    public void setUrating(String urating) {
        Urating = urating;
    }

    public String getWatched() {
        return Watched;
    }

    public void setWatched(String watched) {
        Watched = watched;
    }

    public String getPlot() {
        return Plot;
    }

    public void setPlot(String plot) {
        Plot = plot;
    }

    public String getGenre() {
        return Genre;
    }

    public void setGenre(String genre) {
        Genre = genre;
    }
}
