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

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;

import static com.example.scoops.Login.Vemail;
import static com.example.scoops.RequestAccepted.a_email;
import static com.example.scoops.RequestAccepted.a_phone;
import static com.example.scoops.RequestAccepted.s_name;

public class AcceptedInfo extends AppCompatActivity {

    private TextView moreInfoName,  moreInfoLocation, moreInfoItems;
    public static TextView InfoEmail, InfoPhone;
    private Button finished;
    private ProgressBar loading;
    public static String f_name, f_location, f_email, f_phone;
    String name, location, email, phone;
    private static String URL_READ_A = "https://lamp.ms.wits.ac.za/~s1445435/readInfoA.php";
    private static String URL_FINISH = "https://lamp.ms.wits.ac.za/~s1445435/finish.php";
    private static String TAG_A = AcceptedInfo.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accepted_info);
        displayDetail();

        //Hide status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        finished = findViewById(R.id.btnfinishedDel);
        moreInfoName = findViewById(R.id.txtAname);
        moreInfoLocation = findViewById(R.id.txtAlocation);
        InfoPhone = findViewById(R.id.txtAphone);
        InfoEmail = findViewById(R.id.txtAemail);
        moreInfoItems = findViewById(R.id.txtAitemsList);
        loading = findViewById(R.id.loading2);

        //COMPLETE DELIVERY
        finished.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishDelivery();
;           }
        });

    }

    private void displayDetail(){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_READ_A, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i(TAG_A, response.toString());

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("read");

                    if (success.equals("1")) {
                        for (int i = 0; i < jsonArray.length(); ++i) {
                            JSONObject object = jsonArray.getJSONObject(i);

                            String s_email = object.getString("EMAIL").trim();
                            String s_item = object.getString("ITEMS").trim();
                            String s_location = object.getString("LOCATION").trim();

                            moreInfoName.setText(s_name);
                            moreInfoLocation.setText(s_location);
                            InfoEmail.setText(s_email);
                            moreInfoItems.setText(s_item);
                            InfoPhone.setText(a_phone);

                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(AcceptedInfo.this, "Error Reading", Toast.LENGTH_SHORT).show();
                }
            }//String s_name = object.getString("NAME").trim();
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AcceptedInfo.this, "Error Reading", Toast.LENGTH_SHORT).show();
                    }
                })
        {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", f_email);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void finishDelivery(){

        //loading.setVisibility(View.VISIBLE);
        //finished.setVisibility(View.GONE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_FINISH, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //loading.setVisibility(View.GONE);
                //finished.setVisibility(View.VISIBLE);

                if (response.contains("Delivery Completed")) {
                    Toast.makeText(AcceptedInfo.this, response, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), RequestFeed.class));
                    finish();

                }
                else {
                    Toast.makeText(AcceptedInfo.this, response, Toast.LENGTH_SHORT).show();

                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AcceptedInfo.this, "Something Went Wrong!", Toast.LENGTH_SHORT).show();
                        //loading.setVisibility(View.GONE);
                        //finished.setVisibility(View.VISIBLE);
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", f_email);
                params.put("Vemail", Vemail);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}