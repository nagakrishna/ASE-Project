package com.example.nagakrishna.farmville_new;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class HomeActivity extends AppCompatActivity {

    public static final String MY_PREFS_NAME = "MyPrefsFile";
    private ImageView imageView1;
    private ImageView imageView2;
    private ImageView imageView3;
    private ImageView imageView4;
    private ImageView imageView5;
    private ImageView imageView6;
    private ImageView imageView7;
    private ImageView imageView8;
    private ImageView imageView9;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.imageView1 = (ImageView) findViewById(R.id.imageviewTAB1);
        this.imageView2 = (ImageView) findViewById(R.id.imageviewTAB2);
        this.imageView3 = (ImageView) findViewById(R.id.imageviewTAB3);
        this.imageView4 = (ImageView) findViewById(R.id.imageviewTAB4);
        this.imageView5 = (ImageView) findViewById(R.id.imageviewTAB5);
        this.imageView6 = (ImageView) findViewById(R.id.imageviewTAB6);
        this.imageView7 = (ImageView) findViewById(R.id.imageviewTAB7);
        this.imageView8 = (ImageView) findViewById(R.id.imageviewTAB8);
        this.imageView9 = (ImageView) findViewById(R.id.imageviewTAB9);
        this.imageView1.setImageDrawable(getResources().getDrawable(R.drawable.weather));
        this.imageView2.setImageDrawable(getResources().getDrawable(R.drawable.market));
        this.imageView3.setImageDrawable(getResources().getDrawable(R.drawable.news));
        this.imageView4.setImageDrawable(getResources().getDrawable(R.drawable.books));
        this.imageView5.setImageDrawable(getResources().getDrawable(R.drawable.market_prices));
        this.imageView6.setImageDrawable(getResources().getDrawable(R.drawable.suggestions));
        this.imageView7.setImageDrawable(getResources().getDrawable(R.drawable.places));
        this.imageView8.setImageDrawable(getResources().getDrawable(R.drawable.settings));
        this.imageView9.setImageDrawable(getResources().getDrawable(R.drawable.temp));


        //retrieving data from shared preference
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String email = prefs.getString("email", null);
    }

}
