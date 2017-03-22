package com.example.wen.wenbook.ui.adapter;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.wen.wenbook.R;
import com.example.wen.wenbook.bean.Book;
import com.example.wen.wenbook.common.font.WenFont;
import com.mikepenz.iconics.IconicsDrawable;


/**
 * Created by wen on 2017/3/21.
 */

public class BookGridAdapter extends BaseQuickAdapter<Book,BaseViewHolder> {

    public BookGridAdapter() {
        super(R.layout.fragment_book_grid_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, Book item) {

        // 设置图片
        ImageView ivCover = helper.getView(R.id.iv_cover);
        Glide.with(ivCover.getContext())
                .load(item.getImage())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(new IconicsDrawable(ivCover.getContext()).icon(WenFont.Icon.wen_book).color(Color.GRAY).paddingDp(10))
                .into(ivCover);

        // 设置其他

        RatingBar ratingBar =  helper.getView(R.id.rb_rate);
        ratingBar.setRating((Float.parseFloat(item.getAverage())/2));

        helper.setText(R.id.tv_rate,item.getAverage())
                .setText(R.id.tv_title,item.getTitle());

    }

    @Override
    public long getItemId(int position) {
        return this.mData.get(position).getId();
    }
}
