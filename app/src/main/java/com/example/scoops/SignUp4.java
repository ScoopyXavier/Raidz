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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignUp4 extends AppCompatActivity {

    //Registration
    private EditText Fname, Lname, phone, address, city, email, DOB, password, confirm ;
    private ImageButton register;
    private ProgressBar loading;
    private static String URL_REG = "http://192.168.43.243/register.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up4);

        register = findViewById(R.id.btnRegister);

        //Registration
        loading = findViewById(R.id.loading);
        Fname = findViewById(R.id.txtFirst);
        Lname = findViewById(R.id.txtLast);
        phone = findViewById(R.id.txtPhone);
        address = findViewById(R.id.txtAddress);
        city = findViewById(R.id.txtCity);
        email = findViewById(R.id.txtMail);
        DOB = findViewById(R.id.txtDate);
        password = findViewById(R.id.txtCreate);
        confirm = findViewById(R.id.txtConfirm);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //openLogin();
                Registration();
            }
        });

    }

    public void openLogin(){
        Intent intent = new Intent(this,Login.class);
        startActivity(intent);
    }

    //Registration
    private void Registration(){
        loading.setVisibility(View.VISIBLE);
        register.setVisibility(View.GONE);

        final String Fname = this.Fname.getText().toString().trim();
        final String Lname = this.Lname.getText().toString().trim();
        final String phone = this.phone.getText().toString().trim();
        final String DOB = this.DOB.getText().toString().trim();
        final String address = this.address.getText().toString().trim();
        final String city = this.city.getText().toString().trim();
        final String email = this.email.getText().toString().trim();
        final String password = this.password.getText().toString().trim();
        //final String confirm = this.confirm.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REG, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            try{
                JSONObject jsonObject = new JSONObject(response);
                String Success = jsonObject.getString("Success");

                if (Success.equals("1")){
                    Toast.makeText(SignUp4.this, "Registration Successful!", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(SignUp4.this, "Registration Error!" + e.toString(),  Toast.LENGTH_SHORT).show();
                loading.setVisibility(View.GONE);
                register.setVisibility(View.VISIBLE);
            }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SignUp4.this, "Registration Error!" + error.toString(),  Toast.LENGTH_SHORT).show();
                        loading.setVisibility(View.GONE);
                        register.setVisibility(View.VISIBLE);
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Fname", Fname);
                params.put("Lname", Lname);
                params.put("address", address);
                params.put("city", city);
                params.put("phone", phone);
                params.put("email", email);
                params.put("password", password);
                params.put("DOB", DOB);

                //return super.getParams();

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
