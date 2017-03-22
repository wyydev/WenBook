package com.example.wen.wenbook.presenter.impl;

import android.util.Log;

import com.example.wen.wenbook.bean.Book;
import com.example.wen.wenbook.bean.DoubanBook;
import com.example.wen.wenbook.bean.DoubanBookBean;
import com.example.wen.wenbook.common.Constant;
import com.example.wen.wenbook.common.rx.DoubanBookTransformer;
import com.example.wen.wenbook.common.util.BookUtils;
import com.example.wen.wenbook.presenter.BasePresenter;
import com.example.wen.wenbook.presenter.contract.SearchBookContract;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by wen on 2017/3/22.
 */

public class SearchBookPresenter extends BasePresenter<SearchBookContract.ISearchBookModel,SearchBookContract.SearchView> {

    @Inject
    public SearchBookPresenter(SearchBookContract.ISearchBookModel model, SearchBookContract.SearchView view) {
        super(model, view);
    }

    public void searchBook(int searchType, String bookName, final int start){


        switch (searchType){
            case Constant.SEARCH_LOCAL:

                // URLencode
                try {
                  //  bookName = URLEncoder.encode(bookName, "UTF-8");

                    if (bookName != null) {
                        // 用默认字符编码解码字符串。
                        byte[] bs = bookName.getBytes();
                        // 用新的字符编码生成字符串
                        bookName = new String(bs, "UTF-8");
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                mModel.searchLocal(bookName).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Book>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mView.showLoading();
                    }

                    @Override
                    public void onNext(List<Book> bookList) {
                        mView.dismissLoading();

                        Log.d("SearchBookPresenter",bookList.toString());
                            if (bookList != null){
                                mView.showResult(bookList,bookList.size());
                            }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showError(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        mView.dismissLoading();
                        mView.onLoadMoreComplete();
                    }
                });
                break;

            case Constant.SEARCH_NET:
              /*  mModel.searchOnline(bookName,start)
                .compose(DoubanBookTransformer.transform())
                        .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<List<DoubanBook>>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                                mView.showLoading();
                            }

                            @Override
                            public void onNext(List<DoubanBook> value) {
                                mView.dismissLoading();
                                if (value != null){
                                    mView.showResult(BookUtils.parseAll(value));
                                }

                            }

                            @Override
                            public void onError(Throwable e) {
                                mView.showError(e.getMessage());
                            }

                            @Override
                            public void onComplete() {
                                mView.dismissLoading();
                            }
                        });*/

                // URLencode
                try {
                    //bookName = URLEncoder.encode(bookName, "UTF-8");

                    if (bookName != null) {
                        // 用默认字符编码解码字符串。
                        byte[] bs = bookName.getBytes();
                        // 用新的字符编码生成字符串
                        bookName = new String(bs, "UTF-8");
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }



                mModel.searchOnline(bookName,start).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<DoubanBookBean>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                                if (start == 0)
                                mView.showLoading();
                            }

                            @Override
                            public void onNext(DoubanBookBean value) {

                                if (start == 0)
                                mView.dismissLoading();

                                if (value != null){
                                    mView.showResult(BookUtils.parseAll(value.getBooks()),value.getTotal());
                                }


                            }

                            @Override
                            public void onError(Throwable e) {
                                if (start == 0)
                                mView.dismissLoading();

                                mView.showError(e.getMessage());
                            }

                            @Override
                            public void onComplete() {
                                if (start == 0)
                                mView.dismissLoading();

                                mView.onLoadMoreComplete();
                            }
                        });

                break;
        }
    }
}
