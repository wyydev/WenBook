package com.example.wen.wenbook.common.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.wen.wenbook.R;
import com.example.wen.wenbook.common.font.WenFont;
import com.mikepenz.iconics.IconicsDrawable;

/**
 * Created by wen on 2017/4/13.
 */

public class ImageLoder {
    public static void loadImage(Context context, String imageUrl, ImageView imageView){
        Glide.with(context).load(imageUrl).placeholder(R.mipmap.ic_launcher)
                .error(new IconicsDrawable(context, WenFont.Icon.wen_smile).colorRes(R.color.colorPrimary))
                .into(imageView);
    }
}
