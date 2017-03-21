package com.example.wen.wenbook.data.model;

import android.content.Context;
import android.widget.Toast;

import com.example.wen.wenbook.BuildConfig;
import com.example.wen.wenbook.MyApplication;
import com.example.wen.wenbook.bean.WeChatToken;
import com.example.wen.wenbook.bean.WeChatUser;
import com.example.wen.wenbook.common.Constant;
import com.example.wen.wenbook.data.http.ApiService;
import com.example.wen.wenbook.presenter.contract.LonginContract;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.tencent.mm.opensdk.modelmsg.SendAuth;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by wen on 2017/3/20.
 */

public class LoginModel implements LonginContract.ILoginModel {

    private Context mContext;
    private ApiService mApiService;


    public LoginModel(Context context,ApiService apiService){

        this.mContext = context;
        this.mApiService = apiService;

    }

    @Override
    public void askWeChatLogin() {
        if(!MyApplication.getApi().isWXAppInstalled()){
            Toast.makeText(mContext, "请先安装微信", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(mContext, "正在唤起微信", Toast.LENGTH_SHORT).show();
            SendAuth.Req req = new SendAuth.Req();
            req.scope = "snsapi_userinfo";
            req.state = "Moke";
            MyApplication.getApi().sendReq(req);
        }
    }

    @Override
    public Observable<WeChatToken> weChatToken(String code) {

   /*     //构建请求token全路径url
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?"
                + "appid="+Constant.WE_CHAT_APP_ID+"&secret="+Constant.WE_CHAT_SECRET+"&code="+code+"&grant_type=authorization_code";*/

        //构建请求参数
        HashMap<String,String> params = new HashMap<>();
        params.put("appid",Constant.WE_CHAT_APP_ID);
        params.put("secret",Constant.WE_CHAT_SECRET);
        params.put("code",code);
        params.put("grant_type",Constant.GRANT_TYPE);


        return mApiService.weChatToken(params);

    }

    @Override
    public Observable<WeChatUser> weChatUser(String token,String openId) {
     /*   //构建请求user信息全路径url
        String url = "https://api.weixin.qq.com/sns/userinfo?"+"access_token="+token+"&openid="+openId;*/

        //构建请求参数
        HashMap<String,String> params = new HashMap<>();
        params.put("access_token",token);
        params.put("openid",openId);

        return mApiService.weChatUser(params);
    }





    @Override
    public void qqLogin() {

    }

}
