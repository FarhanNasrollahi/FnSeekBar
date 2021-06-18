package com.example.testseekbar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.fnseekbar.FnSeekBar;

public class MainActivity extends AppCompatActivity {

    FnSeekBar fnSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        fnSeekBar = findViewById(R.id.fnseekbarview);
        fnSeekBar.setSeekbarChangeListener(new FnSeekBar.OnSeekbarChangeListener() {
            @Override
            public void values(int values) {

            }

            @Override
            public void stopTouch() {

            }

            @Override
            public void startTouch() {

            }
        });

    }
}