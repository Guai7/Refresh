package com.study.mvp_zk3_210802.mvp.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.listener.OnItemLongClickListener;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.constant.SpinnerStyle;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;
import com.study.mvp_zk3_210802.R;
import com.study.mvp_zk3_210802.mvp.adapter.RecyclerAdapter;
import com.study.mvp_zk3_210802.mvp.contract.IContract;
import com.study.mvp_zk3_210802.mvp.entity.FoodEntity;
import com.study.mvp_zk3_210802.mvp.model.FoodModel;
import com.study.mvp_zk3_210802.mvp.presenter.FoodPresenter;
import com.study.mvp_zk3_210802.mvp.sqlData.SQLDataBase;
import com.study.mybase.view.BaseActivity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
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
    //是否用于刷新
    private boolean isRefresh = false;
    //整合的数据（当前适配器内可视化数据）
    private List<FoodEntity.DataBean> dataBeans;
    //刷新控件
    private com.scwang.smart.refresh.layout.SmartRefreshLayout mainRefresh;
    //刷新头
    private com.scwang.smart.refresh.header.ClassicsHeader mainRefreshHead;
    //刷新尾
    private com.scwang.smart.refresh.footer.ClassicsFooter mainRefreshFoot;
    //自定义弹窗
    private PopupWindow window;
    //数据库对象
    private SQLDataBase sqlDataBase;

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

        mainRefreshHead = findViewById(R.id.main_refresh_head);
        initRefresh();
    }

    /**
     * 设置刷新视图
     */
    private void initRefresh() {
        mainRefreshHead.setAccentColor(Color.WHITE);//设置强调颜色
        mainRefreshHead.setPrimaryColor(Color.GRAY);//设置主题颜色
        mainRefreshHead.setTextSizeTitle(16);//设置标题文字大小（sp单位）
        mainRefreshHead.setTextSizeTime(20);//设置时间文字大小（sp单位）
        mainRefreshHead.setTextTimeMarginTop(10);//设置时间文字的上边距（dp单位）
        mainRefreshHead.setEnableLastTime(false);//是否显示时间
        mainRefreshHead.setFinishDuration(500);//设置刷新完成显示的停留时间（设为0可以关闭停留功能）
        mainRefreshHead.setDrawableSize(20);//同时设置箭头和图片的大小（dp单位）
        mainRefreshHead.setDrawableMarginRight(20);//设置图片和箭头和文字的间距（dp单位）
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.jiantou);
        mainRefreshHead.setArrowBitmap(bitmap);//设置箭头位图（1.1.0版本删除）
        mainRefreshHead.setProgressBitmap(bitmap);//设置图片位图（1.1.0版本删除）
        mainRefreshHead.setSpinnerStyle(SpinnerStyle.FixedBehind);//设置移动样式（不支持：MatchLayout）

        mainRefreshFoot = findViewById(R.id.main_refresh_foot);

        mainRefreshFoot.setAccentColor(Color.WHITE);//设置强调颜色
        mainRefreshFoot.setPrimaryColor(Color.GRAY);//设置主题颜色
        mainRefreshFoot.setTextSizeTitle(16);//设置标题文字大小（sp单位）
        mainRefreshFoot.setFinishDuration(500);//设置刷新完成显示的停留时间（设为0可以关闭停留功能）
        mainRefreshFoot.setDrawableSize(20);//同时设置箭头和图片的大小（dp单位）
        mainRefreshFoot.setDrawableMarginRight(20);//设置图片和箭头和文字的间距（dp单位）
        mainRefreshFoot.setArrowBitmap(bitmap);//设置箭头位图（1.1.0版本删除）
        mainRefreshFoot.setProgressBitmap(bitmap);//设置图片位图（1.1.0版本删除）
        mainRefreshFoot.setSpinnerStyle(SpinnerStyle.FixedBehind);//设置移动样式（不支持：MatchLayout）
    }

    /**
     * 开始使用P层的getFood方法获取数据
     */
    @Override
    public void initData() {

        //获取sp
        SharedPreferences login = getSharedPreferences("login", MODE_PRIVATE);
        //获取值           获取布尔类型                      默认值
        boolean one = login.getBoolean("one", true);

        //判断是否第一次运行
        if (one){
            //第一次运行，网络获取数据
            Toast.makeText(this, "第一次运行，网络获取数据", Toast.LENGTH_SHORT).show();
            foodPresenter.getFood("dish_list.php?stage_id=1&limit="+count+"&page="+page);

            //获取sp的编写模式
            SharedPreferences.Editor edit = login.edit();
            //设置第一次运行值为 否
            edit.putBoolean("one", false);
            //编译应用（提交效果一样）
            edit.apply();

        }else {
            //非第一次运行，数据库加载数据

            //new一个总体适配器内数据源
            dataBeans = new ArrayList<>();

            Toast.makeText(this, "非第一次运行，数据库加载数据", Toast.LENGTH_SHORT).show();
            //判断数据库是否为空 并new
            if (sqlDataBase==null){
                sqlDataBase = new SQLDataBase(this,"food.db",null,1);
            }

            //获取写入对象
            SQLiteDatabase db = sqlDataBase.getWritableDatabase();
            //查询所有
            Cursor foods = db.query("foods", null, null, null, null, null, null);
            while (foods.moveToNext()){
                String title = foods.getString(foods.getColumnIndex("title"));
                String pic = foods.getString(foods.getColumnIndex("pic"));

                FoodEntity.DataBean foodEntity = new FoodEntity.DataBean(title,pic);
                dataBeans.add(foodEntity);
            }

            //关闭引用
            foods.close();
            db.close();

            //最后
            recyclerAdapter = new RecyclerAdapter(dataBeans);
            recyclerAdapter.setOnItemClickListener(this);
            recyclerAdapter.setOnItemLongClickListener(this);
            mainRv.setAdapter(recyclerAdapter);
        }

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

        if (getSharedPreferences("login",MODE_PRIVATE).getBoolean("one",true)){
            //把是否刷新改为否
            isRefresh = false;
            //页码+1
            ++page;
            //并获取数据
            foodPresenter.getFood("dish_list.php?stage_id=1&limit="+count+"&page="+page);
        }else {
           mainRefreshFoot.setNoMoreData(true);
        }
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

            addDataBase(list);

        }else {
            //不是的话 判断是否为刷新
            if (isRefresh) {
                //是刷新就清空适配器
                recyclerAdapter.getData().clear();
            }else {
                addDataBase(list);
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

    private void addDataBase(List<FoodEntity.DataBean> list) {
        //将加载的数据存入到数据库
        sqlDataBase = new SQLDataBase(this,"food.db",null,1);
        //获取写入对象
        SQLiteDatabase db = sqlDataBase.getWritableDatabase();
        ContentValues values = new ContentValues();
        for (int i = 0; i < list.size(); i++) {
            values.put("title",list.get(i).getTitle());
            values.put("pic",list.get(i).getPic());
            db.insert("foods",null,values);
            //清空（防止重复new对象）
            values.clear();
        }

        //关闭引用
        db.close();
        showToast("添加数据库成功");
    }


    /**
     * 条目点击事件
     * @param adapter
     * @param view
     * @param position
     */
    @Override
    public void onItemClick(@NonNull @NotNull BaseQuickAdapter adapter, @NonNull @NotNull View view, int position) {
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
        initPopupWindow();

        //创建popupWindow
        window.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

        View inflate = LayoutInflater.from(this).inflate(R.layout.popap_window, null);
        window.setContentView(inflate);

        /**
         * 遮罩效果
         */
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.alpha = 0.5f;
        getWindow().setAttributes(attributes);

        /**
         * 确认删除
         */
        inflate.findViewById(R.id.window_delete_yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dataBeans.remove(position);
                recyclerAdapter.notifyItemRemoved(position);
                showToast("删除成功");

                window.dismiss();
            }
        });

        /**
         * 取消
         */
        inflate.findViewById(R.id.window_delete_no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                window.dismiss();
                return;
            }
        });

        //恢复透明度
        window.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams attributes = getWindow().getAttributes();
                attributes.alpha = 1.0f;
                getWindow().setAttributes(attributes);
            }
        });

        //点击外部取消
        window.setOutsideTouchable(true);

        //设置显示位置
        View thisActivity = LayoutInflater.from(this).inflate(R.layout.activity_main, null);
        window.showAtLocation(thisActivity, Gravity.CENTER,0,0);

        return true;
    }

    //初始化popup
    private void initPopupWindow() {
        if (window == null) {
            window = new PopupWindow();
        }
    }

}