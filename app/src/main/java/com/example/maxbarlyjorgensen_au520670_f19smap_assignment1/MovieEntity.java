package com.example.maxbarlyjorgensen_au520670_f19smap_assignment1;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;


@Entity
public class MovieEntity {
    @PrimaryKey int movId;

    @ColumnInfo(name = "Title")
        public String title;

    @ColumnInfo(name = "Genre")
    public String Genre;

    @ColumnInfo(name = "ImdbScore")
    public String ImdbScore;

    @ColumnInfo(name = "UScore")
    public String UScore;

    @ColumnInfo(name = "Plot")
    public String Plot;

    @ColumnInfo(name = "Comment")
    public String Comment;

    @ColumnInfo(name = "Watched")
    public String Watched;
}

