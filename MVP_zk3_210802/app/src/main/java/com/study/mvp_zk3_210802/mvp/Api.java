package com.study.mvp_zk3_210802.mvp;

import com.study.mvp_zk3_210802.mvp.entity.FoodEntity;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Url;

public
interface Api {
    //RxJava
    @GET
    Observable<FoodEntity> getFood(@Url String url);
}
