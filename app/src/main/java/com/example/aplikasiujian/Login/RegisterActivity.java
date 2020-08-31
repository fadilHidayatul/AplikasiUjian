package com.example.aplikasiujian.Login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
                apiInterface.getRegister(regNama.getText().toString(),regNis.getText().toString(),regPassword.getText().toString())
                        .enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                if (response.isSuccessful()){
                                    try {
                                        JSONObject object = new JSONObject(response.body().string());
                                        if (object.getString("success").equals("1")){
                                            Toast.makeText(getApplicationContext(), "Register berhasil, silahkan login", Toast.LENGTH_LONG).show();
                                            Intent goLogin = new Intent(RegisterActivity.this, LoginActivity.class);
                                            startActivity(goLogin);
                                        }else{
                                            Toast.makeText(getApplicationContext(), ""+object.getString("message"), Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }else{
                                    try {
                                        JSONObject object = new JSONObject(response.errorBody().string());
                                        Toast.makeText(getApplicationContext(), ""+object.getString("MESSAGE"), Toast.LENGTH_SHORT).show();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                Toast.makeText(getApplicationContext(), "Koneksi bermasalah!!!.", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

    }

    public void Login(View view) {
        Intent login = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(login);
    }
}
