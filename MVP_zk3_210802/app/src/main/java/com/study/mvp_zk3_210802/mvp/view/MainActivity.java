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

    //Recycler的适配器（用于适配数据）
    private RecyclerAdapter recyclerAdapter;

    //RecyclerView展示数据控件
    private androidx.recyclerview.widget.RecyclerView mainRv;
    //P层
    private FoodPresenter foodPresenter;

    //页码和数量（用于分页 改变Url中的参数）
    private int page = 1,count = 20;
    //刷新控件
    private com.scwang.smart.refresh.layout.SmartRefreshLayout mainRefresh;

    //是否用于刷新
    private boolean isRefresh = false;
    //整合的数据
    private List<FoodEntity.DataBean> dataBeans;

    /**
     * 初始化控件和一些全局变量对象
     */
    @Override
    public void initView() {
        foodPresenter = new FoodPresenter(this,new FoodModel());
        mainRv = findViewById(R.id.main_rv);
        mainRv.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        mainRefresh = findViewById(R.id.main_refresh);
        mainRefresh.setOnRefreshListener(this);
        mainRefresh.setOnLoadMoreListener(this);
    }

    /**
     * 开始使用P层的getFood方法获取数据
     */
    @Override
    public void initData() {
        foodPresenter.getFood("dish_list.php?stage_id=1&limit="+count+"&page="+page);
    }

    //绑定主视图（必写）
    @Override
    public int bindLayout() {
        return R.layout.activity_main;
    }

    /**
     * 由于没写进度条 所以空闲
     */
    @Override
    public void showLoading() {

    }

    /**
     * 由于没写进度条 所以空闲
     */
    @Override
    public void hideLoading() {

    }


    /**
     * 显示数据（回调方法）
     * @param list 展示的数据集合
     */
    @Override
    public void showFood(List<FoodEntity.DataBean> list) {
        //调用初始化适配器方法
        initAdapter(list);
    }

    /**
     * 上拉加载更多
     * @param refreshLayout
     */
    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        //把是否刷新改为否
        isRefresh = false;
        //页码+1
        ++page;
        //并获取数据
        foodPresenter.getFood("dish_list.php?stage_id=1&limit="+count+"&page="+page);
    }

    /**
     * 下拉刷新
     * @param refreshLayout
     */
    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        //把是否刷新改为是
        isRefresh = true;
        //页码=1（重新开头）
        page = 1;
        //获取数据
        foodPresenter.getFood("dish_list.php?stage_id=1&limit="+count+"&page="+page);
    }

    /**
     * 初始化适配器
     * @param list 回调回来的数据
     */
    private void initAdapter(List<FoodEntity.DataBean> list) {
        //判断适配器是否为空（是否是刚运行加载的数据）
        if (recyclerAdapter==null){
            //是的话就初始化 并设置点击事件和适配
            recyclerAdapter = new RecyclerAdapter(list);
            recyclerAdapter.setOnItemClickListener(this);
            recyclerAdapter.setOnItemLongClickListener(this);
            mainRv.setAdapter(recyclerAdapter);
        }else {
            //不是的话 判断是否为刷新
            if (isRefresh) {
                //是刷新就清空适配器
                recyclerAdapter.getData().clear();
            }
            //然后正常添加适配数据 并刷新数据（不管是不是刷新 都会执行）
            recyclerAdapter.getData().addAll(list);
            recyclerAdapter.notifyDataSetChanged();
        }

        //最后将整合全局数据 改为 设配器其中的数据
        dataBeans = recyclerAdapter.getData();

        //结束刷新加载动画
        mainRefresh.finishRefresh();
        mainRefresh.finishLoadMore();

    }

    /**
     * 条目点击事件
     * @param adapter
     * @param view
     * @param position
     */
    @Override
    public void onItemClick(@NonNull @NotNull BaseQuickAdapter<?, ?> adapter, @NonNull @NotNull View view, int position) {
        //使用ARouter跳转并传值（跳转到详情页）
        ARouter.getInstance().build("/test/details")
                .withSerializable("object",dataBeans.get(position))
                .navigation();
    }

    /**
     * 条目长按点击事件
     * @param adapter
     * @param view
     * @param position
     * @return
     */
    @Override
    public boolean onItemLongClick(@NonNull @NotNull BaseQuickAdapter adapter, @NonNull @NotNull View view, int position) {
        dataBeans.remove(position);
        recyclerAdapter.notifyItemRemoved(position);
        showToast("删除成功");
        return true;
    }
}