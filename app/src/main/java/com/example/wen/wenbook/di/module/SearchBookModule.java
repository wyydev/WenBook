package com.example.wen.wenbook.di.module;

import android.app.Application;

import com.example.wen.wenbook.data.http.ApiService;
import com.example.wen.wenbook.data.model.LoginModel;
import com.example.wen.wenbook.data.model.SearchModel;
import com.example.wen.wenbook.presenter.contract.LonginContract;
import com.example.wen.wenbook.presenter.contract.SearchBookContract;
import com.example.wen.wenbook.presenter.impl.LoginPresenter;
import com.example.wen.wenbook.presenter.impl.SearchBookPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by wen on 2017/3/21.
 */

@Module
public class SearchBookModule {

    private SearchBookContract.SearchView mSearchView;

    public SearchBookModule(SearchBookContract.SearchView searchView){
        this.mSearchView = searchView;
    }

    @Provides
    public SearchBookPresenter provideSearchBookPresenter(SearchBookContract.ISearchBookModel iSearchBookModel, SearchBookContract.SearchView searchView){
        return new SearchBookPresenter(iSearchBookModel,searchView);
    }

    @Provides
    public SearchBookContract.SearchView provideSearchView(){
        return mSearchView;
    }

    @Provides
    public SearchBookContract.ISearchBookModel provideSearchModel( ApiService apiService){
        return new SearchModel(apiService);
    }
}
