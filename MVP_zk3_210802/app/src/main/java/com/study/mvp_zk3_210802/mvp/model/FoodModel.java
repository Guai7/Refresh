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

    }
}
