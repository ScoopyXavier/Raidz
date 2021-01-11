package com.example.scoops;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
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
import com.example.scoops.Login;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.namespace.NamespaceContext;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.scoops.Login.ss_email;
import static com.example.scoops.Login.ss_name;

public class EditProfile extends AppCompatActivity {

    SessionManager sessionManager;
    private EditText Name, Email, Phone, Address, City;
    private String ss_name, ss_email, ss_phone, ss_address, ss_city;
    private TextView editPhoto;
    CircleImageView profilePic;
    private ImageButton save;
    private String getId;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private ProgressBar loading;
    private Bitmap bitmap;
    private static String URL_READ = "https://lamp.ms.wits.ac.za/~s1445435/readInfo.php";
    private static String URL_EDIT = "https://lamp.ms.wits.ac.za/~s1445435/editInfo.php";
    private static String URL_UPLOAD = "https://lamp.ms.wits.ac.za/~s1445435/uploadV.php";
    private static String TAG = EditProfile.class.getSimpleName();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        sessionManager = new SessionManager(this);
        //Hide status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);



        Name = findViewById(R.id.txtNameE);
        Email = findViewById(R.id.txtMailE);
        Phone = findViewById(R.id.txtPhoneE);
        Address = findViewById(R.id.txtAddressE);
        City = findViewById(R.id.txtCityE);
        save = findViewById(R.id.btnSave);
        editPhoto = findViewById(R.id.txtEditPP);
        profilePic = findViewById(R.id.profilepic);

        HashMap<String, String> user = sessionManager.getUserDetail();
        getId = user.get(sessionManager.ID);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveEditDetail();
            }
        });

        /*
        //Radio buttons
        radioGroup = findViewById(R.id.radioGroup);
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);
        String gender = radioButton.getText().toString().trim();*/

        //PHOTO
        editPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //chooseFile();
            }
        });
    }


    private void checkedButton(View v){

    }

    //GET DETAILS FROM USER
    private void getUserDetails(){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_READ, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i(TAG, response.toString());

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
                    Toast.makeText(EditProfile.this, "Error Reading", Toast.LENGTH_SHORT).show();
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(EditProfile.this, "Error reading" , Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("ID", getId);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getUserDetails();
        //profilePic.setImageBitmap(bitmap);

    }

    //SAVE THE DETAILS
    private void saveEditDetail(){
        ss_email = Email.getText().toString().trim();
        ss_name = Name.getText().toString().trim();
        ss_phone = Phone.getText().toString().trim();
        ss_address = Address.getText().toString().trim();
        ss_city = City.getText().toString().trim();
        final String ID = getId;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_EDIT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");

                    if (success.equals("1")){
                        Toast.makeText(EditProfile.this, "Profile Updated", Toast.LENGTH_SHORT).show();
                        sessionManager.createSession(ss_name, ss_email, ID);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(EditProfile.this, "Error Updating Information", Toast.LENGTH_SHORT).show();
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(EditProfile.this, "Error Updating Information", Toast.LENGTH_SHORT).show();

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
                params.put("ID", ID);

                return params;
            }
        };

        Name.setText(ss_name);
        Email.setText(ss_email);
        Phone.setText(ss_phone);
        Address.setText(ss_address);
        City.setText(ss_city);
        profilePic.setImageBitmap(bitmap);

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

   /* private void chooseFile(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Choose Photo"),1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null){
            Uri filepath = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filepath);
                profilePic.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }

            UploadPicture(getId, getStringImage(bitmap));
        }
    }

    //UPLOAD PROFILE PICTURE
    private void UploadPicture(final String id, final String photo) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_UPLOAD, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i(TAG, response.toString());

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String success = jsonObject.getString("success");

                    if (success.equals("1")){
                        Toast.makeText(EditProfile.this, "Photo Uploaded", Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(EditProfile.this, "Error Uploading Image!", Toast.LENGTH_SHORT).show();
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(EditProfile.this, "Error Uploading Image!", Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("ID", id);
                params.put("photo", photo);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    public String getStringImage(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

        byte[] imageByteArr = byteArrayOutputStream.toByteArray();
        String encodedImage = Base64.encodeToString(imageByteArr, Base64.DEFAULT);

        return encodedImage;
    }*/
}