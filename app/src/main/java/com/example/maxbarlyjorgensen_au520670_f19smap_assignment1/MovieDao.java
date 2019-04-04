package com.example.maxbarlyjorgensen_au520670_f19smap_assignment1;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface MovieDao {
    @Query("SELECT * FROM movieEntity")
    List<MovieEntity> getAll();

    @Query("SELECT * FROM movieEntity WHERE movId IN (:movIds)")
    List<MovieEntity> loadAllByIds(int[] movIds);

    @Query("SELECT * FROM movieEntity WHERE title LIKE :first")
    MovieEntity findByTitle(String title);

    @Insert(onConflict = REPLACE)
    void insert(MovieEntity title);

    @Delete
    void delete(MovieEntity title);
}
