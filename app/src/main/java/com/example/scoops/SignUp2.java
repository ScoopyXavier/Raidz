package com.example.scoops;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class SignUp2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up2);

        ImageButton SignUp3 = findViewById(R.id.btnCont1);

        SignUp3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSignUp3();
            }
        });


    }
    public void openSignUp3(){
        Intent intent = new Intent(this,SignUp3.class);
        startActivity(intent);
    }
}
