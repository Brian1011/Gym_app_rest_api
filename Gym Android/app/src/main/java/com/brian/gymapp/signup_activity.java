package com.brian.gymapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
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

public class signup_activity extends AppCompatActivity  {

    ImageView imageView;
    private static final int PICK_IMAGE=100;
    Uri imageUri;
    EditText editTextfirstname, editTextlastname, editTextemail, editTextpass1, editTextpass2;
    RadioGroup signup_radio_group;
    RadioGroup radioGroupGender;
    RadioButton radiomale, radiofemale, radiothers;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_activity);

        //allow user to get image from gallery
        imageView = (ImageView)findViewById(R.id.user_imageview);

        //get the values
        editTextemail = (EditText)findViewById(R.id.email);
        editTextfirstname = (EditText)findViewById(R.id.first_name);
        editTextlastname = (EditText)findViewById(R.id.lastname);
        editTextpass1 = (EditText)findViewById(R.id.pass1);
        editTextpass2 = (EditText)findViewById(R.id.pass2);
        signup_radio_group = (RadioGroup) findViewById(R.id.signup_radio_group);
        radioGroupGender = (RadioGroup)findViewById(R.id.signup_radio_group);
        radiomale = (RadioButton)findViewById(R.id.radio_male);
        //radiomale.isChecked(true);
        radiofemale = (RadioButton)findViewById(R.id.radio_female);
        radiothers = (RadioButton)findViewById(R.id.radio_others);

        //ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(signup_activity.this);
        progressDialog.setMax(100);//make a maximum value
        progressDialog.setMessage("Loading...");
        progressDialog.setTitle("Gym App");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        //we get a response
        //progressDialog.dismiss();
    }

    //onclick open gallery
    public void openGallery(View view) {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
        }
    }

    //go to login page
    public void login_page(View view) {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    //on button clicked
    public void registerUser(View view) {
       final String email = editTextemail.getText().toString();
       final String firstname = editTextfirstname.getText().toString();
       final String lastname = editTextlastname.getText().toString();
       final String pass1 = editTextpass1.getText().toString();
       final String pass2 = editTextpass2.getText().toString();
       final String gender = ((RadioButton)findViewById(radioGroupGender.getCheckedRadioButtonId())).getText().toString();
       // final String gender = ((RadioButton)findViewById(R.id.s)).getText().toString();

        //perform validations
        if(TextUtils.isEmpty(email)){
            editTextemail.setError("Please Insert Email");
            editTextemail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextemail.setError("Enter a valid email address");
            editTextemail.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(firstname)){
            editTextfirstname.setError("Insert Name");
            editTextfirstname.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(lastname)){
            editTextlastname.setError("Insert Name");
            editTextlastname.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(pass1)){
            editTextpass1.setError("Password Cannot be empty");
            editTextpass1.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(pass2)){
            editTextpass2.setError("Password Cannot be empty");
            editTextpass2.requestFocus();
            return;
            //return;
        }


        if(!pass1.equals(pass2)){
            Toast.makeText(getApplicationContext(),"Password are not same",Toast.LENGTH_SHORT).show();
            return;
           // return;
        }

        if(pass1.length()<4){
            Toast.makeText(getApplicationContext(),"Password lenght has to be more than 4",Toast.LENGTH_SHORT).show();
            return;
           // return;
        }

        if(gender == null){
            Toast.makeText(getApplicationContext(),"Select you gender",Toast.LENGTH_SHORT).show();
        }


        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, public_url.register_user,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //ProgressBar.setVisibility(View.Gone);
                        //we get a response
                        progressDialog.dismiss();

                        try {
                            //converting response to JSON object
                            JSONObject obj = new JSONObject(response);

                            //if no error response
                            if (obj.getBoolean("status")) {
                                Toast.makeText(getApplicationContext(), obj.getString("message"),Toast.LENGTH_SHORT).show();

                                //getting the user from the response
                                JSONObject userJson = obj.getJSONObject("user");

                                //create a new user object
                                User_model user = new User_model(
                                        userJson.getInt("id"),
                                        userJson.getString("firstname"),
                                        userJson.getString("lastname"),
                                        userJson.getString("email"),
                                        userJson.getString("age"),
                                        userJson.getString("gender"),
                                        userJson.getString("preffered_location"),
                                        userJson.getDouble("target_weight"),
                                        userJson.getDouble("weight")
                                );

                                //Storing the user in shared Preference
                                SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);

                                //Starting the homepage activity
                                finish();
                                startActivity(new Intent(getApplicationContext(), homepage.class));
                            }else{
                                //get the json object validation
                                Toast.makeText(getApplicationContext(), obj.toString(),Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("firstname",firstname);
                params.put("lastname",lastname);
                params.put("gender",gender);
                params.put("password",pass1);
                params.put("email",email);
                return params;
            }

        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }
}
