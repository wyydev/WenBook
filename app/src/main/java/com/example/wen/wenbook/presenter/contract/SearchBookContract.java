package com.example.wen.wenbook.presenter.contract;

import com.example.wen.wenbook.bean.Book;
import com.example.wen.wenbook.bean.DoubanBook;
import com.example.wen.wenbook.bean.DoubanBookBean;
import com.example.wen.wenbook.presenter.BaseView;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by wen on 2017/3/22.
 */

public interface SearchBookContract {
    public interface ISearchBookModel{
        Observable<DoubanBookBean> searchOnline(String bookName);
        Observable<List<Book>> searchLocal(String bookName);
    }

    public interface SearchView extends BaseView{
        void showLoading();
        void dismissLoading();
        void showResult(List<Book> bookList);
        void showError(String errorMsg);
    }
}
