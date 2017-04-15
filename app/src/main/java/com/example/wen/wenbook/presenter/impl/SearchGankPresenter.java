package com.example.wen.wenbook.presenter.impl;

import com.example.wen.wenbook.bean.SearchGankBean;
import com.example.wen.wenbook.presenter.BasePresenter;
import com.example.wen.wenbook.presenter.contract.SearchGankContract;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by wen on 2017/4/15.
 */

public class SearchGankPresenter extends BasePresenter<SearchGankContract.ISearchGankModel,SearchGankContract.SearchGankView> {


    @Inject
    public SearchGankPresenter(SearchGankContract.ISearchGankModel model, SearchGankContract.SearchGankView view) {
        super(model, view);
    }


    /**
     * @param searchContent 搜索内容
     * @param count 一次返回几条数据
     * @param page 第几页数据
     */
    public void getSearchGank(String searchContent, String count, final int page){


        mModel.getSearchGank(searchContent,count,page+"").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SearchGankBean>() {
                               @Override
                               public void onSubscribe(Disposable d) {
                                   if (page == 1)
                                       mView.showLoading();
                               }

                               @Override
                               public void onNext(SearchGankBean value) {
                                   if (page == 1)
                                       mView.dismissLoading();

                                   boolean isLast = false;
                                   if (value != null ){

                                       if (value.getResults().size() == 0){

                                           isLast = true;

                                       }

                                       mView.showSearchGank(value.getResults(),isLast);
                                   }
                               }

                               @Override
                               public void onError(Throwable e) {
                                   if (page == 1)
                                       mView.dismissLoading();

                                   mView.showError(e.getMessage());
                               }

                               @Override
                               public void onComplete() {
                                   if (page == 1)
                                       mView.dismissLoading();

                                   if (page > 1){
                                       mView.onLoadMoreComplete();
                                   }


                               }
                           }
                );
    }


}
