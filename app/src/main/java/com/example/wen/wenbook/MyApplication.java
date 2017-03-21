package com.example.wen.wenbook;

import android.app.Application;
import android.content.Context;

import com.example.wen.wenbook.common.Constant;
import com.example.wen.wenbook.di.component.AppComponent;
import com.example.wen.wenbook.di.component.DaggerAppComponent;
import com.example.wen.wenbook.di.module.AppModule;
import com.example.wen.wenbook.di.module.HttpModule;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.litepal.LitePal;
import org.litepal.LitePalApplication;

/**
 * Created by wen on 2017/3/20.
 */

public class MyApplication extends Application {

    private AppComponent mAppComponent;

    public static MyApplication get(Context context){
        return (MyApplication) context.getApplicationContext();
    }

    private static final String APP_ID = Constant.WE_CHAT_APP_ID;

    public static IWXAPI mIWXAPI;

    @Override
    public void onCreate() {
        super.onCreate();

        register2Wx();

        //初始化LitePal
        LitePal.initialize(this);

        mAppComponent =  DaggerAppComponent.builder().appModule(new AppModule(this)).httpModule(new HttpModule()).build();

    }

    public  AppComponent getAppComponent(){
        return mAppComponent;
    }

    private void register2Wx(){

        mIWXAPI = WXAPIFactory.createWXAPI(this,APP_ID,true);

        mIWXAPI.registerApp(APP_ID);

    }

    public static IWXAPI getApi(){

        return mIWXAPI;
    }
}
