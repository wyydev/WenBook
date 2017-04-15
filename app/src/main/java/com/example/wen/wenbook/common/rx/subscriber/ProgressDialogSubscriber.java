package com.example.wen.wenbook.common.rx.subscriber;

import android.content.Context;

import com.example.wen.wenbook.common.exception.BaseException;
import com.example.wen.wenbook.presenter.BaseView;

import io.reactivex.disposables.Disposable;

/**
 * Created by wen on 2017/4/12.
 */

public abstract class ProgressDialogSubscriber<T> extends ErrorHandleSubscriber<T>{
    private BaseView mBaseView;

    /**
     * 是否需要显示ProgressDialog
     * 可以通过重写return false 表示不显示
     */
    protected boolean isShowProgressDialog(){
        return true;
    }

    public ProgressDialogSubscriber(Context context , BaseView baseView) {

        super(context);

        this.mBaseView = baseView;
    }

    @Override
    public void onSubscribe(Disposable d) {
        if (isShowProgressDialog()) {
            showProgressDialog();
        }
    }



    @Override
    public void onError(Throwable e) {

        if (isShowProgressDialog()){
            dismissProgressDialog();
        }
        BaseException baseException = mErrorHandler.handleError(e);

        mBaseView.showError(baseException.getMsg());
    }

    @Override
    public void onComplete() {
        if (isShowProgressDialog()){
            dismissProgressDialog();
        }

    }


    private void showProgressDialog() {
        mBaseView.showLoading();
    }

    private void dismissProgressDialog() {
        mBaseView.dismissLoading();
    }

}
