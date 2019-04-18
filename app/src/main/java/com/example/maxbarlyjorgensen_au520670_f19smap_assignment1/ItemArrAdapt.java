//Inspired by https://www.youtube.com/watch?v=FKUlw7mFXRM
//Icons inspired by

package com.example.maxbarlyjorgensen_au520670_f19smap_assignment1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import DB.MovieModel;


public class ItemArrAdapt extends ArrayAdapter {

    Context context;
    private List<MovieModel> movieList = new ArrayList<>();



    static class ItemView{
        TextView Titel;
        TextView UserRating;
        ImageView imgico;
        TextView userRate;
        TextView rate;
        TextView watched;
    }

    public ItemArrAdapt(Context context, int resource) {
        super(context, resource);
    }

    public void add(MovieModel mov) {
        movieList.add(mov);
        super.add(mov);
    }

    @Override
    public int getCount() {
        return this.movieList.size();
    }
    @Override
    public MovieModel getItem(int position) {
        return this.movieList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        ItemView view;
        if(row == null){
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.relative, parent, false);
            view = new ItemView();

            view.Titel = (TextView) row.findViewById(R.id.TextViewTitle);
            view.imgico = (ImageView) row.findViewById(R.id.imageViewR);
            view.rate = (TextView) row.findViewById(R.id.TextViewR);
            view.userRate = (TextView) row.findViewById(R.id.TextUR);
            view.watched = (TextView) row.findViewById(R.id.OverviewWatched);


            row.setTag(view);
        }else{

            view = (ItemView) row.getTag();
        }

        MovieModel movieItem = getItem(position);
        //Title
        if(movieItem.Title == null){
            view.Titel.setText(("Ingen titel"));
        }else{view.Titel.setText(movieItem.Title);}

        //Rating
        if(movieItem.imdbRating == null){
            view.rate.setText(("N/A"));
        }else{view.rate.setText(("R: " + movieItem.imdbRating));}


        //watched
        if(movieItem.Watched == null){
            view.watched.setText(("Watched: N"));
        }else{
            if(movieItem.Watched.equals("true"))
            {
                view.watched.setText(("Watched"));
            }
            else{
                view.watched.setText((""));
            }
        }


        //UserRating
        if(movieItem.Urating == null){
            view.userRate.setText(("N/A"));
        }else{
            if(movieItem.Urating.equals("0")) {
                view.userRate.setText(("N/A"));
            }else {
                view.userRate.setText(("UR: " + movieItem.Urating));
            }

        }



        //ICO
        if(movieItem.Genre == null){
            view.imgico.setImageResource(R.mipmap.unknown2fl_round);
        }else{

           String[] firstCategoty = movieItem.Genre.split(",");

           switch (firstCategoty[0]) {
               case "Action":
                   view.imgico.setImageResource(R.mipmap.action2fl_round);
                   break;

               case "History":
                   view.imgico.setImageResource(R.mipmap.history2fl_round);
                   break;

               case "Animation":
                   view.imgico.setImageResource(R.mipmap.animation2fl_round);
                   break;

               case "Drama":
                   view.imgico.setImageResource(R.mipmap.drama2fl_round);
                   break;

               case "Thriller":
                   view.imgico.setImageResource(R.mipmap.thrilller2fl_round);
                   break;

               case "Comedy":
                   view.imgico.setImageResource(R.mipmap.comedy2fl_round);
                   break;


               default:
                   view.imgico.setImageResource(R.mipmap.history2fl_round);
                   break;

           }


        }
        return row;
    }

}
