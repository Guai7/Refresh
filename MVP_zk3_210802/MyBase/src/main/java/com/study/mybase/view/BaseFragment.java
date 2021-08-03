package com.study.mybase.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public abstract
class BaseFragment extends Fragment implements IView,IFragment {

    private View view;

    /**
     * 创建视图时调用
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //这里绑定主视图
        view = inflater.inflate(bindLayout(),container,false);
        //返回主视图
        return view;
    }

    /**
     * 创建视图后调用
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //初始化视图
        initView();

        //初始化数据
        initData();
    }

    /**
     * 实现findViewById方法
     * @param id
     * @param <T>
     * @return
     */
    @Override
    public <T extends View> T findViewById(int id) {
        return view.findViewById(id);
    }
}
