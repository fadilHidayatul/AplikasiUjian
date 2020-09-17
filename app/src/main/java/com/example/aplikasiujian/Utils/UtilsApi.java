package com.example.aplikasiujian.Utils;

public class UtilsApi {
    //public static final String baseURL = "http://10.234.252.94/ujianTA/"; //wifi id
    //public static final String baseURL = "http://192.168.43.136/ujianTA/"; //androidAP
    //public static final String baseURL = "http://192.168.43.229/ujianTA/"; //faRhan
    //public static final String baseURL = "http://192.168.43.244/"; //Kolord
    public static final String baseURL = "http://35.173.133.143/"; //AWS


    //public static final String urlImage = "http://192.168.43.136";
    public static final String urlImage = "http://35.173.133.143";
    //public static final String urlImage = "http://192.168.43.229";

    public static ApiInterface getApiService(){
        return RetrofitClient.getRetrofit(baseURL).create(ApiInterface.class);
    }

}
