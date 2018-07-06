package com.brian.gymapp;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

public class splash extends AppCompatActivity {
    /*Duration to wait */
    final int SPLASH_DISPLAY_LENGTH = 3000;
    ProgressBar pg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        /*
        * New Handler to start the menu activity
        * and close this splash screen after some seconds
        * */
        // Initialize the 2 intents

        //if user is logged in go to homepage
        if(SharedPrefManager.getInstance(this).isLoggedIn()){
            Thread timer = new Thread(){
                public void run() {
                    try {
                        sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        Intent intent = new Intent(splash.this,homepage.class);
                        startActivity(intent);
                        finish();
                    }
                }
            };
            timer.start();
        } else {
            Thread timer = new Thread(){
                public void run() {
                    try {
                        sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        Intent intent = new Intent(splash.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            };
            timer.start();
        }




    }

}
