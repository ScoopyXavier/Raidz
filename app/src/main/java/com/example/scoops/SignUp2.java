package com.example.scoops;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class SignUp2 extends AppCompatActivity {

    //Registration
    private EditText Fname, Lname, Email, Password, Confirm;
    private String s_Fname, s_Lname, s_Email, s_Password;
    private CheckBox peek, peek2;
    private ImageButton register;
    private ProgressBar loading;
    private static String URL_REG = "https://lamp.ms.wits.ac.za/~s1445435/register.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up2);

        register = findViewById(R.id.btnRegister);

        //Registration
        loading = findViewById(R.id.loading);
        Fname = findViewById(R.id.txtFirst);
        Lname = findViewById(R.id.txtLast);
        Email = findViewById(R.id.txtMail);
        Password = findViewById(R.id.txtCreate);
        Confirm = findViewById(R.id.txtConfirm);
        peek = findViewById(R.id.peek);
        peek2 = findViewById(R.id.peek2);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Registration();
                //openApp();
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
    private void Registration(){
        loading.setVisibility(View.VISIBLE);
        register.setVisibility(View.GONE);

        //Disallow Continuation when text_box empty
        if (Fname.getText().toString().equals("")){
            Toast.makeText(this, "Please Enter Your First Name", Toast.LENGTH_SHORT).show();
        }
        else if (Lname.getText().toString().equals("")){
            Toast.makeText(this, "Please Enter Your Last Name", Toast.LENGTH_SHORT).show();
        }
        else if(Email.getText().toString().equals("")){
            Toast.makeText(this, "Please Enter Your Email Address", Toast.LENGTH_SHORT).show();
        }
        else if(Password.getText().toString().equals("")){
            Toast.makeText(this, "Please Enter Your Password", Toast.LENGTH_SHORT).show();
        }
        else if(Confirm.getText().toString().equals("")){
            Toast.makeText(SignUp2.this, "Please Confirm Your Password", Toast.LENGTH_SHORT).show();
        }
        else{
            s_Fname = this.Fname.getText().toString().trim();
            s_Lname = this.Lname.getText().toString().trim();
            s_Email = this.Email.getText().toString().trim();
            s_Password = this.Password.getText().toString().trim();
            //s_Confirm = this.confirm.getText().toString().trim();
        }
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REG, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(SignUp2.this, response, Toast.LENGTH_SHORT).show();
                loading.setVisibility(View.GONE);
                register.setVisibility(View.VISIBLE);

            }

        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SignUp2.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        loading.setVisibility(View.GONE);
                        register.setVisibility(View.VISIBLE);
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Fname", s_Fname);
                params.put("Lname", s_Lname);
                params.put("Email", s_Email);
                params.put("Password", s_Password);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}



