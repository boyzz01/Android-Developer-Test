package com.ardy.androiddevelopertest.api;


import com.ardy.androiddevelopertest.response.JobResponse;

import java.util.List;


import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiClient {


    @GET("positions.json")
    Observable<List<JobResponse>> getAllJob();




}
