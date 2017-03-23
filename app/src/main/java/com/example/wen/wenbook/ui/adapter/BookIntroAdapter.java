package com.example.wen.wenbook.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.wen.wenbook.R;
import com.example.wen.wenbook.bean.TagItem;

/**
 * Created by wen on 2017/3/23.
 */

public class BookIntroAdapter extends BaseQuickAdapter<TagItem,BaseViewHolder> {
    public BookIntroAdapter() {
        super(R.layout.fragment_book_intro_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, TagItem item) {
        helper.setText(R.id.tag,item.getTag())
                .setText(R.id.content,item.getContent());
    }
}
