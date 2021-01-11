package com.example.scoops;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.esafirm.imagepicker.features.ImagePicker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.example.scoops.Login.ss_email;
import static com.example.scoops.Login.ss_name;

public class EditProfileC extends AppCompatActivity {

    SessionManager_C sessionManager;
    private EditText Name, Email, Phone, Address, City;
    private String ss_name, ss_email, ss_phone, ss_address, ss_city;
    private TextView picture;
    private ImageButton save;
    private String getId_C;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private ProgressBar loading;
    private static String URL_READ_C = "https://lamp.ms.wits.ac.za/~s1445435/readInfoC.php";
    private static String URL_EDIT_C = "https://lamp.ms.wits.ac.za/~s1445435/editInfoC.php";
    private static String TAG_C = EditProfileC.class.getSimpleName();
    //int imageUploadType = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_c);

        sessionManager = new SessionManager_C(this);

        //Hide status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        Name = findViewById(R.id.txtNameEC);
        Email = findViewById(R.id.txtMailEC);
        Phone = findViewById(R.id.txtPhoneEC);
        Address = findViewById(R.id.txtAddressEC);
        City = findViewById(R.id.txtCityEC);
        save = findViewById(R.id.btnSaveC);
        picture = findViewById(R.id.txtEditPPC);
        loading = findViewById(R.id.loadingC);


        HashMap<String, String> user = sessionManager.getUserDetailC();
        getId_C = user.get(sessionManager.ID_C);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveEditDetailC();
            }
        });

        //UPLOAD PROFILE PICTURE;
        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        /*
        //Radio buttons
        radioGroup = findViewById(R.id.radioGroup);
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);
        String gender = radioButton.getText().toString().trim();*/
    }


    private void checkedButton(View v){

    }

    //GET DETAILS FROM USER
    private void getUserDetailsC(){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_READ_C, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i(TAG_C, response.toString());

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("read");

                    if (success.equals("1")){
                        for (int i = 0; i < jsonArray.length(); ++i){
                            JSONObject object = jsonArray.getJSONObject(i);

                            String s_email = object.getString("Email").trim();
                            String s_name = object.getString("Name").trim();
                            String s_phone = object.getString("Phone").trim();
                            String s_address = object.getString("Address").trim();
                            String s_city = object.getString("City").trim();

                            Email.setText(s_email);
                            Name.setText(s_name);

                            if (s_phone == "null"){Phone.setText("");}
                            else{Phone.setText(s_phone);}

                            if (s_address == "null"){Address.setText("");}
                            else{Address.setText(s_address);}

                            if (s_city == "null"){City.setText("");}
                            else{City.setText(s_city);}

                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(EditProfileC.this, "Error Reading", Toast.LENGTH_SHORT).show();
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(EditProfileC.this, "Error reading" , Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("ID", getId_C);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getUserDetailsC();

    }

    //SAVE THE DETAILS
    private void saveEditDetailC(){
        ss_email = Email.getText().toString().trim();
        ss_name = Name.getText().toString().trim();
        ss_phone = Phone.getText().toString().trim();
        ss_address = Address.getText().toString().trim();
        ss_city = City.getText().toString().trim();
        final String ID_C = getId_C;

        save.setVisibility(View.GONE);
        loading.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_EDIT_C, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");

                    if (success.equals("1")){
                        Toast.makeText(EditProfileC.this, "Profile Updated", Toast.LENGTH_SHORT).show();
                        sessionManager.createSessionC(ss_name, ss_email, ID_C);
                        save.setVisibility(View.VISIBLE);
                        loading.setVisibility(View.GONE);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(EditProfileC.this, "Error Updating Information", Toast.LENGTH_SHORT).show();
                    save.setVisibility(View.VISIBLE);
                    loading.setVisibility(View.GONE);
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(EditProfileC.this, "Error Updating Information", Toast.LENGTH_SHORT).show();

                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Name", ss_name);
                params.put("Email", ss_email);
                params.put("Phone", ss_phone);
                params.put("Address", ss_address);
                params.put("City", ss_city);
                params.put("ID", ID_C);

                return params;
            }
        };

        Name.setText(ss_name);
        Email.setText(ss_email);
        Phone.setText(ss_phone);
        Address.setText(ss_address);
        City.setText(ss_city);

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}