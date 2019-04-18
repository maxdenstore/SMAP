package DB;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addMovie(MovieModel... m);

    @Delete
    void deleteMovie(MovieModel m);

    @Query("SELECT * FROM movies")
    List<MovieModel> getAllMovies();

    @Query("SELECT * FROM movies WHERE Title LIKE :Title LIMIT 1")
    MovieModel get(String Title);

    @Update
    void update(MovieModel movieModel);

    @Update
    void updateAll(List<MovieModel> movies);

}
