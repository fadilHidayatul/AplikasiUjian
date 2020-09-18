package com.example.aplikasiujian.Login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.aplikasiujian.R;
import com.example.aplikasiujian.Utils.ApiInterface;
import com.example.aplikasiujian.Utils.UtilsApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.reg_nama)
    EditText regNama;
    @BindView(R.id.reg_nis)
    EditText regNis;
    @BindView(R.id.reg_password)
    EditText regPassword;
    @BindView(R.id.btnRegister)
    Button btnRegister;
    @BindView(R.id.tvtanya)
    TextView tvtanya;
    @BindView(R.id.txtRegister)
    TextView txtRegister;

    ApiInterface apiInterface;
    Context context;
    @BindView(R.id.txtwarning)
    TextView txtwarning;
    @BindView(R.id.warning)
    RelativeLayout warning;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        context = this;
        apiInterface = UtilsApi.getApiService();

        performRegister();
    }

    private void performRegister() {
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(regNama.getText().toString())){
                    regNama.setError("Masukkan Nama");
                }else if (TextUtils.isEmpty(regNis.getText().toString())){
                    regNis.setError("Masukkan NIS");
                }else if (TextUtils.isEmpty(regPassword.getText().toString())){
                    regPassword.setError("Masukkan Password");
                }else {
                    apiInterface.getRegister(regNama.getText().toString(), regNis.getText().toString(), regPassword.getText().toString())
                            .enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                    if (response.isSuccessful()) {
                                        try {
                                            JSONObject object = new JSONObject(response.body().string());
                                            if (object.getString("success").equals("1")) {
                                                Toast.makeText(getApplicationContext(), "Register berhasil, silahkan login", Toast.LENGTH_LONG).show();
                                                Intent goLogin = new Intent(RegisterActivity.this, LoginActivity.class);
                                                startActivity(goLogin);
                                            } else {
                                                warning.setVisibility(View.VISIBLE);
                                                txtwarning.setText(object.getString("message"));
                                                closeWarning();
                                                //Toast.makeText(getApplicationContext(), "" + object.getString("message"), Toast.LENGTH_SHORT).show();
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    } else {
                                        try {
                                            JSONObject object = new JSONObject(response.errorBody().string());
                                            warning.setVisibility(View.VISIBLE);
                                            txtwarning.setText(object.getString("MESSAGE"));
                                            closeWarning();
                                            //Toast.makeText(getApplicationContext(), "" + object.getString("MESSAGE"), Toast.LENGTH_SHORT).show();
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {
                                    warning.setVisibility(View.VISIBLE);
                                    txtwarning.setText("Cek Koneksi Internet");
                                    closeWarning();
                                    //Toast.makeText(getApplicationContext(), "Cek koneksi internet", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });
    }

    private void closeWarning() {
        handler =  new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                warning.setVisibility(View.GONE);
            }
        },5000);
    }

    public void Login(View view) {
        Intent login = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(login);
    }
}
