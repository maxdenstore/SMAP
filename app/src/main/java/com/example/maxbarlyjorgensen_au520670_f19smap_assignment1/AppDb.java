package com.example.maxbarlyjorgensen_au520670_f19smap_assignment1;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {MovieEntity.class}, version = 1)
public abstract class AppDb extends RoomDatabase {
    public abstract MovieDao movieDao();
}
