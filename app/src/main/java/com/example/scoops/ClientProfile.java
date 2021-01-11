package com.example.scoops;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import static com.example.scoops.NotificationActivity_C.checkStatus;
import static com.example.scoops.NotificationActivity_C.removeCheck;

public class ClientProfile extends AppCompatActivity {

    private ImageButton edit, notifications, history, help, request, logout;
    //private Button checkStatus;
    SessionManager_C sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_profile);

        //AlarmHandler alarmHandler = new AlarmHandler(this);
        //alarmHandler.cancelAlarmManager();

        //Hide status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        sessionManager = new SessionManager_C(this);
       sessionManager.checkLoginC();

       edit = findViewById(R.id.btnEditProfileC);
        history = findViewById(R.id.btnHistoryC);
        request = findViewById(R.id.btnRequestC);
        notifications = findViewById(R.id.btnNotificationC);
        help = findViewById(R.id.btnHelpC);
        logout = findViewById(R.id.btnLogoutC);
        request = findViewById(R.id.btnRequestC);
        history = findViewById(R.id.btnHistoryC);

        //Go to edit profile
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), EditProfileC.class));

            }
        });

        //POST REQUEST
        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), PostRequest.class));
            }
        });

        //LOGOUT
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManager.logoutC();
                //alarmHandler.cancelAlarmManager();
            }
        });

        //GO TO HISTORY
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), HistoryActivity_C.class));
            }
        });

        notifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), NotificationActivity_C.class));

            }
        });

    }
    @Override
    public void onBackPressed() {
    }

}