package com.example.scoops;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
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

import static com.example.scoops.AcceptedInfo.InfoPhone;
import static com.example.scoops.AcceptedInfo.f_email;
import static com.example.scoops.AcceptedInfo.f_location;
import static com.example.scoops.AcceptedInfo.f_name;

public class RequestAccepted extends AppCompatActivity {


    private LinearLayout finishDel, viewDetails;
    private TextView a_name, a_location, a_items;
    private static String TAG_T = RequestAccepted.class.getSimpleName();
    public static String a_email, a_phone, s_name, s_items, emailV;
    private static String URL_READ_T = "https://lamp.ms.wits.ac.za/~s1445435/readInfoT.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_post_taken);

        displayDetails();

        //Hide status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        a_name = findViewById(R.id.user_name_t);
        a_location = findViewById(R.id.location_t);
        a_items = findViewById(R.id.f_items);
        finishDel = findViewById(R.id.btnfinishDel);
        viewDetails = findViewById(R.id.btnViewDetails_T);

        a_items.setText(s_items);

        finishDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AcceptedInfo.class));
            }
        });

        viewDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AcceptedInfo.class));
            }
        });



    }

    private void displayDetails(){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_READ_T, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i(TAG_T, response.toString());

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("read");

                    if (success.equals("1")) {
                        for (int i = 0; i < jsonArray.length(); ++i) {
                            JSONObject object = jsonArray.getJSONObject(i);

                            s_name = object.getString("Name").trim();
                            a_phone = object.getString("Phone").trim();
                            String s_location = object.getString("Location").trim();

                            a_name.setText(s_name);
                            a_location.setText(s_location);


                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(RequestAccepted.this, "Error Reading", Toast.LENGTH_SHORT).show();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(RequestAccepted.this, "Error Reading", Toast.LENGTH_SHORT).show();
                    }
                })
        {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", a_email);
                params.put("emailV", emailV);
                f_email = a_email;
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(RequestAccepted.this, "Please complete your delivery first!", Toast.LENGTH_SHORT).show();
    }
}