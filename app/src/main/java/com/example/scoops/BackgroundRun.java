package com.example.scoops;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.scoops.model.FriendsModel;

import java.util.HashMap;
import java.util.Map;

public class BackgroundRun extends BroadcastReceiver {

    private static String URL_TAKEN = "https://lamp.ms.wits.ac.za/~s1445435/taken.php";
    public static String b_email;

    @Override
    public void onReceive(Context context, Intent intent) {
        createNotificationChannel();

        //ENABLE PUSH NOTIFICATION TO START ACTIVITY
        Intent intent2 = new Intent(context, VolDetails.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent2, PendingIntent.FLAG_UPDATE_CURRENT);

        //SEND PUSH NOTIFICATION
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "CHANNEL_ID")
                .setSmallIcon(R.drawable.ss)
                .setContentTitle("Surrogate Shopper Update")
                .setContentText("Your request has been accepted a volunteer is on their way")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);


        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_TAKEN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {



                if (response.contains("Taken")) {
                    //Toast.makeText(NotificationActivity_C.this, "Your Request has been accepted a volunteer is on their way", Toast.LENGTH_SHORT).show();
                    notificationManagerCompat.notify(200, builder.build());
                    //Intent intent3 = new Intent(String.valueOf(VolDetails.class));
                    //context.startActivity(intent3);

                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(context, "Your Request has not yet been accepted", Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Email", b_email);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    private void createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Client Channel"; //getString(R.string.channel_name);
            String description = "Channel for notification"; //getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("CHANNEL_ID", name, importance);
            channel.setDescription(description);

           // NotificationManager notificationManager = getSystemService(NotificationManager.class);
           // notificationManager.createNotificationChannel(channel);
        }
    }

}
