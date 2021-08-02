package com.study.mvp_zk3_210802.mvp.view;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursorDriver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQuery;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.study.mvp_zk3_210802.R;
import com.study.mvp_zk3_210802.mvp.entity.FoodEntity;
import com.study.mvp_zk3_210802.mvp.utils.GlideUtils;
import com.study.mybase.view.BaseActivity;

import java.io.Serializable;

@Route(path = "/test/details")
public
class DetailsActivity extends BaseActivity {
    private android.widget.ImageView detailsImg;
    private android.widget.TextView detailsText1;
    private android.widget.TextView detailsText2;
    private android.widget.Button returnBtn;

    @Override
    public void initView() {
        ARouter.getInstance().inject(this);
        detailsImg = findViewById(R.id.details_img);
        detailsText1 = findViewById(R.id.details_text1);
        detailsText2 = findViewById(R.id.details_text2);
        returnBtn = findViewById(R.id.return_btn);
    }

    @Override
    public void initData() {
        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //获取数据
        Intent intent = getIntent();
        FoodEntity.DataBean dataBean = (FoodEntity.DataBean) intent.getSerializableExtra("object");
        //赋值
        GlideUtils.getInstance().glide(this,dataBean.getPic(),detailsImg);
        detailsText1.setText(dataBean.getTitle());
        detailsText2.setText(dataBean.getFood_str());
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_details;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
