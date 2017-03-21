package com.example.wen.wenbook.presenter.impl;

import com.example.wen.wenbook.bean.WeChatToken;
import com.example.wen.wenbook.bean.WeChatUser;
import com.example.wen.wenbook.presenter.BasePresenter;
import com.example.wen.wenbook.presenter.contract.LonginContract;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by wen on 2017/3/20.
 */

public class LoginPresenter extends BasePresenter<LonginContract.ILoginModel,LonginContract.LoginView> {

    @Inject
    public LoginPresenter(LonginContract.ILoginModel model, LonginContract.LoginView view) {
        super(model, view);
    }

    public void askWeChatLogin(){
        mModel.askWeChatLogin();
    }

    public void weChatLogin(String code){
        mModel.weChatToken(code).subscribeOn(Schedulers.io()).subscribe(new Observer<WeChatToken>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(WeChatToken value) {
                if (value != null){
                    getUser(value.getAccess_token(),value.getOpenid());
                }
            }

            @Override
            public void onError(Throwable e) {
                mView.showError("获取token失败"+e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void getUser(String token,String openId) {
        mModel.weChatUser(token,openId).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<WeChatUser>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(WeChatUser value) {
                        if (value != null){
                            mView.showUser(value);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showError("获取用户信息失败"+e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void qqLogin(){
        mModel.qqLogin();
    }


}
