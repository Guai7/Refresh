package com.study.mvp_zk3_210802.mvp.contract;

import com.study.mvp_zk3_210802.mvp.entity.FoodEntity;
import com.study.mybase.model.IModel;
import com.study.mybase.view.IView;

import java.util.List;

import io.reactivex.Observer;

public
interface IContract {
    //获取数据的视图接口
    interface IFoodView extends IView{
        /**
         * 显示数据
         * @param list 展示的数据集合
         */
        void showFood(List<FoodEntity.DataBean> list);
    }

    //获取数据的M层接口
    interface IFoodModel extends IModel{
        /**
         * 获取数据
         * @param url 后缀链接
         * @param observer 观察者
         */
        void getFood(String url,Observer<FoodEntity> observer);
    }
}
