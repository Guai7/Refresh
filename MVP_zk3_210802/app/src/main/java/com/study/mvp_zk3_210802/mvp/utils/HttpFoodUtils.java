package com.study.mvp_zk3_210802.mvp.utils;

import java.util.concurrent.TimeUnit;

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

    //初始化 类似单例
    public Retrofit getRetrofit() {
        if (retrofit==null){
            retrofit = new Retrofit.Builder()
                    //这里填写主要url
                    .baseUrl("http://www.qubaobei.com/ios/cf/")
                    //添加Okhttp拦截器 并设置超时时间
                    .client(new OkHttpClient.Builder()
                            .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                            .readTimeout(2, TimeUnit.MINUTES)
                            .connectTimeout(2,TimeUnit.MINUTES)
                            .writeTimeout(2,TimeUnit.MINUTES)
                            .build())
                    //添加解析工厂和回调工厂
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    //创建
                    .build();
        }
        //返回
        return retrofit;
    }
}
