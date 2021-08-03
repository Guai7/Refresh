package com.study.mybase.presenter;

import com.study.mybase.model.IModel;
import com.study.mybase.view.IView;

//创建泛型 一个转型V层，另一个M层
public abstract
class BasePresenter<V extends IView,M extends IModel> implements IPresenter {
    //创建两个继承类可调用的变量
    protected V view;
    protected M model;

    //构造方法
    public BasePresenter(V view, M model) {
        this.view = view;
        this.model = model;
    }

    //销毁方法
    @Override
    public void destroy() {
        if (model!=null){
            model.destroy();
            model = null;
        }
    }
}
