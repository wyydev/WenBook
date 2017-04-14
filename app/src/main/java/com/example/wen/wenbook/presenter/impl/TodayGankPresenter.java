package com.example.wen.wenbook.presenter.impl;

import com.example.wen.wenbook.bean.GankDate;
import com.example.wen.wenbook.bean.TodayGank;
import com.example.wen.wenbook.common.rx.subscriber.ProgressDialogSubscriber;
import com.example.wen.wenbook.common.util.ACache;
import com.example.wen.wenbook.presenter.BasePresenter;
import com.example.wen.wenbook.presenter.contract.TodayGankContract;
import com.google.gson.Gson;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by wen on 2017/4/12.
 */

public class TodayGankPresenter extends BasePresenter<TodayGankContract.ITodayGankModel,TodayGankContract.TodayGankView> {


    private static final String TEMP_GANK_DATA = "temp_gank_data";

    private ACache mACache;

    @Inject
    public TodayGankPresenter(TodayGankContract.ITodayGankModel model, TodayGankContract.TodayGankView view) {
        super(model, view);

    }

    public void getTodayGank(String year, String month, String day){

        mACache = ACache.get(mContext);

            mModel.todayGank(year,month,day).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new ProgressDialogSubscriber<TodayGank>(mContext,mView) {
                @Override
                public void onNext(TodayGank value) {

                    if (value != null){
                        mACache.put(TEMP_GANK_DATA,new Gson().toJson(value));
                        mView.showResult(value);
                    }else {
                        mView.showResult(new Gson().fromJson( mACache.getAsString(TEMP_GANK_DATA),TodayGank.class));
                    }
                }
            });
        }


    public void gankDate(){
        mModel.gankDate().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Observer<GankDate>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(GankDate value) {
                if (value != null){
                    mView.showGankDate(value);
                }
            }

            @Override
            public void onError(Throwable e) {
                mView.showError(e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });
    }

}
