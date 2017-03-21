package com.example.wen.wenbook.presenter.contract;

import com.example.wen.wenbook.bean.WeChatToken;
import com.example.wen.wenbook.bean.WeChatUser;
import com.example.wen.wenbook.presenter.BaseView;

import io.reactivex.Observable;
import retrofit2.Call;

/**
 * Created by wen on 2017/3/20.
 */

public interface LonginContract {
    public interface ILoginModel{
        void askWeChatLogin();
        Observable<WeChatToken> weChatToken(String code);
        Observable<WeChatUser> weChatUser(String token,String openId);
        void qqLogin();
    }

    public interface LoginView extends BaseView{
        void showUser(WeChatUser user);
        void showError(String errorMsg);
    }
}
