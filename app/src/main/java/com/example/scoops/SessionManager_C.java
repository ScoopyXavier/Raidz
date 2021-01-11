package com.example.scoops;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager_C {
    SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    public Context context;
    int PRIVATE_MODE = 0;

    private static final String PREF_NAME_C = "LOGIN_C";
    private static final String LOGIN_C = "IS_LOGIN_C";
    public static final String NAME_C = "NAME_C";
    public static final String EMAIL_C = "EMAIL_C";
    public static final String ID_C = "ID_C";

    public SessionManager_C(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME_C, PRIVATE_MODE);
        editor = sharedPreferences.edit();

    }

    public void createSessionC(String name_c, String email_c, String id_c){
        editor.putBoolean(LOGIN_C, true);
        editor.putString(NAME_C, name_c);
        editor.putString(EMAIL_C, email_c);
        editor.putString(ID_C, id_c);
        editor.apply();
    }

    public boolean isLoginC(){return sharedPreferences.getBoolean(LOGIN_C, false);}

    public void checkLoginC(){
        if (!this.isLoginC()){
            Intent intent = new Intent(context, Login.class);
            context.startActivity(intent);
            ((ClientProfile) context).finish();
        }
    }

    public HashMap<String, String> getUserDetailC(){
        HashMap<String, String> user = new HashMap<>();
        user.put(NAME_C, sharedPreferences.getString(NAME_C, null));
        user.put(EMAIL_C, sharedPreferences.getString(EMAIL_C, null));
        user.put(ID_C, sharedPreferences.getString(ID_C, null));

        return user;
    }

    public void logoutC(){
        editor.clear();
        editor.commit();
        Intent intent = new Intent(context, Login.class);
        context.startActivity(intent);
        ((ClientProfile) context).finish();
    }

}
