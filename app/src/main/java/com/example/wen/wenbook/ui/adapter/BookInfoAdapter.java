package com.example.wen.wenbook.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.wen.wenbook.R;
import com.example.wen.wenbook.bean.TagItem;

import java.util.List;

/**
 * Created by wen on 2017/3/22.
 */

public class BookInfoAdapter extends BaseQuickAdapter<TagItem,BaseViewHolder> {
    public BookInfoAdapter(List<TagItem> data) {
        super(R.layout.fragment_book_info_item,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TagItem item) {

        helper.setText(R.id.tag,item.getTag())
        .setText(R.id.content,item.getContent());

    }
}
