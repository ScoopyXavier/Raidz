package com.example.scoops;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;

@RequiresApi(api = Build.VERSION_CODES.M)
public class RequestFeed extends AppCompatActivity implements RecyclerView.OnScrollChangeListener {
    //Creating a List of Requests
    private ArrayList<RequestV> listRequestV;

    //Creating Views
    private BottomNavigationView bottomNavigation;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    String webURL = "https://lamp.ms.wits.ac.za/home/s1445435/vol.php?page=";

    //Volley Request Queue
    private RequestQueue requestQueue;

    //The request counter to send ?page=1, ?page=2  requests
    private int requestCount = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_feed);

        //Hide status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        //Initializing Views
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //Initializing our Requests list
        listRequestV = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(this);

        //Calling method to get data to fetch data
        getData();

        //Adding an scroll change listener to recyclerview
        recyclerView.setOnScrollChangeListener(this);

        //initializing our adapter
        adapter = new RequestCardAdapter(listRequestV, this);

        //Adding adapter to recyclerview
        recyclerView.setAdapter(adapter);

        //SET BOTTOM NAVIGATION
        bottomNavigation = findViewById(R.id.bottom_navigation_vv);

        bottomNavigation.inflateMenu(R.menu.bottom_navigation_v);
        bottomNavigation.setItemBackgroundResource(R.color.colorPrimaryDark);
        bottomNavigation.setItemTextColor(ContextCompat.getColorStateList(bottomNavigation.getContext(), R.color.nav_item_colors));
        bottomNavigation.setItemIconTintList(ContextCompat.getColorStateList(bottomNavigation.getContext(), R.color.nav_item_colors));

        BottomNavigationView requestView = findViewById(R.id.bottom_navigation_vv);
        requestView.setSelectedItemId(R.id.nb_browse);
        requestView.getMenu().findItem(R.id.nb_browse).setChecked(true);

        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nb_browse:
                        startActivity(new Intent(RequestFeed.this, RequestFeed.class));
                        break;

                    case R.id.nb_home:
                        startActivity(new Intent(RequestFeed.this, VolunteerProfile.class));
                        break;

                    case R.id.nb_notifications:
                        startActivity(new Intent(RequestFeed.this, NotificationActivity.class));;
                        break;
                }
                return true;
            }
        });


    }

    //Request to get json from server we are passing an integer here
    //This integer will used to specify the page number for the request ?page = requestcount
    //This method would return a JsonArrayRequest that will be added to the request queue
    private JsonArrayRequest getDataFromServer(int requestCount) {
        //Initializing ProgressBar
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar1);

        //Displaying Progressbar
        progressBar.setVisibility(View.VISIBLE);
        setProgressBarIndeterminateVisibility(true);

        //JsonArrayRequest of volley
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(webURL + String.valueOf(requestCount),
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //Calling method parseData to parse the json response
                        parseData(response);

                        //Hiding the progressbar
                        progressBar.setVisibility(View.GONE);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);
                        //If an error occurs that means end of the list has reached
                        //Toast.makeText(RequestFeed.this, "No More Items Available", Toast.LENGTH_SHORT).show();
                    }
                });

        //Returning the request
        return jsonArrayRequest;
    }
    //This method will get data from the web api
    private void getData() {
        //Adding the method to the queue by calling the method getDataFromServer
        requestQueue.add(getDataFromServer(requestCount));

        //Incrementing the request counter
        requestCount++;
    }

    //This method will parse json data
    private void parseData(JSONArray array) {
        for (int i = 0; i < array.length(); i++) {
            //Creating the Request object
            RequestV requestV = new RequestV();
            JSONObject json = null;
            try {
                //Getting json
                json = array.getJSONObject(i);

                //Adding data to the request object
                requestV.setName(json.getString("name"));
                requestV.setEmail(json.getString("email"));
                requestV.setLocation(json.getString("location"));
                requestV.setPhone(json.getString("phone"));
                requestV.setDate(json.getString("date"));
                requestV.setItem(json.getString("items"));

            } catch (JSONException e) {
                e.printStackTrace();
            }
            //Adding the request object to the list
            listRequestV.add(requestV);
        }

        //Notifying the adapter that data has been added or changed
        adapter.notifyDataSetChanged();
    }

    //This method would check that the recyclerview scroll has reached the bottom or not
    private boolean isLastItemDisplaying(RecyclerView recyclerView) {
        if (recyclerView.getAdapter().getItemCount() != 0) {
            int lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();

            if (lastVisibleItemPosition != RecyclerView.NO_POSITION && lastVisibleItemPosition == recyclerView.getAdapter().getItemCount() - 1)
                return true;
        }
        return false;
    }

    @Override
    public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        //ifScrolled at last then
        if (isLastItemDisplaying(recyclerView)) {

            //Calling the method getData again
            getData();
        }
    }
}