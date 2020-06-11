package com.example.scoops;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class LandingPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    //Move to next page on button click ("Sign Up")

        ImageButton SignUp = findViewById(R.id.btnSignUp);

        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSignUp();
            }
        });

        ImageButton login = findViewById(R.id.btnLogin1);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLogin2();
            }
        });
    }

    public void openSignUp(){
        Intent intent = new Intent (this,SignUp.class);
        startActivity(intent);


    }

    public void openLogin2(){
        Intent intent = new Intent (this,Login.class);
        startActivity(intent);
    }
}
