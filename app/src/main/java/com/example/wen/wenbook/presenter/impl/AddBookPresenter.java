package com.example.wen.wenbook.presenter.impl;

import com.example.wen.wenbook.bean.DoubanBook;
import com.example.wen.wenbook.common.util.BookUtils;
import com.example.wen.wenbook.presenter.BasePresenter;
import com.example.wen.wenbook.presenter.contract.AddBookContract;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by wen on 2017/3/22.
 */

public class AddBookPresenter extends BasePresenter<AddBookContract.IAddBookModel,AddBookContract.AddBookView> {

    @Inject
    public AddBookPresenter(AddBookContract.IAddBookModel model, AddBookContract.AddBookView view) {
        super(model, view);
    }

    public void searchBookFromIsbn(String isbn){
        mModel.searchBookFromIsbn(isbn).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DoubanBook>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mView.showLoading();
                    }

                    @Override
                    public void onNext(DoubanBook value) {
                        mView.dismissLoading();
                        if (value != null){
                            mView.showBook(BookUtils.DoubanBook2Book(value));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.dismissLoading();
                        mView.showError(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        mView.dismissLoading();
                    }
                });
    }
}
