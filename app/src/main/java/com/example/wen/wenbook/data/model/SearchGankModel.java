package com.example.wen.wenbook.data.model;

import com.example.wen.wenbook.bean.SearchGankBean;
import com.example.wen.wenbook.data.http.ApiService;
import com.example.wen.wenbook.presenter.contract.SearchGankContract;

import io.reactivex.Observable;

/**
 * Created by wen on 2017/4/15.
 */

public class SearchGankModel implements SearchGankContract.ISearchGankModel {

    private ApiService mApiService;

    public SearchGankModel(ApiService apiService) {
        mApiService = apiService;
    }

    @Override
    public Observable<SearchGankBean> getSearchGank(String searchContent, String count, String page) {
        return mApiService.getSearchGank(searchContent,count,page);
    }
}
