package com.example.wen.wenbook.di.module;

import com.example.wen.wenbook.data.http.ApiService;
import com.example.wen.wenbook.data.model.SearchGankModel;
import com.example.wen.wenbook.presenter.contract.SearchGankContract;
import com.example.wen.wenbook.presenter.impl.SearchGankPresenter;


import dagger.Module;
import dagger.Provides;

/**
 * Created by wen on 2017/4/12.
 */

@Module
public class SearchGankModule {

    private SearchGankContract.SearchGankView mSearchGankView;

    public SearchGankModule(SearchGankContract.SearchGankView searchGankView){
        this.mSearchGankView = searchGankView;
    }

    @Provides
    public SearchGankPresenter provideSearchGankPresenter(SearchGankContract.ISearchGankModel model, SearchGankContract.SearchGankView view){
        return new SearchGankPresenter(model,view);
    }

    @Provides
    public SearchGankContract.SearchGankView provideSearchGankView(){
        return mSearchGankView;
    }

    @Provides
    public SearchGankContract.ISearchGankModel provideISearchGankModel( ApiService apiService){
        return new SearchGankModel(apiService);
    }
}
