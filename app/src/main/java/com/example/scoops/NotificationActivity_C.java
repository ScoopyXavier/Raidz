package com.example.scoops;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotificationActivity_C extends AppCompatActivity {


    private TextView accepted, notAccepted, noNot;
    private ImageView bubble;
    public static String c_email;
    public static Button checkStatus;
    private ProgressBar loading;
    private static String URL_TAKEN = "https://lamp.ms.wits.ac.za/~s1445435/taken.php";
    private static String NOTIFICATION = "SOMETHING";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification__c);

        //NOTIFICATION CHANNEL
        createNotificationChannel();

        //Hide status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        checkStatus = findViewById(R.id.check);
        bubble = findViewById(R.id.bubble);
        accepted = findViewById(R.id.accepted);
        notAccepted = findViewById(R.id.notAccepted);
        loading = findViewById(R.id.loadingA);
        noNot = findViewById(R.id.noNot);


        checkStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifications();
            }
        });
    }

    public void notifications(){
        createNotificationChannel();

        //ENABLE PUSH NOTIFICATION TO START ACTIVITY
        Intent intent = new Intent(this, NotificationActivity_C.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        //SEND PUSH NOTIFICATION
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "CHANNEL_ID")
                .setSmallIcon(R.drawable.ss)
                .setContentTitle("Surrogate Shopper Update")
                .setContentText("Your request has been accepted a volunteer is on their way")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        Intent intent2 = new Intent(this, NotificationActivity_C.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent2 = PendingIntent.getActivity(this, 0, intent2, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder2 = new NotificationCompat.Builder(this, "CHANNEL_ID")
                .setSmallIcon(R.drawable.ss)
                .setContentTitle("Surrogate Shopper Update")
                .setContentText("Your request has not yet been accept, thank you for your patience")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent2)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_TAKEN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
               bubble.setVisibility(View.GONE);
                accepted.setVisibility(View.GONE);
                notAccepted.setVisibility(View.GONE);
                noNot.setVisibility(View.GONE);
                loading.setVisibility(View.VISIBLE);


                if (response.contains("Taken")) {
                    //Toast.makeText(NotificationActivity_C.this, "Your Request has been accepted a volunteer is on their way", Toast.LENGTH_SHORT).show();
                    notificationManagerCompat.notify(100, builder.build());
                    bubble.setVisibility(View.VISIBLE);
                    accepted.setVisibility(View.VISIBLE);
                    loading.setVisibility(View.GONE);

                }

                else {
                    notificationManagerCompat.notify(200, builder2.build());
                    //Toast.makeText(NotificationActivity_C.this, "Your Request has not yet been accepted", Toast.LENGTH_SHORT).show();
                   bubble.setVisibility(View.VISIBLE);
                    notAccepted.setVisibility(View.VISIBLE);
                    loading.setVisibility(View.GONE);
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(NotificationActivity_C.this, "Your Request has not yet been accepted", Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Email", c_email);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Client Channel"; //getString(R.string.channel_name);
            String description = "Channel for notification"; //getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("CHANNEL_ID", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public static void removeCheck(){
        checkStatus.setVisibility(View.GONE);
    }
}