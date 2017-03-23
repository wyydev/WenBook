package com.example.wen.wenbook.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.wen.wenbook.R;
import com.example.wen.wenbook.bean.Book;
import com.example.wen.wenbook.common.font.WenFont;
import com.example.wen.wenbook.ui.activity.BookInfoActivity;
import com.example.wen.wenbook.ui.activity.BookInfoAddActivity;
import com.example.wen.wenbook.ui.activity.BookInfoAddActivity2;
import com.mikepenz.iconics.IconicsDrawable;

import org.litepal.crud.DataSupport;

import java.util.List;


/**
 * Created by wen on 2017/3/22.
 */

public class BookSearchAdapter extends BaseQuickAdapter<Book,BaseViewHolder>{

    private Context mContext;

    public BookSearchAdapter(Context context) {
        super(R.layout.activity_search_book_item);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, final Book item) {
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

        //CardView点击事件
        helper.getView(R.id.book_item).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //到数据库查询是否存在该图书
                List<Book> books = DataSupport.where("isbn13 = ?", item.getIsbn13()).find(Book.class);

                // 如果图书已添加
                if (books.size() > 0) {
                    Intent intent = new Intent(mContext, BookInfoActivity.class);
                    intent.putExtra("id", books.get(0).getId());
                    mContext.startActivity(intent);
                } else {
                    // 图书未添加，跳转到添加页面
                    Intent intent = new Intent(mContext, BookInfoAddActivity2.class);
                    intent.putExtra("ISBN", item.getIsbn13());
                    mContext.startActivity(intent);
                }
            }
        });
    }
}
