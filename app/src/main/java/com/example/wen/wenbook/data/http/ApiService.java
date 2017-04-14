package com.example.wen.wenbook.data.http;

import com.example.wen.wenbook.bean.Book;
import com.example.wen.wenbook.bean.DoubanBook;
import com.example.wen.wenbook.bean.DoubanBookBean;
import com.example.wen.wenbook.bean.GankDate;
import com.example.wen.wenbook.bean.TodayGank;
import com.example.wen.wenbook.bean.WeChatToken;
import com.example.wen.wenbook.bean.WeChatUser;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
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

    @GET("search")
    Observable<DoubanBookBean> searchBook(@Query("q") String q ,@Query("start") int start);

    @GET("isbn/{isbn}")
    Observable<DoubanBook> searchBookFromIsbn(@Path("isbn") String isbn);

    @GET("http://gank.io/api/day/{year}/{month}/{day}")
    Observable<TodayGank> todayGank(@Path("year") String year, @Path("month") String month, @Path("day") String day);

    @GET("http://gank.io/api/day/history")
    Observable<GankDate> gankDate();
}
