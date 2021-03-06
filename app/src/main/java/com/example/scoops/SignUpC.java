package com.example.scoops;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class SignUpC extends AppCompatActivity {
    //Registration
    private EditText Name, Email, Password, Confirm;
    private String s_Name, s_Email, s_Password;
    private CheckBox peek, peek2;
    private ImageButton register;
    private ProgressBar loading;
    private static String URL_REG = "https://lamp.ms.wits.ac.za/~s1445435/registerC.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_c);
        register = findViewById(R.id.btnRegister2);

        //Hide status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        //Registration/
        loading = findViewById(R.id.loading);
        Name = findViewById(R.id.txtName2);
        Email = findViewById(R.id.txtMail4);
        Password = findViewById(R.id.txtCreate2);
        Confirm = findViewById(R.id.txtConfirm2);
        peek = findViewById(R.id.peek4);
        peek2 = findViewById(R.id.peek3);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String s_email = Email.getText().toString().trim();
                String s_Name = Name.getText().toString().trim();
                String s_Confirm = Confirm.getText().toString().trim();
                String s_password = Password.getText().toString().trim();

                //Disallow Continuation when text_box empty
                if (!s_Name.isEmpty() && !s_Confirm.isEmpty() && !s_email.isEmpty() && !s_password.isEmpty() && s_password.equals(s_Confirm)){
                    Registration(s_Name, s_email, s_password, s_Confirm);
                }
                else {
                    Confirm.setError(null);
                    Password.setError(null);
                    Name.setError(null);
                    Email.setError(null);

                    if(s_Confirm != s_password) {
                        Confirm.setError("Passwords do not match");
                        Password.setError("Passwords do not match");
                    }
                    if (s_Name.isEmpty()) {
                        Name.setError("Please Enter Your First Name");
                    }
                    if (s_email.isEmpty()) {
                        Email.setError("Please Enter Your Email Address");
                    }
                    if (s_password.isEmpty()) {
                        Password.setError("Please Enter Your Password");
                    }
                    if (s_Confirm.isEmpty()) {
                        Confirm.setError("Please Confirm Your Password");
                    }
                }
            }
        });

        //Show Password
        peek.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    Password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else{
                    Password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        peek2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    Confirm.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    Confirm.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

    }

    //Registration
    private void Registration(final String name, final String email, final String password, final String confirm) {

        //Disallow Continuation when text_box empty
        if (Name.getText().toString().equals("")) {
            Toast.makeText(this, "Please Enter Your First Name", Toast.LENGTH_SHORT).show();
        }else if (Email.getText().toString().equals("")) {
            Toast.makeText(this, "Please Enter Your Email Address", Toast.LENGTH_SHORT).show();
        } else if (Password.getText().toString().equals("")) {
            Toast.makeText(this, "Please Enter Your Password", Toast.LENGTH_SHORT).show();
        } else if (Confirm.getText().toString().equals("")) {
            Toast.makeText(this, "Please Confirm Your Password", Toast.LENGTH_SHORT).show();
        } else {
            loading.setVisibility(View.VISIBLE);
            register.setVisibility(View.GONE);

            s_Name = this.Name.getText().toString().trim();
            s_Email = this.Email.getText().toString().trim();
            s_Password = this.Password.getText().toString().trim();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REG, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    loading.setVisibility(View.GONE);
                    register.setVisibility(View.VISIBLE);

                    if (response.contains("The Email address is already registered. Choose a different Email address or Login")){
                        Toast.makeText(SignUpC.this, response, Toast.LENGTH_SHORT).show();
                        Email.setError("Already Registered");
                    }
                    else{
                        Toast.makeText(SignUpC.this, response, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), Login.class));
                        finish();
                    }

                }

            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(SignUpC.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                            loading.setVisibility(View.GONE);
                            register.setVisibility(View.VISIBLE);
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("Name", s_Name);
                    params.put("Email", s_Email);
                    params.put("Password", s_Password);

                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }
    }
}
