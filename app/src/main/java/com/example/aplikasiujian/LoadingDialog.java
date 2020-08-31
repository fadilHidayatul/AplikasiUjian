package com.example.aplikasiujian;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;

public class LoadingDialog {
    private Context context;
    private AlertDialog alertDialog;

    public LoadingDialog(Context context) {
        this.context = context;
    }

    public void startLoading() {
        AlertDialog.Builder builder =new AlertDialog.Builder(context);

        builder.setView(LayoutInflater.from(context).inflate(R.layout.activity_loading_dialog,null));

        alertDialog = builder.create();
        alertDialog.show();
    }

    public void stopLoading(){
        alertDialog.dismiss();
    }
}
