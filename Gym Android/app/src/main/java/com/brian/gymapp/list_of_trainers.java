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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class list_of_trainers extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager layoutManager;
    ProgressDialog progressDialog;

    List<InstructorsUtils> instructorsUtilsList;

    RequestQueue rq;
   // String request_url = public_url.custom_url+"instructors";//"http://192.168.43.111:8000/api/";
   String request_url = public_url.custom_url+"instructors";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_trainers);

        rq = Volley.newRequestQueue(this);
        recyclerView = (RecyclerView)findViewById(R.id.recylerViewContainer_trainers);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        instructorsUtilsList = new ArrayList<>();
        sendRequest();

        //ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(list_of_trainers.this);
        progressDialog.setMax(100);//make a maximum value
        progressDialog.setMessage("Loading...");
        progressDialog.setTitle("Gym App");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        progressDialog.setCancelable(false);
        //we get a response
        //progressDialog.dismiss();
    }

    public void sendRequest(){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, request_url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    //we get a response
                    progressDialog.dismiss();

                    InstructorsUtils instructorsUtils = new InstructorsUtils();

                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        instructorsUtils.setInstructor_name(jsonObject.getString("name"));
                        instructorsUtils.setEmail(jsonObject.getString("email"));
                        instructorsUtils.setGender(jsonObject.getString("gender"));
                        instructorsUtils.setGym_name(jsonObject.getString("gym_name"));
                        instructorsUtils.setInstructor_contacts(jsonObject.getString("contacts"));
                        instructorsUtils.setInstructor_image(jsonObject.getString("photo"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    instructorsUtilsList.add(instructorsUtils);
                }

                mAdapter = new CustomRecylerAdapter_Instructors(list_of_trainers.this, instructorsUtilsList);
                recyclerView.setAdapter(mAdapter);
            }

        }, new Response.ErrorListener(){
            public void onErrorResponse(VolleyError error) {
                Log.i("Volley Error: ","error"+error);
            }

        });
        rq.add(jsonArrayRequest);
    }

}
