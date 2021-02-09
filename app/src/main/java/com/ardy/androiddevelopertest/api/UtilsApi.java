package com.ardy.androiddevelopertest.api;



public class UtilsApi {
    public static final String BASE_URL_API = "https://jobs.github.com/";

    // Mendeklarasikan Interface BaseApiService
    public static BaseApiService getAPIService(){
        return RetrofitClient.getClient(BASE_URL_API).create(BaseApiService.class);
    }
}
