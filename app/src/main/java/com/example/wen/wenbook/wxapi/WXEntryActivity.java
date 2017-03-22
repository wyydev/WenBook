package com.example.wen.wenbook.wxapi;


import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import com.example.wen.wenbook.BuildConfig;
import com.example.wen.wenbook.MyApplication;
import com.example.wen.wenbook.bean.WeChatUser;
import com.example.wen.wenbook.common.Constant;
import com.example.wen.wenbook.data.http.ApiService;
import com.example.wen.wenbook.data.model.LoginModel;
import com.example.wen.wenbook.di.module.LoginModule;
import com.example.wen.wenbook.presenter.contract.LonginContract;
import com.example.wen.wenbook.presenter.impl.LoginPresenter;
import com.example.wen.wenbook.ui.activity.BaseActivity;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by wen on 2017/3/20.
 */

public class WXEntryActivity extends Activity implements IWXAPIEventHandler ,LonginContract.LoginView{


   private LoginPresenter mLoginPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.getApi().handleIntent(getIntent(), this);

        mLoginPresenter = new LoginPresenter(new LoginModel(this,provideRetrofit(provideOkHttpClient()).create(ApiService.class)),this);

    }


    @Override
    public void onReq(BaseReq baseReq) {

    }

    /**
     * 发送到微信请求的响应结果
     * <p>
     * （1）用户同意授权后得到微信返回的一个code，将code替换到请求地址GetCodeRequest里的CODE，同样替换APPID和SECRET
     * （2）将新地址newGetCodeRequest通过HttpClient去请求，解析返回的JSON数据
     * （3）通过解析JSON得到里面的openid （用于获取用户个人信息）还有 access_token
     * （4）同样地，将openid和access_token替换到GetUnionIDRequest请求个人信息的地址里
     * （5）将新地址newGetUnionIDRequest通过HttpClient去请求，解析返回的JSON数据
     * （6）通过解析JSON得到该用户的个人信息，包括unionid
     */
    @Override
    public void onResp(BaseResp baseResp) {
        if (baseResp.getType() == 2) {
            finish();
        }

        if (baseResp.getType() == 1) {

            switch (baseResp.errCode) {
                // 同意授权
                case BaseResp.ErrCode.ERR_OK:

                    SendAuth.Resp respLogin = (SendAuth.Resp) baseResp;
                    // 获得code
                    String code = respLogin.code;

                    Log.d("WXEntryActivity","code:"+code);

                    mLoginPresenter.weChatLogin(code);
            }
        }
    }



    @Override
    public void showUser(WeChatUser user) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void dismissLoading() {

    }

    @Override
    public void showError(String errorMsg) {

    }


    public OkHttpClient provideOkHttpClient(){

        OkHttpClient.Builder builder = new OkHttpClient.Builder();


        if(BuildConfig.DEBUG){
            // log用拦截器
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();

            // 开发模式记录整个body，否则只记录基本信息如返回200，http协议版本等
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            builder.addInterceptor(logging);

        }

        // 如果使用到HTTPS，我们需要创建SSLSocketFactory，并设置到client
//        SSLSocketFactory sslSocketFactory = null;
        return builder
                // .addInterceptor(new CommonParamsInterceptor(application,gson))
                // 连接超时时间设置
                .connectTimeout(10, TimeUnit.SECONDS)
                // 读取超时时间设置
                .readTimeout(10, TimeUnit.SECONDS)

                .build();


    }

    public Retrofit provideRetrofit(OkHttpClient okHttpClient){


        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                // .addCallAdapterFactory(RxJavaCallAdapterFactory.create()) //1.0
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient);

        return builder.build();

    }
}
