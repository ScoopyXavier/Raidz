package com.example.scoops;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class VolunteerProfile extends AppCompatActivity {

    private ImageButton logout, edit, history, notification, help, request;
    SessionManager sessionManager;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer_profile);


        sessionManager = new SessionManager(this);
        sessionManager.checkLoginV();

        //Hide status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        email = getIntent().getStringExtra("email");
        edit = findViewById(R.id.btnEditProfileV);
        history = findViewById(R.id.btnHistoryV);
        request = findViewById(R.id.btnRequestV);
        notification = findViewById(R.id.btnNotificationV);
        help = findViewById(R.id.btnHelpV);
        logout = findViewById(R.id.btnLogout);

        //LOGOUT
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManager.logoutV();
            }
        });

        //GO TO EDIT PROFILE PAGE
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //setValues();
                startActivity(new Intent(getApplicationContext(), EditProfile.class));

               /* if (ss_name != "" && ss_email != "" && ss_phone != "" && ss_address != "" && ss_city != "") {
                    Email.setText(ss_email);
                    Name.setText(ss_name);
                    Phone.setText(ss_phone);
                    Address.setText(ss_address);
                    City.setText(ss_city);
                }*/
            }
        });

        //GO TO HISTORY
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), HistoryActivity.class));
            }
        });

        //GO TO REQUEST FEED
        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRequestFeed();
            }
        });
    }

    private void openEdit(){

    }

    public void openRequestFeed(){
        Intent intent = new Intent(this, RequestFeed.class);
        intent.putExtra("volEmail", email);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
    }

}
