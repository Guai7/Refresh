package com.study.mybase.view;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * 和正常Activity一样继承AppCompatActivity 需要实现activity的接口 和 视图层的接口
 */
public abstract
class BaseActivity extends AppCompatActivity implements IActivity,IView {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(bindLayout());   //这里调用获取视图方法 用于设置主视图

        //初始化视图
        initView();
        //初始化数据
        initData();
    }

    /**
     * Toast信息
     * @param msg 需要Toast的信息
     */
    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
