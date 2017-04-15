package com.example.wen.wenbook.ui.adapter;

import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.wen.wenbook.R;
import com.example.wen.wenbook.bean.SearchGank;


/**
 * Created by wen on 2017/4/15.
 */

public class SearchGankAdapter extends BaseQuickAdapter<SearchGank,BaseViewHolder> {
    public SearchGankAdapter() {
        super(R.layout.item_search_gank);
    }

    @Override
    protected void convert(BaseViewHolder helper, SearchGank item) {

        String publish_at = "";
        String[] date = item.getPublishedAt().split("T");
        if (date.length > 0 && !TextUtils.isEmpty(date[0])){
            publish_at = date[0];
        }

        helper.setText(R.id.tv_desc,item.getDesc())
                .setText(R.id.tv_who,item.getWho())
                .setText(R.id.tv_type,item.getType())
                .setText(R.id.tv_publish_at,publish_at);

    }
}
