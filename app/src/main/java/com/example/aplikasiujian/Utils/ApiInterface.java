package com.example.aplikasiujian.Utils;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {
    @FormUrlEncoded
    @POST("user/login.php")
    Call<ResponseBody> getLogin(@Field("nis") String nis,
                                @Field("password") String password);

    @FormUrlEncoded
    @POST("user/register.php")
    Call<ResponseBody> getRegister(@Field("name") String nama,
                                   @Field("nis") String nis,
                                   @Field("password") String password);

    @GET("user/getUser.php")
    Call<ResponseBody> getUser(@Query("id") String id
                               );

    @GET("soal/read_id.php")
    Call<ResponseBody> getSoal(@Query("id_matpel") String idMatpel);

    @FormUrlEncoded
    @POST("nilai/up_nilai.php")
    Call<ResponseBody> sendScore(@Query("id_user") String idUser,
                                 @Query("id_matpel") String idMatpel,
                                 @Field("nilai") int nilai
                                );

    @GET("nilai/getnilai_id.php")
    Call<ResponseBody> getNilai(@Query("id_user") int idUser
                                );
}
