package com.study.mvp_zk3_210802.mvp.presenter;

import com.blankj.utilcode.util.ToastUtils;
import com.study.mvp_zk3_210802.mvp.contract.IContract;
import com.study.mvp_zk3_210802.mvp.entity.FoodEntity;
import com.study.mybase.presenter.BasePresenter;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public
class FoodPresenter extends BasePresenter<IContract.IFoodView, IContract.IFoodModel> {
    public FoodPresenter(IContract.IFoodView view, IContract.IFoodModel model) {
        super(view, model);
    }

    public void getFood(String url){
        model.getFood(url, new Observer<FoodEntity>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(FoodEntity foodEntity) {
                view.showFood(foodEntity.getData());
            }

            @Override
            public void onError( Throwable e) {
                ToastUtils.showShort(e.getMessage().toString());
            }

            @Override
            public void onComplete() {

            }
        });
    }
}
