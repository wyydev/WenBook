package com.example.wen.wenbook.presenter.contract;

import com.example.wen.wenbook.bean.GankDate;
import com.example.wen.wenbook.bean.TodayGank;
import com.example.wen.wenbook.presenter.BaseView;

import io.reactivex.Observable;

/**
 * Created by wen on 2017/4/12.
 */

public interface TodayGankContract {
    public interface ITodayGankModel{
        public Observable<TodayGank> todayGank(String year, String month, String day);
        public Observable<GankDate> gankDate();
    }

    public interface TodayGankView extends BaseView{
        public void showResult(TodayGank todayGank);
        public void showGankDate(GankDate gankDate);
    }
}
