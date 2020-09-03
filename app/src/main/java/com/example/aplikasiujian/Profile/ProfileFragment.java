package com.example.aplikasiujian.Profile;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.aplikasiujian.LoadingDialog;
import com.example.aplikasiujian.Login.LoginActivity;
import com.example.aplikasiujian.R;
import com.example.aplikasiujian.SharedPreferences.PrefManager;
import com.example.aplikasiujian.Utils.ApiInterface;
import com.example.aplikasiujian.Utils.UtilsApi;

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


public class ProfileFragment extends Fragment {
    @BindView(R.id.btnLogout)
    Button btnLogout;
    @BindView(R.id.prof)
    ImageView prof;
    @BindView(R.id.prof_user)
    TextView profUser;
    @BindView(R.id.prof_nis)
    TextView profNis;
    PrefManager manager;
    Context context;
    ApiInterface apiInterface;
    LoadingDialog loadingDialog;

    public ProfileFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, view);

        context = this.getContext();
        manager = new PrefManager(context);
        apiInterface = UtilsApi.getApiService();
        loadingDialog = new LoadingDialog(context);

        Glide.with(view.getContext()).load(R.drawable.prof).into(prof);
        fetchProfile();
        doLogout();

        return view;
    }

    private void fetchProfile() {
        loadingDialog.startLoading();
        apiInterface.getUser(manager.getIdUser()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try {
                        loadingDialog.stopLoading();
                        JSONObject object = new JSONObject(response.body().string());
                        if (object.getString("status").equals("200")){
                            JSONArray data = object.getJSONArray("data");
                            for (int i = 0; i <data.length() ; i++) {
                                profUser.setText(data.getJSONObject(i).getString("username"));
                                profNis.setText(data.getJSONObject(i).getString("nis"));
                            }
                        }else{
                            Toast.makeText(getContext(), ""+object.getString("message"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else{
                    try {
                        JSONObject object = new JSONObject(response.errorBody().string());
                        Toast.makeText(getContext(), ""+object.getString("MESSAGE"), Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getContext(), "koneksi ang baruak", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void doLogout() {
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setMessage("Apakah kamu akan keluar aplikasi?")
                        .setCancelable(false)
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                manager.removeSession();
                                getActivity().finish();
                                Intent intent = new Intent(v.getContext(), LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
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
    }
}
