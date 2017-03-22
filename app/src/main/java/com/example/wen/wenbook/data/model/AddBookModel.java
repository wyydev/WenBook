package com.example.wen.wenbook.data.model;

import com.example.wen.wenbook.bean.DoubanBook;
import com.example.wen.wenbook.data.http.ApiService;
import com.example.wen.wenbook.presenter.contract.AddBookContract;

import io.reactivex.Observable;

/**
 * Created by wen on 2017/3/22.
 */

public class AddBookModel implements AddBookContract.IAddBookModel {

    private ApiService mApiService;

    public AddBookModel(ApiService apiService) {
        mApiService = apiService;
    }

    @Override
    public Observable<DoubanBook> searchBookFromIsbn(String isbn) {
        return mApiService.searchBookFromIsbn(isbn);
    }
}
