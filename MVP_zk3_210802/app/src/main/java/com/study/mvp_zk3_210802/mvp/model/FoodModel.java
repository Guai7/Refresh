package com.study.mvp_zk3_210802.mvp.model;

import com.study.mvp_zk3_210802.mvp.Api;
import com.study.mvp_zk3_210802.mvp.contract.IContract;
import com.study.mvp_zk3_210802.mvp.entity.FoodEntity;
import com.study.mvp_zk3_210802.mvp.utils.HttpFoodUtils;
import com.study.mybase.model.BaseModel;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public
class FoodModel extends BaseModel implements IContract.IFoodModel {

    //获取数据 网络请求的方法 调用Retrofit中的单例方法 实现API.class中的方法 并回调观察者
    @Override
    public void getFood(String url,Observer<FoodEntity> observer) {
        HttpFoodUtils.getInstance()
                .getRetrofit()
                .create(Api.class)
                .getFood(url)
                //RxJava框架
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    @Override
    public void destroy() {
        //销毁方法
    }
}
