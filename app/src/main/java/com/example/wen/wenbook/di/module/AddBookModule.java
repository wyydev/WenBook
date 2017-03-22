package com.example.wen.wenbook.di.module;

import com.example.wen.wenbook.data.http.ApiService;
import com.example.wen.wenbook.data.model.AddBookModel;
import com.example.wen.wenbook.data.model.SearchModel;
import com.example.wen.wenbook.presenter.contract.AddBookContract;
import com.example.wen.wenbook.presenter.contract.SearchBookContract;
import com.example.wen.wenbook.presenter.impl.AddBookPresenter;
import com.example.wen.wenbook.presenter.impl.SearchBookPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by wen on 2017/3/21.
 */

@Module
public class AddBookModule {

    private AddBookContract.AddBookView mAddBookView;

    public AddBookModule(AddBookContract.AddBookView addBookView){
        this.mAddBookView = addBookView;
    }

    @Provides
    public AddBookPresenter provideAddBookPresenter(AddBookContract.IAddBookModel iAddBookModel, AddBookContract.AddBookView addBookView){
        return new AddBookPresenter(iAddBookModel,addBookView);
    }

    @Provides
    public AddBookContract.AddBookView provideAddBookView(){
        return mAddBookView;
    }

    @Provides
    public AddBookContract.IAddBookModel provideAddBookModel( ApiService apiService){
        return new AddBookModel(apiService);
    }
}
