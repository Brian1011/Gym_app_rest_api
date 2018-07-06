package com.brian.gymapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

//store data of the currently logged in user
public class SharedPrefManager {
    //the constants
    private static final String SHARED_PREF_NAME = "userdetailspref";
    private static final String KEY_EMAIL = "keyemail";
    private static final String KEY_GENDER = "keygender";
    private static final String KEY_ID = "keyid";
    private static final String KEY_AGE = "keyage";
    private static final String KEY_PREFFERED_LOCATION = "key_preffered_Location";
    private static final String KEY_TARGET_WEIGHT = "key_target_weight";
    private static final String KEY_CURRENT_WEIGHT = "key weight";
    private static final String KEY_FIRST_NAME = "key FNAME";
    private static final String KEY_LAST_NAME = "key LAST NAME";

    private static SharedPrefManager mInstance;
    private static Context mCtx;

    private SharedPrefManager(Context context)
    {
        mCtx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context){
        if(mInstance == null){
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    //method to let the user login
    //this method will store the user data in the shared preferences
    public void userLogin(User_model user_model){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_ID,user_model.getId());
        editor.putString(KEY_FIRST_NAME,user_model.getFirstname());
        editor.putString(KEY_LAST_NAME,user_model.getLastname());
        editor.putString(KEY_EMAIL,user_model.getEmail());
        editor.putString(KEY_AGE,user_model.getAge());
        editor.putString(KEY_GENDER,user_model.getGender());
        editor.putString(KEY_PREFFERED_LOCATION,user_model.getPreffered_location());
        editor.putFloat(KEY_TARGET_WEIGHT, ((float) user_model.getTarget_weight()));
        editor.putFloat(KEY_CURRENT_WEIGHT, ((float) user_model.getWeight()));
        editor.apply();
        //editor.commit();
    }

    //this method will check whether the user is logged in or not
    public boolean isLoggedIn(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_EMAIL, null)!=null;
    }

    //this method will give the logged in user
    public User_model get_User(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new User_model(
                sharedPreferences.getInt(KEY_ID, -1),
                sharedPreferences.getString(KEY_FIRST_NAME,null),
                sharedPreferences.getString(KEY_LAST_NAME,null),
                sharedPreferences.getString(KEY_EMAIL, null),
                sharedPreferences.getString(KEY_AGE,null),
                sharedPreferences.getString(KEY_GENDER,null),
                sharedPreferences.getString(KEY_PREFFERED_LOCATION,null),
                sharedPreferences.getFloat(KEY_TARGET_WEIGHT, 0),
                sharedPreferences.getFloat(KEY_CURRENT_WEIGHT,0)
        );
    }

    //this method will logout the user
    public void logout(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        mCtx.startActivity(new Intent(mCtx, MainActivity.class));
    }


}
