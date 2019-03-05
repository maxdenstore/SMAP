package com.example.maxbarlyjorgensen_au520670_f19smap_assignment1;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.MainThread;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class ItemArrAdapt extends ArrayAdapter {

    private List<String[]> movieList = new ArrayList<String[]>();


    static class ItemView{
        TextView Titel;
        TextView UserRating;
        ImageView imgico;
        TextView userRate;
        TextView rate;
    }

    public ItemArrAdapt(Context context, int resource) {
        super(context, resource);
    }

    public void add(String[] object) {
        movieList.add(object);
        super.add(object);
    }

    @Override
    public int getCount() {
        return this.movieList.size();
    }
    @Override
    public String[] getItem(int position) {
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


            row.setTag(view);
        }else{

            view = (ItemView) row.getTag();
        }

        String[] movieItem = getItem(position);

        if(movieItem[0] == null){
            view.Titel.setText(("Ingen titel"));
       }else{view.Titel.setText(movieItem[0]);}

        if(movieItem[3] == null){
            view.userRate.setText(("Ingen rating blev fundet"));
        }else{view.userRate.setText(("R: " + movieItem[3]));}


        if(movieItem[2] == null){
            view.imgico.setImageResource(R.mipmap.unknown2fl_round);
        }else{

           String[] firstCategoty = movieItem[2].split(",");

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
