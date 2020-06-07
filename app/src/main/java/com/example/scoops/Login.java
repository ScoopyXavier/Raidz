package com.example.scoops;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ImageButton volunteer = findViewById(R.id.btnLogin2);

        volunteer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openVolunteer();
            }
        });


    }

    public void openVolunteer(){
        Intent intent = new Intent(this,VolunteerProfile.class);
        startActivity(intent);
    }
}
