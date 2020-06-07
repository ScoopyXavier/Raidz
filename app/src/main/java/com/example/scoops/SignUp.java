package com.example.scoops;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class SignUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        ImageButton vol = (ImageButton) findViewById(R.id.btnVolunteer);

        vol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSignUp2();
            }
        });
    }

    public void openSignUp2(){
        Intent intent = new Intent(this,SignUp2.class);
        startActivity(intent);

    }
}
