package com.study.mvp_zk3_210802.mvp;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;

public
class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
//        ARouter集成
        ARouter.init(this);
    }
}
