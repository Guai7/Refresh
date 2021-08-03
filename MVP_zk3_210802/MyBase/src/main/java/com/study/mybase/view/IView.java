package com.study.mybase.view;

public
interface IView {
    //用于显示进度条
    void showLoading();
    //用于隐藏进度条
    void hideLoading();

    //用于便捷Toast信息
    void showToast(String msg);
}
