package com.study.mvp_zk3_210802.mvp;

import com.study.mvp_zk3_210802.mvp.entity.FoodEntity;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

//Api接口 用于编写Retrofit调用的方法
public
interface Api {
    /**
     * Get方式获取数据
     * @param url 用于加载的url的后缀（主要部分在Utils层中）
     * @return
     */
    @GET
    Observable<FoodEntity> getFood(@Url String url);

    /**
     * 另外一种写法 可以把url后缀写为固定值 参数自己传
     * @param page page参数 会自动给到url中作为参数
     * @return
     */
    @GET("dish_list.php?stage_id=1&limit=20")
    Observable<FoodEntity> getFood(@Query("page")int page);
}
