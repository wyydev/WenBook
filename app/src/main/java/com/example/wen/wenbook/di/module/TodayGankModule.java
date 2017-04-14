package com.example.wen.wenbook.di.module;

import com.example.wen.wenbook.data.http.ApiService;
import com.example.wen.wenbook.data.model.TodayGankModel;
import com.example.wen.wenbook.presenter.contract.TodayGankContract;
import com.example.wen.wenbook.presenter.impl.TodayGankPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by wen on 2017/4/12.
 */

@Module
public class TodayGankModule {

    private TodayGankContract.TodayGankView mTodayGankView;

    public TodayGankModule(TodayGankContract.TodayGankView todayGankView){
        this.mTodayGankView = todayGankView;
    }

    @Provides
    public TodayGankPresenter provideTodayGankPresenter(TodayGankContract.ITodayGankModel model, TodayGankContract.TodayGankView view){
        return new TodayGankPresenter(model,view);
    }

    @Provides
    public TodayGankContract.TodayGankView provideTodayGankView(){
        return mTodayGankView;
    }

    @Provides
    public TodayGankContract.ITodayGankModel provideITodayGankModel( ApiService apiService){
        return new TodayGankModel(apiService);
    }
}
