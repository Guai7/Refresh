package com.study.mvp_zk3_210802.mvp.contract;

import com.study.mvp_zk3_210802.mvp.entity.FoodEntity;
import com.study.mybase.model.IModel;
import com.study.mybase.view.IView;

import java.util.List;

import io.reactivex.Observer;

public
interface IContract {
    interface IFoodView extends IView{
        void showFood(List<FoodEntity.DataBean> list);
    }

    interface IFoodModel extends IModel{
        void getFood(String url,Observer<FoodEntity> observer);
    }
}
