package com.example.scoops;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.example.scoops.Login;

import java.util.HashMap;

import javax.xml.namespace.NamespaceContext;

import static com.example.scoops.Login.ss_email;
import static com.example.scoops.Login.ss_name;

public class EditProfile extends AppCompatActivity {

    SessionManager sessionManager;
    private EditText Name, Email, Phone, Address, City;
    private ImageButton save;
    private String getId;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private static String URL_READ = "https://lamp.ms.wits.ac.za/~s1445435/readInfo.php";
    private static String URL_EDIT = "https://lamp.ms.wits.ac.za/~s1445435/editInfo.php";
    private static String TAG = EditProfile.class.getSimpleName();
    private Menu action;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        sessionManager = new SessionManager(this);


        Name = findViewById(R.id.txtNameE);
        Email = findViewById(R.id.txtMailE);
        Phone = findViewById(R.id.txtPhoneE);
        Address = findViewById(R.id.txtAddressE);
        City = findViewById(R.id.txtCityE);
        save = findViewById(R.id.btnSave);

        HashMap<String, String> user = sessionManager.getUserDetail();
        String mName = user.get(sessionManager.NAME);
        String mEmail = user.get(sessionManager.EMAIL);

        Name.setText(mName);
        Email.setText(mEmail);


        /*
        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();




        HashMap<String, String> user = sessionManager.getUserDetail();
        getId = user.get(sessionManager.ID);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //sessionManager.logout();
            }
        });

        //Radio buttons
        radioGroup = findViewById(R.id.radioGroup);
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);
        String gender = radioButton.getText().toString().trim();*/
    }

    /*
    private void checkedButton(View v){

    }
    private void getUserDetails(){

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_READ, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                Log.i(TAG, response.toString());

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("read");

                    if (success.equals("1")){
                        for (int i = 0; i < jsonArray.length(); ++i){
                            JSONObject object = jsonArray.getJSONObject(i);

                            String s_email = Email.getText().toString().trim();
                            String s_Fname = Fname.getText().toString().trim();
                            String s_Lname = Lname.getText().toString().trim();
                            String s_phone = Phone.getText().toString().trim();
                            String s_address = Address.getText().toString().trim();
                            String s_city = City.getText().toString().trim();

                            Email.setText(s_email);
                            Fname.setText(s_Fname);
                            Lname.setText(s_Lname);
                            Phone.setText(s_phone);
                            Address.setText(s_address);
                            City.setText(s_city);

                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                    Toast.makeText(EditProfile.this, "Error reading" + e.toString(), Toast.LENGTH_SHORT).show();
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(EditProfile.this, "Error reading" + error.toString(), Toast.LENGTH_SHORT).show();
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

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_action, menu);
        action = menu;
        action.findItem(R.id.menu_save).setVisible(false);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_edit:
                Fname.setFocusableInTouchMode(true);
                Lname.setFocusableInTouchMode(true);
                Email.setFocusableInTouchMode(true);
                Phone.setFocusableInTouchMode(true);
                Address.setFocusableInTouchMode(true);
                City.setFocusableInTouchMode(true);

                InputMethodManager immm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                immm.showSoftInput(Fname, InputMethodManager.SHOW_IMPLICIT);

                action.findItem(R.id.menu_edit).setVisible(false);
                action.findItem(R.id.menu_save).setVisible(true);

                return true;

            case R.id.menu_save:
                saveEditDetail();

                action.findItem(R.id.menu_edit).setVisible(false);
                action.findItem(R.id.menu_save).setVisible(true);

                Fname.setFocusableInTouchMode(false);
                Lname.setFocusableInTouchMode(false);
                Email.setFocusableInTouchMode(false);
                Phone.setFocusableInTouchMode(false);
                Address.setFocusableInTouchMode(false);
                City.setFocusable(false);
                Fname.setFocusable(false);
                Lname.setFocusable(false);
                Email.setFocusable(false);
                Phone.setFocusable(false);
                Address.setFocusable(false);
                City.setFocusable(false);

                return true;

            default:
                return super.onOptionsItemSelected(item);

        }


    }

    private void saveEditDetail(){
        final String s_email = Email.getText().toString().trim();
        final String s_fname = Fname.getText().toString().trim();
        final String s_lname = Lname.getText().toString().trim();
        final String s_phone = Phone.getText().toString().trim();
        final String s_address = Address.getText().toString().trim();
        final String s_city = City.getText().toString().trim();
        final String ID = getId;

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Saving...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_EDIT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();


                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");

                    if (success.equals("1")){
                        Toast.makeText(EditProfile.this, "Success!", Toast.LENGTH_SHORT).show();
                        sessionManager.createSession(s_fname, s_email, ID);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                    Toast.makeText(EditProfile.this, "Error reading" + e.toString(), Toast.LENGTH_SHORT).show();
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(EditProfile.this, "Error reading" + error.toString(), Toast.LENGTH_SHORT).show();

                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Fname", s_fname);
                params.put("Lname", s_lname);
                params.put("Email", s_email);
                params.put("Address", s_address);
                params.put("City", s_city);
                params.put("ID", ID);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }*/


}