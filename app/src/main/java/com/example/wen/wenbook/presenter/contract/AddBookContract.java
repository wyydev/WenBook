package com.example.wen.wenbook.presenter.contract;

import com.example.wen.wenbook.bean.Book;
import com.example.wen.wenbook.bean.DoubanBook;
import com.example.wen.wenbook.presenter.BaseView;

import io.reactivex.Observable;

/**
 * Created by wen on 2017/3/22.
 */

public interface AddBookContract {
    public interface IAddBookModel{
        Observable<DoubanBook> searchBookFromIsbn(String isbn);
    }

    public interface AddBookView extends BaseView{

       void showBook(Book book);
    }
}
