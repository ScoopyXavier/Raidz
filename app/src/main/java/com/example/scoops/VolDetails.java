package com.example.scoops;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
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

import static com.example.scoops.AcceptedInfo.f_email;

public class VolDetails extends AppCompatActivity {

    private TextView name, email, phone;
    public static String v_email;
    private static String URL_WHOTOOKIT = "https://lamp.ms.wits.ac.za/~s1445435/volDetails.php";
    private static String TAG_VT = AcceptedInfo.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vol_details);

        isTaken();

        //Hide status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        name = findViewById(R.id.volName);
        email = findViewById(R.id.volEmail);
        phone = findViewById(R.id.volPhone);

    }


    private void isTaken(){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_WHOTOOKIT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i(TAG_VT, response.toString());

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("read");

                    if (success.equals("1")) {
                        for (int i = 0; i < jsonArray.length(); ++i) {
                            JSONObject object = jsonArray.getJSONObject(i);

                            String vol_name = object.getString("Name").trim();
                            String vol_phone = object.getString("Phone").trim();
                            String vol_email = object.getString("Email").trim();

                            name.setText(vol_name);
                            email.setText(vol_email);
                            phone.setText(vol_phone);
                        }
                    }
                    else{
                        startActivity(new Intent(getApplicationContext(), RequestActivity.class));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(VolDetails.this, "Delivery already completed", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), RequestActivity.class));
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(VolDetails.this, "Delivery already completed", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), RequestActivity.class));
                    }
                })
        {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", v_email);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}