package com.example.scoops;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class ClientProfile extends AppCompatActivity {

    private ImageButton edit, notifications, history, help, request, logout;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_profile);

        sessionManager = new SessionManager(this);
        sessionManager.checkLoginC();

        edit = findViewById(R.id.btnEditProfileC);
        history = findViewById(R.id.btnHistoryC);
        request = findViewById(R.id.btnRequestC);
        notifications = findViewById(R.id.btnNotificationC);
        help = findViewById(R.id.btnHelpC);
        logout = findViewById(R.id.btnLogoutC);

        //Go to edit profile
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), EditProfileC.class));
            }
        });


        //LOGOUT
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManager.logoutC();
            }
        });
    }
}