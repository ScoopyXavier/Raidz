package com.example.scoops;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;

public class SignUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //Hide status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        //Volunteer Sign up
        ImageButton vol = (ImageButton) findViewById(R.id.btnVolunteer);
        vol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSignUp2();
            }
        });

        //Client Sign up
        ImageButton cli = (ImageButton) findViewById(R.id.btnClient);
        cli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSignUpC();
            }
        });
    }

    public void openSignUp2(){
        Intent intent = new Intent(this,SignUp2.class);
        startActivity(intent);

    }

    public void openSignUpC(){
        Intent intent = new Intent(this,SignUpC.class);
        startActivity(intent);

    }
}
