package com.pato.gadsleaderboard.views;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.pato.gadsleaderboard.R;

public class SubmitProjectActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_project);

        ActionBar actionBar = getSupportActionBar();
        //enable up button.
        actionBar.setDisplayHomeAsUpEnabled(true);


    }
}
