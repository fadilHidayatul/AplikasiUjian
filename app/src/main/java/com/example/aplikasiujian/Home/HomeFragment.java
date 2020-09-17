package com.example.aplikasiujian.Home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.aplikasiujian.LoadingDialog;
import com.example.aplikasiujian.R;
import com.example.aplikasiujian.SharedPreferences.PrefManager;
import com.example.aplikasiujian.Ujian.UjianActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeFragment extends Fragment {

    @BindView(R.id.bindo)
    Button bindo;
    @BindView(R.id.bing)
    Button bing;
    @BindView(R.id.mm)
    Button mm;
    @BindView(R.id.ipa)
    Button ipa;

    PrefManager manager;
    Context context;

    public HomeFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this,view);
        context = view.getContext();
        manager = new PrefManager(context);

        bindo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ujian = new Intent(view.getContext(), UjianActivity.class);
                ujian.putExtra("FLAG",1);
                startActivity(ujian);
            }
        });

        bing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ujianBing = new Intent(view.getContext(), UjianActivity.class);
                ujianBing.putExtra("FLAG",2);
                startActivity(ujianBing);
            }
        });

        mm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ujianmm = new Intent(view.getContext(), UjianActivity.class);
                ujianmm.putExtra("FLAG",3);
                startActivity(ujianmm);
            }
        });

        ipa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ujianipa = new Intent(view.getContext(), UjianActivity.class);
                ujianipa.putExtra("FLAG", 4);
                startActivity(ujianipa);
            }
        });


        return view;
    }
}
