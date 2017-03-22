package com.example.wen.wenbook.ui.adapter;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.wen.wenbook.R;
import com.example.wen.wenbook.bean.Book;
import com.example.wen.wenbook.common.font.WenFont;
import com.mikepenz.iconics.IconicsDrawable;


/**
 * Created by wen on 2017/3/22.
 */

public class BookSearchAdapter extends BaseQuickAdapter<Book,BaseViewHolder>{

    public BookSearchAdapter() {
        super(R.layout.activity_search_book_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, Book item) {
       ImageView bookImage =  helper.getView(R.id.book_item_image);
        // 设置图片
        Glide.with(bookImage.getContext())
                .load(item.getImage())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(new IconicsDrawable(bookImage.getContext()).icon(WenFont.Icon.wen_book).colorRes(R.color.boo_item_icon).paddingDp(10))
                .into(bookImage);

        // 设置其他

        helper.setText(R.id.book_item_title,item.getTitle())
                .setText(R.id.book_item_points,item.getAverage())
                .setText(R.id.book_item_author,item.getAuthor())
                .setText(R.id.book_item_publisher,item.getPublisher())
                .setText(R.id.book_item_pubdate,item.getPubdate())
                .setText(R.id.book_item_pubdate,item.getPubdate())
                .setText(R.id.book_item_price,item.getPrice());


        // 设置翻译者
        if (item.getTranslator().isEmpty()) {
            helper.getView(R.id.book_item_divider).setVisibility(View.GONE);
            helper.setText(R.id.book_item_translator,"");
        } else {
            helper.setText(R.id.book_item_translator,item.getTranslator() + " 译");
        }
    }
}
