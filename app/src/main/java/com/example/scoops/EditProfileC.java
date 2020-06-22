package com.example.scoops;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.HashMap;

import static com.example.scoops.Login.ss_email;
import static com.example.scoops.Login.ss_name;

public class EditProfileC extends AppCompatActivity {

    private EditText Name, Email, Phone, Address, City;
    private ImageButton save;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_c);

        sessionManager = new SessionManager(this);

        Name = findViewById(R.id.txtNameEC);
        Email = findViewById(R.id.txtMailEC);
        Phone = findViewById(R.id.txtPhoneEC);
        Address = findViewById(R.id.txtAddressEC);
        City = findViewById(R.id.txtCityEC);
        save = findViewById(R.id.btnSaveC);


        HashMap<String, String> user = sessionManager.getUserDetail();
        String mName = user.get(sessionManager.NAME);
        String mEmail = user.get(sessionManager.EMAIL);

        Name.setText(mName);
        Email.setText(mEmail);
    }
}