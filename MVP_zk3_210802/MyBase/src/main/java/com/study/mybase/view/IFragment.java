package com.study.mybase.view;

import android.view.View;

import androidx.annotation.IdRes;

public
interface IFragment extends IActivity {
    //方便Fragment 初始化组件
    <T extends View> T findViewById(@IdRes int id);
}
