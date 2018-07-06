package com.brian.gymapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

public class user_profile extends AppCompatActivity {

    ImageView imageView;
    private static final int PICK_IMAGE=100;
    Uri imageUri;
    TextView textViewemail;
    private EditText editTextfname, editTextlname, editTexttargetweight, editTextcurrentweight, editTextprefferedlocation, editTextage;
    RadioGroup radioGroupGender;
    RadioButton radiomale, radiofemale, radiothers;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        //allow user to get image from gallery
        imageView = (ImageView)findViewById(R.id.user_imageviews);

        //get the views
        textViewemail = (TextView)findViewById(R.id.user_email);
        editTextfname = (EditText)findViewById(R.id.user_firstname);
        editTextlname = (EditText)findViewById(R.id.user_lastname);
        editTexttargetweight = (EditText)findViewById(R.id.user_target_weight);
        editTextprefferedlocation = (EditText)findViewById(R.id.user_preffered_location);
        editTextcurrentweight = (EditText)findViewById(R.id.user_current_weight);
        editTextage = (EditText)findViewById(R.id.user_age);
        radioGroupGender = (RadioGroup)findViewById(R.id.profile_radio_group);
        radiomale = (RadioButton)findViewById(R.id.radio_male);
        radiofemale = (RadioButton)findViewById(R.id.radio_female);
        radiothers = (RadioButton)findViewById(R.id.radio_others);


        //get the first and last name of the logged in user
       // textViewfullname = (TextView)findViewById(R.id.user_welcome);

        //get the current logged in user
        User_model user = SharedPrefManager.getInstance(this).get_User();

        //display the details of the user
        textViewemail.setText(user.getEmail());
        editTextfname.setText(user.getFirstname());
        editTextlname.setText(user.getLastname());
        /*
        editTextprefferedlocation.setText(user.getPreffered_location());
        editTextage.setText(user.getAge());
        editTextcurrentweight.setText(String.valueOf(user.getWeight()));
        editTexttargetweight.setText(String.valueOf(user.getTarget_weight()));
        */

        if(user.getPreffered_location().equals("none")){
            //display default message
        }else{
            //display from the db
            editTextprefferedlocation.setText(user.getPreffered_location());
        }

        if(user.getAge().equals("0")){
            //display default message
        }else{
            //display from the db
            editTextage.setText(user.getAge());
        }

        if(user.getWeight()!=0){
            editTextcurrentweight.setText(String.valueOf(user.getWeight()));
        }

        if(user.getTarget_weight()!=0){
            editTexttargetweight.setText(String.valueOf(user.getTarget_weight()));
        }


        //check the gender and set appropriate images
        if(user.getGender().equals("Male")){
            imageView.setImageResource(R.drawable.male_official);
            radiomale.setChecked(true);
        }else if(user.getGender().equals("Female")){
            imageView.setImageResource(R.drawable.female3);
            radiofemale.setChecked(true);
        }else{
            radiothers.setChecked(true);
        }

        findViewById(R.id.button_update_profile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if user clicks here
                //update the profile
                update_user_profile();
            }
        });

        //ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(user_profile.this);
        progressDialog.setMax(100);//make a maximum value
        progressDialog.setMessage("Loading...");
        progressDialog.setTitle("Gym App");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        //progressDialog.show();
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

    //on click
    public void update_user_profile() {
        //get the values
       final String firstname = editTextfname.getText().toString();
       final String lastname = editTextlname.getText().toString();
       final String currentweight = editTextcurrentweight.getText().toString();
       final String prefferedlocation = editTextprefferedlocation.getText().toString();
       final String targetweight = editTexttargetweight.getText().toString();
       final String age = editTextage.getText().toString();
       final String gender = ((RadioButton) findViewById(radioGroupGender.getCheckedRadioButtonId())).getText().toString();


       //email and id are from shared preference i.e the current logged in usser
       User_model user = SharedPrefManager.getInstance(this).get_User();
       final String email = user.getEmail();
       final int user_id = user.getId();
       final String user_id_int = String.valueOf(user_id);

        //validate the data
        //perform validations
        if(TextUtils.isEmpty(age)){
            editTextage.setError("Insert your Age");
            editTextage.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(firstname)){
            editTextfname.setError("Insert Name");
            editTextfname.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(lastname)){
            editTextlname.setError("Insert Name");
            editTextlname.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(currentweight)){
            editTextcurrentweight.setError("Current weight can't be empty");
            editTextcurrentweight.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(targetweight)){
            editTexttargetweight.setError("Target weight can't be empty");
            editTexttargetweight.requestFocus();
            return;
            //return;
        }

        if(gender == null){
            Toast.makeText(getApplicationContext(),"Select you gender",Toast.LENGTH_SHORT).show();
        }

        if(TextUtils.isEmpty(prefferedlocation)){
            editTextprefferedlocation.setError("Location cant be empty");
            editTextprefferedlocation.requestFocus();
            return;
        }

        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, public_url.update_profile+user_id_int,
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

                                //Refresh Current Activity
                                finish();
                                startActivity(getIntent());
                            }else{
                                //get the json object validation
                                //JSONObject userJson = obj.getJSONObject("user");
                               // Log.d("Result",obj.toString(4));

                                //Toast.makeText(getApplicationContext(),firstname+lastname+email+age+gender+currentweight+targetweight, Toast.LENGTH_LONG).show();
                                //Toast.makeText(getApplicationContext(), public_url.update_profile+user_id_int,Toast.LENGTH_SHORT).show();
                                Toast.makeText(getApplicationContext(), obj.toString(),Toast.LENGTH_LONG).show();
                                //textViewemail.setText(firstname+lastname+email+age+gender+currentweight+targetweight);

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
                params.put("firstname",firstname);
                params.put("lastname",lastname);
                params.put("email",email);
                params.put("age",age);
                params.put("gender",gender);
                params.put("weight",currentweight);
                params.put("target_weight",targetweight);
                params.put("preffered_location",prefferedlocation);
                return params;
            }

        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);


    }
}
