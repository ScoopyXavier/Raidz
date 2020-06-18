package com.example.scoops;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
    //LOGIN
    private EditText Password, Email;
    private ImageButton Login;
    private ProgressBar loading;
    private String URL_LOGIN = "https://lamp.ms.wits.ac.za/~s1445435/login2.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //LOGIN
        Password = findViewById(R.id.txtPassword);
        Email = findViewById(R.id.txtMail2);
        Login = findViewById(R.id.btnLogin2);
        loading = findViewById(R.id.loading);

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String s_email = Email.getText().toString().trim();
                String s_password = Password.getText().toString().trim();

                if (!s_email.isEmpty() && !s_password.isEmpty()){
                    Login(s_email, s_password);
                }
                else {
                    if (s_email.isEmpty()) {
                        Email.setError("Please Enter Your Email Address");
                    }
                    if (s_password.isEmpty()) {
                        Password.setError("Please Enter Your Password");
                    }
                }

            }
        });

    }

    //LOGIN
    private void Login(final String email, final String password){
        loading.setVisibility(View.VISIBLE);
        Login.setVisibility(View.GONE);

        final String Email = this.Email.getText().toString().trim();
        final String Password = this.Password.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.contains("1")) {
                    startActivity(new Intent(getApplicationContext(), VolunteerProfile.class));
                } else {
                    Toast.makeText(getApplicationContext(), "Wrong Username or Password!", Toast.LENGTH_SHORT).show();
                }
                loading.setVisibility(View.GONE);
                Login.setVisibility(View.VISIBLE);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.setVisibility(View.GONE);
                        Login.setVisibility(View.VISIBLE);
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("Email", Email);
                params.put("Password", Password);

                return params;
            }
        };

        //Alternative Garbage
        /*StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.setVisibility(View.GONE);
                Login.setVisibility(View.VISIBLE);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("Login");

                    if (success.equals("1")) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);

                            String Fname = object.getString("Fname").trim();
                            String Lname = object.getString("Lname").trim();

                            Toast.makeText(Login.this, "Hello! " + Fname + " " + Lname, Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(Login.this, "Error " + e.toString(), Toast.LENGTH_SHORT).show();
                    loading.setVisibility(View.GONE);
                    Login.setVisibility(View.VISIBLE);
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Login.this, "Error " + error.toString(), Toast.LENGTH_SHORT).show();
                        loading.setVisibility(View.GONE);
                        Login.setVisibility(View.VISIBLE);
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("Email", Email);
                params.put("Password", Password);

                return params;
            }
        };*/

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
