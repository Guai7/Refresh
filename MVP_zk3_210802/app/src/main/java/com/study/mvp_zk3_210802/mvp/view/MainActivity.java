package com.study.mvp_zk3_210802.mvp.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.PopupWindow;

import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.listener.OnItemLongClickListener;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;
import com.study.mvp_zk3_210802.R;
import com.study.mvp_zk3_210802.mvp.adapter.RecyclerAdapter;
import com.study.mvp_zk3_210802.mvp.contract.IContract;
import com.study.mvp_zk3_210802.mvp.entity.FoodEntity;
import com.study.mvp_zk3_210802.mvp.model.FoodModel;
import com.study.mvp_zk3_210802.mvp.presenter.FoodPresenter;
import com.study.mybase.view.BaseActivity;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MainActivity extends BaseActivity implements IContract.IFoodView, OnRefreshLoadMoreListener, OnRefreshListener, OnItemClickListener, OnItemLongClickListener {

    private RecyclerAdapter recyclerAdapter;

    private androidx.recyclerview.widget.RecyclerView mainRv;
    private FoodPresenter foodPresenter;
    private int page = 1,count = 20;
    private com.scwang.smart.refresh.layout.SmartRefreshLayout mainRefresh;

    private boolean isRefresh = false;
    private List<FoodEntity.DataBean> dataBeans;

    @Override
    public void initView() {
        foodPresenter = new FoodPresenter(this,new FoodModel());
        mainRv = findViewById(R.id.main_rv);
        mainRv.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        mainRefresh = findViewById(R.id.main_refresh);
        mainRefresh.setOnRefreshListener(this);
        mainRefresh.setOnLoadMoreListener(this);
    }

    @Override
    public void initData() {
        foodPresenter.getFood("dish_list.php?stage_id=1&limit="+count+"&page="+page);
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }


    @Override
    public void showFood(List<FoodEntity.DataBean> list) {
        initAdapter(list);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        isRefresh = false;
        ++page;
        foodPresenter.getFood("dish_list.php?stage_id=1&limit="+count+"&page="+page);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        isRefresh = true;
        page = 1;
        foodPresenter.getFood("dish_list.php?stage_id=1&limit="+count+"&page="+page);
    }

    private void initAdapter(List<FoodEntity.DataBean> list) {



        if (recyclerAdapter==null){
            recyclerAdapter = new RecyclerAdapter(list);
            recyclerAdapter.setOnItemClickListener(this);
            recyclerAdapter.setOnItemLongClickListener(this);
            mainRv.setAdapter(recyclerAdapter);
        }else {
            if (isRefresh){
                recyclerAdapter.getData().clear();
                recyclerAdapter.getData().addAll(list);
            }else {
                recyclerAdapter.getData().addAll(list);
            }

            recyclerAdapter.notifyDataSetChanged();
        }
        dataBeans = recyclerAdapter.getData();
        mainRefresh.finishRefresh();
        mainRefresh.finishLoadMore();

    }

    @Override
    public void onItemClick(@NonNull @NotNull BaseQuickAdapter<?, ?> adapter, @NonNull @NotNull View view, int position) {
        //使用ARouter跳转并传值
        ARouter.getInstance().build("/test/details")
                .withSerializable("object",dataBeans.get(position))
                .navigation();
    }

    @Override
    public boolean onItemLongClick(@NonNull @NotNull BaseQuickAdapter adapter, @NonNull @NotNull View view, int position) {
        dataBeans.remove(position);
        recyclerAdapter.notifyItemRemoved(position);
        showToast("删除成功");
        return true;
    }
}