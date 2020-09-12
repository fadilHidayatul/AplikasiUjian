package com.example.aplikasiujian.SharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context mcontext;

    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "ujian";
    public static final String SESSION_KEY = "SESSION_USER";
    public static final String ID_USER = "ID_USER";
    public static final String TOKEN_USER = "TOKEN_USER";
    public static final String USERNAME = "USERNAME";
    public static final String KELAS = "KELAS";

    public PrefManager(Context context){
        this.mcontext = context;
        pref = mcontext.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    //login session
    public void saveSession(){
        editor.putBoolean(SESSION_KEY, true);
        editor.commit();
    }
    public boolean getSession(){
        return pref.getBoolean(SESSION_KEY,false);
    }
    public void removeSession(){
        editor.putBoolean(SESSION_KEY, false);
        editor.commit();
    }

    //set dari user
    public void spStringKelas(String key, String value){
        editor.putString(key, value);
        editor.commit();
    }
        public void spStringToken(String key, String value){
        editor.putString(key,value);
        editor.commit();
    }
    public void spString(String key, String value){
        editor.putString(key, value);
        editor.commit();
    }
    public void spStringName(String key, String value){
        editor.putString(key, value);
        editor.commit();
    }

    //get dari user
    public String getKelasUser(){
        return pref.getString(KELAS,"");
    }
    public String getIdUser(){
        return pref.getString(ID_USER,"");
    }
    public String getToken(){
        return pref.getString(TOKEN_USER,"");
    }
    public String getName(){
        return pref.getString(USERNAME,"");
    }




}
