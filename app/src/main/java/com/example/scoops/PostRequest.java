package com.example.scoops;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import static com.example.scoops.Login.r_email;
import static com.example.scoops.Login.r_id;
import static com.example.scoops.Login.r_phone;
import static com.example.scoops.SessionManager_C.*;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.scoops.rest.ApiClient;
import com.example.scoops.rest.services.UserInterface;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;


public class PostRequest extends AppCompatActivity {

    @BindView(R.id.postBtnTxt)
    TextView postBtnTxt;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.request_edit)
    EditText requestEdit;

    private TextView post;
    public static String s_request;
    private EditText requestText;
    public static EditText location;
    private static String URL_REQUEST = "https://lamp.ms.wits.ac.za/~s1445435/post.php";

    //These are for the location services

    private static final int PERMISSIONS_FINE_LOCATION = 99;
    public static final int defaultInterval = 30;
    public static final int fastestInterval = 5;
    //API for the location services, majority of the activity will use this
    FusedLocationProviderClient fusedLocationProviderClient;

    //Location request is a config file for all setting related to the way the FusedLocationProviderCLient works
    LocationRequest locationRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_request);
        ButterKnife.bind(this);

        post = findViewById(R.id.postBtnTxt);
        requestText = findViewById(R.id.request_edit);
        location = findViewById(R.id.location_edit);

        //Hide status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        locationRequest = new LocationRequest();
        locationRequest.setInterval(1000 * defaultInterval);
        locationRequest.setFastestInterval(1000 * fastestInterval);

        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        updateGPS();

        //GO TO REQUEST ACTIVITY
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(getApplicationContext(), RequestActivity.class));
                uploadPost();
            }
        });

        //BACK BUTTON AT THE TOP
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.arrow_back_white);
        getSupportActionBar().setTitle("");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void uploadPost() {
        s_request = requestEdit.getText().toString().trim();
        String locations = location.getText().toString().trim();
        String ID = r_id;
        String Email = r_email;


        if (s_request.trim().length() > 0) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REQUEST, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    if (response.contains("Request Uploaded!")){
                        Toast.makeText(PostRequest.this, "Request Uploaded!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(PostRequest.this, RequestActivity.class);
                        startActivity(intent);

                    }
                    else{
                        Toast.makeText(PostRequest.this, response, Toast.LENGTH_SHORT).show();

                    }
                }

            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(PostRequest.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                        }
                    })
            {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();

                    params.put("ID", ID);
                    params.put("Email", Email);
                    params.put("Post", s_request);
                    params.put("Location", locations);
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }

        else {
            Toast.makeText(PostRequest.this, "Please enter the items to request", Toast.LENGTH_SHORT).show();
        }

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case PERMISSIONS_FINE_LOCATION:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    updateGPS();
                }
                else{
                    Toast.makeText(this, "This app requires permission to be granted in order to work properly", Toast.LENGTH_SHORT).show();
                    //finish();
                }
        }

    }
    private void updateGPS(){
        //get permissions from user to use GPS
        //get the location from the fused client
        // update the UI - ie set all properties in their associated text view items.

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            //user granted permission
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location gpslocation) {
                    //We got the permissions. Put the values of the location. XXX into UIU components
                    updateUIValues(gpslocation);
                }
            });
        }
        else{
            //permission not granted
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_FINE_LOCATION);
            }
        }
    }
    private void updateUIValues(Location gpslocation){
        // update all the text view objects to show the location

        Geocoder geocoder = new Geocoder(this);
        try{
            List<Address> addresses = geocoder.getFromLocation(gpslocation.getLatitude(),gpslocation.getLongitude(),1);
            location.setText(addresses.get(0).getAddressLine(0));
        }
        catch (Exception e){
            location.setText("Unable to find location");
        }

    }
}
