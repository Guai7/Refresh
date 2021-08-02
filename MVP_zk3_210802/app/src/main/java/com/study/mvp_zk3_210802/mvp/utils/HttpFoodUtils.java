package com.study.mvp_zk3_210802.mvp.utils;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 网络请求数据 单例类
 */
public
class HttpFoodUtils {
    private static HttpFoodUtils utils;
    private Retrofit retrofit;

    /**
     * 饿汉式单例
     * @return
     */
    public static HttpFoodUtils getInstance() {
        if (utils==null){
            utils = new HttpFoodUtils();
        }
        return utils;
    }

    public Retrofit getRetrofit() {
        if (retrofit==null){
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://www.qubaobei.com/ios/cf/")
                    .client(new OkHttpClient.Builder()
                            .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                            .build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
