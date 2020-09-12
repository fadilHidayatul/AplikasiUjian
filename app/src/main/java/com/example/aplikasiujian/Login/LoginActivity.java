package com.example.aplikasiujian.Login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.aplikasiujian.MainActivity;
import com.example.aplikasiujian.Model.User;
import com.example.aplikasiujian.R;
import com.example.aplikasiujian.SharedPreferences.PrefManager;
import com.example.aplikasiujian.Utils.ApiInterface;
import com.example.aplikasiujian.Utils.UtilsApi;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.log_nis)
    EditText nis;
    @BindView(R.id.log_password)
    EditText password;
    @BindView(R.id.btnLogin)
    Button btnLogin;
    @BindView(R.id.tvtanya)
    TextView tvtanya;
    @BindView(R.id.txtRegister)
    TextView txtRegister;

    PrefManager manager;
    ApiInterface apiInterface;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        context =this;
        manager = new PrefManager(context);
        apiInterface = UtilsApi.getApiService();

        performLogin();
    }

    private void performLogin() {
       btnLogin.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
                apiInterface.getLogin(nis.getText().toString(), password.getText().toString())
                        .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            try {
                                JSONObject object = new JSONObject(response.body().string());
                                if (object.getString("status").equals("200")){
                                    JSONObject data = object.getJSONObject("data");

                                    //kirim data ke model
                                    Gson gson = new Gson();
                                    User.DataBean user = gson.fromJson(data+"",User.DataBean.class);

                                    //simpan di shared preferences
                                    manager.saveSession();
                                    manager.spStringToken(PrefManager.TOKEN_USER, user.getToken());
                                    manager.spString(PrefManager.ID_USER, user.getId_user());
                                    manager.spStringName(PrefManager.USERNAME, user.getUsername());
                                    manager.spStringKelas(PrefManager.KELAS, user.getKelas());

                                    Intent masukApp = new Intent(LoginActivity.this, MainActivity.class);
                                    masukApp.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(masukApp);
                                }else{
                                    Toast.makeText(getApplicationContext(), ""+object.getString("message"), Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }else {
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
                        Toast.makeText(context, "Cek koneksi internet", Toast.LENGTH_SHORT).show();

                    }
                });


           }
       });
    }

    public void Register(View view) {
        Intent register = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(register);
    }

    @Override
    protected void onStart() {
        super.onStart();
        PrefManager prefManager = new PrefManager(this);
        boolean userId = prefManager.getSession();
        if (userId) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }
}
