package com.example.wen.wenbook.common.rx;

import android.util.Log;

import com.example.wen.wenbook.bean.Book;
import com.example.wen.wenbook.bean.DoubanBook;
import com.example.wen.wenbook.bean.DoubanBookBean;
import com.example.wen.wenbook.common.util.BookUtils;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by wen on 2017/3/22.
 */

public class DoubanBookTransformer  {

    public static  ObservableTransformer<DoubanBookBean,List<DoubanBook>> transform(){

       /* return new ObservableTransformer<List<DoubanBook>, List<Book>>() {
            @Override
            public ObservableSource<List<Book>> apply(Observable<List<DoubanBook>> sourceObservable) {
                return sourceObservable.flatMap(new Function<List<DoubanBook>, ObservableSource<List<Book>>>() {
                    @Override
                    public ObservableSource<List<Book>> apply(final List<DoubanBook> doubanBooks) throws Exception {
                        if (!doubanBooks.isEmpty()){
                            return Observable.create(new ObservableOnSubscribe<List<Book>>() {
                                @Override
                                public void subscribe(ObservableEmitter<List<Book>> e) throws Exception {
                                    try {
                                        //发送转换的数据回去
                                        e.onNext(BookUtils.parseAll(doubanBooks));
                                        e.onComplete();
                                    } catch (Exception e1) {
                                        e.onError(e1);
                                    }
                                }
                            });
                        }else {
                            return Observable.error(new Exception("发生错误啦"));
                        }


                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        };*/

        return new ObservableTransformer<DoubanBookBean, List<DoubanBook>>() {
            @Override
            public ObservableSource<List<DoubanBook>> apply(Observable<DoubanBookBean> upstream) {
                return upstream.flatMap(new Function<DoubanBookBean, ObservableSource<List<DoubanBook>>>() {
                    @Override
                    public ObservableSource<List<DoubanBook>> apply(final DoubanBookBean doubanBookBean) throws Exception {
                       if (doubanBookBean != null){
                           return Observable.create(new ObservableOnSubscribe<List<DoubanBook>>() {
                               @Override
                               public void subscribe(ObservableEmitter<List<DoubanBook>> e) throws Exception {
                                   try {
                                       //发送转换的数据回去
                                       e.onNext(doubanBookBean.getBooks());
                                       Log.d("Transformer",doubanBookBean.toString());
                                       e.onComplete();
                                   } catch (Exception e1) {
                                       e.onError(e1);
                                   }
                               }
                           });
                       }else {
                           return Observable.error(new Exception("发生错误啦"));
                       }
                    }
                });
            }
        };

    }




}
