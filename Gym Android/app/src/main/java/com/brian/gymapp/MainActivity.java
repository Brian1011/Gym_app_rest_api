package com.brian.gymapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText editTextemail, editTextpassword;
    ProgressBar progressBar;
    SharedPrefManager sharedPrefManager;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        editTextemail = (EditText)findViewById(R.id.login_email);
        editTextpassword = (EditText)findViewById(R.id.login_password);

        //ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMax(100);//make a maximum value
        progressDialog.setMessage("Loading...");
        progressDialog.setTitle("Gym App");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        //progressDialog.show();
        progressDialog.setCancelable(false);
        //we get a response
        //progressDialog.dismiss();

    }

    //open signup page
    public void signup_page(View view) {
        Intent intent = new Intent(this,signup_activity.class);
        startActivity(intent);
    }

    //Login in the user
    public void home_page(View view) {
        //first get the values
        final String email = editTextemail.getText().toString();
        final String password = editTextpassword.getText().toString();

        //validating the inputs
        if(TextUtils.isEmpty(email)){
            editTextemail.setError("Please Enter your email");
            editTextemail.requestFocus();
        }

        if(TextUtils.isEmpty(password)){
            editTextpassword.setError("Please Enter your password");
            editTextpassword.requestFocus();
            return;
        }

        if(password.length() < 4){
            editTextpassword.setError("Password cannot be less than 4");
            editTextpassword.requestFocus();
            return;
        }

        progressDialog.show();
        //if everything is fine
        StringRequest stringRequest = new StringRequest(Request.Method.POST, public_url.login_user,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                       //we get a response
                        progressDialog.dismiss();

                       //Toast.makeText(MainActivity.this,response,Toast.LENGTH_LONG).show();
                        try {
                            //converting response to json Object
                            JSONObject obj = new JSONObject(response);

                            //if no error in response
                            if (obj.getBoolean("status")) {
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                                //getting the user from the response
                                JSONObject userJSON = obj.getJSONObject("user");

                                User_model user = new User_model(
                                        userJSON.getInt("id"),
                                        userJSON.getString("firstname"),
                                        userJSON.getString("lastname"),
                                        userJSON.getString("email"),
                                        userJSON.getString("age"),
                                        userJSON.getString("gender"),
                                        userJSON.getString("preffered_location"),
                                        userJSON.getDouble("target_weight"),
                                        userJSON.getDouble("weight")
                                );

                                SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);
                                finish();
                                startActivity(new Intent(getApplicationContext(), homepage.class));
                            } else {
                                //display an error message
                               // startActivity(new Intent(getApplicationContext(), homepage.class));
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                                //Toast.makeText(getApplicationContext(),"Hello", Toast.LENGTH_SHORT).show();

                            }
                        } catch (JSONException E) {
                            E.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }){
            protected Map<String, String>getParams()throws AuthFailureError{
                Map<String, String>params = new HashMap<>();
                params.put("email",email);
                params.put("password",password);
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }


}
