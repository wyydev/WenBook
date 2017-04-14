package com.example.wen.wenbook.common.rx.subscriber;

import android.content.Context;

import com.example.wen.wenbook.common.exception.BaseException;

/**
 * Created by wen on 2017/4/12.
 */

public abstract class ErrorHandleSubscriber<T> extends DefaultSubscriber<T> {
    protected ErrorHandler mErrorHandler;
    private Context mContext;

    public ErrorHandleSubscriber(Context context) {

        this.mContext = context;

        mErrorHandler = new ErrorHandler(mContext);

    }

    @Override
    public void onError(Throwable e) {



        BaseException baseException = mErrorHandler.handleError(e);

        if (baseException == null){
            e.printStackTrace();
        }else {
            mErrorHandler.showErrorMessage(baseException);

        }

    }
}
