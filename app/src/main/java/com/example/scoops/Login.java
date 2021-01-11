package com.example.scoops;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.view.WindowManager;
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

import static com.example.scoops.VolDetails.v_email;
import static com.example.scoops.BackgroundRun.b_email;
import static com.example.scoops.RequestAccepted.emailV;
import static com.example.scoops.RequestActivity.sum_email;
import static com.example.scoops.RequestActivity.sum_name;
import static com.example.scoops.SessionManager.EMAIL;
import static com.example.scoops.NotificationActivity_C.c_email;

public class Login extends AppCompatActivity {
    //LOGIN
    public static String ss_name, ss_email, r_name, r_email, r_phone, r_id;
    //public static int r_id;
    private EditText Password, Email;
    private ImageButton login;
    private ProgressBar loading;
    public static String Vemail;
    SessionManager sessionManager;
    SessionManager_C sessionManager_c;
    private static String URL_LOGIN = "https://lamp.ms.wits.ac.za/~s1445435/loginF.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sessionManager = new SessionManager(this);
        sessionManager_c = new SessionManager_C(this);

        //Hide status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        //LOGIN
        Password = findViewById(R.id.txtPassword);
        Email = findViewById(R.id.txtMail4);
        login = findViewById(R.id.btnLogin2);
        loading = findViewById(R.id.loading);
        TextView registerL =  findViewById(R.id.txtSignUpL);

        login.setOnClickListener(new View.OnClickListener() {
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

        //GO TO SIGN UP (NO ACCOUNT)
        registerL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SignUp.class));
            }
        });

        SpannableString content = new SpannableString("Register Account");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        registerL.setText(content);

        /*if (EMAIL != null || !EMAIL.equals("")){
            Intent intent = new Intent(Login.this, VolunteerProfile.class);
            startActivity(intent);
            killActivity();
        }

        if (EMAIL_C != null || !EMAIL_C.equals("")){
            Intent intent = new Intent(Login.this, ClientProfile.class);
            startActivity(intent);
            killActivity();
        }*/

    }

    //LOGIN
    private void Login(final String email, final String password){
        loading.setVisibility(View.VISIBLE);
        login.setVisibility(View.GONE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("login");

                            if (success.equals("1")) {
                                for (int i = 0; i < jsonArray.length(); ++i) {
                                    JSONObject object = jsonArray.getJSONObject(i);

                                    String name = object.getString("Name").trim();
                                    String ID = object.getString("ID").trim();
                                    Vemail = object.getString("Email").trim();

                                    emailV = Vemail;

                                    sessionManager.createSession(name, Vemail, ID);

                                    Toast.makeText(Login.this, "Hello " + name + "!", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Login.this, VolunteerProfile.class);
                                    intent.putExtra("Name",  name);
                                    intent.putExtra("Email", email);
                                    startActivity(intent);
                                    killActivity();


                                    loading.setVisibility(View.GONE);

                                    //startActivity(new Intent(getApplicationContext(), VolunteerProfile.class));



                                }

                            }
                            else if (success.equals("2")){
                                for (int i = 0; i < jsonArray.length(); ++i) {
                                    JSONObject object = jsonArray.getJSONObject(i);

                                    String name = object.getString("Name").trim();
                                    String ID = object.getString("ID").trim();
                                    String email = object.getString("Email").trim();


                                    sessionManager_c.createSessionC(name, email, ID);

                                    Toast.makeText(Login.this, "Hello " + name + "!", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Login.this, ClientProfile.class);
                                    intent.putExtra("Name",  name);
                                    intent.putExtra("Email", email);
                                    startActivity(intent);
                                    killActivity();

                                    r_name = name; r_email = email; r_id = ID; c_email = email; sum_email = email; sum_name = name; b_email = email; v_email = email;

                                    loading.setVisibility(View.GONE);

                                    //startActivity(new Intent(getApplicationContext(), ClientProfile.class));


                                }

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(Login.this, "Wrong Username or Password!" , Toast.LENGTH_SHORT).show();
                            loading.setVisibility(View.GONE);
                            login.setVisibility(View.VISIBLE);
                        }
                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(Login.this, "Wrong Username or Password!" , Toast.LENGTH_SHORT).show();
                                loading.setVisibility(View.GONE);
                                login.setVisibility(View.VISIBLE);
                            }
                        })
                {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();

                        params.put("Email", email);
                        params.put("Password", password);

                        return params;
                    }
                };


                RequestQueue requestQueue = Volley.newRequestQueue(this);
                requestQueue.add(stringRequest);
    }
    private void killActivity() {
        finish();
    }
}
