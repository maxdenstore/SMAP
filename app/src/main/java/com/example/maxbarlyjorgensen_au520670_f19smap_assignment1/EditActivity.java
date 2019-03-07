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


        backBtn = (Button) findViewById(R.id.button);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBackMain();
            }


        });

        data = (String[]) getIntent().getSerializableExtra("DATA");
        recievedPosition = (int) getIntent().getSerializableExtra("POS");


        TextView title = (TextView) findViewById(R.id.EditTitle);
        title.setText(data[0]);
        setSeekbar();

        comment = (EditText) findViewById(R.id.editTextComment);

        final CheckBox checked = (CheckBox) findViewById(R.id.EditWatched);

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

    public void setSeekbar() {
        seek_Bar = (SeekBar) findViewById(R.id.seekBar);
        seek_Bar.setProgress(Integer.decode(data[4]));
        rating = (TextView) findViewById(R.id.textViewRating);
        rating.setText(seek_Bar.getProgress() + " / " +seek_Bar.getMax());

        seek_Bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressVal;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressVal = progress;
                rating.setText(progress+ " / " +seek_Bar.getMax());
                data[4] = Integer.toString(progressVal);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                rating.setText(progressVal+ " / " +seek_Bar.getMax());
                data[4] = Integer.toString(progressVal);
            }
        });

    }

}