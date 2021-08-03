package com.study.mvp_zk3_210802.mvp.utils;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.transition.Transition;
import android.transition.TransitionValues;
import android.widget.ImageView;

import com.blankj.utilcode.util.NetworkUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
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
        //判断网络是否可用
        if (NetworkUtils.isAvailableByPing()){
            Glide.with(context)
                    .load(url)
//                    .skipMemoryCache(true)      //禁用内存缓存
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)  //缓存方式： 自适应
                    .error(R.drawable.ic_launcher_background)
//                    .placeholder(R.drawable.ic_launcher_background)
                    .transform(new RoundedCorners(50))
                    .into(view);
        }else {
            Glide.with(context)
                    .load(url)
                    .onlyRetrieveFromCache(true)    //仅从缓存内加载图片
                    .error(R.drawable.ic_launcher_background)
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(view);
        }
    }


//    /**
//     * 加载图片(毛玻璃效果)
//     * @param context
//     * @param url             //没做出来
//     * @param view
//     */
//    public void glideM(Context context, String url, ImageView view){
//
//        Glide.with(context)
//                    .load(url)
//                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
//                    .error(R.drawable.ic_launcher_background)
//                    .placeholder(R.drawable.ic_launcher_background)
//                    .into(view);
//
//    }


}
