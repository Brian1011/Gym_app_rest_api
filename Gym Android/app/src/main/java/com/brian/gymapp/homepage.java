package com.brian.gymapp;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

public class homepage extends AppCompatActivity {

    TextView textViewfullname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        //load local language
        loadLocale();

        //if user is not logged in go to login page
        if(!SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }

        //get the first and last name of the logged in user
        textViewfullname = (TextView)findViewById(R.id.user_welcome);

        //get the current logged in user
        User_model user = SharedPrefManager.getInstance(this).get_User();

        //set the values
        String welcome_message = getString(R.string.welcome)+" "+user.getFirstname()+" "+user.getLastname();
        textViewfullname.setText(welcome_message);
    }

    //go to user profile page
    public void user_Profile(View view) {
        Intent intent = new Intent(homepage.this,user_profile.class);
        startActivity(intent);
    }

    //go to map page
    public void map_page(View view) {
        Intent intent = new Intent(homepage.this,gym_map.class);
        startActivity(intent);
    }

    //go to new workout session
    public void new_workout_session(View view) {
        Intent intent = new Intent(homepage.this,new_workout.class);
        startActivity(intent);
    }

    //Clear the Shared Prefference and then return to login
    public void logout(View view) {
        //finish();
        SharedPrefManager.getInstance(getApplicationContext()).logout();
    }

    //Go to instructors page
    public void instructors_page(View view){
        Intent intent = new Intent(homepage.this,list_of_trainers.class);
        startActivity(intent);
    }

    //Go to past workouts session
    public void past_workouts(View view) {
        Intent intent = new Intent(homepage.this,past_workout.class);
        startActivity(intent);
    }

    //pop up language options
    public void pop_languages(View view) {
        //Show an alert
        //array of languages
        final String[] listItems = {"English","Swahili","French"};

        AlertDialog.Builder mbuilder = new AlertDialog.Builder(homepage.this);
        mbuilder.setTitle(getResources().getString(R.string.change_language));
        mbuilder.setSingleChoiceItems(listItems, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which == 0){
                    //set to swahili
                    setLocale("sw-rTZ");
                }else if(which == 1){
                    //set to English
                    setLocale("en");
                }else if(which == 2){
                    setLocale("fr");
                }

                //dismiss alert dialog when language is selected
                dialog.dismiss();
            }
        });
        AlertDialog mdialog = mbuilder.create();
        //show alert dialog
        mdialog.show();
    }

    private void setLocale(String lang){
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,getBaseContext().getResources().getDisplayMetrics());

        //save data to shared preference
        SharedPreferences.Editor editor = getSharedPreferences("settings",MODE_PRIVATE).edit();
        editor.putString("My Language",lang);
        editor.apply();
    }

    //load language in shared preference
    public void loadLocale(){
        SharedPreferences prefs = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String language = prefs.getString("My Language","");
        setLocale(language);
    }
}
