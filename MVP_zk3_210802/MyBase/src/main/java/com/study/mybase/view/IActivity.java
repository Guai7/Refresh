package com.study.mybase.view;

public
interface IActivity {
    //用于初始化视图
    void initView();
    //用于加载数据
    void initData();

    //用于绑定视图时使用（获取视图）
    int bindLayout();
}
