package com.example.aplikasiujian.Ujian;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.aplikasiujian.LoadingDialog;
import com.example.aplikasiujian.Model.Soal;
import com.example.aplikasiujian.R;
import com.example.aplikasiujian.SharedPreferences.PrefManager;
import com.example.aplikasiujian.Utils.ApiInterface;
import com.example.aplikasiujian.Utils.UtilsApi;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UjianActivity extends AppCompatActivity {

    @BindView(R.id.no_soal)
    TextView noSoal;
    @BindView(R.id.img_soal)
    ImageView imgSoal;
    @BindView(R.id.txt_soal)
    TextView txtSoal;
    @BindView(R.id.a)
    RadioButton a;
    @BindView(R.id.b)
    RadioButton b;
    @BindView(R.id.c)
    RadioButton c;
    @BindView(R.id.d)
    RadioButton d;
    @BindView(R.id.radio_group)
    RadioGroup radioGroup;
    @BindView(R.id.btnPrev)
    Button btnPrev;
    @BindView(R.id.btnNext)
    Button btnNext;
    public int count = 1;
    @BindView(R.id.btnSubmit)
    Button btnSubmit;
    @BindView(R.id.countDown)
    TextView countDown;
    ApiInterface apiInterface;
    Context context;
    List<Soal.DATABean> dataBeans;

    String idMatpel = "";
    @BindView(R.id.s_kelas)
    TextView sKelas;

    private CountDownTimer countDownTimer;
    public long timeLeft = 0;

    String jawaban;
    String kunci;

    List<String> jawab;

    PrefManager manager;
    LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ujian);
        ButterKnife.bind(this);
        apiInterface = UtilsApi.getApiService();
        context = this;
        manager = new PrefManager(context);

        loadingDialog = new LoadingDialog(this);

        cekmatpel();
        fetchSoal();
    }

    public void onRadioClick(View view) {
        Boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.a:
                if (checked) {
                    jawaban = a.getText().toString();
                }
                break;
            case R.id.b:
                if (checked) {
                    jawaban = b.getText().toString();
                }
                break;
            case R.id.c:
                if (checked) {
                    jawaban = c.getText().toString();
                }
                break;
            case R.id.d:
                if (checked) {
                    jawaban = d.getText().toString();
                }
                break;
        }

    }

    public void fetchSoal() {
        loadingDialog.startLoading();
        apiInterface.getSoal(idMatpel, manager.getKelasUser()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject object = new JSONObject(response.body().string());
                        loadingDialog.stopLoading();
                        if (object.getString("status").equals("200")) {
                            JSONArray array = object.getJSONArray("DATA");

                            //ambil soal tambah ke model dari API
                            dataBeans = new ArrayList<>();
                            Gson gson = new Gson();
                            for (int i = 0; i < array.length(); i++) {
                                Soal.DATABean bean = gson.fromJson(array.get(i).toString(), Soal.DATABean.class);
                                dataBeans.add(bean);
                            }
                            sKelas.setText(manager.getKelasUser());
                            int num = array.length();
                            noSoal.setText(count + "");

                            //set soal pertama
                            txtSoal.setText(dataBeans.get(count - 1).getText_soal());
                            a.setText(dataBeans.get(count - 1).getA());
                            b.setText(dataBeans.get(count - 1).getB());
                            c.setText(dataBeans.get(count - 1).getC());
                            d.setText(dataBeans.get(count - 1).getD());
                            if (dataBeans.get(count - 1).getImage_soal() == null) {
                                imgSoal.setVisibility(View.GONE);
                            }
                            String urlGambar = UtilsApi.urlImage + "" + dataBeans.get(count - 1).getImage_soal();
                            Glide.with(context).load(urlGambar).placeholder(R.drawable.nilai_pic).into(imgSoal);

                            jawab = new ArrayList<>();
                            for (int i = 0; i < num; i++) {
                                jawab.add(""); //default
                            }

                            if (count == 1) {
                                btnPrev.setVisibility(View.GONE);
                            }
                            btnNext.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    jawab.set(count - 1, jawaban); //set jawaban dari soal 1

                                    count++;
                                    noSoal.setText("" + count);
                                    if (count < num) {
                                        btnNext.setVisibility(View.VISIBLE);
                                        btnPrev.setVisibility(View.VISIBLE);
                                    } else if (count == num) {
                                        btnNext.setVisibility(View.GONE);
                                        btnSubmit.setVisibility(View.VISIBLE);
                                        //submitbtn()
                                    } else {
                                        btnNext.setVisibility(View.VISIBLE);
                                        btnPrev.setVisibility(View.VISIBLE);
                                    }

                                    txtSoal.setText(dataBeans.get(count - 1).getText_soal());
                                    a.setText(dataBeans.get(count - 1).getA());
                                    b.setText(dataBeans.get(count - 1).getB());
                                    c.setText(dataBeans.get(count - 1).getC());
                                    d.setText(dataBeans.get(count - 1).getD());
                                    if (dataBeans.get(count - 1).getImage_soal() == null) {
                                        imgSoal.setVisibility(View.GONE);
                                    } else {
                                        imgSoal.setVisibility(View.VISIBLE);
                                    }
                                    String urlGambar = UtilsApi.urlImage + "" + dataBeans.get(count - 1).getImage_soal();
                                    Glide.with(context).load(urlGambar).placeholder(R.drawable.nilai_pic).into(imgSoal);

                                    //Toast.makeText(getApplicationContext(), "" + jawab.get(count - 2), Toast.LENGTH_SHORT).show();//

                                    //cek radio jika tombol dibalik
                                    if (a.getText().toString().equals(jawab.get(count - 1))) {
                                        a.setChecked(true);
                                    } else if (b.getText().toString().equals(jawab.get(count - 1))) {
                                        b.setChecked(true);
                                    } else if (c.getText().toString().equals(jawab.get(count - 1))) {
                                        c.setChecked(true);
                                    } else if (d.getText().toString().equals(jawab.get(count - 1))) {
                                        d.setChecked(true);
                                    } else {
                                        a.setChecked(true);
                                    }
                                }
                            });

                            btnSubmit.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    jawab.set(count - 1, jawaban);
                                    Log.i("TAG", "onClick: " + jawab);
                                    Toast.makeText(getApplicationContext(), "" + jawab.get(count - 1), Toast.LENGTH_SHORT).show();
                                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                                    builder.setTitle("Ujian Selesai!!!")
                                            .setMessage("Apakah kamu yakin dengan jawaban yang sudah diisi?")
                                            .setCancelable(false)
                                            .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    //pengolahan nilai disini
                                                    int totalBenar = 0;
                                                    for (int i = 0; i < num; i++) {
                                                        if (jawab.get(i).equals(dataBeans.get(i).getKunci())) {
                                                            totalBenar++;
                                                        }
                                                    }
                                                    int nilai = totalBenar * 5;
                                                    kirimNilai(nilai);

