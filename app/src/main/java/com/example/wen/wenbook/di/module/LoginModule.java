package com.example.wen.wenbook.di.module;

import android.app.Application;

import com.example.wen.wenbook.data.http.ApiService;
import com.example.wen.wenbook.data.model.LoginModel;
import com.example.wen.wenbook.presenter.contract.LonginContract;
import com.example.wen.wenbook.presenter.impl.LoginPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by wen on 2017/3/21.
 */

@Module
public class LoginModule {

    private LonginContract.LoginView mLoginView;

    public LoginModule(LonginContract.LoginView loginView){
        this.mLoginView = loginView;
    }

    @Provides
    public LoginPresenter provideLoginPresenter(LonginContract.ILoginModel iLoginModel, LonginContract.LoginView loginView){
        return new LoginPresenter(iLoginModel,loginView);
    }

    @Provides
    public LonginContract.LoginView provideLoginView(){
        return mLoginView;
    }

    @Provides
    public LonginContract.ILoginModel provideLoginModel(Application application, ApiService apiService){
        return new LoginModel(application,apiService);
    }
}
