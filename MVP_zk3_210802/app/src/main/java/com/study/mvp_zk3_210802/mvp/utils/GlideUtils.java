package com.study.mvp_zk3_210802.mvp.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.study.mvp_zk3_210802.R;

/**
 * Glide工具类
 */
public
class GlideUtils {

    private static GlideUtils utils;

    /**
     * 饿汉式单例
     * @return
     */
    public static GlideUtils getInstance() {
        if (utils==null){
            utils = new GlideUtils();
        }
        return utils;
    }

    /**
     * 加载图片
     * @param context
     * @param url
     * @param view
     */
    public void glide(Context context, String url, ImageView view){
        Glide.with(context)
                .load(url)
                .error(R.drawable.ic_launcher_background)
                .placeholder(R.drawable.ic_launcher_background)
                .transform(new RoundedCorners(50))
                .into(view);
    }
}
