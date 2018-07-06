package com.brian.gymapp;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class past_workout extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager layoutManager;
    ProgressDialog progressDialog;

    List<pastWorkoutsUtils> pastWorkoutsUtilsList;

    RequestQueue rq;

    //url has to contain id of the current logged in user
    User_model user = SharedPrefManager.getInstance(this).get_User();
    final int user_id = user.getId();

    String request_url = public_url.past_workouts+user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_workout);

        //create a progress dialog to show the process
        //ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(past_workout.this);
        progressDialog.setMax(100);//make a maximum value
        progressDialog.setMessage("Loading...");
        progressDialog.setTitle("Gym App");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        progressDialog.setCancelable(false);
        //progressDialog.dismiss();

        rq = Volley.newRequestQueue(this);

        recyclerView = (RecyclerView)findViewById(R.id.recylerViewContainer_workoutsession);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        pastWorkoutsUtilsList = new ArrayList<>();
        sendRequest();
    }

    public void sendRequest(){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, request_url, null,
                new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                //once we get a response
                progressDialog.dismiss();

                for (int i = 0; i < response.length(); i++) {
                    pastWorkoutsUtils pastworkoutsUtils = new pastWorkoutsUtils();

                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        pastworkoutsUtils.setWorkoutDate(jsonObject.getString("date"));
                        pastworkoutsUtils.setWorkoutLocation(jsonObject.getString("location"));
                        pastworkoutsUtils.setWorkoutReps(jsonObject.getInt("number_of_reps"));
                        pastworkoutsUtils.setWorkoutType(jsonObject.getString("exercise_name"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    pastWorkoutsUtilsList.add(pastworkoutsUtils);
                }

                mAdapter = new CustomRecylerAdapter_pastworkouts(past_workout.this, pastWorkoutsUtilsList);
                recyclerView.setAdapter(mAdapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Volley Error","Error"+error);
            }
        });

        rq.add(jsonArrayRequest);
    }

}
