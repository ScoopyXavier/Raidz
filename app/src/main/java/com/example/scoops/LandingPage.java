package com.example.scoops;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import static com.example.scoops.SessionManager.EMAIL;
import static com.example.scoops.SessionManager_C.EMAIL_C;


public class LandingPage extends AppCompatActivity {

    SessionManager sessionManager;
    SessionManager_C sessionManager_c;
    Animation topAnim, bottAnim;
    ImageView welcome, hands, discover;
    ImageButton login, SignUp;
    private static int SPLASH = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sessionManager = new SessionManager(this);

        //Animation
        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        //Hide status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        welcome = findViewById(R.id.imageView2);
        hands = findViewById(R.id.imageView);
        discover = findViewById(R.id.imageView3);

        welcome.setAnimation(topAnim);
        hands.setAnimation(topAnim);
        discover.setAnimation(bottAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(LandingPage.this, Login.class));
                finish();
            }
        }, SPLASH);



        /*if (EMAIL != null || !EMAIL.equals("")){
            Intent intent = new Intent(LandingPage.this, VolunteerProfile.class);
            startActivity(intent);
            killActivity();
        }

        if (EMAIL_C != null || !EMAIL_C.equals("")){
            Intent intent = new Intent(LandingPage.this, ClientProfile.class);
            startActivity(intent);
            finish();
        }*/

    }

    public void openSignUp(){
        Intent intent = new Intent (this,SignUp.class);
        startActivity(intent);


    }

    public void openLogin2(){
        Intent intent = new Intent (this,Login.class);
        startActivity(intent);
    }

    private void killActivity() {
        finish();
    }
}
