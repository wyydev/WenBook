package com.example.wen.wenbook.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.wen.wenbook.R;
import com.example.wen.wenbook.bean.Book;


/**
 * Created by wen on 2017/3/21.
 */

public class BookGridAdapter extends BaseQuickAdapter<Book,BaseViewHolder> {

    public BookGridAdapter() {
        super(R.layout.fragment_book_grid_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, Book item) {

    }

    @Override
    public long getItemId(int position) {
        return this.mData.get(position).getId();
    }
}
