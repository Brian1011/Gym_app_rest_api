package com.brian.gymapp;

import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class new_workout extends AppCompatActivity {

    EditText editTextlocation, editTextexercisename, editTextreps;
    TextView date_field_textview;
    String date_field;
    String dateMessage;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_workout);

        //set date to todays date
        Calendar cal = Calendar.getInstance();
        processDatePickerResult(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH));

        //get the values
        date_field_textview = (TextView) findViewById(R.id.session_date);
        editTextlocation = (EditText)findViewById(R.id.new_work_location);
        editTextexercisename = (EditText)findViewById(R.id.exercise_name);
        editTextreps = (EditText)findViewById(R.id.reps);

        //ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(new_workout.this);
        progressDialog.setMax(100);//make a maximum value
        progressDialog.setMessage("Loading...");
        progressDialog.setTitle("Gym App");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        //progressDialog.show();
        progressDialog.setCancelable(false);
        //we get a response
        //progressDialog.dismiss();
    }

    public void showDatePickerDialog(View v) {
        datepicker newFragment = new datepicker();
        newFragment.show(getSupportFragmentManager(),
                getString(R.string.date_picker));
    }

    public void processDatePickerResult(int year, int month, int day){
        String month_string = Integer.toString(month+1);
        String day_string = Integer.toString(day);
        String year_string = Integer.toString(year);

        //String dateMessage = (month_string + "/" + day_string + "/" + year_string);
        dateMessage = (year_string+"-"+month_string+"-"+day_string);

        //set date to the texfield
        date_field_textview = (TextView) findViewById(R.id.session_date);
        date_field_textview.setText(dateMessage);
    }

    //save new workout session
    public void save_workout(View view) {
        //step 1 validate text
        final String workout_date = date_field_textview.getText().toString();
        final String workout_name = editTextexercisename.getText().toString();
        final String workout_location = editTextlocation.getText().toString();
        final String workout_reps = editTextreps.getText().toString();

        //get user id from shared preference
        User_model user = SharedPrefManager.getInstance(this).get_User();
        final int user_id = user.getId();
        final String user_id_int = String.valueOf(user_id);

        //validate the data
        if(TextUtils.isEmpty(workout_location)){
            editTextlocation.setError("Insert a location");
            editTextlocation.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(workout_name)){
            editTextexercisename.setError("Insert Workout name");
            editTextexercisename.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(workout_reps)){
            editTextreps.setError("Reps Cant be empty");
            editTextreps.requestFocus();
            return;
        }

        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, public_url.new_workout,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //ProgressBar.setVisibility(View.Gone);
                        progressDialog.dismiss();
                        try {
                            //converting response to JSON object
                            JSONObject obj = new JSONObject(response);

                            //if no error response
                            if (obj.getBoolean("status")) {
                                Toast.makeText(getApplicationContext(), obj.getString("message"),Toast.LENGTH_SHORT).show();

                                //Refresh Current Activity
                                finish();
                                startActivity(getIntent());
                            }else{
                                Toast.makeText(getApplicationContext(), obj.toString(),Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                // params.put("id",user_id_int);
                params.put("user_id",user_id_int);
                params.put("date",workout_date);
                params.put("location",workout_location);
                params.put("exercise_name",workout_name);
                params.put("number_of_reps",workout_reps);
                return params;
            }

        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);


    }
}