//                                                    Toast.makeText(UjianActivity.this, "Silahkan cek nilai kamu : "+nilai, Toast.LENGTH_SHORT).show();

                                                }
                                            })
                                            .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.cancel();
                                                }
                                            });
                                    AlertDialog alertDialog = builder.create();
                                    alertDialog.show();
                                }
                            });
                            btnPrev.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    count--;
                                    noSoal.setText("" + count);

                                    if (count > 1) {
                                        btnNext.setVisibility(View.VISIBLE);
                                        btnSubmit.setVisibility(View.INVISIBLE);
                                    } else if (count == 1) {
                                        btnPrev.setVisibility(View.GONE);
                                    } else {
                                        btnSubmit.setVisibility(View.GONE);
                                        btnNext.setVisibility(View.VISIBLE);
                                    }

                                    txtSoal.setText(dataBeans.get(count - 1).getText_soal());
                                    a.setText(dataBeans.get(count - 1).getA());
                                    b.setText(dataBeans.get(count - 1).getB());
                                    c.setText(dataBeans.get(count - 1).getC());
                                    d.setText(dataBeans.get(count - 1).getD());
                                    if (dataBeans.get(count - 1).getImage_soal() == null) {
                                        imgSoal.setVisibility(View.GONE);
                                    } else {
                                        imgSoal.setVisibility(View.VISIBLE);
                                    }
                                    String urlGambar = UtilsApi.urlImage + "" + dataBeans.get(count - 1).getImage_soal();
                                    Glide.with(context).load(urlGambar).placeholder(R.drawable.nilai_pic).into(imgSoal);

                                    if (a.getText().toString().equals(jawab.get(count - 1))) {
                                        a.setChecked(true);
                                    } else if (b.getText().toString().equals(jawab.get(count - 1))) {
                                        b.setChecked(true);
                                    } else if (c.getText().toString().equals(jawab.get(count - 1))) {
                                        c.setChecked(true);
                                    } else if (d.getText().toString().equals(jawab.get(count - 1))) {
                                        d.setChecked(true);
                                    }

                                    //Toast.makeText(getApplicationContext(), ""+jawaban, Toast.LENGTH_SHORT).show();
                                }
                            });

                            timeLeft = timeLeft + (Long.parseLong(dataBeans.get(0).getWaktu_pengerjaan()) * 60000);
                            startCountdown();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Cek koneksi internet", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void kirimNilai(int nilai) {

        String score = String.valueOf(nilai);
        apiInterface.sendScore(manager.getIdUser(),idMatpel,manager.getKelasUser(),nilai).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject object = new JSONObject(response.body().string());
                        if (object.getString("status").equals("200")) {
                            Toast.makeText(getApplicationContext(), "Ujian Selesai, Silahkan Cek Nilai", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(UjianActivity.this, HasilUjianActivity.class);
                            intent.putExtra("array", (Serializable) jawab);
                            intent.putExtra("nilai", score);
                            intent.putExtra("idmatpel", idMatpel);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), "" + object.getString("message"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(context, "Cek koneksi internet", Toast.LENGTH_SHORT).show();
                loadingDialog.stopLoading();
            }
        });

    }


    private void cekmatpel() {
        Intent intent = getIntent();
        int flag = intent.getIntExtra("FLAG", 0);
        if (flag == 1) {
            idMatpel = String.valueOf(flag);
        } else if (flag == 2) {
            idMatpel = String.valueOf(flag);
        } else if (flag == 3) {
            idMatpel = String.valueOf(flag);
        } else if (flag == 4) {
            idMatpel = String.valueOf(flag);
        }
    }

    private void startCountdown() {
        countDownTimer = new CountDownTimer(timeLeft, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeft = millisUntilFinished;
                updateTimer();
            }

            private void updateTimer() {
                int minutes = (int) timeLeft / 60000;
                int seconds = (int) timeLeft % 60000 / 1000;
                String stringTime;

                stringTime = "" + minutes;
                stringTime += ":";

                if (seconds < 10) {
                    stringTime += "0";
                }
                stringTime += seconds;
                countDown.setText(stringTime);
            }

            @Override
            public void onFinish() {
                countDown.setText("Done");
                UjianActivity.this.finish();
            }
        }.start();

    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Keluar Ujian")
                .setMessage("Apakah kamu ingin keluar ujian? Jika keluar jawaban tidak tersimpan")
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(UjianActivity.this, "Ujian tidak tersimpan", Toast.LENGTH_SHORT).show();
                        UjianActivity.super.onBackPressed();

                    }
                }).setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}
