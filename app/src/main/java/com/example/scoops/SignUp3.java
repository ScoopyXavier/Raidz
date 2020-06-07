package com.example.scoops;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class SignUp3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up3);

        ImageButton SignUp4 = findViewById(R.id.btnCont2);

        SignUp4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSignUp4();
            }
        });
    }

    public void openSignUp4(){
        Intent intent = new Intent(this, SignUp4.class);
        startActivity(intent);
    }
}
