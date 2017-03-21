package com.example.wen.wenbook.data.http;

import com.example.wen.wenbook.bean.WeChatToken;
import com.example.wen.wenbook.bean.WeChatUser;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * Created by wen on 2017/3/20.
 */

public interface ApiService {
    //https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code
    @GET("https://api.weixin.qq.com/sns/oauth2/access_token")
    Observable<WeChatToken> weChatToken(@QueryMap Map<String,String> options);

    // https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID
    @GET("https://api.weixin.qq.com/sns/userinfo")
    Observable<WeChatUser> weChatUser(@QueryMap Map<String,String> options);
}
