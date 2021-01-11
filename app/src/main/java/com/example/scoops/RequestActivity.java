package com.example.scoops;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.scoops.fragment.HistoryFragment;
import com.example.scoops.fragment.HomeFragment;
import com.example.scoops.fragment.NotificationFragment;
import com.example.scoops.fragment.RequestFragment;
import com.example.scoops.fragment.VolunteersFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.scoops.AcceptedInfo.f_email;
import static com.example.scoops.RequestAccepted.a_phone;
import static com.example.scoops.RequestAccepted.s_name;

public class RequestActivity extends AppCompatActivity {


    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigation;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private FloatingActionButton post;
    private View home;
    private TextView textItems, date, name, location;
    public static String sum_email, sum_name;
    private static String URL_SUMMARY = "https://lamp.ms.wits.ac.za/~s1445435/summary.php";
    private static String TAG_S = AcceptedInfo.class.getSimpleName();

    //HistoryFragment historyFrag;
    RequestFragment requestFragment;
    HomeFragment homeFragment;
    //VolunteersFragment volunteersFragment;
   // NotificationFragment notificationFragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);
        ButterKnife.bind(this);

        requestSummary();

        AlarmHandler alarmHandler = new AlarmHandler(this);
        //alarmHandler.cancelAlarmManager();
        alarmHandler.setAlarmManager();

        //Hide status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        home = findViewById(R.id.nb_home);
        textItems = findViewById(R.id.itemsSummary);
        name = findViewById(R.id.nameSummary);
        date = findViewById(R.id.dateSummary);
        location = findViewById(R.id.locationSummary);

        name.setText(sum_name);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //SET BOTTOM NAVIGATION
        bottomNavigation.inflateMenu(R.menu.bottom_navigation_main);
        bottomNavigation.setItemBackgroundResource(R.color.colorPrimaryDark);
        bottomNavigation.setItemTextColor(ContextCompat.getColorStateList(bottomNavigation.getContext(), R.color.nav_item_colors));
        bottomNavigation.setItemIconTintList(ContextCompat.getColorStateList(bottomNavigation.getContext(), R.color.nav_item_colors));

        //historyFrag = new HistoryFragment();
        requestFragment = new RequestFragment();
        homeFragment = new HomeFragment();
        //volunteersFragment = new VolunteersFragment();
        //notificationFragment = new NotificationFragment();

        setFragment(homeFragment);
        BottomNavigationView requestView = findViewById(R.id.bottom_navigation);
        requestView.setSelectedItemId(R.id.nb_request);
        requestView.getMenu().findItem(R.id.nb_request).setChecked(true);

        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nb_request:
                        setFragment(requestFragment);
                        break;

                    case R.id.nb_home:
                        startActivity(new Intent(RequestActivity.this, ClientProfile.class));
                        break;

                    case R.id.nb_notifications:
                        startActivity(new Intent(RequestActivity.this, NotificationActivity_C.class));
                        break;
                }
                return true;
            }
        });

    }

    public void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        //fragmentTransaction.replace(R.id.framelayout, fragment);
        fragmentTransaction.commit();
    }

    private void requestSummary(){


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_SUMMARY, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i(TAG_S, response.toString());

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("read");

                    if (success.equals("1")) {
                        for (int i = 0; i < jsonArray.length(); ++i) {
                            JSONObject object = jsonArray.getJSONObject(i);

                            String s_date = object.getString("UPLOAD_TIME").trim();
                            String s_item = object.getString("ITEMS").trim();
                            String s_location = object.getString("LOCATION").trim();

                            date.setText(s_date);
                            location.setText(s_location);
                            textItems.setText(s_item);


                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(RequestActivity.this, "Error Reading", Toast.LENGTH_SHORT).show();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(RequestActivity.this, "Error Reading", Toast.LENGTH_SHORT).show();
                    }
                })
        {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", sum_email);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
    @Override
    public void onBackPressed() {
        Toast.makeText(RequestActivity.this, "Your request is in session!", Toast.LENGTH_SHORT).show();
    }




}