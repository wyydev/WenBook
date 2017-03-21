package com.example.wen.wenbook.presenter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

/**
 * Created by wen on 2017/2/28.
 */

public class BasePresenter<M,V extends BaseView> {

    protected M mModel;
    protected V mView;

    protected Context mContext;


    public BasePresenter(M model, V view) {
        mModel = model;
        mView = view;

        if(mView instanceof Fragment){
            mContext =((Fragment) mView).getActivity();
        }else {
            mContext = (Activity) mView;
        }
    }


}
