package com.example.wen.wenbook.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.wen.wenbook.R;


/**
 * Created by wen on 2017/4/13.
 */

public class GankDateAdapter extends BaseQuickAdapter<String,BaseViewHolder> {
    public GankDateAdapter() {
        super(R.layout.item_gank_date);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.gank_date,item);
    }
}
