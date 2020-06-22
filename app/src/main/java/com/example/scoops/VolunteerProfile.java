package com.example.scoops;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

    private static String URL_LOGIN = "https://lamp.ms.wits.ac.za/~s1445435/loginF.php";

    private ImageButton logout, edit, history, notification, help, request;
    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer_profile);


        sessionManager = new SessionManager(this);
        sessionManager.checkLoginV();

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


        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), EditProfile.class));
            }
        });
    }
}
