package com.example.wen.wenbook.data.model;

import com.example.wen.wenbook.bean.Book;
import com.example.wen.wenbook.bean.DoubanBook;
import com.example.wen.wenbook.bean.DoubanBookBean;
import com.example.wen.wenbook.data.http.ApiService;
import com.example.wen.wenbook.presenter.contract.SearchBookContract;

import org.litepal.crud.DataSupport;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import io.reactivex.Observable;

/**
 * Created by wen on 2017/3/22.
 */

public class SearchModel implements SearchBookContract.ISearchBookModel {

    private ApiService mApiService;

    public SearchModel(ApiService apiService) {
       this.mApiService = apiService;
    }

    @Override
    public Observable<DoubanBookBean> searchOnline(String bookName) {

        // URLencode
        try {
            bookName = URLEncoder.encode(bookName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return mApiService.searchBook(bookName);
    }

    @Override
    public Observable<List<Book>> searchLocal(String bookName) {
        List<Book> books = DataSupport.where("title like ?", "%" + bookName + "%").find(Book.class);

        return Observable.fromArray(books);
    }
}
