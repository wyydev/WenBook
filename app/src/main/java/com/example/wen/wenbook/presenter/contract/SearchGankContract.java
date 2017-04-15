package com.example.wen.wenbook.presenter.contract;

import com.example.wen.wenbook.bean.SearchGank;
import com.example.wen.wenbook.bean.SearchGankBean;
import com.example.wen.wenbook.presenter.BaseView;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by wen on 2017/4/15.
 */

public interface SearchGankContract {
    public interface ISearchGankModel{
        public Observable<SearchGankBean> getSearchGank(String searchContent, String count, String page);
    }

    public interface SearchGankView extends BaseView{
        public void showSearchGank(List<SearchGank> searchGankList,boolean isLast);
        public void onLoadMoreComplete();
    }
}
