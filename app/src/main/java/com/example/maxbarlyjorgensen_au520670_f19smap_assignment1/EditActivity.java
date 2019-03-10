package com.example.maxbarlyjorgensen_au520670_f19smap_assignment1;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

public class EditActivity extends AppCompatActivity {
    private Button backBtn;
    private SeekBar seek_Bar;
    private static TextView rating;
    private String[] data;
    private int recievedPosition;
    private EditText comment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);


        backBtn = (Button) findViewById(R.id.btnsave);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBackMain();
            }


        });


        backBtn = (Button) findViewById(R.id.btncancel);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBackCancelMain();
            }


        });

        data = (String[]) getIntent().getSerializableExtra("DATA");
        recievedPosition = (int) getIntent().getSerializableExtra("POS");


        TextView title = (TextView) findViewById(R.id.EditTitle);
        title.setText(data[0]);
        setSeekbar();

        comment = (EditText) findViewById(R.id.editTextComment);

        final CheckBox checked = (CheckBox) findViewById(R.id.EditWatched);
        if(data[5].equals("true")){
            checked.setChecked(true);
        }
        checked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checked.isChecked()){
                    data[5] = "true";
                }
            }
        });





    }

    private void goBackMain() {
        data[6] = comment.getText().toString();
        Intent intent;
        intent = new Intent(this, OverviewActivity.class);
        intent.putExtra("DATA", data);
        intent.putExtra("POS", recievedPosition);
        startActivity(intent);
    }

    private void goBackCancelMain() {
        Intent intent;
        intent = new Intent(this, OverviewActivity.class);
        startActivity(intent);
    }


    public void setSeekbar() {
        seek_Bar = (SeekBar) findViewById(R.id.seekBar);
        float flval = Float.parseFloat(data[4]);
        int valCur = (int) (flval*10);
        seek_Bar.setProgress(valCur);

        rating = (TextView) findViewById(R.id.textViewRating);
        rating.setText(data[4] + " / " +seek_Bar.getMax()/10);

        seek_Bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            float progressVal;
            String valCur = String.valueOf(progressVal);
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressVal =(float) progress;
                rating.setText(progressVal/10+ " / " +seek_Bar.getMax()/10);
                data[4] = String.valueOf(progressVal/10);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                rating.setText(progressVal/10+ " / " +seek_Bar.getMax()/10);
                data[4] = String.valueOf(progressVal/10);
            }
        });

    }

}