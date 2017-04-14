package com.example.wen.wenbook.data.model;


import com.example.wen.wenbook.bean.GankDate;
import com.example.wen.wenbook.bean.TodayGank;
import com.example.wen.wenbook.data.http.ApiService;
import com.example.wen.wenbook.presenter.contract.TodayGankContract;

import io.reactivex.Observable;

/**
 * Created by wen on 2017/4/12.
 */

public class TodayGankModel implements TodayGankContract.ITodayGankModel {

    private ApiService mApiService;

    public TodayGankModel(ApiService apiService) {
        mApiService = apiService;
    }

    @Override
    public Observable<TodayGank> todayGank(String year, String month, String day) {
        return mApiService.todayGank(year,month,day);
    }

    @Override
    public Observable<GankDate> gankDate() {
        return mApiService.gankDate();
    }


}
