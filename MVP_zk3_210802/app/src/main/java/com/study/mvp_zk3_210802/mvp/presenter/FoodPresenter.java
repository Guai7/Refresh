package com.study.mvp_zk3_210802.mvp.presenter;

import com.blankj.utilcode.util.ToastUtils;
import com.study.mvp_zk3_210802.mvp.contract.IContract;
import com.study.mvp_zk3_210802.mvp.entity.FoodEntity;
import com.study.mybase.presenter.BasePresenter;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * P层
 */
public
class FoodPresenter extends BasePresenter<IContract.IFoodView, IContract.IFoodModel> {

    private Disposable disposable;

    public FoodPresenter(IContract.IFoodView view, IContract.IFoodModel model) {
        super(view, model);
    }

    public void getFood(String url){
        model.getFood(url, new Observer<FoodEntity>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;     //设为全局
            }

            @Override
            public void onNext(FoodEntity foodEntity) {
                view.showFood(foodEntity.getData());
            }

            @Override
            public void onError( Throwable e) {
                ToastUtils.showShort(e.getMessage().toString());
                disposable.dispose();       //遇到错误后 杀死d 断开M层和V层的连接 防止内存泄漏
            }

            @Override
            public void onComplete() {
//                disposable.dispose();       //方法执行完毕后 断开连接
            }
        });
    }
}
