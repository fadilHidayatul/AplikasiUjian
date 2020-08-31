package com.example.aplikasiujian.Ujian;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.aplikasiujian.MainActivity;
import com.example.aplikasiujian.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HasilUjianActivity extends AppCompatActivity {

    @BindView(R.id.hasilUjian)
    TextView hasilUjian;
    @BindView(R.id.mp)
    TextView mp;
    @BindView(R.id.btnGoToMain)
    Button btnGoToMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hasil_ujian);
        ButterKnife.bind(this);
        Intent intent = getIntent();


        ArrayList<String> listJawaban = (ArrayList<String>) getIntent().getSerializableExtra("array"); // untuk mengirim jawaban


        Log.i("TAG", "onCreate: " + listJawaban);
        hasilUjian.setText(intent.getStringExtra("nilai"));

        if (intent.getStringExtra("idmatpel").equals("1")) {
            mp.setText("Bahasa Indonesia");
        }else if(intent.getStringExtra("idmatpel").equals("2")){
            mp.setText("Bahasa Inggris");
        }else if(intent.getStringExtra("idmatpel").equals("3")){
            mp.setText("Matematika");
        }else if(intent.getStringExtra("idmatpel").equals("4")){
            mp.setText("IPA");
        }

        btnGoToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(HasilUjianActivity.this, MainActivity.class);
                startActivity(intent1);
            }
        });

    }

    @Override
    public void onBackPressed() {
    }
}
