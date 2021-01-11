package com.example.scoops;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.example.scoops.RequestAccepted.a_email;
import static com.example.scoops.RequestAccepted.s_items;


public class RequestInfo extends AppCompatActivity {
    public TextView moreInfoName;
    public TextView moreInfoLocation;
    public TextView moreInfoEmail;
    public TextView moreInfoItems, moreInfoPhone;
    public Button acceptRequest;
    private ProgressBar loading;
    SessionManager_C sessionManager;
    String name;
    String location;
    String email, phone;
    public static String f_name, f_location, f_email, f_phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_details);
        getIntent();

        sessionManager = new SessionManager_C(this);

        //Hide status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        moreInfoName = findViewById(R.id.txtRname);
        moreInfoLocation = findViewById(R.id.txtRlocation);
        moreInfoPhone = findViewById(R.id.txtRphone);
        moreInfoEmail = findViewById(R.id.txtRemail);
        moreInfoItems = findViewById(R.id.txtRitemsList);
        acceptRequest = findViewById(R.id.btnAccept);
        name = getIntent().getStringExtra("name");
        phone = getIntent().getStringExtra("phone");
        location = getIntent().getStringExtra("location");
        email = getIntent().getStringExtra("email");
        loading = findViewById(R.id.loading);
        String infoURL = "https://lamp.ms.wits.ac.za/home/s1445435/more.php?email=" + email;
        final String acceptURL = "https://lamp.ms.wits.ac.za/home/s1445435/accept.php?email=" + email;


        moreInfoName.setText(name);
        moreInfoLocation.setText(location);
        moreInfoEmail.setText(email);
        moreInfoPhone.setText(phone);

        a_email = email;

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(infoURL).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                // ... check for failure using `isSuccessful` before proceeding

                // Read data on the worker thread
                final String responseData = response.body().string();

                // Run view-related code back on the main thread
                RequestInfo.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            processJson(responseData);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
            }
        });

        acceptRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acceptRequest.setEnabled(false);
                acceptRequest.setVisibility(View.GONE);
                loading.setVisibility(View.VISIBLE);


                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url(acceptURL).build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {
                        // ... check for failure using `isSuccessful` before proceeding

                        // Read data on the worker thread
                        final String responseData = response.body().string();

                        // Run view-related code back on the main thread
                        RequestInfo.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                Toast.makeText(RequestInfo.this, "Accepted", Toast.LENGTH_SHORT).show();
                                loading.setVisibility(View.GONE);
                                acceptRequest.setVisibility(View.GONE);

                                startActivity(new Intent(getApplicationContext(), RequestAccepted.class));

                                killActivity();
                            }
                        });
                    }
                });
            }
        });
    }

    public void processJson(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);
        JSONObject jsonObject = jsonArray.getJSONObject(0);
        String location = jsonObject.getString("LOCATION");
        String items = jsonObject.getString("ITEMS");
        //String phone = jsonObject.getString("phone");
        moreInfoItems.setText(items);
        moreInfoLocation.setText(location);
        s_items = items;
    }

    private void killActivity(){
        finish();
    }
}