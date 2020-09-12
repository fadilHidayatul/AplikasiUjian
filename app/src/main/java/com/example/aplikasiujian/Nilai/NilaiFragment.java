package com.example.aplikasiujian.Nilai;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.aplikasiujian.LoadingDialog;
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

public class NilaiFragment extends Fragment {

    @BindView(R.id.hi)
    TextView hi;
    @BindView(R.id.namaUser)
    TextView namaUser;
    @BindView(R.id.nilai_bindo)
    TextView nilaiBindo;
    @BindView(R.id.nilai_bing)
    TextView nilaiBing;
    @BindView(R.id.nilai_mm)
    TextView nilaiMm;
    @BindView(R.id.nilai_ipa)
    TextView nilaiIpa;
    ApiInterface apiInterface;
    Context context;
    PrefManager manager;
    LoadingDialog loadingDialog;

    public NilaiFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view =  inflater.inflate(R.layout.fragment_nilai, container, false);
        ButterKnife.bind(this,view);

        context = this.getContext();
        apiInterface = UtilsApi.getApiService();
        manager = new PrefManager(context);
        loadingDialog = new LoadingDialog(context);

        fetchNilai();

        return view;
    }

    private void fetchNilai() {
        loadingDialog.startLoading();
        int idUser = Integer.parseInt(manager.getIdUser());
        namaUser.setText(manager.getName());
        apiInterface.getNilai(idUser, manager.getKelasUser()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try {
                        loadingDialog.stopLoading();
                        JSONObject object = new JSONObject(response.body().string());
                        if (object.getString("status").equals("200")){
                            JSONArray array = object.getJSONArray("DATA");

                            for (int i = 0; i < array.length(); i++) {
                                if (array.getJSONObject(i).getString("id_matpel").equals("1")){
                                    nilaiBindo.setText(array.getJSONObject(i).getString("nilai"));
                                }

                                if (array.getJSONObject(i).getString("id_matpel").equals("2")){
                                    nilaiBing.setText(array.getJSONObject(i).getString("nilai"));
                                }

                                if (array.getJSONObject(i).getString("id_matpel").equals("3")){
                                    nilaiMm.setText(array.getJSONObject(i).getString("nilai"));
                                }

                                if (array.getJSONObject(i).getString("id_matpel").equals("4")){
                                    nilaiIpa.setText(array.getJSONObject(i).getString("nilai"));
                                }

                            }
//                            namaUser.setText(array.getJSONObject(0).getString("username"));

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
                Toast.makeText(getContext(), "Cek koneksi internet", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
