package com.example.aplikasiujian;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.aplikasiujian.Home.HomeFragment;
import com.example.aplikasiujian.Login.LoginActivity;
import com.example.aplikasiujian.Nilai.NilaiFragment;
import com.example.aplikasiujian.Profile.ProfileFragment;
import com.example.aplikasiujian.SharedPreferences.PrefManager;
import com.example.aplikasiujian.Utils.ApiInterface;
import com.example.aplikasiujian.Utils.UtilsApi;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.bottom_navigation)
    BottomNavigationView navigationView;
    private boolean doubleback;
    private Toast toast;

    ApiInterface apiInterface;
    PrefManager manager;
    Context context;
    LoadingDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        changeFragment(new HomeFragment(), HomeFragment.class.getSimpleName());

        bottomView();
        context = this.getApplicationContext();
        apiInterface = UtilsApi.getApiService();
        manager = new PrefManager(context);
        dialog = new LoadingDialog(MainActivity.this);

        auth();
    }

    private void auth() {

        apiInterface.getUser(manager.getIdUser()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try {
                        JSONObject object = new JSONObject(response.body().string());
                        if (object.getString("status").equals("200")){
                            JSONArray data = object.getJSONArray("data");

                            if (!data.getJSONObject(0).getString("token").equals(manager.getToken())){
                                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                builder.setMessage("Sesi Kamu Berakhir, Harap Login Kembali")
                                        .setCancelable(false)
                                        .setPositiveButton("Oke", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent intent = new Intent(context, LoginActivity.class);
                                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                startActivity(intent);
                                            }
                                        });
                                AlertDialog alertDialog = builder.create();
                                alertDialog.show();
                                manager.removeSession();
                                //Toast.makeText(context, "token habis", Toast.LENGTH_SHORT).show();
                            }

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

            }
        });
    }

    private void bottomView() {
        Intent intent = getIntent();
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectFragment = null;
                switch (item.getItemId()) {
                    case R.id.home:
                        changeFragment(new HomeFragment(), HomeFragment.class.getSimpleName());
                        break;
                    case R.id.nilai:
                        changeFragment(new NilaiFragment(), NilaiFragment.class.getSimpleName());
                        break;
                    case R.id.profile:
                        changeFragment(new ProfileFragment(), ProfileFragment.class.getSimpleName());
                        break;
                }
                return true;
            }
        });
    }

    public void changeFragment(Fragment fragment, String tagFragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Fragment current = fragmentManager.getPrimaryNavigationFragment();
        if (current != null) {
            fragmentTransaction.hide(current);
        }

        Fragment temp = fragmentManager.findFragmentByTag(tagFragment);
        if (temp == null) {
            temp = fragment;
            fragmentTransaction.add(R.id.fragment_container, temp, tagFragment);
        } else {
            fragmentTransaction.show(temp);
        }

        fragmentTransaction.setPrimaryNavigationFragment(temp);
        fragmentTransaction.setReorderingAllowed(true);
        fragmentTransaction.commitAllowingStateLoss();
    }


    @Override
    public void onBackPressed() {
        if (doubleback){
            toast.cancel();
            super.onBackPressed();
            moveTaskToBack(true);
        }else{
            toast = Toast.makeText(this,"Press again to exit",Toast.LENGTH_SHORT);
            toast.show();
            doubleback = true;
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleback = false;
                }
            },2000);
        }

    }

}
