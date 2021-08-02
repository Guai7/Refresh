package com.study.mvp_zk3_210802.mvp.adapter;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.study.mvp_zk3_210802.R;
import com.study.mvp_zk3_210802.mvp.entity.FoodEntity;
import com.study.mvp_zk3_210802.mvp.utils.GlideUtils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public
class RecyclerAdapter extends BaseMultiItemQuickAdapter<FoodEntity.DataBean, BaseViewHolder> {

    public RecyclerAdapter(@Nullable List<FoodEntity.DataBean> data) {
        super(data);
        addItemType(0, R.layout.item_layout);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, FoodEntity.DataBean dataBean) {
        baseViewHolder.setText(R.id.item_text,dataBean.getTitle());
        GlideUtils.getInstance().glide(getContext(),dataBean.getPic(),baseViewHolder.getView(R.id.item_img));
    }
}
