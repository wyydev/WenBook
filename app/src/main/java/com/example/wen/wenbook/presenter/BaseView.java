package com.example.wen.wenbook.presenter;

/**
 * Created by wen on 2017/3/20.
 */

public interface BaseView {
    void showLoading();
    void dismissLoading();
    void showError(String errorMsg);
}
