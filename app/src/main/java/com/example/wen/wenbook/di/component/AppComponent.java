package com.example.wen.wenbook.di.component;

import android.app.Application;

import com.example.wen.wenbook.data.http.ApiService;
import com.example.wen.wenbook.di.module.AppModule;
import com.example.wen.wenbook.di.module.HttpModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by wen on 2017/3/21.
 */
@Singleton
@Component(modules = {AppModule.class, HttpModule.class})
public interface AppComponent {

    //提供一个方法获取ApiService，会到相应的HttpModule寻找
    public ApiService getApiService();

    public Application getApplication();


}
